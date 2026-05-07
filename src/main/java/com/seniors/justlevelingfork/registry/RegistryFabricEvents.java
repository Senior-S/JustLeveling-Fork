package com.seniors.justlevelingfork.registry;

import com.seniors.justlevelingfork.common.capability.AptitudeCapability;
import com.seniors.justlevelingfork.handler.HandlerAptitude;
import com.seniors.justlevelingfork.handler.HandlerCommonConfig;
import com.seniors.justlevelingfork.handler.HandlerTrinkets;
import com.seniors.justlevelingfork.network.packet.common.CounterAttackSP;
import com.seniors.justlevelingfork.network.packet.client.PlayerMessagesCP;
import com.seniors.justlevelingfork.network.packet.client.SyncAptitudeCapabilityCP;
import com.seniors.justlevelingfork.registry.skills.TreasureHunterSkill;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.BlockPos;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.TickTask;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.phys.AABB;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class RegistryFabricEvents {
    public static void load() {
        UseItemCallback.EVENT.register((player, world, hand) -> {
            ItemStack stack = player.getItemInHand(hand);
            return canUseItem(player, stack) ? InteractionResultHolder.pass(stack) : InteractionResultHolder.fail(stack);
        });
        UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {
            ItemStack stack = player.getItemInHand(hand);
            Block block = world.getBlockState(hitResult.getBlockPos()).getBlock();
            return canUseItem(player, stack) && canUseBlock(player, block) ? InteractionResult.PASS : InteractionResult.FAIL;
        });
        UseEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> canUseEntity(player, entity) && canUseItem(player, player.getItemInHand(hand)) ? InteractionResult.PASS : InteractionResult.FAIL);
        ServerLivingEntityEvents.AFTER_DEATH.register(RegistryFabricEvents::handleLivingDeath);
        AttackEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
            if (!canUseItem(player, player.getItemInHand(hand))) {
                return InteractionResult.FAIL;
            }
            triggerLimitBreaker(player, entity);
            consumeCounterAttack(player);
            return InteractionResult.PASS;
        });
        PlayerBlockBreakEvents.BEFORE.register((world, player, pos, state, blockEntity) -> canUseItem(player, player.getMainHandItem()) && canUseBlock(player, state.getBlock()));
        PlayerBlockBreakEvents.AFTER.register((world, player, pos, state, blockEntity) -> dropTreasureHunterItem(world, player, pos.getX(), pos.getY(), pos.getZ(), state));
        HandlerTrinkets.load();

        ServerTickEvents.END_SERVER_TICK.register(server -> server.getPlayerList().getPlayers().forEach(player -> {
            dropLockedEquippedItems(player);
            HandlerTrinkets.dropLockedTrinkets(player);
            updatePlayerPassives(player);
            RegistryTitles.syncTitles(player);
        }));
    }

    private static void handleLivingDeath(LivingEntity entity, DamageSource damageSource) {
        if (entity.level().isClientSide || entity instanceof Player || !(damageSource.getEntity() instanceof ServerPlayer player)) {
            return;
        }

        if (RegistrySkills.FIGHTING_SPIRIT != null) {
            new RegistryEffects.addEffect(player, RegistrySkills.FIGHTING_SPIRIT.get().isEnabled(player), MobEffects.DAMAGE_BOOST).add((int) (10.0D + 20.0D * RegistrySkills.FIGHTING_SPIRIT.get().getValue()[1]), (int) (RegistrySkills.FIGHTING_SPIRIT.get().getValue()[0] - 1.0D));
        }

        if (RegistrySkills.LIFE_EATER != null && RegistrySkills.LIFE_EATER.get().isEnabled(player)) {
            player.heal((float) RegistrySkills.LIFE_EATER.get().getValue()[0]);
        }

        if (RegistrySkills.LUCKY_DROP != null && RegistrySkills.LUCKY_DROP.get().isEnabled(player)) {
            int random = (int) Math.floor(Math.random() * RegistrySkills.LUCKY_DROP.get().getValue()[0]);
            if (random == 0) {
                List<ItemStack> equipment = new ArrayList<>();
                entity.getAllSlots().forEach(equipment::add);
                BlockPos pos = entity.blockPosition();
                enqueueTask(entity.level(), () -> multiplyRecentDrops(player, entity.level(), pos, equipment), 1);
            }
        }
    }

    private static void multiplyRecentDrops(ServerPlayer player, Level level, BlockPos pos, List<ItemStack> equipment) {
        List<ItemEntity> dropEntities = new ArrayList<>();
        for (Entity entity : level.getEntities(null, new AABB((pos.getX() - 1), (pos.getY() - 1), (pos.getZ() - 1), (pos.getX() + 1), (pos.getY() + 1), (pos.getZ() + 1)))) {
            if (entity instanceof ItemEntity itemEntity) {
                dropEntities.add(itemEntity);
            }
        }

        for (ItemEntity dropEntity : dropEntities) {
            if (dropEntity.tickCount <= 1) {
                ItemStack itemStack = dropEntity.getItem();
                if (!equipment.contains(itemStack) && itemStack.getMaxStackSize() > 1) {
                    itemStack.setCount(itemStack.getCount() * (int) RegistrySkills.LUCKY_DROP.get().getValue()[1]);
                    PlayerMessagesCP.send(player, "overlay.skill.justlevelingfork.lucky_drop", (int) RegistrySkills.LUCKY_DROP.get().getValue()[1]);
                    dropEntity.setItem(itemStack);
                }
            }
        }
    }

    private static void enqueueTask(Level world, Runnable task, int delay) {
        if (!(world instanceof ServerLevel serverLevel)) {
            return;
        }

        MinecraftServer server = serverLevel.getServer();
        server.submit(new TickTask(server.getTickCount() + delay, task));
    }

    private static void dropTreasureHunterItem(Level world, Player player, int x, int y, int z, BlockState state) {
        if (!(world instanceof ServerLevel) || player.isCreative() || player.isSpectator() || RegistrySkills.TREASURE_HUNTER == null) {
            return;
        }

        if (state.is(RegistryTags.Blocks.DIRT) && RegistrySkills.TREASURE_HUNTER.get().isEnabled(player)) {
            ItemStack stack = TreasureHunterSkill.drop();
            if (stack != null && !stack.isEmpty()) {
                world.addFreshEntity(new ItemEntity(world, x, y, z, stack));
            }
        }
    }

    private static void triggerLimitBreaker(Player player, Entity target) {
        if (player.level().isClientSide || player.isCreative() || RegistrySkills.LIMIT_BREAKER == null || !RegistrySkills.LIMIT_BREAKER.get().isEnabled(player)) {
            return;
        }

        int random = (int) Math.floor(Math.random() * RegistrySkills.LIMIT_BREAKER.get().getValue()[0]);
        if (random == 1) {
            target.hurt(target.damageSources().playerAttack(player), (float) RegistrySkills.LIMIT_BREAKER.get().getValue()[1]);
            player.level().playSound(null, player, RegistrySounds.LIMIT_BREAKER.get(), SoundSource.PLAYERS, 0.5F, 1.0F);
        }
    }

    private static void consumeCounterAttack(Player player) {
        if (player.level().isClientSide || !(player instanceof ServerPlayer serverPlayer)) {
            return;
        }

        AptitudeCapability provider = AptitudeCapability.get(serverPlayer);
        if (provider != null && provider.getCounterAttack()) {
            CounterAttackSP.apply(serverPlayer, false, 0.0F);
        }
    }

    private static void updatePlayerPassives(ServerPlayer player) {
        AptitudeCapability provider = AptitudeCapability.get(player);
        if (provider == null) {
            return;
        }

        updateCounterAttackTimer(player, provider);

        if (RegistrySkills.ONE_HANDED != null) {
            boolean enabled = player.getOffhandItem().isEmpty() && RegistrySkills.ONE_HANDED.get().isEnabled(player);
            new RegistryAttributes.registerAttribute(player, Attributes.ATTACK_DAMAGE, (float) RegistrySkills.ONE_HANDED.get().getValue()[0], UUID.fromString("55550aa2-eff2-4a81-b92b-a1cb95f15555")).amplifyAttribute(enabled);
        }
        if (RegistrySkills.DIAMOND_SKIN != null) {
            boolean enabled = player.isShiftKeyDown() && RegistrySkills.DIAMOND_SKIN.get().isEnabled(player);
            new RegistryAttributes.registerAttribute(player, Attributes.ARMOR, (float) RegistrySkills.DIAMOND_SKIN.get().getValue()[1], UUID.fromString("55550aa2-eff2-4a81-b92b-a1cb95f15556")).amplifyAttribute(enabled);
        }

        RegistryAttributes.modifierAttributes(player);
        if (player.getHealth() > player.getMaxHealth()) {
            player.setHealth(player.getMaxHealth());
        }

        new RegistryEffects.addEffect(player, RegistrySkills.CAT_EYES != null && RegistrySkills.CAT_EYES.get().isEnabled(player), MobEffects.NIGHT_VISION).add(210);
        new RegistryEffects.addEffect(player, RegistrySkills.DIAMOND_SKIN != null && RegistrySkills.DIAMOND_SKIN.get().isEnabled(player), MobEffects.DAMAGE_RESISTANCE).add(210, (int) (RegistrySkills.DIAMOND_SKIN.get().getValue()[0] - 1.0D));
    }

    private static void updateCounterAttackTimer(ServerPlayer player, AptitudeCapability provider) {
        if (!provider.getCounterAttack()) {
            return;
        }

        provider.setCounterAttackTimer(provider.getCounterAttackTimer() + 1);
        if (RegistrySkills.COUNTER_ATTACK != null && provider.getCounterAttackTimer() >= RegistrySkills.COUNTER_ATTACK.get().getValue()[0] * 40.0D) {
            provider.setCounterAttack(false);
            provider.setCounterAttackTimer(0);
            new RegistryAttributes.registerAttribute(player, Attributes.ATTACK_DAMAGE, 0.0D, UUID.fromString("55550aa2-eff2-4a81-b92b-a1cb95f15590")).amplifyAttribute(false);
            SyncAptitudeCapabilityCP.send(player);
        }
    }

    private static boolean canUseItem(Player player, ItemStack stack) {
        if (shouldSkipLockCheck(player) || stack.isEmpty()) {
            return true;
        }

        AptitudeCapability provider = AptitudeCapability.get(player);
        if (provider == null) {
            return true;
        }

        if (!provider.canUseItem(player, Objects.requireNonNull(BuiltInRegistries.ITEM.getKey(stack.getItem())))) {
            return false;
        }

        if (stack.getItem() instanceof ProjectileWeaponItem) {
            ItemStack projectile = player.getProjectile(stack);
            return projectile.isEmpty() || provider.canUseItem(player, projectile);
        }

        return true;
    }

    private static boolean canUseBlock(Player player, Block block) {
        if (shouldSkipLockCheck(player)) {
            return true;
        }

        AptitudeCapability provider = AptitudeCapability.get(player);
        return provider == null || provider.canUseBlock(player, block);
    }

    private static boolean canUseEntity(Player player, Entity entity) {
        if (shouldSkipLockCheck(player)) {
            return true;
        }

        AptitudeCapability provider = AptitudeCapability.get(player);
        return provider == null || provider.canUseEntity(player, entity);
    }

    private static void dropLockedEquippedItems(ServerPlayer player) {
        if (shouldSkipLockCheck(player)) {
            return;
        }

        dropIfLocked(player, player.getMainHandItem());
        dropIfLocked(player, player.getOffhandItem());
        player.getArmorSlots().forEach(stack -> dropIfLocked(player, stack));
    }

    private static void dropIfLocked(ServerPlayer player, ItemStack stack) {
        if (!stack.isEmpty() && shouldDropLockedItem(player, stack)) {
            player.drop(stack.copy(), false);
            stack.setCount(0);
        }
    }

    private static boolean shouldDropLockedItem(ServerPlayer player, ItemStack stack) {
        if (HandlerCommonConfig.HANDLER.instance().dropLockedItems) {
            return !canUseItem(player, stack);
        }

        AptitudeCapability provider = AptitudeCapability.get(player);
        if (provider == null || provider.canUseItem(player, stack)) {
            return false;
        }

        List<com.seniors.justlevelingfork.client.core.Aptitudes> aptitudes = HandlerAptitude.getValue(Objects.requireNonNull(BuiltInRegistries.ITEM.getKey(stack.getItem())).toString());
        return aptitudes != null && aptitudes.stream().anyMatch(com.seniors.justlevelingfork.client.core.Aptitudes::isDroppable);
    }

    private static boolean shouldSkipLockCheck(Player player) {
        return player == null || player.isCreative() || player.isSpectator();
    }
}
