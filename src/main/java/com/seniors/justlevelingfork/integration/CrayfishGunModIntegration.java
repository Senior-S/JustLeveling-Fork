package com.seniors.justlevelingfork.integration;

import com.mrcrayfish.guns.event.GunFireEvent;
import com.seniors.justlevelingfork.common.capability.AptitudeCapability;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;

public class CrayfishGunModIntegration {
    public static boolean isModLoaded() {
        return ModList.get().isLoaded("cgm");
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onGunFireEvent(GunFireEvent.Pre event){
        Player player = event.getEntity();

        if (!player.isCreative()) {
            ItemStack itemStack = event.getStack();
            AptitudeCapability provider = AptitudeCapability.get(player);
            if (!provider.canUseItemClient(itemStack)) {
                event.setCanceled(true);
            }
        }
    }
}
