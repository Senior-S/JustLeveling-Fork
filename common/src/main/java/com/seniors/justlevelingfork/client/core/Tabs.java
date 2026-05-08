
package com.seniors.justlevelingfork.client.core;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

public final class Tabs {
    private final String name;
    public String getName() { return name; }
    private final ItemStack itemStack;
    public ItemStack getItemStack() { return itemStack; }
    private final Screen screen;
    public Screen getScreen() { return screen; }
    private final Boolean isScreen;
    public boolean isScreen() { return isScreen; }

    private final Component getName;
    public Component getComponentName() { return getName; }

    public Tabs(String name, ItemStack itemStack, Screen screen, Boolean isScreen, Component getName) {
        this.name = name;
        this.itemStack = itemStack;
        this.screen = screen;
        this.isScreen = isScreen;
        this.getName = getName;
    }
}


