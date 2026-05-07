package com.seniors.justlevelingfork.mixin;

import com.seniors.justlevelingfork.common.capability.AptitudeCapability;
import net.minecraft.core.NonNullList;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractContainerMenu.class)
public abstract class MixAbstractContainerMenu {
    @Shadow
    @Final
    public NonNullList<Slot> slots;

    @Shadow
    public abstract ItemStack getCarried();

    @Inject(method = "clicked", at = @At("HEAD"), cancellable = true)
    private void justlevelingfork$blockLockedEquipmentMoves(int slotIndex, int button, ClickType clickType, Player player, CallbackInfo ci) {
        if (!(player instanceof ServerPlayer) || player.isCreative() || player.isSpectator()) {
            return;
        }

        if (clickType == ClickType.QUICK_CRAFT && shouldBlockLockedQuickCraft(player)) {
            ci.cancel();
            return;
        }

        if (slotIndex < 0 || slotIndex >= this.slots.size()) {
            return;
        }

        Slot slot = this.slots.get(slotIndex);
        ItemStack slotStack = slot.getItem();
        ItemStack carriedStack = this.getCarried();

        if (clickType == ClickType.PICKUP && isEquipmentInventorySlot(slot) && !carriedStack.isEmpty() && !canUseItem(player, carriedStack)) {
            ci.cancel();
            return;
        }

        if (clickType == ClickType.SWAP) {
            boolean swapsIntoEquipmentSlot = isEquipmentInventorySlot(slot) && !player.getInventory().getItem(button).isEmpty() && !canUseItem(player, player.getInventory().getItem(button));
            boolean swapsIntoOffhand = button == Inventory.SLOT_OFFHAND && !slotStack.isEmpty() && !canUseItem(player, slotStack);
            if (swapsIntoEquipmentSlot || swapsIntoOffhand) {
                ci.cancel();
                return;
            }
        }

        if (clickType == ClickType.QUICK_MOVE && (AbstractContainerMenu) (Object) this instanceof InventoryMenu && !slotStack.isEmpty() && !canUseItem(player, slotStack)) {
            ci.cancel();
        }
    }

    private boolean shouldBlockLockedQuickCraft(Player player) {
        ItemStack carriedStack = this.getCarried();
        return !carriedStack.isEmpty() && !canUseItem(player, carriedStack) && this.slots.stream().anyMatch(this::isEquipmentInventorySlot);
    }

    private boolean isEquipmentInventorySlot(Slot slot) {
        return slot.container instanceof Inventory && slot.getContainerSlot() >= Inventory.INVENTORY_SIZE;
    }

    private boolean canUseItem(Player player, ItemStack stack) {
        AptitudeCapability provider = AptitudeCapability.get(player);
        return provider == null || provider.canUseItem(player, stack);
    }
}
