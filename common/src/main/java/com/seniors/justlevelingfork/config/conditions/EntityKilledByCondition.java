package com.seniors.justlevelingfork.config.conditions;

import com.seniors.justlevelingfork.JustLevelingFork;
import com.seniors.justlevelingfork.config.models.TitleModel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.EntityType;

public class EntityKilledByCondition extends ConditionImpl<Integer> {

    public EntityKilledByCondition(){
        super("EntiyKilledBy");
    }

    @Override
    public void ProcessVariable(String value, ServerPlayer serverPlayer) {
        var entityType = EntityType.byString(value);
        if (entityType.isEmpty()) {
            setProcessedValue(0);
            JustLevelingFork.getLOGGER().error(">> Error! Entity name {} not found!", value);
            return;
        }

        setProcessedValue(serverPlayer.getStats().getValue(Stats.ENTITY_KILLED_BY.get(entityType.get())));
    }

    @Override
    public boolean MeetCondition(String value, TitleModel.EComparator comparator) {
        int parsedValue = Integer.parseInt(value);

        return switch (comparator) {
            case EQUALS -> getProcessedValue().equals(parsedValue);
            case GREATER -> getProcessedValue() > parsedValue;
            case LESS -> getProcessedValue() < parsedValue;
            case GREATER_OR_EQUAL -> getProcessedValue() >= parsedValue;
            case LESS_OR_EQUAL -> getProcessedValue() <= parsedValue;
            default -> false;
        };
    }
}
