package com.seniors.justlevelingfork.registry;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;

public class RegistrySounds {
    public static FabricRegistryRef<SoundEvent> LIMIT_BREAKER = register("mortal_strike");
    public static FabricRegistryRef<SoundEvent> GAIN_TITLE = register("gain_title");

    public static void load() {
    }

    private static FabricRegistryRef<SoundEvent> register(String name) {
        ResourceLocation id = RegistryItems.id(name);
        return new FabricRegistryRef<>(Registry.register(BuiltInRegistries.SOUND_EVENT, id, SoundEvent.createVariableRangeEvent(id)));
    }
}
