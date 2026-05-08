package org.infernalstudios.questlog.core.quests;

import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import org.infernalstudios.questlog.core.quests.rewards.Reward;

import java.util.function.Function;

public class QuestRewardRegistry {
    public static void register(ResourceLocation id, Function<JsonObject, Reward> factory) {
    }
}
