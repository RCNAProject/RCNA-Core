package net.zaltren.rcna.client.discord;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.gui.GuiWorldSelection;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.GameType;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.zaltren.rcna.RCNACoreMod;

@SideOnly(Side.CLIENT)
@Mod.EventBusSubscriber(value = Side.CLIENT, modid = RCNACoreMod.MODID)
public class RPCHandler {

    private static final int UPDATE_INTERVAL = 200; // ticks (10 seconds)
    private static int tickCounter = 0;
    private static boolean wasInWorld = false;

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;

        Minecraft mc = Minecraft.getMinecraft();

        if (mc.player == null || mc.world == null) {
            if (wasInWorld) {
                wasInWorld = false;
                tickCounter = 0;
                DiscordRPCManager.updatePresence("In the Main Menu", null);
            }
            return;
        }

        wasInWorld = true;
        tickCounter++;
        if (tickCounter < UPDATE_INTERVAL) return;
        tickCounter = 0;

        updateInGamePresence(mc);
    }

    @SubscribeEvent
    public static void onGuiOpen(GuiOpenEvent event) {
        Minecraft mc = Minecraft.getMinecraft();

        if (mc.player == null) {
            // Main menu navigation
            if (event.getGui() instanceof GuiWorldSelection) {
                DiscordRPCManager.updatePresence("Selecting a World", null);
            } else if (event.getGui() instanceof GuiMultiplayer) {
                DiscordRPCManager.updatePresence("Selecting a Server", null);
            } else if (event.getGui() instanceof GuiOptions) {
                DiscordRPCManager.updatePresence("In Settings", null);
            } else if (event.getGui() instanceof GuiMainMenu) {
                DiscordRPCManager.updatePresence("In the Main Menu", null);
            }
        } else {
            // In-game screen changes
            if (event.getGui() instanceof GuiOptions) {
                DiscordRPCManager.updatePresence("In Settings", null);
            } else {
                // Screen closed or changed — force a presence refresh on the next tick
                tickCounter = UPDATE_INTERVAL;
            }
        }
    }

    private static void updateInGamePresence(Minecraft mc) {
        EntityPlayer player = mc.player;

        // Singleplayer vs Multiplayer
        String mode = mc.getCurrentServerData() != null ? "Multiplayer" : "Singleplayer";

        // Activity
        String activity;
        if (player.getHealth() <= 0) {
            activity = "Dead";
        } else if (player.isPlayerSleeping()) {
            activity = "Sleeping";
        } else {
            activity = "Exploring " + getDimensionName(player.dimension);
        }

        // Biome
        Biome biome = mc.world.getBiome(player.getPosition());
        String biomeName = biome.getBiomeName();

        // Health
        int health = (int) Math.ceil(player.getHealth());
        int maxHealth = (int) Math.ceil(player.getMaxHealth());

        // World / server name
        String worldName = getWorldName(mc);

        // State line: mode | biome | health | world
        String state = mode + " | " + biomeName + " | \u2764 " + health + "/" + maxHealth;
        if (worldName != null) state += " | " + worldName;

        // Gamemode small image
        GameType gameType = mc.playerController.getCurrentGameType();
        String gamemodeKey = getGamemodeKey(gameType);
        String gamemodeName = getGamemodeName(gameType);

        DiscordRPCManager.updatePresence(activity, state, gamemodeKey, gamemodeName);
    }

    private static String getWorldName(Minecraft mc) {
        if (mc.getIntegratedServer() != null) {
            return mc.getIntegratedServer().getWorldName();
        } else if (mc.getCurrentServerData() != null) {
            return mc.getCurrentServerData().serverName;
        }
        return null;
    }

    private static String getGamemodeKey(GameType gameType) {
        switch (gameType) {
            case SURVIVAL:  return "survival";
            case CREATIVE:  return "creative";
            case ADVENTURE: return "adventure";
            case SPECTATOR: return "spectator";
            default:        return null;
        }
    }

    private static String getGamemodeName(GameType gameType) {
        switch (gameType) {
            case SURVIVAL:  return "Survival";
            case CREATIVE:  return "Creative";
            case ADVENTURE: return "Adventure";
            case SPECTATOR: return "Spectator";
            default:        return "Unknown";
        }
    }

    private static String getDimensionName(int dimensionId) {
        switch (dimensionId) {
            case -1: return "The Nether";
            case  0: return "The Overworld";
            case  1: return "The End";
            default: return "Dimension " + dimensionId;
        }
    }
}
