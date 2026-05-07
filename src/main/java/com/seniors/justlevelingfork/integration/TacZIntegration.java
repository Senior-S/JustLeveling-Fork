package com.seniors.justlevelingfork.integration;

import com.seniors.justlevelingfork.common.capability.AptitudeCapability;
import com.seniors.justlevelingfork.handler.HandlerCommonConfig;
import com.tacz.guns.api.event.common.GunFireEvent;
import com.tacz.guns.item.ModernKineticGunItem;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class TacZIntegration {

    public static boolean isModLoaded() {
        return FabricLoader.getInstance().isModLoaded("tacz");
    }

    public static void load() {
        if (isModLoaded()) {
            GunFireEvent.CALLBACK.register(new TacZIntegration()::onGunFireEvent);
        }
    }

    public void onGunFireEvent(GunFireEvent event) {
        if (event.getShooter() instanceof Player player) {
            if (HandlerCommonConfig.HANDLER.instance().logTaczGunNames) {
                ItemStack itemStack = event.getGunItemStack();
                ResourceLocation gunResourceLocation = getGunId(itemStack);
                if (gunResourceLocation == null) {
                    return;
                }

                player.sendSystemMessage(Component.literal(String.format("[JLFork] >> Gun ID: %s", gunResourceLocation)));
            }

            if (!player.isCreative()) {
                ItemStack itemStack = event.getGunItemStack();
                ResourceLocation gunResourceLocation = getGunId(itemStack);
                if (gunResourceLocation == null) {
                    return;
                }

                AptitudeCapability provider = AptitudeCapability.get(player);
                if (provider != null && !provider.canUseItem(player, gunResourceLocation)) {
                    event.setCanceled(true);
                }
            }
        }
    }

    private ResourceLocation getGunId(ItemStack itemStack) {
        if (!(itemStack.getItem() instanceof ModernKineticGunItem gunItem)) {
            return null;
        }

        return gunItem.getGunId(itemStack);
    }
}
