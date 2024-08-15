package com.seniors.justlevelingfork.config.models;

import com.seniors.justlevelingfork.JustLevelingFork;
import com.seniors.justlevelingfork.common.capability.AptitudeCapability;
import com.seniors.justlevelingfork.registry.RegistryAptitudes;
import com.seniors.justlevelingfork.registry.title.Title;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TitleModel {
    public String TitleId;

    public List<String> Conditions = new ArrayList<>();

    public boolean Default;

    public Boolean HideRequirements = false;

    private transient Title _title;

    public Title getTitle() {
        return _title;
    }

    public TitleModel() {
        TitleId = "rookie";
        Conditions = new ArrayList<>();
        Default = true;
        HideRequirements = false;
    }

    public TitleModel(String titleID, List<String> conditions, boolean isDefault) {
        TitleId = titleID;
        Conditions = conditions;
        Default = isDefault;
        HideRequirements = false;
    }

    public TitleModel(String titleID, List<String> conditions, boolean isDefault, boolean hideRequirements) {
        TitleId = titleID;
        Conditions = conditions;
        Default = isDefault;
        HideRequirements = hideRequirements;
    }

    @Override
    public String toString() {
        return String.format("%s:%s:%s", TitleId, String.join("=", Conditions), Default);
    }

    public boolean CheckRequirements(ServerPlayer serverPlayer) {

        // If the title should be given by default then lets ignore conditions.
        if (Default) return true;

        byte passedConditions = 0;
        for (String condition : Conditions) {
            String[] split = condition.split("/");

            if (split.length != 4) {
                JustLevelingFork.getLOGGER().error(">> Error! Title {} have a wrong formatted condition. (General)", TitleId);
                continue;
            }
            EConditionType conditionType;
            EComparator comparator;
            int value = 0;

            try {
                conditionType = EConditionType.valueOf(CapitalizeString(split[0]));
                comparator = EComparator.valueOf(split[2].toUpperCase());
                if (conditionType != EConditionType.Special) {
                    value = Integer.parseInt(split[3]);
                }
            } catch (NumberFormatException e) {
                JustLevelingFork.getLOGGER().error(">> Error! Title {} have a wrong formatted condition. (Value)", TitleId);
                continue;
            } catch (IllegalArgumentException e) {
                JustLevelingFork.getLOGGER().error(">> Error! Title {} have a wrong formatted condition. (Condition type or Comparator)", TitleId);
                continue;
            }

            switch (conditionType) {
                case Aptitude:
                    try {
                        EAptitude aptitude = EAptitude.valueOf(CapitalizeString(split[1]));
                        int aptitudeLevel = AptitudeCapability.get(serverPlayer).getAptitudeLevel(RegistryAptitudes.getAptitude(aptitude.toString()));

                        if (Compare(aptitudeLevel, value, comparator)) {
                            passedConditions++;
                        }
                    } catch (IllegalArgumentException e) {
                        JustLevelingFork.getLOGGER().error(">> Error! Title {} have a wrong formatted condition. (Aptitude Name)", TitleId);
                    }
                    break;
                case Stat:
                    var optionalStat = Optional.ofNullable(ResourceLocation.tryParse(split[1].toLowerCase())).flatMap(Stats.CUSTOM.getRegistry()::getOptional).map(Stats.CUSTOM::get);
                    if (optionalStat.isEmpty()) {
                        JustLevelingFork.getLOGGER().error(">> Error! Title {} have a wrong formatted condition. (Stat name)", TitleId);
                        break;
                    }

                    int statValue = serverPlayer.getStats().getValue(Stats.CUSTOM, optionalStat.get().getValue());

                    if (Compare(statValue, value, comparator)) {
                        passedConditions++;
                    }
                    break;
                case EntityKilled:
                    var entityType = EntityType.byString(split[1].toLowerCase());
                    if (entityType.isEmpty()) {
                        JustLevelingFork.getLOGGER().error(">> Error! Title {} have a wrong formatted condition. (Entity name)", TitleId);
                        break;
                    }

                    int entityKilledValue = serverPlayer.getStats().getValue(Stats.ENTITY_KILLED.get(entityType.get()));

                    if (Compare(entityKilledValue, value, comparator)) {
                        passedConditions++;
                    }
                    break;
                case Special:
                    String dimension = split[3];

                    if(serverPlayer.level().dimension().location().toString().equalsIgnoreCase(dimension)){
                        passedConditions++;
                    }
                    break;
            }
        }

        return passedConditions == Conditions.size();
    }

    public RegistryObject<Title> Registry(DeferredRegister<Title> TITLES) {
        _title = register(TitleId, Default);
        return TITLES.register(TitleId, () -> _title);
    }

    private Title register(String name, boolean requirement) {
        ResourceLocation key = new ResourceLocation(JustLevelingFork.MOD_ID, name);
        return new Title(key, requirement, this.HideRequirements);
    }

    private String CapitalizeString(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    private boolean Compare(int a, int b, EComparator comparator) {
        return switch (comparator) {
            case EQUALS -> a == b;
            case GREATER -> a > b;
            case LESS -> a < b;
            case GREATER_OR_EQUAL -> a >= b;
            case LESS_OR_EQUAL -> a <= b;
            default -> false;
        };
    }

    public enum EConditionType {

        Aptitude,
        Stat,
        EntityKilled,
        Special
    }

    public enum EComparator {
        EQUALS,
        GREATER,
        LESS,
        GREATER_OR_EQUAL,
        LESS_OR_EQUAL
    }

}