package com.seniors.justlevelingfork.handler;

import com.seniors.justlevelingfork.registry.skills.ConvergenceSkill;
import com.seniors.justlevelingfork.registry.skills.TreasureHunterSkill;
import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.http.annotation.Obsolete;

import java.util.List;

@Obsolete
public class HandlerConfigCommon {
    public static final ForgeConfigSpec.Builder CONFIG = new ForgeConfigSpec.Builder();

    public static final ForgeConfigSpec SPEC;

    public static final ForgeConfigSpec.IntValue aptitudeMaxLevel;

    public static final ForgeConfigSpec.IntValue aptitudeFirstCostLevel;

    public static final ForgeConfigSpec.BooleanValue showPotionsHud;

    public static final ForgeConfigSpec.ConfigValue<List<? extends String>> lockItemList;

    public static final ForgeConfigSpec.BooleanValue logErrors;

    public static final ForgeConfigSpec.BooleanValue closeCraftingMenuOnFail;

    public static final ForgeConfigSpec.DoubleValue attackDamageValue;

    public static final ForgeConfigSpec.DoubleValue attackKnockbackValue;

    public static final ForgeConfigSpec.DoubleValue maxHealthValue;
    public static final ForgeConfigSpec.DoubleValue knockbackResistanceValue;
    public static final ForgeConfigSpec.DoubleValue movementSpeedValue;
    public static final ForgeConfigSpec.DoubleValue projectileDamageValue;
    public static final ForgeConfigSpec.DoubleValue armorValue;
    public static final ForgeConfigSpec.DoubleValue armorToughnessValue;
    public static final ForgeConfigSpec.DoubleValue attackSpeedValue;
    public static final ForgeConfigSpec.DoubleValue entityReachValue;
    public static final ForgeConfigSpec.DoubleValue blockReachValue;
    public static final ForgeConfigSpec.DoubleValue breakSpeedValue;
    public static final ForgeConfigSpec.DoubleValue beneficialEffectValue;
    public static final ForgeConfigSpec.DoubleValue magicResistValue;
    public static final ForgeConfigSpec.DoubleValue criticalDamageValue;
    public static final ForgeConfigSpec.DoubleValue luckValue;
    public static final ForgeConfigSpec.DoubleValue oneHandedAmplifier;
    public static final ForgeConfigSpec.IntValue fightingSpiritBoost;
    public static final ForgeConfigSpec.IntValue fightingSpiritDuration;
    public static final ForgeConfigSpec.IntValue berserkerPercent;
    public static final ForgeConfigSpec.DoubleValue athleticsModifier;
    public static final ForgeConfigSpec.IntValue lionHeartPercent;
    public static final ForgeConfigSpec.IntValue quickRepositionBoost;
    public static final ForgeConfigSpec.IntValue quickRepositionDuration;
    public static final ForgeConfigSpec.IntValue stealthMasteryUnSneakPercent;
    public static final ForgeConfigSpec.IntValue stealthMasterySneakPercent;
    public static final ForgeConfigSpec.DoubleValue stealthMasteryModifier;
    public static final ForgeConfigSpec.IntValue counterAttackDuration;
    public static final ForgeConfigSpec.IntValue counterAttackPercent;
    public static final ForgeConfigSpec.IntValue diamondSkinBoost;
    public static final ForgeConfigSpec.DoubleValue diamondSkinSneakAmplifier;
    public static final ForgeConfigSpec.IntValue hagglerPercent;
    public static final ForgeConfigSpec.DoubleValue alchemyManipulationAmplifier;
    public static final ForgeConfigSpec.DoubleValue obsidianSmasherModifier;
    public static final ForgeConfigSpec.IntValue treasureHunterProbability;
    public static final ForgeConfigSpec.ConfigValue<List<? extends String>> treasureHunterItemList;
    public static final ForgeConfigSpec.IntValue convergenceProbability;
    public static final ForgeConfigSpec.ConfigValue<List<? extends String>> convergenceItemList;
    public static final ForgeConfigSpec.DoubleValue lifeEaterAmplifier;
    public static final ForgeConfigSpec.DoubleValue criticalRoll6Modifier;
    public static final ForgeConfigSpec.IntValue criticalRoll1Probability;
    public static final ForgeConfigSpec.DoubleValue luckyDropModifier;
    public static final ForgeConfigSpec.IntValue luckyDropProbability;
    public static final ForgeConfigSpec.DoubleValue limitBreakerAmplifier;
    public static final ForgeConfigSpec.IntValue limitBreakerProbability;
    public static int defaultAptitudeMaxLevel = 32;
    public static int defaultAptitudeFirstCostLevel = 5;

