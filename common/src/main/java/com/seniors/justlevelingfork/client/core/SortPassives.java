 package com.seniors.justlevelingfork.client.core;
 
 public enum SortPassives {
   ByName(0, "tooltip.sort.button.by_name"),
   ByReverseName(1, "tooltip.sort.button.by_name_reversed");
   
   public final int index;
   public final String order;
   
   SortPassives(int index, String order) {
     this.index = index;
     this.order = order;
   }
   
   public static SortPassives fromIndex(int index) {
     if (index == 0) return ByName; 
     if (index == 1) return ByReverseName;
     
     return ByName;
   }
 }


