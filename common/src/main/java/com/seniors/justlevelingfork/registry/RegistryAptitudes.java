package com.seniors.justlevelingfork.registry;

import com.seniors.justlevelingfork.JustLevelingFork;
import com.seniors.justlevelingfork.handler.HandlerResources;
import com.seniors.justlevelingfork.registry.aptitude.Aptitude;
import com.mojang.serialization.Lifecycle;
import net.minecraft.core.MappedRegistry;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

import java.util.stream.Collectors;

public class RegistryAptitudes {
    private static final ResourceKey<Registry<Aptitude>> APTITUDES_KEY = ResourceKey.createRegistryKey(RegistryItems.id("aptitudes"));
    private static final Registry<Aptitude> APTITUDES = new MappedRegistry<>(APTITUDES_KEY, Lifecycle.stable());
    public static final FabricRegistryView<Aptitude> APTITUDES_REGISTRY = new FabricRegistryView<>(APTITUDES);

    public static final FabricRegistryRef<Aptitude> STRENGTH = register(0, "strength", HandlerResources.STRENGTH_LOCKED_ICON, mc("textures/block/yellow_terracotta.png"));
    public static final FabricRegistryRef<Aptitude> CONSTITUTION = register(1, "constitution", HandlerResources.CONSTITUTION_LOCKED_ICON, mc("textures/block/red_terracotta.png"));
    public static final FabricRegistryRef<Aptitude> DEXTERITY = register(2, "dexterity", HandlerResources.DEXTERITY_LOCKED_ICON, mc("textures/block/blue_terracotta.png"));
    public static final FabricRegistryRef<Aptitude> DEFENSE = register(3, "defense", HandlerResources.DEFENSE_LOCKED_ICON, mc("textures/block/cyan_terracotta.png"));
    public static final FabricRegistryRef<Aptitude> INTELLIGENCE = register(4, "intelligence", HandlerResources.INTELLIGENCE_LOCKED_ICON, mc("textures/block/orange_terracotta.png"));
    public static final FabricRegistryRef<Aptitude> BUILDING = register(5, "building", HandlerResources.BUILDING_LOCKED_ICON, mc("textures/block/brown_terracotta.png"));
    public static final FabricRegistryRef<Aptitude> MAGIC = register(6, "magic", HandlerResources.MAGIC_LOCKED_ICON, mc("textures/block/purple_terracotta.png"));
    public static final FabricRegistryRef<Aptitude> LUCK = register(7, "luck", HandlerResources.LUCK_LOCKED_ICON, mc("textures/block/lime_terracotta.png"));

    public static void load() {
    }

    private static FabricRegistryRef<Aptitude> register(int index, String name, ResourceLocation[] lockedTexture, ResourceLocation background) {
        ResourceLocation key = RegistryItems.id(name);
        return new FabricRegistryRef<>(Registry.register(APTITUDES, key, new Aptitude(index, key, lockedTexture, background)));
    }

    private static ResourceLocation mc(String path) {
        return ResourceLocation.fromNamespaceAndPath("minecraft", path);
    }

    public static Aptitude getAptitude(String aptitudeName) {
        return APTITUDES_REGISTRY.getValues().stream().collect(Collectors.toMap(Aptitude::getName, Aptitude::get)).get(aptitudeName.toLowerCase());
    }
}
