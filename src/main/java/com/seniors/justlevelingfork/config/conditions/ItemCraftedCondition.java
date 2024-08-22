package com.seniors.justlevelingfork.config.conditions;

import com.seniors.justlevelingfork.JustLevelingFork;
import com.seniors.justlevelingfork.config.models.TitleModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;

import java.util.Optional;

public class ItemCraftedCondition extends ConditionImpl<Integer> {

    public ItemCraftedCondition(){
        super("ItemCrafted");
    }

    @Override
    public void ProcessVariable(String value, ServerPlayer serverPlayer) {
        var optionalStat = Optional.ofNullable(ResourceLocation.tryParse(value.toLowerCase())).flatMap(Stats.ITEM_CRAFTED.getRegistry()::getOptional).map(Stats.ITEM_CRAFTED::get);
        if (optionalStat.isEmpty()) {
            JustLevelingFork.getLOGGER().error(">> Error! Item name {} not found!", value);
            setProcessedValue(0);
            return;
        }

        setProcessedValue(serverPlayer.getStats().getValue(Stats.ITEM_CRAFTED, optionalStat.get().getValue()));
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
