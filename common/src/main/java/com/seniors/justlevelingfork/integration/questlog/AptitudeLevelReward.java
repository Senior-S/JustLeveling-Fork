package com.seniors.justlevelingfork.integration.questlog;

import com.google.gson.JsonObject;
import com.seniors.justlevelingfork.integration.ftbquests.FTBQuestUtil;
import net.minecraft.server.level.ServerPlayer;
import org.infernalstudios.questlog.core.quests.rewards.Reward;
import org.infernalstudios.questlog.util.JsonUtils;

public class AptitudeLevelReward extends Reward {
    private final String aptitude;
    private final int level;
    private final boolean onlyIncrease;

    public AptitudeLevelReward(JsonObject definition) {
        super(definition);
        this.aptitude = JsonUtils.getString(definition, "aptitude");
        this.level = JsonUtils.getInt(definition, "level");
        this.onlyIncrease = JsonUtils.getOrDefault(definition, "only_increase", true);
    }

    @Override
    public void applyReward(ServerPlayer player) {
        FTBQuestUtil.setAptitudeLevel(player, this.aptitude, this.level, this.onlyIncrease);
        super.applyReward(player);
    }
}
