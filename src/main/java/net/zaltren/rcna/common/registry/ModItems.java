package net.zaltren.rcna.common.registry;

import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.zaltren.rcna.RCNACoreMod;
import net.zaltren.rcna.common.item.ItemDebug;

@Mod.EventBusSubscriber(modid = RCNACoreMod.MODID)
public class ModItems {

    public static Item DEBUG_ITEM;

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {

        DEBUG_ITEM = new ItemDebug();
        event.getRegistry().register(DEBUG_ITEM);
    }

}
