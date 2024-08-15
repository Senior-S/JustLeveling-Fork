package com.seniors.justlevelingfork.registry;

import com.seniors.justlevelingfork.JustLevelingFork;
import com.seniors.justlevelingfork.common.command.arguments.AptitudeArgument;
import com.seniors.justlevelingfork.common.command.arguments.TitleArgument;
import net.minecraft.commands.synchronization.ArgumentTypeInfo;
import net.minecraft.commands.synchronization.ArgumentTypeInfos;
import net.minecraft.commands.synchronization.SingletonArgumentInfo;
import net.minecraft.core.registries.Registries;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;


public class RegistryArguments {
    private static final DeferredRegister<ArgumentTypeInfo<?, ?>> REGISTER = DeferredRegister.create(Registries.COMMAND_ARGUMENT_TYPE, JustLevelingFork.MOD_ID);

    public static final RegistryObject<SingletonArgumentInfo<AptitudeArgument>> APTITUDE_ARGUMENT = REGISTER.register("aptitude", () -> ArgumentTypeInfos.registerByClass(AptitudeArgument.class, SingletonArgumentInfo.contextFree(AptitudeArgument::getArgument)));

    public static final RegistryObject<SingletonArgumentInfo<TitleArgument>> TITLE_ARGUMENT = REGISTER.register("title", () -> ArgumentTypeInfos.registerByClass(TitleArgument.class, SingletonArgumentInfo.contextFree(TitleArgument::getArgument)));

    public static void load(IEventBus eventBus) {
        REGISTER.register(eventBus);
    }
}


