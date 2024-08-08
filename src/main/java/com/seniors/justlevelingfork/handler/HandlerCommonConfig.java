package com.seniors.justlevelingfork.handler;

import com.google.gson.GsonBuilder;
import com.seniors.justlevelingfork.JustLevelingFork;
import com.seniors.justlevelingfork.config.StringListGroup;
import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.autogen.Boolean;
import dev.isxander.yacl3.config.v2.api.autogen.*;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.loading.FMLPaths;

import java.util.Arrays;
import java.util.List;

public class HandlerCommonConfig {
    public static ConfigClassHandler<HandlerCommonConfig> HANDLER = ConfigClassHandler.createBuilder(HandlerCommonConfig.class)
            .id(new ResourceLocation(JustLevelingFork.MOD_ID, "config"))
            .serializer(config -> GsonConfigSerializerBuilder.create(config)
                    .setPath(FMLPaths.CONFIGDIR.get().resolve("justleveling-fork.common.json5"))
                    .appendGsonBuilder(GsonBuilder::setPrettyPrinting)
                    .setJson5(true)
                    .build())
            .build();

    @SerialEntry(comment = "DON'T CHANGE THIS, REQUIRED TO AUTOMATICALLY TRY TO PORT LOCK ITEMS FROM THE OLD CONFIG.")
    @Boolean(formatter = Boolean.Formatter.TRUE_FALSE)
    public boolean usingNewConfig = false;

    @SerialEntry(comment = "Should the mod automatically check for updates on load?")
    @Boolean(formatter = Boolean.Formatter.ON_OFF)
    public boolean checkForUpdates = true;

    // General options
    @SerialEntry(comment = "Aptitudes Max Level")
    @AutoGen(category = "common", group = "general")
    @IntField(min = 2, max = 1000)
    public int aptitudeMaxLevel = 32;
    @SerialEntry(comment = "First aptitudes level cost")
    @AutoGen(category = "common", group = "general")
    @IntField(min = 1, max = 1000)
    public int aptitudeFirstCostLevel = 5;
    @SerialEntry(comment = "Show potions overlay over skills")
    @AutoGen(category = "common", group = "general")
    @Boolean(formatter = Boolean.Formatter.ON_OFF)
    public boolean showPotionsHud = true;
    @SerialEntry(comment = "If true, when trying to craft a non unlocked item, it will close the crafting the menu")
    @AutoGen(category = "common", group = "general")
    @Boolean(formatter = Boolean.Formatter.ON_OFF)
    public boolean closeCraftingMenu = true;

    @SerialEntry(comment = "If true, locked items will be automatically dropped from player hands")
    @AutoGen(category = "common", group = "general")
    @Boolean(formatter = Boolean.Formatter.ON_OFF)
    public boolean dropLockedItems = false;

    @SerialEntry(comment = "TAC:Zero have a special id system, so if you wanna get the id to restrict you need to enable this and shoot.")
    @AutoGen(category = "common", group = "general")
    @Boolean(formatter = Boolean.Formatter.ON_OFF)
    public boolean logTaczGunNames = false;

    @SerialEntry(comment = "If Iron's Spells 'n Spellbooks is present, it will log the spells id's on cast required to restrict them.")
    @AutoGen(category = "common", group = "general")
    @Boolean(formatter = Boolean.Formatter.ON_OFF)
    public boolean logSpellIds = false;

    // Passive options
    @SerialEntry(comment = "Attack Damage passive value at max level")
    @AutoGen(category = "common", group = "passives")
    @FloatField(min = 0.0f, max = 10000.0f)
    public float attackDamageValue = 1.5f;

    @SerialEntry(comment = "Attack Knockback passive value at max level")
    @AutoGen(category = "common", group = "passives")
    @FloatField(min = 0.0f, max = 10000.0f)
    public float attackKnockbackValue = 0.4f;

    @SerialEntry(comment = "Max Health passive value at max level")
    @AutoGen(category = "common", group = "passives")
    @FloatField(min = 0.0f, max = 10000.0f)
    public float maxHealthValue = 20.0f;

    @SerialEntry(comment = "Knockback Resistance passive value at max level")
    @AutoGen(category = "common", group = "passives")
    @FloatField(min = 0.0f, max = 10000.0f)
    public float knockbackResistanceValue = 0.5f;

    @SerialEntry(comment = "Movement Speed passive value at max level")
    @AutoGen(category = "common", group = "passives")
    @FloatField(min = 0.0f, max = 10000.0f)
    public float movementSpeedValue = 0.05f;

    @SerialEntry(comment = "Projectile Damage passive value at max level")
    @AutoGen(category = "common", group = "passives")
    @FloatField(min = 0.0f, max = 10000.0f)
    public float projectileDamageValue = 5.0f;

    @SerialEntry(comment = "Armor passive value at max level")
    @AutoGen(category = "common", group = "passives")
    @FloatField(min = 0.0f, max = 10000.0f)
    public float armorValue = 4.0f;

