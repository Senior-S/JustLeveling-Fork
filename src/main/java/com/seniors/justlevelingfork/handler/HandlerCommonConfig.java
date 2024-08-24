package com.seniors.justlevelingfork.handler;

import com.seniors.justlevelingfork.config.ConfigClassHandler;
import com.seniors.justlevelingfork.config.anotations.SerialEntry;

import java.util.Arrays;
import java.util.List;

public class HandlerCommonConfig {
    /*public static ConfigClassHandler<HandlerCommonConfig> HANDLER = ConfigClassHandler.createBuilder(HandlerCommonConfig.class)
            .id(new ResourceLocation(JustLevelingFork.MOD_ID, "config"))
            .serializer(config -> GsonConfigSerializerBuilder.create(config)
                    .setPath(Configuration.getAbsoluteDirectory().resolve("justleveling-fork.common.json5"))
                    .appendGsonBuilder(GsonBuilder::setPrettyPrinting)
                    .setJson5(true)
                    .build())
            .build();*/

    public static ConfigClassHandler<HandlerCommonConfig> HANDLER = new ConfigClassHandler<>(HandlerCommonConfig.class, "justleveling-fork.common.json5");

    @SerialEntry(comment = "DON'T CHANGE THIS, REQUIRED TO AUTOMATICALLY TRY TO PORT LOCK ITEMS FROM THE OLD CONFIG.")
    public boolean usingNewConfig = false;

    @SerialEntry(comment = "Should the mod automatically check for updates on load?")
    public boolean checkForUpdates = true;

    // General options
    @SerialEntry(comment = "Aptitudes Max Level")
    public int aptitudeMaxLevel = 32;

    @SerialEntry(comment = "Global max level, the global level is calculated summing all aptitudes level, so if this is set to 32 players will be able to only maximize 1 skill.")
    public int playersMaxGlobalLevel = 256;

    @SerialEntry(comment = "First aptitudes level cost")
    public int aptitudeFirstCostLevel = 5;
    @SerialEntry(comment = "Show potions overlay over skills")
    public boolean showPotionsHud = true;
    @SerialEntry(comment = "If true, when trying to craft a non unlocked item, it will close the crafting the menu")
    public boolean closeCraftingMenu = true;

    @SerialEntry(comment = "If true, locked items will be automatically dropped from player hands")
    public boolean dropLockedItems = false;

    @SerialEntry(comment = "TAC:Zero have a special id system, so if you wanna get the id to restrict you need to enable this and shoot.")
    public boolean logTaczGunNames = false;

    @SerialEntry(comment = "If Iron's Spells 'n Spellbooks is present, it will log the spells id's on cast required to restrict them.")
    public boolean logSpellIds = false;

    // Passive options
    @SerialEntry(comment = "Attack Damage passive value at max level")
    public float attackDamageValue = 1.5f;

    @SerialEntry(comment = "Attack damage passive levels. Don't modify the length of the array!")
    public int[] attackPassiveLevels = new int[]{5, 8, 11, 14, 17, 20, 23, 26, 29, 32};

    @SerialEntry(comment = "Attack Knockback passive value at max level")
    public float attackKnockbackValue = 0.4f;

    @SerialEntry(comment = "Attack knockback passive levels. Don't modify the length of the array!")
    public int[] attackKnockbackPassiveLevels = new int[]{8, 14, 20, 26, 32};

    @SerialEntry(comment = "Max Health passive value at max level")
    public float maxHealthValue = 20.0f;

    @SerialEntry(comment = "Max health passive levels. Don't modify the length of the array!")
    public int[] maxHealthPassiveLevels = new int[]{5, 8, 11, 14, 17, 20, 23, 26, 29, 32};

    @SerialEntry(comment = "Knockback Resistance passive value at max level")
    public float knockbackResistanceValue = 0.5f;

    @SerialEntry(comment = "Knockback resistance passive levels. Don't modify the length of the array!")
    public int[] knockbackResistancePassiveLevels = new int[]{8, 14, 20, 26, 32};

    @SerialEntry(comment = "Movement Speed passive value at max level")
    public float movementSpeedValue = 0.05f;

    @SerialEntry(comment = "Movement speed passive levels. Don't modify the length of the array!")
    public int[] movementSpeedPassiveLevels = new int[]{8, 14, 20, 26, 32};

    @SerialEntry(comment = "Projectile Damage passive value at max level")
    public float projectileDamageValue = 5.0f;

    @SerialEntry(comment = "Projectile damage passive levels. Don't modify the length of the array!")
    public int[] projectileDamagePassiveLevels = new int[]{8, 14, 20, 26, 32};

    @SerialEntry(comment = "Armor passive value at max level")
    public float armorValue = 4.0f;

    @SerialEntry(comment = "Armor passive levels. Don't modify the length of the array!")
    public int[] armorPassiveLevels = new int[]{8, 14, 20, 26, 32};

    @SerialEntry(comment = "Armor Toughness passive value at max level")
    public float armorToughnessValue = 1.0f;

