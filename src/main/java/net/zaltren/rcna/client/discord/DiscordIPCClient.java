package net.zaltren.rcna.client.discord;

import com.google.gson.JsonNull;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.management.ManagementFactory;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;

public class DiscordIPCClient {

    private static final int OP_HANDSHAKE = 0;
    private static final int OP_FRAME     = 1;

    private RandomAccessFile pipe;
    private volatile boolean connected = false;

    /**
     * Opens the Discord IPC pipe and performs the handshake.
     * Must be called from the dedicated RPC I/O thread only.
     */
    public void connect(long appId) throws Exception {
        for (int i = 0; i <= 9; i++) {
            try {
                pipe = new RandomAccessFile("\\\\.\\pipe\\discord-ipc-" + i, "rw");
                break;
            } catch (IOException ignored) {}
        }
        if (pipe == null) throw new Exception("Could not find Discord IPC pipe. Is Discord running?");

        JsonObject handshake = new JsonObject();
        handshake.addProperty("v", 1);
        handshake.addProperty("client_id", String.valueOf(appId));
        sendFrame(OP_HANDSHAKE, handshake.toString());

        // Read the READY response — all on the same thread, no concurrent I/O.
        readFrame();
        connected = true;
    }

    /**
     * Sends a SET_ACTIVITY command and reads Discord's ACK.
     * Must be called from the dedicated RPC I/O thread only.
     */
    public void sendPresence(String details, String state,
                             String largeImageKey, String largeImageText,
                             String smallImageKey, String smallImageText,
                             long startTimestamp) throws Exception {
        JsonObject activity = new JsonObject();
        if (details != null) activity.addProperty("details", details);
        if (state != null)   activity.addProperty("state", state);

        JsonObject timestamps = new JsonObject();
        timestamps.addProperty("start", startTimestamp);
        activity.add("timestamps", timestamps);

        JsonObject assets = new JsonObject();
        if (largeImageKey != null) {
            assets.addProperty("large_image", largeImageKey);
            if (largeImageText != null) assets.addProperty("large_text", largeImageText);
        }
        if (smallImageKey != null) {
            assets.addProperty("small_image", smallImageKey);
            if (smallImageText != null) assets.addProperty("small_text", smallImageText);
        }
        if (!assets.entrySet().isEmpty()) activity.add("assets", assets);

        sendActivityCommand(activity);
    }

    /**
     * Clears the Discord presence and reads Discord's ACK.
     * Must be called from the dedicated RPC I/O thread only.
     */
    public void clearPresence() throws Exception {
        JsonObject frame = new JsonObject();
        frame.addProperty("cmd", "SET_ACTIVITY");
        JsonObject args = new JsonObject();
        args.addProperty("pid", getProcessId());
        args.add("activity", JsonNull.INSTANCE);
        frame.add("args", args);
        frame.addProperty("nonce", String.valueOf(System.currentTimeMillis()));
        sendFrame(OP_FRAME, frame.toString());
        readFrame(); // drain Discord's ACK
    }

    public void close() {
        connected = false;
        try { pipe.close(); } catch (Exception ignored) {}
    }

    public boolean isConnected() {
        return connected;
    }

    private void sendActivityCommand(JsonObject activity) throws Exception {
        JsonObject frame = new JsonObject();
        frame.addProperty("cmd", "SET_ACTIVITY");
        JsonObject args = new JsonObject();
        args.addProperty("pid", getProcessId());
        args.add("activity", activity);
        frame.add("args", args);
        frame.addProperty("nonce", String.valueOf(System.currentTimeMillis()));
        sendFrame(OP_FRAME, frame.toString());
        readFrame(); // drain Discord's ACK — keeps reads and writes sequential
    }

    private void sendFrame(int opcode, String json) throws IOException {
        byte[] data = json.getBytes(StandardCharsets.UTF_8);
        ByteBuffer buf = ByteBuffer.allocate(8 + data.length);
        buf.order(ByteOrder.LITTLE_ENDIAN);
        buf.putInt(opcode);
        buf.putInt(data.length);
        buf.put(data);
        pipe.write(buf.array());
    }

    private String readFrame() throws IOException {
        byte[] header = new byte[8];
        pipe.readFully(header);
        ByteBuffer headerBuf = ByteBuffer.wrap(header);
        headerBuf.order(ByteOrder.LITTLE_ENDIAN);
        headerBuf.getInt(); // opcode — not needed
        int length = headerBuf.getInt();
        byte[] data = new byte[length];
        pipe.readFully(data);
        return new String(data, StandardCharsets.UTF_8);
    }

    private static int getProcessId() {
        String name = ManagementFactory.getRuntimeMXBean().getName();
        try {
            return Integer.parseInt(name.split("@")[0]);
        } catch (Exception e) {
            return 0;
        }
    }
}
