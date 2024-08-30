package com.seniors.justlevelingfork.client.core;

import com.mojang.authlib.GameProfile;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.seniors.justlevelingfork.registry.RegistrySounds;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.SkullBlockEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.ModList;

import java.awt.*;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;


public class Utils {
    public static int FONT_COLOR = (new Color(62, 62, 62)).getRGB();
    public static final Minecraft client = Minecraft.getInstance();

    public static void renderGuiItem(ItemStack itemStack, int x, int y, float scale) {
        client.textureManager.getTexture(TextureAtlas.LOCATION_BLOCKS).setFilter(false, false);
        BakedModel bakedModel = client.getItemRenderer().getModel(itemStack, (Level)null, client.player, 0);
        RenderSystem.setShaderTexture(0, TextureAtlas.LOCATION_BLOCKS);
        RenderSystem.enableBlend();
        RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        PoseStack posestack = RenderSystem.getModelViewStack();
        posestack.pushPose();
        posestack.translate(((float)x), ((float)y), 100.0D);
        posestack.translate(8.0D, 8.0D, 0.0D);
        posestack.scale(1.0F, -1.0F, 1.0F);
        posestack.scale(16.0F + scale, 16.0F + scale, 16.0F + scale);
        RenderSystem.applyModelViewMatrix();
        PoseStack posestack1 = new PoseStack();
        MultiBufferSource.BufferSource multibuffersource$buffersource = Minecraft.getInstance().renderBuffers().bufferSource();
        boolean flag = !bakedModel.usesBlockLight();
        if (flag) {
            Lighting.setupForFlatItems();
        }

        client.getItemRenderer().render(itemStack, ItemTransforms.TransformType.GUI, false, posestack1, multibuffersource$buffersource, 15728880, OverlayTexture.NO_OVERLAY, bakedModel);
        multibuffersource$buffersource.endBatch();
        RenderSystem.enableDepthTest();
        if (flag) {
            Lighting.setupFor3DItems();
        }

        posestack.popPose();
        RenderSystem.applyModelViewMatrix();
    }

    public static ItemStack playerHead() {
        ItemStack head = new ItemStack(Items.PLAYER_HEAD);
        if (client.player != null) {
            if (client.player.getGameProfile().getProperties().isEmpty()) {
                return head;
            }
            CompoundTag nbt = head.getOrCreateTag();
            GameProfile gameProfile = client.player.getGameProfile();
            SkullBlockEntity.updateGameprofile(gameProfile, profile -> nbt.put("SkullOwner", NbtUtils.writeGameProfile(new CompoundTag(), profile)));
            head.setTag(nbt);
        }
        return head;
    }

    public static void playSound() {
        client.getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
    }

    public static void drawToolTip(Screen screen, PoseStack matrixStack, Component tooltip, int mouseX, int mouseY) {
        matrixStack.pushPose();
        screen.renderTooltip(matrixStack, tooltip, mouseX, mouseY);
        matrixStack.popPose();
    }

    public static void drawToolTipList(Screen screen, PoseStack matrixStack, List<Component> tooltip, int mouseX, int mouseY) {
        matrixStack.pushPose();
        screen.renderTooltip(matrixStack, tooltip, Optional.empty(), mouseX, mouseY);
        matrixStack.popPose();
    }

    public static boolean checkMouse(int x, int y, int mouseX, int mouseY, int width, int height) {
        return (x <= mouseX && x + width >= mouseX && y <= mouseY && y + height >= mouseY);
    }

    public static String numberFormat(int number) {
        return String.format("%02d", number);
    }

    public static void drawCenter(PoseStack matrixStack, Component string, int x, int y) {
        client.font.draw(matrixStack, string, (float)x - (float)client.font.width(string) / 2.0F, (float)y, FONT_COLOR);
    }

    public static void drawCenterWithShadow(PoseStack matrixStack, String string, int x, int y, int color) {
        client.font.drawShadow(matrixStack, string, x - client.font.width(string) / 2.0F, y, color, true);
    }

    public static void drawCenterWithShadow(PoseStack matrixStack, Component string, int x, int y, int color) {
        client.font.drawShadow(matrixStack, string.getString(), x - client.font.width(string) / 2.0F, y, color, true);
    }

    public static String intToRoman(int number) {
        String[] thousands = {"", "M", "MM", "MMM"};
        String[] hundreds = {"", "C", "CC", "CCC", "CD", "D", "DC", "DCC", "DCCC", "CM"};
        String[] tens = {"", "X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC"};
        String[] units = {"", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX"};
        return thousands[number / 1000] + thousands[number / 1000] + hundreds[number % 1000 / 100] + tens[number % 100 / 10];
    }

    public static String periodValue(double value) {
        DecimalFormat df = new DecimalFormat("#.#");
        String number = String.valueOf(value);
        if (number.contains(".")) {
            String decimal = number.split("\\.")[1];
            int zero = 1;
            for (int i = 0; i < decimal.length(); i++) {
                if (decimal.charAt(i) == '0') {
                    zero++;
                }
            }

            String format = "#." + "#".repeat(zero);
            return (new DecimalFormat(format)).format(value);
        }
        return df.format(value);
    }

    public static String getModName(String modId) {
        AtomicReference<String> modName = new AtomicReference<>("Misspelled Mod");
        if (modId != null) {
            ModList.get().getModContainerById(modId).ifPresent(modContainer -> modName.set(modContainer.getModInfo().getDisplayName()));
        }
        return modName.get();
    }

    @OnlyIn(Dist.CLIENT)
    public static void getTitleSound() {
        client.getSoundManager().play(SimpleSoundInstance.forUI(RegistrySounds.GAIN_TITLE.get(), 1.0F));
    }
}


