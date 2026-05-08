package com.seniors.justlevelingfork.mixin;

import com.seniors.justlevelingfork.common.capability.AptitudeCapability;
import net.minecraft.server.level.ServerPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayer.class)
public abstract class MixServerPlayer {
    @Unique
    private final ServerPlayer this$class = (ServerPlayer) (Object) this;

    @Inject(method = "restoreFrom", at = @At("TAIL"))
    private void justlevelingfork$copyAptitudeData(ServerPlayer oldPlayer, boolean alive, CallbackInfo ci) {
        AptitudeCapability.get(this.this$class).copyFrom(AptitudeCapability.get(oldPlayer));
    }
}
