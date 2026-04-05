package net.zaltren.rcna.client.ingameinfo.tags;

import com.github.lunatrius.ingameinfo.tag.Tag;
import com.github.lunatrius.ingameinfo.tag.registry.TagRegistry;
import sereneseasons.api.season.ISeasonState;
import sereneseasons.api.season.SeasonHelper;

public class SeasonTags {

    public static void register() {
        TagRegistry.INSTANCE.register(new CurrentSeason().setName("season"));
        TagRegistry.INSTANCE.register(new CurrentSubSeason().setName("seasonsub"));
    }

    // Current season: Spring, Summer, Autumn, Winter
    private static class CurrentSeason extends Tag {
        @Override public String getCategory() { return "sereneseasons"; }
        @Override
        public String getValue() {
            if (world == null) return "N/A";
            ISeasonState state = SeasonHelper.getSeasonState(world);
            if (state == null) return "N/A";
            return formatName(state.getSeason().name());
        }
    }

    // Current sub-season: Early Spring, Mid Summer, Late Autumn, etc.
    private static class CurrentSubSeason extends Tag {
        @Override public String getCategory() { return "sereneseasons"; }
        @Override
        public String getValue() {
            if (world == null) return "N/A";
            ISeasonState state = SeasonHelper.getSeasonState(world);
            if (state == null) return "N/A";
            return formatName(state.getSubSeason().name());
        }
    }

    // Converts "EARLY_SPRING" -> "Early Spring"
    private static String formatName(String enumName) {
        String[] parts = enumName.split("_");
        StringBuilder sb = new StringBuilder();
        for (String part : parts) {
            if (sb.length() > 0) sb.append(" ");
            sb.append(Character.toUpperCase(part.charAt(0)));
            sb.append(part.substring(1).toLowerCase());
        }
        return sb.toString();
    }
}
