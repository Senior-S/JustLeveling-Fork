package com.seniors.justlevelingfork.network.packet.common;

import com.seniors.justlevelingfork.network.packet.JustLevelingPacket;

import com.seniors.justlevelingfork.common.capability.AptitudeCapability;
import com.seniors.justlevelingfork.network.ServerNetworking;
import com.seniors.justlevelingfork.network.packet.client.SyncAptitudeCapabilityCP;
import com.seniors.justlevelingfork.registry.RegistryPassives;
import com.seniors.justlevelingfork.registry.passive.Passive;


import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;

public class PassiveLevelDownSP implements JustLevelingPacket {
    private final String passive;

    public PassiveLevelDownSP(Passive passive) {
        this.passive = passive.getName();
    }

    public PassiveLevelDownSP(FriendlyByteBuf buffer) {
        this.passive = buffer.readUtf();
    }

    public void toBytes(FriendlyByteBuf buffer) {
        buffer.writeUtf(this.passive);
    }

    public void handle(ServerPlayer sender) {
            ServerPlayer player = sender;

            if (player != null) {
                AptitudeCapability capability = AptitudeCapability.get(player);

                Passive passive = RegistryPassives.getPassive(this.passive);
                if (capability != null && RegistryPassives.isEnabled(passive)) {
                    capability.subPassiveLevel(passive, 1);
                    SyncAptitudeCapabilityCP.send(player);
                }
            }
    }

    public static void send(Passive passive) {
        ServerNetworking.sendToServer(new PassiveLevelDownSP(passive));
    }
}


