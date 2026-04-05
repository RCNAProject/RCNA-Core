package net.zaltren.rcna.config;

import net.minecraftforge.common.config.Config;
import net.zaltren.rcnacore.Tags;

@Config(modid = Tags.MOD_ID, name = Tags.MOD_ID + "/discord")
public class DiscordConfig {

    @Config.Comment("Your Discord application ID. Create one at https://discord.com/developers/applications")
    @Config.LangKey("config.rcna.discord.app_id")
    public static String appId = "1490187420262469743";

    @Config.Comment("The asset key for the large image set in your Discord application.")
    @Config.LangKey("config.rcna.discord.large_image_key")
    public static String largeImageKey = "rcna_logo";

    @Config.Comment("The tooltip text shown when hovering over the large image (usually the pack name).")
    @Config.LangKey("config.rcna.discord.large_image_text")
    public static String largeImageText = "RunicCraft: New Ascension";

    @Config.Comment("Set to false to disable Discord Rich Presence entirely.")
    @Config.LangKey("config.rcna.discord.enabled")
    public static boolean enabled = true;
}
