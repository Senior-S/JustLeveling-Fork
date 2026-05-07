package com.seniors.justlevelingfork.handler;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.seniors.justlevelingfork.client.core.SortPassives;
import com.seniors.justlevelingfork.client.core.SortSkills;
import com.seniors.justlevelingfork.config.Configuration;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;

public class HandlerConfigClient {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path CONFIG_PATH = Configuration.getAbsoluteDirectory().resolve("justleveling-fork.client.json");
    private static final ClientConfig DATA = new ClientConfig();
    private static boolean loaded = false;

    public static final ClientValue<Boolean> showCriticalRollSkillOverlay = new ClientValue<>(
            () -> DATA.showCriticalRollSkillOverlay,
            value -> DATA.showCriticalRollSkillOverlay = value
    );
    public static final ClientValue<Boolean> showLuckyDropSkillOverlay = new ClientValue<>(
            () -> DATA.showLuckyDropSkillOverlay,
            value -> DATA.showLuckyDropSkillOverlay = value
    );
    public static final ClientValue<Boolean> showSkillModName = new ClientValue<>(
            () -> DATA.showSkillModName,
            value -> DATA.showSkillModName = value
    );
    public static final ClientValue<Boolean> showTitleModName = new ClientValue<>(
            () -> DATA.showTitleModName,
            value -> DATA.showTitleModName = value
    );
    public static final ClientValue<SortPassives> sortPassive = new ClientValue<>(
            () -> DATA.sortPassive,
            value -> DATA.sortPassive = value
    );
    public static final ClientValue<SortSkills> sortSkill = new ClientValue<>(
            () -> DATA.sortSkill,
            value -> DATA.sortSkill = value
    );

    public static void load() {
        if (loaded) {
            return;
        }

        try {
            Files.createDirectories(CONFIG_PATH.getParent());
            if (Files.exists(CONFIG_PATH)) {
                try (Reader reader = Files.newBufferedReader(CONFIG_PATH)) {
                    ClientConfig parsed = GSON.fromJson(reader, ClientConfig.class);
                    if (parsed != null) {
                        DATA.copyFrom(parsed);
                    }
                }
            }
            save();
            loaded = true;
        } catch (IOException exception) {
            throw new IllegalStateException("Failed to load client config from " + CONFIG_PATH, exception);
        }
    }

    public static void save() {
        try {
            Files.createDirectories(CONFIG_PATH.getParent());
            try (Writer writer = Files.newBufferedWriter(CONFIG_PATH)) {
                GSON.toJson(DATA, writer);
            }
        } catch (IOException exception) {
            throw new IllegalStateException("Failed to save client config to " + CONFIG_PATH, exception);
        }
    }

    public static class ClientValue<T> {
        private final ValueGetter<T> getter;
        private final ValueSetter<T> setter;

        private ClientValue(ValueGetter<T> getter, ValueSetter<T> setter) {
            this.getter = getter;
            this.setter = setter;
        }

        public T get() {
            return getter.get();
        }

        public void set(T value) {
            setter.set(value);
            save();
        }
    }

    private static class ClientConfig {
        private boolean showCriticalRollSkillOverlay = true;
        private boolean showLuckyDropSkillOverlay = true;
        private boolean showSkillModName = false;
        private boolean showTitleModName = false;
        private SortPassives sortPassive = SortPassives.ByName;
        private SortSkills sortSkill = SortSkills.ByLevel;

        private void copyFrom(ClientConfig other) {
            showCriticalRollSkillOverlay = other.showCriticalRollSkillOverlay;
            showLuckyDropSkillOverlay = other.showLuckyDropSkillOverlay;
            showSkillModName = other.showSkillModName;
            showTitleModName = other.showTitleModName;
            sortPassive = other.sortPassive == null ? SortPassives.ByName : other.sortPassive;
            sortSkill = other.sortSkill == null ? SortSkills.ByLevel : other.sortSkill;
        }
    }

    private interface ValueGetter<T> {
        T get();
    }

    private interface ValueSetter<T> {
        void set(T value);
    }
}
