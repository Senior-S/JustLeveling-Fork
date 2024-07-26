package com.seniors.justlevelingfork.registry;

import com.seniors.justlevelingfork.handler.HandlerConfigCommon;
import com.seniors.justlevelingfork.handler.HandlerResources;
import com.seniors.justlevelingfork.registry.aptitude.Aptitude;
import com.seniors.justlevelingfork.registry.passive.Passive;

import java.util.function.Supplier;
import java.util.stream.Collectors;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;
import net.minecraftforge.registries.RegistryObject;


public class RegistryPassives {
    public static final ResourceKey<Registry<Passive>> PASSIVES_KEY = ResourceKey.createRegistryKey(new ResourceLocation("justlevelingfork", "passives"));
    public static final DeferredRegister<Passive> PASSIVES = DeferredRegister.create(PASSIVES_KEY, "justlevelingfork");
    public static final Supplier<IForgeRegistry<Passive>> PASSIVES_REGISTRY = PASSIVES.makeRegistry(() -> (new RegistryBuilder()).disableSaving());

    public static final RegistryObject<Passive> ATTACK_DAMAGE = PASSIVES.register("attack_damage", () -> register("attack_damage", RegistryAptitudes.STRENGTH.get(), HandlerResources.create("textures/skill/strength/passive_attack_damage.png"), Attributes.ATTACK_DAMAGE, "96a891fe-5919-418d-8205-f50464391500", HandlerConfigCommon.attackDamageValue, 5, 8, 11, 14, 17, 20, 23, 26, 29, 32));


    public static final RegistryObject<Passive> ATTACK_KNOCKBACK = PASSIVES.register("attack_knockback", () -> register("attack_knockback", RegistryAptitudes.STRENGTH.get(), HandlerResources.create("textures/skill/strength/passive_attack_knockback.png"), Attributes.ATTACK_KNOCKBACK, "96a891fe-5919-418d-8205-f50464391501", HandlerConfigCommon.attackKnockbackValue, 8, 14, 20, 26, 32));


    public static final RegistryObject<Passive> MAX_HEALTH = PASSIVES.register("max_health", () -> register("max_health", RegistryAptitudes.CONSTITUTION.get(), HandlerResources.create("textures/skill/constitution/passive_max_health.png"), Attributes.MAX_HEALTH, "96a891fe-5919-418d-8205-f50464391502", HandlerConfigCommon.maxHealthValue, 5, 8, 11, 14, 17, 20, 23, 26, 29, 32));


    public static final RegistryObject<Passive> KNOCKBACK_RESISTANCE = PASSIVES.register("knockback_resistance", () -> register("knockback_resistance", RegistryAptitudes.CONSTITUTION.get(), HandlerResources.create("textures/skill/constitution/passive_knockback_resistance.png"), Attributes.KNOCKBACK_RESISTANCE, "96a891fe-5919-418d-8205-f50464391503", HandlerConfigCommon.knockbackResistanceValue, 8, 14, 20, 26, 32));


    public static final RegistryObject<Passive> MOVEMENT_SPEED = PASSIVES.register("movement_speed", () -> register("movement_speed", RegistryAptitudes.DEXTERITY.get(), HandlerResources.create("textures/skill/dexterity/passive_movement_speed.png"), Attributes.MOVEMENT_SPEED, "96a891fe-5919-418d-8205-f50464391504", HandlerConfigCommon.movementSpeedValue, 8, 14, 20, 26, 32));


    public static final RegistryObject<Passive> PROJECTILE_DAMAGE = PASSIVES.register("projectile_damage", () -> register("projectile_damage", RegistryAptitudes.DEXTERITY.get(), HandlerResources.create("textures/skill/dexterity/passive_projectile_damage.png"), RegistryAttributes.PROJECTILE_DAMAGE.get(), "96a891fe-5919-418d-8205-f50464391505", HandlerConfigCommon.projectileDamageValue, 8, 14, 20, 26, 32));


    public static final RegistryObject<Passive> ARMOR = PASSIVES.register("armor", () -> register("armor", RegistryAptitudes.DEFENSE.get(), HandlerResources.create("textures/skill/defense/passive_armor.png"), Attributes.ARMOR, "96a891fe-5919-418d-8205-f50464391506", HandlerConfigCommon.armorValue, 8, 14, 20, 26, 32));


    public static final RegistryObject<Passive> ARMOR_TOUGHNESS = PASSIVES.register("armor_toughness", () -> register("armor_toughness", RegistryAptitudes.DEFENSE.get(), HandlerResources.create("textures/skill/defense/passive_armor_toughness.png"), Attributes.ARMOR_TOUGHNESS, "96a891fe-5919-418d-8205-f50464391507", HandlerConfigCommon.armorToughnessValue, 8, 14, 20, 26, 32));