    @SerialEntry(comment = "Armor Toughness passive value at max level")
    @AutoGen(category = "common", group = "passives")
    @FloatField(min = 0.0f, max = 10000.0f)
    public float armorToughnessValue = 1.0f;

    @SerialEntry(comment = "Attack Speed passive value at max level")
    @AutoGen(category = "common", group = "passives")
    @FloatField(min = 0.0f, max = 10000.0f)
    public float attackSpeedValue = 0.4f;

    @SerialEntry(comment = "Entity Reach passive value at max level")
    @AutoGen(category = "common", group = "passives")
    @FloatField(min = 0.0f, max = 10000.0f)
    public float entityReachValue = 1.0f;

    @SerialEntry(comment = "Block Reach passive value at max level")
    @AutoGen(category = "common", group = "passives")
    @FloatField(min = 0.0f, max = 10000.0f)
    public float blockReachValue = 1.5f;

    @SerialEntry(comment = "Break Speed passive value at max level")
    @AutoGen(category = "common", group = "passives")
    @FloatField(min = 0.0f, max = 10000.0f)
    public float breakSpeedValue = 0.5f;

    @SerialEntry(comment = "Beneficial Effect passive value at max level")
    @AutoGen(category = "common", group = "passives")
    @FloatField(min = 0.0f, max = 10000.0f)
    public float beneficialEffectValue = 60.0f;

    @SerialEntry(comment = "Magic Resist passive value at max level")
    @AutoGen(category = "common", group = "passives")
    @FloatField(min = 0.0f, max = 10000.0f)
    public float magicResistValue = 0.5f;

    @SerialEntry(comment = "Critical Damage passive value at max level")
    @AutoGen(category = "common", group = "passives")
    @FloatField(min = 0.0f, max = 10000.0f)
    public float criticalDamageValue = 0.25f;

    @SerialEntry(comment = "Luck passive value at max level")
    @AutoGen(category = "common", group = "passives")
    @FloatField(min = 0.0f, max = 10000.0f)
    public float luckValue = 2.0f;


    // Skills options
    @SerialEntry(comment = "One Handed skill damage amplifier increase")
    @AutoGen(category = "common", group = "skills")
    @FloatField(min = 0.0f, max = 10000.0f)
    public float oneHandedAmplifier = 0.5f;

    @SerialEntry(comment = "Fighting Spirit skill strength potion effect boost")
    @AutoGen(category = "common", group = "skills")
    @IntField(min = 1, max = 255)
    public int fightingSpiritBoost = 1;

    @SerialEntry(comment = "Fighting Spirit skill strength potion effect duration")
    @AutoGen(category = "common", group = "skills")
    @IntField(min = 1, max = 3600)
    public int fightingSpiritDuration = 3;

    @SerialEntry(comment = "Berserker skill health percent")
    @AutoGen(category = "common", group = "skills")
    @IntField(min = 0, max = 100)
    public int berserkerPercent = 30;

    @SerialEntry(comment = "Athletics skill air modifier multiply")
    @AutoGen(category = "common", group = "skills")
    @FloatField(min = 0.0f, max = 500.0f)
    public float athleticsModifier = 1.5f;

    @SerialEntry(comment = "Lion Heart skill negative potion effect percent")
    @AutoGen(category = "common", group = "skills")
    @IntField(min = 0, max = 100)
    public int lionHeartPercent = 50;

    @SerialEntry(comment = "Quick Reposition skill speed potion effect boost")
    @AutoGen(category = "common", group = "skills")
    @IntField(min = 1, max = 255)
    public int quickRepositionBoost = 2;

    @SerialEntry(comment = "Quick Reposition skill speed potion effect duration")
    @AutoGen(category = "common", group = "skills")
    @IntField(min = 1, max = 3600)
    public int quickRepositionDuration = 3;

    @SerialEntry(comment = "Stealth Mastery skill enemy vision percent")
    @AutoGen(category = "common", group = "skills")
    @IntField(min = 0, max = 100)
    public int stealthMasteryUnSneakPercent = 20;

    @SerialEntry(comment = "Stealth Mastery skill enemy vision percent when player is sneaking")
    @AutoGen(category = "common", group = "skills")
    @IntField(min = 0, max = 100)
    public int stealthMasterySneakPercent = 60;

    @SerialEntry(comment = "Stealth Mastery skill arrow damage modifier multiply")
    @AutoGen(category = "common", group = "skills")
    @FloatField(min = 0.0f, max = 100.0f)
    public float stealthMasteryModifier = 1.25f;

    @SerialEntry(comment = "Counter Attack skill duration to return the attack")
    @AutoGen(category = "common", group = "skills")
    @IntField(min = 0, max = 3600)
    public int counterAttackDuration = 3;

    @SerialEntry(comment = "Counter Attack skill damage returned percent")
    @AutoGen(category = "common", group = "skills")
    @IntField(min = 0, max = 500)
    public int counterAttackPercent = 50;

