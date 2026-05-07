package com.seniors.justlevelingfork.integration.ftbquests;

import dev.ftb.mods.ftblibrary.config.ConfigGroup;
import dev.ftb.mods.ftbquests.quest.Quest;
import dev.ftb.mods.ftbquests.quest.reward.Reward;
import dev.ftb.mods.ftbquests.quest.reward.RewardType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;

public class TitleReward extends Reward {
    private String title = "titleless";
    private boolean equipTitle = false;

    public TitleReward(long id, Quest quest) {
        super(id, quest);
    }

    @Override
    public RewardType getType() {
        return FTBQuestsIntegration.TITLE_REWARD;
    }

    @Override
    public void writeData(CompoundTag nbt) {
        super.writeData(nbt);
        nbt.putString("title", this.title);
        nbt.putBoolean("equip_title", this.equipTitle);
    }

    @Override
    public void readData(CompoundTag nbt) {
        super.readData(nbt);
        this.title = nbt.getString("title");
        this.equipTitle = nbt.getBoolean("equip_title");
    }

    @Override
    public void writeNetData(FriendlyByteBuf buffer) {
        super.writeNetData(buffer);
        buffer.writeUtf(this.title);
        buffer.writeBoolean(this.equipTitle);
    }

    @Override
    public void readNetData(FriendlyByteBuf buffer) {
        super.readNetData(buffer);
        this.title = buffer.readUtf();
        this.equipTitle = buffer.readBoolean();
    }

    @Override
    public void fillConfigGroup(ConfigGroup config) {
        super.fillConfigGroup(config);
        config.addString("title", this.title, v -> this.title = v, "titleless")
                .setNameKey("ftbquests.reward.justlevelingfork.title_id");
        config.addBool("equip_title", this.equipTitle, v -> this.equipTitle = v, false)
                .setNameKey("ftbquests.reward.justlevelingfork.equip_title");
    }

    @Override
    public void claim(ServerPlayer player, boolean notify) {
        FTBQuestUtil.unlockTitle(player, this.title, this.equipTitle);
    }
}
