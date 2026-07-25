package com.seniors.justlevelingfork.client.core;

import com.seniors.justlevelingfork.handler.HandlerAptitude;
import com.seniors.justlevelingfork.handler.HandlerCommonConfig;
import com.seniors.justlevelingfork.handler.HandlerConvergenceItemsConfig;
import com.seniors.justlevelingfork.handler.HandlerLockItemsConfig;
import com.seniors.justlevelingfork.registry.RegistryPassives;
import com.seniors.justlevelingfork.registry.RegistrySkills;

public final class ClientConfigurationSync {
    private ClientConfigurationSync() {
    }

    public static void refreshRegistryModels() {
        RegistrySkills.refreshFromConfig();
        RegistryPassives.refreshFromConfig();
    }

    public static void restoreLocalConfiguration() {
        HandlerCommonConfig.HANDLER.load();
        HandlerConvergenceItemsConfig.HANDLER.load();
        HandlerLockItemsConfig.HANDLER.load();
        refreshRegistryModels();
        HandlerAptitude.ForceRefresh();
    }
}
