package com.seniors.justlevelingfork.network.packet.client;

import com.seniors.justlevelingfork.client.gui.OverlayAptitudeGui;
import com.seniors.justlevelingfork.network.ServerNetworking;

import java.util.function.Supplier;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent;

public class AptitudeOverlayCP {
    private final String aptitude;

    public AptitudeOverlayCP(String aptitude) {
        this.aptitude = aptitude;
    }

    public AptitudeOverlayCP(FriendlyByteBuf buffer) {
        this.aptitude = buffer.readUtf();
    }

    public void toBytes(FriendlyByteBuf buffer) {
        buffer.writeUtf(this.aptitude);
    }

    public void handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> OverlayAptitudeGui.showWarning(this.aptitude));
        context.setPacketHandled(true);
    }

    public static void send(Player player, String aptitude) {
        ServerNetworking.sendToPlayer(new AptitudeOverlayCP(aptitude), (ServerPlayer) player);
    }
}


