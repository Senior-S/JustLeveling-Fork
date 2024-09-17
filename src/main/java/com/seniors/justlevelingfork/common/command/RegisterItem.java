package com.seniors.justlevelingfork.common.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.seniors.justlevelingfork.common.command.arguments.AptitudeArgument;
import com.seniors.justlevelingfork.config.models.LockItem;
import com.seniors.justlevelingfork.handler.HandlerLockItemsConfig;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;

public class RegisterItem {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register((Commands.literal("registeritem")
                .requires((source) -> source.hasPermission(2)))
                .then(Commands.argument("aptitude", AptitudeArgument.getArgument())
                        .then(Commands.argument("level", IntegerArgumentType.integer())
                                .executes(RegisterItem::execute))
                )
        );
    }

    private static int execute(CommandContext<CommandSourceStack> command) throws CommandSyntaxException {
        if (command.getSource().getEntity() instanceof Player player) {
            ItemStack stack = player.getMainHandItem();
            if (stack == ItemStack.EMPTY || stack.isEmpty()) {
                player.sendSystemMessage(Component.literal("No item detected in main hand!"));
                return Command.SINGLE_SUCCESS;
            }
            ResourceLocation location = Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(stack.getItem()));
            String aptitudeName = command.getArgument("aptitude", String.class);
            Integer level = command.getArgument("level", Integer.class);

            Optional<LockItem> optionalLockItem = HandlerLockItemsConfig.HANDLER.instance().lockItemList.stream().filter(c -> c.Item.equalsIgnoreCase(location.toString())).findFirst();
            if (optionalLockItem.isPresent()) {
                LockItem lockItem = optionalLockItem.get();
                int index = HandlerLockItemsConfig.HANDLER.instance().lockItemList.indexOf(lockItem);
                if (level < 1) {
                    if (lockItem.Aptitudes.size() <= 1) {
                        HandlerLockItemsConfig.HANDLER.instance().lockItemList.remove(index);
                        player.sendSystemMessage(Component.literal("Removing item from lockItemList..."));
                    }
                    else {
                        Optional<LockItem.Aptitude> aptitude = lockItem.Aptitudes.stream().filter(c -> c.Aptitude.toString().equalsIgnoreCase(aptitudeName)).findFirst();
                        aptitude.ifPresent(value -> lockItem.Aptitudes.remove(value));

                        HandlerLockItemsConfig.HANDLER.instance().lockItemList.set(index, lockItem);
                        player.sendSystemMessage(Component.literal("Removing aptitude from item..."));
                    }

                    return Command.SINGLE_SUCCESS;
                }

                lockItem.Aptitudes.stream().filter(c -> c.Aptitude.toString().equalsIgnoreCase(aptitudeName)).findFirst().ifPresent(value -> lockItem.Aptitudes.remove(value));

                lockItem.Aptitudes.add(new LockItem.Aptitude(aptitudeName, level));

                HandlerLockItemsConfig.HANDLER.instance().lockItemList.set(index, lockItem);
                HandlerLockItemsConfig.HANDLER.save();

                player.sendSystemMessage(Component.literal("Item already in lockItemList, adding extra aptitude..."));
                return Command.SINGLE_SUCCESS;
            }

            LockItem lockItem = new LockItem(location.toString());
            lockItem.Aptitudes = new ArrayList<>();
            lockItem.Aptitudes.add(new LockItem.Aptitude(aptitudeName, level));

            HandlerLockItemsConfig.HANDLER.instance().lockItemList.add(lockItem);
            HandlerLockItemsConfig.HANDLER.save();

            player.sendSystemMessage(Component.literal("Item added into lockItemList..."));
        }

        return Command.SINGLE_SUCCESS;
    }
}
