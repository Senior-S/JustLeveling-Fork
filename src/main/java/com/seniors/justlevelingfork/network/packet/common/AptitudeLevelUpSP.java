package com.seniors.justlevelingfork.network.packet.common;

import com.seniors.justlevelingfork.JustLevelingFork;
import com.seniors.justlevelingfork.client.core.Utils;
import com.seniors.justlevelingfork.common.capability.AptitudeCapability;
import com.seniors.justlevelingfork.handler.HandlerCommonConfig;
import com.seniors.justlevelingfork.network.ServerNetworking;
import com.seniors.justlevelingfork.network.packet.client.SyncAptitudeCapabilityCP;
import com.seniors.justlevelingfork.registry.RegistryAptitudes;
import com.seniors.justlevelingfork.registry.aptitude.Aptitude;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class AptitudeLevelUpSP {
    private final String aptitude;

    public AptitudeLevelUpSP(Aptitude aptitude) {
        this.aptitude = aptitude.getName();
    }

    public AptitudeLevelUpSP(FriendlyByteBuf buffer) {
        this.aptitude = buffer.readUtf();
    }

    public void toBytes(FriendlyByteBuf buffer) {
        buffer.writeUtf(this.aptitude);
    }

    public void handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            if (player != null) {
                AptitudeCapability capability = AptitudeCapability.get(player);
                Aptitude aptitudePlayer = RegistryAptitudes.getAptitude(this.aptitude);
                int aptitudeLevel = capability.getAptitudeLevel(aptitudePlayer);

                boolean canLevelUpAptitude = (player.isCreative()
                        || AptitudeLevelUpSP.requiredPoints(aptitudeLevel) <= player.totalExperience
                        || AptitudeLevelUpSP.requiredExperienceLevels(aptitudeLevel) <= player.experienceLevel);

                if (!canLevelUpAptitude){
                    JustLevelingFork.getLOGGER().info("Received level up packet without the required EXP needed to level up, skipping packet...");
                    return;
                }

                int requiredPoints = requiredPoints(aptitudeLevel);

                capability.addAptitudeLevel(aptitudePlayer, 1);
                SyncAptitudeCapabilityCP.send(player);
                if (!player.isCreative()) {
                    Utils.addPlayerXP(player, requiredPoints * -1);
                }
            }
        });
        context.setPacketHandled(true);
    }

    public static int requiredPoints(int aptitudeLevel) {
        return Utils.getExperienceForLevel(aptitudeLevel + HandlerCommonConfig.HANDLER.instance().aptitudeFirstCostLevel - 1);
    }

    public static int requiredExperienceLevels(int aptitudeLevel) {
        return aptitudeLevel + HandlerCommonConfig.HANDLER.instance().aptitudeFirstCostLevel - 1;
    }

    public static void send(Aptitude aptitude) {
        ServerNetworking.sendToServer(new AptitudeLevelUpSP(aptitude));
    }
}


