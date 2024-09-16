package com.seniors.justlevelingfork.kubejs.events;

import dev.latvian.mods.kubejs.event.EventGroup;
import dev.latvian.mods.kubejs.event.EventHandler;

public interface CustomEvents {
    EventGroup GROUP = EventGroup.of("JLForkEvents");

    EventHandler APTITUDE_LEVELUP = GROUP.client("aptitudeLevelUp", () -> LevelUpEvent.class);
}
