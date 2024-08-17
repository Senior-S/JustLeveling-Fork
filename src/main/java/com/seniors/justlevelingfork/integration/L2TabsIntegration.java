package com.seniors.justlevelingfork.integration;

import net.minecraftforge.fml.ModList;

public class L2TabsIntegration {

    public static boolean isModLoaded() {
        return ModList.get().isLoaded("l2tabs");
    }
}
