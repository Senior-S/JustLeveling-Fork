package com.seniors.justlevelingfork.config.controller;

import com.seniors.justlevelingfork.config.LockItem;
import dev.isxander.yacl3.api.Option;
import dev.isxander.yacl3.api.controller.ControllerBuilder;

public interface LockItemControllerBuilder extends ControllerBuilder<LockItem> {
    static LockItemControllerBuilder create(Option<LockItem> option){
        return new LockItemControllerBuilderImpl(option);
    }
}
