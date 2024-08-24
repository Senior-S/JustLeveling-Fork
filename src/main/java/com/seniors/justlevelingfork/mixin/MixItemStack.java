package com.seniors.justlevelingfork.mixin;

import com.seniors.justlevelingfork.registry.RegistrySkills;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin({ItemStack.class})
public abstract class MixItemStack {

    @Inject(method = {"appendEnchantmentNames"}, at = {@At("HEAD")}, cancellable = true)
    private static void appendEnchantmentNames(List<Component> list, ListTag tags, CallbackInfo info) {
        info.cancel();

        for (int i = 0; i < tags.size(); i++) {
            CompoundTag nbt = tags.getCompound(i);

            if (RegistrySkills.SCHOLAR.get().isEnabled()) {
                Registry.ENCHANTMENT.getOptional(EnchantmentHelper.getEnchantmentId(nbt)).ifPresent((enchantment) -> {
                    list.add(enchantment.getFullname(EnchantmentHelper.getEnchantmentLevel(nbt)));
                });
            } else {
                Registry.ENCHANTMENT.getOptional(EnchantmentHelper.getEnchantmentId(nbt)).ifPresent((enchantment) -> {
                    list.add((new TranslatableComponent("tooltip.skill.scholar.lock_item")).withStyle(ChatFormatting.GRAY));
                });
            }
        }
    }
}


