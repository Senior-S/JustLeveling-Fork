package com.seniors.justlevelingfork.mixin;

import com.seniors.justlevelingfork.registry.RegistrySkills;

import java.util.List;

import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({ItemStack.class})
public abstract class MixItemStack {
    @Inject(method = {"appendEnchantmentNames"}, at = {@At("HEAD")}, cancellable = true)
    private static void appendEnchantmentNames(List<Component> list, ListTag tags, CallbackInfo info) {
        info.cancel();

        for (int i = 0; i < tags.size(); i++) {
            CompoundTag nbt = tags.getCompound(i);

            if (RegistrySkills.SCHOLAR.get().isEnabled()) {
                BuiltInRegistries.ENCHANTMENT.getOptional(EnchantmentHelper.getEnchantmentId(nbt)).ifPresent(p_41708_ -> list.add(p_41708_.getFullname(EnchantmentHelper.getEnchantmentLevel(nbt))));
            } else {
                BuiltInRegistries.ENCHANTMENT.getOptional(EnchantmentHelper.getEnchantmentId(nbt)).ifPresent(p_41708_ -> list.add(Component.translatable("tooltip.skill.scholar.lock_item").withStyle(ChatFormatting.GRAY)));
            }
        }
    }
}


