 package com.seniors.justlevelingfork.client.core;
 
 public enum SortSkills {
   ByName(0, "tooltip.sort.button.by_name"),
   ByReverseName(1, "tooltip.sort.button.by_name_reversed"),
   ByLevel(2, "tooltip.sort.button.by_level");
   
   public final int index;
   public final String order;
   
   SortSkills(int index, String order) {
     this.index = index;
     this.order = order;
   }
   
   public static SortSkills fromIndex(int index) {
     if (index == 0) return ByName; 
     if (index == 1) return ByReverseName; 
     if (index == 2) return ByLevel;
     
     return ByName;
   }
 }


