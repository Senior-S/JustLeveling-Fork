package com.seniors.justlevelingfork;

import com.mojang.logging.LogUtils;
import com.seniors.justlevelingfork.config.Configuration;
import com.seniors.justlevelingfork.handler.HandlerCommonConfig;
import com.seniors.justlevelingfork.registry.*;
import dev.architectury.platform.Platform;
import net.minecraft.server.MinecraftServer;
import org.jetbrains.annotations.NotNull;
import org.apache.commons.lang3.tuple.MutablePair;
import org.slf4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.util.Objects;

public class JustLevelingFork {
    public static final String MOD_ID = "justlevelingfork";
    public static final String MOD_NAME = "just_leveling_fork";

    private static final Logger LOGGER = LogUtils.getLogger();
    public static MutablePair<Boolean, String> UpdatesAvailable = new MutablePair<>(false, "");
    public static MinecraftServer server;

    public static Logger getLOGGER() {
        return LOGGER;
    }

    public static void init() {
        Configuration.Init();

        RegistryArgumentTypes.load();
        RegistryItems.load();
        RegistryAptitudes.load();
        RegistryAttributes.load();
        RegistryPassives.load();
        RegistrySkills.load();
        RegistrySounds.load();
        RegistryTitles.load();

        if (HandlerCommonConfig.HANDLER.instance().checkForUpdates) {
            try {
                String version = getLatestVersion();
                String currentVersion = Platform.getOptionalMod(MOD_ID)
                        .map(dev.architectury.platform.Mod::getVersion)
                        .orElse("");
                if (!Objects.equals(currentVersion, version)) {
                    UpdatesAvailable.left = true;
                    UpdatesAvailable.right = version;
                    LOGGER.info(">> NEW VERSION AVAILABLE: {}", version);
                }
            } catch (Exception e) {
                LOGGER.warn(">> Error checking for updates!");
            }
        }
    }

    @NotNull
    private static String getLatestVersion() throws IOException {
        URL u = URI.create("https://raw.githubusercontent.com/Senior-S/JustLeveling-Fork/master/VERSION").toURL();
        URLConnection conn = u.openConnection();
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder buffer = new StringBuilder();
        String inputLine;
        while ((inputLine = in.readLine()) != null) buffer.append(inputLine);
        in.close();
        return buffer.toString();
    }

}
