package com.seniors.justlevelingfork.mixin;

import com.seniors.justlevelingfork.JustLevelingFork;
import dev.architectury.platform.Platform;
import net.bettercombat.api.WeaponAttributes;
import net.bettercombat.api.client.AttackRangeExtensions;
import net.bettercombat.client.collision.OrientedBoundingBox;
import net.bettercombat.client.collision.TargetFinder;
import net.bettercombat.client.collision.WeaponHitBoxes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Comparator;
import java.util.List;

@Mixin(TargetFinder.class)
public abstract class MixTargetFinder {
    @Inject(method = {"findAttackTargetResult"}, at = {@At("HEAD")}, cancellable = true, remap = false)
    private static void findAttackTargetResult(Player player, Entity cursorTarget, WeaponAttributes.Attack attack, double attackRange, CallbackInfoReturnable<TargetFinder.TargetResult> info) {
        if(player == null || cursorTarget == null){
            return;
        }

        Vec3 origin = TargetFinder.getInitialTracingPoint(player);
        List<Entity> entities = TargetFinder.getInitialTargets(player, cursorTarget, attackRange);
        if (!AttackRangeExtensions.sources().isEmpty()) {
            attackRange = apply$AttackRangeModifiers(player, attackRange);
        }
        AttributeInstance playerReach = player.getAttribute(Attributes.ENTITY_INTERACTION_RANGE);
        if (playerReach == null) {
            return;
        }
        AttributeModifier modifier = playerReach.getModifier(ResourceLocation.fromNamespaceAndPath(JustLevelingFork.MOD_ID, "96a891fe-5919-418d-8205-f50464391509"));
        if (modifier == null){
            return;
        }
        info.cancel();

        attackRange += modifier.amount();

        // Quality equipment directly changes the register function through reflection.
        // So I need to "replicate" what the reflection does here.
        if (Platform.isModLoaded("quality_equipment")) {
            attackRange += 3.0D;
        }

        boolean isSpinAttack = (attack.angle() > 180.0D);
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

    @Unique
    private static double apply$AttackRangeModifiers(Player player, double attackRange) {
        AttackRangeExtensions.Context context = new AttackRangeExtensions.Context(player, attackRange);
        List<AttackRangeExtensions.Modifier> modifiers = AttackRangeExtensions.sources().stream().map(function -> function.apply(context)).sorted(Comparator.comparingInt(AttackRangeExtensions.Modifier::operationOrder)).toList();
        double result = attackRange;

        for (AttackRangeExtensions.Modifier modifier : modifiers) {
            switch (modifier.operation()) {
                case ADD:
                    result += modifier.value();
                    break;
                case MULTIPLY:
                    result *= modifier.value();
                    break;
            }
        }
        return result;
    }
}


