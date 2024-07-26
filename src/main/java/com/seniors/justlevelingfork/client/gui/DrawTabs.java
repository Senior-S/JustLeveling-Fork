package com.seniors.justlevelingfork.client.gui;

import com.seniors.justlevelingfork.client.core.Tabs;
import com.seniors.justlevelingfork.client.core.Utils;
import com.seniors.justlevelingfork.client.screen.JustLevelingScreen;
import com.seniors.justlevelingfork.registry.RegistryItems;
import com.mojang.blaze3d.systems.RenderSystem;

import java.util.ArrayList;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class DrawTabs {
    public static final ResourceLocation TEXTURE = new ResourceLocation("justlevelingfork", "textures/gui/container/tabs.png");
    public static final Minecraft client = Minecraft.getInstance();
    public static ArrayList<Tabs> tabList = new ArrayList<>();
    public static boolean isMouseCheck = false;
    public static boolean checkMouse = false;

    public static void render(GuiGraphics matrixStack, int mouseX, int mouseY, int textureWidth, int textureHeight, int recipe) {
        Screen screen = client.screen;
        if (client.player != null) {
            isMouseCheck = false;
            tabList = new ArrayList<>();
            tabList.add(new Tabs("inventory", Utils.playerHead(), new InventoryScreen(client.player), screen instanceof InventoryScreen, Component.translatable("container.inventory")));
            tabList.add(new Tabs("leveling", RegistryItems.LEVELING_BOOK.get().getDefaultInstance(), new JustLevelingScreen(), screen instanceof JustLevelingScreen, Component.translatable("screen.aptitude.title")));
        }
        for (int i = 0; i < tabList.size(); i++) {
            Tabs type = tabList.get(i);
            int x = (client.getWindow().getGuiScaledWidth() - textureWidth) / 2 + i * 27 + recipe;
            int y = (client.getWindow().getGuiScaledHeight() - textureHeight) / 2 - 28;
            renderWidget(matrixStack, type, x, y, mouseX, mouseY);
        }
    }

    public static void renderWidget(GuiGraphics matrixStack, Tabs type, int x, int y, int mouseX, int mouseY) {
        matrixStack.pose().pushPose();
        RenderSystem.enableBlend();
        matrixStack.blit(TEXTURE, x, y, type.getName().equals("inventory") ? 0 : 26, type.isScreen() ? 32 : 0, 26, 32);
        if (Utils.checkMouse(x, y, mouseX, mouseY, 26, 32))
            Utils.drawToolTip(matrixStack, type.getComponentName(), mouseX, mouseY);
        float scale = (type.getItemStack().getItem() instanceof net.minecraft.world.item.StandingAndWallBlockItem) ? 1.125F : 1.0F;
        float newX = (x + 13.0F - 8.0F) / scale;
        float newY = (y + 15.0F - 8.0F + (type.isScreen() ? 0.0F : 2.0F)) / scale;
        matrixStack.pose().pushPose();
        matrixStack.pose().scale(scale, scale, 1.0F);
        matrixStack.renderItem(type.getItemStack(), (int) newX, (int) newY);
        matrixStack.pose().popPose();
        matrixStack.pose().popPose();

        if (Utils.checkMouse(x, y, mouseX, mouseY, 26, 32) && !type.isScreen()) {
            Utils.drawToolTip(matrixStack, type.getComponentName(), mouseX, mouseY);
            isMouseCheck = true;
            if (checkMouse) {
                setScreen(tabList.indexOf(type));
                checkMouse = false;
            }
        }
    }

    public static void setScreen(int i) {
        Utils.playSound();
        client.setScreen(tabList.get(i).getScreen());
    }

    public static void mouseClicked(int button) {
        if (button == 0 && isMouseCheck) checkMouse = true;
    }

    public static void onClose() {
        checkMouse = false;
    }
}


