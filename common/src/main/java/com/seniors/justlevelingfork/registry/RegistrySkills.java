package com.seniors.justlevelingfork.registry;

import com.seniors.justlevelingfork.JustLevelingFork;
import com.seniors.justlevelingfork.client.core.Value;
import com.seniors.justlevelingfork.client.core.ValueType;
import com.seniors.justlevelingfork.handler.HandlerCommonConfig;
import com.seniors.justlevelingfork.handler.HandlerResources;
import com.seniors.justlevelingfork.registry.aptitude.Aptitude;
import com.seniors.justlevelingfork.registry.skills.Skill;
import com.mojang.serialization.Lifecycle;
import net.minecraft.core.MappedRegistry;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;


public class RegistrySkills {
    public static final ResourceKey<Registry<Skill>> SKILLS_KEY = ResourceKey.createRegistryKey(RegistryItems.id("skills"));
    public static final Registry<Skill> SKILLS = new MappedRegistry<>(SKILLS_KEY, Lifecycle.stable());
    public static final FabricRegistryView<Skill> SKILLS_REGISTRY = new FabricRegistryView<>(SKILLS);
    private static boolean migratedLegacyDisabledSkills = false;

    public static final FabricRegistryRef<Skill> ONE_HANDED = register("one_handed", RegistryAptitudes.STRENGTH.get(), HandlerCommonConfig.HANDLER.instance().oneHandedRequiredLevel, HandlerResources.ONE_HANDED_SKILL, new Value(ValueType.AMPLIFIER, HandlerCommonConfig.HANDLER.instance().oneHandedAmplifier));

    public static final FabricRegistryRef<Skill> FIGHTING_SPIRIT = register("fighting_spirit", RegistryAptitudes.STRENGTH.get(), HandlerCommonConfig.HANDLER.instance().fightingSpiritRequiredLevel, HandlerResources.FIGHTING_SPIRIT_SKILL, new Value(ValueType.BOOST, HandlerCommonConfig.HANDLER.instance().fightingSpiritBoost), new Value(ValueType.DURATION, HandlerCommonConfig.HANDLER.instance().fightingSpiritDuration));

    public static final FabricRegistryRef<Skill> BERSERKER = register("berserker", RegistryAptitudes.STRENGTH.get(), HandlerCommonConfig.HANDLER.instance().berserkerRequiredLevel, HandlerResources.BERSERKER_SKILL, new Value(ValueType.PERCENT, HandlerCommonConfig.HANDLER.instance().berserkerPercent));

    public static final FabricRegistryRef<Skill> ATHLETICS = register("athletics", RegistryAptitudes.CONSTITUTION.get(), HandlerCommonConfig.HANDLER.instance().athleticsRequiredLevel, HandlerResources.ATHLETICS_SKILL, new Value(ValueType.MODIFIER, HandlerCommonConfig.HANDLER.instance().athleticsModifier));

    public static final FabricRegistryRef<Skill> TURTLE_SHIELD = register("turtle_shield", RegistryAptitudes.CONSTITUTION.get(), HandlerCommonConfig.HANDLER.instance().turtleShieldRequiredLevel, HandlerResources.TURTLE_SHIELD_SKILL);

    public static final FabricRegistryRef<Skill> LION_HEART = register("lion_heart", RegistryAptitudes.CONSTITUTION.get(), HandlerCommonConfig.HANDLER.instance().lionHeartRequiredLevel, HandlerResources.LION_HEART_SKILL, new Value(ValueType.PERCENT, HandlerCommonConfig.HANDLER.instance().lionHeartPercent));

    public static final FabricRegistryRef<Skill> QUICK_REPOSITION = register("quick_reposition", RegistryAptitudes.DEXTERITY.get(), HandlerCommonConfig.HANDLER.instance().quickRepositionRequiredLevel, HandlerResources.QUICK_REPOSITION_SKILL, new Value(ValueType.BOOST, HandlerCommonConfig.HANDLER.instance().quickRepositionBoost), new Value(ValueType.DURATION, HandlerCommonConfig.HANDLER.instance().quickRepositionDuration));

    public static final FabricRegistryRef<Skill> STEALTH_MASTERY = register("stealth_mastery", RegistryAptitudes.DEXTERITY.get(), HandlerCommonConfig.HANDLER.instance().stealthMasteryRequiredLevel, HandlerResources.STEALTH_MASTERY_SKILL, new Value(ValueType.PERCENT, HandlerCommonConfig.HANDLER.instance().stealthMasteryUnSneakPercent), new Value(ValueType.PERCENT, HandlerCommonConfig.HANDLER.instance().stealthMasterySneakPercent), new Value(ValueType.MODIFIER, HandlerCommonConfig.HANDLER.instance().stealthMasteryModifier));

    public static final FabricRegistryRef<Skill> CAT_EYES = register("cat_eyes", RegistryAptitudes.DEXTERITY.get(), HandlerCommonConfig.HANDLER.instance().catEyesRequiredLevel, HandlerResources.CAT_EYES_SKILL);

