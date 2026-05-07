package com.seniors.justlevelingfork.mixin;

import com.seniors.justlevelingfork.common.capability.AptitudeCapability;
import com.seniors.justlevelingfork.common.capability.AptitudeCapabilityHolder;
import com.seniors.justlevelingfork.handler.HandlerCommonConfig;
import com.seniors.justlevelingfork.network.packet.client.PlayerMessagesCP;
import com.seniors.justlevelingfork.registry.RegistryAttributes;
import com.seniors.justlevelingfork.registry.RegistrySkills;
import com.seniors.justlevelingfork.registry.RegistryTags;
import com.seniors.justlevelingfork.registry.RegistryTitles;
import com.seniors.justlevelingfork.registry.title.Title;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({Player.class})
public abstract class MixPlayer extends LivingEntity implements AptitudeCapabilityHolder {
    @Unique
    private static final String JUST_LEVELING_APTITUDE_DATA = "JustLevelingForkAptitudes";

    @Unique
    private final Player this$class = (Player) (Object) this;

    @Unique
    private final AptitudeCapability justlevelingfork$aptitudeCapability = new AptitudeCapability();

    @Unique
    private boolean justlevelingfork$criticalAttack;

    @Unique
    private boolean justlevelingfork$vanillaCriticalAttack;

    protected MixPlayer(Level level) {
        super(EntityType.PLAYER, level);
    }


    public int getMaxAirSupply() {
        if (RegistrySkills.ATHLETICS != null && RegistrySkills.ATHLETICS.get().isEnabled(this.this$class)) {
            return (int) (300.0D * RegistrySkills.ATHLETICS.get().getValue()[0]);
        }
        return 300;
    }

    @ModifyReturnValue(method = "getDestroySpeed", at = @At("RETURN"))
    private float justlevelingfork$modifyDestroySpeed(float original, BlockState state) {
        ItemStack mainHand = this.this$class.getMainHandItem();
        if (!(mainHand.getItem() instanceof PickaxeItem || mainHand.getItem() instanceof ShovelItem || mainHand.getItem() instanceof AxeItem)) {
            return original;
        }

        float modifier = original * (1.0F + (float) this.this$class.getAttributeValue(RegistryAttributes.BREAK_SPEED.get()));
        if (mainHand.getItem() instanceof PickaxeItem && state.is(RegistryTags.Blocks.OBSIDIAN)) {
            if (RegistrySkills.OBSIDIAN_SMASHER != null && RegistrySkills.OBSIDIAN_SMASHER.get().isEnabled(this.this$class)) {
                return (float) (original * RegistrySkills.OBSIDIAN_SMASHER.get().getValue()[0]) + modifier;
            }
            return original;
        }

        return original + modifier;
    }

    @Inject(method = "attack", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;resetAttackStrengthTicker()V"))
    private void justlevelingfork$captureCriticalAttack(Entity target, CallbackInfo ci) {
        boolean fullyCharged = this.this$class.getAttackStrengthScale(0.5F) > 0.9F;
        boolean vanillaCritical = fullyCharged
                && this.this$class.fallDistance > 0.0F
                && !this.this$class.onGround()
                && !this.this$class.onClimbable()
                && !this.this$class.isInWater()
                && !this.this$class.hasEffect(MobEffects.BLINDNESS)
                && !this.this$class.isPassenger()
                && target instanceof LivingEntity
                && !this.this$class.isSprinting();
        this.justlevelingfork$vanillaCriticalAttack = vanillaCritical;
        this.justlevelingfork$criticalAttack = vanillaCritical || isBerserkerCritical();
    }

    @WrapOperation(
            method = "attack",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;hurt(Lnet/minecraft/world/damagesource/DamageSource;F)Z", ordinal = 0)
    )
    private boolean justlevelingfork$modifyCriticalDamage(Entity target, DamageSource source, float amount, Operation<Boolean> original) {
        try {
            return original.call(target, source, modifyCriticalDamage(amount));
        } finally {
            this.justlevelingfork$criticalAttack = false;
            this.justlevelingfork$vanillaCriticalAttack = false;
        }
    }

    @Unique
    private boolean isBerserkerCritical() {
        return RegistrySkills.BERSERKER != null
                && RegistrySkills.BERSERKER.get().isEnabled(this.this$class)
                && this.this$class.getHealth() <= this.this$class.getMaxHealth() * (float) (RegistrySkills.BERSERKER.get().getValue()[0] / 100.0D);
    }

    @Unique
    private float modifyCriticalDamage(float amount) {
        if (!this.justlevelingfork$criticalAttack) {
            return amount;
        }

        float criticalMultiplier = 1.5F + (float) this.this$class.getAttributeValue(RegistryAttributes.CRITICAL_DAMAGE.get());
        float modified = this.justlevelingfork$vanillaCriticalAttack ? amount / 1.5F * criticalMultiplier : amount * criticalMultiplier;
        if (this.this$class instanceof ServerPlayer serverPlayer && RegistrySkills.CRITICAL_ROLL != null && RegistrySkills.CRITICAL_ROLL.get().isEnabled(serverPlayer)) {
            int dice = (int) Math.floor(Math.random() * 7.0D);
            if (dice == 1) {
                PlayerMessagesCP.send(serverPlayer, "overlay.skill.justlevelingfork.critical_roll_1", 0);
                modified /= 1.0F + 1.0F / (float) RegistrySkills.CRITICAL_ROLL.get().getValue()[1];
            } else if (dice == 6) {
                PlayerMessagesCP.send(serverPlayer, "overlay.skill.justlevelingfork.critical_roll_6", 0);
                modified *= (float) RegistrySkills.CRITICAL_ROLL.get().getValue()[0];
            }
        }

        return modified;
    }

    @ModifyReturnValue(method = "getDisplayName", at = @At("RETURN"))
    private Component justlevelingfork$addTitlePrefix(Component original) {
        if (!HandlerCommonConfig.HANDLER.instance().displayTitlesAsPrefix) {
            return original;
        }

        AptitudeCapability capability = AptitudeCapability.get(this.this$class);
        if (capability == null) {
            return original;
        }

        Title title = RegistryTitles.getTitle(capability.getPlayerTitle());
        Component titleComponent = title != null
                ? Component.translatable(title.getKey())
                : Component.translatable(RegistryTitles.TITLELESS.get().getKey());
        MutableComponent prefixedName = Component.literal("[")
                .append(titleComponent)
                .append(Component.literal("] "))
                .append(original.copy());
        return prefixedName;
    }

    @Override
    public AptitudeCapability justlevelingfork$getAptitudeCapability() {
        return this.justlevelingfork$aptitudeCapability;
    }

    @Inject(method = "addAdditionalSaveData", at = @At("TAIL"))
    private void justlevelingfork$saveAptitudeData(CompoundTag tag, CallbackInfo ci) {
        tag.put(JUST_LEVELING_APTITUDE_DATA, this.justlevelingfork$aptitudeCapability.serializeNBT());
    }

    @Inject(method = "readAdditionalSaveData", at = @At("TAIL"))
    private void justlevelingfork$readAptitudeData(CompoundTag tag, CallbackInfo ci) {
        if (tag.contains(JUST_LEVELING_APTITUDE_DATA, 10)) {
            this.justlevelingfork$aptitudeCapability.deserializeNBT(tag.getCompound(JUST_LEVELING_APTITUDE_DATA));
        }
    }
}


