package net.zaltren.rcna.common.registry;

import net.minecraft.item.Item;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.zaltren.rcna.RCNACoreMod;

@SideOnly(Side.CLIENT)
@Mod.EventBusSubscriber(value = Side.CLIENT, modid = RCNACoreMod.MODID)
public class ModelRegistry {

    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event) {

        // Register block item model
        ModelLoader.setCustomModelResourceLocation(
                Item.getItemFromBlock(ModBlocks.DEBUG_BLOCK),
                0,
                new ModelResourceLocation(ModBlocks.DEBUG_BLOCK.getRegistryName(), "inventory")
        );

        // Register debug item model
        ModelLoader.setCustomModelResourceLocation(
                ModItems.DEBUG_ITEM,
                0,
                new ModelResourceLocation("rcnacore:debug_item", "inventory")
        );
    }
}