    public static final FabricRegistryRef<Skill> SNOW_WALKER = register("snow_walker", RegistryAptitudes.DEFENSE.get(), HandlerCommonConfig.HANDLER.instance().snowWalkerRequiredLevel, HandlerResources.SNOW_WALKER_SKILL);

    public static final FabricRegistryRef<Skill> COUNTER_ATTACK = register("counter_attack", RegistryAptitudes.DEFENSE.get(), HandlerCommonConfig.HANDLER.instance().counterattackRequiredLevel, HandlerResources.COUNTER_ATTACK_SKILL, new Value(ValueType.DURATION, HandlerCommonConfig.HANDLER.instance().counterAttackDuration), new Value(ValueType.PERCENT, HandlerCommonConfig.HANDLER.instance().counterAttackPercent));

    public static final FabricRegistryRef<Skill> DIAMOND_SKIN = register("diamond_skin", RegistryAptitudes.DEFENSE.get(), HandlerCommonConfig.HANDLER.instance().diamondSkinRequiredLevel, HandlerResources.DIAMOND_SKIN_SKILL, new Value(ValueType.BOOST, HandlerCommonConfig.HANDLER.instance().diamondSkinBoost), new Value(ValueType.AMPLIFIER, HandlerCommonConfig.HANDLER.instance().diamondSkinSneakAmplifier));

    public static final FabricRegistryRef<Skill> SCHOLAR = register("scholar", RegistryAptitudes.INTELLIGENCE.get(), HandlerCommonConfig.HANDLER.instance().scholarRequiredLevel, HandlerResources.SCHOLAR_SKILL);

    public static final FabricRegistryRef<Skill> HAGGLER = register("haggler", RegistryAptitudes.INTELLIGENCE.get(), HandlerCommonConfig.HANDLER.instance().hagglerRequiredLevel, HandlerResources.HAGGLER_SKILL, new Value(ValueType.PERCENT, HandlerCommonConfig.HANDLER.instance().hagglerPercent));

    public static final FabricRegistryRef<Skill> ALCHEMY_MANIPULATION = register("alchemy_manipulation", RegistryAptitudes.INTELLIGENCE.get(), HandlerCommonConfig.HANDLER.instance().alchemyManipulationRequiredLevel, HandlerResources.ALCHEMY_MANIPULATION_SKILL, new Value(ValueType.AMPLIFIER, HandlerCommonConfig.HANDLER.instance().alchemyManipulationAmplifier));

    public static final FabricRegistryRef<Skill> OBSIDIAN_SMASHER = register("obsidian_smasher", RegistryAptitudes.BUILDING.get(), HandlerCommonConfig.HANDLER.instance().obsidianSmasherRequiredLevel, HandlerResources.OBSIDIAN_SMASHER_SKILL, new Value(ValueType.MODIFIER, HandlerCommonConfig.HANDLER.instance().obsidianSmasherModifier));

    public static final FabricRegistryRef<Skill> TREASURE_HUNTER = register("treasure_hunter", RegistryAptitudes.BUILDING.get(), HandlerCommonConfig.HANDLER.instance().treasureHunterRequiredLevel, HandlerResources.TREASURE_HUNTER_SKILL, new Value(ValueType.PROBABILITY, HandlerCommonConfig.HANDLER.instance().treasureHunterProbability));

    public static final FabricRegistryRef<Skill> CONVERGENCE = register("convergence", RegistryAptitudes.BUILDING.get(), HandlerCommonConfig.HANDLER.instance().convergenceRequiredLevel, HandlerResources.CONVERGENCE_SKILL, new Value(ValueType.PROBABILITY, HandlerCommonConfig.HANDLER.instance().convergenceProbability));

    public static final FabricRegistryRef<Skill> SAFE_PORT = register("safe_port", RegistryAptitudes.MAGIC.get(), HandlerCommonConfig.HANDLER.instance().safePortRequiredLevel, HandlerResources.SAFE_PORT_SKILL);

    public static final FabricRegistryRef<Skill> LIFE_EATER = register("life_eater", RegistryAptitudes.MAGIC.get(), HandlerCommonConfig.HANDLER.instance().lifeEaterRequiredLevel, HandlerResources.LIFE_EATER_SKILL, new Value(ValueType.AMPLIFIER, HandlerCommonConfig.HANDLER.instance().lifeEaterModifier));

    public static final FabricRegistryRef<Skill> WORMHOLE_STORAGE = register("wormhole_storage", RegistryAptitudes.MAGIC.get(), HandlerCommonConfig.HANDLER.instance().wornholeStorageRequiredLevel, HandlerResources.WORMHOLE_STORAGE_SKILL);

    public static final FabricRegistryRef<Skill> CRITICAL_ROLL = register("critical_roll", RegistryAptitudes.LUCK.get(), HandlerCommonConfig.HANDLER.instance().criticalRollRequiredLevel, HandlerResources.CRITICAL_ROLL_SKILL, new Value(ValueType.MODIFIER, HandlerCommonConfig.HANDLER.instance().criticalRoll6Modifier), new Value(ValueType.PROBABILITY, HandlerCommonConfig.HANDLER.instance().criticalRoll1Probability));

