package com.seniors.justlevelingfork.registry;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;

import java.util.Collection;

public final class FabricRegistryView<T> {
    private final Registry<T> registry;

    public FabricRegistryView(Registry<T> registry) {
        this.registry = registry;
    }

    public Collection<T> getValues() {
        return registry.stream().toList();
    }

    public T getValue(ResourceLocation id) {
        return registry.get(id);
    }

    public FabricRegistryView<T> get() {
        return this;
    }

    public boolean containsKey(ResourceLocation id) {
        return registry.containsKey(id);
    }

    public ResourceLocation getKey(T value) {
        return registry.getKey(value);
    }
}
