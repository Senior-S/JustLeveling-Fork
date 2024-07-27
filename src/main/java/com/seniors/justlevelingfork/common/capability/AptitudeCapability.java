 package com.seniors.justlevelingfork.common.capability;
 import com.seniors.justlevelingfork.client.core.Aptitudes;
 import com.seniors.justlevelingfork.handler.HandlerAptitude;
 import com.seniors.justlevelingfork.handler.HandlerConfigCommon;
 import com.seniors.justlevelingfork.network.packet.client.AptitudeOverlayCP;
 import com.seniors.justlevelingfork.registry.RegistryAptitudes;
 import com.seniors.justlevelingfork.registry.RegistryCapabilities;
 import com.seniors.justlevelingfork.registry.RegistryPassives;
 import com.seniors.justlevelingfork.registry.RegistrySkills;
 import com.seniors.justlevelingfork.registry.RegistryTitles;
 import com.seniors.justlevelingfork.registry.aptitude.Aptitude;
 import com.seniors.justlevelingfork.registry.passive.Passive;
 import com.seniors.justlevelingfork.registry.skills.Skill;
 import com.seniors.justlevelingfork.registry.title.Title;
 import java.util.HashMap;
 import java.util.List;
 import java.util.Map;
 import java.util.Objects;

 import net.minecraft.client.Minecraft;
 import net.minecraft.nbt.CompoundTag;
 import net.minecraft.resources.ResourceLocation;
 import net.minecraft.world.entity.Entity;
 import net.minecraft.world.entity.player.Player;
 import net.minecraft.world.item.ItemStack;
 import net.minecraft.world.level.block.Block;
 import net.minecraftforge.common.util.INBTSerializable;
 import net.minecraftforge.registries.ForgeRegistries;
 
 public class AptitudeCapability implements INBTSerializable<CompoundTag> {
   public Map<String, Integer> aptitudeLevel = mapAptitudes();
   public Map<String, Integer> passiveLevel = mapPassive();
   public Map<String, Boolean> toggleSkill = mapSkills();
   public Map<String, Boolean> unlockTitle = mapTitles();
   public String playerTitle = RegistryTitles.getTitle("titleless").getName();
   public double betterCombatEntityRange = 0.0D;
   
   public int counterAttackTimer = 0;
   public boolean counterAttack = false;
   
   private Map<String, Integer> mapAptitudes() {
     Map<String, Integer> map = new HashMap<>();
     for (int i = 0; i < RegistryAptitudes.APTITUDES_REGISTRY.get().getValues().stream().toList().size(); i++) {
       Aptitude aptitude = RegistryAptitudes.APTITUDES_REGISTRY.get().getValues().stream().toList().get(i);
       map.put(aptitude.getName(), 1);
     } 
     return map;
   }
   
   private Map<String, Integer> mapPassive() {
     Map<String, Integer> map = new HashMap<>();
     for (int i = 0; i < RegistryPassives.PASSIVES_REGISTRY.get().getValues().stream().toList().size(); i++) {
       Passive passive = RegistryPassives.PASSIVES_REGISTRY.get().getValues().stream().toList().get(i);
       map.put(passive.getName(), 0);
     } 
     return map;
   }
   
   private Map<String, Boolean> mapSkills() {
     Map<String, Boolean> map = new HashMap<>();
     for (int i = 0; i < RegistrySkills.SKILLS_REGISTRY.get().getValues().stream().toList().size(); i++) {
       Skill skill = RegistrySkills.SKILLS_REGISTRY.get().getValues().stream().toList().get(i);
       map.put(skill.getName(), false);
     } 
     return map;
   }
   
   private Map<String, Boolean> mapTitles() {
     Map<String, Boolean> map = new HashMap<>();
     for (int i = 0; i < RegistryTitles.TITLES_REGISTRY.get().getValues().stream().toList().size(); i++) {
       Title title = RegistryTitles.TITLES_REGISTRY.get().getValues().stream().toList().get(i);
       map.put(title.getName(), title.requirement);
     } 
     return map;
   }
   
   public boolean getCounterAttack() {
     return this.counterAttack;
   }
   
   public void setCounterAttack(boolean set) {
     this.counterAttack = set;
   }
   
   public int getCounterAttackTimer() {
     return this.counterAttackTimer;
   }
   
   public void setCounterAttackTimer(int timer) {
     this.counterAttackTimer = timer;
   }
   
   public static AptitudeCapability get(Player player) {
     return player.getCapability(RegistryCapabilities.APTITUDE).orElseThrow(() -> new IllegalArgumentException("Player " + player.getName() + " does not have Capabilities!"));
   }
   
   public static AptitudeCapability get() {
     return (Minecraft.getInstance()).player.getCapability(RegistryCapabilities.APTITUDE).orElseThrow(() -> new IllegalArgumentException("Player does not have Capabilities!"));
   }
   
   public int getAptitudeLevel(Aptitude aptitude) {
     return this.aptitudeLevel.get(aptitude.getName());
   }
   
   public void setAptitudeLevel(Aptitude aptitude, int lvl) {
     this.aptitudeLevel.put(aptitude.getName(), lvl);
   }
   
   public void addAptitudeLevel(Aptitude aptitude, int addLvl) {
     this.aptitudeLevel.put(aptitude.getName(), Math.min(this.aptitudeLevel.get(aptitude.getName()) + addLvl, HandlerConfigCommon.aptitudeMaxLevel.get()));
   }
   
   public int getPassiveLevel(Passive passive) {
     return this.passiveLevel.get(passive.getName());
   }
   
   public void addPassiveLevel(Passive passive, int addLvl) {
     this.passiveLevel.put(passive.getName(), Math.min(this.passiveLevel.get(passive.getName()) + addLvl, passive.levelsRequired.length));
   }
   
   public void subPassiveLevel(Passive passive, int subLvl) {
     this.passiveLevel.put(passive.getName(), Math.max(this.passiveLevel.get(passive.getName()) - subLvl, 0));
   }
   
   public boolean getToggleSkill(Skill skill) {
     return this.toggleSkill.get(skill.getName());
   }
   
   public void setToggleSkill(Skill skill, boolean toggle) {
     this.toggleSkill.put(skill.getName(),toggle);
   }
   
   public boolean getLockTitle(Title title) {
     return this.unlockTitle.get(title.getName());
   }
   
   public void setUnlockTitle(Title title, boolean requirement) {
     this.unlockTitle.put(title.getName(), requirement);
   }
   
   public String getPlayerTitle() {
     return this.playerTitle;
   }
   
   public void setPlayerTitle(Title title) {
     this.playerTitle = title.getName();
   }
   
   public boolean isDroppable(Player player, ItemStack item) {
     String itemName = ForgeRegistries.ITEMS.getKey(item.getItem()).toString();
     List<Aptitudes> list = HandlerAptitude.getValue(itemName);
     if (list != null) {
       for (Aptitudes aptitude : list) {
         if (getAptitudeLevel(aptitude.getAptitude()) < aptitude.getAptitudeLvl() && aptitude.isDroppable()) {
           if (player instanceof net.minecraft.server.level.ServerPlayer) AptitudeOverlayCP.send(player, aptitude.getResource());
           return true;
         } 
       } 
     }
     return false;
   }
   
   public boolean canUseItem(Player player, ItemStack item) {
     return canUse(player, Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(item.getItem())));
   }
   
   public boolean canUseBlock(Player player, Block block) {
     return canUse(player, Objects.requireNonNull(ForgeRegistries.BLOCKS.getKey(block)));
   }
   
   public boolean canUseEntity(Player player, Entity entity) {
     return canUse(player, Objects.requireNonNull(ForgeRegistries.ENTITY_TYPES.getKey(entity.getType())));
   }
   
   private boolean canUse(Player player, ResourceLocation resource) {
     List<Aptitudes> aptitude = HandlerAptitude.getValue(resource.toString());
     if (aptitude != null) {
       for (Aptitudes aptitudes : aptitude) {
         if (getAptitudeLevel(aptitudes.getAptitude()) < aptitudes.getAptitudeLvl()) {
           if (player instanceof net.minecraft.server.level.ServerPlayer) AptitudeOverlayCP.send(player, resource.toString()); 
           return false;
         } 
       } 
     }
     return true;
   }


   public CompoundTag serializeNBT() {
     CompoundTag nbt = new CompoundTag(); int i;
     for (i = 0; i < RegistryAptitudes.APTITUDES_REGISTRY.get().getValues().stream().toList().size(); i++) {
       Aptitude aptitude = RegistryAptitudes.APTITUDES_REGISTRY.get().getValues().stream().toList().get(i);
       nbt.putInt("aptitude." + aptitude.getName(), this.aptitudeLevel.get(aptitude.getName()));
     } 
     for (i = 0; i < RegistryPassives.PASSIVES_REGISTRY.get().getValues().stream().toList().size(); i++) {
       Passive passive = RegistryPassives.PASSIVES_REGISTRY.get().getValues().stream().toList().get(i);
       nbt.putInt("passive." + passive.getName(), this.passiveLevel.get(passive.getName()));
     } 
     for (i = 0; i < RegistrySkills.SKILLS_REGISTRY.get().getValues().stream().toList().size(); i++) {
       Skill skill = RegistrySkills.SKILLS_REGISTRY.get().getValues().stream().toList().get(i);
       nbt.putBoolean("skill." + skill.getName(), this.toggleSkill.get(skill.getName()));
     } 
     for (i = 0; i < RegistryTitles.TITLES_REGISTRY.get().getValues().stream().toList().size(); i++) {
       Title title = RegistryTitles.TITLES_REGISTRY.get().getValues().stream().toList().get(i);
       nbt.putBoolean("title." + title.getName(), this.unlockTitle.get(title.getName()));
     } 
     nbt.putInt("counterAttackTimer", this.counterAttackTimer);
     nbt.putBoolean("counterAttack", this.counterAttack);
     nbt.putString("playerTitle", this.playerTitle);
     nbt.putDouble("betterCombatEntityRange", this.betterCombatEntityRange);
     return nbt;
   }
   
   public void deserializeNBT(CompoundTag nbt) {
     int i;
     for (i = 0; i < RegistryAptitudes.APTITUDES_REGISTRY.get().getValues().toArray().length; i++) {
       Aptitude aptitude = RegistryAptitudes.APTITUDES_REGISTRY.get().getValues().stream().toList().get(i);
       this.aptitudeLevel.put(aptitude.getName(), nbt.getInt("aptitude." + aptitude.getName()));
     } 
     for (i = 0; i < RegistryPassives.PASSIVES_REGISTRY.get().getValues().toArray().length; i++) {
       Passive passive = RegistryPassives.PASSIVES_REGISTRY.get().getValues().stream().toList().get(i);
       this.passiveLevel.put(passive.getName(),nbt.getInt("passive." + passive.getName()));
     } 
     for (i = 0; i < RegistrySkills.SKILLS_REGISTRY.get().getValues().toArray().length; i++) {
       Skill skill = RegistrySkills.SKILLS_REGISTRY.get().getValues().stream().toList().get(i);
       this.toggleSkill.put(skill.getName(), nbt.getBoolean("skill." + skill.getName()));
     } 
     for (i = 0; i < RegistryTitles.TITLES_REGISTRY.get().getValues().toArray().length; i++) {
       Title title = RegistryTitles.TITLES_REGISTRY.get().getValues().stream().toList().get(i);
       this.unlockTitle.put(title.getName(), nbt.getBoolean("title." + title.getName()));
     } 
     this.counterAttackTimer = nbt.getInt("counterAttackTimer");
     this.counterAttack = nbt.getBoolean("counterAttack");
     this.playerTitle = nbt.getString("playerTitle");
     this.betterCombatEntityRange = nbt.getDouble("betterCombatEntityRange");
   }
 }


