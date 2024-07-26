package com.seniors.justlevelingfork.handler;

import com.seniors.justlevelingfork.registry.skills.ConvergenceSkill;
import com.seniors.justlevelingfork.registry.skills.TreasureHunterSkill;

import java.util.List;
import java.util.Objects;

import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class HandlerClothConfig {
    public static Screen getConfigScreen(Screen parent, boolean isTransparent) {
        ConfigBuilder builder = ConfigBuilder.create().setParentScreen(parent).setTitle(Component.translatable("config.cloth-config.justlevelingfork.title"));
        builder.setDefaultBackgroundTexture(new ResourceLocation("minecraft:textures/block/dirt.png"));
        ConfigCategory general = builder.getOrCreateCategory(Component.translatable("config.cloth-config.justlevelingfork.general"));
        ConfigCategory passives = builder.getOrCreateCategory(Component.translatable("config.cloth-config.justlevelingfork.passives"));
        ConfigCategory skills = builder.getOrCreateCategory(Component.translatable("config.cloth-config.justlevelingfork.skills"));
        ConfigEntryBuilder configEntryBuilder = builder.entryBuilder();


        Objects.requireNonNull(HandlerConfigCommon.aptitudeMaxLevel);
        general.addEntry(configEntryBuilder.startIntField(Component.translatable("config.cloth-config.justlevelingfork.aptitudeMaxLevel"), HandlerConfigCommon.aptitudeMaxLevel.get()).setDefaultValue(HandlerConfigCommon.defaultAptitudeMaxLevel).setSaveConsumer(HandlerConfigCommon.aptitudeMaxLevel::set)
                .build());


        Objects.requireNonNull(HandlerConfigCommon.aptitudeFirstCostLevel);
        general.addEntry(configEntryBuilder.startIntField(Component.translatable("config.cloth-config.justlevelingfork.aptitudeFirstCostLevel"), HandlerConfigCommon.aptitudeFirstCostLevel.get()).setDefaultValue(HandlerConfigCommon.defaultAptitudeFirstCostLevel).setSaveConsumer(HandlerConfigCommon.aptitudeFirstCostLevel::set)
                .build());


        Objects.requireNonNull(HandlerConfigCommon.showPotionsHud);
        general.addEntry(configEntryBuilder.startBooleanToggle(Component.translatable("config.cloth-config.justlevelingfork.showPotionsHud"), HandlerConfigCommon.showPotionsHud.get()).setDefaultValue(HandlerConfigCommon.defaultShowPotionsHud).setSaveConsumer(HandlerConfigCommon.showPotionsHud::set)
                .build());


        Objects.requireNonNull(HandlerConfigCommon.lockItemList);
        general.addEntry(configEntryBuilder.startStrList(Component.translatable("config.cloth-config.justlevelingfork.lockItemList"), (List) HandlerConfigCommon.lockItemList.get()).setDefaultValue(HandlerAptitude.defaultLockItemList).setSaveConsumer(HandlerConfigCommon.lockItemList::set)
                .setTooltip(new Component[]{Component.translatable("config.cloth-config.justlevelingfork.lockItemList.tooltip")
                }).build());


        Objects.requireNonNull(HandlerConfigCommon.attackDamageValue);
        passives.addEntry(configEntryBuilder.startDoubleField(Component.translatable("config.cloth-config.justlevelingfork.attackDamageValue"), HandlerConfigCommon.attackDamageValue.get()).setDefaultValue(HandlerConfigCommon.defaultAttackDamageValue).setSaveConsumer(HandlerConfigCommon.attackDamageValue::set)
                .build());


        Objects.requireNonNull(HandlerConfigCommon.attackKnockbackValue);
        passives.addEntry(configEntryBuilder.startDoubleField(Component.translatable("config.cloth-config.justlevelingfork.attackKnockbackValue"), HandlerConfigCommon.attackKnockbackValue.get()).setDefaultValue(HandlerConfigCommon.defaultAttackKnockbackValue).setSaveConsumer(HandlerConfigCommon.attackKnockbackValue::set)
                .build());


        Objects.requireNonNull(HandlerConfigCommon.maxHealthValue);
        passives.addEntry(configEntryBuilder.startDoubleField(Component.translatable("config.cloth-config.justlevelingfork.maxHealthValue"), HandlerConfigCommon.maxHealthValue.get()).setDefaultValue(HandlerConfigCommon.defaultMaxHealthValue).setSaveConsumer(HandlerConfigCommon.maxHealthValue::set)
                .build());


        Objects.requireNonNull(HandlerConfigCommon.knockbackResistanceValue);
        passives.addEntry(configEntryBuilder.startDoubleField(Component.translatable("config.cloth-config.justlevelingfork.knockbackResistanceValue"), HandlerConfigCommon.knockbackResistanceValue.get()).setDefaultValue(HandlerConfigCommon.defaultKnockbackResistanceValue).setSaveConsumer(HandlerConfigCommon.knockbackResistanceValue::set)
                .build());


        Objects.requireNonNull(HandlerConfigCommon.movementSpeedValue);
        passives.addEntry(configEntryBuilder.startDoubleField(Component.translatable("config.cloth-config.justlevelingfork.movementSpeedValue"), HandlerConfigCommon.movementSpeedValue.get()).setDefaultValue(HandlerConfigCommon.defaultMovementSpeedValue).setSaveConsumer(HandlerConfigCommon.movementSpeedValue::set)
                .build());


        Objects.requireNonNull(HandlerConfigCommon.projectileDamageValue);
        passives.addEntry(configEntryBuilder.startDoubleField(Component.translatable("config.cloth-config.justlevelingfork.projectileDamageValue"), HandlerConfigCommon.projectileDamageValue.get()).setDefaultValue(HandlerConfigCommon.defaultProjectileDamageValue).setSaveConsumer(HandlerConfigCommon.projectileDamageValue::set)
                .build());


        Objects.requireNonNull(HandlerConfigCommon.armorValue);
        passives.addEntry(configEntryBuilder.startDoubleField(Component.translatable("config.cloth-config.justlevelingfork.armorValue"), HandlerConfigCommon.armorValue.get()).setDefaultValue(HandlerConfigCommon.defaultArmorValue).setSaveConsumer(HandlerConfigCommon.armorValue::set)
                .build());


        Objects.requireNonNull(HandlerConfigCommon.armorToughnessValue);
        passives.addEntry(configEntryBuilder.startDoubleField(Component.translatable("config.cloth-config.justlevelingfork.armorToughnessValue"), HandlerConfigCommon.armorToughnessValue.get()).setDefaultValue(HandlerConfigCommon.defaultArmorToughnessValue).setSaveConsumer(HandlerConfigCommon.armorToughnessValue::set)
                .build());


        Objects.requireNonNull(HandlerConfigCommon.attackSpeedValue);
        passives.addEntry(configEntryBuilder.startDoubleField(Component.translatable("config.cloth-config.justlevelingfork.attackSpeedValue"), HandlerConfigCommon.attackSpeedValue.get()).setDefaultValue(HandlerConfigCommon.defaultAttackSpeedValue).setSaveConsumer(HandlerConfigCommon.attackSpeedValue::set)
                .build());


        Objects.requireNonNull(HandlerConfigCommon.entityReachValue);
        passives.addEntry(configEntryBuilder.startDoubleField(Component.translatable("config.cloth-config.justlevelingfork.entityReachValue"), HandlerConfigCommon.entityReachValue.get()).setDefaultValue(HandlerConfigCommon.defaultEntityReachValue).setSaveConsumer(HandlerConfigCommon.entityReachValue::set)
                .build());


        Objects.requireNonNull(HandlerConfigCommon.blockReachValue);
        passives.addEntry(configEntryBuilder.startDoubleField(Component.translatable("config.cloth-config.justlevelingfork.blockReachValue"), HandlerConfigCommon.blockReachValue.get()).setDefaultValue(HandlerConfigCommon.defaultBlockReachValue).setSaveConsumer(HandlerConfigCommon.blockReachValue::set)
                .build());


        Objects.requireNonNull(HandlerConfigCommon.breakSpeedValue);
        passives.addEntry(configEntryBuilder.startDoubleField(Component.translatable("config.cloth-config.justlevelingfork.breakSpeedValue"), HandlerConfigCommon.breakSpeedValue.get()).setDefaultValue(HandlerConfigCommon.defaultBreakSpeedValue).setSaveConsumer(HandlerConfigCommon.breakSpeedValue::set)
                .build());


        Objects.requireNonNull(HandlerConfigCommon.beneficialEffectValue);
        passives.addEntry(configEntryBuilder.startDoubleField(Component.translatable("config.cloth-config.justlevelingfork.beneficialEffectValue"), HandlerConfigCommon.beneficialEffectValue.get()).setDefaultValue(HandlerConfigCommon.defaultBeneficialEffectValue).setSaveConsumer(HandlerConfigCommon.beneficialEffectValue::set)
                .build());


        Objects.requireNonNull(HandlerConfigCommon.magicResistValue);
        passives.addEntry(configEntryBuilder.startDoubleField(Component.translatable("config.cloth-config.justlevelingfork.magicResistValue"), HandlerConfigCommon.magicResistValue.get()).setDefaultValue(HandlerConfigCommon.defaultMagicResistValue).setSaveConsumer(HandlerConfigCommon.magicResistValue::set)
                .build());


        Objects.requireNonNull(HandlerConfigCommon.criticalDamageValue);
        passives.addEntry(configEntryBuilder.startDoubleField(Component.translatable("config.cloth-config.justlevelingfork.criticalDamageValue"), HandlerConfigCommon.criticalDamageValue.get()).setDefaultValue(HandlerConfigCommon.defaultCriticalDamageValue).setSaveConsumer(HandlerConfigCommon.criticalDamageValue::set)
                .build());


        Objects.requireNonNull(HandlerConfigCommon.luckValue);
        passives.addEntry(configEntryBuilder.startDoubleField(Component.translatable("config.cloth-config.justlevelingfork.luckValue"), HandlerConfigCommon.luckValue.get()).setDefaultValue(HandlerConfigCommon.defaultLuckValue).setSaveConsumer(HandlerConfigCommon.luckValue::set)
                .build());


        Objects.requireNonNull(HandlerConfigCommon.oneHandedAmplifier);
        skills.addEntry(configEntryBuilder.startDoubleField(Component.translatable("config.cloth-config.justlevelingfork.oneHandedAmplifier"), HandlerConfigCommon.oneHandedAmplifier.get()).setDefaultValue(HandlerConfigCommon.defaultOneHandedAmplifier).setSaveConsumer(HandlerConfigCommon.oneHandedAmplifier::set)
                .build());


        Objects.requireNonNull(HandlerConfigCommon.fightingSpiritBoost);
        skills.addEntry(configEntryBuilder.startIntField(Component.translatable("config.cloth-config.justlevelingfork.fightingSpiritBoost"), HandlerConfigCommon.fightingSpiritBoost.get()).setDefaultValue(HandlerConfigCommon.defaultFightingSpiritBoost).setSaveConsumer(HandlerConfigCommon.fightingSpiritBoost::set)
                .build());


        Objects.requireNonNull(HandlerConfigCommon.fightingSpiritDuration);
        skills.addEntry(configEntryBuilder.startIntField(Component.translatable("config.cloth-config.justlevelingfork.fightingSpiritDuration"), HandlerConfigCommon.fightingSpiritDuration.get()).setDefaultValue(HandlerConfigCommon.defaultFightingSpiritDuration).setSaveConsumer(HandlerConfigCommon.fightingSpiritDuration::set)
                .build());


        Objects.requireNonNull(HandlerConfigCommon.berserkerPercent);
        skills.addEntry(configEntryBuilder.startIntField(Component.translatable("config.cloth-config.justlevelingfork.berserkerPercent"), HandlerConfigCommon.berserkerPercent.get()).setDefaultValue(HandlerConfigCommon.defaultBerserkerPercent).setSaveConsumer(HandlerConfigCommon.berserkerPercent::set)
                .build());


        Objects.requireNonNull(HandlerConfigCommon.athleticsModifier);
        skills.addEntry(configEntryBuilder.startDoubleField(Component.translatable("config.cloth-config.justlevelingfork.athleticsModifier"), HandlerConfigCommon.athleticsModifier.get()).setDefaultValue(HandlerConfigCommon.defaultAthleticsModifier).setSaveConsumer(HandlerConfigCommon.athleticsModifier::set)
                .build());


        Objects.requireNonNull(HandlerConfigCommon.lionHeartPercent);
        skills.addEntry(configEntryBuilder.startIntField(Component.translatable("config.cloth-config.justlevelingfork.lionHeartPercent"), HandlerConfigCommon.lionHeartPercent.get()).setDefaultValue(HandlerConfigCommon.defaultLionHeartPercent).setSaveConsumer(HandlerConfigCommon.lionHeartPercent::set)
                .build());


        Objects.requireNonNull(HandlerConfigCommon.quickRepositionBoost);
        skills.addEntry(configEntryBuilder.startIntField(Component.translatable("config.cloth-config.justlevelingfork.quickRepositionBoost"), HandlerConfigCommon.quickRepositionBoost.get()).setDefaultValue(HandlerConfigCommon.defaultQuickRepositionBoost).setSaveConsumer(HandlerConfigCommon.quickRepositionBoost::set)
                .build());


        Objects.requireNonNull(HandlerConfigCommon.quickRepositionDuration);
        skills.addEntry(configEntryBuilder.startIntField(Component.translatable("config.cloth-config.justlevelingfork.quickRepositionDuration"), HandlerConfigCommon.quickRepositionDuration.get()).setDefaultValue(HandlerConfigCommon.defaultQuickRepositionDuration).setSaveConsumer(HandlerConfigCommon.quickRepositionDuration::set)
                .build());


        Objects.requireNonNull(HandlerConfigCommon.stealthMasteryUnSneakPercent);
        skills.addEntry(configEntryBuilder.startIntField(Component.translatable("config.cloth-config.justlevelingfork.stealthMasteryUnSneakPercent"), HandlerConfigCommon.stealthMasteryUnSneakPercent.get()).setDefaultValue(HandlerConfigCommon.defaultStealthMasteryUnSneakPercent).setSaveConsumer(HandlerConfigCommon.stealthMasteryUnSneakPercent::set)
                .build());


        Objects.requireNonNull(HandlerConfigCommon.stealthMasterySneakPercent);
        skills.addEntry(configEntryBuilder.startIntField(Component.translatable("config.cloth-config.justlevelingfork.stealthMasterySneakPercent"), HandlerConfigCommon.stealthMasterySneakPercent.get()).setDefaultValue(HandlerConfigCommon.defaultStealthMasterySneakPercent).setSaveConsumer(HandlerConfigCommon.stealthMasterySneakPercent::set)
                .build());


        Objects.requireNonNull(HandlerConfigCommon.stealthMasteryModifier);
        skills.addEntry(configEntryBuilder.startDoubleField(Component.translatable("config.cloth-config.justlevelingfork.stealthMasteryModifier"), HandlerConfigCommon.stealthMasteryModifier.get()).setDefaultValue(HandlerConfigCommon.defaultStealthMasteryModifier).setSaveConsumer(HandlerConfigCommon.stealthMasteryModifier::set)
                .build());


        Objects.requireNonNull(HandlerConfigCommon.counterAttackDuration);
        skills.addEntry(configEntryBuilder.startIntField(Component.translatable("config.cloth-config.justlevelingfork.counterAttackDuration"), HandlerConfigCommon.counterAttackDuration.get()).setDefaultValue(HandlerConfigCommon.defaultCounterAttackDuration).setSaveConsumer(HandlerConfigCommon.counterAttackDuration::set)
                .build());


        Objects.requireNonNull(HandlerConfigCommon.counterAttackPercent);
        skills.addEntry(configEntryBuilder.startIntField(Component.translatable("config.cloth-config.justlevelingfork.counterAttackPercent"), HandlerConfigCommon.counterAttackPercent.get()).setDefaultValue(HandlerConfigCommon.defaultCounterAttackPercent).setSaveConsumer(HandlerConfigCommon.counterAttackPercent::set)
                .build());


        Objects.requireNonNull(HandlerConfigCommon.diamondSkinBoost);
        skills.addEntry(configEntryBuilder.startIntField(Component.translatable("config.cloth-config.justlevelingfork.diamondSkinBoost"), HandlerConfigCommon.diamondSkinBoost.get()).setDefaultValue(HandlerConfigCommon.defaultDiamondSkinBoost).setSaveConsumer(HandlerConfigCommon.diamondSkinBoost::set)
                .build());


        Objects.requireNonNull(HandlerConfigCommon.diamondSkinSneakAmplifier);
        skills.addEntry(configEntryBuilder.startDoubleField(Component.translatable("config.cloth-config.justlevelingfork.diamondSkinSneakAmplifier"), HandlerConfigCommon.diamondSkinSneakAmplifier.get()).setDefaultValue(HandlerConfigCommon.defaultDiamondSkinSneakAmplifier).setSaveConsumer(HandlerConfigCommon.diamondSkinSneakAmplifier::set)
                .build());


        Objects.requireNonNull(HandlerConfigCommon.hagglerPercent);
        skills.addEntry(configEntryBuilder.startIntField(Component.translatable("config.cloth-config.justlevelingfork.hagglerPercent"), HandlerConfigCommon.hagglerPercent.get()).setDefaultValue(HandlerConfigCommon.defaultHagglerPercent).setSaveConsumer(HandlerConfigCommon.hagglerPercent::set)
                .build());


        Objects.requireNonNull(HandlerConfigCommon.alchemyManipulationAmplifier);
        skills.addEntry(configEntryBuilder.startDoubleField(Component.translatable("config.cloth-config.justlevelingfork.alchemyManipulationAmplifier"), HandlerConfigCommon.alchemyManipulationAmplifier.get()).setDefaultValue(HandlerConfigCommon.defaultAlchemyManipulationAmplifier).setSaveConsumer(HandlerConfigCommon.alchemyManipulationAmplifier::set)
                .build());


        Objects.requireNonNull(HandlerConfigCommon.obsidianSmasherModifier);
        skills.addEntry(configEntryBuilder.startDoubleField(Component.translatable("config.cloth-config.justlevelingfork.obsidianSmasherModifier"), HandlerConfigCommon.obsidianSmasherModifier.get()).setDefaultValue(HandlerConfigCommon.defaultObsidianSmasherModifier).setSaveConsumer(HandlerConfigCommon.obsidianSmasherModifier::set)
                .build());


        Objects.requireNonNull(HandlerConfigCommon.treasureHunterProbability);
        skills.addEntry(configEntryBuilder.startIntField(Component.translatable("config.cloth-config.justlevelingfork.treasureHunterProbability"), HandlerConfigCommon.treasureHunterProbability.get().intValue()).setDefaultValue(HandlerConfigCommon.defaultTreasureHunterProbability).setSaveConsumer(HandlerConfigCommon.treasureHunterProbability::set)
                .build());


        Objects.requireNonNull(HandlerConfigCommon.treasureHunterItemList);
        skills.addEntry(configEntryBuilder.startStrList(Component.translatable("config.cloth-config.justlevelingfork.treasureHunterItemList"), (List) HandlerConfigCommon.treasureHunterItemList.get()).setDefaultValue(TreasureHunterSkill.defaultItemList).setSaveConsumer(HandlerConfigCommon.treasureHunterItemList::set)
                .setTooltip(new Component[]{Component.translatable("config.cloth-config.justlevelingfork.treasureHunterItemList.tooltip")
                }).build());


        Objects.requireNonNull(HandlerConfigCommon.convergenceProbability);
        skills.addEntry(configEntryBuilder.startIntField(Component.translatable("config.cloth-config.justlevelingfork.convergenceProbability"), HandlerConfigCommon.convergenceProbability.get()).setDefaultValue(HandlerConfigCommon.defaultConvergenceProbability).setSaveConsumer(HandlerConfigCommon.convergenceProbability::set)
                .build());


        Objects.requireNonNull(HandlerConfigCommon.convergenceItemList);
        skills.addEntry(configEntryBuilder.startStrList(Component.translatable("config.cloth-config.justlevelingfork.convergenceItemList"), (List) HandlerConfigCommon.convergenceItemList.get()).setDefaultValue(ConvergenceSkill.defaultItemList).setSaveConsumer(HandlerConfigCommon.convergenceItemList::set)
                .setTooltip(new Component[]{Component.translatable("config.cloth-config.justlevelingfork.convergenceItemList.tooltip")
                }).build());


        Objects.requireNonNull(HandlerConfigCommon.lifeEaterAmplifier);
        skills.addEntry(configEntryBuilder.startDoubleField(Component.translatable("config.cloth-config.justlevelingfork.lifeEaterAmplifier"), HandlerConfigCommon.lifeEaterAmplifier.get()).setDefaultValue(HandlerConfigCommon.defaultLifeEaterAmplifier).setSaveConsumer(HandlerConfigCommon.lifeEaterAmplifier::set)
                .build());


        Objects.requireNonNull(HandlerConfigCommon.criticalRoll6Modifier);
        skills.addEntry(configEntryBuilder.startDoubleField(Component.translatable("config.cloth-config.justlevelingfork.criticalRoll6Modifier"), HandlerConfigCommon.criticalRoll6Modifier.get()).setDefaultValue(HandlerConfigCommon.defaultCriticalRoll6Modifier).setSaveConsumer(HandlerConfigCommon.criticalRoll6Modifier::set)
                .build());


        Objects.requireNonNull(HandlerConfigCommon.criticalRoll1Probability);
        skills.addEntry(configEntryBuilder.startIntField(Component.translatable("config.cloth-config.justlevelingfork.criticalRoll1Probability"), HandlerConfigCommon.criticalRoll1Probability.get()).setDefaultValue(HandlerConfigCommon.defaultCriticalRoll1Probability).setSaveConsumer(HandlerConfigCommon.criticalRoll1Probability::set)
                .build());


        Objects.requireNonNull(HandlerConfigCommon.luckyDropModifier);
        skills.addEntry(configEntryBuilder.startDoubleField(Component.translatable("config.cloth-config.justlevelingfork.luckyDropModifier"), HandlerConfigCommon.luckyDropModifier.get()).setDefaultValue(HandlerConfigCommon.defaultLuckyDropModifier).setSaveConsumer(HandlerConfigCommon.luckyDropModifier::set)
                .build());


        Objects.requireNonNull(HandlerConfigCommon.luckyDropProbability);
        skills.addEntry(configEntryBuilder.startIntField(Component.translatable("config.cloth-config.justlevelingfork.luckyDropProbability"), HandlerConfigCommon.luckyDropProbability.get()).setDefaultValue(HandlerConfigCommon.defaultLuckyDropProbability).setSaveConsumer(HandlerConfigCommon.luckyDropProbability::set)
                .build());


        Objects.requireNonNull(HandlerConfigCommon.limitBreakerAmplifier);
        skills.addEntry(configEntryBuilder.startDoubleField(Component.translatable("config.cloth-config.justlevelingfork.limitBreakerAmplifier"), HandlerConfigCommon.limitBreakerAmplifier.get()).setDefaultValue(HandlerConfigCommon.defaultLimitBreakerAmplifier).setSaveConsumer(HandlerConfigCommon.limitBreakerAmplifier::set)
                .build());


        Objects.requireNonNull(HandlerConfigCommon.limitBreakerProbability);
        skills.addEntry(configEntryBuilder.startIntField(Component.translatable("config.cloth-config.justlevelingfork.limitBreakerProbability"), HandlerConfigCommon.limitBreakerProbability.get()).setDefaultValue(HandlerConfigCommon.defaultLimitBreakerProbability).setSaveConsumer(HandlerConfigCommon.limitBreakerProbability::set)
                .build());


        return builder.setTransparentBackground(isTransparent).build();
    }
}


