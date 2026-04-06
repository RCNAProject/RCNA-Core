package net.zaltren.rcna.client.ingameinfo.tags;

import com.github.lunatrius.ingameinfo.tag.Tag;
import com.github.lunatrius.ingameinfo.tag.registry.TagRegistry;
import net.minecraft.client.multiplayer.ChunkProviderClient;
import net.minecraft.world.WorldServer;
import net.minecraft.world.storage.WorldInfo;
import net.minecraftforge.common.DimensionManager;

public class WeatherTags {

    public static void register() {
        TagRegistry.INSTANCE.register(new WeatherTime().setName("weathertime"));
        TagRegistry.INSTANCE.register(new LoadedChunks().setName("chunkcount"));
        TagRegistry.INSTANCE.register(new IsSnowing().setName("issnowing"));
    }

    // Number of chunks currently loaded on the client
    private static class LoadedChunks extends Tag {
        @Override public String getCategory() { return "vanilla"; }

        @Override
        public String getValue() {
            if (world == null) return "0";
            // makeString() returns "MultiplayerChunkCache: X, X" — parse the count from it
            String s = ((ChunkProviderClient) world.getChunkProvider()).makeString();
            try {
                return s.split(": ")[1].split(",")[0].trim();
            } catch (Exception e) {
                return "?";
            }
        }
    }

    // Returns "true" if it is currently raining AND the player's position would produce snow
    // (biome is cold enough at current altitude). Uses world.canSnowAt() which is the correct
    // Minecraft API for snow-vs-rain detection — unlike the {snowing} IGIXM variable which is unreliable.
    private static class IsSnowing extends Tag {
        @Override public String getCategory() { return "vanilla"; }

        @Override
        public String getValue() {
            if (world == null || player == null) return "false";
            if (!world.isRaining()) return "false";
            return world.canSnowAt(player.getPosition(), false) ? "true" : "false";
        }
    }

    // Ticks until the current weather state changes, formatted as M:SS.
    // Rain timers are server-side only — read from the overworld WorldServer via DimensionManager.
    // Returns "N/A" if the server world is unavailable (e.g. multiplayer without a server component).
    private static class WeatherTime extends Tag {
        @Override public String getCategory() { return "vanilla"; }

        @Override
        public String getValue() {
            WorldServer serverWorld = DimensionManager.getWorld(0);
            if (serverWorld == null) return "N/A";

            WorldInfo info = serverWorld.getWorldInfo();
            int ticks = info.getCleanWeatherTime() > 0 ? info.getCleanWeatherTime() : info.getRainTime();
            if (ticks < 0) ticks = 0;
            int seconds = ticks / 20;
            return String.format("%d:%02d", seconds / 60, seconds % 60);
        }
    }
}
