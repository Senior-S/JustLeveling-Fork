package com.seniors.justlevelingfork.registry.skills;

import com.google.gson.JsonSyntaxException;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.seniors.justlevelingfork.handler.HandlerCommonConfig;
import com.seniors.justlevelingfork.JustLevelingFork;
import com.seniors.justlevelingfork.registry.RegistrySkills;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.arguments.item.ItemParser;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.TagParser;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.flag.FeatureFlags;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TreasureHunterSkill {
    public static ItemStack drop() {
        int randomizer = (int) Math.floor(Math.random() * RegistrySkills.TREASURE_HUNTER.get().getValue()[0]);
        ItemStack stack = null;
        for (int i = 0; i < getItems().size(); i++) {
            List<BlockDrops> drops = getItems().get(i);
            if (randomizer == i) {
                int dropsRandom = (int) Math.floor(Math.random() * drops.size());
                for (int j = 0; j < drops.size(); j++) {
                    if (dropsRandom == j) {
                        stack = drops.get(j).createStack(dropsRandom);
                    }
                }
            }
        }
        return stack;
    }

    private static void applyLegacyNbt(ItemStack stack, CompoundTag tag) {
        if (tag.isEmpty()) {
            return;
        }

        CompoundTag customData = tag.copy();
        if (customData.contains("Damage", 99)) {
            stack.set(DataComponents.DAMAGE, Math.max(0, customData.getInt("Damage")));
            customData.remove("Damage");
        }

        if (!customData.isEmpty()) {
            CustomData.set(DataComponents.CUSTOM_DATA, stack, customData);
        }
    }

    public static ArrayList<List<BlockDrops>> getItems() {
        ArrayList<List<BlockDrops>> dropList = new ArrayList<>();
        List<? extends String> configList = HandlerCommonConfig.HANDLER.instance().treasureHunterItemList;

        for (String getValue : configList) {
            List<BlockDrops> getItems = new ArrayList<>();
            if (getValue.contains("List[") && getValue.charAt(getValue.length() - 1) == ']') {
                String newValue = getValue.split("List\\[")[1].substring(0, getValue.split("List\\[")[1].length() - 1);
                int itemsSize = 1;
                for (int i = 0; i < newValue.length(); ) {
                    if (newValue.charAt(i) == ';') itemsSize++;
                    i++;
                }

                Item[] arrayOfItem = new Item[itemsSize];
                for (int j = 0; j < itemsSize; j++) {
                    String resource = newValue.split(";")[j];
                    BlockDrops parsedDrop = parseDrop(resource, arrayOfItem);
                    arrayOfItem[j] = parsedDrop.getStack[j];
                    getItems.add(parsedDrop);
                }
                dropList.add(getItems);
                continue;
            }
            getItems.add(parseDrop(getValue, new Item[1]));
            dropList.add(getItems);
        }


        return dropList;
    }

    private static BlockDrops parseDrop(String itemConfig, Item[] items) {
        if (usesComponentSyntax(itemConfig)) {
            try {
                CommandBuildContext context = CommandBuildContext.simple(RegistryAccess.fromRegistryOfRegistries(BuiltInRegistries.REGISTRY), FeatureFlags.DEFAULT_FLAGS);
                ItemParser.ItemResult itemResult = new ItemParser(context).parse(new StringReader(itemConfig));
                items[items.length == 1 ? 0 : nextEmptyIndex(items)] = itemResult.item().value();
                return new BlockDrops(items, new CompoundTag(), itemResult.components());
            } catch (CommandSyntaxException e) {
                throw new JsonSyntaxException("Invalid item component entry: " + e);
            }
        }

        CompoundTag compound = new CompoundTag();
        String itemId = itemConfig;
        if (usesLegacyNbtSyntax(itemConfig)) {
            itemId = itemConfig.split("\\{")[0];
            String nbt = "{" + itemConfig.split("\\{", 2)[1];
            try {
                compound = TagParser.parseTag(nbt);
            } catch (CommandSyntaxException e) {
                throw new JsonSyntaxException("Invalid NBT Entry: " + e);
            }
        }

        String namespace = itemId.split(":")[0];
        String path = itemId.split(":")[1];
        items[items.length == 1 ? 0 : nextEmptyIndex(items)] = BuiltInRegistries.ITEM.get(ResourceLocation.fromNamespaceAndPath(namespace, path));
        return new BlockDrops(items, compound, DataComponentPatch.EMPTY);
    }

    private static boolean usesComponentSyntax(String itemConfig) {
        return itemConfig.contains("[") && itemConfig.endsWith("]");
    }

    private static boolean usesLegacyNbtSyntax(String itemConfig) {
        return itemConfig.contains("{") && itemConfig.endsWith("}");
    }

    private static int nextEmptyIndex(Item[] items) {
        for (int i = 0; i < items.length; i++) {
            if (items[i] == null) {
                return i;
            }
        }

        JustLevelingFork.getLOGGER().warn("Treasure Hunter drop list has more entries than expected; using the last slot.");
        return items.length - 1;
    }

    public static List<String> defaultItemList = Arrays.asList("minecraft:flint", "minecraft:clay_ball", "trashList[minecraft:feather;minecraft:bone_meal]", "lostToolList[minecraft:stick;minecraft:wooden_pickaxe{Damage:59};minecraft:wooden_shovel{Damage:59};minecraft:wooden_axe{Damage:59}]", "discList[minecraft:music_disc_13;minecraft:music_disc_cat;minecraft:music_disc_blocks;minecraft:music_disc_chirp;minecraft:music_disc_far;minecraft:music_disc_mall;minecraft:music_disc_mellohi;minecraft:music_disc_stal;minecraft:music_disc_strad;minecraft:music_disc_ward;minecraft:music_disc_11;minecraft:music_disc_wait]", "seedList[minecraft:beetroot_seeds;minecraft:wheat_seeds;minecraft:pumpkin_seeds;minecraft:melon_seeds;minecraft:brown_mushroom;minecraft:red_mushroom]", "mineralList[minecraft:raw_iron;minecraft:raw_gold;minecraft:raw_copper;minecraft:coal;minecraft:charcoal]");

    public static final class BlockDrops {
        private final Item[] getStack;
        private final CompoundTag getCompoundTag;
        private final DataComponentPatch components;

        public BlockDrops(Item[] getStack, CompoundTag getCompoundTag, DataComponentPatch components) {
            this.getStack = getStack;
            this.getCompoundTag = getCompoundTag;
            this.components = components;
        }

        public ItemStack createStack(int index) {
            ItemStack stack = this.getStack[index].getDefaultInstance();
            stack.applyComponents(this.components);
            applyLegacyNbt(stack, this.getCompoundTag);
            return stack;
        }
    }
}


