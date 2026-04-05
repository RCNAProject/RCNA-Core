package net.zaltren.rcna.client.ingameinfo.tags;

import com.github.lunatrius.ingameinfo.tag.Tag;
import com.github.lunatrius.ingameinfo.tag.registry.TagRegistry;
import thaumcraft.api.aura.AuraHelper;
import thaumcraft.api.capabilities.IPlayerWarp;
import thaumcraft.api.capabilities.ThaumcraftCapabilities;

public class ThaumcraftTags {

    public static void register() {
        TagRegistry.INSTANCE.register(new Vis().setName("tcvis"));
        TagRegistry.INSTANCE.register(new Flux().setName("tcflux"));
        TagRegistry.INSTANCE.register(new Warp(IPlayerWarp.EnumWarpType.NORMAL).setName("tcwarp"));
        TagRegistry.INSTANCE.register(new Warp(IPlayerWarp.EnumWarpType.TEMPORARY).setName("tcwarptemp"));
        TagRegistry.INSTANCE.register(new Warp(IPlayerWarp.EnumWarpType.PERMANENT).setName("tcwarpperm"));
    }

    // Vis level in the current chunk's aura
    private static class Vis extends Tag {
        @Override public String getCategory() { return "thaumcraft"; }
        @Override
        public String getValue() {
            if (world == null || player == null) return "0";
            return String.format("%.1f", AuraHelper.getVis(world, player.getPosition()));
        }
    }

    // Flux level in the current chunk's aura
    private static class Flux extends Tag {
        @Override public String getCategory() { return "thaumcraft"; }
        @Override
        public String getValue() {
            if (world == null || player == null) return "0";
            return String.format("%.1f", AuraHelper.getFlux(world, player.getPosition()));
        }
    }

    // Warp level (normal, temporary, or permanent depending on type passed)
    private static class Warp extends Tag {
        private final IPlayerWarp.EnumWarpType type;
        Warp(IPlayerWarp.EnumWarpType type) { this.type = type; }

        @Override public String getCategory() { return "thaumcraft"; }
        @Override
        public String getValue() {
            if (player == null) return "0";
            IPlayerWarp warp = ThaumcraftCapabilities.getWarp(player);
            return warp != null ? String.valueOf(warp.get(type)) : "0";
        }
    }
}
