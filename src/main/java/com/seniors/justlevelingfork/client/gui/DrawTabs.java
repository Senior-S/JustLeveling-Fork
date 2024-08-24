package com.seniors.justlevelingfork.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.seniors.justlevelingfork.JustLevelingFork;
import com.seniors.justlevelingfork.client.core.Tabs;
import com.seniors.justlevelingfork.client.core.Utils;
import com.seniors.justlevelingfork.client.screen.JustLevelingScreen;
import com.seniors.justlevelingfork.registry.RegistryItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.PlayerHeadItem;
import net.minecraft.world.item.StandingAndWallBlockItem;

import java.util.ArrayList;

public class DrawTabs {
    public static final ResourceLocation TEXTURE = new ResourceLocation(JustLevelingFork.MOD_ID, "textures/gui/container/tabs.png");
    public static final Minecraft client = Minecraft.getInstance();
    public static ArrayList<Tabs> tabList = new ArrayList<>();
    public static boolean isMouseCheck = false;
    public static boolean checkMouse = false;

    public static void render(Screen screenTarget, PoseStack matrixStack, int mouseX, int mouseY, int textureWidth, int textureHeight, int recipe) {
        Screen screen = client.screen;
        if (client.player != null) {
            isMouseCheck = false;
            tabList = new ArrayList<>();
            tabList.add(new Tabs("inventory", Utils.playerHead(), new InventoryScreen(client.player), screen instanceof InventoryScreen, new TranslatableComponent("container.inventory")));
            tabList.add(new Tabs("leveling", RegistryItems.LEVELING_BOOK.get().getDefaultInstance(), new JustLevelingScreen(), screen instanceof JustLevelingScreen, new TranslatableComponent("screen.aptitude.title")));
        }

        int i;
        Tabs type;
        int x;
        int y;
        for (i = 0; i < tabList.size(); i++) {
            type = tabList.get(i);
            x = (client.getWindow().getGuiScaledWidth() - textureWidth) / 2 + i * 27 + recipe;
            y = (client.getWindow().getGuiScaledHeight() - textureHeight) / 2 - 28;
            renderWidget(matrixStack, type, x, y);
        }

        for(i = 0; i < tabList.size(); ++i) {
            type = tabList.get(i);
            x = (client.getWindow().getGuiScaledWidth() - textureWidth) / 2 + i * 27 + recipe;
            y = (client.getWindow().getGuiScaledHeight() - textureHeight) / 2 - 28;
            if (Utils.checkMouse(x, y, mouseX, mouseY, 26, 32)) {
                Utils.drawToolTip(screenTarget, matrixStack, type.getComponentName(), mouseX, mouseY);
                if (!type.isScreen()) {
                    isMouseCheck = true;
                    if (checkMouse) {
                        setScreen(tabList.indexOf(type));
                        checkMouse = false;
                    }
                }
            }
        }
    }

    public static void renderWidget(PoseStack matrixStack, Tabs type, int x, int y) {
        matrixStack.pushPose();
        RenderSystem.enableBlend();
        RenderSystem.setShaderTexture(0, TEXTURE);
        GuiComponent.blit(matrixStack, x, y, (float) (type.getName().equals("inventory") ? 0 : 26), type.isScreen() ? 32.0F : 0.0F, 26, 32, 256, 256);
        float scale = !(type.getItemStack().getItem() instanceof StandingAndWallBlockItem) && !(type.getItemStack().getItem() instanceof PlayerHeadItem) ? 1.0F : 2.0F;
        float newX = (float)x + 13.0F - 8.0F;
        float newY = (float)y + 15.0F - 8.0F + (type.isScreen() ? 0.0F : 2.0F);
        matrixStack.pushPose();
        matrixStack.scale(scale, scale, 1.0F);
        Utils.renderGuiItem(type.getItemStack(), (int)newX, (int)newY, scale);
        matrixStack.popPose();
        matrixStack.popPose();
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


