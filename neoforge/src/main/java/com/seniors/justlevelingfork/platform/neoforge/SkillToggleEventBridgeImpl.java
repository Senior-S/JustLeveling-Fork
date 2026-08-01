package com.seniors.justlevelingfork.platform.neoforge;

import com.seniors.justlevelingfork.neoforge.kubejs.JustLevelingKubeJSEvents;
import com.seniors.justlevelingfork.neoforge.kubejs.SkillToggleKubeEvent;
import com.seniors.justlevelingfork.registry.skills.Skill;
import dev.architectury.platform.Platform;
import net.minecraft.server.level.ServerPlayer;

public final class SkillToggleEventBridgeImpl {
    private SkillToggleEventBridgeImpl() {
    }

    public static void post(ServerPlayer player, Skill skill, boolean enabled) {
        if (Platform.isModLoaded("kubejs")) {
            JustLevelingKubeJSEvents.SKILL_TOGGLED.post(new SkillToggleKubeEvent(player, skill, enabled));
        }
    }
}
