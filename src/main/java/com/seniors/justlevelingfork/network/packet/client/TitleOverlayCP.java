package com.seniors.justlevelingfork.network.packet.client;

import com.seniors.justlevelingfork.network.packet.JustLevelingPacket;

import com.seniors.justlevelingfork.client.gui.OverlayTitleGui;
import com.seniors.justlevelingfork.network.ServerNetworking;
import com.seniors.justlevelingfork.registry.RegistryTitles;
import com.seniors.justlevelingfork.registry.title.Title;


import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

public class TitleOverlayCP implements JustLevelingPacket {
    private final String title;

    public TitleOverlayCP(Title title) {
        this.title = title.getName();
    }

    public TitleOverlayCP(FriendlyByteBuf buffer) {
        this.title = buffer.readUtf();
    }

    public void toBytes(FriendlyByteBuf buffer) {
        buffer.writeUtf(this.title);
    }

    public void handle(ServerPlayer sender) {
            Title title = RegistryTitles.getTitle(this.title);
            OverlayTitleGui.list.enqueue(title);
            OverlayTitleGui.showWarning();
    }

    public static void send(Player player, Title title) {
        ServerNetworking.sendToPlayer(new TitleOverlayCP(title), (ServerPlayer) player);
    }
}


