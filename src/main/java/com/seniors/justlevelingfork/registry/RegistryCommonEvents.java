package com.seniors.justlevelingfork.registry;

import com.seniors.justlevelingfork.JustLevelingFork;
import com.seniors.justlevelingfork.common.capability.AptitudeCapability;
import com.seniors.justlevelingfork.common.capability.LazyAptitudeCapability;
import com.seniors.justlevelingfork.common.command.AptitudeLevelCommand;
import com.seniors.justlevelingfork.common.command.AptitudesReloadCommand;
import com.seniors.justlevelingfork.common.command.TitleCommand;
import com.seniors.justlevelingfork.handler.HandlerCommonConfig;
import com.seniors.justlevelingfork.integration.TetraIntegration;
import com.seniors.justlevelingfork.network.packet.client.*;
import com.seniors.justlevelingfork.network.packet.common.CounterAttackSP;
import com.seniors.justlevelingfork.registry.skills.ConvergenceSkill;
import com.seniors.justlevelingfork.registry.skills.TreasureHunterSkill;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.TickTask;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.EntityTeleportEvent;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.CriticalHitEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.loading.FMLPaths;
import net.minecraftforge.registries.ForgeRegistries;

import java.io.File;
import java.util.*;

/**
 * Registry COMMON events into the forge mod bus.
 */
@Mod.EventBusSubscriber(modid = JustLevelingFork.MOD_ID)
public class RegistryCommonEvents {

    @SubscribeEvent
    public void onPlayerLoggedInEvent(PlayerEvent.PlayerLoggedInEvent event) {
        Player player = event.getEntity();
        JustLevelingFork.getLOGGER().info("Player logged in");
        if (!player.level().isClientSide()) {
            if (player instanceof ServerPlayer serverPlayer) {
                ConfigSyncCP.sendToPlayer(serverPlayer);
                //TitlesSyncCP.sendToPlayer(serverPlayer);
                CommonConfigSyncCP.sendToPlayer(serverPlayer);
                DynamicConfigSyncCP.sendToPlayer(serverPlayer);
            }
        }
    }

    @SubscribeEvent
    public void onServerStarting(final ServerStartingEvent event) { // Let's migrate the config on server start to it runs on client and server.
        File oldConfigFile = FMLPaths.CONFIGDIR.get().resolve("just_leveling-common.toml").toFile();
        if (!HandlerCommonConfig.HANDLER.instance().usingNewConfig && oldConfigFile.exists()) {
            JustLevelingFork.getLOGGER().info("Configuration not migrated yet, starting migration...");
            JustLevelingFork.migrateOldConfig();
        } else if (!HandlerCommonConfig.HANDLER.instance().usingNewConfig && !oldConfigFile.exists()) {
            HandlerCommonConfig.HANDLER.instance().usingNewConfig = true;
            HandlerCommonConfig.HANDLER.save();
        }
    }

    @SubscribeEvent
    public static void onRegisterCommands(RegisterCommandsEvent event) {
        AptitudeLevelCommand.register(event.getDispatcher());
        TitleCommand.register(event.getDispatcher());
        AptitudesReloadCommand.register(event.getDispatcher());
    }

