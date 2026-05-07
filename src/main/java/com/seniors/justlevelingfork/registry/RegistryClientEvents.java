package com.seniors.justlevelingfork.registry;

import com.seniors.justlevelingfork.client.core.Aptitudes;
import com.seniors.justlevelingfork.common.capability.AptitudeCapability;
import com.seniors.justlevelingfork.handler.HandlerAptitude;
import com.seniors.justlevelingfork.handler.HandlerCommonConfig;
import com.seniors.justlevelingfork.integration.MiapiIntegration;
import com.seniors.justlevelingfork.integration.TetraIntegration;
import com.seniors.justlevelingfork.registry.aptitude.Aptitude;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
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
 * registry CLIENT events into the forge mod bus.
 */
public class RegistryClientEvents {
    @SubscribeEvent
    public void onTooltipDisplay(ItemTooltipEvent event) {
        if ((Minecraft.getInstance()).player != null && (Minecraft.getInstance()).player.isAlive()) {
            List<Component> tooltips = event.getToolTip();
            ItemStack itemStack = event.getItemStack();
            AptitudeCapability capability = AptitudeCapability.get();
            if (capability == null) {
                return;
            }

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
                        tooltips.add(Component.empty());
                        tooltips.add(Component.translatable("tooltip.aptitude.item_requirement").withStyle(ChatFormatting.DARK_PURPLE));
                        for (Aptitudes aptitudes : aptitudesList) {
                            Aptitude aptitude = aptitudes.getAptitude();
                            if (aptitude != null) {
                                ChatFormatting colour = (AptitudeCapability.get().getAptitudeLevel(aptitude) >= aptitudes.getAptitudeLvl()) ? ChatFormatting.GREEN : ChatFormatting.RED;
                                tooltips.add(Component.translatable("tooltip.aptitude.item_requirements", Component.translatable(aptitude.getKey()).withStyle(ChatFormatting.DARK_AQUA), Component.literal(String.valueOf(aptitudes.getAptitudeLvl())).withStyle(colour)).withStyle(ChatFormatting.GRAY));
                            }
                        }
                    }
                    return;
                }
            }
            List<Aptitudes> list = getTooltipRequirements(location.toString(), itemStack);
            if (list != null) {
                List<Component> requirementLines = new ArrayList<>();
                for (Aptitudes aptitudes : list) {
                    Aptitude aptitude = aptitudes.getAptitude();
                    if (aptitude != null) {
                        boolean met = capability.getAptitudeLevel(aptitude) >= aptitudes.getAptitudeLvl();
                        if (HandlerCommonConfig.HANDLER.instance().hideMetUsageRequirements && met) {
                            continue;
                        }
                        ChatFormatting colour = met ? ChatFormatting.GREEN : ChatFormatting.RED;
                        requirementLines.add(Component.translatable("tooltip.aptitude.item_requirements", Component.translatable(aptitude.getKey()), Component.literal(String.valueOf(aptitudes.getAptitudeLvl())).withStyle(colour)));
                    }
                }
                if (!requirementLines.isEmpty()) {
                    tooltips.add(Component.empty());
                    tooltips.add(Component.translatable("tooltip.aptitude.item_requirement").withStyle(ChatFormatting.DARK_PURPLE));
                    tooltips.addAll(requirementLines);
                }
            }
        }
    }

    private List<Aptitudes> getTooltipRequirements(String itemId, ItemStack stack) {
        List<Aptitudes> requirements = new ArrayList<>();
        addRequirements(requirements, HandlerAptitude.getValue(itemId));
        if (MiapiIntegration.isModularItem(stack)) {
            for (ResourceLocation moduleId : MiapiIntegration.getModuleIds(stack)) {
                addRequirements(requirements, HandlerAptitude.getValue(moduleId.toString()));
            }
        }
        return requirements.isEmpty() ? null : requirements;
    }

    private void addRequirements(List<Aptitudes> requirements, List<Aptitudes> add) {
        if (add == null) {
            return;
        }
        for (Aptitudes aptitudes : add) {
            if (!requirements.contains(aptitudes)) {
                requirements.add(aptitudes);
            }
        }
    }
}


