package com.seniors.justlevelingfork;

import com.mojang.blaze3d.platform.InputConstants;
import com.seniors.justlevelingfork.client.gui.OverlayAptitudeGui;
import com.seniors.justlevelingfork.client.gui.OverlayTitleGui;
import com.seniors.justlevelingfork.client.screen.JustLevelingScreen;
import com.seniors.justlevelingfork.handler.HandlerCommonConfig;
import com.seniors.justlevelingfork.registry.RegistryClientEvents;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class JustLevelingClient {
    public static Minecraft client = Minecraft.getInstance();
    public static KeyMapping OPEN_JUSTLEVELING_SCREEN = new KeyMapping("key.justlevelingfork.open_aptitudes", InputConstants.Type.KEYSYM, 89, "key.justlevelingfork.title");

    @EventBusSubscriber(modid = JustLevelingFork.MOD_ID, value = {Dist.CLIENT})
    public static class ClientForgeEvents {
        @SubscribeEvent
        public static void checkKeyboard(InputEvent.Key event) {
            if (JustLevelingClient.client.player != null && JustLevelingClient.client.level != null &&
                    JustLevelingClient.OPEN_JUSTLEVELING_SCREEN.consumeClick())
                JustLevelingClient.client.setScreen(new JustLevelingScreen());
        }
    }

    @EventBusSubscriber(modid = JustLevelingFork.MOD_ID, value = {Dist.CLIENT}, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientProxy {
        @SubscribeEvent
        public static void clientSetup(FMLClientSetupEvent event) {
            ModLoadingContext.get().registerExtensionPoint(
                    ConfigScreenHandler.ConfigScreenFactory.class,
                    () -> new ConfigScreenHandler.ConfigScreenFactory(
                            (client, parent) ->
                                    HandlerCommonConfig.HANDLER.generateGui().generateScreen(parent)
                    )
            );

            MinecraftForge.EVENT_BUS.register(new RegistryClientEvents());
            MinecraftForge.EVENT_BUS.register(new OverlayAptitudeGui());
            MinecraftForge.EVENT_BUS.register(new OverlayTitleGui());

            if (!HandlerCommonConfig.HANDLER.instance().usingNewConfig) {
                JustLevelingFork.getLOGGER().info("Configuration not migrated yet, starting migration...");
                JustLevelingFork.migrateOldConfig();
            }
        }

        @SubscribeEvent
        public static void registerKeys(RegisterKeyMappingsEvent event) {
            event.register(JustLevelingClient.OPEN_JUSTLEVELING_SCREEN);
        }
    }
}
