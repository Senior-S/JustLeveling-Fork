package com.seniors.justlevelingfork.handler;

import com.seniors.justlevelingfork.client.core.SortPassives;
import com.seniors.justlevelingfork.client.core.SortSkills;
import net.minecraftforge.common.ForgeConfigSpec;

public class HandlerConfigClient {
    public static final ForgeConfigSpec.Builder CONFIG = new ForgeConfigSpec.Builder();

    public static final ForgeConfigSpec SPEC;

    public static final ForgeConfigSpec.BooleanValue showCriticalRollSkillOverlay;
    public static final ForgeConfigSpec.BooleanValue showLuckyDropSkillOverlay;
    public static final ForgeConfigSpec.BooleanValue showSkillModName;
    public static final ForgeConfigSpec.BooleanValue showTitleModName;
    public static final ForgeConfigSpec.EnumValue<SortPassives> sortPassive;
    public static final ForgeConfigSpec.EnumValue<SortSkills> sortSkill;
    public static boolean defaultShowCriticalRollSkillOverlay = true;
    public static boolean defaultShowLuckDropSkillOverlay = true;
    public static boolean defaultShowSkillModName = false;
    public static boolean defaultShowTitleModName = false;
    public static SortPassives defaultSortPassive = SortPassives.ByName;
    public static SortSkills defaultSortSkill = SortSkills.ByLevel;

    static {
        CONFIG.push("general");
        showCriticalRollSkillOverlay = CONFIG.define("showCriticalRollSkillOverlay", defaultShowCriticalRollSkillOverlay);
        showLuckyDropSkillOverlay = CONFIG.define("showLuckyDropSkillOverlay", defaultShowLuckDropSkillOverlay);
        showSkillModName = CONFIG.define("showSkillModName", defaultShowSkillModName);
        showTitleModName = CONFIG.define("showTitleModName", defaultShowTitleModName);
        sortPassive = CONFIG.defineEnum("sortPassive", defaultSortPassive);
        sortSkill = CONFIG.defineEnum("sortSkill", defaultSortSkill);
        CONFIG.pop();
        SPEC = CONFIG.build();
    }
}


