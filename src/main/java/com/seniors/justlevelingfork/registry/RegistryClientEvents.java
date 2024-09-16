package com.seniors.justlevelingfork.registry;

import com.seniors.justlevelingfork.JustLevelingFork;
import com.seniors.justlevelingfork.client.core.Aptitudes;
import com.seniors.justlevelingfork.common.capability.AptitudeCapability;
import com.seniors.justlevelingfork.handler.HandlerAptitude;
import com.seniors.justlevelingfork.handler.HandlerCommonConfig;
import com.seniors.justlevelingfork.integration.TetraIntegration;
import com.seniors.justlevelingfork.registry.aptitude.Aptitude;
import com.seniors.justlevelingfork.registry.title.Title;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
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
    public void onClientChatReceivedEvent(ClientChatReceivedEvent event){
        if (HandlerCommonConfig.HANDLER.instance().displayTitlesAsPrefix) {
            ServerPlayer serverPlayer = JustLevelingFork.server.getPlayerList().getPlayer(event.getSender());
            if(serverPlayer == null) return;
            AptitudeCapability capability = AptitudeCapability.get(serverPlayer);
            if(capability == null) return;
            Title titleKey = RegistryTitles.getTitle(capability.getPlayerTitle());
            String title = (titleKey != null) ? Component.translatable(RegistryTitles.getTitle(AptitudeCapability.get().getPlayerTitle()).getKey()).getString() : "";

            if(!title.isEmpty()){
                String originalMessage = event.getMessage().getString();
                event.setMessage(Component.literal(String.format("[%s] %s", title, originalMessage)));
            }
        }
    }

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
            List<Aptitudes> list = HandlerAptitude.getValue(location.toString());
            if (list != null) {
                tooltips.add(Component.empty());
                tooltips.add(Component.translatable("tooltip.aptitude.item_requirement").withStyle(ChatFormatting.DARK_PURPLE));
                for (Aptitudes aptitudes : list) {
                    Aptitude aptitude = aptitudes.getAptitude();
                    if (aptitude != null) {
                        ChatFormatting colour = (AptitudeCapability.get().getAptitudeLevel(aptitude) >= aptitudes.getAptitudeLvl()) ? ChatFormatting.GREEN : ChatFormatting.RED;
                        tooltips.add(Component.translatable("tooltip.aptitude.item_requirements", Component.translatable(aptitude.getKey()), Component.literal(String.valueOf(aptitudes.getAptitudeLvl())).withStyle(colour)));
                    }
                }
            }
        }
    }
}


