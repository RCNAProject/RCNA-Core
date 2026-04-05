package net.zaltren.rcna.common.registry;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.zaltren.rcna.RCNACoreMod;
import net.zaltren.rcna.common.block.BlockDebug;

@Mod.EventBusSubscriber(modid = RCNACoreMod.MODID)
public class ModBlocks {

    public static Block DEBUG_BLOCK;

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        DEBUG_BLOCK = new BlockDebug();
        event.getRegistry().register(DEBUG_BLOCK);
    }

    @SubscribeEvent
    public static void registerItemBlocks(RegistryEvent.Register<Item> event) {
        event.getRegistry().register(
                new ItemBlock(DEBUG_BLOCK)
                        .setRegistryName(DEBUG_BLOCK.getRegistryName())
        );
    }
}