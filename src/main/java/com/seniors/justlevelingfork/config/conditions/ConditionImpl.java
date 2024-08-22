package com.seniors.justlevelingfork.config.conditions;

import com.seniors.justlevelingfork.config.models.TitleModel;
import net.minecraft.server.level.ServerPlayer;

public abstract class ConditionImpl<T> {

    private String _conditionName;
    public String getConditionName(){
        return _conditionName;
    }

    private T _processedValue;
    public T getProcessedValue(){
        return _processedValue;
    }
    public void setProcessedValue(T value)
    {
        _processedValue = value;
    }

    public ConditionImpl(String conditionName){
        _conditionName = conditionName;
    }

    public abstract void ProcessVariable(String value, ServerPlayer serverPlayer);

    public abstract boolean MeetCondition(String value, TitleModel.EComparator comparator);
}
