package com.seniors.justlevelingfork.config.models;

import com.seniors.justlevelingfork.JustLevelingFork;
import com.seniors.justlevelingfork.config.conditions.ConditionImpl;
import com.seniors.justlevelingfork.handler.HandlerConditions;
import com.seniors.justlevelingfork.registry.title.Title;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
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
            EComparator comparator;

            try {
                comparator = EComparator.valueOf(split[2].toUpperCase());
            } catch (IllegalArgumentException e) {
                JustLevelingFork.getLOGGER().error(">> Error! Title {} have a wrong formatted condition. (Comparator)", TitleId);
                continue;
            }

            Optional<ConditionImpl<?>> conditionImpl = HandlerConditions.getConditionByName(split[0]);
            if(conditionImpl.isEmpty()){
                JustLevelingFork.getLOGGER().error(">> Error! Title {} have a wrong formatted condition. (Condition type or Comparator)", TitleId);
                continue;
            }

            conditionImpl.get().ProcessVariable(split[1], serverPlayer);
            if(conditionImpl.get().MeetCondition(split[3], comparator)){
                passedConditions++;
            }
        }

        return passedConditions == Conditions.size();
    }

    public RegistryObject<Title> registry(DeferredRegister<Title> TITLES) {
        _title = register(TitleId, Default);
        return TITLES.register(TitleId, () -> _title);
    }

    private Title register(String name, boolean requirement) {
        ResourceLocation key = new ResourceLocation(JustLevelingFork.MOD_ID, name);
        return new Title(key, requirement, this.HideRequirements);
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