    public static final RegistryObject<Passive> ATTACK_SPEED = PASSIVES.register("attack_speed", () -> register("attack_speed", RegistryAptitudes.INTELLIGENCE.get(), HandlerResources.create("textures/skill/intelligence/passive_attack_speed.png"), Attributes.ATTACK_SPEED, "96a891fe-5919-418d-8205-f50464391508", HandlerConfigCommon.attackSpeedValue, 8, 14, 20, 26, 32));


    public static final RegistryObject<Passive> ENTITY_REACH = PASSIVES.register("entity_reach", () -> register("entity_reach", RegistryAptitudes.INTELLIGENCE.get(), HandlerResources.create("textures/skill/intelligence/passive_entity_reach.png"), ForgeMod.ENTITY_REACH.get(), "96a891fe-5919-418d-8205-f50464391509", HandlerConfigCommon.entityReachValue, 8, 14, 20, 26, 32));


    public static final RegistryObject<Passive> BLOCK_REACH = PASSIVES.register("block_reach", () -> register("block_reach", RegistryAptitudes.BUILDING.get(), HandlerResources.create("textures/skill/building/passive_block_reach.png"), ForgeMod.BLOCK_REACH.get(), "96a891fe-5919-418d-8205-f50464391510", HandlerConfigCommon.blockReachValue, 8, 14, 20, 26, 32));


    public static final RegistryObject<Passive> BREAK_SPEED = PASSIVES.register("break_speed", () -> register("break_speed", RegistryAptitudes.BUILDING.get(), HandlerResources.create("textures/skill/building/passive_break_speed.png"), RegistryAttributes.BREAK_SPEED.get(), "96a891fe-5919-418d-8205-f50464391511", HandlerConfigCommon.breakSpeedValue, 8, 14, 20, 26, 32));


    public static final RegistryObject<Passive> BENEFICIAL_EFFECT = PASSIVES.register("beneficial_effect", () -> register("beneficial_effect", RegistryAptitudes.MAGIC.get(), HandlerResources.create("textures/skill/magic/passive_beneficial_effect.png"), RegistryAttributes.BENEFICIAL_EFFECT.get(), "96a891fe-5919-418d-8205-f50464391512", HandlerConfigCommon.beneficialEffectValue, 5, 8, 11, 14, 17, 20, 23, 26, 29, 32));


    public static final RegistryObject<Passive> MAGIC_RESIST = PASSIVES.register("magic_resist", () -> register("magic_resist", RegistryAptitudes.MAGIC.get(), HandlerResources.create("textures/skill/magic/passive_magic_resist.png"), RegistryAttributes.MAGIC_RESIST.get(), "96a891fe-5919-418d-8205-f50464391513", HandlerConfigCommon.magicResistValue, 8, 14, 20, 26, 32));


    public static final RegistryObject<Passive> CRITICAL_DAMAGE = PASSIVES.register("critical_damage", () -> register("critical_damage", RegistryAptitudes.LUCK.get(), HandlerResources.create("textures/skill/luck/passive_critical_damage.png"), RegistryAttributes.CRITICAL_DAMAGE.get(), "96a891fe-5919-418d-8205-f50464391515", HandlerConfigCommon.criticalDamageValue, 5, 8, 11, 14, 17, 20, 23, 26, 29, 32));


    public static final RegistryObject<Passive> LUCK = PASSIVES.register("luck", () -> register("luck", RegistryAptitudes.LUCK.get(), HandlerResources.create("textures/skill/luck/passive_luck.png"), Attributes.LUCK, "96a891fe-5919-418d-8205-f50464391514", HandlerConfigCommon.luckValue, 5, 8, 11, 14, 17, 20, 23, 26, 29, 32));


    private static Passive register(String name, Aptitude aptitude, ResourceLocation texture, Attribute attribute, String attributeUuid, Object attributeValue, int... levelsRequired) {
        ResourceLocation key = new ResourceLocation("justlevelingfork", name);
        return new Passive(key, aptitude, texture, attribute, attributeUuid, attributeValue, levelsRequired);
    }

    public static void load(IEventBus eventBus) {
        PASSIVES.register(eventBus);
    }

    public static Passive getPassive(String passiveName) {
        return PASSIVES_REGISTRY.get().getValues().stream().collect(Collectors.toMap(Passive::getName, Passive::get)).get(passiveName);
    }
}


