package com.seniors.justlevelingfork.mixin;

import com.seniors.justlevelingfork.registry.RegistryAttributes;
import com.seniors.justlevelingfork.registry.RegistrySkills;

import java.util.Map;
import javax.annotation.Nullable;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.MobEffectEvent;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({LivingEntity.class})
public abstract class MixLivingEntity {
    @Shadow
    @Final
    private Map<MobEffect, MobEffectInstance> activeEffects;
    @Unique
    private final LivingEntity this$class = (LivingEntity) (Object) this;

    @Shadow
    protected abstract void onEffectAdded(MobEffectInstance paramMobEffectInstance, @Nullable Entity paramEntity);

    @Shadow
    protected abstract void onEffectUpdated(MobEffectInstance paramMobEffectInstance, boolean paramBoolean, @Nullable Entity paramEntity);

    @ModifyVariable(method = {"getDamageAfterArmorAbsorb"}, at = @At("HEAD"), argsOnly = true)
    private float additionalEntityAttributes$reduceMagicDamage(float damage, DamageSource source) {
        AttributeInstance magicResist = this$class.getAttribute(RegistryAttributes.MAGIC_RESIST.get());
        if (magicResist == null) {
            return damage;
        }
        if (source.isIndirect() && magicResist.getValue() > 0.0D) {
            damage = (float) (damage - damage * magicResist.getValue());
        }
        return damage;
    }

    @Shadow
    public abstract boolean canBeAffected(MobEffectInstance paramMobEffectInstance);

    @Shadow
    @Deprecated
    public abstract boolean canBreatheUnderwater();

    @Inject(method = {"addEffect(Lnet/minecraft/world/effect/MobEffectInstance;)Z"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;addEffect(Lnet/minecraft/world/effect/MobEffectInstance;Lnet/minecraft/world/entity/Entity;)Z", opcode = 181)}, cancellable = true)
    public final void onAddEffect(MobEffectInstance effect, CallbackInfoReturnable<Boolean> info) {
        if (this.this$class instanceof Player player) {
            info.cancel();
            info.setReturnValue(this$onDrinkPotion(effect, player));
        }

    }

    @Inject(method = {"addEffect(Lnet/minecraft/world/effect/MobEffectInstance;Lnet/minecraft/world/entity/Entity;)Z"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;canBeAffected(Lnet/minecraft/world/effect/MobEffectInstance;)Z", opcode = 181)}, cancellable = true)
    public final void onAddEffect(MobEffectInstance effect, Entity source, CallbackInfoReturnable<Boolean> info) {
        if (this.this$class instanceof Player player) {
            info.cancel();
            info.setReturnValue(this$onAddEffect(effect, player));
        }

    }

    @Unique
    private boolean this$onAddEffect(MobEffectInstance effect, Player player) {
        int duration = effect.getDuration();
        if (effect.getEffect().getCategory() == MobEffectCategory.HARMFUL && RegistrySkills.LION_HEART.get().isEnabled(player))
            duration -= (int) (effect.getDuration() * RegistrySkills.LION_HEART.get().getValue()[0] / 100.0D);


        MobEffectInstance newEffect = new MobEffectInstance(effect.getEffect(), duration, effect.getAmplifier());

        if (!canBeAffected(effect)) {
            return false;
        }
        MobEffectInstance mobeffectinstance = this.activeEffects.get(effect.getEffect());
        MinecraftForge.EVENT_BUS.post(new MobEffectEvent.Added(this.this$class, mobeffectinstance, effect, player));
        if (mobeffectinstance == null) {
            this.activeEffects.put(newEffect.getEffect(), newEffect);
            onEffectAdded(newEffect, player);
            return true;
        }
        if (mobeffectinstance.update(effect)) {
            onEffectUpdated(newEffect, true, player);
            return true;
        }
        return false;
    }


    @Unique
    private boolean this$onDrinkPotion(MobEffectInstance effect, Player player) {
        int duration = effect.getDuration();
        int amplifier = effect.getAmplifier();

        if (effect.getEffect().getCategory() == MobEffectCategory.BENEFICIAL &&
                player.isUsingItem() && (player.getMainHandItem().getItem() instanceof net.minecraft.world.item.PotionItem || player.getOffhandItem().getItem() instanceof net.minecraft.world.item.PotionItem)) {
            if (RegistrySkills.ALCHEMY_MANIPULATION.get().isEnabled(player))
                amplifier += (int) RegistrySkills.ALCHEMY_MANIPULATION.get().getValue()[0];
            float newDuration = (int) (player.getAttributeValue(RegistryAttributes.BENEFICIAL_EFFECT.get()) * 20.0D);
            duration = (int) (duration + newDuration);
        }


        MobEffectInstance newEffect = new MobEffectInstance(effect.getEffect(), duration, amplifier);
        if (!canBeAffected(effect)) {
            return false;
        }
        MobEffectInstance mobeffectinstance = this.activeEffects.get(effect.getEffect());
        MinecraftForge.EVENT_BUS.post(new MobEffectEvent.Added(this.this$class, mobeffectinstance, effect, player));
        if (mobeffectinstance == null) {
            this.activeEffects.put(newEffect.getEffect(), newEffect);
            onEffectAdded(newEffect, player);
            return true;
        }
        if (mobeffectinstance.update(effect)) {
            onEffectUpdated(newEffect, true, player);
            return true;
        }
        return false;
    }


    @Inject(method = {"getVisibilityPercent"}, at = {@At("TAIL")}, cancellable = true)
    public void getVisibilityPercent(Entity source, CallbackInfoReturnable<Double> cir) {
        double visibilityPercent = cir.getReturnValue();
        if (this.this$class instanceof ServerPlayer player) {
            double isSneaking = player.isShiftKeyDown() ? RegistrySkills.STEALTH_MASTERY.get().getValue()[0] : RegistrySkills.STEALTH_MASTERY.get().getValue()[1];
            if (RegistrySkills.STEALTH_MASTERY.get().isEnabled(player))
                cir.setReturnValue(visibilityPercent * isSneaking / 100.0D);
        }

    }
}


