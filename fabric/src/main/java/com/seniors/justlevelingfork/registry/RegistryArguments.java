package com.seniors.justlevelingfork.registry;

import com.seniors.justlevelingfork.JustLevelingFork;
import com.seniors.justlevelingfork.common.command.AptitudeLevelCommand;
import com.seniors.justlevelingfork.common.command.AptitudesReloadCommand;
import com.seniors.justlevelingfork.common.command.GlobalLimitCommand;
import com.seniors.justlevelingfork.common.command.RegisterItem;
import com.seniors.justlevelingfork.common.command.TitleCommand;
import com.seniors.justlevelingfork.common.command.UpdateAptitudeLevelCommand;
import com.seniors.justlevelingfork.common.command.arguments.AptitudeArgument;
import com.seniors.justlevelingfork.common.command.arguments.TitleArgument;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.command.v2.ArgumentTypeRegistry;
import net.minecraft.commands.synchronization.SingletonArgumentInfo;
import net.minecraft.resources.ResourceLocation;

public class RegistryArguments {
    public static void load() {
        ArgumentTypeRegistry.registerArgumentType(
                ResourceLocation.fromNamespaceAndPath(JustLevelingFork.MOD_ID, "aptitude"),
                AptitudeArgument.class,
                SingletonArgumentInfo.contextFree(AptitudeArgument::getArgument)
        );
        ArgumentTypeRegistry.registerArgumentType(
                ResourceLocation.fromNamespaceAndPath(JustLevelingFork.MOD_ID, "title"),
                TitleArgument.class,
                SingletonArgumentInfo.contextFree(TitleArgument::getArgument)
        );

        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            AptitudeLevelCommand.register(dispatcher);
            TitleCommand.register(dispatcher);
            AptitudesReloadCommand.register(dispatcher);
            RegisterItem.register(dispatcher);
            GlobalLimitCommand.register(dispatcher);
            UpdateAptitudeLevelCommand.register(dispatcher);
        });
    }
}
