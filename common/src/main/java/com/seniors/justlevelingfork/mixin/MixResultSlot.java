package com.seniors.justlevelingfork.mixin;

import com.seniors.justlevelingfork.registry.RegistrySkills;
import com.seniors.justlevelingfork.registry.skills.ConvergenceSkill;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ResultSlot;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ResultSlot.class)
public abstract class MixResultSlot {
    @Inject(method = "onTake", at = @At("TAIL"))
    private void justlevelingfork$dropConvergenceItem(Player player, ItemStack craftedStack, CallbackInfo ci) {
        if (!(player instanceof ServerPlayer) || RegistrySkills.CONVERGENCE == null || !RegistrySkills.CONVERGENCE.get().isEnabled(player)) {
            return;
        }

        int randomizer = (int) Math.floor(Math.random() * RegistrySkills.CONVERGENCE.get().getValue()[0]);
        if (RegistrySkills.CONVERGENCE.get().getValue()[0] >= 100 || randomizer == 1) {
            ItemStack convergenceItem = ConvergenceSkill.drop(craftedStack);
            if (convergenceItem != null) {
                player.drop(convergenceItem, false);
            }
        }
    }
}
