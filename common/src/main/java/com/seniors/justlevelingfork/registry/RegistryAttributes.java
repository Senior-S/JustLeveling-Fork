package com.seniors.justlevelingfork.registry;

import com.seniors.justlevelingfork.JustLevelingFork;
import com.seniors.justlevelingfork.common.capability.AptitudeCapability;
import com.seniors.justlevelingfork.registry.passive.Passive;
import dev.architectury.platform.Platform;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraft.world.entity.player.Player;

import java.util.UUID;

public class RegistryAttributes {
    private static final DeferredRegister<Attribute> ATTRIBUTES = DeferredRegister.create(JustLevelingFork.MOD_ID, Registries.ATTRIBUTE);

    public static final FabricRegistryRef<Holder<Attribute>> BREAK_SPEED = register("break_speed");
    public static final FabricRegistryRef<Holder<Attribute>> CRITICAL_DAMAGE = register("critical_damage");
    public static final FabricRegistryRef<Holder<Attribute>> PROJECTILE_DAMAGE = register("projectile_damage");
    public static final FabricRegistryRef<Holder<Attribute>> BENEFICIAL_EFFECT = register("beneficial_effect");
    public static final FabricRegistryRef<Holder<Attribute>> MAGIC_RESIST = register("magic_resist");

    public static void load() {
        ATTRIBUTES.register();

        if (Platform.isFabric()) {
            registerFabricPlayerAttributes(Player.createAttributes()
                    .add(BREAK_SPEED.get())
                    .add(CRITICAL_DAMAGE.get())
                    .add(PROJECTILE_DAMAGE.get())
                    .add(BENEFICIAL_EFFECT.get())
                    .add(MAGIC_RESIST.get()));
        }
    }

    private static void registerFabricPlayerAttributes(AttributeSupplier.Builder builder) {
        try {
            Class<?> registryClass = Class.forName("net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry");
            registryClass.getMethod("register", EntityType.class, AttributeSupplier.Builder.class)
                    .invoke(null, EntityType.PLAYER, builder);
        } catch (ReflectiveOperationException exception) {
            throw new IllegalStateException("Failed to register Fabric player attributes", exception);
        }
    }

    private static FabricRegistryRef<Holder<Attribute>> register(String name) {
        RegistrySupplier<Attribute> attribute = ATTRIBUTES.register(name,
                () -> new RangedAttribute("attribute.name." + JustLevelingFork.MOD_ID + "." + name, 0.0D, 0.0D, 1024.0D).setSyncable(true));
        return new FabricRegistryRef<>(() -> BuiltInRegistries.ATTRIBUTE.wrapAsHolder(attribute.get()));
    }

    public static void modifierAttributes(ServerPlayer serverPlayer) {
        AptitudeCapability aptitudeCapability = AptitudeCapability.get(serverPlayer);
        if (aptitudeCapability == null) return;

        for (Passive passive : RegistryPassives.PASSIVES_REGISTRY.getValues()) {
            new registerAttribute(serverPlayer, passive.getAttribute(), passive.getValue() / passive.levelsRequired.length * passive.getLevel(serverPlayer), UUID.fromString(passive.attributeUuid)).amplifyAttribute(RegistryPassives.isEnabled(passive));
        }
    }

    public static class registerAttribute {
        private final Player player;
        private final Holder<Attribute> attribute;
        private final double modifier;
        private final UUID uuid;

        public registerAttribute(Player player, Holder<Attribute> attribute, double modifier, UUID uuid) {
            this.player = player;
            this.attribute = attribute;
            this.modifier = modifier;
            this.uuid = uuid;
        }

        public void amplifyAttribute(boolean isEnabled) {
            AttributeInstance instance = this.player.getAttribute(this.attribute);
            if (instance == null) return;

            ResourceLocation id = ResourceLocation.fromNamespaceAndPath(JustLevelingFork.MOD_ID, uuid.toString());
            AttributeModifier oldModifier = instance.getModifier(id);
            if (oldModifier != null) instance.removeModifier(oldModifier);

            AttributeModifier newModifier = new AttributeModifier(id, this.modifier, AttributeModifier.Operation.ADD_VALUE);
            if (isEnabled) {
                instance.addPermanentModifier(newModifier);
            } else {
                instance.removeModifier(id);
            }
        }
    }
}
