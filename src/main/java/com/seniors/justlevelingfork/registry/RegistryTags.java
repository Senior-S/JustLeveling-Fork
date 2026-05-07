package com.seniors.justlevelingfork.registry;

import com.seniors.justlevelingfork.JustLevelingFork;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class RegistryTags {
    public static class Items {
        public static TagKey<Item> tag(String name) {
            return TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(JustLevelingFork.MOD_ID, name));
        }
    }

    public static class Blocks {
        public static final TagKey<Block> OBSIDIAN = tag("obsidian");
        public static final TagKey<Block> DIRT = tag("dirt");

        public static TagKey<Block> tag(String name) {
            return TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(JustLevelingFork.MOD_ID, name));
        }
    }
}


