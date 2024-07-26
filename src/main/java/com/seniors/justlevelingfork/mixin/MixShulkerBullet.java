package com.seniors.justlevelingfork.mixin;

import com.seniors.justlevelingfork.registry.RegistrySkills;
import com.google.common.base.MoreObjects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ShulkerBullet;
import net.minecraft.world.phys.EntityHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({ShulkerBullet.class})
public abstract class MixShulkerBullet {

    @Unique
    private final ShulkerBullet this$class = (ShulkerBullet) (Object) this;

    @Inject(method = {"onHitEntity"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/world/entity/projectile/Projectile;onHitEntity(Lnet/minecraft/world/phys/EntityHitResult;)V", shift = At.Shift.AFTER)}, cancellable = true)
    protected void onHitEntity(EntityHitResult hitResult, CallbackInfo info) {
        info.cancel();
        Entity entity = hitResult.getEntity();
        Entity entity1 = this.this$class.getOwner();
        LivingEntity livingentity = (entity1 instanceof LivingEntity) ? (LivingEntity) entity1 : null;
        boolean flag = entity.hurt(this.this$class.damageSources().mobProjectile(this.this$class, livingentity), 4.0F);
        if (flag) {
            assert livingentity != null;
            this.this$class.doEnchantDamageEffects(livingentity, entity);
            if (entity instanceof LivingEntity livingentity1) {
                if (livingentity1 instanceof Player player) {
                    if (!RegistrySkills.TURTLE_SHIELD.get().isEnabled(player)) {
                        player.addEffect(new MobEffectInstance(MobEffects.LEVITATION, 200), MoreObjects.firstNonNull(entity1, this.this$class));
                    }
                } else {
                    livingentity1.addEffect(new MobEffectInstance(MobEffects.LEVITATION, 200), MoreObjects.firstNonNull(entity1, this.this$class));
                }
            }

        }
    }
}


