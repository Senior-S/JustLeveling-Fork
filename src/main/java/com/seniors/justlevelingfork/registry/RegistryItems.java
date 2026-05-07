package com.seniors.justlevelingfork.registry;

import com.seniors.justlevelingfork.JustLevelingFork;
import com.seniors.justlevelingfork.common.item.SkillResetItem;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class RegistryItems {
    public static final DeferredRegister<Item> REGISTER = DeferredRegister.create(ForgeRegistries.ITEMS, JustLevelingFork.MOD_ID);

    public static final RegistryObject<Item> LEVELING_BOOK = REGISTER.register("leveling_book", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> SKILL_RESET_CRYSTAL = REGISTER.register("skill_reset_crystal", () -> new SkillResetItem(new Item.Properties().stacksTo(1)));

    public static void load(IEventBus eventBus) {
        REGISTER.register(eventBus);
    }

    public static ResourceLocation id(String path) {
        return new ResourceLocation(JustLevelingFork.MOD_ID, path);
    }
}


