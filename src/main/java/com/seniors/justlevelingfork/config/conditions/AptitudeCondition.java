package com.seniors.justlevelingfork.config.conditions;

import com.seniors.justlevelingfork.common.capability.AptitudeCapability;
import com.seniors.justlevelingfork.config.models.EAptitude;
import com.seniors.justlevelingfork.config.models.TitleModel;
import com.seniors.justlevelingfork.registry.RegistryAptitudes;
import net.minecraft.server.level.ServerPlayer;
import org.apache.commons.lang3.StringUtils;

public class AptitudeCondition extends ConditionImpl<Integer> {

    public AptitudeCondition() {
        super("Aptitude");
    }

    @Override
    public void ProcessVariable(String value, ServerPlayer serverPlayer) {
        EAptitude aptitude = EAptitude.valueOf(StringUtils.capitalize(value));
        int aptitudeLevel = AptitudeCapability.get(serverPlayer).getAptitudeLevel(RegistryAptitudes.getAptitude(aptitude.toString()));

        setProcessedValue(aptitudeLevel);
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
