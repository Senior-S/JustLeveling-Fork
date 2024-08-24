package com.seniors.justlevelingfork.registry;

import com.seniors.justlevelingfork.JustLevelingFork;
import com.seniors.justlevelingfork.client.core.Value;
import com.seniors.justlevelingfork.client.core.ValueType;
import com.seniors.justlevelingfork.handler.HandlerCommonConfig;
import com.seniors.justlevelingfork.handler.HandlerResources;
import com.seniors.justlevelingfork.registry.aptitude.Aptitude;
import com.seniors.justlevelingfork.registry.skills.Skill;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;
import java.util.stream.Collectors;


public class RegistrySkills {
    public static final ResourceKey<Registry<Skill>> SKILLS_KEY = ResourceKey.createRegistryKey(new ResourceLocation(JustLevelingFork.MOD_ID, "skills"));
    public static final DeferredRegister<Skill> SKILLS;
    public static Supplier<IForgeRegistry<Skill>> SKILLS_REGISTRY;
    public static final RegistryObject<Skill> ONE_HANDED;
    public static final RegistryObject<Skill> FIGHTING_SPIRIT;
    public static final RegistryObject<Skill> BERSERKER;
    public static final RegistryObject<Skill> ATHLETICS;
    public static final RegistryObject<Skill> TURTLE_SHIELD;
    public static final RegistryObject<Skill> LION_HEART;
    public static final RegistryObject<Skill> QUICK_REPOSITION;
    public static final RegistryObject<Skill> STEALTH_MASTERY;
    public static final RegistryObject<Skill> CAT_EYES;
    public static final RegistryObject<Skill> SNOW_WALKER;
    public static final RegistryObject<Skill> COUNTER_ATTACK;
    public static final RegistryObject<Skill> DIAMOND_SKIN;
    public static final RegistryObject<Skill> SCHOLAR;
    public static final RegistryObject<Skill> HAGGLER;
    public static final RegistryObject<Skill> ALCHEMY_MANIPULATION;
    public static final RegistryObject<Skill> OBSIDIAN_SMASHER;
    public static final RegistryObject<Skill> TREASURE_HUNTER;
    public static final RegistryObject<Skill> CONVERGENCE;
    public static final RegistryObject<Skill> SAFE_PORT;
    public static final RegistryObject<Skill> LIFE_EATER;
    public static final RegistryObject<Skill> WORMHOLE_STORAGE;
    public static final RegistryObject<Skill> CRITICAL_ROLL;
    public static final RegistryObject<Skill> LUCKY_DROP;
    public static final RegistryObject<Skill> LIMIT_BREAKER;

    private static Skill register(String name, Aptitude aptitude, int requiredLvl, ResourceLocation texture, Value... configValues) {
        ResourceLocation key = new ResourceLocation(JustLevelingFork.MOD_ID, name);
        return new Skill(key, aptitude, requiredLvl, texture, configValues);
    }

    public static void load(IEventBus eventBus) {
        SKILLS.register(eventBus);
    }

    public static Skill getSkill(String skillName) {
        return SKILLS_REGISTRY.get().getValues().stream().collect(Collectors.toMap(Skill::getName, Skill::get)).get(skillName);
    }

