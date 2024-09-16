package com.seniors.justlevelingfork.integration;

import com.seniors.justlevelingfork.kubejs.events.CustomEvents;
import com.seniors.justlevelingfork.kubejs.events.LevelUpEvent;
import com.seniors.justlevelingfork.registry.aptitude.Aptitude;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.fml.ModList;

public class KubeJSIntegration {

    public static boolean isModLoaded() {
        return ModList.get().isLoaded("kubejs");
    }

    public static boolean postLevelUpEvent(Player player, Aptitude aptitude) {
        LevelUpEvent event = new LevelUpEvent(player, aptitude);
        CustomEvents.APTITUDE_LEVELUP.post(event);
        return event.getCancelled();
    }

}
