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

public class GlobalLimitCommand {
    private static final int MIN_GLOBAL_LEVEL = 32;
    private static final int MAX_GLOBAL_LEVEL = 99_999;

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register((
                Commands.literal("globallimit")
                        .requires((source) -> source.hasPermission(2))
                        .then(Commands.argument("level", IntegerArgumentType.integer(MIN_GLOBAL_LEVEL, MAX_GLOBAL_LEVEL))
                                .executes(GlobalLimitCommand::execute)
                        )
        ));
    }

    private static int execute(CommandContext<CommandSourceStack> command) {
        int globalLimitLevel = command.getArgument("level", Integer.class);
        if (globalLimitLevel < MIN_GLOBAL_LEVEL || globalLimitLevel > MAX_GLOBAL_LEVEL) {
            command.getSource().sendFailure(Component.literal(String.format("Global level limit must be between %d and %d.", MIN_GLOBAL_LEVEL, MAX_GLOBAL_LEVEL)));
            return 0;
        }

        HandlerCommonConfig.HANDLER.instance().playersMaxGlobalLevel = globalLimitLevel;
        HandlerCommonConfig.HANDLER.save();

        DynamicConfigSyncCP.sendToAllPlayers();
        command.getSource().sendSystemMessage(Component.literal(String.format("Updating playersMaxGlobalLevel, new level: %d", globalLimitLevel)));

        return Command.SINGLE_SUCCESS;
    }

}
