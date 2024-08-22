package com.seniors.justlevelingfork.config.conditions;

import com.seniors.justlevelingfork.JustLevelingFork;
import com.seniors.justlevelingfork.config.models.TitleModel;
import net.minecraft.advancements.Advancement;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

import java.util.Objects;

public class AdvancementCondition extends ConditionImpl<Boolean> {

    public AdvancementCondition(){
        super("Advancement");
    }

    @Override
    public void ProcessVariable(String value, ServerPlayer serverPlayer) {
        Advancement advancement = Objects.requireNonNull(serverPlayer.getServer()).getAdvancements().getAdvancement(new ResourceLocation(value.replace("-", "/")));
        if (advancement == null){
            JustLevelingFork.getLOGGER().error(">> Error! Advancement name {} not found!", value);
            setProcessedValue(false);
            return;
        }

        setProcessedValue(serverPlayer.getAdvancements().getOrStartProgress(advancement).isDone());
    }

    @Override
    public boolean MeetCondition(String value, TitleModel.EComparator comparator) {
        return getProcessedValue();
    }
}
