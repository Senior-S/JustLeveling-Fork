package com.seniors.justlevelingfork.integration.ftbquests;

import dev.ftb.mods.ftblibrary.config.ConfigGroup;
import dev.ftb.mods.ftbquests.quest.Quest;
import dev.ftb.mods.ftbquests.quest.reward.Reward;
import dev.ftb.mods.ftbquests.quest.reward.RewardType;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.core.HolderLookup;
import net.minecraft.server.level.ServerPlayer;

public class AptitudeLevelReward extends Reward {
    private String aptitude = "dexterity";
    private int level = 10;
    private boolean onlyIncrease = true;

    public AptitudeLevelReward(long id, Quest quest) {
        super(id, quest);
    }

    @Override
    public RewardType getType() {
        return FTBQuestsIntegration.APTITUDE_LEVEL_REWARD;
    }

    @Override
    public void writeData(CompoundTag nbt, HolderLookup.Provider provider) {
        super.writeData(nbt, provider);
        nbt.putString("aptitude", this.aptitude);
        nbt.putInt("level", this.level);
        nbt.putBoolean("only_increase", this.onlyIncrease);
    }

    @Override
    public void readData(CompoundTag nbt, HolderLookup.Provider provider) {
        super.readData(nbt, provider);
        this.aptitude = nbt.getString("aptitude");
        this.level = nbt.getInt("level");
        this.onlyIncrease = !nbt.contains("only_increase") || nbt.getBoolean("only_increase");
    }

    @Override
    public void writeNetData(RegistryFriendlyByteBuf buffer) {
        super.writeNetData(buffer);
        buffer.writeUtf(this.aptitude);
        buffer.writeInt(this.level);
        buffer.writeBoolean(this.onlyIncrease);
    }

    @Override
    public void readNetData(RegistryFriendlyByteBuf buffer) {
        super.readNetData(buffer);
        this.aptitude = buffer.readUtf();
        this.level = buffer.readInt();
        this.onlyIncrease = buffer.readBoolean();
    }

    @Environment(EnvType.CLIENT)
    @Override
    public void fillConfigGroup(ConfigGroup config) {
        super.fillConfigGroup(config);
        config.addString("aptitude", this.aptitude, v -> this.aptitude = v, "dexterity")
                .setNameKey("ftbquests.reward.justlevelingfork.aptitude");
        config.addInt("level", this.level, v -> this.level = v, 10, 1, Integer.MAX_VALUE)
                .setNameKey("ftbquests.reward.justlevelingfork.level");
        config.addBool("only_increase", this.onlyIncrease, v -> this.onlyIncrease = v, true)
                .setNameKey("ftbquests.reward.justlevelingfork.only_increase");
    }

    @Override
    public void claim(ServerPlayer player, boolean notify) {
        FTBQuestUtil.setAptitudeLevel(player, this.aptitude, this.level, this.onlyIncrease);
    }
}
