package com.seniors.justlevelingfork.registry;

import com.seniors.justlevelingfork.JustLevelingFork;
import com.seniors.justlevelingfork.common.command.arguments.AptitudeArgument;
import com.seniors.justlevelingfork.common.command.arguments.TitleArgument;
import com.mojang.brigadier.arguments.ArgumentType;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.platform.Platform;
import net.minecraft.commands.synchronization.ArgumentTypeInfo;
import net.minecraft.commands.synchronization.ArgumentTypeInfos;
import net.minecraft.commands.synchronization.SingletonArgumentInfo;
import net.minecraft.core.registries.Registries;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class RegistryArgumentTypes {
    private static final DeferredRegister<ArgumentTypeInfo<?, ?>> ARGUMENT_TYPES = DeferredRegister.create(JustLevelingFork.MOD_ID, Registries.COMMAND_ARGUMENT_TYPE);

    static {
        ARGUMENT_TYPES.register("aptitude", () -> registerByClass(AptitudeArgument.class, SingletonArgumentInfo.contextFree(AptitudeArgument::getArgument)));
        ARGUMENT_TYPES.register("title", () -> registerByClass(TitleArgument.class, SingletonArgumentInfo.contextFree(TitleArgument::getArgument)));
    }

    public static void load() {
        if (Platform.isFabric()) {
            return;
        }
        ARGUMENT_TYPES.register();
    }

    @SuppressWarnings("unchecked")
    private static <A extends ArgumentType<?>> ArgumentTypeInfo<A, ?> registerByClass(Class<A> argumentClass, ArgumentTypeInfo<A, ?> info) {
        try {
            Method registerByClass = ArgumentTypeInfos.class.getMethod("registerByClass", Class.class, ArgumentTypeInfo.class);
            return (ArgumentTypeInfo<A, ?>) registerByClass.invoke(null, argumentClass, info);
        } catch (NoSuchMethodException ignored) {
            return info;
        } catch (IllegalAccessException | InvocationTargetException exception) {
            throw new RuntimeException("Failed to register command argument type " + argumentClass.getName(), exception);
        }
    }
}
