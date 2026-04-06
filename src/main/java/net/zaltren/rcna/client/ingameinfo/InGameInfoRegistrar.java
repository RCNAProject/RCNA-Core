package net.zaltren.rcna.client.ingameinfo;

import net.minecraftforge.fml.common.Loader;
import net.zaltren.rcna.RCNACoreMod;
import net.zaltren.rcna.client.ingameinfo.tags.BloodMagicTags;
import net.zaltren.rcna.client.ingameinfo.tags.GregTechTags;
import net.zaltren.rcna.client.ingameinfo.tags.SeasonTags;
import net.zaltren.rcna.client.ingameinfo.tags.ThaumcraftTags;
import net.zaltren.rcna.client.ingameinfo.tags.ToughAsNailsTags;
import net.zaltren.rcna.client.ingameinfo.tags.WeatherTags;

public class InGameInfoRegistrar {

    public static void register() {
        if (!Loader.isModLoaded("ingameinfoxml")) return;

        WeatherTags.register();
        RCNACoreMod.LOGGER.info("Registered InGame Info XML tags for Weather.");

        if (Loader.isModLoaded("thaumcraft")) {
            ThaumcraftTags.register();
            RCNACoreMod.LOGGER.info("Registered InGame Info XML tags for Thaumcraft.");
        }
        if (Loader.isModLoaded("bloodmagic")) {
            BloodMagicTags.register();
            RCNACoreMod.LOGGER.info("Registered InGame Info XML tags for Blood Magic.");
        }
        if (Loader.isModLoaded("sereneseasons")) {
            SeasonTags.register();
            RCNACoreMod.LOGGER.info("Registered InGame Info XML tags for Serene Seasons.");
        }
        if (Loader.isModLoaded("toughasnails")) {
            ToughAsNailsTags.register();
            RCNACoreMod.LOGGER.info("Registered InGame Info XML tags for Tough as Nails.");
        }
        if (Loader.isModLoaded("gregtech")) {
            GregTechTags.register();
            RCNACoreMod.LOGGER.info("Registered InGame Info XML tags for GregTech.");
        }
    }
}
