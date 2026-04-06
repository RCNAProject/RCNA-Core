package net.zaltren.rcna.client.ingameinfo.tags;

import com.github.lunatrius.ingameinfo.tag.Tag;
import com.github.lunatrius.ingameinfo.tag.registry.TagRegistry;
import gregtech.api.GTValues;
import gregtech.api.capability.GregtechCapabilities;
import gregtech.api.capability.IElectricItem;
import gregtech.common.blocks.BlockOre;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;

public class GregTechTags {

    public static void register() {
        TagRegistry.INSTANCE.register(new OreChunk().setName("gtorchunk"));
        TagRegistry.INSTANCE.register(new DeadVein().setName("gtdeadvein"));
        TagRegistry.INSTANCE.register(new VoltageTier().setName("gtvoltage"));
    }

    // Returns true if the chunk is at the center of a GT 3x3 ore vein grid
    private static boolean isGridCenter(ChunkPos pos) {
        return Math.floorMod(pos.x, 3) == 1 && Math.floorMod(pos.z, 3) == 1;
    }

    // Returns true if the chunk contains any GT ore blocks (y=8–80)
    private static boolean hasGTOres(World world, ChunkPos pos) {
        int baseX = pos.x * 16;
        int baseZ = pos.z * 16;
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                for (int y = 8; y <= 80; y++) {
                    if (world.getBlockState(new BlockPos(baseX + x, y, baseZ + z)).getBlock() instanceof BlockOre) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    // Grid center chunk with GT ores present — an active ore vein
    private static class OreChunk extends Tag {
        private ChunkPos lastChunk = null;
        private String cached = "false";

        @Override public String getCategory() { return "gregtech"; }

        @Override
        public String getValue() {
            if (world == null || player == null) return "false";
            ChunkPos current = new ChunkPos(player.getPosition());
            if (current.equals(lastChunk)) return cached;

            lastChunk = current;
            cached = isGridCenter(current) && hasGTOres(world, current) ? "true" : "false";
            return cached;
        }
    }

    // Grid center chunk with NO GT ores — a dead vein (grid was skipped or rolled empty)
    private static class DeadVein extends Tag {
        private ChunkPos lastChunk = null;
        private String cached = "false";

        @Override public String getCategory() { return "gregtech"; }

        @Override
        public String getValue() {
            if (world == null || player == null) return "false";
            ChunkPos current = new ChunkPos(player.getPosition());
            if (current.equals(lastChunk)) return cached;

            lastChunk = current;
            cached = isGridCenter(current) && !hasGTOres(world, current) ? "true" : "false";
            return cached;
        }
    }

    // Highest GT voltage tier reached, based on electric items in the player's inventory
    private static class VoltageTier extends Tag {
        @Override public String getCategory() { return "gregtech"; }

        @Override
        public String getValue() {
            if (player == null) return "None";
            int maxTier = -1;
            for (int i = 0; i < player.inventory.getSizeInventory(); i++) {
                ItemStack stack = player.inventory.getStackInSlot(i);
                if (stack.isEmpty()) continue;
                IElectricItem item = stack.getCapability(GregtechCapabilities.CAPABILITY_ELECTRIC_ITEM, null);
                if (item != null) {
                    maxTier = Math.max(maxTier, item.getTier());
                }
            }
            return maxTier >= 0 ? GTValues.VN[maxTier] : "None";
        }
    }
}
