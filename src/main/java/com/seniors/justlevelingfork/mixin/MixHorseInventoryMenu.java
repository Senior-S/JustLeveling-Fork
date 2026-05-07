package com.seniors.justlevelingfork.mixin;

import com.seniors.justlevelingfork.common.HorseEquipmentRestrictions;
import net.minecraft.world.Container;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.HorseInventoryMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HorseInventoryMenu.class)
public abstract class MixHorseInventoryMenu {
    @Shadow
    @Final
    private Container horseContainer;

    @Shadow
    @Final
    private AbstractHorse horse;

    @Unique
    private Player justlevelingfork$player;

    @Inject(method = "<init>", at = @At("RETURN"))
    private void justlevelingfork$replaceEquipmentSlots(int containerId, Inventory inventory, Container container, AbstractHorse horse, CallbackInfo ci) {
        this.justlevelingfork$player = inventory.player;
        this.justlevelingfork$replaceSlot(0, new Slot(this.horseContainer, 0, 8, 18) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return stack.is(Items.SADDLE)
                        && !this.hasItem()
                        && MixHorseInventoryMenu.this.horse.isSaddleable()
                        && HorseEquipmentRestrictions.canEquip(MixHorseInventoryMenu.this.justlevelingfork$player, MixHorseInventoryMenu.this.horse, stack);
            }

            @Override
            public boolean isActive() {
                return MixHorseInventoryMenu.this.horse.isSaddleable();
            }
        });
        this.justlevelingfork$replaceSlot(1, new Slot(this.horseContainer, 1, 8, 36) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return MixHorseInventoryMenu.this.horse.isArmor(stack)
                        && HorseEquipmentRestrictions.canEquip(MixHorseInventoryMenu.this.justlevelingfork$player, MixHorseInventoryMenu.this.horse, stack);
            }

            @Override
            public boolean isActive() {
                return MixHorseInventoryMenu.this.horse.canWearArmor();
            }

            @Override
            public int getMaxStackSize() {
                return 1;
            }
        });
    }

    @Unique
    private void justlevelingfork$replaceSlot(int index, Slot slot) {
        slot.index = index;
        ((AbstractContainerMenu) (Object) this).slots.set(index, slot);
    }
}
