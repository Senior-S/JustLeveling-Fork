package com.seniors.justlevelingfork.registry;

import com.seniors.justlevelingfork.JustLevelingFork;
import com.seniors.justlevelingfork.client.core.Value;
import com.seniors.justlevelingfork.client.core.ValueType;
import com.seniors.justlevelingfork.handler.HandlerConfigCommon;
import com.seniors.justlevelingfork.handler.HandlerResources;
import com.seniors.justlevelingfork.registry.aptitude.Aptitude;
import com.seniors.justlevelingfork.registry.skills.Skill;

import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;
import net.minecraftforge.registries.RegistryObject;


public class RegistrySkills {
    public static final ResourceKey<Registry<Skill>> SKILLS_KEY = ResourceKey.createRegistryKey(new ResourceLocation(JustLevelingFork.MOD_ID, "skills"));
    public static final DeferredRegister<Skill> SKILLS = DeferredRegister.create(SKILLS_KEY, JustLevelingFork.MOD_ID);
    public static final Supplier<IForgeRegistry<Skill>> SKILLS_REGISTRY = SKILLS.makeRegistry(() -> new RegistryBuilder<Skill>().disableSaving());

    public static final RegistryObject<Skill> ONE_HANDED = SKILLS.register("one_handed", () -> register("one_handed", RegistryAptitudes.STRENGTH.get(), 10, HandlerResources.ONE_HANDED_SKILL, new Value(ValueType.AMPLIFIER, HandlerConfigCommon.oneHandedAmplifier)));


    public static final RegistryObject<Skill> FIGHTING_SPIRIT = SKILLS.register("fighting_spirit", () -> register("fighting_spirit", RegistryAptitudes.STRENGTH.get(), 16, HandlerResources.FIGHTING_SPIRIT_SKILL, new Value(ValueType.BOOST, HandlerConfigCommon.fightingSpiritBoost), new Value(ValueType.DURATION, HandlerConfigCommon.fightingSpiritDuration)));


    public static final RegistryObject<Skill> BERSERKER = SKILLS.register("berserker", () -> register("berserker", RegistryAptitudes.STRENGTH.get(), 30, HandlerResources.BERSERKER_SKILL, new Value(ValueType.PERCENT, HandlerConfigCommon.berserkerPercent)));


    public static final RegistryObject<Skill> ATHLETICS = SKILLS.register("athletics", () -> register("athletics", RegistryAptitudes.CONSTITUTION.get(), 10, HandlerResources.ATHLETICS_SKILL, new Value(ValueType.MODIFIER, HandlerConfigCommon.athleticsModifier)));


    public static final RegistryObject<Skill> TURTLE_SHIELD = SKILLS.register("turtle_shield", () -> register("turtle_shield", RegistryAptitudes.CONSTITUTION.get(), 20, HandlerResources.TURTLE_SHIELD_SKILL));


    public static final RegistryObject<Skill> LION_HEART = SKILLS.register("lion_heart", () -> register("lion_heart", RegistryAptitudes.CONSTITUTION.get(), 32, HandlerResources.LION_HEART_SKILL, new Value(ValueType.PERCENT, HandlerConfigCommon.lionHeartPercent)));


    public static final RegistryObject<Skill> QUICK_REPOSITION = SKILLS.register("quick_reposition", () -> register("quick_reposition", RegistryAptitudes.DEXTERITY.get(), 10, HandlerResources.QUICK_REPOSITION_SKILL, new Value(ValueType.BOOST, HandlerConfigCommon.quickRepositionBoost), new Value(ValueType.DURATION, HandlerConfigCommon.quickRepositionDuration)));


    public static final RegistryObject<Skill> STEALTH_MASTERY = SKILLS.register("stealth_mastery", () -> register("stealth_mastery", RegistryAptitudes.DEXTERITY.get(), 16, HandlerResources.STEALTH_MASTERY_SKILL, new Value(ValueType.PERCENT, HandlerConfigCommon.stealthMasteryUnSneakPercent), new Value(ValueType.PERCENT, HandlerConfigCommon.stealthMasterySneakPercent), new Value(ValueType.MODIFIER, HandlerConfigCommon.stealthMasteryModifier)));


    public static final RegistryObject<Skill> CAT_EYES = SKILLS.register("cat_eyes", () -> register("cat_eyes", RegistryAptitudes.DEXTERITY.get(), 32, HandlerResources.CAT_EYES_SKILL));


    public static final RegistryObject<Skill> SNOW_WALKER = SKILLS.register("snow_walker", () -> register("snow_walker", RegistryAptitudes.DEFENSE.get(), 10, HandlerResources.SNOW_WALKER_SKILL));


    public static final RegistryObject<Skill> COUNTER_ATTACK = SKILLS.register("counter_attack", () -> register("counter_attack", RegistryAptitudes.DEFENSE.get(), 18, HandlerResources.COUNTER_ATTACK_SKILL, new Value(ValueType.DURATION, HandlerConfigCommon.counterAttackDuration), new Value(ValueType.PERCENT, HandlerConfigCommon.counterAttackPercent)));


