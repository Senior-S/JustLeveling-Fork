package com.seniors.justlevelingfork.registry;

import com.seniors.justlevelingfork.handler.HandlerTrinkets;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.item.ItemStack;

public class RegistryFabricEvents {
    public static void load() {
        UseItemCallback.EVENT.register((player, world, hand) -> {
            ItemStack stack = player.getItemInHand(hand);
            return RegistryGameplayEvents.canUseItem(player, stack)
                    ? InteractionResultHolder.pass(stack)
                    : InteractionResultHolder.fail(stack);
        });

        UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {
            ItemStack stack = player.getItemInHand(hand);
            return RegistryGameplayEvents.canUseItem(player, stack)
                    && RegistryGameplayEvents.canUseBlock(player, world.getBlockState(hitResult.getBlockPos()).getBlock())
                    ? InteractionResult.PASS
                    : InteractionResult.FAIL;
        });

        UseEntityCallback.EVENT.register((player, world, hand, entity, hitResult) ->
                RegistryGameplayEvents.canUseEntity(player, entity)
                        && RegistryGameplayEvents.canUseItem(player, player.getItemInHand(hand))
                        ? InteractionResult.PASS
                        : InteractionResult.FAIL);

        ServerLivingEntityEvents.AFTER_DEATH.register(RegistryGameplayEvents::handleLivingDeath);

        AttackEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
            if (!RegistryGameplayEvents.canUseItem(player, player.getItemInHand(hand))) {
                return InteractionResult.FAIL;
            }
            RegistryGameplayEvents.triggerLimitBreaker(player, entity);
            RegistryGameplayEvents.consumeCounterAttack(player);
            return InteractionResult.PASS;
        });

        PlayerBlockBreakEvents.BEFORE.register((world, player, pos, state, blockEntity) ->
                RegistryGameplayEvents.canUseItem(player, player.getMainHandItem())
                        && RegistryGameplayEvents.canUseBlock(player, state.getBlock()));
        PlayerBlockBreakEvents.AFTER.register((world, player, pos, state, blockEntity) ->
                RegistryGameplayEvents.dropTreasureHunterItem(world, player, pos.getX(), pos.getY(), pos.getZ(), state));

        HandlerTrinkets.load();

        ServerTickEvents.END_SERVER_TICK.register(server -> server.getPlayerList().getPlayers().forEach(player -> {
            RegistryGameplayEvents.dropLockedEquippedItems(player);
            HandlerTrinkets.dropLockedTrinkets(player);
            RegistryGameplayEvents.updatePlayerPassives(player);
            RegistryTitles.syncTitles(player);
        }));
    }
}