    static {
        SKILLS = DeferredRegister.create(SKILLS_KEY, JustLevelingFork.MOD_ID);
        SKILLS_REGISTRY = SKILLS.makeRegistry(Skill.class, () -> {
            return new RegistryBuilder().disableSaving().disableSync();
        });
        ONE_HANDED = SKILLS.register("one_handed", () -> {
            return register("one_handed", RegistryAptitudes.STRENGTH.get(), 10, HandlerResources.ONE_HANDED_SKILL, new Value(ValueType.AMPLIFIER, HandlerCommonConfig.HANDLER.instance().oneHandedAmplifier));
        });
        FIGHTING_SPIRIT = SKILLS.register("fighting_spirit", () -> {
            return register("fighting_spirit", RegistryAptitudes.STRENGTH.get(), 16, HandlerResources.FIGHTING_SPIRIT_SKILL, new Value(ValueType.BOOST, HandlerCommonConfig.HANDLER.instance().fightingSpiritBoost), new Value(ValueType.DURATION, HandlerCommonConfig.HANDLER.instance().fightingSpiritDuration));
        });
        BERSERKER = SKILLS.register("berserker", () -> {
            return register("berserker", RegistryAptitudes.STRENGTH.get(), 30, HandlerResources.BERSERKER_SKILL, new Value(ValueType.PERCENT, HandlerCommonConfig.HANDLER.instance().berserkerPercent));
        });
        ATHLETICS = SKILLS.register("athletics", () -> {
            return register("athletics", RegistryAptitudes.CONSTITUTION.get(), 10, HandlerResources.ATHLETICS_SKILL, new Value(ValueType.MODIFIER, HandlerCommonConfig.HANDLER.instance().athleticsModifier));
        });
        TURTLE_SHIELD = SKILLS.register("turtle_shield", () -> {
            return register("turtle_shield", RegistryAptitudes.CONSTITUTION.get(), 20, HandlerResources.TURTLE_SHIELD_SKILL);
        });
        LION_HEART = SKILLS.register("lion_heart", () -> {
            return register("lion_heart", RegistryAptitudes.CONSTITUTION.get(), 32, HandlerResources.LION_HEART_SKILL, new Value(ValueType.PERCENT, HandlerCommonConfig.HANDLER.instance().lionHeartPercent));
        });
        QUICK_REPOSITION = SKILLS.register("quick_reposition", () -> {
            return register("quick_reposition", RegistryAptitudes.DEXTERITY.get(), 10, HandlerResources.QUICK_REPOSITION_SKILL, new Value(ValueType.BOOST, HandlerCommonConfig.HANDLER.instance().quickRepositionBoost), new Value(ValueType.DURATION, HandlerCommonConfig.HANDLER.instance().quickRepositionDuration));
        });
        STEALTH_MASTERY = SKILLS.register("stealth_mastery", () -> {
            return register("stealth_mastery", RegistryAptitudes.DEXTERITY.get(), 16, HandlerResources.STEALTH_MASTERY_SKILL, new Value(ValueType.PERCENT, HandlerCommonConfig.HANDLER.instance().stealthMasteryUnSneakPercent), new Value(ValueType.PERCENT, HandlerCommonConfig.HANDLER.instance().stealthMasterySneakPercent), new Value(ValueType.MODIFIER, HandlerCommonConfig.HANDLER.instance().stealthMasteryModifier));
        });
        CAT_EYES = SKILLS.register("cat_eyes", () -> {
            return register("cat_eyes", RegistryAptitudes.DEXTERITY.get(), 32, HandlerResources.CAT_EYES_SKILL);
        });
        SNOW_WALKER = SKILLS.register("snow_walker", () -> {
            return register("snow_walker", RegistryAptitudes.DEFENSE.get(), 10, HandlerResources.SNOW_WALKER_SKILL);
        });
        COUNTER_ATTACK = SKILLS.register("counter_attack", () -> {
            return register("counter_attack", RegistryAptitudes.DEFENSE.get(), 18, HandlerResources.COUNTER_ATTACK_SKILL, new Value(ValueType.DURATION, HandlerCommonConfig.HANDLER.instance().counterAttackDuration), new Value(ValueType.PERCENT, HandlerCommonConfig.HANDLER.instance().counterAttackPercent));
        });
        DIAMOND_SKIN = SKILLS.register("diamond_skin", () -> {
            return register("diamond_skin", RegistryAptitudes.DEFENSE.get(), 30, HandlerResources.DIAMOND_SKIN_SKILL, new Value(ValueType.BOOST, HandlerCommonConfig.HANDLER.instance().diamondSkinBoost), new Value(ValueType.AMPLIFIER, HandlerCommonConfig.HANDLER.instance().diamondSkinSneakAmplifier));
        });
        SCHOLAR = SKILLS.register("scholar", () -> {
            return register("scholar", RegistryAptitudes.INTELLIGENCE.get(), 8, HandlerResources.SCHOLAR_SKILL);
        });
        HAGGLER = SKILLS.register("haggler", () -> {
            return register("haggler", RegistryAptitudes.INTELLIGENCE.get(), 16, HandlerResources.HAGGLER_SKILL, new Value(ValueType.PERCENT, HandlerCommonConfig.HANDLER.instance().hagglerPercent));
        });
        ALCHEMY_MANIPULATION = SKILLS.register("alchemy_manipulation", () -> {
            return register("alchemy_manipulation", RegistryAptitudes.INTELLIGENCE.get(), 30, HandlerResources.ALCHEMY_MANIPULATION_SKILL, new Value(ValueType.AMPLIFIER, HandlerCommonConfig.HANDLER.instance().alchemyManipulationAmplifier));
        });
        OBSIDIAN_SMASHER = SKILLS.register("obsidian_smasher", () -> {
            return register("obsidian_smasher", RegistryAptitudes.BUILDING.get(), 12, HandlerResources.OBSIDIAN_SMASHER_SKILL, new Value(ValueType.MODIFIER, HandlerCommonConfig.HANDLER.instance().obsidianSmasherModifier));
        });
        TREASURE_HUNTER = SKILLS.register("treasure_hunter", () -> {
            return register("treasure_hunter", RegistryAptitudes.BUILDING.get(), 20, HandlerResources.TREASURE_HUNTER_SKILL, new Value(ValueType.PROBABILITY, HandlerCommonConfig.HANDLER.instance().treasureHunterProbability));
        });
        CONVERGENCE = SKILLS.register("convergence", () -> {
            return register("convergence", RegistryAptitudes.BUILDING.get(), 30, HandlerResources.CONVERGENCE_SKILL, new Value(ValueType.PROBABILITY, HandlerCommonConfig.HANDLER.instance().convergenceProbability));
        });
        SAFE_PORT = SKILLS.register("safe_port", () -> {
            return register("safe_port", RegistryAptitudes.MAGIC.get(), 12, HandlerResources.SAFE_PORT_SKILL);
        });
        LIFE_EATER = SKILLS.register("life_eater", () -> {
            return register("life_eater", RegistryAptitudes.MAGIC.get(), 18, HandlerResources.LIFE_EATER_SKILL, new Value(ValueType.AMPLIFIER, HandlerCommonConfig.HANDLER.instance().lifeEaterModifier));
        });
        WORMHOLE_STORAGE = SKILLS.register("wormhole_storage", () -> {
            return register("wormhole_storage", RegistryAptitudes.MAGIC.get(), 32, HandlerResources.WORMHOLE_STORAGE_SKILL);
        });
        CRITICAL_ROLL = SKILLS.register("critical_roll", () -> {
            return register("critical_roll", RegistryAptitudes.LUCK.get(), 12, HandlerResources.CRITICAL_ROLL_SKILL, new Value(ValueType.MODIFIER, HandlerCommonConfig.HANDLER.instance().criticalRoll6Modifier), new Value(ValueType.PROBABILITY, HandlerCommonConfig.HANDLER.instance().criticalRoll1Probability));
        });
        LUCKY_DROP = SKILLS.register("lucky_drop", () -> {
            return register("lucky_drop", RegistryAptitudes.LUCK.get(), 22, HandlerResources.LUCKY_DROP_SKILL, new Value(ValueType.PROBABILITY, HandlerCommonConfig.HANDLER.instance().luckyDropProbability), new Value(ValueType.MODIFIER, HandlerCommonConfig.HANDLER.instance().luckyDropModifier));
        });
        LIMIT_BREAKER = SKILLS.register("limit_breaker", () -> {
            return register("limit_breaker", RegistryAptitudes.LUCK.get(), 32, HandlerResources.LIMIT_BREAKER_SKILL, new Value(ValueType.PROBABILITY, HandlerCommonConfig.HANDLER.instance().limitBreakerProbability), new Value(ValueType.AMPLIFIER, HandlerCommonConfig.HANDLER.instance().limitBreakerAmplifier));
        });
    }
}