    public static final RegistryObject<Skill> DIAMOND_SKIN = SKILLS.register("diamond_skin", () -> register("diamond_skin", RegistryAptitudes.DEFENSE.get(), 30, HandlerResources.DIAMOND_SKIN_SKILL, new Value(ValueType.BOOST, HandlerConfigCommon.diamondSkinBoost), new Value(ValueType.AMPLIFIER, HandlerConfigCommon.diamondSkinSneakAmplifier)));


    public static final RegistryObject<Skill> SCHOLAR = SKILLS.register("scholar", () -> register("scholar", RegistryAptitudes.INTELLIGENCE.get(), 8, HandlerResources.SCHOLAR_SKILL));


    public static final RegistryObject<Skill> HAGGLER = SKILLS.register("haggler", () -> register("haggler", RegistryAptitudes.INTELLIGENCE.get(), 16, HandlerResources.HAGGLER_SKILL, new Value(ValueType.PERCENT, HandlerConfigCommon.hagglerPercent)));


    public static final RegistryObject<Skill> ALCHEMY_MANIPULATION = SKILLS.register("alchemy_manipulation", () -> register("alchemy_manipulation", RegistryAptitudes.INTELLIGENCE.get(), 30, HandlerResources.ALCHEMY_MANIPULATION_SKILL, new Value(ValueType.AMPLIFIER, HandlerConfigCommon.alchemyManipulationAmplifier)));


    public static final RegistryObject<Skill> OBSIDIAN_SMASHER = SKILLS.register("obsidian_smasher", () -> register("obsidian_smasher", RegistryAptitudes.BUILDING.get(), 12, HandlerResources.OBSIDIAN_SMASHER_SKILL, new Value(ValueType.MODIFIER, HandlerConfigCommon.obsidianSmasherModifier)));


    public static final RegistryObject<Skill> TREASURE_HUNTER = SKILLS.register("treasure_hunter", () -> register("treasure_hunter", RegistryAptitudes.BUILDING.get(), 20, HandlerResources.TREASURE_HUNTER_SKILL, new Value(ValueType.PROBABILITY, HandlerConfigCommon.treasureHunterProbability)));


    public static final RegistryObject<Skill> CONVERGENCE = SKILLS.register("convergence", () -> register("convergence", RegistryAptitudes.BUILDING.get(), 30, HandlerResources.CONVERGENCE_SKILL, new Value(ValueType.PROBABILITY, HandlerConfigCommon.convergenceProbability)));


    public static final RegistryObject<Skill> SAFE_PORT = SKILLS.register("safe_port", () -> register("safe_port", RegistryAptitudes.MAGIC.get(), 12, HandlerResources.SAFE_PORT_SKILL));


    public static final RegistryObject<Skill> LIFE_EATER = SKILLS.register("life_eater", () -> register("life_eater", RegistryAptitudes.MAGIC.get(), 18, HandlerResources.LIFE_EATER_SKILL, new Value(ValueType.AMPLIFIER, HandlerConfigCommon.lifeEaterAmplifier)));


    public static final RegistryObject<Skill> WORMHOLE_STORAGE = SKILLS.register("wormhole_storage", () -> register("wormhole_storage", RegistryAptitudes.MAGIC.get(), 32, HandlerResources.WORMHOLE_STORAGE_SKILL));


    public static final RegistryObject<Skill> CRITICAL_ROLL = SKILLS.register("critical_roll", () -> register("critical_roll", RegistryAptitudes.LUCK.get(), 12, HandlerResources.CRITICAL_ROLL_SKILL, new Value(ValueType.MODIFIER, HandlerConfigCommon.criticalRoll6Modifier), new Value(ValueType.PROBABILITY, HandlerConfigCommon.criticalRoll1Probability)));


    public static final RegistryObject<Skill> LUCKY_DROP = SKILLS.register("lucky_drop", () -> register("lucky_drop", RegistryAptitudes.LUCK.get(), 22, HandlerResources.LUCKY_DROP_SKILL, new Value(ValueType.PROBABILITY, HandlerConfigCommon.luckyDropProbability), new Value(ValueType.MODIFIER, HandlerConfigCommon.luckyDropModifier)));


    public static final RegistryObject<Skill> LIMIT_BREAKER = SKILLS.register("limit_breaker", () -> register("limit_breaker", RegistryAptitudes.LUCK.get(), 32, HandlerResources.LIMIT_BREAKER_SKILL, new Value(ValueType.PROBABILITY, HandlerConfigCommon.limitBreakerProbability), new Value(ValueType.AMPLIFIER, HandlerConfigCommon.limitBreakerAmplifier)));


    private static Skill register(String name, Aptitude aptitude, int requiredLvl, ResourceLocation texture, Value... configValues) {
        ResourceLocation key = new ResourceLocation(JustLevelingFork.MOD_ID, name);
        return new Skill(key, aptitude, requiredLvl, texture, configValues);
    }

    public static void load(IEventBus eventBus) {
        SKILLS.register(eventBus);
    }

    public static Skill getSkill(String skillName) {
        return (Skill) ((Map) ((IForgeRegistry) SKILLS_REGISTRY.get()).getValues().stream().collect(Collectors.toMap(Skill::getName, Skill::get))).get(skillName);
    }
}


