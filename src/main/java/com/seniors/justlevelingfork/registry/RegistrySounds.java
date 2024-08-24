package com.seniors.justlevelingfork.registry;

import com.seniors.justlevelingfork.JustLevelingFork;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class RegistrySounds {
    public static final DeferredRegister<SoundEvent> REGISTER;
    public static RegistryObject<SoundEvent> LIMIT_BREAKER;
    public static RegistryObject<SoundEvent> GAIN_TITLE;

    public static void load(IEventBus eventBus) {
        REGISTER.register(eventBus);
    }

    static {
        REGISTER = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, JustLevelingFork.MOD_ID);
        LIMIT_BREAKER = REGISTER.register("mortal_strike", () -> {
            return new SoundEvent(new ResourceLocation(JustLevelingFork.MOD_ID, "mortal_strike"));
        });
        GAIN_TITLE = REGISTER.register("gain_title", () -> {
            return new SoundEvent(new ResourceLocation(JustLevelingFork.MOD_ID, "gain_title"));
        });
    }
}


