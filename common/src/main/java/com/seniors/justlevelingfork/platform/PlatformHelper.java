package com.seniors.justlevelingfork.platform;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.world.entity.player.Player;

public final class PlatformHelper {
    private PlatformHelper() {
    }

    @ExpectPlatform
    public static boolean isFakePlayer(Player player) {
        throw new AssertionError();
    }
}
