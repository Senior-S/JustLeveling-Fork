package com.seniors.justlevelingfork.registry;

import com.seniors.justlevelingfork.common.capability.AptitudeCapability;
import com.seniors.justlevelingfork.config.models.TitleModel;
import com.seniors.justlevelingfork.handler.HandlerConditions;
import com.seniors.justlevelingfork.handler.HandlerTitlesConfig;
import com.seniors.justlevelingfork.registry.title.Title;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;

import java.util.stream.Collectors;

public class RegistryTitles {
    public static final ResourceKey<Registry<Title>> TITLES_KEY = ResourceKey.createRegistryKey(RegistryItems.id("titles"));
    public static final Registry<Title> TITLES = FabricRegistryBuilder.createSimple(TITLES_KEY).buildAndRegister();
    public static final FabricRegistryView<Title> TITLES_REGISTRY = new FabricRegistryView<>(TITLES);

    public static final FabricRegistryRef<Title> TITLELESS = register("titleless", new Title(RegistryItems.id("titleless"), true, true));
    public static final FabricRegistryRef<Title> ADMIN = register("administrator", new Title(RegistryItems.id("administrator"), false, true));

    public static void load() {
        HandlerTitlesConfig.HANDLER.instance().titleList.forEach(TitleModel::registry);
        HandlerConditions.registerDefaults();
    }

    public static FabricRegistryRef<Title> register(String name, Title title) {
        return new FabricRegistryRef<>(Registry.register(TITLES, RegistryItems.id(name), title));
    }

    public static Title getTitle(String titleName) {
        return TITLES_REGISTRY.getValues().stream().collect(Collectors.toMap(Title::getName, Title::get)).get(titleName);
    }

    public static void syncTitles(ServerPlayer serverPlayer) {
        serverPlayerTitles(serverPlayer);
        AptitudeCapability capability = AptitudeCapability.get(serverPlayer);
        if (capability == null) return;

        Title title = getTitle(capability.getPlayerTitle());
        if (title != null) {
            serverPlayer.setCustomName(Component.translatable(title.getKey()));
        }
    }

    public static void serverPlayerTitles(ServerPlayer serverPlayer) {
        if (serverPlayer.isDeadOrDying()) return;
        AptitudeCapability capability = AptitudeCapability.get(serverPlayer);
        if (capability == null) return;

        for (TitleModel titleModel : HandlerTitlesConfig.HANDLER.instance().titleList) {
            titleModel.getTitle().setRequirement(serverPlayer, titleModel.CheckRequirements(serverPlayer));
        }
        ADMIN.get().setRequirement(serverPlayer, serverPlayer.hasPermissions(2));
    }
}
