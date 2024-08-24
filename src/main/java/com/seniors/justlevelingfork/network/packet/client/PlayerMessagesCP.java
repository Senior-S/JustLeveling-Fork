package com.seniors.justlevelingfork.network.packet.client;

import com.seniors.justlevelingfork.handler.HandlerConfigClient;
import com.seniors.justlevelingfork.network.ServerNetworking;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;


public class PlayerMessagesCP {
    private final String message;
    private final int amount;

    public PlayerMessagesCP(String skill, int amount) {
        this.message = skill;
        this.amount = amount;
    }

    public PlayerMessagesCP(FriendlyByteBuf buffer) {
        this.message = buffer.readUtf();
        this.amount = buffer.readInt();
    }

    public void toBytes(FriendlyByteBuf buffer) {
        buffer.writeUtf(this.message);
        buffer.writeInt(this.amount);
    }

    public void handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            LocalPlayer localPlayer = (Minecraft.getInstance()).player;
            assert localPlayer != null;
            if (this.message.equals("overlay.skill.justlevelingfork.lucky_drop") && HandlerConfigClient.showLuckyDropSkillOverlay.get()) {
                localPlayer.displayClientMessage(new TranslatableComponent(this.message, this.amount), true);
            } else if ((this.message.equals("overlay.skill.justlevelingfork.critical_roll_1") || this.message.equals("overlay.skill.justlevelingfork.critical_roll_6")) && HandlerConfigClient.showCriticalRollSkillOverlay.get()) {
                localPlayer.displayClientMessage(new TranslatableComponent(this.message, this.amount), true);
            }
        });
        context.setPacketHandled(true);
    }

    public static void send(Player player, String message, int amount) {
        ServerNetworking.sendToPlayer(new PlayerMessagesCP(message, amount), (ServerPlayer) player);
    }
}


