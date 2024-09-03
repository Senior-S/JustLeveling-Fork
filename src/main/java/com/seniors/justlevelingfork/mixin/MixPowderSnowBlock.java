package com.seniors.justlevelingfork.mixin;

import com.seniors.justlevelingfork.registry.RegistrySkills;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.PowderSnowBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({PowderSnowBlock.class})
public abstract class MixPowderSnowBlock {
    @Inject(method = {"canEntityWalkOnPowderSnow"}, at = {@At("HEAD")}, cancellable = true)
    private static void canEntityWalkOnPowderSnow(Entity entity, CallbackInfoReturnable<Boolean> info) {
        if (entity instanceof Player player) {
            if (RegistrySkills.SNOW_WALKER != null && RegistrySkills.SNOW_WALKER.get().isEnabled(player))
                info.setReturnValue(Boolean.TRUE);
        }

    }
}


