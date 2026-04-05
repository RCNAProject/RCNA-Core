package net.zaltren.rcna.client.ingameinfo;

import net.minecraftforge.fml.common.Loader;
import net.zaltren.rcna.RCNACoreMod;
import net.zaltren.rcna.client.ingameinfo.tags.BloodMagicTags;
import net.zaltren.rcna.client.ingameinfo.tags.SeasonTags;
import net.zaltren.rcna.client.ingameinfo.tags.ThaumcraftTags;
import net.zaltren.rcna.client.ingameinfo.tags.ToughAsNailsTags;

public class InGameInfoRegistrar {

    public static void register() {
        if (!Loader.isModLoaded("ingameinfoxml")) return;

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
    }
}
