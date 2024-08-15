package com.seniors.justlevelingfork.common.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.seniors.justlevelingfork.handler.HandlerCommonConfig;
import com.seniors.justlevelingfork.network.packet.client.DynamicConfigSyncCP;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

public class GlobalLimitCommand {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register((
                Commands.literal("globallimit")
                        .requires((source) -> source.hasPermission(2))
                        .then(Commands.argument("level", IntegerArgumentType.integer())
                                .executes(GlobalLimitCommand::execute)
                        )
        ));
    }

    private static int execute(CommandContext<CommandSourceStack> command) {
        int globalLimitLevel = command.getArgument("level", Integer.class);

        if(command.getSource().getEntity() != null
                && command.getSource().getEntity() instanceof Player){
            command.getSource().sendSystemMessage(Component.literal("This command can't be called client side!"));
            return Command.SINGLE_SUCCESS;
        }

        HandlerCommonConfig.HANDLER.instance().playersMaxGlobalLevel = globalLimitLevel;
        HandlerCommonConfig.HANDLER.save();

        DynamicConfigSyncCP.sendToAllPlayers();
        command.getSource().sendSystemMessage(Component.literal(String.format("Updating playersMaxGlobalLevel, new level: %d", globalLimitLevel)));

        return Command.SINGLE_SUCCESS;
    }

}
