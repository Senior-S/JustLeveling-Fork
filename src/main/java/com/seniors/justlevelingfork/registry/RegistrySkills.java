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
    public static final DeferredRegister<Skill> SKILLS = DeferredRegister.create(SKILLS_KEY, JustLevelingFork.MOD_ID);
    public static final Supplier<IForgeRegistry<Skill>> SKILLS_REGISTRY = SKILLS.makeRegistry(() -> new RegistryBuilder<Skill>().disableSaving());

    public static final RegistryObject<Skill> ONE_HANDED = HandlerCommonConfig.HANDLER.instance().oneHandedRequiredLevel < 0 ? null : SKILLS.register("one_handed", () -> register("one_handed", RegistryAptitudes.STRENGTH.get(), HandlerCommonConfig.HANDLER.instance().oneHandedRequiredLevel, HandlerResources.ONE_HANDED_SKILL, new Value(ValueType.AMPLIFIER, HandlerCommonConfig.HANDLER.instance().oneHandedAmplifier)));

    public static final RegistryObject<Skill> FIGHTING_SPIRIT = HandlerCommonConfig.HANDLER.instance().fightingSpiritRequiredLevel < 0 ? null : SKILLS.register("fighting_spirit", () -> register("fighting_spirit", RegistryAptitudes.STRENGTH.get(), HandlerCommonConfig.HANDLER.instance().fightingSpiritRequiredLevel, HandlerResources.FIGHTING_SPIRIT_SKILL, new Value(ValueType.BOOST, HandlerCommonConfig.HANDLER.instance().fightingSpiritBoost), new Value(ValueType.DURATION, HandlerCommonConfig.HANDLER.instance().fightingSpiritDuration)));

    public static final RegistryObject<Skill> BERSERKER = HandlerCommonConfig.HANDLER.instance().berserkerRequiredLevel < 0 ? null : SKILLS.register("berserker", () -> register("berserker", RegistryAptitudes.STRENGTH.get(), HandlerCommonConfig.HANDLER.instance().berserkerRequiredLevel, HandlerResources.BERSERKER_SKILL, new Value(ValueType.PERCENT, HandlerCommonConfig.HANDLER.instance().berserkerPercent)));

    public static final RegistryObject<Skill> ATHLETICS = HandlerCommonConfig.HANDLER.instance().athleticsRequiredLevel < 0 ? null : SKILLS.register("athletics", () -> register("athletics", RegistryAptitudes.CONSTITUTION.get(), HandlerCommonConfig.HANDLER.instance().athleticsRequiredLevel, HandlerResources.ATHLETICS_SKILL, new Value(ValueType.MODIFIER, HandlerCommonConfig.HANDLER.instance().athleticsModifier)));

    public static final RegistryObject<Skill> TURTLE_SHIELD = HandlerCommonConfig.HANDLER.instance().turtleShieldRequiredLevel < 0 ? null : SKILLS.register("turtle_shield", () -> register("turtle_shield", RegistryAptitudes.CONSTITUTION.get(), HandlerCommonConfig.HANDLER.instance().turtleShieldRequiredLevel, HandlerResources.TURTLE_SHIELD_SKILL));

    public static final RegistryObject<Skill> LION_HEART = HandlerCommonConfig.HANDLER.instance().lionHeartRequiredLevel < 0 ? null : SKILLS.register("lion_heart", () -> register("lion_heart", RegistryAptitudes.CONSTITUTION.get(), HandlerCommonConfig.HANDLER.instance().lionHeartRequiredLevel, HandlerResources.LION_HEART_SKILL, new Value(ValueType.PERCENT, HandlerCommonConfig.HANDLER.instance().lionHeartPercent)));

    public static final RegistryObject<Skill> QUICK_REPOSITION = HandlerCommonConfig.HANDLER.instance().quickRepositionRequiredLevel < 0 ? null : SKILLS.register("quick_reposition", () -> register("quick_reposition", RegistryAptitudes.DEXTERITY.get(), HandlerCommonConfig.HANDLER.instance().quickRepositionRequiredLevel, HandlerResources.QUICK_REPOSITION_SKILL, new Value(ValueType.BOOST, HandlerCommonConfig.HANDLER.instance().quickRepositionBoost), new Value(ValueType.DURATION, HandlerCommonConfig.HANDLER.instance().quickRepositionDuration)));

    public static final RegistryObject<Skill> STEALTH_MASTERY = HandlerCommonConfig.HANDLER.instance().stealthMasteryRequiredLevel < 0 ? null : SKILLS.register("stealth_mastery", () -> register("stealth_mastery", RegistryAptitudes.DEXTERITY.get(), HandlerCommonConfig.HANDLER.instance().stealthMasteryRequiredLevel, HandlerResources.STEALTH_MASTERY_SKILL, new Value(ValueType.PERCENT, HandlerCommonConfig.HANDLER.instance().stealthMasteryUnSneakPercent), new Value(ValueType.PERCENT, HandlerCommonConfig.HANDLER.instance().stealthMasterySneakPercent), new Value(ValueType.MODIFIER, HandlerCommonConfig.HANDLER.instance().stealthMasteryModifier)));

    public static final RegistryObject<Skill> CAT_EYES = HandlerCommonConfig.HANDLER.instance().catEyesRequiredLevel < 0 ? null : SKILLS.register("cat_eyes", () -> register("cat_eyes", RegistryAptitudes.DEXTERITY.get(), HandlerCommonConfig.HANDLER.instance().catEyesRequiredLevel, HandlerResources.CAT_EYES_SKILL));

    public static final RegistryObject<Skill> SNOW_WALKER = HandlerCommonConfig.HANDLER.instance().snowWalkerRequiredLevel < 0 ? null : SKILLS.register("snow_walker", () -> register("snow_walker", RegistryAptitudes.DEFENSE.get(), HandlerCommonConfig.HANDLER.instance().snowWalkerRequiredLevel, HandlerResources.SNOW_WALKER_SKILL));

    public static final RegistryObject<Skill> COUNTER_ATTACK = HandlerCommonConfig.HANDLER.instance().counterattackRequiredLevel < 0 ? null : SKILLS.register("counter_attack", () -> register("counter_attack", RegistryAptitudes.DEFENSE.get(), HandlerCommonConfig.HANDLER.instance().counterattackRequiredLevel, HandlerResources.COUNTER_ATTACK_SKILL, new Value(ValueType.DURATION, HandlerCommonConfig.HANDLER.instance().counterAttackDuration), new Value(ValueType.PERCENT, HandlerCommonConfig.HANDLER.instance().counterAttackPercent)));

    public static final RegistryObject<Skill> DIAMOND_SKIN = HandlerCommonConfig.HANDLER.instance().diamondSkinRequiredLevel < 0 ? null : SKILLS.register("diamond_skin", () -> register("diamond_skin", RegistryAptitudes.DEFENSE.get(), HandlerCommonConfig.HANDLER.instance().diamondSkinRequiredLevel, HandlerResources.DIAMOND_SKIN_SKILL, new Value(ValueType.BOOST, HandlerCommonConfig.HANDLER.instance().diamondSkinBoost), new Value(ValueType.AMPLIFIER, HandlerCommonConfig.HANDLER.instance().diamondSkinSneakAmplifier)));

    public static final RegistryObject<Skill> SCHOLAR = HandlerCommonConfig.HANDLER.instance().scholarRequiredLevel < 0 ? null : SKILLS.register("scholar", () -> register("scholar", RegistryAptitudes.INTELLIGENCE.get(), HandlerCommonConfig.HANDLER.instance().scholarRequiredLevel, HandlerResources.SCHOLAR_SKILL));

    public static final RegistryObject<Skill> HAGGLER = HandlerCommonConfig.HANDLER.instance().hagglerRequiredLevel < 0 ? null : SKILLS.register("haggler", () -> register("haggler", RegistryAptitudes.INTELLIGENCE.get(), HandlerCommonConfig.HANDLER.instance().hagglerRequiredLevel, HandlerResources.HAGGLER_SKILL, new Value(ValueType.PERCENT, HandlerCommonConfig.HANDLER.instance().hagglerPercent)));

    public static final RegistryObject<Skill> ALCHEMY_MANIPULATION = HandlerCommonConfig.HANDLER.instance().alchemyManipulationRequiredLevel < 0 ? null : SKILLS.register("alchemy_manipulation", () -> register("alchemy_manipulation", RegistryAptitudes.INTELLIGENCE.get(), HandlerCommonConfig.HANDLER.instance().alchemyManipulationRequiredLevel, HandlerResources.ALCHEMY_MANIPULATION_SKILL, new Value(ValueType.AMPLIFIER, HandlerCommonConfig.HANDLER.instance().alchemyManipulationAmplifier)));

    public static final RegistryObject<Skill> OBSIDIAN_SMASHER = HandlerCommonConfig.HANDLER.instance().obsidianSmasherRequiredLevel < 0 ? null : SKILLS.register("obsidian_smasher", () -> register("obsidian_smasher", RegistryAptitudes.BUILDING.get(), HandlerCommonConfig.HANDLER.instance().obsidianSmasherRequiredLevel, HandlerResources.OBSIDIAN_SMASHER_SKILL, new Value(ValueType.MODIFIER, HandlerCommonConfig.HANDLER.instance().obsidianSmasherModifier)));

    public static final RegistryObject<Skill> TREASURE_HUNTER = HandlerCommonConfig.HANDLER.instance().treasureHunterRequiredLevel < 0 ? null : SKILLS.register("treasure_hunter", () -> register("treasure_hunter", RegistryAptitudes.BUILDING.get(), HandlerCommonConfig.HANDLER.instance().treasureHunterRequiredLevel, HandlerResources.TREASURE_HUNTER_SKILL, new Value(ValueType.PROBABILITY, HandlerCommonConfig.HANDLER.instance().treasureHunterProbability)));

    public static final RegistryObject<Skill> CONVERGENCE = HandlerCommonConfig.HANDLER.instance().convergenceRequiredLevel < 0 ? null : SKILLS.register("convergence", () -> register("convergence", RegistryAptitudes.BUILDING.get(), HandlerCommonConfig.HANDLER.instance().convergenceRequiredLevel, HandlerResources.CONVERGENCE_SKILL, new Value(ValueType.PROBABILITY, HandlerCommonConfig.HANDLER.instance().convergenceProbability)));

    public static final RegistryObject<Skill> SAFE_PORT = HandlerCommonConfig.HANDLER.instance().safePortRequiredLevel < 0 ? null : SKILLS.register("safe_port", () -> register("safe_port", RegistryAptitudes.MAGIC.get(), HandlerCommonConfig.HANDLER.instance().safePortRequiredLevel, HandlerResources.SAFE_PORT_SKILL));

    public static final RegistryObject<Skill> LIFE_EATER = HandlerCommonConfig.HANDLER.instance().lifeEaterRequiredLevel < 0 ? null : SKILLS.register("life_eater", () -> register("life_eater", RegistryAptitudes.MAGIC.get(), HandlerCommonConfig.HANDLER.instance().lifeEaterRequiredLevel, HandlerResources.LIFE_EATER_SKILL, new Value(ValueType.AMPLIFIER, HandlerCommonConfig.HANDLER.instance().lifeEaterModifier)));

    public static final RegistryObject<Skill> WORMHOLE_STORAGE = HandlerCommonConfig.HANDLER.instance().wornholeStorageRequiredLevel < 0 ? null : SKILLS.register("wormhole_storage", () -> register("wormhole_storage", RegistryAptitudes.MAGIC.get(), HandlerCommonConfig.HANDLER.instance().wornholeStorageRequiredLevel, HandlerResources.WORMHOLE_STORAGE_SKILL));

    public static final RegistryObject<Skill> CRITICAL_ROLL = HandlerCommonConfig.HANDLER.instance().criticalRollRequiredLevel < 0 ? null : SKILLS.register("critical_roll", () -> register("critical_roll", RegistryAptitudes.LUCK.get(), HandlerCommonConfig.HANDLER.instance().criticalRollRequiredLevel, HandlerResources.CRITICAL_ROLL_SKILL, new Value(ValueType.MODIFIER, HandlerCommonConfig.HANDLER.instance().criticalRoll6Modifier), new Value(ValueType.PROBABILITY, HandlerCommonConfig.HANDLER.instance().criticalRoll1Probability)));

    public static final RegistryObject<Skill> LUCKY_DROP =  HandlerCommonConfig.HANDLER.instance().luckyDropRequiredLevel < 0 ? null : SKILLS.register("lucky_drop", () -> register("lucky_drop", RegistryAptitudes.LUCK.get(), HandlerCommonConfig.HANDLER.instance().luckyDropRequiredLevel, HandlerResources.LUCKY_DROP_SKILL, new Value(ValueType.PROBABILITY, HandlerCommonConfig.HANDLER.instance().luckyDropProbability), new Value(ValueType.MODIFIER, HandlerCommonConfig.HANDLER.instance().luckyDropModifier)));

    public static final RegistryObject<Skill> LIMIT_BREAKER = HandlerCommonConfig.HANDLER.instance().limitBreakerRequiredLevel < 0 ? null : SKILLS.register("limit_breaker", () -> register("limit_breaker", RegistryAptitudes.LUCK.get(), HandlerCommonConfig.HANDLER.instance().limitBreakerRequiredLevel, HandlerResources.LIMIT_BREAKER_SKILL, new Value(ValueType.PROBABILITY, HandlerCommonConfig.HANDLER.instance().limitBreakerProbability), new Value(ValueType.AMPLIFIER, HandlerCommonConfig.HANDLER.instance().limitBreakerAmplifier)));

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
}


