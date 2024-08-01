package com.seniors.justlevelingfork.config;

import dev.isxander.yacl3.api.Option;
import dev.isxander.yacl3.api.controller.ControllerBuilder;
import dev.isxander.yacl3.api.controller.StringControllerBuilder;
import dev.isxander.yacl3.config.v2.api.ConfigField;
import dev.isxander.yacl3.config.v2.api.autogen.ListGroup;
import dev.isxander.yacl3.config.v2.api.autogen.OptionAccess;

import java.util.List;

public class StringListGroup implements ListGroup.ValueFactory<String>, ListGroup.ControllerFactory<String> {

    @Override
    public String provideNewValue() {
        return "";
    }

    @Override
    public ControllerBuilder<String> createController(ListGroup annotation, ConfigField<List<String>> field, OptionAccess storage, Option<String> option) {
        return StringControllerBuilder.create(option);
    }
}
