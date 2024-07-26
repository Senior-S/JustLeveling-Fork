package com.seniors.justlevelingfork.network.packet.common;

import com.seniors.justlevelingfork.common.capability.AptitudeCapability;
import com.seniors.justlevelingfork.network.ServerNetworking;
import com.seniors.justlevelingfork.network.packet.client.SyncAptitudeCapabilityCP;
import com.seniors.justlevelingfork.registry.RegistryAttributes;

import java.util.UUID;
import java.util.function.Supplier;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.network.NetworkEvent;

public class CounterAttackSP {
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

    public void handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            if (player != null) {
                AptitudeCapability aptitude = AptitudeCapability.get(player);
                aptitude.setCounterAttack(this.isCounterAttack);
                (new RegistryAttributes.registerAttribute(player, Attributes.ATTACK_DAMAGE, this.modifier, UUID.fromString("55550aa2-eff2-4a81-b92b-a1cb95f15590"))).amplifyAttribute(true);
                if (!this.isCounterAttack)
                    aptitude.setCounterAttackTimer(0);
                SyncAptitudeCapabilityCP.send(player);
            }
        });
        context.setPacketHandled(true);
    }

    public static void send(boolean isCounterAttack, float modifier) {
        ServerNetworking.sendToServer(new CounterAttackSP(isCounterAttack, modifier));
    }
}


