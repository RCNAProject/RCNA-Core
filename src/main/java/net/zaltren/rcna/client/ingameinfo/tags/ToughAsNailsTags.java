package net.zaltren.rcna.client.ingameinfo.tags;

import com.github.lunatrius.ingameinfo.tag.Tag;
import com.github.lunatrius.ingameinfo.tag.registry.TagRegistry;
import toughasnails.api.TANCapabilities;
import toughasnails.api.stat.capability.ITemperature;
import toughasnails.api.stat.capability.IThirst;

public class ToughAsNailsTags {

    public static void register() {
        TagRegistry.INSTANCE.register(new Temperature().setName("tantemp"));
        TagRegistry.INSTANCE.register(new Thirst().setName("tanthirst"));
        TagRegistry.INSTANCE.register(new Hydration().setName("tanhydration"));
    }

    // Player body temperature as a readable label (Icy / Cool / Mild / Warm / Hot)
    private static class Temperature extends Tag {
        @Override public String getCategory() { return "toughasnails"; }
        @Override
        public String getValue() {
            if (player == null) return "Unknown";
            ITemperature temp = player.getCapability(TANCapabilities.TEMPERATURE, null);
            if (temp == null || temp.getTemperature() == null || temp.getTemperature().getRange() == null)
                return "Unknown";
            String range = temp.getTemperature().getRange().name();
            return Character.toUpperCase(range.charAt(0)) + range.substring(1).toLowerCase();
        }
    }

    // Player thirst level (0-20, like hunger)
    private static class Thirst extends Tag {
        @Override public String getCategory() { return "toughasnails"; }
        @Override
        public String getValue() {
            if (player == null) return "0";
            IThirst thirst = player.getCapability(TANCapabilities.THIRST, null);
            return thirst != null ? String.valueOf(thirst.getThirst()) : "0";
        }
    }

    // Player hydration level (like saturation for thirst)
    private static class Hydration extends Tag {
        @Override public String getCategory() { return "toughasnails"; }
        @Override
        public String getValue() {
            if (player == null) return "0";
            IThirst thirst = player.getCapability(TANCapabilities.THIRST, null);
            return thirst != null ? String.format("%.1f", thirst.getHydration()) : "0";
        }
    }
}
