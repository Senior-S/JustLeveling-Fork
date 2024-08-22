package com.seniors.justlevelingfork.handler;

import com.seniors.justlevelingfork.config.conditions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class HandlerConditions {

    private static final List<ConditionImpl<?>> Conditions = new ArrayList<>();

    public static void registerDefaults(){
        registerCondition(new AptitudeCondition());
        registerCondition(new DimensionCondition());
        registerCondition(new EntityKilledCondition());
        registerCondition(new EntityKilledByCondition());
        registerCondition(new StatCondition());
        registerCondition(new BlockMinedCondition());
        registerCondition(new ItemCraftedCondition());
        registerCondition(new ItemUsedCondition());
        registerCondition(new ItemBrokenCondition());
        registerCondition(new ItemPickedUpCondition());
        registerCondition(new ItemDroppedCondition());
        registerCondition(new AdvancementCondition());
    }

    public static void registerCondition(ConditionImpl<?> condition){
        if(Conditions.stream().anyMatch(c -> c.getConditionName().equalsIgnoreCase(condition.getConditionName()))){
            throw new IllegalArgumentException(String.format("Condition with name %s already exists!", condition.getConditionName()));
        }

        Conditions.add(condition);
    }

    public static Optional<ConditionImpl<?>> getConditionByName(String conditionName){
        return Conditions.stream().filter(c -> c.getConditionName().equalsIgnoreCase(conditionName)).findFirst();
    }



}
