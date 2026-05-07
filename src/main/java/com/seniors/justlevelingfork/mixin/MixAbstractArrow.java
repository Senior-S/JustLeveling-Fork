package com.seniors.justlevelingfork.mixin;

import com.seniors.justlevelingfork.registry.RegistryAttributes;
import com.seniors.justlevelingfork.registry.RegistryEffects;
import com.seniors.justlevelingfork.registry.RegistrySkills;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.phys.EntityHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractArrow.class)
public abstract class MixAbstractArrow {
    @Shadow
    private double baseDamage;

    @Unique
    private double justlevelingfork$baseDamageBeforeSkill;

    @Inject(method = "onHitEntity", at = @At("HEAD"))
    private void justlevelingfork$applyProjectileSkillDamage(EntityHitResult hitResult, CallbackInfo ci) {
        this.justlevelingfork$baseDamageBeforeSkill = this.baseDamage;
        AbstractArrow arrow = (AbstractArrow) (Object) this;
        Entity owner = arrow.getOwner();
        if (owner instanceof Player player) {
            double arrowDamage = this.baseDamage + player.getAttributeValue(RegistryAttributes.PROJECTILE_DAMAGE.get()) / 5.0D;
            if (RegistrySkills.STEALTH_MASTERY != null && RegistrySkills.STEALTH_MASTERY.get().isEnabled(player) && player.isShiftKeyDown()) {
                arrowDamage += this.baseDamage * (RegistrySkills.STEALTH_MASTERY.get().getValue()[2] - 1.0D);
            }

            this.baseDamage = arrowDamage;
        }
    }

    @Inject(method = "onHitEntity", at = @At("TAIL"))
    private void justlevelingfork$restoreProjectileDamageAndApplyHitEffects(EntityHitResult hitResult, CallbackInfo ci) {
        this.baseDamage = this.justlevelingfork$baseDamageBeforeSkill;
        AbstractArrow arrow = (AbstractArrow) (Object) this;
        if (hitResult.getType() == net.minecraft.world.phys.HitResult.Type.ENTITY && arrow.getOwner() instanceof ServerPlayer serverPlayer) {
            new RegistryEffects.addEffect(serverPlayer, RegistrySkills.QUICK_REPOSITION != null && RegistrySkills.QUICK_REPOSITION.get().isEnabled(serverPlayer), MobEffects.MOVEMENT_SPEED).add((int) (10.0D + 20.0D * RegistrySkills.QUICK_REPOSITION.get().getValue()[1]), (int) (RegistrySkills.QUICK_REPOSITION.get().getValue()[0] - 1.0D));
        }
    }
}
