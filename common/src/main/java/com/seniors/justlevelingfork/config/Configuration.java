package com.seniors.justlevelingfork.config;

import com.seniors.justlevelingfork.handler.*;
import com.seniors.justlevelingfork.JustLevelingFork;
import dev.architectury.platform.Platform;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Configuration {

    private static Path _absoluteDirectory = Platform.getConfigFolder().resolve("JLFork");

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
        try {
            Files.createDirectories(_absoluteDirectory);
        } catch (IOException exception) {
            JustLevelingFork.getLOGGER().error("Could not create configuration directory {}", _absoluteDirectory, exception);
        }

        migrateLegacyConfig("justleveling-fork.common.json5");
        migrateLegacyConfig("justleveling-fork.lockItems.json5");
        HandlerCommonConfig.HANDLER.load();
        HandlerLockItemsConfig.HANDLER.load();
    }

    private static void migrateLegacyConfig(String fileName) {
        Path legacyPath = Platform.getConfigFolder().resolve(fileName);
        Path targetPath = _absoluteDirectory.resolve(fileName);
        if (Files.notExists(legacyPath) || Files.exists(targetPath)) {
            return;
        }

        try {
            Files.move(legacyPath, targetPath);
        } catch (IOException exception) {
            JustLevelingFork.getLOGGER().error("Could not migrate legacy configuration {} to {}", legacyPath, targetPath, exception);
        }
    }
}

