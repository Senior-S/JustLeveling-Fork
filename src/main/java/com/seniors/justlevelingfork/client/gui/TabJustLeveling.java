package com.seniors.justlevelingfork.client.gui;

import com.seniors.justlevelingfork.client.core.Utils;
import com.seniors.justlevelingfork.client.screen.JustLevelingScreen;
import dev.xkmc.l2tabs.tabs.core.BaseTab;
import dev.xkmc.l2tabs.tabs.core.TabManager;
import dev.xkmc.l2tabs.tabs.core.TabToken;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

public class TabJustLeveling extends BaseTab<TabJustLeveling> {

    public TabJustLeveling(TabToken<TabJustLeveling> token, TabManager manager, ItemStack stack, Component title){
        super(token, manager, stack, title);
    }

    @Override
    public void onTabClicked() {
        Utils.playSound();
        Minecraft.getInstance().setScreen(new JustLevelingScreen());
    }
}
