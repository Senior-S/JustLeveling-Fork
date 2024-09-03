package com.seniors.justlevelingfork.handler;

import com.google.gson.GsonBuilder;
import com.seniors.justlevelingfork.JustLevelingFork;
import com.seniors.justlevelingfork.config.Configuration;
import com.seniors.justlevelingfork.config.StringListGroup;
import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.autogen.ListGroup;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;
import net.minecraft.resources.ResourceLocation;

import java.util.List;

public class HandlerConvergenceItemsConfig {
    public static ConfigClassHandler<HandlerConvergenceItemsConfig> HANDLER = ConfigClassHandler.createBuilder(HandlerConvergenceItemsConfig.class)
            .id(new ResourceLocation(JustLevelingFork.MOD_ID, "config"))
            .serializer(config -> GsonConfigSerializerBuilder.create(config)
                    .setPath(Configuration.getAbsoluteDirectory().resolve("justleveling-fork.convergence-items.json5"))
                    .appendGsonBuilder(GsonBuilder::setPrettyPrinting)
                    .setJson5(true)
                    .build())
            .build();

    @SerialEntry(comment = "Convergence skill convergence item list")
    @ListGroup(controllerFactory = StringListGroup.class, valueFactory = StringListGroup.class)
    public List<String> convergenceItemList = List.of();
}
