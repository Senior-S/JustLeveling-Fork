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
import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
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
        CLIENTBOUND_PACKETS.forEach(registration -> PayloadTypeRegistry.playS2C().register(registration.type, registration.codec()));
        SERVERBOUND_PACKETS.forEach(registration -> {
            PayloadTypeRegistry.playC2S().register(registration.type, registration.codec());
            ServerPlayNetworking.registerGlobalReceiver(registration.type, (payload, context) -> context.server().execute(() -> registration.handle(payload, context.player())));
        });
        CLIENTBOUND_PACKETS.forEach(registration -> PACKETS_BY_CLASS.put(registration.packetClass, registration));
        SERVERBOUND_PACKETS.forEach(registration -> PACKETS_BY_CLASS.put(registration.packetClass, registration));
    }

    public static void initClient() {
        CLIENTBOUND_PACKETS.forEach(registration -> ClientPlayNetworking.registerGlobalReceiver(registration.type, (payload, context) -> context.client().execute(() -> registration.handle(payload, null))));
    }

    public static void sendToServer(JustLevelingPacket message) {
        ClientPlayNetworking.send(toPayload(message));
    }

    public static void sendToPlayer(JustLevelingPacket message, ServerPlayer serverPlayer) {
        ServerPlayNetworking.send(serverPlayer, toPayload(message));
    }

    public static void sendToAllClients(JustLevelingPacket message) {
        MinecraftServer server = JustLevelingFork.server;
        if (server == null) {
            return;
        }
        RawPacketPayload payload = toPayload(message);
        server.getPlayerList().getPlayers().forEach(player -> ServerPlayNetworking.send(player, payload));
    }

    private static RawPacketPayload toPayload(JustLevelingPacket message) {
        PacketRegistration<?> registration = PACKETS_BY_CLASS.get(message.getClass());
        if (registration == null) {
            throw new IllegalArgumentException("Unregistered packet: " + message.getClass().getName());
        }
        FriendlyByteBuf buffer = new FriendlyByteBuf(Unpooled.buffer());
        try {
            message.toBytes(buffer);
            byte[] bytes = new byte[buffer.readableBytes()];
            buffer.readBytes(bytes);
            return new RawPacketPayload(registration.type, bytes);
        } catch (RuntimeException e) {
            throw new IllegalStateException("Failed to encode packet " + message.getClass().getName(), e);
        } finally {
            buffer.release();
        }
    }

    private static <T extends JustLevelingPacket> PacketRegistration<T> packet(String path, Class<T> packetClass, Function<FriendlyByteBuf, T> decoder) {
        return new PacketRegistration<>(new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(JustLevelingFork.MOD_ID, path)), packetClass, decoder);
    }

    private record RawPacketPayload(CustomPacketPayload.Type<RawPacketPayload> type, byte[] bytes) implements CustomPacketPayload {
    }

    private record PacketRegistration<T extends JustLevelingPacket>(CustomPacketPayload.Type<RawPacketPayload> type, Class<T> packetClass, Function<FriendlyByteBuf, T> decoder) {
        private StreamCodec<RegistryFriendlyByteBuf, RawPacketPayload> codec() {
            return CustomPacketPayload.codec(
                    (value, buffer) -> buffer.writeByteArray(value.bytes()),
                    buffer -> new RawPacketPayload(this.type, buffer.readByteArray())
            );
        }

        private void handle(RawPacketPayload payload, ServerPlayer sender) {
            FriendlyByteBuf buffer = new FriendlyByteBuf(Unpooled.wrappedBuffer(payload.bytes()));
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