    public static final FabricRegistryRef<Skill> LUCKY_DROP = register("lucky_drop", RegistryAptitudes.LUCK.get(), HandlerCommonConfig.HANDLER.instance().luckyDropRequiredLevel, HandlerResources.LUCKY_DROP_SKILL, new Value(ValueType.PROBABILITY, HandlerCommonConfig.HANDLER.instance().luckyDropProbability), new Value(ValueType.MODIFIER, HandlerCommonConfig.HANDLER.instance().luckyDropModifier));

    public static final FabricRegistryRef<Skill> LIMIT_BREAKER = register("limit_breaker", RegistryAptitudes.LUCK.get(), HandlerCommonConfig.HANDLER.instance().limitBreakerRequiredLevel, HandlerResources.LIMIT_BREAKER_SKILL, new Value(ValueType.PROBABILITY, HandlerCommonConfig.HANDLER.instance().limitBreakerProbability), new Value(ValueType.AMPLIFIER, HandlerCommonConfig.HANDLER.instance().limitBreakerAmplifier));

    private static FabricRegistryRef<Skill> register(String name, Aptitude aptitude, int requiredLvl, ResourceLocation texture, Value... configValues) {
        ResourceLocation key = RegistryItems.id(name);
        requiredLvl = migrateLegacyDisabledSkill(key, requiredLvl);
        return new FabricRegistryRef<>(Registry.register(SKILLS, key, new Skill(key, aptitude, requiredLvl, texture, configValues)));
    }

    private static int migrateLegacyDisabledSkill(ResourceLocation key, int requiredLvl) {
        if (requiredLvl >= 0) return requiredLvl;

        HandlerCommonConfig config = HandlerCommonConfig.HANDLER.instance();
        List<String> disabledSkills = new ArrayList<>(config.disabledSkills);
        boolean alreadyDisabled = disabledSkills.stream()
                .map(RegistrySkills::normalizeConfiguredName)
                .anyMatch(disabledSkill -> disabledSkill.equals(key.toString()) || disabledSkill.equals(key.getPath()));

        if (!alreadyDisabled) {
            disabledSkills.add(key.getPath());
            config.disabledSkills = disabledSkills;
        }

        setLegacyRequiredLevelToZero(config, key.getPath());
        migratedLegacyDisabledSkills = true;
        return 0;
    }

    private static void setLegacyRequiredLevelToZero(HandlerCommonConfig config, String skillName) {
        switch (skillName) {
            case "one_handed" -> config.oneHandedRequiredLevel = 0;
            case "fighting_spirit" -> config.fightingSpiritRequiredLevel = 0;
            case "berserker" -> config.berserkerRequiredLevel = 0;
            case "athletics" -> config.athleticsRequiredLevel = 0;
            case "turtle_shield" -> config.turtleShieldRequiredLevel = 0;
            case "lion_heart" -> config.lionHeartRequiredLevel = 0;
            case "quick_reposition" -> config.quickRepositionRequiredLevel = 0;
            case "stealth_mastery" -> config.stealthMasteryRequiredLevel = 0;
            case "cat_eyes" -> config.catEyesRequiredLevel = 0;
            case "snow_walker" -> config.snowWalkerRequiredLevel = 0;
            case "counter_attack" -> config.counterattackRequiredLevel = 0;
            case "diamond_skin" -> config.diamondSkinRequiredLevel = 0;
            case "scholar" -> config.scholarRequiredLevel = 0;
            case "haggler" -> config.hagglerRequiredLevel = 0;
            case "alchemy_manipulation" -> config.alchemyManipulationRequiredLevel = 0;
            case "obsidian_smasher" -> config.obsidianSmasherRequiredLevel = 0;
            case "treasure_hunter" -> config.treasureHunterRequiredLevel = 0;
            case "convergence" -> config.convergenceRequiredLevel = 0;
            case "safe_port" -> config.safePortRequiredLevel = 0;
            case "life_eater" -> config.lifeEaterRequiredLevel = 0;
            case "wormhole_storage" -> config.wornholeStorageRequiredLevel = 0;
            case "critical_roll" -> config.criticalRollRequiredLevel = 0;
            case "lucky_drop" -> config.luckyDropRequiredLevel = 0;
            case "limit_breaker" -> config.limitBreakerRequiredLevel = 0;
        }
    }

    public static void load() {
        if (migratedLegacyDisabledSkills) {
            HandlerCommonConfig.HANDLER.save();
        }
    }

    public static Skill getSkill(String skillName) {
        return SKILLS_REGISTRY.getValues().stream().collect(Collectors.toMap(Skill::getName, Skill::get)).get(skillName);
    }

    public static boolean isEnabled(Skill skill) {
        return skill != null && isEnabled(skill.key);
    }

    public static boolean isEnabled(ResourceLocation key) {
        return HandlerCommonConfig.HANDLER.instance().disabledSkills.stream()
                .map(RegistrySkills::normalizeConfiguredName)
                .noneMatch(disabledSkill -> disabledSkill.equals(key.toString()) || disabledSkill.equals(key.getPath()));
    }

    private static String normalizeConfiguredName(String name) {
        return name == null ? "" : name.trim().toLowerCase(Locale.ROOT);
    }
}


