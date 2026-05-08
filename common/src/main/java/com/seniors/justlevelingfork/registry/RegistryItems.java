package com.seniors.justlevelingfork.registry;

import com.seniors.justlevelingfork.JustLevelingFork;
import com.seniors.justlevelingfork.common.item.SkillResetItem;
import dev.architectury.registry.registries.DeferredRegister;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

import java.util.function.Supplier;

public class RegistryItems {
    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(JustLevelingFork.MOD_ID, Registries.ITEM);

    public static final FabricRegistryRef<Item> LEVELING_BOOK = register("leveling_book", () -> new Item(new Item.Properties()));
    public static final FabricRegistryRef<Item> SKILL_RESET_CRYSTAL = register("skill_reset_crystal", () -> new SkillResetItem(new Item.Properties().stacksTo(1)));

    public static void load() {
        ITEMS.register();
    }

    private static FabricRegistryRef<Item> register(String name, Supplier<Item> item) {
        return new FabricRegistryRef<>(ITEMS.register(name, item));
    }

    public static ResourceLocation id(String name) {
        return ResourceLocation.fromNamespaceAndPath(JustLevelingFork.MOD_ID, name);
    }
}
