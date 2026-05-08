package com.seniors.justlevelingfork.config.conditions;

import com.seniors.justlevelingfork.JustLevelingFork;
import com.seniors.justlevelingfork.config.models.TitleModel;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

import java.util.Objects;

public class AdvancementCondition extends ConditionImpl<Boolean> {

    public AdvancementCondition(){
        super("Advancement");
    }

    @Override
    public void ProcessVariable(String value, ServerPlayer serverPlayer) {
        AdvancementHolder advancement = Objects.requireNonNull(serverPlayer.getServer()).getAdvancements().get(ResourceLocation.parse(value.replace("-", "/")));
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
