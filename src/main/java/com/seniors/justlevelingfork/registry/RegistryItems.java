package com.seniors.justlevelingfork.registry;

import com.seniors.justlevelingfork.JustLevelingFork;
import com.seniors.justlevelingfork.common.item.SkillResetItem;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

public class RegistryItems {
    public static final FabricRegistryRef<Item> LEVELING_BOOK = register("leveling_book", new Item(new Item.Properties()));
    public static final FabricRegistryRef<Item> SKILL_RESET_CRYSTAL = register("skill_reset_crystal", new SkillResetItem(new Item.Properties().stacksTo(1)));

    public static void load() {
    }

    private static FabricRegistryRef<Item> register(String name, Item item) {
        return new FabricRegistryRef<>(Registry.register(BuiltInRegistries.ITEM, id(name), item));
    }

    public static ResourceLocation id(String name) {
        return ResourceLocation.fromNamespaceAndPath(JustLevelingFork.MOD_ID, name);
    }
}
