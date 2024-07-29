package com.seniors.justlevelingfork.registry;
import com.seniors.justlevelingfork.JustLevelingFork;
import com.seniors.justlevelingfork.handler.HandlerResources;
import com.seniors.justlevelingfork.registry.aptitude.Aptitude;

import java.util.function.Supplier;
import java.util.stream.Collectors;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.*;

public class RegistryAptitudes {

    private static final ResourceKey<Registry<Aptitude>> APTITUDES_KEY = ResourceKey.createRegistryKey(new ResourceLocation(JustLevelingFork.MOD_ID, "aptitudes"));
    private static final DeferredRegister<Aptitude> APTITUDES = DeferredRegister.create(APTITUDES_KEY, JustLevelingFork.MOD_ID);
    public static Supplier<IForgeRegistry<Aptitude>> APTITUDES_REGISTRY = APTITUDES.makeRegistry(() -> new RegistryBuilder<Aptitude>().disableSaving());

    public static final RegistryObject<Aptitude> STRENGTH = APTITUDES.register("strength", () -> register(0, "strength", HandlerResources.STRENGTH_LOCKED_ICON, new ResourceLocation("minecraft:textures/block/yellow_terracotta.png")));
    public static final RegistryObject<Aptitude> CONSTITUTION = APTITUDES.register("constitution", () -> register(1, "constitution", HandlerResources.CONSTITUTION_LOCKED_ICON, new ResourceLocation("minecraft:textures/block/red_terracotta.png")));
    public static final RegistryObject<Aptitude> DEXTERITY = APTITUDES.register("dexterity", () -> register(2, "dexterity", HandlerResources.DEXTERITY_LOCKED_ICON, new ResourceLocation("minecraft:textures/block/blue_terracotta.png")));
    public static final RegistryObject<Aptitude> DEFENSE = APTITUDES.register("defense", () -> register(3, "defense", HandlerResources.DEFENSE_LOCKED_ICON, new ResourceLocation("minecraft:textures/block/cyan_terracotta.png")));
    public static final RegistryObject<Aptitude> INTELLIGENCE = APTITUDES.register("intelligence", () -> register(4, "intelligence", HandlerResources.INTELLIGENCE_LOCKED_ICON, new ResourceLocation("minecraft:textures/block/orange_terracotta.png")));
    public static final RegistryObject<Aptitude> BUILDING = APTITUDES.register("building", () -> register(5, "building", HandlerResources.BUILDING_LOCKED_ICON, new ResourceLocation("minecraft:textures/block/brown_terracotta.png")));
    public static final RegistryObject<Aptitude> MAGIC = APTITUDES.register("magic", () -> register(6, "magic", HandlerResources.MAGIC_LOCKED_ICON, new ResourceLocation("minecraft:textures/block/purple_terracotta.png")));
    public static final RegistryObject<Aptitude> LUCK = APTITUDES.register("luck", () -> register(7, "luck", HandlerResources.LUCK_LOCKED_ICON, new ResourceLocation("minecraft:textures/block/lime_terracotta.png")));

    public static void load(IEventBus eventBus) {
        APTITUDES.register(eventBus);
    }

    private static Aptitude register(int index, String name, ResourceLocation[] lockedTexture, ResourceLocation background) {
        ResourceLocation key = new ResourceLocation(JustLevelingFork.MOD_ID, name);
        return new Aptitude(index, key, lockedTexture, background);
    }

    public static Aptitude getAptitude(String aptitudeName) {
        return APTITUDES_REGISTRY.get().getValues().stream().collect(Collectors.toMap(Aptitude::getName, Aptitude::get)).get(aptitudeName);
    }
}


