package com.seniors.justlevelingfork.client.gui;

import com.seniors.justlevelingfork.client.core.Aptitudes;
import com.seniors.justlevelingfork.client.core.Utils;
import com.seniors.justlevelingfork.common.capability.AptitudeCapability;
import com.seniors.justlevelingfork.handler.HandlerAptitude;
import com.mojang.blaze3d.systems.RenderSystem;

import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

public class OverlayAptitudeGui {
    private static final Minecraft client = Minecraft.getInstance();
    private static List<Aptitudes> aptitudes = null;
    private static int showTicks = 0;

    public static void render(GuiGraphics matrixStack) {
        if (client.level != null && client.player != null && showTicks > 0 && AptitudeCapability.get(client.player) != null && aptitudes != null) {
            matrixStack.pose().pushPose();
            int xOff = client.getWindow().getGuiScaledWidth() / 2;
            int yOff = client.getWindow().getGuiScaledHeight() / 4;

            MutableComponent overlayMessage = Component.translatable("overlay.aptitude.message");
            int overlayWidth = client.font.width(overlayMessage) / 2;

            RenderSystem.enableBlend();
            for (int i = 0; i < 16; i++) {
                float f = (showTicks < 40) ? (0.003F * i / 40.0F * showTicks) : (0.003F * i);
                int alpha = Math.round(f * 255.0F);
                int color = alpha << 24;
                matrixStack.fill(xOff - overlayWidth - 14 - 16 - i, yOff - 11 - 16 - i, xOff + overlayWidth + 14 + 16 - i, yOff + 45 + 16 - i, color);
            }
            float alpha = (showTicks < 40) ? (0.025F * showTicks) : 1.0F;
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, alpha);
            Utils.drawCenterWithShadow(matrixStack, overlayMessage, xOff, yOff, 16733525);

            for (int j = 0; j < aptitudes.size(); j++) {
                Aptitudes abilities = aptitudes.get(j);
                String level = Integer.toString(abilities.getAptitudeLvl());
                boolean met = (AptitudeCapability.get().getAptitudeLevel(abilities.getAptitude()) >= abilities.getAptitudeLvl());

                int x = xOff + j * 24 - aptitudes.size() * 12;
                int y = yOff + 15;

                RenderSystem.enableBlend();
                RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, alpha);
                matrixStack.blit(abilities.getAptitude().getLockedTexture(abilities.getAptitudeLvl()), x, y, 0.0F, 0.0F, 16, 16, 16, 16);
                Utils.drawCenterWithShadow(matrixStack, level, x + 16, y + 12, met ? 5635925 : 16733525);
            }

            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            matrixStack.pose().popPose();
        }
    }

    public static void clientTick() {
        if (showTicks > 0) showTicks--;
    }

    public static void showWarning(String aptitude) {
        aptitudes = HandlerAptitude.getValue(aptitude);
        showTicks = 90;
    }
}


