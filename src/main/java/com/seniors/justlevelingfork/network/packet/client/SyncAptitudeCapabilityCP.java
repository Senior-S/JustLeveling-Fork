package com.seniors.justlevelingfork.network.packet.client;

import com.seniors.justlevelingfork.common.capability.AptitudeCapability;
import com.seniors.justlevelingfork.network.ServerNetworking;

import java.util.function.Supplier;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent;

public class SyncAptitudeCapabilityCP {
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

    public void handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> AptitudeCapability.get().deserializeNBT(this.nbt));


        context.setPacketHandled(true);
    }

    public static void send(Player player) {
        ServerNetworking.sendToPlayer(new SyncAptitudeCapabilityCP(AptitudeCapability.get(player).serializeNBT()), (ServerPlayer) player);
    }
}