    @SubscribeEvent
    public static void onAttachCapabilitiesPlayer(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Player) {
            AptitudeCapability AptitudeCapability = new AptitudeCapability();
            LazyAptitudeCapability lazyAptitudeCapability = new LazyAptitudeCapability(AptitudeCapability);
            event.addCapability(new ResourceLocation(JustLevelingFork.MOD_ID, "aptitudes"), lazyAptitudeCapability);
        }
    }

    @SubscribeEvent
    public static void onRegisterCapabilities(RegisterCapabilitiesEvent event) {
        event.register(AptitudeCapability.class);
    }

    @SubscribeEvent
    public void onPlayerClone(PlayerEvent.Clone event) {
        Player player = event.getEntity();
        if (player instanceof ServerPlayer serverPlayerNew) {
            player = event.getOriginal();
            if (player instanceof ServerPlayer serverPlayerOld) {
                serverPlayerOld.reviveCaps();
                serverPlayerOld.getCapability(RegistryCapabilities.APTITUDE).ifPresent((oldAbilities) -> {
                    serverPlayerNew.getCapability(RegistryCapabilities.APTITUDE).ifPresent((newAbilities) -> {
                        newAbilities.copyFrom(oldAbilities);
                    });
                });
                RegistryAttributes.modifierAttributes(serverPlayerNew);
                RegistryTitles.syncTitles(serverPlayerNew);
                if (!serverPlayerOld.isDeadOrDying()) {
                    serverPlayerNew.setHealth(serverPlayerOld.getHealth());
                } else {
                    serverPlayerNew.setHealth(serverPlayerOld.getMaxHealth());
                }
                RegistryAttributes.modifierAttributes(serverPlayerOld);
                RegistryTitles.syncTitles(serverPlayerOld);
                serverPlayerOld.invalidateCaps();
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerJoinWorld(EntityJoinLevelEvent event) {
        if (!event.getLevel().isClientSide()) {
            Entity entity = event.getEntity();
            if (entity instanceof ServerPlayer serverPlayer) {
                SyncAptitudeCapabilityCP.send(serverPlayer);
                RegistryAttributes.modifierAttributes(serverPlayer);
                RegistryTitles.syncTitles(serverPlayer);

                // If there's any update notify OP players about this.
                if (HandlerCommonConfig.HANDLER.instance().checkForUpdates
                        && JustLevelingFork.UpdatesAvailable.left) {
                    if (serverPlayer.hasPermissions(2)) {
                        Component component = Component.literal(String.format("[JustLevelingFork] Version %s is available, it's recommended to update!", JustLevelingFork.UpdatesAvailable.right));
                        serverPlayer.sendSystemMessage(component);
                    }
                }
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onLeftClickBlock(PlayerInteractEvent.LeftClickBlock event) {
        Player player = event.getEntity();
        if (player.isCreative()) return;
        ItemStack item = event.getItemStack();
        Block block = event.getLevel().getBlockState(event.getPos()).getBlock();
        AptitudeCapability provider = AptitudeCapability.get(player);

        ResourceLocation location = Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(item.getItem()));
        if (ModList.get().isLoaded("tetra") && TetraIntegration.TetraItems.contains(location.toString())) {
            List<String> extractedTypes = TetraIntegration.GetItemTypes(item);
            if (!extractedTypes.isEmpty()) {
                for (String tetraItem : extractedTypes) {
                    if (!provider.canUseSpecificID(player, tetraItem)) {
                        event.setCanceled(true);
                        return;
                    }
                }
            }
        }

        if ((!provider.canUseItem(player, location) || !provider.canUseBlock(player, block))) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        Player player = event.getEntity();
        if (player.isCreative()) return;
        ItemStack item = event.getItemStack();
        Block block = event.getLevel().getBlockState(event.getPos()).getBlock();
        AptitudeCapability provider = AptitudeCapability.get(player);

        ResourceLocation location = Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(item.getItem()));
        if (ModList.get().isLoaded("tetra") && TetraIntegration.TetraItems.contains(location.toString())) {
            List<String> extractedTypes = TetraIntegration.GetItemTypes(item);
            if (!extractedTypes.isEmpty()) {
                for (String tetraItem : extractedTypes) {
                    if (!provider.canUseSpecificID(player, tetraItem)) {
                        event.setCanceled(true);
                        return;
                    }
                }
            }
        }

        if ((!provider.canUseItem(player, location) || !provider.canUseBlock(player, block))) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onRightClickItem(PlayerInteractEvent.RightClickItem event) {
        Player player = event.getEntity();
        if (player.isCreative()) return;
        ItemStack item = event.getItemStack();
        AptitudeCapability provider = AptitudeCapability.get(player);

        ResourceLocation location = Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(item.getItem()));
        if (ModList.get().isLoaded("tetra") && TetraIntegration.TetraItems.contains(location.toString())) {
            List<String> extractedTypes = TetraIntegration.GetItemTypes(item);
            if (!extractedTypes.isEmpty()) {
                for (String tetraItem : extractedTypes) {
                    if (!provider.canUseSpecificID(player, tetraItem)) {
                        event.setCanceled(true);
                        return;
                    }
                }
            }
        }

        if (!provider.canUseItem(player, location)) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onRightClickEntity(PlayerInteractEvent.EntityInteract event) {
        Player player = event.getEntity();
        if (player.isCreative()) return;
        Entity entity = event.getTarget();
        ItemStack item = event.getItemStack();
        AptitudeCapability provider = AptitudeCapability.get(player);

        ResourceLocation location = Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(item.getItem()));
        if (ModList.get().isLoaded("tetra") && TetraIntegration.TetraItems.contains(location.toString())) {
            List<String> extractedTypes = TetraIntegration.GetItemTypes(item);
            if (!extractedTypes.isEmpty()) {
                for (String tetraItem : extractedTypes) {
                    if (!provider.canUseSpecificID(player, tetraItem)) {
                        event.setCanceled(true);
                        return;
                    }
                }
            }
        }

        if (!provider.canUseEntity(player, entity) || !provider.canUseItem(player, location)) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onChangeEquipment(LivingEquipmentChangeEvent event) {
        LivingEntity livingEntity = event.getEntity();
        if (livingEntity instanceof Player player) {
            if (!player.isCreative() && event.getSlot().getType() == EquipmentSlot.Type.ARMOR) {
                AptitudeCapability provider = AptitudeCapability.get(player);
                ItemStack item = event.getTo();

                if (!provider.canUseItem(player, item)) {
                    player.drop(item.copy(), false);
                    item.setCount(0);
                }
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        Player player = event.player;
        if (!player.isCreative()) {
            if (HandlerCommonConfig.HANDLER.instance().dropLockedItems) {
                player.getCapability(RegistryCapabilities.APTITUDE).ifPresent(aptitudeCapability -> {
                    AptitudeCapability provider = AptitudeCapability.get(player);

                    ItemStack hand = player.getMainHandItem();
                    ItemStack offHand = player.getOffhandItem();
                    if (!provider.canUseItem(player, hand)) {
                        player.drop(hand.copy(), false);
                        hand.setCount(0);
                    }
                    if (!provider.canUseItem(player, offHand)) {
                        player.drop(offHand.copy(), false);
                        offHand.setCount(0);
                    }
                });
            }
        }
        if (player instanceof ServerPlayer serverPlayer) {
            serverPlayer.getCapability(RegistryCapabilities.APTITUDE).ifPresent(aptitudeCapability -> {
                AptitudeCapability provider = AptitudeCapability.get(serverPlayer);

                if (provider.getCounterAttack()) {
                    provider.setCounterAttackTimer(provider.getCounterAttackTimer() + 1);
                    if (provider.getCounterAttackTimer() >= RegistrySkills.COUNTER_ATTACK.get().getValue()[0] * 40.0D) {
                        CounterAttackSP.send(false, 0.0F);
                    }
                }
            });
            new RegistryAttributes.registerAttribute(serverPlayer, Attributes.ATTACK_DAMAGE, (float) RegistrySkills.ONE_HANDED.get().getValue()[0], UUID.fromString("55550aa2-eff2-4a81-b92b-a1cb95f15555")).amplifyAttribute((serverPlayer.getOffhandItem().getCount() == 0 && RegistrySkills.ONE_HANDED.get().isEnabled(serverPlayer)));
            new RegistryAttributes.registerAttribute(serverPlayer, Attributes.ARMOR, (float) RegistrySkills.DIAMOND_SKIN.get().getValue()[1], UUID.fromString("55550aa2-eff2-4a81-b92b-a1cb95f15556")).amplifyAttribute((serverPlayer.isShiftKeyDown() && RegistrySkills.DIAMOND_SKIN.get().isEnabled(serverPlayer)));

            RegistryAttributes.modifierAttributes(serverPlayer);
            if (serverPlayer.getHealth() > serverPlayer.getMaxHealth())
                serverPlayer.setHealth(serverPlayer.getMaxHealth());

            new RegistryEffects.addEffect(serverPlayer, RegistrySkills.CAT_EYES.get().isEnabled(player), MobEffects.NIGHT_VISION).add(210);
            new RegistryEffects.addEffect(serverPlayer, RegistrySkills.DIAMOND_SKIN.get().isEnabled(player), MobEffects.DAMAGE_RESISTANCE).add(210, (int) (RegistrySkills.DIAMOND_SKIN.get().getValue()[0] - 1.0D));
        }

    }

    @SubscribeEvent
    public void onPlayerTickLow(TickEvent.PlayerTickEvent event) {
        if (event.side == LogicalSide.SERVER) {
            Player player = event.player;
            if (player instanceof ServerPlayer serverPlayer) {
                RegistryTitles.syncTitles(serverPlayer);
            }

        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onPlayerAttackEntity(AttackEntityEvent event) {
        Entity target = event.getTarget();
        Player player = event.getEntity();
        if (player != null) {
            if (!player.isCreative()) {
                AptitudeCapability provider = AptitudeCapability.get(player);
                ItemStack item = player.getMainHandItem();

                if (!provider.canUseItem(player, item)) {
                    event.setCanceled(true);
                }
            }

            int random = (int) Math.floor(Math.random() * RegistrySkills.LIMIT_BREAKER.get().getValue()[0]);

            if (RegistrySkills.LIMIT_BREAKER.get().isEnabled(player)) {
                Level level = event.getEntity().level();
                if (level instanceof ServerLevel serverLevel) {
                    if (random == 1) {
                        target.hurt(target.damageSources().playerAttack(player), (float) RegistrySkills.LIMIT_BREAKER.get().getValue()[1]);
                        serverLevel.playSound(null, player, RegistrySounds.LIMIT_BREAKER.get(), SoundSource.PLAYERS, 0.5F, 1.0F);
                    }
                }

            }
            player.getCapability(RegistryCapabilities.APTITUDE).ifPresent(capability -> {
                AptitudeCapability aptitudeCapability = AptitudeCapability.get(player);
                if (aptitudeCapability.getCounterAttack()) {
                    CounterAttackSP.send(false, 0.0F);
                }
            });
        }
    }

    @SubscribeEvent
    public static void onPlayerMining(PlayerEvent.BreakSpeed event) {
        Player player = event.getEntity();
        float modifier = event.getOriginalSpeed() * (1.0F + (float) player.getAttributeValue(RegistryAttributes.BREAK_SPEED.get()));
        if (player.getMainHandItem().is(itemHolder -> itemHolder.get() instanceof net.minecraft.world.item.PickaxeItem)) {
            if (event.getState().is(RegistryTags.Blocks.OBSIDIAN)) {
                if (RegistrySkills.OBSIDIAN_SMASHER.get().isEnabled(player)) {
                    event.setNewSpeed((float) (event.getOriginalSpeed() * RegistrySkills.OBSIDIAN_SMASHER.get().getValue()[0]) + modifier);
                } else {
                    event.setNewSpeed(event.getOriginalSpeed());
                }
            } else {
                event.setNewSpeed(event.getOriginalSpeed() + modifier);
            }
        }
        if (player.getMainHandItem().is(itemHolder -> itemHolder.get() instanceof net.minecraft.world.item.ShovelItem))
            event.setNewSpeed(event.getOriginalSpeed() + modifier);
        if (player.getMainHandItem().is(itemHolder -> itemHolder.get() instanceof net.minecraft.world.item.AxeItem))
            event.setNewSpeed(event.getOriginalSpeed() + modifier);
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onPlayerCriticalHit(CriticalHitEvent event) {
        Player player = event.getEntity();
        if (player != null) {
            float damage = event.getDamageModifier();
            float attribute = (float) event.getEntity().getAttributeValue(RegistryAttributes.CRITICAL_DAMAGE.get());
            event.setDamageModifier(damage + attribute);

            if (RegistrySkills.BERSERKER.get().isEnabled(player) && player.getHealth() <= player.getMaxHealth() * (float) (RegistrySkills.BERSERKER.get().getValue()[0] / 100.0D)) {
                float newDamage = event.getDamageModifier();
                if (player.onGround() || player.isInWater()) {
                    event.setResult(Event.Result.ALLOW);
                    event.setDamageModifier(newDamage * 1.5F);
                }
            }

            if (player instanceof ServerPlayer serverPlayer) {
                if (RegistrySkills.CRITICAL_ROLL.get().isEnabled(serverPlayer) && (event.isVanillaCritical() || (RegistrySkills.BERSERKER.get().isEnabled(player) && player.getHealth() <= player.getMaxHealth() * (float) (RegistrySkills.BERSERKER.get().getValue()[0] / 100.0D)))) {
                    float newDamage = event.getDamageModifier();
                    int dice = (int) Math.floor(Math.random() * 7.0D);
                    if (dice == 1) {
                        PlayerMessagesCP.send(serverPlayer, "overlay.skill.justlevelingfork.critical_roll_1", 0);
                        event.setDamageModifier(newDamage / (1.0F + 1.0F / (float) RegistrySkills.CRITICAL_ROLL.get().getValue()[1]));
                    }
                    if (dice == 6) {
                        PlayerMessagesCP.send(serverPlayer, "overlay.skill.justlevelingfork.critical_roll_6", 0);
                        event.setDamageModifier(newDamage * (float) RegistrySkills.CRITICAL_ROLL.get().getValue()[0]);
                    }
                }
            }

        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onAttackEntity(LivingHurtEvent event) {
        if (event.getSource() != null) {
            Entity source = event.getSource().getEntity();
            if (source instanceof LivingEntity livingEntity) {
                if (livingEntity.getAttribute(Attributes.ATTACK_DAMAGE) != null) {
                    float sourceDamage = (float) livingEntity.getAttributeValue(Attributes.ATTACK_DAMAGE);
                    LivingEntity livingEntity1 = event.getEntity();
                    if (livingEntity1 instanceof ServerPlayer player) {
                        if (RegistrySkills.COUNTER_ATTACK.get().isEnabled(player)) {
                            player.getCapability(RegistryCapabilities.APTITUDE).ifPresent(aptitudeCapability -> {
                                CounterAttackSP.sendToPlayer(true, (float) (sourceDamage * RegistrySkills.COUNTER_ATTACK.get().getValue()[1] / 100.0D), player);
                            });
                        }
                    }

                }
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onPlayerShootArrow(ProjectileImpactEvent event) {
        Projectile projectile = event.getProjectile();
        if (projectile instanceof Arrow arrow) {
            Entity entity = event.getProjectile().getOwner();
            if (entity instanceof Player player) {
                double baseDamage = arrow.getBaseDamage();
                double arrowDamage = baseDamage + player.getAttributeValue(RegistryAttributes.PROJECTILE_DAMAGE.get()) / 5.0D;
                arrow.setBaseDamage(arrowDamage);
                if (RegistrySkills.STEALTH_MASTERY.get().isEnabled(player) && player.isShiftKeyDown())
                    arrow.setBaseDamage(arrowDamage + baseDamage * (RegistrySkills.STEALTH_MASTERY.get().getValue()[2] - 1.0D));
            }

            entity = event.getProjectile().getOwner();
            if (entity instanceof ServerPlayer serverPlayer) {
                if (event.getRayTraceResult().getType() == HitResult.Type.ENTITY)
                    (new RegistryEffects.addEffect(serverPlayer, RegistrySkills.QUICK_REPOSITION.get().isEnabled(serverPlayer), MobEffects.MOVEMENT_SPEED)).add((int) (10.0D + 20.0D * RegistrySkills.QUICK_REPOSITION.get().getValue()[1]), (int) (RegistrySkills.QUICK_REPOSITION.get().getValue()[0] - 1.0D));
            }
        }

    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onPlayerTeleport(EntityTeleportEvent.EnderPearl event) {
        if (event.getEntity() != null) {
            Entity entity = event.getEntity();
            if (entity instanceof Player player) {
                if (RegistrySkills.SAFE_PORT.get().isEnabled(player)) event.setAttackDamage(0.0F);
            }
        }

    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onPlayerBreakBlock(BlockEvent.BreakEvent event) {
        Player player = event.getPlayer();
        if (player != null &&
                event.getState().is(RegistryTags.Blocks.DIRT) && RegistrySkills.TREASURE_HUNTER.get().isEnabled(player)) {
            Level level = player.level();
            BlockPos pos = event.getPos();
            ItemStack stack = TreasureHunterSkill.drop();
            if (stack != null) {
                ItemEntity itemEntity = new ItemEntity(level, pos.getX(), pos.getY(), pos.getZ(), stack);
                enqueueTask(level, () -> level.addFreshEntity(itemEntity), 0);
            }
        }
    }


    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onPlayerCraft(PlayerEvent.ItemCraftedEvent event) {
        Player player = event.getEntity();
        if (player != null) {
            int randomizer = (int) Math.floor(Math.random() * RegistrySkills.CONVERGENCE.get().getValue()[0]);
            if (RegistrySkills.CONVERGENCE.get().isEnabled(player) && randomizer == 1) {
                ItemStack convergenceItem = ConvergenceSkill.drop(event.getCrafting());
                if (convergenceItem != null) {
                    player.drop(convergenceItem, false);
                }
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onEntityDrops(LivingDropsEvent event) {
        if (event.getEntity() != null) {
            if (!(event.getEntity() instanceof Player)) {
                Entity entity1 = event.getSource().getEntity();
                if (entity1 instanceof Player player) {
                    new RegistryEffects.addEffect((ServerPlayer) player, RegistrySkills.FIGHTING_SPIRIT.get().isEnabled(player), MobEffects.DAMAGE_BOOST).add((int) (10.0D + 20.0D * RegistrySkills.FIGHTING_SPIRIT.get().getValue()[1]), (int) (RegistrySkills.FIGHTING_SPIRIT.get().getValue()[0] - 1.0D));
                }
            }

            Entity entity = event.getSource().getEntity();
            if (entity instanceof Player) {
                Player player = (Player) entity;
                if (RegistrySkills.LIFE_EATER.get().isEnabled(player)) {
                    player.heal((float) RegistrySkills.LIFE_EATER.get().getValue()[0]);
                }
            }

            if (!(event.getEntity() instanceof Player)) {
                entity = event.getSource().getEntity();
                if (entity instanceof Player player) {
                    if (RegistrySkills.LUCKY_DROP.get().isEnabled(player)) {
                        int random = (int) Math.floor(Math.random() * RegistrySkills.LUCKY_DROP.get().getValue()[0]);
                        if (random == 0) {
                            List<ItemStack> equipment = new ArrayList<>();

                            for (ItemStack next : event.getEntity().getAllSlots()) {
                                equipment.add(next);
                            }

                            BlockPos pos = event.getEntity().blockPosition();
                            enqueueTask(event.getEntity().level(), () -> {
                                List<ItemEntity> dropEntities = new ArrayList<>();
                                Iterator<Entity> var5 = event.getEntity().level().getEntities(null, new AABB((pos.getX() - 1), (pos.getY() - 1), (pos.getZ() - 1), (pos.getX() + 1), (pos.getY() + 1), (pos.getZ() + 1))).iterator();
                                while (var5.hasNext()) {
                                    Entity ea = var5.next();
                                    if (ea instanceof ItemEntity) dropEntities.add((ItemEntity) ea);
                                }
                                var5 = (Iterator) dropEntities.iterator();
                                while (var5.hasNext()) {
                                    ItemEntity dropEntity = (ItemEntity) var5.next();
                                    int tickCount = dropEntity.tickCount;
                                    if (tickCount <= 1) {
                                        ItemStack itemStack = dropEntity.getItem();
                                        if (!equipment.contains(itemStack)) {
                                            if (itemStack.getMaxStackSize() > 1)
                                                itemStack.setCount(itemStack.getCount() * (int) RegistrySkills.LUCKY_DROP.get().getValue()[1]);
                                            PlayerMessagesCP.send(player, "overlay.skill.justlevelingfork.lucky_drop", (int) RegistrySkills.LUCKY_DROP.get().getValue()[1]);
                                            dropEntity.setItem(itemStack);
                                        }
                                    }
                                }
                            }, 0);
                        }
                    }
                }
            }

        }
    }

    public static void enqueueTask(Level world, Runnable task, int delay) {
        if (!(world instanceof ServerLevel)) {
            return;
        }

        MinecraftServer server = ((ServerLevel) world).getServer();
        server.submit(new TickTask(server.getTickCount() + delay, task));
    }
}