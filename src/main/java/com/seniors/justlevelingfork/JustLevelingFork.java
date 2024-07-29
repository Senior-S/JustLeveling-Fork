package com.seniors.justlevelingfork;

import com.seniors.justlevelingfork.handler.HandlerConfigClient;
import com.seniors.justlevelingfork.handler.HandlerConfigCommon;
import com.seniors.justlevelingfork.handler.HandlerCurios;
import com.seniors.justlevelingfork.network.ServerNetworking;
import com.seniors.justlevelingfork.registry.RegistryAptitudes;
import com.seniors.justlevelingfork.registry.RegistryArguments;
import com.seniors.justlevelingfork.registry.RegistryAttributes;
import com.seniors.justlevelingfork.registry.RegistryCommonEvents;
import com.seniors.justlevelingfork.registry.RegistryItems;
import com.seniors.justlevelingfork.registry.RegistryPassives;
import com.seniors.justlevelingfork.registry.RegistrySkills;
import com.seniors.justlevelingfork.registry.RegistrySounds;
import com.seniors.justlevelingfork.registry.RegistryTitles;
import com.mojang.logging.LogUtils;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityAttributeModificationEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(JustLevelingFork.MOD_ID)
public class JustLevelingFork {
    public static final String MOD_ID = "justlevelingfork";
    public static final String MOD_NAME = "just_leveling_fork";

    private static final Logger LOGGER = LogUtils.getLogger();

    public static Logger getLOGGER() {
        return LOGGER;
    }

    public JustLevelingFork() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        eventBus.addListener(this::attributeSetup);

        RegistryItems.load(eventBus);
        RegistryAptitudes.load(eventBus);
        RegistryPassives.load(eventBus);
        RegistrySkills.load(eventBus);
        RegistryTitles.load(eventBus);
        RegistryAttributes.load(eventBus);
        RegistrySounds.load(eventBus);
        RegistryArguments.load(eventBus);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, HandlerConfigCommon.SPEC, "just_leveling-common.toml");
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, HandlerConfigClient.SPEC, "just_leveling-client.toml");
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(new RegistryCommonEvents());
        if (HandlerCurios.isModLoaded())
            MinecraftForge.EVENT_BUS.register(new HandlerCurios());
        ServerNetworking.init();
    }

    private void attributeSetup(EntityAttributeModificationEvent event) {
        for (EntityType<? extends LivingEntity> type : event.getTypes()) {
            event.add(type, RegistryAttributes.CRITICAL_DAMAGE.get());
            event.add(type, RegistryAttributes.MAGIC_RESIST.get());
            event.add(type, RegistryAttributes.BREAK_SPEED.get());
            event.add(type, RegistryAttributes.PROJECTILE_DAMAGE.get());
            event.add(type, RegistryAttributes.BENEFICIAL_EFFECT.get());
        }
    }
}
