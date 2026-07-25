package com.seniors.justlevelingfork.platform.neoforge;

import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.common.util.FakePlayer;

public final class PlatformHelperImpl {
    private PlatformHelperImpl() {
    }

    public static boolean isFakePlayer(Player player) {
        return player instanceof FakePlayer;
    }
}
