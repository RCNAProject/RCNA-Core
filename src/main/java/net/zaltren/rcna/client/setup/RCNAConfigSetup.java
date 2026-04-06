package net.zaltren.rcna.client.setup;

import com.github.lunatrius.ingameinfo.Alignment;
import com.github.lunatrius.ingameinfo.handler.ConfigurationHandler;
import net.minecraftforge.fml.common.Loader;
import net.zaltren.rcna.RCNACoreMod;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class RCNAConfigSetup {

    private static final float RCNA_SCALE = 0.62f;
    private static final int RCNA_TOPLEFT_X = 2;
    private static final int RCNA_TOPLEFT_Y = 75;

    public static void install() {
        File configDir = Loader.instance().getConfigDir();
        copyIfMissing("InGameInfo.xml", new File(configDir, "InGameInfo.xml"));
        patchIGIXMConfig(new File(configDir, "ingameinfoxml.cfg"));
        applyIGIXMSettings();
    }

    // Copy InGameInfo.xml into config if it doesn't already exist
    private static void copyIfMissing(String resourceName, File target) {
        if (target.exists()) return;
        try (java.io.InputStream in = RCNAConfigSetup.class.getResourceAsStream(
                "/assets/rcnacore/defaults/" + resourceName)) {
            if (in == null) {
                RCNACoreMod.LOGGER.warn("Bundled resource not found: {}", resourceName);
                return;
            }
            Files.copy(in, target.toPath());
            RCNACoreMod.LOGGER.info("Installed RCNA HUD config: {}", target.getName());
        } catch (Exception e) {
            RCNACoreMod.LOGGER.error("Failed to install RCNA HUD config", e);
        }
    }

    // Patch scale and topleft in ingameinfoxml.cfg — always applied so our values persist
    private static void patchIGIXMConfig(File cfg) {
        if (!cfg.exists()) return;
        try {
            List<String> lines = Files.readAllLines(cfg.toPath(), StandardCharsets.UTF_8);
            List<String> patched = new ArrayList<>();
            boolean scaleDone = false;
            boolean topleftDone = false;
            for (String line : lines) {
                String trimmed = line.trim();
                if (!scaleDone && trimmed.startsWith("S:scale=")) {
                    patched.add("    S:scale=" + RCNA_SCALE);
                    scaleDone = true;
                } else if (!topleftDone && trimmed.startsWith("S:topleft=")) {
                    patched.add("    S:topleft=" + RCNA_TOPLEFT_X + " " + RCNA_TOPLEFT_Y);
                    topleftDone = true;
                } else {
                    patched.add(line);
                }
            }
            Files.write(cfg.toPath(), patched, StandardCharsets.UTF_8);
            RCNACoreMod.LOGGER.info("Patched ingameinfoxml.cfg (scale={}, topleft={} {})",
                    RCNA_SCALE, RCNA_TOPLEFT_X, RCNA_TOPLEFT_Y);
        } catch (Exception e) {
            RCNACoreMod.LOGGER.error("Failed to patch ingameinfoxml.cfg", e);
        }
    }

    // Apply settings to IGIXM's in-memory state for immediate effect on first launch
    private static void applyIGIXMSettings() {
        try {
            ConfigurationHandler.scale = RCNA_SCALE;
            Alignment.TOPLEFT.x = RCNA_TOPLEFT_X;
            Alignment.TOPLEFT.y = RCNA_TOPLEFT_Y;
            RCNACoreMod.LOGGER.info("Applied IGIXM settings in memory (scale={}, topleft={} {})",
                    RCNA_SCALE, RCNA_TOPLEFT_X, RCNA_TOPLEFT_Y);
        } catch (Exception e) {
            RCNACoreMod.LOGGER.error("Failed to apply IGIXM settings in memory", e);
        }
    }
}
