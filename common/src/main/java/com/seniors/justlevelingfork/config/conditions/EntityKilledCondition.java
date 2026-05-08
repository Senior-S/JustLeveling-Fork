package com.seniors.justlevelingfork.config.conditions;

import com.seniors.justlevelingfork.JustLevelingFork;
import com.seniors.justlevelingfork.config.models.TitleModel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.EntityType;

public class EntityKilledCondition extends ConditionImpl<Integer> {

    public EntityKilledCondition(){
        super("EntityKilled");
    }

    @Override
    public void ProcessVariable(String value, ServerPlayer serverPlayer) {
        var entityType = EntityType.byString(value);
        if (entityType.isEmpty()) {
            JustLevelingFork.getLOGGER().error(">> Error! Entity name {} not found!", value);
            setProcessedValue(0);
            return;
        }

        setProcessedValue(serverPlayer.getStats().getValue(Stats.ENTITY_KILLED.get(entityType.get())));
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
