package com.seniors.justlevelingfork.neoforge.kubejs;

import dev.latvian.mods.kubejs.event.EventGroup;
import dev.latvian.mods.kubejs.event.EventHandler;

public final class JustLevelingKubeJSEvents {
    public static final EventGroup GROUP = EventGroup.of("JLForkEvents");
    public static final EventHandler SKILL_TOGGLED = GROUP.server("skillToggled", () -> SkillToggleKubeEvent.class);

    private JustLevelingKubeJSEvents() {
    }
}
