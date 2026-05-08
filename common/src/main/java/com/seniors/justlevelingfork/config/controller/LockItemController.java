package com.seniors.justlevelingfork.config.controller;

import com.seniors.justlevelingfork.config.models.LockItem;
import dev.isxander.yacl3.api.Option;
import dev.isxander.yacl3.gui.controllers.dropdown.AbstractDropdownController;

public class LockItemController extends AbstractDropdownController<LockItem> {

    public LockItemController(Option<LockItem> option){
        super(option);
    }

    @Override
    public String getString() {
        return option.pendingValue().toString();
    }

    @Override
    public void setFromString(String value) {
        option.requestSet(LockItem.getLockItemFromString(value, option.pendingValue()));
    }
}
