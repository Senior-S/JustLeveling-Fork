package com.seniors.justlevelingfork.integration.ftbquests;

import com.seniors.justlevelingfork.common.capability.AptitudeCapability;
import com.seniors.justlevelingfork.handler.HandlerCommonConfig;
import com.seniors.justlevelingfork.network.packet.client.SyncAptitudeCapabilityCP;
import com.seniors.justlevelingfork.registry.RegistryAptitudes;
import com.seniors.justlevelingfork.registry.RegistryTitles;
import com.seniors.justlevelingfork.registry.aptitude.Aptitude;
import com.seniors.justlevelingfork.registry.title.Title;
import net.minecraft.server.level.ServerPlayer;

public final class FTBQuestUtil {
    private FTBQuestUtil() {
    }

    public static Aptitude getAptitude(String aptitudeName) {
        if (aptitudeName == null || aptitudeName.isBlank()) {
            return null;
        }
        return RegistryAptitudes.getAptitude(aptitudeName.trim().toLowerCase());
    }

    public static Title getTitle(String titleName) {
        if (titleName == null || titleName.isBlank()) {
            return null;
        }
        return RegistryTitles.getTitle(titleName.trim().toLowerCase());
    }

    public static int clampAptitudeLevel(int level) {
        return Math.max(1, Math.min(level, HandlerCommonConfig.HANDLER.instance().aptitudeMaxLevel));
    }

    public static boolean hasAptitudeLevel(ServerPlayer player, String aptitudeName, int level) {
        Aptitude aptitude = getAptitude(aptitudeName);
        AptitudeCapability capability = AptitudeCapability.get(player);
        return aptitude != null && capability != null && capability.getAptitudeLevel(aptitude) >= clampAptitudeLevel(level);
    }

    public static boolean setAptitudeLevel(ServerPlayer player, String aptitudeName, int level, boolean onlyIncrease) {
        Aptitude aptitude = getAptitude(aptitudeName);
        AptitudeCapability capability = AptitudeCapability.get(player);
        if (aptitude == null || capability == null) {
            return false;
        }

        int clampedLevel = clampAptitudeLevel(level);
        if (onlyIncrease) {
            clampedLevel = Math.max(capability.getAptitudeLevel(aptitude), clampedLevel);
        }
        capability.setAptitudeLevel(aptitude, clampedLevel);
        SyncAptitudeCapabilityCP.send(player);
        return true;
    }

    public static boolean unlockTitle(ServerPlayer player, String titleName, boolean equipTitle) {
        Title title = getTitle(titleName);
        AptitudeCapability capability = AptitudeCapability.get(player);
        if (title == null || capability == null) {
            return false;
        }

        capability.setUnlockTitle(title, true);
        if (equipTitle) {
            capability.setPlayerTitle(title);
        }
        SyncAptitudeCapabilityCP.send(player);
        RegistryTitles.syncTitles(player);
        return true;
    }
}
