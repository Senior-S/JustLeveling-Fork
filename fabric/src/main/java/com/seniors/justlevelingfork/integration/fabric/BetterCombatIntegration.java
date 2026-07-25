package com.seniors.justlevelingfork.integration.fabric;

import com.seniors.justlevelingfork.JustLevelingFork;
import net.bettercombat.api.client.AttackRangeExtensions;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public final class BetterCombatIntegration {
    private static final ResourceLocation ENTITY_REACH_MODIFIER_ID = ResourceLocation.fromNamespaceAndPath(
            JustLevelingFork.MOD_ID,
            "96a891fe-5919-418d-8205-f50464391509"
    );
    private static boolean registered;

    private BetterCombatIntegration() {
    }

    public static void register() {
        if (registered) {
            return;
        }

        AttackRangeExtensions.register(context -> new AttackRangeExtensions.Modifier(
                getJustLevelingReachBonus(context.player()),
                AttackRangeExtensions.Operation.ADD
        ));
        registered = true;
    }

    private static double getJustLevelingReachBonus(net.minecraft.world.entity.player.Player player) {
        AttributeInstance reach = player.getAttribute(Attributes.ENTITY_INTERACTION_RANGE);
        if (reach == null) {
            return 0.0D;
        }

        AttributeModifier modifier = reach.getModifier(ENTITY_REACH_MODIFIER_ID);
        return modifier == null ? 0.0D : modifier.amount();
    }
}
