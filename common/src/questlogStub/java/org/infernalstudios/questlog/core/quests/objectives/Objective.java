package org.infernalstudios.questlog.core.quests.objectives;

import com.google.gson.JsonObject;
import org.infernalstudios.questlog.core.quests.Quest;

public class Objective {
    public Objective(JsonObject definition) {
    }

    public void registerEventListeners() {
    }

    public Quest getParent() {
        return null;
    }

    public int getUnits() {
        return 0;
    }

    public void setUnits(int units) {
    }

    public int getRequiredAmount() {
        return 1;
    }

    public boolean isCompleted() {
        return false;
    }
}
