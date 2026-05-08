package com.seniors.justlevelingfork.fabric;

import com.seniors.justlevelingfork.JustLevelingFork;
import com.seniors.justlevelingfork.integration.TacZIntegration;
import com.seniors.justlevelingfork.integration.ftbquests.FTBQuestsIntegration;
import com.seniors.justlevelingfork.integration.questlog.QuestlogIntegration;
import com.seniors.justlevelingfork.network.ServerNetworking;
import com.seniors.justlevelingfork.network.packet.client.CommonConfigSyncCP;
import com.seniors.justlevelingfork.network.packet.client.ConfigSyncCP;
import com.seniors.justlevelingfork.network.packet.client.DynamicConfigSyncCP;
import com.seniors.justlevelingfork.network.packet.client.SyncAptitudeCapabilityCP;
import com.seniors.justlevelingfork.registry.RegistryArguments;
import com.seniors.justlevelingfork.registry.RegistryFabricEvents;
import dev.architectury.platform.Platform;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;

public class JustLevelingForkFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        JustLevelingFork.init();
        RegistryArguments.load();
        RegistryFabricEvents.load();

        if (Platform.isModLoaded("tacz")) {
            TacZIntegration.load();
        }
        if (Platform.isModLoaded("ftbquests")) {
            FTBQuestsIntegration.load();
        }
        if (Platform.isModLoaded("questlog")) {
            QuestlogIntegration.load();
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
    }
}
