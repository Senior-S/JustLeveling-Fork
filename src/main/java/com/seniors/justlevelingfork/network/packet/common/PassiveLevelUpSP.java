package com.seniors.justlevelingfork.network.packet.common;

import com.seniors.justlevelingfork.common.capability.AptitudeCapability;
import com.seniors.justlevelingfork.network.ServerNetworking;
import com.seniors.justlevelingfork.network.packet.client.SyncAptitudeCapabilityCP;
import com.seniors.justlevelingfork.registry.RegistryPassives;
import com.seniors.justlevelingfork.registry.passive.Passive;

import java.util.function.Supplier;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

public class PassiveLevelUpSP {
    private final String passive;

    public PassiveLevelUpSP(Passive passive) {
        this.passive = passive.getName();
    }

    public PassiveLevelUpSP(FriendlyByteBuf buffer) {
        this.passive = buffer.readUtf();
    }

    public void toBytes(FriendlyByteBuf buffer) {
        buffer.writeUtf(this.passive);
    }

    public void handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();

            if (player != null) {
                AptitudeCapability capability = AptitudeCapability.get(player);

                Passive passive = RegistryPassives.getPassive(this.passive);
                capability.addPassiveLevel(passive, 1);
                SyncAptitudeCapabilityCP.send(player);
            }
        });
        context.setPacketHandled(true);
    }

    public static void send(Passive passive) {
        ServerNetworking.sendToServer(new PassiveLevelUpSP(passive));
    }
}


