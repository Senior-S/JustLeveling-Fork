package com.seniors.justlevelingfork.registry;

import java.util.function.Supplier;

public final class FabricRegistryRef<T> implements Supplier<T> {
    private final Supplier<T> value;

    public FabricRegistryRef(T value) {
        this(() -> value);
    }

    public FabricRegistryRef(Supplier<T> value) {
        this.value = value;
    }

    @Override
    public T get() {
        return value.get();
    }
}
