package com.evandev.triggers;

import java.util.function.Consumer;

public class Triggers {
    public static final EventBus EVENTS = new EventBus();

    public static class EventBus {
        public <T> void addListener(Consumer<T> listener) {
        }
    }
}
