package com.seniors.justlevelingfork.neoforge;

import com.seniors.justlevelingfork.common.command.AptitudeLevelCommand;
import com.seniors.justlevelingfork.common.command.AptitudesReloadCommand;
import com.seniors.justlevelingfork.common.command.GlobalLimitCommand;
import com.seniors.justlevelingfork.common.command.RegisterItem;
import com.seniors.justlevelingfork.common.command.TitleCommand;
import com.seniors.justlevelingfork.common.command.UpdateAptitudeLevelCommand;
import com.seniors.justlevelingfork.JustLevelingFork;
import com.seniors.justlevelingfork.integration.ftbquests.FTBQuestsIntegration;
import com.seniors.justlevelingfork.integration.questlog.QuestlogIntegration;
import com.seniors.justlevelingfork.network.ServerNetworking;
import com.seniors.justlevelingfork.network.packet.client.CommonConfigSyncCP;
import com.seniors.justlevelingfork.network.packet.client.ConfigSyncCP;
import com.seniors.justlevelingfork.network.packet.client.DynamicConfigSyncCP;
import com.seniors.justlevelingfork.network.packet.client.SyncAptitudeCapabilityCP;
import com.seniors.justlevelingfork.handler.HandlerTrinkets;
import com.seniors.justlevelingfork.registry.RegistryAttributes;
import com.seniors.justlevelingfork.registry.RegistryGameplayEvents;
import com.seniors.justlevelingfork.registry.RegistryTitles;
import dev.architectury.platform.Platform;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeModificationEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.player.AttackEntityEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.neoforged.neoforge.event.server.ServerStartedEvent;
import net.neoforged.neoforge.event.server.ServerStoppedEvent;
import net.neoforged.neoforge.event.tick.ServerTickEvent;

@Mod(JustLevelingFork.MOD_ID)
public class JustLevelingForkNeoForge {
    public JustLevelingForkNeoForge(IEventBus modBus) {
        JustLevelingFork.init();
        ServerNetworking.init();
        if (FMLEnvironment.dist.isClient()) {
            JustLevelingForkNeoForgeClient.init(modBus);
        }
        if (Platform.isModLoaded("ftbquests")) {
            FTBQuestsIntegration.load();
        }
        if (Platform.isModLoaded("questlog")) {
            QuestlogIntegration.load();
        }

        modBus.addListener(this::registerAttributes);
        NeoForge.EVENT_BUS.addListener(this::serverStarted);
        NeoForge.EVENT_BUS.addListener(this::serverStopped);
        NeoForge.EVENT_BUS.addListener(this::playerLoggedIn);
        NeoForge.EVENT_BUS.addListener(this::playerRespawned);
        NeoForge.EVENT_BUS.addListener(this::registerCommands);
        NeoForge.EVENT_BUS.addListener(this::livingDeath);
        NeoForge.EVENT_BUS.addListener(this::attackEntity);
        NeoForge.EVENT_BUS.addListener(this::rightClickItem);
        NeoForge.EVENT_BUS.addListener(this::rightClickBlock);
        NeoForge.EVENT_BUS.addListener(this::entityInteract);
        NeoForge.EVENT_BUS.addListener(this::breakBlock);
        NeoForge.EVENT_BUS.addListener(this::serverTick);
    }

    private void registerAttributes(EntityAttributeModificationEvent event) {
        event.add(EntityType.PLAYER, RegistryAttributes.BREAK_SPEED.get());
        event.add(EntityType.PLAYER, RegistryAttributes.CRITICAL_DAMAGE.get());
        event.add(EntityType.PLAYER, RegistryAttributes.PROJECTILE_DAMAGE.get());
        event.add(EntityType.PLAYER, RegistryAttributes.BENEFICIAL_EFFECT.get());
        event.add(EntityType.PLAYER, RegistryAttributes.MAGIC_RESIST.get());
    }

    private void serverStarted(ServerStartedEvent event) {
        JustLevelingFork.server = event.getServer();
    }

    private void serverStopped(ServerStoppedEvent event) {
        JustLevelingFork.server = null;
    }

    private void playerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getEntity() instanceof net.minecraft.server.level.ServerPlayer player) {
            ConfigSyncCP.sendToPlayer(player);
            CommonConfigSyncCP.sendToPlayer(player);
            DynamicConfigSyncCP.sendToPlayer(player);
            SyncAptitudeCapabilityCP.send(player);
        }
    }

    private void playerRespawned(PlayerEvent.PlayerRespawnEvent event) {
        if (event.getEntity() instanceof net.minecraft.server.level.ServerPlayer player) {
            player.server.execute(() -> SyncAptitudeCapabilityCP.send(player));
        }
    }

    private void registerCommands(RegisterCommandsEvent event) {
        AptitudeLevelCommand.register(event.getDispatcher());
        TitleCommand.register(event.getDispatcher());
        AptitudesReloadCommand.register(event.getDispatcher());
        RegisterItem.register(event.getDispatcher());
        GlobalLimitCommand.register(event.getDispatcher());
        UpdateAptitudeLevelCommand.register(event.getDispatcher());
    }

    private void livingDeath(LivingDeathEvent event) {
        RegistryGameplayEvents.handleLivingDeath(event.getEntity(), event.getSource());
    }

    private void attackEntity(AttackEntityEvent event) {
        if (!RegistryGameplayEvents.canUseItem(event.getEntity(), event.getEntity().getMainHandItem())) {
            event.setCanceled(true);
            return;
        }

        RegistryGameplayEvents.triggerLimitBreaker(event.getEntity(), event.getTarget());
        RegistryGameplayEvents.consumeCounterAttack(event.getEntity());
    }

    private void rightClickItem(PlayerInteractEvent.RightClickItem event) {
        if (!RegistryGameplayEvents.canUseItem(event.getEntity(), event.getItemStack())) {
            event.setCancellationResult(InteractionResult.FAIL);
            event.setCanceled(true);
        }
    }

    private void rightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        if (!RegistryGameplayEvents.canUseItem(event.getEntity(), event.getItemStack())
                || !RegistryGameplayEvents.canUseBlock(event.getEntity(), event.getLevel().getBlockState(event.getPos()).getBlock())) {
            event.setCancellationResult(InteractionResult.FAIL);
            event.setCanceled(true);
        }
    }

    private void entityInteract(PlayerInteractEvent.EntityInteract event) {
        if (!RegistryGameplayEvents.canUseEntity(event.getEntity(), event.getTarget())
                || !RegistryGameplayEvents.canUseItem(event.getEntity(), event.getItemStack())) {
            event.setCancellationResult(InteractionResult.FAIL);
            event.setCanceled(true);
        }
    }

    private void breakBlock(BlockEvent.BreakEvent event) {
        if (!RegistryGameplayEvents.canUseItem(event.getPlayer(), event.getPlayer().getMainHandItem())
                || !RegistryGameplayEvents.canUseBlock(event.getPlayer(), event.getState().getBlock())) {
            event.setCanceled(true);
            return;
        }

        RegistryGameplayEvents.dropTreasureHunterItem(
                event.getPlayer().level(),
                event.getPlayer(),
                event.getPos().getX(),
                event.getPos().getY(),
                event.getPos().getZ(),
                event.getState()
        );
    }

    private void serverTick(ServerTickEvent.Post event) {
        event.getServer().getPlayerList().getPlayers().forEach(player -> {
            RegistryGameplayEvents.dropLockedEquippedItems(player);
            HandlerTrinkets.dropLockedTrinkets(player);
            RegistryGameplayEvents.updatePlayerPassives(player);
            RegistryTitles.syncTitles(player);
        });
    }
}
