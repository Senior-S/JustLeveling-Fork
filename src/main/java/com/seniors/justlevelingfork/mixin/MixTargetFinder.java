package com.seniors.justlevelingfork.mixin;

import net.bettercombat.api.WeaponAttributes;
import net.bettercombat.client.collision.OrientedBoundingBox;
import net.bettercombat.client.collision.TargetFinder;
import net.bettercombat.client.collision.WeaponHitBoxes;
import net.bettercombat.compatibility.CompatibilityFlags;
import net.bettercombat.compatibility.PehkuiHelper;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeMod;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Mixin({TargetFinder.class})
public abstract class MixTargetFinder {

    @Inject(method = {"findAttackTargetResult"}, at = {@At("HEAD")}, cancellable = true, remap = false)
    private static void findAttackTargetResult(Player player, Entity cursorTarget, WeaponAttributes.Attack attack, double attackRange, CallbackInfoReturnable<TargetFinder.TargetResult> info) {
        info.cancel();
        Vec3 origin = TargetFinder.getInitialTracingPoint(player);
        List<Entity> entities = TargetFinder.getInitialTargets(player, cursorTarget, attackRange);
        if (CompatibilityFlags.usePehkui) {
            attackRange *= (double) PehkuiHelper.getScale(player);
        }

        attackRange += ((AttributeModifier) Objects.requireNonNull(((AttributeInstance)Objects.requireNonNull(player.getAttribute( ForgeMod.ATTACK_RANGE.get()))).getModifier(UUID.fromString("96a891fe-5919-418d-8205-f50464391509")))).getAmount();
        boolean isSpinAttack = attack.angle() > 180.0D;
        Vec3 size = WeaponHitBoxes.createHitbox(attack.hitbox(), attackRange, isSpinAttack);
        OrientedBoundingBox obb = new OrientedBoundingBox(origin, size, player.getXRot(), player.getYRot());
        if (!isSpinAttack) {
            obb = obb.offsetAlongAxisZ(size.z / 2.0D);
        }

        obb.updateVertex();
        TargetFinder.CollisionFilter collisionFilter = new TargetFinder.CollisionFilter(obb);
        entities = collisionFilter.filter(entities);
        TargetFinder.RadialFilter radialFilter = new TargetFinder.RadialFilter(origin, obb.axisZ, attackRange, attack.angle());
        entities = radialFilter.filter(entities);
        info.setReturnValue(new TargetFinder.TargetResult(entities, obb));
    }
}


