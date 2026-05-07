package com.seniors.justlevelingfork.integration.ftbquests;

import dev.ftb.mods.ftblibrary.config.ConfigGroup;
import dev.ftb.mods.ftbquests.quest.Quest;
import dev.ftb.mods.ftbquests.quest.TeamData;
import dev.ftb.mods.ftbquests.quest.task.Task;
import dev.ftb.mods.ftbquests.quest.task.TaskType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;

public class AptitudeLevelTask extends Task {
    private String aptitude = "dexterity";
    private int level = 10;

    public AptitudeLevelTask(long id, Quest quest) {
        super(id, quest);
    }

    @Override
    public TaskType getType() {
        return FTBQuestsIntegration.APTITUDE_LEVEL_TASK;
    }

    @Override
    public void writeData(CompoundTag nbt) {
        super.writeData(nbt);
        nbt.putString("aptitude", this.aptitude);
        nbt.putInt("level", this.level);
    }

    @Override
    public void readData(CompoundTag nbt) {
        super.readData(nbt);
        this.aptitude = nbt.getString("aptitude");
        this.level = nbt.getInt("level");
    }

    @Override
    public void writeNetData(FriendlyByteBuf buffer) {
        super.writeNetData(buffer);
        buffer.writeUtf(this.aptitude);
        buffer.writeInt(this.level);
    }

    @Override
    public void readNetData(FriendlyByteBuf buffer) {
        super.readNetData(buffer);
        this.aptitude = buffer.readUtf();
        this.level = buffer.readInt();
    }

    @Override
    public void fillConfigGroup(ConfigGroup config) {
        super.fillConfigGroup(config);
        config.addString("aptitude", this.aptitude, v -> this.aptitude = v, "dexterity")
                .setNameKey("ftbquests.task.justlevelingfork.aptitude");
        config.addInt("level", this.level, v -> this.level = v, 10, 1, Integer.MAX_VALUE)
                .setNameKey("ftbquests.task.justlevelingfork.level");
    }

    @Override
    public int autoSubmitOnPlayerTick() {
        return 20;
    }

    @Override
    public void submitTask(TeamData teamData, ServerPlayer player, ItemStack craftedItem) {
        if (checkTaskSequence(teamData) && FTBQuestUtil.hasAptitudeLevel(player, this.aptitude, this.level)) {
            teamData.setProgress(this, 1L);
        }
    }
}
