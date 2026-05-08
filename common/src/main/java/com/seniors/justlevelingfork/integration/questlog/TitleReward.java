package com.seniors.justlevelingfork.integration.questlog;

import com.google.gson.JsonObject;
import com.seniors.justlevelingfork.integration.ftbquests.FTBQuestUtil;
import net.minecraft.server.level.ServerPlayer;
import org.infernalstudios.questlog.core.quests.rewards.Reward;
import org.infernalstudios.questlog.util.JsonUtils;

public class TitleReward extends Reward {
    private final String title;
    private final boolean equipTitle;

    public TitleReward(JsonObject definition) {
        super(definition);
        this.title = JsonUtils.getString(definition, "title");
        this.equipTitle = JsonUtils.getOrDefault(definition, "equip_title", false);
    }

    @Override
    public void applyReward(ServerPlayer player) {
        FTBQuestUtil.unlockTitle(player, this.title, this.equipTitle);
        super.applyReward(player);
    }
}
