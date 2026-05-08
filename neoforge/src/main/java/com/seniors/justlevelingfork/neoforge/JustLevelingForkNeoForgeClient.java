package com.seniors.justlevelingfork.neoforge;

import com.mojang.blaze3d.platform.InputConstants;
import com.seniors.justlevelingfork.JustLevelingFork;
import com.seniors.justlevelingfork.client.gui.OverlayAptitudeGui;
import com.seniors.justlevelingfork.client.gui.OverlayTitleGui;
import com.seniors.justlevelingfork.client.screen.JustLevelingScreen;
import com.seniors.justlevelingfork.client.core.Aptitudes;
import com.seniors.justlevelingfork.common.capability.AptitudeCapability;
import com.seniors.justlevelingfork.handler.HandlerAptitude;
import com.seniors.justlevelingfork.handler.HandlerCommonConfig;
import com.seniors.justlevelingfork.handler.HandlerConfigClient;
import com.seniors.justlevelingfork.integration.MiapiIntegration;
import com.seniors.justlevelingfork.network.ServerNetworking;
import com.seniors.justlevelingfork.registry.aptitude.Aptitude;
import net.minecraft.ChatFormatting;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class JustLevelingForkNeoForgeClient {
    private static final KeyMapping OPEN_JUSTLEVELING_SCREEN = new KeyMapping(
            "key.justlevelingfork.open_aptitudes",
            InputConstants.Type.KEYSYM,
            89,
            "key.justlevelingfork.title"
    );

    public static void init(IEventBus modBus) {
        HandlerConfigClient.load();
        ServerNetworking.initClient();

        modBus.addListener(JustLevelingForkNeoForgeClient::registerKeyMappings);
        modBus.addListener(JustLevelingForkNeoForgeClient::registerGuiLayers);
        NeoForge.EVENT_BUS.addListener(JustLevelingForkNeoForgeClient::clientTick);
        NeoForge.EVENT_BUS.addListener(JustLevelingForkNeoForgeClient::itemTooltip);
    }

    private static void registerKeyMappings(RegisterKeyMappingsEvent event) {
        event.register(OPEN_JUSTLEVELING_SCREEN);
    }

    private static void clientTick(ClientTickEvent.Post event) {
        Minecraft client = Minecraft.getInstance();
        while (OPEN_JUSTLEVELING_SCREEN.consumeClick()) {
            if (client.player != null && client.level != null) {
                client.setScreen(new JustLevelingScreen());
            }
        }
        OverlayAptitudeGui.clientTick();
        OverlayTitleGui.clientTick();
    }

    private static void registerGuiLayers(RegisterGuiLayersEvent event) {
        event.registerAbove(
                VanillaGuiLayers.CHAT,
                ResourceLocation.fromNamespaceAndPath(JustLevelingFork.MOD_ID, "aptitude_overlay"),
                (guiGraphics, deltaTracker) -> {
                    OverlayAptitudeGui.render(guiGraphics);
                    OverlayTitleGui.render(guiGraphics);
                }
        );
    }

    public static void openConfigScreen() {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.screen != null) {
            minecraft.setScreen(HandlerCommonConfig.HANDLER.generateGui().generateScreen(minecraft.screen));
        }
    }

    private static void itemTooltip(ItemTooltipEvent event) {
        appendAptitudeTooltip(event.getItemStack(), event.getToolTip());
    }

    private static void appendAptitudeTooltip(ItemStack itemStack, List<Component> tooltips) {
        if (Minecraft.getInstance().player == null || !Minecraft.getInstance().player.isAlive() || itemStack.isEmpty()) {
            return;
        }

        AptitudeCapability capability = AptitudeCapability.get();
        if (capability == null) {
            return;
        }

        List<Aptitudes> visibleRequirements = getVisibleRequirements(itemStack, capability);
        if (visibleRequirements.isEmpty()) {
            return;
        }

        tooltips.add(Component.empty());
        tooltips.add(Component.translatable("tooltip.aptitude.item_requirement").withStyle(ChatFormatting.DARK_PURPLE));
        for (Aptitudes aptitudes : visibleRequirements) {
            Aptitude aptitude = aptitudes.getAptitude();
            if (aptitude != null) {
                ChatFormatting colour = capability.getAptitudeLevel(aptitude) >= aptitudes.getAptitudeLvl() ? ChatFormatting.GREEN : ChatFormatting.RED;
                tooltips.add(Component.translatable("tooltip.aptitude.item_requirements", Component.translatable(aptitude.getKey()), Component.literal(String.valueOf(aptitudes.getAptitudeLvl())).withStyle(colour)));
            }
        }
    }

    private static List<Aptitudes> getVisibleRequirements(ItemStack itemStack, AptitudeCapability capability) {
        List<Aptitudes> requirements = new ArrayList<>();
        addRequirements(requirements, Objects.requireNonNull(BuiltInRegistries.ITEM.getKey(itemStack.getItem())));

        if (MiapiIntegration.isModularItem(itemStack)) {
            MiapiIntegration.getModuleIds(itemStack).forEach(moduleId -> addRequirements(requirements, moduleId));
        }

        return getVisibleRequirements(requirements, capability);
    }

    private static void addRequirements(List<Aptitudes> requirements, ResourceLocation id) {
        List<Aptitudes> list = HandlerAptitude.getValue(id.toString());
        if (list != null) {
            requirements.addAll(list);
        }
    }

    private static List<Aptitudes> getVisibleRequirements(List<Aptitudes> list, AptitudeCapability capability) {
        if (!HandlerCommonConfig.HANDLER.instance().hideMetUsageRequirements) {
            return list;
        }

        return list.stream()
                .filter(aptitudes -> aptitudes.getAptitude() == null || capability.getAptitudeLevel(aptitudes.getAptitude()) < aptitudes.getAptitudeLvl())
                .collect(Collectors.toList());
    }
}
