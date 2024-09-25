package com.seniors.justlevelingfork.network.packet.common;

import com.seniors.justlevelingfork.JustLevelingFork;
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
                        || AptitudeLevelUpSP.requiredLevels(aptitudeLevel) <= player.experienceLevel);

                if (!canLevelUpAptitude){
                    JustLevelingFork.getLOGGER().info("Received level up packet without the required EXP needed to level up, skipping packet...");
                    return;
                }

                int requiredLevel = requiredPoints(aptitudeLevel);

                capability.addAptitudeLevel(aptitudePlayer, 1);
                SyncAptitudeCapabilityCP.send(player);
                if (!player.isCreative()) {
                    player.giveExperiencePoints(-requiredLevel);
                }
            }
        });
        context.setPacketHandled(true);
    }

    public static int requiredPoints(int aptitudeLevel) {
        return getXpNeededForLevel(aptitudeLevel + HandlerCommonConfig.HANDLER.instance().aptitudeFirstCostLevel - 1);
    }

    public static int requiredLevels(int aptitudeLevel) {
        return aptitudeLevel + HandlerCommonConfig.HANDLER.instance().aptitudeFirstCostLevel - 1;
    }

    private static int getXpNeededForLevel(int level) {
        int xp = 0;
        for (int i = 0; i < level; i++) {
            if (level < 17) {
                xp = level * level + 6 * level;
            } else if (level < 32) {
                xp = (int) (2.5D * (level * level) - 40.5D * level + 360.0D);
            } else {
                xp = (int) (4.5D * (level * level) - 162.5D * level + 2220.0D);
            }
        }
        return xp;
    }

    public static void send(Aptitude aptitude) {
        ServerNetworking.sendToServer(new AptitudeLevelUpSP(aptitude));
    }
}


