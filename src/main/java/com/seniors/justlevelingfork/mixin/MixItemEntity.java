package com.seniors.justlevelingfork.mixin;

import com.seniors.justlevelingfork.common.capability.AptitudeCapability;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemEntity.class)
public abstract class MixItemEntity {
    @Unique
    private final ItemEntity this$class = (ItemEntity) (Object) this;

    @Inject(method = "playerTouch", at = @At("HEAD"), cancellable = true)
    private void justlevelingfork$preventLockedPickup(Player player, CallbackInfo ci) {
        if (player.isCreative() || player.isSpectator()) {
            return;
        }

        AptitudeCapability provider = AptitudeCapability.get(player);
        if (provider != null && !provider.canUseItem(player, this.this$class.getItem())) {
            ci.cancel();
        }
    }
}
