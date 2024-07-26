package com.seniors.justlevelingfork.registry;

import com.seniors.justlevelingfork.handler.HandlerConfigCommon;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;

public class RegistryEffects {
    public static class addEffect {
        public final ServerPlayer player;
        public final boolean toggle;
        public final MobEffect effect;

        public addEffect(ServerPlayer player, boolean toggle, MobEffect mobEffect) {
            this.player = player;
            this.toggle = toggle;
            this.effect = mobEffect;
        }

        public void add(int duration) {
            if (this.toggle) {
                MobEffectInstance instance = new MobEffectInstance(this.effect, duration, 0, false, false, HandlerConfigCommon.showPotionsHud.get());
                this.player.addEffect(instance);
            }
        }

        public void add(int duration, int amplifier) {
            if (this.toggle) {
                MobEffectInstance instance = new MobEffectInstance(this.effect, duration, amplifier, false, false, HandlerConfigCommon.showPotionsHud.get());
                this.player.addEffect(instance);
            }
        }
    }
}


