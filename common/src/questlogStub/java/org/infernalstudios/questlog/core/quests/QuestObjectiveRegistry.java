package org.infernalstudios.questlog.core.quests;

import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import org.infernalstudios.questlog.core.quests.objectives.Objective;

import java.util.function.Function;

public class QuestObjectiveRegistry {
    public static void register(ResourceLocation id, Function<JsonObject, Objective> factory) {
    }
}
