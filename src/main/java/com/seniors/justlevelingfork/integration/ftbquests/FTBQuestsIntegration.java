package com.seniors.justlevelingfork.integration.ftbquests;

import com.seniors.justlevelingfork.registry.RegistryItems;
import dev.ftb.mods.ftblibrary.icon.Icon;
import dev.ftb.mods.ftbquests.quest.reward.RewardTypes;
import dev.ftb.mods.ftbquests.quest.task.TaskTypes;
import net.minecraft.network.chat.Component;

public final class FTBQuestsIntegration {
    public static final dev.ftb.mods.ftbquests.quest.task.TaskType APTITUDE_LEVEL_TASK = TaskTypes.register(
            RegistryItems.id("aptitude_level"),
            AptitudeLevelTask::new,
            () -> Icon.getIcon("justlevelingfork:item/leveling_book")
    ).setDisplayName(Component.translatable("ftbquests.task.justlevelingfork.aptitude_level"));

    public static final dev.ftb.mods.ftbquests.quest.reward.RewardType APTITUDE_LEVEL_REWARD = RewardTypes.register(
            RegistryItems.id("aptitude_level"),
            AptitudeLevelReward::new,
            () -> Icon.getIcon("justlevelingfork:item/leveling_book")
    ).setDisplayName(Component.translatable("ftbquests.reward.justlevelingfork.aptitude_level"));

    public static final dev.ftb.mods.ftbquests.quest.reward.RewardType TITLE_REWARD = RewardTypes.register(
            RegistryItems.id("title"),
            TitleReward::new,
            () -> Icon.getIcon("minecraft:item/name_tag")
    ).setDisplayName(Component.translatable("ftbquests.reward.justlevelingfork.title"));

    private FTBQuestsIntegration() {
    }

    public static void load() {
    }
}
