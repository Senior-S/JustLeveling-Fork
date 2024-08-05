package com.seniors.justlevelingfork.common.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.seniors.justlevelingfork.handler.HandlerAptitude;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

public class AptitudesReloadCommand {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register((Commands.literal("aptitudesreload").requires((source) -> {
            return source.hasPermission(2);
        })).executes(AptitudesReloadCommand::execute));
    }

    private static int execute(CommandContext<CommandSourceStack> command){
        HandlerAptitude.ForceRefresh();

        if(command.getSource().getEntity() instanceof Player player){
            player.sendSystemMessage(Component.literal("Forcing refresh of aptitudes..."));
        }


        return Command.SINGLE_SUCCESS;
    }
}
