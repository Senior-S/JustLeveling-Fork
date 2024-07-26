package com.seniors.justlevelingfork.network.packet.common;

import com.seniors.justlevelingfork.common.capability.AptitudeCapability;
import com.seniors.justlevelingfork.network.ServerNetworking;
import com.seniors.justlevelingfork.network.packet.client.SyncAptitudeCapabilityCP;
import com.seniors.justlevelingfork.registry.RegistryTitles;
import com.seniors.justlevelingfork.registry.title.Title;

import java.util.function.Supplier;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

public class SetPlayerTitleSP {
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

    public void handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();

            if (player != null) {
                AptitudeCapability capability = AptitudeCapability.get(player);
                Title title = RegistryTitles.getTitle(this.title);
                capability.setPlayerTitle(title);
                player.setCustomName(Component.translatable(title.getKey()));
                player.refreshDisplayName();
                player.refreshTabListName();
                SyncAptitudeCapabilityCP.send(player);
            }
        });
        context.setPacketHandled(true);
    }

    public static void send(Title title) {
        ServerNetworking.sendToServer(new SetPlayerTitleSP(title));
    }
}


