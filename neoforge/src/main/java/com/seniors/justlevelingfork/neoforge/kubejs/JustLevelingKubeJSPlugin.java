package com.seniors.justlevelingfork.neoforge.kubejs;

import com.seniors.justlevelingfork.client.core.ValueType;
import com.seniors.justlevelingfork.registry.aptitude.Aptitude;
import dev.latvian.mods.kubejs.plugin.KubeJSPlugin;
import dev.latvian.mods.kubejs.event.EventGroupRegistry;
import dev.latvian.mods.kubejs.script.BindingRegistry;

/**
 * KubeJS 7 bindings used by the documented JustLeveling scripts.
 */
public final class JustLevelingKubeJSPlugin implements KubeJSPlugin {
    @Override
    public void registerEvents(EventGroupRegistry events) {
        events.register(JustLevelingKubeJSEvents.GROUP);
    }

    @Override
    public void registerBindings(BindingRegistry bindings) {
        bindings.add("ValueType", ValueType.class);
        bindings.add("Aptitude", Aptitude.class);
    }
}
