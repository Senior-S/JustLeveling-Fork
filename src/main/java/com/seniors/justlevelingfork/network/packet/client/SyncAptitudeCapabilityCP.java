package com.seniors.justlevelingfork.network.packet.client;

import com.seniors.justlevelingfork.network.packet.JustLevelingPacket;

import com.seniors.justlevelingfork.JustLevelingFork;
import com.seniors.justlevelingfork.common.capability.AptitudeCapability;
import com.seniors.justlevelingfork.network.ServerNetworking;


import net.minecraft.client.player.LocalPlayer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

public class SyncAptitudeCapabilityCP implements JustLevelingPacket {
    private final CompoundTag nbt;

    public SyncAptitudeCapabilityCP(CompoundTag nbt) {
        this.nbt = nbt;
    }

    public SyncAptitudeCapabilityCP(FriendlyByteBuf buffer) {
        this.nbt = buffer.readNbt();
    }

    public void toBytes(FriendlyByteBuf buffer) {
        buffer.writeNbt(this.nbt);
    }

    public void handle(ServerPlayer sender) {
            AptitudeCapability capability = AptitudeCapability.get();
            if (capability != null) {
                capability.deserializeNBT(this.nbt);
            }
    }

    public static void send(Player player) {
        AptitudeCapability capability = AptitudeCapability.get(player);
        if (capability != null && player instanceof ServerPlayer serverPlayer) {
            ServerNetworking.sendToPlayer(new SyncAptitudeCapabilityCP(capability.serializeNBT()), serverPlayer);
        }
    }

    public static void sendToAllPlayers() {
        if (JustLevelingFork.server == null) {
            return;
        }
        JustLevelingFork.server.getPlayerList().getPlayers().forEach(SyncAptitudeCapabilityCP::send);
    }
}


