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

public class UpdateAptitudeLevelCommand {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register((
                Commands.literal("updateaptitudelevel")
                        .requires((source) -> source.hasPermission(2))
                        .then(Commands.argument("level", IntegerArgumentType.integer())
                                .executes(UpdateAptitudeLevelCommand::execute)
                        )

        ));
    }

    private static int execute(CommandContext<CommandSourceStack> command) {
        int levelLimit = command.getArgument("level", Integer.class);

        HandlerCommonConfig.HANDLER.instance().aptitudeMaxLevel = levelLimit;
        HandlerCommonConfig.HANDLER.save();

        DynamicConfigSyncCP.sendToAllPlayers();
        command.getSource().sendSystemMessage(Component.literal(String.format("Updating aptitudeMaxLevel, new level: %d", levelLimit)));

        return Command.SINGLE_SUCCESS;
    }
}
