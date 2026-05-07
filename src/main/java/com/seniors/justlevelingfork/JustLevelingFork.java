package com.seniors.justlevelingfork;

import com.mojang.logging.LogUtils;
import com.seniors.justlevelingfork.config.Configuration;
import com.seniors.justlevelingfork.handler.HandlerCommonConfig;
import com.seniors.justlevelingfork.integration.TacZIntegration;
import com.seniors.justlevelingfork.network.ServerNetworking;
import com.seniors.justlevelingfork.network.packet.client.CommonConfigSyncCP;
import com.seniors.justlevelingfork.network.packet.client.ConfigSyncCP;
import com.seniors.justlevelingfork.network.packet.client.DynamicConfigSyncCP;
import com.seniors.justlevelingfork.network.packet.client.SyncAptitudeCapabilityCP;
import com.seniors.justlevelingfork.registry.*;
import com.seniors.justlevelingfork.registry.aptitude.Aptitude;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.loader.api.FabricLoader;
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

public class JustLevelingFork implements ModInitializer {
    public static final String MOD_ID = "justlevelingfork";
    public static final String MOD_NAME = "just_leveling_fork";

    private static final Logger LOGGER = LogUtils.getLogger();
    public static MutablePair<Boolean, String> UpdatesAvailable = new MutablePair<>(false, "");
    public static MinecraftServer server;

    public static Logger getLOGGER() {
        return LOGGER;
    }

    @Override
    public void onInitialize() {
        Configuration.Init();

        RegistryItems.load();
        RegistryAptitudes.load();
        RegistryAttributes.load();
        RegistryPassives.load();
        RegistrySkills.load();
        RegistrySounds.load();
        RegistryArguments.load();
        RegistryTitles.load();
        RegistryFabricEvents.load();
        if (FabricLoader.getInstance().isModLoaded("tacz")) {
            TacZIntegration.load();
        }

        ServerNetworking.init();
        ServerLifecycleEvents.SERVER_STARTED.register(server -> JustLevelingFork.server = server);
        ServerLifecycleEvents.SERVER_STOPPED.register(server -> JustLevelingFork.server = null);
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            ConfigSyncCP.sendToPlayer(handler.player);
            CommonConfigSyncCP.sendToPlayer(handler.player);
            DynamicConfigSyncCP.sendToPlayer(handler.player);
            SyncAptitudeCapabilityCP.send(handler.player);
        });

        if (HandlerCommonConfig.HANDLER.instance().checkForUpdates) {
            try {
                String version = getLatestVersion();
                String currentVersion = FabricLoader.getInstance().getModContainer(MOD_ID)
                        .map(container -> container.getMetadata().getVersion().getFriendlyString())
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
