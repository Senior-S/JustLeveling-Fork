 package com.seniors.justlevelingfork.handler;
 import com.seniors.justlevelingfork.common.capability.AptitudeCapability;
 import net.minecraft.world.entity.LivingEntity;
 import net.minecraft.world.entity.player.Player;
 import net.minecraft.world.item.ItemStack;
 import net.minecraftforge.eventbus.api.EventPriority;
 import net.minecraftforge.eventbus.api.SubscribeEvent;
 import net.minecraftforge.fml.ModList;
 import top.theillusivec4.curios.api.event.CurioChangeEvent;
 
 public class HandlerCurios {
   public static boolean isModLoaded() {
     return ModList.get().isLoaded("curios");
   }
   
   @SubscribeEvent(priority = EventPriority.HIGHEST)
   public void onChangeCurio(CurioChangeEvent event) {
     LivingEntity livingEntity = event.getEntity(); if (livingEntity instanceof Player player) {
           if (!player.isCreative()) {
         ItemStack item1 = event.getTo();
         ItemStack item2 = event.getFrom();
         
         AptitudeCapability aptitudeCapability = AptitudeCapability.get(player);
         if (aptitudeCapability.isDroppable(player, item1)) {
           player.drop(item1.copy(), false);
           item1.setCount(0);
         } 
         if (aptitudeCapability.isDroppable(player, item2)) {
           player.drop(item2.copy(), false);
           item2.setCount(0);
         } 
       }  }
   
   }
 }


