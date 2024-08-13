package com.seniors.justlevelingfork.handler;

import com.seniors.justlevelingfork.common.capability.AptitudeCapability;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameType;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.event.CurioChangeEvent;
import top.theillusivec4.curios.api.type.inventory.IDynamicStackHandler;

public class HandlerCurios {
    public static boolean isModLoaded() {
        return ModList.get().isLoaded("curios");
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onChangeCurio(CurioChangeEvent event) {
        LivingEntity livingEntity = event.getEntity();
        if (livingEntity instanceof Player player) {
            if (!player.isCreative()) {
                ItemStack item1 = event.getTo();
                ItemStack item2 = event.getFrom();

                AptitudeCapability aptitudeCapability = AptitudeCapability.get(player);
                if (!aptitudeCapability.canUseItem(player, item1)) {
                    player.drop(item1.copy(), false);
                    item1.setCount(0);
                }
                if (!aptitudeCapability.canUseItem(player, item2)) {
                    player.drop(item2.copy(), false);
                    item2.setCount(0);
                }
            }
        }
    }

    @SubscribeEvent
    public void onPlayerChangeGameModeEvent(PlayerEvent.PlayerChangeGameModeEvent event){
        if(!HandlerCurios.isModLoaded()){
            return;
        }

        if (event.getNewGameMode() == GameType.SURVIVAL){
            Player player = event.getEntity();

            AptitudeCapability aptitudeCapability = AptitudeCapability.get(player);
            CuriosApi.getCuriosInventory(player).ifPresent(curiosInventory -> {
                curiosInventory.getCurios().forEach((id, slotInventory) -> {
                    IDynamicStackHandler stackHandler =  slotInventory.getStacks();
                    for(int i = 0; i < stackHandler.getSlots(); i++){
                        ItemStack itemStack = stackHandler.getStackInSlot(i);

                        if (!aptitudeCapability.canUseItem(player, itemStack)) {
                            player.drop(itemStack, false);
                            itemStack.setCount(0);
                            stackHandler.setStackInSlot(i, ItemStack.EMPTY);
                            curiosInventory.clearSlotModifiers();
                        }
                    }
                });
            });
        }
    }
}