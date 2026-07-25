package com.seniors.justlevelingfork.network.packet.common;

import com.seniors.justlevelingfork.network.packet.JustLevelingPacket;

import com.seniors.justlevelingfork.common.capability.AptitudeCapability;
import com.seniors.justlevelingfork.network.ServerNetworking;
import com.seniors.justlevelingfork.network.packet.client.SyncAptitudeCapabilityCP;
import com.seniors.justlevelingfork.registry.RegistryPassives;
import com.seniors.justlevelingfork.registry.passive.Passive;


import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;

public class PassiveLevelUpSP implements JustLevelingPacket {
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

    public void handle(ServerPlayer sender) {
        if (sender == null) {
            return;
        }

        AptitudeCapability capability = AptitudeCapability.get(sender);
        Passive passive = RegistryPassives.getPassive(this.passive);
        if (capability == null || passive == null || !RegistryPassives.isEnabled(passive)) {
            return;
        }

        int passiveLevel = capability.getPassiveLevel(passive);
        if (passiveLevel >= passive.levelsRequired.length
                || capability.getAptitudeLevel(passive.aptitude) < passive.levelsRequired[passiveLevel]) {
            return;
        }

        capability.addPassiveLevel(passive, 1);
        SyncAptitudeCapabilityCP.send(sender);
    }

    public static void send(Passive passive) {
        ServerNetworking.sendToServer(new PassiveLevelUpSP(passive));
    }
}


