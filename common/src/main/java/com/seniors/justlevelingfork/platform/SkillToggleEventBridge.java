package com.seniors.justlevelingfork.platform;

import com.seniors.justlevelingfork.registry.skills.Skill;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.server.level.ServerPlayer;

public final class SkillToggleEventBridge {
    private SkillToggleEventBridge() {
    }

    @ExpectPlatform
    public static void post(ServerPlayer player, Skill skill, boolean enabled) {
        throw new AssertionError();
    }
}
