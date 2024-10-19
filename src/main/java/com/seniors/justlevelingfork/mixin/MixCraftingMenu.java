package com.seniors.justlevelingfork.mixin;

import com.seniors.justlevelingfork.common.capability.AptitudeCapability;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.inventory.CraftingMenu;
import net.minecraft.world.inventory.ResultContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CraftingMenu.class)
public abstract class MixCraftingMenu {
    @Inject(at = @At("TAIL"), method = "slotChangedCraftingGrid")
    private static void slotChangedCraftingGrid(AbstractContainerMenu pMenu, Level level, Player player, CraftingContainer container, ResultContainer resultContainer, CallbackInfo ci){
        if (player instanceof ServerPlayer serverPlayer){
            ItemStack itemStack = resultContainer.getItem(0);

            if (!serverPlayer.isCreative()) {
                AptitudeCapability provider = AptitudeCapability.get(player);

                if (provider != null && !provider.canUseItem(serverPlayer, itemStack)){
                    resultContainer.setItem(0, ItemStack.EMPTY);
                }
            }
        }
    }
}
