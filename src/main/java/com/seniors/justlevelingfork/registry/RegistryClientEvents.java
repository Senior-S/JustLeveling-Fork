package com.seniors.justlevelingfork.registry;

import com.seniors.justlevelingfork.client.core.Aptitudes;
import com.seniors.justlevelingfork.common.capability.AptitudeCapability;
import com.seniors.justlevelingfork.handler.HandlerAptitude;
import com.seniors.justlevelingfork.registry.aptitude.Aptitude;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.util.List;
import java.util.Objects;


public class RegistryClientEvents {
    public static void load() {
        ItemTooltipCallback.EVENT.register((itemStack, tooltipContext, tooltipFlag, tooltips) -> appendAptitudeTooltip(itemStack, tooltips));
    }

    private static void appendAptitudeTooltip(ItemStack itemStack, List<Component> tooltips) {
        if (Minecraft.getInstance().player == null || !Minecraft.getInstance().player.isAlive() || itemStack.isEmpty()) {
            return;
        }

        ResourceLocation location = Objects.requireNonNull(BuiltInRegistries.ITEM.getKey(itemStack.getItem()));
        List<Aptitudes> list = HandlerAptitude.getValue(location.toString());
        AptitudeCapability capability = AptitudeCapability.get();
        if (list != null && capability != null) {
            tooltips.add(Component.empty());
            tooltips.add(Component.translatable("tooltip.aptitude.item_requirement").withStyle(ChatFormatting.DARK_PURPLE));
            for (Aptitudes aptitudes : list) {
                Aptitude aptitude = aptitudes.getAptitude();
                if (aptitude != null) {
                    ChatFormatting colour = capability.getAptitudeLevel(aptitude) >= aptitudes.getAptitudeLvl() ? ChatFormatting.GREEN : ChatFormatting.RED;
                    tooltips.add(Component.translatable("tooltip.aptitude.item_requirements", Component.translatable(aptitude.getKey()), Component.literal(String.valueOf(aptitudes.getAptitudeLvl())).withStyle(colour)));
                }
            }
        }
    }
}


