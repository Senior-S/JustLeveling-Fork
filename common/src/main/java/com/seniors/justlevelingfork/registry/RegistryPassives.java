package com.seniors.justlevelingfork.registry;

import com.seniors.justlevelingfork.handler.HandlerCommonConfig;
import com.seniors.justlevelingfork.handler.HandlerResources;
import com.seniors.justlevelingfork.registry.aptitude.Aptitude;
import com.seniors.justlevelingfork.registry.passive.Passive;
import com.mojang.serialization.Lifecycle;
import net.minecraft.core.MappedRegistry;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.Attributes;

import java.util.Locale;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class RegistryPassives {
    public static final ResourceKey<Registry<Passive>> PASSIVES_KEY = ResourceKey.createRegistryKey(RegistryItems.id("passives"));
    public static final Registry<Passive> PASSIVES = new MappedRegistry<>(PASSIVES_KEY, Lifecycle.stable());
    public static final FabricRegistryView<Passive> PASSIVES_REGISTRY = new FabricRegistryView<>(PASSIVES);

    public static final FabricRegistryRef<Passive> ATTACK_DAMAGE = register("attack_damage", RegistryAptitudes.STRENGTH.get(), HandlerResources.create("textures/skill/strength/passive_attack_damage.png"), Attributes.ATTACK_DAMAGE, "96a891fe-5919-418d-8205-f50464391500", HandlerCommonConfig.HANDLER.instance().attackDamageValue, HandlerCommonConfig.HANDLER.instance().attackPassiveLevels);
    public static final FabricRegistryRef<Passive> ATTACK_KNOCKBACK = register("attack_knockback", RegistryAptitudes.STRENGTH.get(), HandlerResources.create("textures/skill/strength/passive_attack_knockback.png"), Attributes.ATTACK_KNOCKBACK, "96a891fe-5919-418d-8205-f50464391501", HandlerCommonConfig.HANDLER.instance().attackKnockbackValue, HandlerCommonConfig.HANDLER.instance().attackKnockbackPassiveLevels);
    public static final FabricRegistryRef<Passive> MAX_HEALTH = register("max_health", RegistryAptitudes.CONSTITUTION.get(), HandlerResources.create("textures/skill/constitution/passive_max_health.png"), Attributes.MAX_HEALTH, "96a891fe-5919-418d-8205-f50464391502", HandlerCommonConfig.HANDLER.instance().maxHealthValue, HandlerCommonConfig.HANDLER.instance().maxHealthPassiveLevels);
    public static final FabricRegistryRef<Passive> KNOCKBACK_RESISTANCE = register("knockback_resistance", RegistryAptitudes.CONSTITUTION.get(), HandlerResources.create("textures/skill/constitution/passive_knockback_resistance.png"), Attributes.KNOCKBACK_RESISTANCE, "96a891fe-5919-418d-8205-f50464391503", HandlerCommonConfig.HANDLER.instance().knockbackResistanceValue, HandlerCommonConfig.HANDLER.instance().knockbackResistancePassiveLevels);
    public static final FabricRegistryRef<Passive> MOVEMENT_SPEED = register("movement_speed", RegistryAptitudes.DEXTERITY.get(), HandlerResources.create("textures/skill/dexterity/passive_movement_speed.png"), Attributes.MOVEMENT_SPEED, "96a891fe-5919-418d-8205-f50464391504", HandlerCommonConfig.HANDLER.instance().movementSpeedValue, HandlerCommonConfig.HANDLER.instance().movementSpeedPassiveLevels);
    public static final FabricRegistryRef<Passive> PROJECTILE_DAMAGE = register("projectile_damage", RegistryAptitudes.DEXTERITY.get(), HandlerResources.create("textures/skill/dexterity/passive_projectile_damage.png"), RegistryAttributes.PROJECTILE_DAMAGE, "96a891fe-5919-418d-8205-f50464391505", HandlerCommonConfig.HANDLER.instance().projectileDamageValue, HandlerCommonConfig.HANDLER.instance().projectileDamagePassiveLevels);
    public static final FabricRegistryRef<Passive> ARMOR = register("armor", RegistryAptitudes.DEFENSE.get(), HandlerResources.create("textures/skill/defense/passive_armor.png"), Attributes.ARMOR, "96a891fe-5919-418d-8205-f50464391506", HandlerCommonConfig.HANDLER.instance().armorValue, HandlerCommonConfig.HANDLER.instance().armorPassiveLevels);
    public static final FabricRegistryRef<Passive> ARMOR_TOUGHNESS = register("armor_toughness", RegistryAptitudes.DEFENSE.get(), HandlerResources.create("textures/skill/defense/passive_armor_toughness.png"), Attributes.ARMOR_TOUGHNESS, "96a891fe-5919-418d-8205-f50464391507", HandlerCommonConfig.HANDLER.instance().armorToughnessValue, HandlerCommonConfig.HANDLER.instance().armorToughnessPassiveLevels);
    public static final FabricRegistryRef<Passive> ATTACK_SPEED = register("attack_speed", RegistryAptitudes.INTELLIGENCE.get(), HandlerResources.create("textures/skill/intelligence/passive_attack_speed.png"), Attributes.ATTACK_SPEED, "96a891fe-5919-418d-8205-f50464391508", HandlerCommonConfig.HANDLER.instance().attackSpeedValue, HandlerCommonConfig.HANDLER.instance().attackSpeedPassiveLevels);
    public static final FabricRegistryRef<Passive> ENTITY_REACH = register("entity_reach", RegistryAptitudes.INTELLIGENCE.get(), HandlerResources.create("textures/skill/intelligence/passive_entity_reach.png"), Attributes.ENTITY_INTERACTION_RANGE, "96a891fe-5919-418d-8205-f50464391509", HandlerCommonConfig.HANDLER.instance().entityReachValue, HandlerCommonConfig.HANDLER.instance().entityReachPassiveLevels);
    public static final FabricRegistryRef<Passive> BLOCK_REACH = register("block_reach", RegistryAptitudes.BUILDING.get(), HandlerResources.create("textures/skill/building/passive_block_reach.png"), Attributes.BLOCK_INTERACTION_RANGE, "96a891fe-5919-418d-8205-f50464391510", HandlerCommonConfig.HANDLER.instance().blockReachValue, HandlerCommonConfig.HANDLER.instance().blockReachPassiveLevels);
    public static final FabricRegistryRef<Passive> BREAK_SPEED = register("break_speed", RegistryAptitudes.BUILDING.get(), HandlerResources.create("textures/skill/building/passive_break_speed.png"), Attributes.BLOCK_BREAK_SPEED, "96a891fe-5919-418d-8205-f50464391511", HandlerCommonConfig.HANDLER.instance().breakSpeedValue, HandlerCommonConfig.HANDLER.instance().breakSpeedPassiveLevels);
    public static final FabricRegistryRef<Passive> BENEFICIAL_EFFECT = register("beneficial_effect", RegistryAptitudes.MAGIC.get(), HandlerResources.create("textures/skill/magic/passive_beneficial_effect.png"), RegistryAttributes.BENEFICIAL_EFFECT, "96a891fe-5919-418d-8205-f50464391512", HandlerCommonConfig.HANDLER.instance().beneficialEffectValue, HandlerCommonConfig.HANDLER.instance().beneficialEffectPassiveLevels);
    public static final FabricRegistryRef<Passive> MAGIC_RESIST = register("magic_resist", RegistryAptitudes.MAGIC.get(), HandlerResources.create("textures/skill/magic/passive_magic_resist.png"), RegistryAttributes.MAGIC_RESIST, "96a891fe-5919-418d-8205-f50464391513", HandlerCommonConfig.HANDLER.instance().magicResistValue, HandlerCommonConfig.HANDLER.instance().magicResistPassiveLevels);
    public static final FabricRegistryRef<Passive> CRITICAL_DAMAGE = register("critical_damage", RegistryAptitudes.LUCK.get(), HandlerResources.create("textures/skill/luck/passive_critical_damage.png"), RegistryAttributes.CRITICAL_DAMAGE, "96a891fe-5919-418d-8205-f50464391515", HandlerCommonConfig.HANDLER.instance().criticalDamageValue, HandlerCommonConfig.HANDLER.instance().criticalDamagePassiveLevels);
    public static final FabricRegistryRef<Passive> LUCK = register("luck", RegistryAptitudes.LUCK.get(), HandlerResources.create("textures/skill/luck/passive_luck.png"), Attributes.LUCK, "96a891fe-5919-418d-8205-f50464391514", HandlerCommonConfig.HANDLER.instance().luckValue, HandlerCommonConfig.HANDLER.instance().luckPassiveLevels);

    private static FabricRegistryRef<Passive> register(String name, Aptitude aptitude, ResourceLocation texture, Holder<Attribute> attribute, String attributeUuid, Object attributeValue, int... levelsRequired) {
        return register(name, aptitude, texture, () -> attribute, attributeUuid, attributeValue, levelsRequired);
    }

    private static FabricRegistryRef<Passive> register(String name, Aptitude aptitude, ResourceLocation texture, Supplier<Holder<Attribute>> attribute, String attributeUuid, Object attributeValue, int... levelsRequired) {
        ResourceLocation key = RegistryItems.id(name);
        return new FabricRegistryRef<>(Registry.register(PASSIVES, key, new Passive(key, aptitude, texture, attribute, attributeUuid, attributeValue, levelsRequired)));
    }

    public static void load() {
    }

    public static Passive getPassive(String passiveName) {
        return PASSIVES_REGISTRY.getValues().stream().collect(Collectors.toMap(Passive::getName, Passive::get)).get(passiveName);
    }

    public static boolean isEnabled(Passive passive) {
        return passive != null && isEnabled(passive.key);
    }

    public static boolean isEnabled(ResourceLocation key) {
        return HandlerCommonConfig.HANDLER.instance().disabledPassives.stream()
                .map(RegistryPassives::normalizeConfiguredName)
                .noneMatch(disabledPassive -> disabledPassive.equals(key.toString()) || disabledPassive.equals(key.getPath()));
    }

    private static String normalizeConfiguredName(String name) {
        return name == null ? "" : name.trim().toLowerCase(Locale.ROOT);
    }
}
