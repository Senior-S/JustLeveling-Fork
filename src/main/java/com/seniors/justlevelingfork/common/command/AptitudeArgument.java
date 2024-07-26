package com.seniors.justlevelingfork.common.command;

import com.seniors.justlevelingfork.registry.RegistryAptitudes;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;

import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class AptitudeArgument implements ArgumentType<ResourceLocation> {
    public static final Collection<String> EXAMPLES = Arrays.asList("foo", "foo:bar", "012");

    static {
        ERROR_UNKNOWN_APTITUDE = new DynamicCommandExceptionType(object -> Component.translatable("commands.argument.aptitude.not_found", object));
    }

    public static final DynamicCommandExceptionType ERROR_UNKNOWN_APTITUDE;

    public static AptitudeArgument getArgument() {
        return new AptitudeArgument();
    }

    public static ResourceLocation getAptitude(CommandContext<CommandSourceStack> context, String name) throws CommandSyntaxException {
        return getResource(context.getArgument(name, ResourceLocation.class));
    }


    public ResourceLocation parse(StringReader reader) throws CommandSyntaxException {
        return getResource(ResourceLocation.read(reader));
    }


    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder) {
        RegistryAptitudes.APTITUDES_REGISTRY.get().getValues().forEach(conduit -> builder.suggest(Objects.requireNonNull(RegistryAptitudes.APTITUDES_REGISTRY.get().getKey(conduit)).toString()));
        return builder.buildFuture();
    }

    public static ResourceLocation getResource(ResourceLocation registryName) throws CommandSyntaxException {
        if (RegistryAptitudes.APTITUDES_REGISTRY.get().containsKey(registryName)) {
            return registryName;
        }
        throw ERROR_UNKNOWN_APTITUDE.create(registryName);
    }


    public Collection<String> getExamples() {
        return EXAMPLES;
    }
}


