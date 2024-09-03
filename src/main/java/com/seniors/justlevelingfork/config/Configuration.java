package com.seniors.justlevelingfork.config;

import com.seniors.justlevelingfork.handler.*;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.loading.FMLPaths;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Configuration {

    private static Path _absoluteDirectory = FMLPaths.CONFIGDIR.get().resolve("JLFork");

    public static Path getAbsoluteDirectory() {
        return _absoluteDirectory;
    }

    public static void Init() {
        Path clientConfigPath = Paths.get("JLFork").resolve("just_leveling-client.toml");
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, HandlerConfigClient.SPEC, clientConfigPath.toString());

        HandlerConvergenceItemsConfig.HANDLER.load();

        LoadDefaultTitles();
        ManageOldConfig();
    }

    private static void LoadDefaultTitles() {
        HandlerTitlesConfig.HANDLER.load();
    }

    private static void ManageOldConfig() {
        // Move old configuration files to the new folder.
        File oldCommonConfig = FMLPaths.CONFIGDIR.get().resolve("justleveling-fork.common.json5").toFile();
        File oldLockItems = FMLPaths.CONFIGDIR.get().resolve("justleveling-fork.lockItems.json5").toFile();

        if (oldCommonConfig.exists()) {
            oldCommonConfig.renameTo(_absoluteDirectory.resolve("justleveling-fork.common.json5").toFile());
            HandlerCommonConfig.HANDLER.load();
        }

        if (oldLockItems.exists()) {
            oldLockItems.renameTo(_absoluteDirectory.resolve("justleveling-fork.lockItems.json5").toFile());
            HandlerLockItemsConfig.HANDLER.load();
        }

        File originalConfigFile = FMLPaths.CONFIGDIR.get().resolve("just_leveling-common.toml").toFile();
        if (originalConfigFile.exists()) {
            if (HandlerCommonConfig.HANDLER.instance().usingNewConfig) {
                originalConfigFile.delete();
            } else {
                ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, HandlerConfigCommon.SPEC, "just_leveling-common.toml");
            }
        }

        HandlerCommonConfig.HANDLER.load();
        HandlerLockItemsConfig.HANDLER.load();
    }
}

