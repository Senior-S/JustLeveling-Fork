package com.seniors.justlevelingfork;

import com.mojang.logging.LogUtils;
import com.seniors.justlevelingfork.config.Configuration;
import com.seniors.justlevelingfork.registry.*;
import net.minecraft.server.MinecraftServer;
import org.slf4j.Logger;

public class JustLevelingFork {
    public static final String MOD_ID = "justlevelingfork";
    public static final String MOD_NAME = "just_leveling_fork";

    private static final Logger LOGGER = LogUtils.getLogger();
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
    }

}
