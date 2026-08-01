package com.seniors.justlevelingfork.neoforge;

import com.seniors.justlevelingfork.registry.RegistryPassives;
import com.seniors.justlevelingfork.registry.RegistrySkills;
import net.neoforged.neoforge.registries.NewRegistryEvent;

/**
 * Publishes the common module's authoritative registries to NeoForge.
 *
 * <p>This also makes them visible to KubeJS' standard
 * {@code StartupEvents.registry} event without putting any KubeJS classes in
 * common code or the Fabric artifact.</p>
 */
final class NeoForgeCustomRegistries {
    private NeoForgeCustomRegistries() {
    }

    static void register(NewRegistryEvent event) {
        event.register(RegistryPassives.PASSIVES);
        event.register(RegistrySkills.SKILLS);
    }
}
