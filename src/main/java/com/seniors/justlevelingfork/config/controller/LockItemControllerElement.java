package com.seniors.justlevelingfork.config.controller;

import com.seniors.justlevelingfork.config.LockItem;
import dev.isxander.yacl3.api.utils.Dimension;
import dev.isxander.yacl3.gui.YACLScreen;
import dev.isxander.yacl3.gui.controllers.dropdown.AbstractDropdownControllerElement;

import java.util.List;

public class LockItemControllerElement extends AbstractDropdownControllerElement<LockItem, String> {
    private final LockItemController lockItemController;
    protected LockItem currentItem = null;

    public LockItemControllerElement(LockItemController control, YACLScreen screen, Dimension<Integer> dim) {
        super(control, screen, dim);
        this.lockItemController = control;
    }

    @Override
    public List<String> computeMatchingValues() {
        return lockItemController.getAllowedValues(inputField);
    }

    @Override
    public String getString(String identifier) {
        return identifier;
    }
}
