package com.seniors.justlevelingfork;

import com.mojang.blaze3d.platform.InputConstants;
import com.seniors.justlevelingfork.client.gui.OverlayAptitudeGui;
import com.seniors.justlevelingfork.client.gui.OverlayTitleGui;
import com.seniors.justlevelingfork.client.screen.JustLevelingScreen;
import com.seniors.justlevelingfork.handler.HandlerCommonConfig;
import com.seniors.justlevelingfork.handler.HandlerConfigClient;
import com.seniors.justlevelingfork.network.ServerNetworking;
import com.seniors.justlevelingfork.registry.RegistryClientEvents;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;

public class JustLevelingClient implements ClientModInitializer {
    public static Minecraft client = Minecraft.getInstance();
    public static KeyMapping OPEN_JUSTLEVELING_SCREEN = new KeyMapping("key.justlevelingfork.open_aptitudes", InputConstants.Type.KEYSYM, 89, "key.justlevelingfork.title");

    @Override
    public void onInitializeClient() {
        HandlerConfigClient.load();
        ServerNetworking.initClient();
        RegistryClientEvents.load();
        KeyBindingHelper.registerKeyBinding(OPEN_JUSTLEVELING_SCREEN);

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (OPEN_JUSTLEVELING_SCREEN.consumeClick()) {
                if (client.player != null && client.level != null) {
                    client.setScreen(new JustLevelingScreen());
                }
            }
            OverlayAptitudeGui.clientTick();
            OverlayTitleGui.clientTick();
        });

        HudRenderCallback.EVENT.register((drawContext, tickDelta) -> {
            OverlayAptitudeGui.render(drawContext);
            OverlayTitleGui.render(drawContext);
        });
    }

    public static void openConfigScreen() {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.screen != null) {
            minecraft.setScreen(HandlerCommonConfig.HANDLER.generateGui().generateScreen(minecraft.screen));
        }
    }
}
