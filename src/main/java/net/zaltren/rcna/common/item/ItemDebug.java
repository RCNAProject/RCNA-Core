package net.zaltren.rcna.common.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.zaltren.rcna.RCNACoreMod;

public class ItemDebug extends Item {

    public ItemDebug() {
        setRegistryName(RCNACoreMod.MODID, "debug_item");
        setTranslationKey(RCNACoreMod.MODID + ".debug_item");
        setCreativeTab(CreativeTabs.MISC);
    }

}
