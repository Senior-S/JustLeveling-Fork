package com.seniors.justlevelingfork.common;

import com.seniors.justlevelingfork.common.capability.AptitudeCapability;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public final class HorseEquipmentRestrictions {
    private HorseEquipmentRestrictions() {
    }

    public static boolean canEquip(Player player, AbstractHorse horse, ItemStack stack) {
        if (player == null || player.isCreative() || stack.isEmpty()) {
            return true;
        }
        if (!stack.is(Items.SADDLE) && !horse.isArmor(stack)) {
            return true;
        }

        AptitudeCapability provider = AptitudeCapability.get(player);
        return provider == null || provider.canUseItem(player, stack);
    }
}
