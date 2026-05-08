package com.seniors.justlevelingfork.config;

import dev.isxander.yacl3.api.Option;
import dev.isxander.yacl3.api.controller.ControllerBuilder;
import dev.isxander.yacl3.api.controller.ItemControllerBuilder;
import dev.isxander.yacl3.config.v2.api.ConfigField;
import dev.isxander.yacl3.config.v2.api.autogen.ListGroup;
import dev.isxander.yacl3.config.v2.api.autogen.OptionAccess;
import net.minecraft.world.item.Item;

import java.util.List;

public class ItemListGroup implements ListGroup.ValueFactory<Item>, ListGroup.ControllerFactory<Item> {

    @Override
    public ControllerBuilder<Item> createController(ListGroup annotation, ConfigField<List<Item>> field, OptionAccess storage, Option<Item> option) {
        return ItemControllerBuilder.create(option);
    }

    @Override
    public Item provideNewValue() {
        return null;
    }
}
