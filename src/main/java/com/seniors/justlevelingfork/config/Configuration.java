package com.seniors.justlevelingfork.config;

import com.seniors.justlevelingfork.handler.*;
import net.fabricmc.loader.api.FabricLoader;

import java.io.File;
import java.nio.file.Path;

public class Configuration {

    private static Path _absoluteDirectory = FabricLoader.getInstance().getConfigDir().resolve("JLFork");

    public static Path getAbsoluteDirectory() {
        return _absoluteDirectory;
    }

    public static void Init() {
        HandlerConvergenceItemsConfig.HANDLER.load();

        LoadDefaultTitles();
        ManageOldConfig();
    }

    private static void LoadDefaultTitles() {
        HandlerTitlesConfig.HANDLER.load();
    }

    private static void ManageOldConfig() {
        // Move old configuration files to the new folder.
        File oldCommonConfig = FabricLoader.getInstance().getConfigDir().resolve("justleveling-fork.common.json5").toFile();
        File oldLockItems = FabricLoader.getInstance().getConfigDir().resolve("justleveling-fork.lockItems.json5").toFile();

        if (oldCommonConfig.exists()) {
            oldCommonConfig.renameTo(_absoluteDirectory.resolve("justleveling-fork.common.json5").toFile());
            HandlerCommonConfig.HANDLER.load();
        }

        if (oldLockItems.exists()) {
            oldLockItems.renameTo(_absoluteDirectory.resolve("justleveling-fork.lockItems.json5").toFile());
            HandlerLockItemsConfig.HANDLER.load();
        }
        HandlerCommonConfig.HANDLER.load();
        HandlerLockItemsConfig.HANDLER.load();
    }
}

