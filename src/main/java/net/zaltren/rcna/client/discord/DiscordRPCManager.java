package net.zaltren.rcna.client.discord;

import net.zaltren.rcna.RCNACoreMod;
import net.zaltren.rcna.config.DiscordConfig;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class DiscordRPCManager {

    // Single-threaded executor — all pipe I/O runs on this one thread, never concurrently.
    private static ExecutorService rpcExecutor;
    private static volatile DiscordIPCClient client;
    private static long startTimestamp;

    public static void init() {
        if (!DiscordConfig.enabled) {
            RCNACoreMod.LOGGER.info("Discord Rich Presence is disabled in config.");
            return;
        }
        startTimestamp = System.currentTimeMillis() / 1000L;
        rpcExecutor = Executors.newSingleThreadExecutor(r -> {
            Thread t = new Thread(r, "discord-rpc-io");
            t.setDaemon(true);
            return t;
        });
        rpcExecutor.submit(() -> {
            try {
                client = new DiscordIPCClient();
                client.connect(Long.parseLong(DiscordConfig.appId));
                RCNACoreMod.LOGGER.info("Connected to Discord.");
                sendPresenceInternal("In the Main Menu", null, null, null);
            } catch (Exception e) {
                RCNACoreMod.LOGGER.warn("Could not connect to Discord: {}", e.getMessage());
            }
        });
    }

    public static void updatePresence(String details, String state) {
        updatePresence(details, state, null, null);
    }

    public static void updatePresence(String details, String state, String smallImageKey, String smallImageText) {
        if (rpcExecutor == null || rpcExecutor.isShutdown()) return;
        rpcExecutor.submit(() -> sendPresenceInternal(details, state, smallImageKey, smallImageText));
    }

    /** Must only be called from the rpcExecutor thread. */
    private static void sendPresenceInternal(String details, String state, String smallImageKey, String smallImageText) {
        if (client == null || !client.isConnected()) return;
        try {
            client.sendPresence(details, state, DiscordConfig.largeImageKey, DiscordConfig.largeImageText, smallImageKey, smallImageText, startTimestamp);
            RCNACoreMod.LOGGER.info("Presence updated: {}", details);
        } catch (Exception e) {
            RCNACoreMod.LOGGER.warn("Failed to update Discord presence: {}", e.getMessage());
        }
    }

    public static void shutdown() {
        if (rpcExecutor != null) {
            rpcExecutor.shutdown();
            try { rpcExecutor.awaitTermination(1, TimeUnit.SECONDS); } catch (Exception ignored) {}
        }
        if (client == null || !client.isConnected()) return;
        try { client.clearPresence(); } catch (Exception ignored) {}
        try { Thread.sleep(500); } catch (Exception ignored) {}
        try { client.close(); } catch (Exception ignored) {}
    }
}
