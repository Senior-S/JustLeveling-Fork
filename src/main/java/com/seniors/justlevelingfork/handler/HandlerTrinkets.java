package com.seniors.justlevelingfork.handler;

import com.seniors.justlevelingfork.common.capability.AptitudeCapability;
import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.TrinketComponent;
import dev.emi.trinkets.api.TrinketsApi;
import dev.emi.trinkets.api.event.TrinketEquipCallback;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class HandlerTrinkets {
    public static boolean isModLoaded() {
        return FabricLoader.getInstance().isModLoaded("trinkets");
    }

    public static void load() {
        if (!isModLoaded()) {
            return;
        }

        TrinketEquipCallback.EVENT.register((stack, slot, entity) -> {
            if (entity instanceof ServerPlayer player && shouldDropLockedTrinket(player, stack)) {
                dropFromSlot(player, slot, stack);
            }
        });
    }

    public static void dropLockedTrinkets(ServerPlayer player) {
        if (!isModLoaded() || shouldSkipLockCheck(player)) {
            return;
        }

        TrinketsApi.getTrinketComponent(player).ifPresent(component -> dropLockedTrinkets(player, component));
    }

    private static void dropLockedTrinkets(ServerPlayer player, TrinketComponent component) {
        component.forEach((slot, stack) -> {
            if (shouldDropLockedTrinket(player, stack)) {
                dropFromSlot(player, slot, stack);
            }
        });
    }

    private static boolean shouldDropLockedTrinket(ServerPlayer player, ItemStack stack) {
        if (shouldSkipLockCheck(player) || stack.isEmpty()) {
            return false;
        }

        AptitudeCapability aptitudeCapability = AptitudeCapability.get(player);
        return aptitudeCapability != null && !aptitudeCapability.canUseItem(player, stack);
    }

    private static void dropFromSlot(ServerPlayer player, SlotReference slot, ItemStack stack) {
        if (stack.isEmpty()) {
            return;
        }

        player.drop(stack.copy(), false);
        slot.inventory().setItem(slot.index(), ItemStack.EMPTY);
        slot.inventory().clearModifiers();
        slot.inventory().markUpdate();
    }

    private static boolean shouldSkipLockCheck(Player player) {
        return player == null || player.isCreative() || player.isSpectator();
    }
}
