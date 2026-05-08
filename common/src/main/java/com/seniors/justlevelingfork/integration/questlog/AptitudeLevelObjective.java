package com.seniors.justlevelingfork.integration.questlog;

import com.evandev.triggers.Triggers;
import com.evandev.triggers.event.events.TriggerPlayerEvent;
import com.google.gson.JsonObject;
import com.seniors.justlevelingfork.integration.ftbquests.FTBQuestUtil;
import net.minecraft.server.level.ServerPlayer;
import org.infernalstudios.questlog.core.quests.objectives.Objective;
import org.infernalstudios.questlog.util.JsonUtils;

public class AptitudeLevelObjective extends Objective {
    private final String aptitude;
    private final int level;
    private int ticksUntilCheck = 0;

    public AptitudeLevelObjective(JsonObject definition) {
        super(definition);
        this.aptitude = JsonUtils.getString(definition, "aptitude");
        this.level = JsonUtils.getInt(definition, "level");
    }

    @Override
    public void registerEventListeners() {
        super.registerEventListeners();
        Triggers.EVENTS.addListener(this::onPlayerTick);
    }

    private void onPlayerTick(TriggerPlayerEvent.Tick event) {
        if (this.isCompleted() || this.getParent() == null) {
            return;
        }

        if (event.player instanceof ServerPlayer player
                && this.getParent().manager.player.equals(player)
                && --this.ticksUntilCheck <= 0) {
            if (FTBQuestUtil.hasAptitudeLevel(player, this.aptitude, this.level)) {
                this.setUnits(this.getRequiredAmount());
            }
            this.ticksUntilCheck = 20;
        }
    }
}
