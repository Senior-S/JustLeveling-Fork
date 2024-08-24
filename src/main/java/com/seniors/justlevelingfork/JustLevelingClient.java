package com.seniors.justlevelingfork;

import com.mojang.blaze3d.platform.InputConstants;
import com.seniors.justlevelingfork.client.gui.OverlayAptitudeGui;
import com.seniors.justlevelingfork.client.gui.OverlayTitleGui;
import com.seniors.justlevelingfork.client.screen.JustLevelingScreen;
import com.seniors.justlevelingfork.integration.CrayfishGunModIntegration;
import com.seniors.justlevelingfork.registry.RegistryClientEvents;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.ClientRegistry;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class JustLevelingClient {
    public static Minecraft client = Minecraft.getInstance();
    public static KeyMapping OPEN_JUSTLEVELING_SCREEN = new KeyMapping("key.justlevelingfork.open_aptitudes", InputConstants.Type.KEYSYM, 89, "key.justlevelingfork.title");

    @EventBusSubscriber(modid = JustLevelingFork.MOD_ID, value = {Dist.CLIENT})
    public static class ClientForgeEvents {
        @SubscribeEvent
        public static void checkKeyboard(InputEvent.KeyInputEvent event) {
            if (JustLevelingClient.client.player != null && JustLevelingClient.client.level != null &&
                    JustLevelingClient.OPEN_JUSTLEVELING_SCREEN.consumeClick())
                JustLevelingClient.client.setScreen(new JustLevelingScreen());
        }
    }

    @EventBusSubscriber(modid = JustLevelingFork.MOD_ID, value = {Dist.CLIENT}, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientProxy {
        @SubscribeEvent
        public static void clientSetup(FMLClientSetupEvent event) {
            MinecraftForge.EVENT_BUS.register(new RegistryClientEvents());
            MinecraftForge.EVENT_BUS.register(new OverlayAptitudeGui());
            MinecraftForge.EVENT_BUS.register(new OverlayTitleGui());
            if (CrayfishGunModIntegration.isModLoaded())
                MinecraftForge.EVENT_BUS.register(new CrayfishGunModIntegration());

            ClientRegistry.registerKeyBinding(JustLevelingClient.OPEN_JUSTLEVELING_SCREEN);
        }
    }
}
