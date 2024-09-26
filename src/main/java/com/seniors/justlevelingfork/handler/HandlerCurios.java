package com.seniors.justlevelingfork.handler;

import com.seniors.justlevelingfork.common.capability.AptitudeCapability;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameType;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.event.CurioEquipEvent;
import top.theillusivec4.curios.api.type.inventory.IDynamicStackHandler;

public class HandlerCurios {
    public static boolean isModLoaded() {
        return ModList.get().isLoaded("curios");
    }

    @SubscribeEvent
    public void onCurioCanEquipEvent(CurioEquipEvent event){
        LivingEntity livingEntity = event.getEntity();
        if (livingEntity instanceof ServerPlayer player) {
            if (!player.isCreative()) {
                ItemStack item = event.getStack();

                AptitudeCapability aptitudeCapability = AptitudeCapability.get(player);
                if (aptitudeCapability == null) return;

                try {
                    if (!aptitudeCapability.canUseItem(player, item)) {
                        event.setResult(Event.Result.DENY);
                    }
                }
                // This NullPointerException can happen if this event is triggered before the player fully establish the connection.
                catch (NullPointerException ignored) {
                    event.setResult(Event.Result.DENY);
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
            if (aptitudeCapability == null) return;
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