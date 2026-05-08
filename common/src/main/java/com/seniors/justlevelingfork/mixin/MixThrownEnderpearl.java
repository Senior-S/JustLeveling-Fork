package com.seniors.justlevelingfork.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.seniors.justlevelingfork.registry.RegistrySkills;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.ThrownEnderpearl;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ThrownEnderpearl.class)
public abstract class MixThrownEnderpearl {
    @ModifyExpressionValue(
            method = "onHit",
            at = @At(value = "CONSTANT", args = "floatValue=5.0F")
    )
    private float justlevelingfork$removeSafePortDamage(float original) {
        Entity owner = ((ThrownEnderpearl) (Object) this).getOwner();
        if (owner instanceof ServerPlayer player && RegistrySkills.SAFE_PORT != null && RegistrySkills.SAFE_PORT.get().isEnabled(player)) {
            return 0.0F;
        }

        return original;
    }
}
