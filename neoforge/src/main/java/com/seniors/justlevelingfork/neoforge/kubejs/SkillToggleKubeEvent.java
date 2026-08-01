package com.seniors.justlevelingfork.neoforge.kubejs;

import com.seniors.justlevelingfork.registry.skills.Skill;
import dev.latvian.mods.kubejs.event.KubeEvent;
import net.minecraft.server.level.ServerPlayer;

public final class SkillToggleKubeEvent implements KubeEvent {
    private final ServerPlayer player;
    private final Skill skill;
    private final boolean enabled;

    public SkillToggleKubeEvent(ServerPlayer player, Skill skill, boolean enabled) {
        this.player = player;
        this.skill = skill;
        this.enabled = enabled;
    }

    public ServerPlayer getPlayer() {
        return player;
    }

    public Skill getSkill() {
        return skill;
    }

    public boolean isEnabled() {
        return enabled;
    }
}
