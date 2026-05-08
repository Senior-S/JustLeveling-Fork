package com.seniors.justlevelingfork.network.packet.common;

import com.seniors.justlevelingfork.network.packet.JustLevelingPacket;

import com.seniors.justlevelingfork.common.capability.AptitudeCapability;
import com.seniors.justlevelingfork.network.ServerNetworking;
import com.seniors.justlevelingfork.network.packet.client.SyncAptitudeCapabilityCP;
import com.seniors.justlevelingfork.registry.RegistryAttributes;

import java.util.UUID;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class CounterAttackSP implements JustLevelingPacket {
    private final boolean isCounterAttack;
    private final float modifier;

    public CounterAttackSP(boolean isCounterAttack, float modifier) {
        this.isCounterAttack = isCounterAttack;
        this.modifier = modifier;
    }

    public CounterAttackSP(FriendlyByteBuf buffer) {
        this.isCounterAttack = buffer.readBoolean();
        this.modifier = buffer.readFloat();
    }

    public void toBytes(FriendlyByteBuf buffer) {
        buffer.writeBoolean(this.isCounterAttack);
        buffer.writeFloat(this.modifier);
    }

    public void handle(ServerPlayer sender) { handle(this, sender); }

    public static void handle(CounterAttackSP msg, ServerPlayer sender) {
            ServerPlayer player = sender;
            if (player != null) {
                apply(player, msg.isCounterAttack, msg.modifier);
            }
    }

    public static void apply(ServerPlayer player, boolean isCounterAttack, float modifier) {
        AptitudeCapability aptitude = AptitudeCapability.get(player);
        if (aptitude == null) {
            return;
        }

        aptitude.setCounterAttack(isCounterAttack);
        new RegistryAttributes.registerAttribute(player, Attributes.ATTACK_DAMAGE, modifier, UUID.fromString("55550aa2-eff2-4a81-b92b-a1cb95f15590")).amplifyAttribute(isCounterAttack);
        if (!isCounterAttack) {
            aptitude.setCounterAttackTimer(0);
        }
        SyncAptitudeCapabilityCP.send(player);
    }

    public static void send(boolean isCounterAttack, float modifier) {
        ServerNetworking.sendToServer(new CounterAttackSP(isCounterAttack, modifier));
    }

}


