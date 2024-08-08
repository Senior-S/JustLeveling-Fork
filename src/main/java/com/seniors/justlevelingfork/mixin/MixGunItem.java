package com.seniors.justlevelingfork.mixin;

import com.seniors.justlevelingfork.common.capability.AptitudeCapability;
import com.vicmatskiv.pointblank.item.GunItem;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(GunItem.class)
public class MixGunItem {
    @Inject(method = "tryFire", at = @At("HEAD"), cancellable = true, remap = false)
    private void tryFire(LocalPlayer player, ItemStack itemStack, Entity targetEntity, CallbackInfoReturnable<Boolean> ci){
        if (!player.isCreative()) {
            AptitudeCapability provider = AptitudeCapability.get(player);
            if (!provider.canUseItemClient(itemStack)) {
                ci.cancel();
            }
        }
    }

}
