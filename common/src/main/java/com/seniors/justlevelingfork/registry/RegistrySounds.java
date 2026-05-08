package com.seniors.justlevelingfork.registry;

import com.seniors.justlevelingfork.JustLevelingFork;
import dev.architectury.registry.registries.DeferredRegister;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;

public class RegistrySounds {
    private static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(JustLevelingFork.MOD_ID, Registries.SOUND_EVENT);

    public static FabricRegistryRef<SoundEvent> LIMIT_BREAKER = register("mortal_strike");
    public static FabricRegistryRef<SoundEvent> GAIN_TITLE = register("gain_title");

    public static void load() {
        SOUNDS.register();
    }

    private static FabricRegistryRef<SoundEvent> register(String name) {
        ResourceLocation id = RegistryItems.id(name);
        return new FabricRegistryRef<>(SOUNDS.register(name, () -> SoundEvent.createVariableRangeEvent(id)));
    }
}
