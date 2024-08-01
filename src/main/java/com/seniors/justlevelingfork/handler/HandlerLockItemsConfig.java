package com.seniors.justlevelingfork.handler;

import com.google.gson.GsonBuilder;
import com.seniors.justlevelingfork.JustLevelingFork;
import com.seniors.justlevelingfork.config.LockItem;
import com.seniors.justlevelingfork.config.LockItemListGroup;
import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.autogen.ListGroup;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.loading.FMLPaths;

import java.util.List;

public class HandlerLockItemsConfig {

    public static ConfigClassHandler<HandlerLockItemsConfig> HANDLER = ConfigClassHandler.createBuilder(HandlerLockItemsConfig.class)
            .id(new ResourceLocation(JustLevelingFork.MOD_ID, "config"))
            .serializer(config -> GsonConfigSerializerBuilder.create(config)
                    .setPath(FMLPaths.CONFIGDIR.get().resolve("justleveling-fork.lockItems.json5"))
                    .appendGsonBuilder(GsonBuilder::setPrettyPrinting)
                    .setJson5(true)
                    .build())
            .build();

    @SerialEntry(comment = "Lock item list")
    @ListGroup(controllerFactory = LockItemListGroup.class, valueFactory = LockItemListGroup.class, addEntriesToBottom = true)
    public List<LockItem> lockItemList = List.of(new LockItem("minecraft:diamond"));
}
