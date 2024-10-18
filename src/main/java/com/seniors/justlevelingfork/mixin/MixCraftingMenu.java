package com.seniors.justlevelingfork.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.seniors.justlevelingfork.common.capability.AptitudeCapability;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.inventory.CraftingMenu;
import net.minecraft.world.inventory.ResultContainer;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(CraftingMenu.class)
public abstract class MixCraftingMenu {
    @WrapOperation(method = "slotChangedCraftingGrid", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/inventory/ResultContainer;setItem(ILnet/minecraft/world/item/ItemStack;)V"))
    private static void slotChangedCraftingGrid(ResultContainer container, int slotIndex, ItemStack stack, Operation<Void> original, @Local(argsOnly = true, index = 2) Player player, @Local(argsOnly = true, index = 3) CraftingContainer craftingInventory) {
        if (player instanceof ServerPlayer serverPlayer){
            if (!serverPlayer.isCreative()) {
                AptitudeCapability provider = AptitudeCapability.get(player);

                if (provider != null && !provider.canUseItem(serverPlayer, stack)){
                    stack = ItemStack.EMPTY;

                    original.call(container, slotIndex, stack);
                }
            }
        }
    }
}
