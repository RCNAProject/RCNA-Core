package net.zaltren.rcna;

import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.zaltren.rcna.common.CommonProxy;
import net.zaltren.rcnacore.Tags;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = Tags.MOD_ID, name = Tags.MOD_NAME, version = Tags.VERSION)
public class RCNACoreMod {

    public static final String MODID = Tags.MOD_ID;

    @SidedProxy(
            clientSide = "net.zaltren.rcna.client.ClientProxy",
            serverSide = "net.zaltren.rcna.common.CommonProxy"
    )
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init();
    }

    public static final Logger LOGGER = LogManager.getLogger(Tags.MOD_NAME);
    /**
     * https://cleanroommc.com/wiki/forge-mod-development/event#overview
     */
    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        LOGGER.info("{} is initializing.", Tags.MOD_NAME);
        ConfigManager.sync(MODID, net.minecraftforge.common.config.Config.Type.INSTANCE);
    }

    @SubscribeEvent
    public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
        if (event.getModID().equals(MODID)) {
            ConfigManager.sync(MODID, net.minecraftforge.common.config.Config.Type.INSTANCE);
        }
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {

    }

}
