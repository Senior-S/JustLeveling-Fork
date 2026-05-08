package com.seniors.justlevelingfork.network;

import com.seniors.justlevelingfork.JustLevelingFork;
import com.seniors.justlevelingfork.network.packet.client.AptitudeOverlayCP;
import com.seniors.justlevelingfork.network.packet.client.CommonConfigSyncCP;
import com.seniors.justlevelingfork.network.packet.client.ConfigSyncCP;
import com.seniors.justlevelingfork.network.packet.client.DynamicConfigSyncCP;
import com.seniors.justlevelingfork.network.packet.client.PlayerMessagesCP;
import com.seniors.justlevelingfork.network.packet.client.SyncAptitudeCapabilityCP;
import com.seniors.justlevelingfork.network.packet.client.TitleOverlayCP;
import com.seniors.justlevelingfork.network.packet.JustLevelingPacket;
import com.seniors.justlevelingfork.network.packet.common.AptitudeLevelUpSP;
import com.seniors.justlevelingfork.network.packet.common.CounterAttackSP;
import com.seniors.justlevelingfork.network.packet.common.OpenEnderChestSP;
import com.seniors.justlevelingfork.network.packet.common.PassiveLevelDownSP;
import com.seniors.justlevelingfork.network.packet.common.PassiveLevelUpSP;
import com.seniors.justlevelingfork.network.packet.common.SetPlayerTitleSP;
import com.seniors.justlevelingfork.network.packet.common.ToggleSkillSP;
import dev.architectury.networking.NetworkManager;
import dev.architectury.platform.Platform;
import dev.architectury.utils.Env;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class ServerNetworking {
    private static final List<PacketRegistration<?>> CLIENTBOUND_PACKETS = List.of(
            packet("aptitude_overlay", AptitudeOverlayCP.class, AptitudeOverlayCP::new),
            packet("common_config_sync", CommonConfigSyncCP.class, CommonConfigSyncCP::new),
            packet("config_sync", ConfigSyncCP.class, ConfigSyncCP::new),
            packet("dynamic_config_sync", DynamicConfigSyncCP.class, DynamicConfigSyncCP::new),
            packet("player_messages", PlayerMessagesCP.class, PlayerMessagesCP::new),
            packet("sync_aptitude_capability", SyncAptitudeCapabilityCP.class, SyncAptitudeCapabilityCP::new),
            packet("title_overlay", TitleOverlayCP.class, TitleOverlayCP::new)
    );
    private static final List<PacketRegistration<?>> SERVERBOUND_PACKETS = List.of(
            packet("aptitude_level_up", AptitudeLevelUpSP.class, AptitudeLevelUpSP::new),
            packet("counter_attack", CounterAttackSP.class, CounterAttackSP::new),
            packet("open_ender_chest", OpenEnderChestSP.class, OpenEnderChestSP::new),
            packet("passive_level_down", PassiveLevelDownSP.class, PassiveLevelDownSP::new),
            packet("passive_level_up", PassiveLevelUpSP.class, PassiveLevelUpSP::new),
            packet("set_player_title", SetPlayerTitleSP.class, SetPlayerTitleSP::new),
            packet("toggle_skill", ToggleSkillSP.class, ToggleSkillSP::new)
    );
    private static final Map<Class<?>, PacketRegistration<?>> PACKETS_BY_CLASS = new HashMap<>();

    public static void init() {
        if (Platform.getEnvironment() == Env.SERVER) {
            CLIENTBOUND_PACKETS.forEach(registration -> NetworkManager.registerS2CPayloadType(registration.id));
        }
        SERVERBOUND_PACKETS.forEach(registration -> {
            NetworkManager.registerReceiver(
                    NetworkManager.clientToServer(),
                    registration.id,
                    (buffer, context) -> {
                        ByteBuf payload = Unpooled.copiedBuffer(buffer);
                        context.queue(() -> registration.handle(payload, context.registryAccess(), (ServerPlayer) context.getPlayer()));
                    }
            );
        });
        CLIENTBOUND_PACKETS.forEach(registration -> PACKETS_BY_CLASS.put(registration.packetClass, registration));
        SERVERBOUND_PACKETS.forEach(registration -> PACKETS_BY_CLASS.put(registration.packetClass, registration));
    }

    public static void initClient() {
        CLIENTBOUND_PACKETS.forEach(registration -> NetworkManager.registerReceiver(
                NetworkManager.serverToClient(),
                registration.id,
                (buffer, context) -> {
                    ByteBuf payload = Unpooled.copiedBuffer(buffer);
                    context.queue(() -> registration.handle(payload, context.registryAccess(), null));
                }
        ));
    }

    public static void sendToServer(JustLevelingPacket message) {
        PacketRegistration<?> registration = getRegistration(message);
        NetworkManager.sendToServer(registration.id, toBuffer(message));
    }

    public static void sendToPlayer(JustLevelingPacket message, ServerPlayer serverPlayer) {
        PacketRegistration<?> registration = getRegistration(message);
        NetworkManager.sendToPlayer(serverPlayer, registration.id, toBuffer(message));
    }

    public static void sendToAllClients(JustLevelingPacket message) {
        MinecraftServer server = JustLevelingFork.server;
        if (server == null) {
            return;
        }
        PacketRegistration<?> registration = getRegistration(message);
        NetworkManager.sendToPlayers(server.getPlayerList().getPlayers(), registration.id, toBuffer(message));
    }

    private static PacketRegistration<?> getRegistration(JustLevelingPacket message) {
        PacketRegistration<?> registration = PACKETS_BY_CLASS.get(message.getClass());
        if (registration == null) {
            throw new IllegalArgumentException("Unregistered packet: " + message.getClass().getName());
        }
        return registration;
    }

    private static RegistryFriendlyByteBuf toBuffer(JustLevelingPacket message) {
        RegistryFriendlyByteBuf buffer = new RegistryFriendlyByteBuf(Unpooled.buffer(), RegistryAccess.EMPTY);
        try {
            message.toBytes(buffer);
            return buffer;
        } catch (RuntimeException e) {
            buffer.release();
            throw new IllegalStateException("Failed to encode packet " + message.getClass().getName(), e);
        }
    }

    private static <T extends JustLevelingPacket> PacketRegistration<T> packet(String path, Class<T> packetClass, Function<FriendlyByteBuf, T> decoder) {
        return new PacketRegistration<>(ResourceLocation.fromNamespaceAndPath(JustLevelingFork.MOD_ID, path), packetClass, decoder);
    }

    private record PacketRegistration<T extends JustLevelingPacket>(ResourceLocation id, Class<T> packetClass, Function<FriendlyByteBuf, T> decoder) {
        private void handle(ByteBuf payload, RegistryAccess registryAccess, ServerPlayer sender) {
            RegistryFriendlyByteBuf buffer = new RegistryFriendlyByteBuf(payload, registryAccess);
            try {
                T packet = this.decoder.apply(buffer);
                packet.handle(sender);
            } catch (RuntimeException e) {
                throw new IllegalStateException("Failed to handle packet " + this.packetClass.getName(), e);
            } finally {
                buffer.release();
            }
        }
    }
}
