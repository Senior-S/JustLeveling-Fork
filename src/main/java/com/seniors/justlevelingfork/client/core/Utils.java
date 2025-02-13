package com.seniors.justlevelingfork.client.core;

import com.seniors.justlevelingfork.registry.RegistrySounds;
import com.mojang.authlib.GameProfile;

import java.awt.Color;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.entity.SkullBlockEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.ModList;


public class Utils {
    public static int FONT_COLOR = (new Color(62, 62, 62)).getRGB();
    public static final Minecraft client = Minecraft.getInstance();

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

    public static void drawToolTip(GuiGraphics matrixStack, Component tooltip, int mouseX, int mouseY) {
        matrixStack.renderTooltip(client.font, tooltip, mouseX, mouseY);
    }

    public static void drawToolTipList(GuiGraphics matrixStack, List<Component> tooltip, int mouseX, int mouseY) {
        matrixStack.renderTooltip(client.font, tooltip, Optional.empty(), mouseX, mouseY);
    }

    public static boolean checkMouse(int x, int y, int mouseX, int mouseY, int width, int height) {
        return (x <= mouseX && x + width >= mouseX && y <= mouseY && y + height >= mouseY);
    }

    public static String numberFormat(int number) {
        return String.format("%02d", number);
    }

    public static void drawCenter(GuiGraphics matrixStack, Component string, int x, int y) {
        matrixStack.drawString(client.font, string, x - client.font.width(string) / 2, y, FONT_COLOR, false);
    }

    public static void drawCenterWithShadow(GuiGraphics matrixStack, String string, int x, int y, int color) {
        matrixStack.drawString(client.font, string, x - client.font.width(string) / 2, y, color, true);
    }

    public static void drawCenterWithShadow(GuiGraphics matrixStack, Component string, int x, int y, int color) {
        matrixStack.drawString(client.font, string, x - client.font.width(string) / 2, y, color, true);
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

    public static int getPlayerXP(Player player) {
        return (int)(getExperienceForLevel(player.experienceLevel) + (player.experienceProgress * player.getXpNeededForNextLevel()));
    }

    public static int xpBarCap(int level) {
        if (level >= 30)
            return 112 + (level - 30) * 9;

        if (level >= 15)
            return 37 + (level - 15) * 5;

        return 7 + level * 2;
    }

    public static int getExperienceForLevel(int level) {
        if (level == 0) return 0;
        if (level <= 15) return sum(level, 7, 2);
        if (level <= 30) return 315 + sum(level - 15, 37, 5);
        return 1395 + sum(level - 30, 112, 9);
    }

    private static int sum(int n, int a0, int d) {
        return n * (2 * a0 + (n - 1) * d) / 2;
    }


}


