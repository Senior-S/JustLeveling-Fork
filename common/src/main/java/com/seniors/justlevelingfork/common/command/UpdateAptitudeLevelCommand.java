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
    private static final int MIN_APTITUDE_LEVEL = 2;
    private static final int MAX_APTITUDE_LEVEL = 1000;

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register((
                Commands.literal("updateaptitudelevel")
                        .requires((source) -> source.hasPermission(2))
                        .then(Commands.argument("level", IntegerArgumentType.integer(MIN_APTITUDE_LEVEL, MAX_APTITUDE_LEVEL))
                                .executes(UpdateAptitudeLevelCommand::execute)
                        )

        ));
    }

    private static int execute(CommandContext<CommandSourceStack> command) {
        int levelLimit = command.getArgument("level", Integer.class);
        if (levelLimit < MIN_APTITUDE_LEVEL || levelLimit > MAX_APTITUDE_LEVEL) {
            command.getSource().sendFailure(Component.literal(String.format("Aptitude max level must be between %d and %d.", MIN_APTITUDE_LEVEL, MAX_APTITUDE_LEVEL)));
            return 0;
        }

        HandlerCommonConfig.HANDLER.instance().aptitudeMaxLevel = levelLimit;
        HandlerCommonConfig.HANDLER.save();

        DynamicConfigSyncCP.sendToAllPlayers();
        command.getSource().sendSystemMessage(Component.literal(String.format("Updating aptitudeMaxLevel, new level: %d", levelLimit)));

        return Command.SINGLE_SUCCESS;
    }
}
