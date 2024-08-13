package com.seniors.justlevelingfork.client.gui;

import com.seniors.justlevelingfork.client.core.TitleQueue;
import com.seniors.justlevelingfork.client.core.Utils;
import com.seniors.justlevelingfork.registry.RegistryCapabilities;
import com.seniors.justlevelingfork.registry.title.Title;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.CustomizeGuiOverlayEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.awt.*;

@OnlyIn(Dist.CLIENT)
public class OverlayTitleGui {
    private final Minecraft client = Minecraft.getInstance();
    public static TitleQueue list = new TitleQueue();
    public static int timerTicks = 110;
    public static int showTicks = 110;
    private static int scaleTick = 0;

    @SubscribeEvent(priority = EventPriority.NORMAL)
    public void onHudRender(CustomizeGuiOverlayEvent.DebugText event) {
        GuiGraphics matrixStack = event.getGuiGraphics();
        if (this.client.level != null && this.client.player != null && showTicks >= 0 && this.client.player.getCapability(RegistryCapabilities.APTITUDE).isPresent()) {
            if (list.count() > 0) {
                Title getTitle = list.peek();
                if (showTicks < 20) {
                    scaleTick--;
                } else {
                    scaleTick++;
                }
                scaleTick = Mth.clamp(scaleTick, 0, 20);
                float scale2 = 0.05625F * scaleTick;
                matrixStack.pose().pushPose();
                matrixStack.pose().scale(scale2, scale2, 1.0F);
                int xOff2 = (int) (this.client.getWindow().getGuiScaledWidth() / scale2 / 2.0F);
                int yOff2 = (int) (this.client.getWindow().getGuiScaledHeight() / scale2 / 4.0F);
                Utils.drawCenterWithShadow(matrixStack, Component.translatable("overlay.title.you_gain_a_title"), xOff2, yOff2 - 10, Color.WHITE.getRGB());
                matrixStack.pose().popPose();

                float scale1 = 0.1F * scaleTick;
                matrixStack.pose().pushPose();
                matrixStack.pose().scale(scale1, scale1, 1.0F);
                int xOff1 = (int) (this.client.getWindow().getGuiScaledWidth() / scale1 / 2.0F);
                int yOff1 = (int) (this.client.getWindow().getGuiScaledHeight() / scale1 / 4.0F);
                Utils.drawCenterWithShadow(matrixStack, Component.translatable("overlay.title.format", Component.translatable(getTitle.getKey()).withStyle(ChatFormatting.BOLD)), xOff1, yOff1, Color.WHITE.getRGB());
                matrixStack.pose().popPose();
                if (showTicks == 0) {
                    list.dequeue();
                    showTicks = timerTicks;
                }
            } else {
                showTicks = 0;
            }
        }
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (list.count() > 0 && showTicks == timerTicks) Utils.getTitleSound();
        if (showTicks > 0) {
            showTicks--;
        }
    }

    public static void showWarning() {
        if (list.count() <= 1)
            showTicks = timerTicks;
    }
}


