package net.zaltren.rcna.client.ingameinfo.tags;

import WayofTime.bloodmagic.core.data.SoulNetwork;
import WayofTime.bloodmagic.util.helper.NetworkHelper;
import com.github.lunatrius.ingameinfo.tag.Tag;
import com.github.lunatrius.ingameinfo.tag.registry.TagRegistry;

public class BloodMagicTags {

    public static void register() {
        TagRegistry.INSTANCE.register(new LP().setName("bmlp"));
        TagRegistry.INSTANCE.register(new LPMax().setName("bmlpmax"));
        TagRegistry.INSTANCE.register(new Tier().setName("bmtier"));
    }

    // Current LP in the player's soul network
    private static class LP extends Tag {
        @Override public String getCategory() { return "bloodmagic"; }
        @Override
        public String getValue() {
            if (player == null) return "0";
            SoulNetwork network = NetworkHelper.getSoulNetwork(player);
            return network != null ? String.valueOf(network.getCurrentEssence()) : "0";
        }
    }

    // Maximum LP the player's soul network can hold
    private static class LPMax extends Tag {
        @Override public String getCategory() { return "bloodmagic"; }
        @Override
        public String getValue() {
            if (player == null) return "0";
            SoulNetwork network = NetworkHelper.getSoulNetwork(player);
            return network != null ? String.valueOf(NetworkHelper.getCurrentMaxOrb(network)) : "0";
        }
    }

    // The tier of the player's equipped soul orb (1-7)
    private static class Tier extends Tag {
        @Override public String getCategory() { return "bloodmagic"; }
        @Override
        public String getValue() {
            if (player == null) return "0";
            SoulNetwork network = NetworkHelper.getSoulNetwork(player);
            return network != null ? String.valueOf(network.getOrbTier()) : "0";
        }
    }
}
