package com.seniors.justlevelingfork.common.capability;

import com.seniors.justlevelingfork.client.core.Aptitudes;
import com.seniors.justlevelingfork.client.gui.OverlayAptitudeGui;
import com.seniors.justlevelingfork.handler.HandlerAptitude;
import com.seniors.justlevelingfork.handler.HandlerCommonConfig;
import com.seniors.justlevelingfork.network.packet.client.AptitudeOverlayCP;
import com.seniors.justlevelingfork.registry.*;
import com.seniors.justlevelingfork.registry.aptitude.Aptitude;
import com.seniors.justlevelingfork.registry.passive.Passive;
import com.seniors.justlevelingfork.registry.skills.Skill;
import com.seniors.justlevelingfork.registry.title.Title;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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
        List<Aptitude> aptitudeList = RegistryAptitudes.APTITUDES_REGISTRY.get().getValues().stream().toList();
        for (Aptitude aptitude : aptitudeList) {
            map.put(aptitude.getName(), 1);
        }
        return map;
    }

    private Map<String, Integer> mapPassive() {
        Map<String, Integer> map = new HashMap<>();
        List<Passive> passiveList = RegistryPassives.PASSIVES_REGISTRY.get().getValues().stream().toList();
        for (Passive passive : passiveList) {
            map.put(passive.getName(), 0);
        }
        return map;
    }

    private Map<String, Boolean> mapSkills() {
        Map<String, Boolean> map = new HashMap<>();
        List<Skill> skillList = RegistrySkills.SKILLS_REGISTRY.get().getValues().stream().toList();
        for (Skill skill : skillList) {
            map.put(skill.getName(), false);
        }
        return map;
    }

    private Map<String, Boolean> mapTitles() {
        Map<String, Boolean> map = new HashMap<>();
        List<Title> titleList = RegistryTitles.TITLES_REGISTRY.get().getValues().stream().toList();
        for (Title title : titleList) {
            map.put(title.getName(), title.Requirement);
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

    public int getGlobalLevel(){
        return this.aptitudeLevel.values().stream().mapToInt(Integer::intValue).sum();
    }

    public void addAptitudeLevel(Aptitude aptitude, int addLvl) {
        this.aptitudeLevel.put(aptitude.getName(), Math.min(this.aptitudeLevel.get(aptitude.getName()) + addLvl, HandlerCommonConfig.HANDLER.instance().aptitudeMaxLevel));
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
        this.toggleSkill.put(skill.getName(), toggle);
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

    public boolean canUseItem(Player player, ItemStack item) {
        return canUse(player, Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(item.getItem())));
    }

    public boolean canUseItem(Player player, ResourceLocation resourceLocation) {
        return canUse(player, resourceLocation);
    }

    // Required for locking PointBlank
    public boolean canUseItemClient(ItemStack item) {
        return canUseClient(Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(item.getItem())));
    }

    public boolean canUseSpecificID(Player player, String specificID){
        return canUse(player, specificID);
    }

    public boolean canUseBlock(Player player, Block block) {
        return canUse(player, Objects.requireNonNull(ForgeRegistries.BLOCKS.getKey(block)));
    }

    public boolean canUseEntity(Player player, Entity entity) {
        return canUse(player, Objects.requireNonNull(ForgeRegistries.ENTITIES.getKey(entity.getType())));
    }

    private boolean canUse(Player player, ResourceLocation resource) {
        List<Aptitudes> aptitude = HandlerAptitude.getValue(resource.toString());
        if (aptitude != null) {
            for (Aptitudes aptitudes : aptitude) {
                if (getAptitudeLevel(aptitudes.getAptitude()) < aptitudes.getAptitudeLvl()) {
                    if (player instanceof net.minecraft.server.level.ServerPlayer)
                        AptitudeOverlayCP.send(player, resource.toString());
                    return false;
                }
            }
        }
        return true;
    }

    // Required for locking PointBlank
    private boolean canUseClient(ResourceLocation resource) {
        List<Aptitudes> aptitude = HandlerAptitude.getValue(resource.toString());
        if (aptitude != null) {
            for (Aptitudes aptitudes : aptitude) {
                if (getAptitudeLevel(aptitudes.getAptitude()) < aptitudes.getAptitudeLvl()) {
                    OverlayAptitudeGui.showWarning(resource.toString());
                    return false;
                }
            }
        }
        return true;
    }

    private boolean canUse(Player player, String restrictionID) {
        List<Aptitudes> aptitude = HandlerAptitude.getValue(restrictionID);
        if (aptitude != null) {
            for (Aptitudes aptitudes : aptitude) {
                if (getAptitudeLevel(aptitudes.getAptitude()) < aptitudes.getAptitudeLvl()) {
                    if (player instanceof net.minecraft.server.level.ServerPlayer)
                        AptitudeOverlayCP.send(player, restrictionID);
                    return false;
                }
            }
        }
        return true;
    }

    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        for (Aptitude aptitude : RegistryAptitudes.APTITUDES_REGISTRY.get().getValues().stream().toList()){
            nbt.putInt("aptitude." + aptitude.getName(), this.aptitudeLevel.get(aptitude.getName()));
        }
        for (Passive passive : RegistryPassives.PASSIVES_REGISTRY.get().getValues().stream().toList()){
            nbt.putInt("passive." + passive.getName(), this.passiveLevel.get(passive.getName()));
        }
        for (Skill skill : RegistrySkills.SKILLS_REGISTRY.get().getValues().stream().toList()){
            nbt.putBoolean("skill." + skill.getName(), this.toggleSkill.get(skill.getName()));
        }
        for (Title title : RegistryTitles.TITLES_REGISTRY.get().getValues().stream().toList()){
            nbt.putBoolean("title." + title.getName(), this.unlockTitle.get(title.getName()));
        }
        nbt.putInt("counterAttackTimer", this.counterAttackTimer);
        nbt.putBoolean("counterAttack", this.counterAttack);
        nbt.putString("playerTitle", this.playerTitle);
        nbt.putDouble("betterCombatEntityRange", this.betterCombatEntityRange);
        return nbt;
    }

    public void deserializeNBT(CompoundTag nbt) {
        for (Aptitude aptitude : RegistryAptitudes.APTITUDES_REGISTRY.get().getValues().stream().toList()){
            this.aptitudeLevel.put(aptitude.getName(), nbt.getInt("aptitude." + aptitude.getName()));
        }
        for (Passive passive : RegistryPassives.PASSIVES_REGISTRY.get().getValues().stream().toList()){
            this.passiveLevel.put(passive.getName(), nbt.getInt("passive." + passive.getName()));
        }
        for (Skill skill : RegistrySkills.SKILLS_REGISTRY.get().getValues().stream().toList()){
            this.toggleSkill.put(skill.getName(), nbt.getBoolean("skill." + skill.getName()));
        }
        for (Title title : RegistryTitles.TITLES_REGISTRY.get().getValues().stream().toList()){
            this.unlockTitle.put(title.getName(), nbt.getBoolean("title." + title.getName()));
        }

        this.counterAttackTimer = nbt.getInt("counterAttackTimer");
        this.counterAttack = nbt.getBoolean("counterAttack");
        this.playerTitle = nbt.getString("playerTitle");
        this.betterCombatEntityRange = nbt.getDouble("betterCombatEntityRange");
    }

    public void copyFrom(AptitudeCapability source) {
        for (Aptitude aptitude : RegistryAptitudes.APTITUDES_REGISTRY.get().getValues().stream().toList()){
            this.aptitudeLevel.put(aptitude.getName(), source.aptitudeLevel.get(aptitude.getName()));
        }
        for (Passive passive : RegistryPassives.PASSIVES_REGISTRY.get().getValues().stream().toList()){
            this.passiveLevel.put(passive.getName(), source.passiveLevel.get(passive.getName()));
        }
        for (Skill skill : RegistrySkills.SKILLS_REGISTRY.get().getValues().stream().toList()){
            this.toggleSkill.put(skill.getName(), source.toggleSkill.get(skill.getName()));
        }
        for (Title title : RegistryTitles.TITLES_REGISTRY.get().getValues().stream().toList()){
            this.unlockTitle.put(title.getName(), source.unlockTitle.get(title.getName()));
        }

        this.counterAttackTimer = source.counterAttackTimer;
        this.counterAttack = source.counterAttack;
        this.playerTitle = source.playerTitle;
        this.betterCombatEntityRange = source.betterCombatEntityRange;
    }
}


