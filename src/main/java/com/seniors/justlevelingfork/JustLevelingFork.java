package com.seniors.justlevelingfork;

import com.mojang.logging.LogUtils;
import com.seniors.justlevelingfork.config.Configuration;
import com.seniors.justlevelingfork.config.models.EAptitude;
import com.seniors.justlevelingfork.config.models.LockItem;
import com.seniors.justlevelingfork.handler.HandlerCommonConfig;
import com.seniors.justlevelingfork.handler.HandlerConfigCommon;
import com.seniors.justlevelingfork.handler.HandlerCurios;
import com.seniors.justlevelingfork.handler.HandlerLockItemsConfig;
import com.seniors.justlevelingfork.integration.CrayfishGunModIntegration;
import com.seniors.justlevelingfork.integration.TacZIntegration;
import com.seniors.justlevelingfork.network.ServerNetworking;
import com.seniors.justlevelingfork.registry.*;
import com.seniors.justlevelingfork.registry.aptitude.Aptitude;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityAttributeModificationEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.moddiscovery.ModInfo;
import net.minecraftforge.forgespi.language.IModInfo;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.MutablePair;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(JustLevelingFork.MOD_ID)
public class JustLevelingFork {
    public static final String MOD_ID = "justlevelingfork";
    public static final String MOD_NAME = "just_leveling_fork";
    private static final Logger LOGGER = LogUtils.getLogger();

    public static Logger getLOGGER() {
        return LOGGER;
    }

    public static MutablePair<Boolean, String> UpdatesAvailable = new MutablePair<>(false, "");

    public JustLevelingFork() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        eventBus.addListener(this::attributeSetup);

        Configuration.Init();

        RegistryItems.load(eventBus);
        RegistryAttributes.load(eventBus);
        RegistryAptitudes.load(eventBus);
        RegistryPassives.load(eventBus);
        RegistrySkills.load(eventBus);
        RegistryTitles.load(eventBus);
        RegistrySounds.load(eventBus);

        MinecraftForge.EVENT_BUS.register(new RegistryCommonEvents());
        if (HandlerCurios.isModLoaded())
            MinecraftForge.EVENT_BUS.register(new HandlerCurios());
        if (TacZIntegration.isModLoaded())
            MinecraftForge.EVENT_BUS.register(new TacZIntegration());
        if (CrayfishGunModIntegration.isModLoaded())
            MinecraftForge.EVENT_BUS.register(new CrayfishGunModIntegration());

        ServerNetworking.init();

        // Check for new updates
        if (HandlerCommonConfig.HANDLER.instance().checkForUpdates) {
            try {
                String version = getLatestVersion();

                Optional<IModInfo> optionalModInfo = ModList.get().getMods().stream().filter(c -> Objects.equals(c.getModId(), MOD_ID)).findFirst();

                // Is this somehow isn't present then some really strange shit happen
                if (optionalModInfo.isPresent()) {
                    ModInfo modInfo = (ModInfo) optionalModInfo.get();
                    if (!Objects.equals(modInfo.getVersion().toString(), version)) {
                        UpdatesAvailable.left = true;
                        UpdatesAvailable.right = version;
                        LOGGER.info(">> NEW VERSION AVAILABLE: {}", version);
                    }
                }
            } catch (Exception e) {
                LOGGER.warn(">> Error checking for updates!");
            }
        }
    }

    @NotNull
    private static String getLatestVersion() throws IOException {
        URL u = new URL("https://raw.githubusercontent.com/Senior-S/JustLeveling-Fork/master/VERSION");
        URLConnection conn = u.openConnection();
        BufferedReader in = new BufferedReader(
                new InputStreamReader(
                        conn.getInputStream()));
        StringBuilder buffer = new StringBuilder();
        String inputLine;
        while ((inputLine = in.readLine()) != null)
            buffer.append(inputLine);
        in.close();
        return buffer.toString();
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

    public static void migrateOldConfig() {
        List<? extends String> configList = HandlerConfigCommon.lockItemList.get();

        List<LockItem> items = new ArrayList<>();
        for (String value : configList) {
            String[] values = value.split("#");
            if (values.length != 2) {
                continue;
            }
            LockItem lockItem = new LockItem(values[0]);

            String getResource = values[0];
            if (getResource.split(":").length != 2) {
                continue;
            }
            String aptitudeValue = values[1];
            String getAptitude = aptitudeValue.contains("<droppable>") ? aptitudeValue.split("<droppable>")[0] : aptitudeValue;
            String[] aptitudeList = getAptitude.split(";");

            List<LockItem.Aptitude> aptitudes = new ArrayList<>();

            for (String getMultipleSkill : aptitudeList) {
                if (getMultipleSkill.isEmpty()) {
                    continue;
                }

                if (getMultipleSkill.contains("#") || getMultipleSkill.contains(",")) {
                    continue;
                }

                String[] aptitudeValues = getMultipleSkill.split(":");

                String aptitudePath = aptitudeValues[0];
                if (aptitudePath.equals("defence")) {
                    aptitudePath = "defense";
                }
                Aptitude aptitudeName = RegistryAptitudes.getAptitude(aptitudePath);
                if (aptitudeName == null) {
                    continue;
                }

                LockItem.Aptitude aptitude = new LockItem.Aptitude();
                aptitude.Aptitude = EAptitude.valueOf(StringUtils.capitalize(aptitudePath));
                aptitude.Level = Integer.parseInt(aptitudeValues[1]);

                aptitudes.add(aptitude);
            }
            if (aptitudes.isEmpty()) {
                continue;
            }

            lockItem.Aptitudes = aptitudes;
            items.add(lockItem);
        }

        items.forEach((item) -> {
            if (HandlerLockItemsConfig.HANDLER.instance().lockItemList.stream().noneMatch((lockItem -> lockItem.Item.equalsIgnoreCase(item.Item)))) {
                HandlerLockItemsConfig.HANDLER.instance().lockItemList.add(item);
            }
        });

        HandlerLockItemsConfig.HANDLER.save();
        HandlerCommonConfig.HANDLER.instance().usingNewConfig = true;
        HandlerCommonConfig.HANDLER.save();
    }
}
