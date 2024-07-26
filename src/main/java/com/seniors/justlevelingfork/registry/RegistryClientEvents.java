package com.seniors.justlevelingfork.registry;

import com.seniors.justlevelingfork.client.core.Aptitudes;
import com.seniors.justlevelingfork.common.capability.AptitudeCapability;
import com.seniors.justlevelingfork.handler.HandlerAptitude;
import com.seniors.justlevelingfork.registry.aptitude.Aptitude;

import java.util.List;
import java.util.Objects;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.ForgeRegistries;

public class RegistryClientEvents {
    @SubscribeEvent
    public void onTooltipDisplay(ItemTooltipEvent event) {
        if ((Minecraft.getInstance()).player != null) {
            List<Component> tooltips = event.getToolTip();

            List<Aptitudes> list = HandlerAptitude.getValue(Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(event.getItemStack().getItem())).toString());
            if (list != null) {
                tooltips.add(Component.empty());
                tooltips.add(Component.translatable("tooltip.aptitude.item_requirement").withStyle(ChatFormatting.DARK_PURPLE));
                for (Aptitudes aptitudes : list) {
                    Aptitude aptitude = aptitudes.getAptitude();
                    if (aptitude != null) {
                        ChatFormatting colour = (AptitudeCapability.get().getAptitudeLevel(aptitude) >= aptitudes.getAptitudeLvl()) ? ChatFormatting.GREEN : ChatFormatting.RED;
                        tooltips.add(Component.translatable("tooltip.aptitude.item_requirements", Component.translatable(aptitude.getKey()).append(":").withStyle(ChatFormatting.DARK_AQUA), Component.literal(String.valueOf(aptitudes.getAptitudeLvl())).withStyle(colour)).withStyle(ChatFormatting.GRAY));
                    }
                }
            }
        }
    }
}