    @SerialEntry(comment = "Armor toughness passive levels. Don't modify the length of the array!")
    public int[] armorToughnessPassiveLevels = new int[]{8, 14, 20, 26, 32};

    @SerialEntry(comment = "Attack Speed passive value at max level")
    public float attackSpeedValue = 0.4f;

    @SerialEntry(comment = "Attack speed passive levels. Don't modify the length of the array!")
    public int[] attackSpeedPassiveLevels = new int[]{8, 14, 20, 26, 32};

    @SerialEntry(comment = "Entity Reach passive value at max level")
    public float entityReachValue = 1.0f;

    @SerialEntry(comment = "Entity reach passive levels. Don't modify the length of the array!")
    public int[] entityReachPassiveLevels = new int[]{8, 14, 20, 26, 32};

    @SerialEntry(comment = "Block Reach passive value at max level")
    public float blockReachValue = 1.5f;

    @SerialEntry(comment = "Block reach passive levels. Don't modify the length of the array!")
    public int[] blockReachPassiveLevels = new int[]{8, 14, 20, 26, 32};

    @SerialEntry(comment = "Break Speed passive value at max level")
    public float breakSpeedValue = 0.5f;

    @SerialEntry(comment = "Break speed passive levels. Don't modify the length of the array!")
    public int[] breakSpeedPassiveLevels = new int[]{8, 14, 20, 26, 3};

    @SerialEntry(comment = "Beneficial Effect passive value at max level")
    public float beneficialEffectValue = 60.0f;

    @SerialEntry(comment = "Beneficial effect passive levels. Don't modify the length of the array!")
    public int[] beneficialEffectPassiveLevels = new int[]{5, 8, 11, 14, 17, 20, 23, 26, 29, 32};

    @SerialEntry(comment = "Magic Resist passive value at max level")
    public float magicResistValue = 0.5f;

    @SerialEntry(comment = "Magic resistance passive levels. Don't modify the length of the array!")
    public int[] magicResistPassiveLevels = new int[]{8, 14, 20, 26, 32};

    @SerialEntry(comment = "Critical Damage passive value at max level")
    public float criticalDamageValue = 0.25f;

    @SerialEntry(comment = "Critical damage passive levels. Don't modify the length of the array!")
    public int[] criticalDamagePassiveLevels = new int[]{5, 8, 11, 14, 17, 20, 23, 26, 29, 32};

    @SerialEntry(comment = "Luck passive value at max level")
    public float luckValue = 2.0f;

    @SerialEntry(comment = "Luck passive levels. Don't modify the length of the array!")
    public int[] luckPassiveLevels = new int[]{5, 8, 11, 14, 17, 20, 23, 26, 29, 32};


    // Skills options
    @SerialEntry(comment = "One Handed skill damage amplifier increase")
    public float oneHandedAmplifier = 0.5f;

    @SerialEntry(comment = "Fighting Spirit skill strength potion effect boost")
    public int fightingSpiritBoost = 1;

    @SerialEntry(comment = "Fighting Spirit skill strength potion effect duration")
    public int fightingSpiritDuration = 3;

    @SerialEntry(comment = "Berserker skill health percent")
    public int berserkerPercent = 30;

    @SerialEntry(comment = "Athletics skill air modifier multiply")
    public float athleticsModifier = 1.5f;

    @SerialEntry(comment = "Lion Heart skill negative potion effect percent")
    public int lionHeartPercent = 50;

    @SerialEntry(comment = "Quick Reposition skill speed potion effect boost")
    public int quickRepositionBoost = 2;

    @SerialEntry(comment = "Quick Reposition skill speed potion effect duration")
    public int quickRepositionDuration = 3;

    @SerialEntry(comment = "Stealth Mastery skill enemy vision percent")
    public int stealthMasteryUnSneakPercent = 20;

    @SerialEntry(comment = "Stealth Mastery skill enemy vision percent when player is sneaking")
    public int stealthMasterySneakPercent = 60;

    @SerialEntry(comment = "Stealth Mastery skill arrow damage modifier multiply")
    public float stealthMasteryModifier = 1.25f;

    @SerialEntry(comment = "Counter Attack skill duration to return the attack")
    public int counterAttackDuration = 3;

    @SerialEntry(comment = "Counter Attack skill damage returned percent")
    public int counterAttackPercent = 50;

    @SerialEntry(comment = "Diamond Skin defence potion effect boost")
    public int diamondSkinBoost = 2;

    @SerialEntry(comment = "Diamond skill defense amplifier increase when player is sneaking")
    public float diamondSkinSneakAmplifier = 2.0f;

    @SerialEntry(comment = "Haggler skill villager trades cost percent reduced")
    public int hagglerPercent = 20;

    @SerialEntry(comment = "Expert Alchemist skill potion amplifier increase")
    public float alchemyManipulationAmplifier = 1.0f;

    @SerialEntry(comment = "Obsidian Smasher skill obsidian breaking speed modifier multiply")
    public float obsidianSmasherModifier = 10.0f;

    @SerialEntry(comment = "Treasure Hunter skill probability chance to get a treasure in dirt")
    public int treasureHunterProbability = 500;

