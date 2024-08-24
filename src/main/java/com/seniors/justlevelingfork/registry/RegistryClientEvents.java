package com.seniors.justlevelingfork.registry;

import com.seniors.justlevelingfork.client.core.Aptitudes;
import com.seniors.justlevelingfork.common.capability.AptitudeCapability;
import com.seniors.justlevelingfork.handler.HandlerAptitude;
import com.seniors.justlevelingfork.integration.TetraIntegration;
import com.seniors.justlevelingfork.registry.aptitude.Aptitude;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * Registry CLIENT events into the forge mod bus.
 */
public class RegistryClientEvents {
    @SubscribeEvent
    public void onTooltipDisplay(ItemTooltipEvent event) {
        if ((Minecraft.getInstance()).player != null) {
            List<Component> tooltips = event.getToolTip();
            ItemStack itemStack = event.getItemStack();

            ResourceLocation location = Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(itemStack.getItem()));
            if (ModList.get().isLoaded("tetra") && TetraIntegration.TetraItems.contains(location.toString())) {
                List<String> extractedTypes = TetraIntegration.GetItemTypes(itemStack);
                if (!extractedTypes.isEmpty()) {
                    List<Aptitudes> aptitudesList = new ArrayList<>();
                    for (String tetraItem : extractedTypes) {
                        List<Aptitudes> list = HandlerAptitude.getValue(tetraItem);
                        if (list != null) {
                            list.forEach(c -> {
                                if (!aptitudesList.contains(c)) aptitudesList.add(c);
                            });
                        }
                    }
                    if (!aptitudesList.isEmpty()) {
                        tooltips.add(TextComponent.EMPTY);
                        tooltips.add(new TranslatableComponent("tooltip.aptitude.item_requirement").withStyle(ChatFormatting.DARK_PURPLE));
                        for (Aptitudes aptitudes : aptitudesList) {
                            Aptitude aptitude = aptitudes.getAptitude();
                            if (aptitude != null) {
                                ChatFormatting colour = AptitudeCapability.get().getAptitudeLevel(aptitude) >= aptitudes.getAptitudeLvl() ? ChatFormatting.GREEN : ChatFormatting.RED;
                                tooltips.add(new TranslatableComponent("tooltip.aptitude.item_requirements", new TranslatableComponent(aptitude.getKey()).withStyle(ChatFormatting.DARK_AQUA), new TextComponent(String.valueOf(aptitudes.getAptitudeLvl())).withStyle(colour)).withStyle(ChatFormatting.GRAY));
                            }
                        }
                    }
                    return;
                }
            }
            List<Aptitudes> list = HandlerAptitude.getValue(location.toString());
            if (list != null) {
                tooltips.add(TextComponent.EMPTY);
                tooltips.add(new TranslatableComponent("tooltip.aptitude.item_requirement").withStyle(ChatFormatting.DARK_PURPLE));
                for (Aptitudes aptitudes : list) {
                    Aptitude aptitude = aptitudes.getAptitude();
                    if (aptitude != null) {
                        ChatFormatting colour = (AptitudeCapability.get().getAptitudeLevel(aptitude) >= aptitudes.getAptitudeLvl()) ? ChatFormatting.GREEN : ChatFormatting.RED;
                        tooltips.add(new TranslatableComponent("tooltip.aptitude.item_requirements", new TranslatableComponent(aptitude.getKey()),  new TextComponent(String.valueOf(aptitudes.getAptitudeLvl())).withStyle(colour)));
                    }
                }
            }
        }
    }
}


