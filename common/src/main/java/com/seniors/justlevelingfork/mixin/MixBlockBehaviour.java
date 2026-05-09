package com.seniors.justlevelingfork.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.seniors.justlevelingfork.registry.RegistrySkills;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(BlockBehaviour.class)
public abstract class MixBlockBehaviour {
    @ModifyReturnValue(method = "getDestroyProgress", at = @At("RETURN"))
    private float justlevelingfork$modifyObsidianDestroyProgress(float original, BlockState state, Player player, BlockGetter level, BlockPos pos) {
        if (!state.is(Blocks.OBSIDIAN) && !state.is(Blocks.CRYING_OBSIDIAN)) {
            return original;
        }

        if (player.getMainHandItem().getDestroySpeed(state) <= 1.0F) {
            return original;
        }

        if (RegistrySkills.OBSIDIAN_SMASHER == null || !RegistrySkills.OBSIDIAN_SMASHER.get().isEnabled(player)) {
            return original;
        }

        return (float) (original * RegistrySkills.OBSIDIAN_SMASHER.get().getValue()[0]);
    }
}
