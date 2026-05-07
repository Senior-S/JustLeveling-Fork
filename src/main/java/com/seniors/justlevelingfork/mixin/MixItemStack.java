package com.seniors.justlevelingfork.mixin;

import com.seniors.justlevelingfork.registry.RegistrySkills;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Consumer;

@Mixin(ItemEnchantments.class)
public abstract class MixItemStack {
    @Shadow
    @Final
    private boolean showInTooltip;

    @Inject(method = "addToTooltip", at = @At("HEAD"), cancellable = true)
    private void justlevelingfork$hideEnchantmentsWithoutScholar(Item.TooltipContext tooltipContext, Consumer<Component> tooltipConsumer, TooltipFlag tooltipFlag, CallbackInfo ci) {
        if (RegistrySkills.SCHOLAR == null || RegistrySkills.SCHOLAR.get().isEnabled()) {
            return;
        }

        ci.cancel();
        if (!this.showInTooltip) {
            return;
        }

        ItemEnchantments enchantments = (ItemEnchantments) (Object) this;
        for (Object2IntMap.Entry<Holder<Enchantment>> ignored : enchantments.entrySet()) {
            tooltipConsumer.accept(Component.translatable("tooltip.skill.scholar.lock_item").withStyle(ChatFormatting.GRAY));
        }
    }
}
