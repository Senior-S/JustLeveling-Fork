package com.seniors.justlevelingfork.registry;

import com.seniors.justlevelingfork.common.capability.AptitudeCapability;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;

public class RegistryCapabilities {
    public static Capability<AptitudeCapability> APTITUDE = CapabilityManager.get(new CapabilityToken<AptitudeCapability>() {

    });
}


