package net.zaltren.rcna.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.SoundType;
import net.minecraft.creativetab.CreativeTabs;
import net.zaltren.rcna.RCNACoreMod;

public class BlockDebug extends Block {

    public BlockDebug() {
        super(Material.ROCK);
        setRegistryName(RCNACoreMod.MODID, "debug_block");
        setTranslationKey(RCNACoreMod.MODID + ".debug_block");
        setCreativeTab(CreativeTabs.MISC);

        setHardness(2.0F);
        setResistance(10.0F);
        setSoundType(SoundType.STONE);
    }

}