    public static boolean defaultShowPotionsHud = true;

    public static double defaultAttackDamageValue = 1.5D;
    public static double defaultAttackKnockbackValue = 0.4D;
    public static double defaultMaxHealthValue = 20.0D;
    public static double defaultKnockbackResistanceValue = 0.5D;
    public static double defaultMovementSpeedValue = 0.05D;
    public static double defaultProjectileDamageValue = 5.0D;
    public static double defaultArmorValue = 4.0D;
    public static double defaultArmorToughnessValue = 1.0D;
    public static double defaultAttackSpeedValue = 0.4D;
    public static double defaultEntityReachValue = 1.0D;
    public static double defaultBlockReachValue = 1.5D;
    public static double defaultBreakSpeedValue = 0.5D;
    public static double defaultBeneficialEffectValue = 60.0D;
    public static double defaultMagicResistValue = 0.5D;
    public static double defaultCriticalDamageValue = 0.25D;
    public static double defaultLuckValue = 2.0D;


    public static double defaultOneHandedAmplifier = 0.5D;
    public static int defaultFightingSpiritBoost = 1;
    public static int defaultFightingSpiritDuration = 3;
    public static int defaultBerserkerPercent = 30;
    public static double defaultAthleticsModifier = 1.5D;
    public static int defaultLionHeartPercent = 50;
    public static int defaultQuickRepositionBoost = 2;
    public static int defaultQuickRepositionDuration = 3;
    public static int defaultStealthMasteryUnSneakPercent = 20;
    public static int defaultStealthMasterySneakPercent = 60;
    public static double defaultStealthMasteryModifier = 1.25D;
    public static int defaultCounterAttackDuration = 3;
    public static int defaultCounterAttackPercent = 50;
    public static int defaultDiamondSkinBoost = 2;
    public static double defaultDiamondSkinSneakAmplifier = 2.0D;
    public static int defaultHagglerPercent = 20;
    public static double defaultAlchemyManipulationAmplifier = 1.0D;
    public static double defaultObsidianSmasherModifier = 10.0D;
    public static int defaultTreasureHunterProbability = 500;
    public static int defaultConvergenceProbability = 8;
    public static double defaultLifeEaterAmplifier = 1.0D;
    public static double defaultCriticalRoll6Modifier = 1.25D;
    public static int defaultCriticalRoll1Probability = 3;
    public static double defaultLuckyDropModifier = 2.0D;
    public static int defaultLuckyDropProbability = 10;
    public static double defaultLimitBreakerAmplifier = 999.0D;
    public static int defaultLimitBreakerProbability = 100;

