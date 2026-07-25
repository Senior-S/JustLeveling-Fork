package com.seniors.justlevelingfork.platform.fabric;

import net.fabricmc.fabric.api.entity.FakePlayer;
import net.minecraft.world.entity.player.Player;

public final class PlatformHelperImpl {
    private PlatformHelperImpl() {
    }

    public static boolean isFakePlayer(Player player) {
        return player instanceof FakePlayer;
    }
}
