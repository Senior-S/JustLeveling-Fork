package com.seniors.justlevelingfork.registry.skills;

import com.seniors.justlevelingfork.handler.HandlerConvergenceItemsConfig;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ConvergenceSkill {
    public static ArrayList<ItemDrops> items = null;

    public static ItemStack drop(ItemStack getCrafting) {
        ItemStack stack = null;

        ArrayList<ItemDrops> itemDrops = getItems();

        if (itemDrops == null || itemDrops.isEmpty()){
           return stack;
        }

        for (ItemDrops drops : itemDrops) {
            if (drops.getCraftingItem != null && drops.getConvergenceItem != null &&
                    getCrafting.is(drops.getCraftingItem)) {
                stack = drops.getConvergenceItem.getDefaultInstance();
            }
        }

        return stack;
    }

    public static ArrayList<ItemDrops> getItems() {
        if (items != null){
            return items;
        }

        List<String> configList = HandlerConvergenceItemsConfig.HANDLER.instance().convergenceItemList;

        for (String getValue : configList) {
            String getCraftingItem = getValue.split("#")[0];
            String getCraftingItemNamespace = getCraftingItem.split(":")[0];
            String getCraftingItemPath = getCraftingItem.split(":")[1];
            Item craftingItem = ForgeRegistries.ITEMS.getValue(new ResourceLocation(getCraftingItemNamespace, getCraftingItemPath));

            String getConvergenceItem = getValue.split("#")[1];
            String getConvergenceItemNamespace = getConvergenceItem.split(":")[0];
            String getConvergenceItemPath = getConvergenceItem.split(":")[1];
            Item convergenceItem = ForgeRegistries.ITEMS.getValue(new ResourceLocation(getConvergenceItemNamespace, getConvergenceItemPath));

            items.add(new ItemDrops(craftingItem, convergenceItem));
        }
        return items;
    }

    public static final class ItemDrops {
        private final Item getCraftingItem;
        private final Item getConvergenceItem;

        public ItemDrops(Item getCraftingItem, Item getConvergenceItem) {
            this.getCraftingItem = getCraftingItem;
            this.getConvergenceItem = getConvergenceItem;
        }

    }

    public static List<String> defaultItemList = Arrays.asList("minecraft:iron_sword#minecraft:iron_ingot", "minecraft:iron_pickaxe#minecraft:iron_ingot", "minecraft:iron_axe#minecraft:iron_ingot", "minecraft:iron_shovel#minecraft:iron_nugget", "minecraft:iron_hoe#minecraft:iron_ingot", "minecraft:golden_sword#minecraft:gold_ingot", "minecraft:golden_pickaxe#minecraft:gold_ingot", "minecraft:golden_axe#minecraft:gold_ingot", "minecraft:golden_shovel#minecraft:gold_ingot", "minecraft:golden_hoe#minecraft:gold_ingot", "minecraft:diamond_sword#minecraft:diamond", "minecraft:diamond_pickaxe#minecraft:diamond", "minecraft:diamond_axe#minecraft:diamond", "minecraft:chainmail_helmet#minecraft:iron_nugget", "minecraft:chainmail_chestplate#minecraft:iron_nugget", "minecraft:chainmail_leggings#minecraft:iron_nugget", "minecraft:chainmail_boots#minecraft:iron_nugget", "minecraft:iron_helmet#minecraft:iron_ingot", "minecraft:iron_chestplate#minecraft:iron_ingot", "minecraft:iron_leggings#minecraft:iron_ingot", "minecraft:iron_boots#minecraft:iron_ingot", "minecraft:golden_helmet#minecraft:gold_ingot", "minecraft:golden_chestplate#minecraft:gold_ingot", "minecraft:golden_leggings#minecraft:gold_ingot", "minecraft:golden_boots#minecraft:gold_ingot", "minecraft:diamond_helmet#minecraft:diamond", "minecraft:diamond_chestplate#minecraft:diamond", "minecraft:diamond_leggings#minecraft:diamond", "minecraft:diamond_boots#minecraft:diamond", "minecraft:netherite_upgrade_smithing_template#minecraft:diamond", "minecraft:sentry_armor_trim_smithing_template#minecraft:diamond", "minecraft:vex_armor_trim_smithing_template#minecraft:diamond", "minecraft:wild_armor_trim_smithing_template#minecraft:diamond", "minecraft:coast_armor_trim_smithing_template#minecraft:diamond", "minecraft:dune_armor_trim_smithing_template#minecraft:diamond", "minecraft:wayfinder_armor_trim_smithing_template#minecraft:diamond", "minecraft:raiser_armor_trim_smithing_template#minecraft:diamond", "minecraft:shaper_armor_trim_smithing_template#minecraft:diamond", "minecraft:host_armor_trim_smithing_template#minecraft:diamond", "minecraft:ward_armor_trim_smithing_template#minecraft:diamond", "minecraft:silence_armor_trim_smithing_template#minecraft:diamond", "minecraft:tide_armor_trim_smithing_template#minecraft:diamond", "minecraft:snout_armor_trim_smithing_template#minecraft:diamond", "minecraft:rib_armor_trim_smithing_template#minecraft:diamond", "minecraft:eye_armor_trim_smithing_template#minecraft:diamond", "minecraft:spire_armor_trim_smithing_template#minecraft:diamond");
}


