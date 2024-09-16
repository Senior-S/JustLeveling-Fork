package com.seniors.justlevelingfork.kubejs.events;

import com.seniors.justlevelingfork.registry.aptitude.Aptitude;
import dev.latvian.mods.kubejs.event.EventJS;
import net.minecraft.world.entity.player.Player;

public class LevelUpEvent extends EventJS {
    private final Player player;
    private final Aptitude aptitude;

    private boolean cancelled = false;

    public LevelUpEvent(Player player, Aptitude aptitude) {
        this.player = player;
        this.aptitude = aptitude;
    }

    public Player getPlayer() {
        return player;
    }

    public Aptitude getAptitude(){
        return aptitude;
    }

    public boolean getCancelled(){
        return cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
}