    @SerialEntry(comment = "Treasure Hunter skill treasures item list")
    public List<String> treasureHunterItemList = Arrays.asList("minecraft:flint", "minecraft:clay_ball", "trashList[minecraft:feather;minecraft:bone_meal]", "lostToolList[minecraft:stick;minecraft:wooden_pickaxe{Damage:59};minecraft:wooden_shovel{Damage:59};minecraft:wooden_axe{Damage:59}]", "discList[minecraft:music_disc_13;minecraft:music_disc_cat;minecraft:music_disc_blocks;minecraft:music_disc_chirp;minecraft:music_disc_far;minecraft:music_disc_mall;minecraft:music_disc_mellohi;minecraft:music_disc_stal;minecraft:music_disc_strad;minecraft:music_disc_ward;minecraft:music_disc_11;minecraft:music_disc_wait]", "seedList[minecraft:beetroot_seeds;minecraft:wheat_seeds;minecraft:pumpkin_seeds;minecraft:melon_seeds;minecraft:brown_mushroom;minecraft:red_mushroom]", "mineralList[minecraft:raw_iron;minecraft:raw_gold;minecraft:raw_copper;minecraft:coal;minecraft:charcoal]");

    @SerialEntry(comment = "Convergence skill probability chance to obtain part of the spent material")
    public int convergenceProbability = 8;

    @SerialEntry(comment = "Convergence skill convergence item list")
    public List<String> convergenceItemList = List.of();

    @SerialEntry(comment = "Life Eater skill life steal amplifier increase")
    public float lifeEaterModifier = 1.0f;

    @SerialEntry(comment = "Critical Roll skill critic modifier multiply when you roll a 6")
    public float criticalRoll6Modifier = 1.25f;

    @SerialEntry(comment = "Critical Roll skill critic probability reduce when you roll a 1")
    public int criticalRoll1Probability = 3;

    @SerialEntry(comment = "Lucky Drop skill mob drops modifier multiply")
    public float luckyDropModifier = 2.0f;

    @SerialEntry(comment = "Lucky Drop skill mobs drops probability")
    public int luckyDropProbability = 10;

    @SerialEntry(comment = "Limit Breaker skill deal damage amplifier")
    public float limitBreakerAmplifier = 999.0f;

    @SerialEntry(comment = "Limit Breaker skill deal damage probability")
    public int limitBreakerProbability = 100;

    // Skill Levels
    @SerialEntry(comment = "Required level to unlock skill")
    public int oneHandedRequiredLevel = 10;
    @SerialEntry(comment = "Required level to unlock skill")
    public int fightingSpiritRequiredLevel = 16;
    @SerialEntry(comment = "Required level to unlock skill")
    public int berserkerRequiredLevel = 30;
    @SerialEntry(comment = "Required level to unlock skill")
    public int athleticsRequiredLevel = 10;
    @SerialEntry(comment = "Required level to unlock skill")
    public int turtleShieldRequiredLevel = 20;
    @SerialEntry(comment = "Required level to unlock skill")
    public int lionHeartRequiredLevel = 32;
    @SerialEntry(comment = "Required level to unlock skill")
    public int quickRepositionRequiredLevel = 10;
    @SerialEntry(comment = "Required level to unlock skill")
    public int stealthMasteryRequiredLevel = 16;
    @SerialEntry(comment = "Required level to unlock skill")
    public int catEyesRequiredLevel = 32;
    @SerialEntry(comment = "Required level to unlock skill")
    public int snowWalkerRequiredLevel = 10;
    @SerialEntry(comment = "Required level to unlock skill")
    public int counterattackRequiredLevel = 18;
    @SerialEntry(comment = "Required level to unlock skill")
    public int diamondSkinRequiredLevel = 30;
    @SerialEntry(comment = "Required level to unlock skill")
    public int scholarRequiredLevel = 8;
    @SerialEntry(comment = "Required level to unlock skill")
    public int hagglerRequiredLevel = 16;
    @SerialEntry(comment = "Required level to unlock skill")
    public int alchemyManipulationRequiredLevel = 30;
    @SerialEntry(comment = "Required level to unlock skill")
    public int obsidianSmasherRequiredLevel = 12;
    @SerialEntry(comment = "Required level to unlock skill")
    public int treasureHunterRequiredLevel = 20;
    @SerialEntry(comment = "Required level to unlock skill")
    public int convergenceRequiredLevel = 30;
    @SerialEntry(comment = "Required level to unlock skill")
    public int safePortRequiredLevel = 12;
    @SerialEntry(comment = "Required level to unlock skill")
    public int lifeEaterRequiredLevel = 18;
    @SerialEntry(comment = "Required level to unlock skill")
    public int wornholeStorageRequiredLevel = 32;
    @SerialEntry(comment = "Required level to unlock skill")
    public int criticalRollRequiredLevel = 12;
    @SerialEntry(comment = "Required level to unlock skill")
    public int luckyDropRequiredLevel = 22;
    @SerialEntry(comment = "Required level to unlock skill")
    public int limitBreakerRequiredLevel = 32;
}
