package com.seniors.justlevelingfork.kubejs;

import com.seniors.justlevelingfork.client.core.ValueType;
import dev.latvian.mods.kubejs.KubeJSPlugin;
import dev.latvian.mods.kubejs.script.BindingsEvent;

public class Plugin extends KubeJSPlugin {

    @Override
    public void registerBindings(BindingsEvent event) {
        super.registerBindings(event);
        event.add("ValueType", ValueType.class);
    }
}
