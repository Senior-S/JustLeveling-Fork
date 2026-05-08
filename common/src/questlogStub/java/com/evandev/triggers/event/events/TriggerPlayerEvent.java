package com.evandev.triggers.event.events;

import net.minecraft.world.entity.player.Player;

public class TriggerPlayerEvent {
    public static class Tick {
        public Player player;
    }
}