    @SerialEntry(comment = "Diamond Skin defence potion effect boost")
    @AutoGen(category = "common", group = "skills")
    @IntField(min = 1, max = 255)
    public int diamondSkinBoost = 2;

    @SerialEntry(comment = "Diamond skill defense amplifier increase when player is sneaking")
    @AutoGen(category = "common", group = "skills")
    @FloatField(min = 0.0f, max = 10000.0f)
    public float diamondSkinSneakAmplifier = 2.0f;

    @SerialEntry(comment = "Haggler skill villager trades cost percent reduced")
    @AutoGen(category = "common", group = "skills")
    @IntField(min = 0, max = 100)
    public int hagglerPercent = 20;

    @SerialEntry(comment = "Expert Alchemist skill potion amplifier increase")
    @AutoGen(category = "common", group = "skills")
    @FloatField(min = 0.0f, max = 10000.0f)
    public float alchemyManipulationAmplifier = 1.0f;

    @SerialEntry(comment = "Obsidian Smasher skill obsidian breaking speed modifier multiply")
    @AutoGen(category = "common", group = "skills")
    @FloatField(min = 0.0f, max = 100.0f)
    public float obsidianSmasherModifier = 10.0f;

    @SerialEntry(comment = "Treasure Hunter skill probability chance to get a treasure in dirt")
    @AutoGen(category = "common", group = "skills")
    @IntField(min = 1, max = 10000)
    public int treasureHunterProbability = 500;

    @SerialEntry(comment = "Treasure Hunter skill treasures item list")
    @ListGroup(controllerFactory = StringListGroup.class, valueFactory = StringListGroup.class)
    public List<String> treasureHunterItemList = Arrays.asList("minecraft:flint", "minecraft:clay_ball", "trashList[minecraft:feather;minecraft:bone_meal]", "lostToolList[minecraft:stick;minecraft:wooden_pickaxe{Damage:59};minecraft:wooden_shovel{Damage:59};minecraft:wooden_axe{Damage:59}]", "discList[minecraft:music_disc_13;minecraft:music_disc_cat;minecraft:music_disc_blocks;minecraft:music_disc_chirp;minecraft:music_disc_far;minecraft:music_disc_mall;minecraft:music_disc_mellohi;minecraft:music_disc_stal;minecraft:music_disc_strad;minecraft:music_disc_ward;minecraft:music_disc_11;minecraft:music_disc_wait]", "seedList[minecraft:beetroot_seeds;minecraft:wheat_seeds;minecraft:pumpkin_seeds;minecraft:melon_seeds;minecraft:brown_mushroom;minecraft:red_mushroom]", "mineralList[minecraft:raw_iron;minecraft:raw_gold;minecraft:raw_copper;minecraft:coal;minecraft:charcoal]");

    @SerialEntry(comment = "Convergence skill probability chance to obtain part of the spent material")
    @AutoGen(category = "common", group = "skills")
    @IntField(min = 1, max = 10000)
    public int convergenceProbability = 8;

    @SerialEntry(comment = "Convergence skill convergence item list")
    @ListGroup(controllerFactory = StringListGroup.class, valueFactory = StringListGroup.class)
    public List<String> convergenceItemList = List.of();

    @SerialEntry(comment = "Life Eater skill life steal amplifier increase")
    @AutoGen(category = "common", group = "skills")
    @FloatField(min = 0.0f, max = 10000.0f)
    public float lifeEaterModifier = 1.0f;

    @SerialEntry(comment = "Critical Roll skill critic modifier multiply when you roll a 6")
    @AutoGen(category = "common", group = "skills")
    @FloatField(min = 0.0f, max = 100.0f)
    public float criticalRoll6Modifier = 1.25f;

    @SerialEntry(comment = "Critical Roll skill critic probability reduce when you roll a 1")
    @AutoGen(category = "common", group = "skills")
    @IntField(min = 1, max = 10000)
    public int criticalRoll1Probability = 3;

    @SerialEntry(comment = "Lucky Drop skill mob drops modifier multiply")
    @AutoGen(category = "common", group = "skills")
    @FloatField(min = 0.0f, max = 10000.0f)
    public float luckyDropModifier = 2.0f;

    @SerialEntry(comment = "Lucky Drop skill mobs drops probability")
    @AutoGen(category = "common", group = "skills")
    @IntField(min = 1, max = 10000)
    public int luckyDropProbability = 10;

    @SerialEntry(comment = "Limit Breaker skill deal damage amplifier")
    @AutoGen(category = "common", group = "skills")
    @FloatField(min = 0.0f, max = 10000.0f)
    public float limitBreakerAmplifier = 999.0f;

    @SerialEntry(comment = "Limit Breaker skill deal damage probability")
    @AutoGen(category = "common", group = "skills")
    @IntField(min = 1, max = 10000)
    public int limitBreakerProbability = 100;
}
