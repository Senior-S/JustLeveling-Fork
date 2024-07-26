package com.seniors.justlevelingfork.registry;

import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class RegistryItems {
    public static final DeferredRegister<Item> REGISTER = DeferredRegister.create(ForgeRegistries.ITEMS, "justlevelingfork");

    public static final RegistryObject<Item> LEVELING_BOOK = REGISTER.register("leveling_book", () -> new Item(new Item.Properties()));

    public static void load(IEventBus eventBus) {
        REGISTER.register(eventBus);
    }
}


