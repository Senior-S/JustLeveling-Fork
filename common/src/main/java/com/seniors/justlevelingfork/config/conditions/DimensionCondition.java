package com.seniors.justlevelingfork.config.conditions;

import com.seniors.justlevelingfork.config.models.TitleModel;
import net.minecraft.server.level.ServerPlayer;

public class DimensionCondition extends ConditionImpl<String> {

    public DimensionCondition(){
        super("Special");
    }

    @Override
    public void ProcessVariable(String value, ServerPlayer serverPlayer) {
        setProcessedValue(serverPlayer.level().dimension().location().toString());
    }

    @Override
    public boolean MeetCondition(String value, TitleModel.EComparator comparator) {
        return getProcessedValue().equalsIgnoreCase(value);
    }
}
