package com.seniors.justlevelingfork.common.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.seniors.justlevelingfork.handler.HandlerAptitude;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.TextComponent;

public class AptitudesReloadCommand {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register((Commands.literal("aptitudesreload").requires((source) -> {
            return source.hasPermission(2);
        })).executes(AptitudesReloadCommand::execute));
    }

    private static int execute(CommandContext<CommandSourceStack> command){
        HandlerAptitude.ForceRefresh();

        command.getSource().sendSuccess(new TextComponent("Forcing refresh of aptitudes..."), true);

        return Command.SINGLE_SUCCESS;
    }
}