    static {

        CONFIG.comment("THIS CONFIGURATION ISN'T USED ANYMORE, ANY CHANGE HERE WILL HAVE NO IMPACT!");
        CONFIG.comment("USE justleveling-fork.common.json5 FOR COMMON CONFIG");
        CONFIG.comment("USE justleveling-fork.lockItems.json5 FOR LOCK ITEMS");

        CONFIG.push("general");
        aptitudeMaxLevel = CONFIG.comment("Aptitudes Max Level [default: " + defaultAptitudeMaxLevel + "]").defineInRange("aptitudeMaxLevel", defaultAptitudeMaxLevel, 2, 1000);
        aptitudeFirstCostLevel = CONFIG.comment("First aptitudes level cost: [default: " + defaultAptitudeFirstCostLevel + "]").defineInRange("aptitudeFirstCostLevel", defaultAptitudeFirstCostLevel, 1, 1000);
        showPotionsHud = CONFIG.comment("Show potions overlay over skills [default: " + defaultShowPotionsHud + "]").define("showPotionsHud", defaultShowPotionsHud);
        lockItemList = CONFIG.comment("Lock item list: (see the wiki if you wanna know how to add a unlock item)").defineList("lockItemList", HandlerAptitude.defaultLockItemList, list -> list instanceof String);
        logErrors = CONFIG.comment("If true, when populating the items a extra check will be made, if a item have a incorrect format it will log the line and continue loading without crashing. Only set to true while adding items.").define("logErrors", true);
        closeCraftingMenuOnFail = CONFIG.comment("If true, when trying to craft a non unlocked item, it will close the crafting the menu.").define("closeCraftingMenu", true);
        CONFIG.pop();

        CONFIG.push("passives");
        attackDamageValue = CONFIG.comment("Attack Damage passive value at max level [default: " + defaultAttackDamageValue + "]").defineInRange("attackDamageValue", defaultAttackDamageValue, 0.0D, 10000.0D);
        attackKnockbackValue = CONFIG.comment("Attack Knockback passive value at max level [default: " + defaultAttackKnockbackValue + "]").defineInRange("attackKnockbackValue", defaultAttackKnockbackValue, 0.0D, 10000.0D);
        maxHealthValue = CONFIG.comment("Max Health passive value at max level [default: " + defaultMaxHealthValue + "]").defineInRange("maxHealthValue", defaultMaxHealthValue, 0.0D, 10000.0D);
        knockbackResistanceValue = CONFIG.comment("Knockback Resistance passive value at max level [default: " + defaultKnockbackResistanceValue + "]").defineInRange("knockbackResistanceValue", defaultKnockbackResistanceValue, 0.0D, 10000.0D);
        movementSpeedValue = CONFIG.comment("Movement Speed passive value at max level [default: " + defaultMovementSpeedValue + "]").defineInRange("movementSpeedValue", defaultMovementSpeedValue, 0.0D, 10000.0D);
        projectileDamageValue = CONFIG.comment("Projectile Damage passive value at max level [default: " + defaultProjectileDamageValue + "]").defineInRange("projectileDamageValue", defaultProjectileDamageValue, 0.0D, 10000.0D);
        armorValue = CONFIG.comment("Armor passive value at max level [default: " + defaultArmorValue + "]").defineInRange("armorValue", defaultArmorValue, 0.0D, 10000.0D);
        armorToughnessValue = CONFIG.comment("Armor Toughness passive value at max level [default: " + defaultArmorToughnessValue + "]").defineInRange("armorToughnessValue", defaultArmorToughnessValue, 0.0D, 10000.0D);
        attackSpeedValue = CONFIG.comment("Attack Speed passive value at max level [default: " + defaultAttackSpeedValue + "]").defineInRange("attackSpeedValue", defaultAttackSpeedValue, 0.0D, 10000.0D);
        entityReachValue = CONFIG.comment("Entity Reach passive value at max level [default: " + defaultEntityReachValue + "]").defineInRange("entityReachValue", defaultEntityReachValue, 0.0D, 10000.0D);
        blockReachValue = CONFIG.comment("Block Reach passive value at max level [default: " + defaultBlockReachValue + "]").defineInRange("blockReachValue", defaultBlockReachValue, 0.0D, 10000.0D);
        breakSpeedValue = CONFIG.comment("Break Speed passive value at max level [default: " + defaultBreakSpeedValue + "]").defineInRange("breakSpeedValue", defaultBreakSpeedValue, 0.0D, 10000.0D);
        beneficialEffectValue = CONFIG.comment("Beneficial Effect passive value at max level [default: " + defaultBeneficialEffectValue + "]").defineInRange("beneficialEffectValue", defaultBeneficialEffectValue, 0.0D, 10000.0D);
        magicResistValue = CONFIG.comment("Magic Resist passive value at max level [default: " + defaultMagicResistValue + "]").defineInRange("magicResistValue", defaultMagicResistValue, 0.0D, 10000.0D);
        criticalDamageValue = CONFIG.comment("Critical Damage passive value at max level [default: " + defaultCriticalDamageValue + "]").defineInRange("criticalDamageValue", defaultCriticalDamageValue, 0.0D, 10000.0D);
        luckValue = CONFIG.comment("Luck passive value at max level [default: " + defaultLuckValue + "]").defineInRange("luckValue", defaultLuckValue, 0.0D, 10000.0D);
        CONFIG.pop();

        CONFIG.push("skills");
        oneHandedAmplifier = CONFIG.comment("One Handed skill damage amplifier increase [default: " + defaultOneHandedAmplifier + "]").defineInRange("oneHandedAmplifier", defaultOneHandedAmplifier, 0.0D, 10000.0D);
        fightingSpiritBoost = CONFIG.comment("Fighting Spirit skill strength potion effect boost [default: " + defaultFightingSpiritBoost + "]").defineInRange("fightingSpiritBoost", defaultFightingSpiritBoost, 1, 255);
        fightingSpiritDuration = CONFIG.comment("Fighting Spirit skill strength potion effect duration [default: " + defaultFightingSpiritDuration + "]").defineInRange("fightingSpiritDuration", defaultFightingSpiritDuration, 1, 3600);
        berserkerPercent = CONFIG.comment("Berserker skill health percent [default: " + defaultBerserkerPercent + "]").defineInRange("berserkerPercent", defaultBerserkerPercent, 0, 100);
        athleticsModifier = CONFIG.comment("Athletics skill air modifier multiply [default: " + defaultAthleticsModifier + "]").defineInRange("athleticsModifier", defaultAthleticsModifier, 0.0D, 500.0D);
        lionHeartPercent = CONFIG.comment("Lion Heart skill negative potion effect percent [default: " + defaultLionHeartPercent + "]").defineInRange("lionHeartPercent", defaultLionHeartPercent, 0, 100);
        quickRepositionBoost = CONFIG.comment("Quick Reposition skill speed potion effect boost [default: " + defaultQuickRepositionBoost + "]").defineInRange("quickRepositionBoost", defaultQuickRepositionBoost, 1, 255);
        quickRepositionDuration = CONFIG.comment("Quick Reposition skill speed potion effect duration [default: " + defaultQuickRepositionDuration + "]").defineInRange("quickRepositionDuration", defaultQuickRepositionDuration, 1, 3600);
        stealthMasteryUnSneakPercent = CONFIG.comment("Stealth Mastery skill enemy vision percent [default: " + defaultStealthMasteryUnSneakPercent + "]").defineInRange("stealthMasteryUnSneakPercent", defaultStealthMasteryUnSneakPercent, 0, 100);
        stealthMasterySneakPercent = CONFIG.comment("Stealth Mastery skill enemy vision percent when player is sneaking [default: " + defaultStealthMasterySneakPercent + "]").defineInRange("stealthMasterySneakPercent", defaultStealthMasterySneakPercent, 0, 100);
        stealthMasteryModifier = CONFIG.comment("Stealth Mastery skill arrow damage modifier multiply [default: " + defaultStealthMasteryModifier + "]").defineInRange("stealthMasteryModifier", defaultStealthMasteryModifier, 0.0D, 100.0D);
        counterAttackDuration = CONFIG.comment("Counter Attack skill duration to return the attack [default: " + defaultCounterAttackDuration + "]").defineInRange("counterAttackDuration", defaultCounterAttackDuration, 0, 3600);
        counterAttackPercent = CONFIG.comment("Counter Attack skill damage returned percent [default: " + defaultCounterAttackPercent + "]").defineInRange("counterAttackPercent", defaultCounterAttackPercent, 0, 500);
        diamondSkinBoost = CONFIG.comment("Diamond Skin defence potion effect boost [default: " + defaultDiamondSkinBoost + "]").defineInRange("diamondSkinBoost", defaultDiamondSkinBoost, 1, 255);
        diamondSkinSneakAmplifier = CONFIG.comment("Diamond skill defense amplifier increase when player is sneaking [default: " + defaultDiamondSkinSneakAmplifier + "]").defineInRange("diamondSkinSneakAmplifier", defaultDiamondSkinSneakAmplifier, 0.0D, 10000.0D);
        hagglerPercent = CONFIG.comment("Haggler skill villager trades cost percent reduced [default: " + defaultHagglerPercent + "]").defineInRange("hagglerPercent", defaultHagglerPercent, 0, 100);
        alchemyManipulationAmplifier = CONFIG.comment("Expert Alchemist skill potion amplifier increase [default: " + defaultAlchemyManipulationAmplifier + "]").defineInRange("alchemyManipulationAmplifier", defaultAlchemyManipulationAmplifier, 0.0D, 10000.0D);
        obsidianSmasherModifier = CONFIG.comment("Obsidian Smasher skill obsidian breaking speed modifier multiply [default: " + defaultObsidianSmasherModifier + "]").defineInRange("obsidianSmasherModifier", defaultObsidianSmasherModifier, 0.0D, 100.0D);
        treasureHunterProbability = CONFIG.comment("Treasure Hunter skill probability chance to get a treasure in dirt, example: 1/500 it means that 1 out of every 500 (0,2%) dirt blocks you can get a treasure. [default: " + defaultTreasureHunterProbability + "]").defineInRange("treasureHunterProbability", defaultTreasureHunterProbability, 1, 10000);
        treasureHunterItemList = CONFIG.comment("Treasure Hunter skill treasures item list: (see the wiki if you wanna know how to add a treasure item)").defineList("treasureHunterItemList", TreasureHunterSkill.defaultItemList, list -> list instanceof String);
        convergenceProbability = CONFIG.comment("Convergence skill probability chance to obtain part of the spent material, example: 1/8 it means that 1 out of every 8 (12,5%) crafted items you can obtain a part of the spent material. [default: " + defaultConvergenceProbability + "]").defineInRange("convergenceProbability", defaultConvergenceProbability, 1, 10000);
        convergenceItemList = CONFIG.comment("Convergence skill convergence item list: (see the wiki if you wanna know how to add a convergence item)").defineList("convergenceItemList", ConvergenceSkill.defaultItemList, list -> list instanceof String);
        lifeEaterAmplifier = CONFIG.comment("Life Eater skill life steal amplifier increase [default: " + defaultLifeEaterAmplifier + "]").defineInRange("lifeEaterModifier", defaultLifeEaterAmplifier, 0.0D, 10000.0D);
        criticalRoll6Modifier = CONFIG.comment("Critical Roll skill critic modifier multiply when you roll a 6 [default: " + defaultCriticalRoll6Modifier + "]").defineInRange("criticalRoll6Modifier", defaultCriticalRoll6Modifier, 0.0D, 100.0D);
        criticalRoll1Probability = CONFIG.comment("Critical Roll skill critic probability reduce when you roll a 1, example: 1/3 it means that 1 out of every 3 (33%) the critic damage will be reduced. [default: " + defaultCriticalRoll1Probability + "]").defineInRange("criticalRoll1Probability", defaultCriticalRoll1Probability, 1, 10000);
        luckyDropModifier = CONFIG.comment("Lucky Drop skill mob drops modifier multiply [default: " + defaultLuckyDropModifier + "]").defineInRange("luckyDropModifier", defaultLuckyDropModifier, 0.0D, 10000.0D);
        luckyDropProbability = CONFIG.comment("Lucky Drop skill mobs drops probability, example: 1/5 it means that 1 out of every 5 (20%) the mob drops will be multiplied. [default: " + defaultLuckyDropProbability + "]").defineInRange("luckyDropProbability", defaultLuckyDropProbability, 1, 10000);
        limitBreakerAmplifier = CONFIG.comment("Limit Breaker skill deal damage amplifier [default: " + defaultLimitBreakerAmplifier + "]").defineInRange("limitBreakerAmplifier", defaultLimitBreakerAmplifier, 0.0D, 10000.0D);
        limitBreakerProbability = CONFIG.comment("Limit Breaker skill deal damage probability, example: 1/100 it means that 1 in 100 (1%) to deal damage amplified. [default: " + defaultLimitBreakerProbability + "]").defineInRange("limitBreakerProbability", defaultLimitBreakerProbability, 1, 10000);
        CONFIG.pop();

        SPEC = CONFIG.build();
    }
}


