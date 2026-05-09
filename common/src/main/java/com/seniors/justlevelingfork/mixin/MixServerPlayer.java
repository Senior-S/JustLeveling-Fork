package com.seniors.justlevelingfork.mixin;

import com.seniors.justlevelingfork.common.capability.AptitudeCapability;
import com.seniors.justlevelingfork.network.packet.client.SyncAptitudeCapabilityCP;
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

    @Unique
    private int justlevelingfork$respawnSyncTicks;

    @Inject(method = "restoreFrom", at = @At("TAIL"))
    private void justlevelingfork$copyAptitudeData(ServerPlayer oldPlayer, boolean alive, CallbackInfo ci) {
        AptitudeCapability capability = AptitudeCapability.get(this.this$class);
        AptitudeCapability oldCapability = AptitudeCapability.get(oldPlayer);
        if (capability != null && oldCapability != null) {
            capability.copyFrom(oldCapability);
            this.justlevelingfork$respawnSyncTicks = 100;
        }
    }

    @Inject(method = "tick", at = @At("TAIL"))
    private void justlevelingfork$syncAptitudeDataAfterRespawn(CallbackInfo ci) {
        if (this.justlevelingfork$respawnSyncTicks <= 0) {
            return;
        }

        if (this.justlevelingfork$respawnSyncTicks == 100
                || this.justlevelingfork$respawnSyncTicks == 1
                || this.justlevelingfork$respawnSyncTicks % 10 == 0) {
            SyncAptitudeCapabilityCP.send(this.this$class);
        }
        this.justlevelingfork$respawnSyncTicks--;
    }
}
