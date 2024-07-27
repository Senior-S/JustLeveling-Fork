package com.seniors.justlevelingfork.network;

import com.seniors.justlevelingfork.network.packet.client.AptitudeOverlayCP;
import com.seniors.justlevelingfork.network.packet.client.PlayerMessagesCP;
import com.seniors.justlevelingfork.network.packet.client.SyncAptitudeCapabilityCP;
import com.seniors.justlevelingfork.network.packet.client.TitleOverlayCP;

import java.util.Optional;

import com.seniors.justlevelingfork.network.packet.common.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;


public class ServerNetworking {
    private static int packetId = 0;
    private static final String PROTOCOL_VERSION = "1";
    public static SimpleChannel instance;

    public static void init() {
        //instance = NetworkRegistry.ChannelBuilder.named(new ResourceLocation("justlevelingfork", "network")).networkProtocolVersion(() -> "1").clientAcceptedVersions("1"::equals).serverAcceptedVersions("1"::equals).simpleChannel();
        instance = NetworkRegistry.newSimpleChannel(new ResourceLocation("justlevelingfork", "network"), () -> PROTOCOL_VERSION, PROTOCOL_VERSION::equals, PROTOCOL_VERSION::equals);

        instance.registerMessage(packetId++, SyncAptitudeCapabilityCP.class, SyncAptitudeCapabilityCP::toBytes, SyncAptitudeCapabilityCP::new, SyncAptitudeCapabilityCP::handle, Optional.of(NetworkDirection.PLAY_TO_CLIENT));
        instance.registerMessage(packetId++, PlayerMessagesCP.class, PlayerMessagesCP::toBytes, PlayerMessagesCP::new, PlayerMessagesCP::handle, Optional.of(NetworkDirection.PLAY_TO_CLIENT));
        instance.registerMessage(packetId++, AptitudeOverlayCP.class, AptitudeOverlayCP::toBytes, AptitudeOverlayCP::new, AptitudeOverlayCP::handle, Optional.of(NetworkDirection.PLAY_TO_CLIENT));
        instance.registerMessage(packetId++, TitleOverlayCP.class, TitleOverlayCP::toBytes, TitleOverlayCP::new, TitleOverlayCP::handle, Optional.of(NetworkDirection.PLAY_TO_CLIENT));

        instance.registerMessage(packetId++, AptitudeLevelUpSP.class, AptitudeLevelUpSP::toBytes, AptitudeLevelUpSP::new, AptitudeLevelUpSP::handle);
        instance.registerMessage(packetId++, PassiveLevelUpSP.class, PassiveLevelUpSP::toBytes, PassiveLevelUpSP::new, PassiveLevelUpSP::handle);
        instance.registerMessage(packetId++, PassiveLevelDownSP.class, PassiveLevelDownSP::toBytes, PassiveLevelDownSP::new, PassiveLevelDownSP::handle);
        instance.registerMessage(packetId++, ToggleSkillSP.class, ToggleSkillSP::toBytes, ToggleSkillSP::new, ToggleSkillSP::handle);
        instance.registerMessage(packetId++, CounterAttackSP.class, CounterAttackSP::toBytes, CounterAttackSP::new, CounterAttackSP::handle);
        instance.registerMessage(packetId++, SetPlayerTitleSP.class, SetPlayerTitleSP::toBytes, SetPlayerTitleSP::new, SetPlayerTitleSP::handle);
        instance.registerMessage(packetId++, OpenEnderChestSP.class, OpenEnderChestSP::toBytes, OpenEnderChestSP::new, OpenEnderChestSP::handle);
    }

    public static void sendToServer(Object message) {
        instance.sendToServer(message);
    }

    public static void sendToPlayer(Object message, ServerPlayer serverPlayer) {
        instance.send(PacketDistributor.PLAYER.with(() -> serverPlayer), message);
    }
}


