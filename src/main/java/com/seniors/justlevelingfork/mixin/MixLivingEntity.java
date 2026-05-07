package com.seniors.justlevelingfork.mixin;

import com.seniors.justlevelingfork.common.capability.AptitudeCapability;
import com.seniors.justlevelingfork.network.packet.common.CounterAttackSP;
import com.seniors.justlevelingfork.registry.RegistryAttributes;
import com.seniors.justlevelingfork.registry.RegistrySkills;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.PotionItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.Inject;

@Mixin(LivingEntity.class)
public abstract class MixLivingEntity {
    @Unique
    private final LivingEntity this$class = (LivingEntity) (Object) this;

    @ModifyVariable(method = "getDamageAfterMagicAbsorb", at = @At("HEAD"), argsOnly = true)
    private float justlevelingfork$reduceMagicDamage(float damage, DamageSource source) {
        AttributeInstance magicResist = this$class.getAttribute(RegistryAttributes.MAGIC_RESIST.get());
        if (magicResist != null && source.getDirectEntity() != source.getEntity() && magicResist.getValue() > 0.0D) {
            return (float) (damage - damage * magicResist.getValue());
        }

        return damage;
    }

    @ModifyVariable(method = "addEffect(Lnet/minecraft/world/effect/MobEffectInstance;Lnet/minecraft/world/entity/Entity;)Z", at = @At("HEAD"), argsOnly = true)
    private MobEffectInstance justlevelingfork$modifyPlayerEffect(MobEffectInstance effect, MobEffectInstance originalEffect, Entity source) {
        if (!(this$class instanceof Player player)) {
            return effect;
        }

        int duration = effect.getDuration();
        int amplifier = effect.getAmplifier();

        if (effect.getEffect().value().getCategory() == MobEffectCategory.HARMFUL && RegistrySkills.LION_HEART != null && RegistrySkills.LION_HEART.get().isEnabled(player)) {
            duration -= (int) (effect.getDuration() * RegistrySkills.LION_HEART.get().getValue()[0] / 100.0D);
        }

        if (effect.getEffect().value().getCategory() == MobEffectCategory.BENEFICIAL && player.isUsingItem() && (player.getMainHandItem().getItem() instanceof PotionItem || player.getOffhandItem().getItem() instanceof PotionItem)) {
            if (RegistrySkills.ALCHEMY_MANIPULATION != null && RegistrySkills.ALCHEMY_MANIPULATION.get().isEnabled(player)) {
                amplifier += (int) RegistrySkills.ALCHEMY_MANIPULATION.get().getValue()[0];
            }

            duration += (int) (player.getAttributeValue(RegistryAttributes.BENEFICIAL_EFFECT.get()) * 20.0D);
        }

        return new MobEffectInstance(effect.getEffect(), Math.max(0, duration), amplifier, effect.isAmbient(), effect.isVisible(), effect.showIcon());
    }

    @Inject(method = "hurt", at = @At("RETURN"))
    private void justlevelingfork$triggerCounterAttack(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        if (!cir.getReturnValue() || !(this$class instanceof ServerPlayer player) || !(source.getEntity() instanceof LivingEntity attacker)) {
            return;
        }

        AttributeInstance attackDamage = attacker.getAttribute(Attributes.ATTACK_DAMAGE);
        AptitudeCapability provider = AptitudeCapability.get(player);
        if (attackDamage != null && provider != null && RegistrySkills.COUNTER_ATTACK != null && RegistrySkills.COUNTER_ATTACK.get().isEnabled(player)) {
            CounterAttackSP.apply(player, true, (float) (attackDamage.getValue() * RegistrySkills.COUNTER_ATTACK.get().getValue()[1] / 100.0D));
        }
    }

    @Inject(method = "getVisibilityPercent", at = @At("RETURN"), cancellable = true)
    public void justlevelingfork$getVisibilityPercent(Entity source, CallbackInfoReturnable<Double> cir) {
        double visibilityPercent = cir.getReturnValue();
        if (this$class instanceof ServerPlayer player && RegistrySkills.STEALTH_MASTERY != null && RegistrySkills.STEALTH_MASTERY.get().isEnabled(player)) {
            double modifier = player.isShiftKeyDown() ? RegistrySkills.STEALTH_MASTERY.get().getValue()[0] : RegistrySkills.STEALTH_MASTERY.get().getValue()[1];
            cir.setReturnValue(visibilityPercent * modifier / 100.0D);
        }
    }
}
