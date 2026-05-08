package com.seniors.justlevelingfork.integration.questlog;

import com.seniors.justlevelingfork.registry.RegistryItems;
import net.minecraft.resources.ResourceLocation;
import org.infernalstudios.questlog.core.quests.QuestObjectiveRegistry;
import org.infernalstudios.questlog.core.quests.QuestRewardRegistry;

public final class QuestlogIntegration {
    private QuestlogIntegration() {
    }

    public static void load() {
        QuestObjectiveRegistry.register(id("aptitude_level"), AptitudeLevelObjective::new);
        QuestRewardRegistry.register(id("aptitude_level"), AptitudeLevelReward::new);
        QuestRewardRegistry.register(id("title"), TitleReward::new);
    }

    private static ResourceLocation id(String path) {
        return RegistryItems.id(path);
    }
}
