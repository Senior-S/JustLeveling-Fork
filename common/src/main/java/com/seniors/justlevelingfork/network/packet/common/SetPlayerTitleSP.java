package com.seniors.justlevelingfork.network.packet.common;

import com.seniors.justlevelingfork.network.packet.JustLevelingPacket;

import com.seniors.justlevelingfork.common.capability.AptitudeCapability;
import com.seniors.justlevelingfork.network.ServerNetworking;
import com.seniors.justlevelingfork.network.packet.client.SyncAptitudeCapabilityCP;
import com.seniors.justlevelingfork.registry.RegistryTitles;
import com.seniors.justlevelingfork.registry.title.Title;


import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

public class SetPlayerTitleSP implements JustLevelingPacket {
    private final String title;

    public SetPlayerTitleSP(Title title) {
        this.title = title.getName();
    }

    public SetPlayerTitleSP(FriendlyByteBuf buffer) {
        this.title = buffer.readUtf();
    }

    public void toBytes(FriendlyByteBuf buffer) {
        buffer.writeUtf(this.title);
    }

    public void handle(ServerPlayer sender) {
        if (sender == null) {
            return;
        }

        AptitudeCapability capability = AptitudeCapability.get(sender);
        Title title = RegistryTitles.getTitle(this.title);
        if (capability == null || title == null || !Boolean.TRUE.equals(capability.unlockTitle.get(title.getName()))) {
            return;
        }

        capability.setPlayerTitle(title);
        sender.setCustomName(Component.translatable(title.getKey()));
        SyncAptitudeCapabilityCP.send(sender);
    }

    public static void send(Title title) {
        ServerNetworking.sendToServer(new SetPlayerTitleSP(title));
    }
}


