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

import java.util.Arrays;
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
    public List<String> convergenceItemList = Arrays.asList("minecraft:iron_sword#minecraft:iron_ingot", "minecraft:iron_pickaxe#minecraft:iron_ingot", "minecraft:iron_axe#minecraft:iron_ingot", "minecraft:iron_shovel#minecraft:iron_nugget", "minecraft:iron_hoe#minecraft:iron_ingot", "minecraft:golden_sword#minecraft:gold_ingot", "minecraft:golden_pickaxe#minecraft:gold_ingot", "minecraft:golden_axe#minecraft:gold_ingot", "minecraft:golden_shovel#minecraft:gold_ingot", "minecraft:golden_hoe#minecraft:gold_ingot", "minecraft:diamond_sword#minecraft:diamond", "minecraft:diamond_pickaxe#minecraft:diamond", "minecraft:diamond_axe#minecraft:diamond", "minecraft:chainmail_helmet#minecraft:iron_nugget", "minecraft:chainmail_chestplate#minecraft:iron_nugget", "minecraft:chainmail_leggings#minecraft:iron_nugget", "minecraft:chainmail_boots#minecraft:iron_nugget", "minecraft:iron_helmet#minecraft:iron_ingot", "minecraft:iron_chestplate#minecraft:iron_ingot", "minecraft:iron_leggings#minecraft:iron_ingot", "minecraft:iron_boots#minecraft:iron_ingot", "minecraft:golden_helmet#minecraft:gold_ingot", "minecraft:golden_chestplate#minecraft:gold_ingot", "minecraft:golden_leggings#minecraft:gold_ingot", "minecraft:golden_boots#minecraft:gold_ingot", "minecraft:diamond_helmet#minecraft:diamond", "minecraft:diamond_chestplate#minecraft:diamond", "minecraft:diamond_leggings#minecraft:diamond", "minecraft:diamond_boots#minecraft:diamond", "minecraft:netherite_upgrade_smithing_template#minecraft:diamond", "minecraft:sentry_armor_trim_smithing_template#minecraft:diamond", "minecraft:vex_armor_trim_smithing_template#minecraft:diamond", "minecraft:wild_armor_trim_smithing_template#minecraft:diamond", "minecraft:coast_armor_trim_smithing_template#minecraft:diamond", "minecraft:dune_armor_trim_smithing_template#minecraft:diamond", "minecraft:wayfinder_armor_trim_smithing_template#minecraft:diamond", "minecraft:raiser_armor_trim_smithing_template#minecraft:diamond", "minecraft:shaper_armor_trim_smithing_template#minecraft:diamond", "minecraft:host_armor_trim_smithing_template#minecraft:diamond", "minecraft:ward_armor_trim_smithing_template#minecraft:diamond", "minecraft:silence_armor_trim_smithing_template#minecraft:diamond", "minecraft:tide_armor_trim_smithing_template#minecraft:diamond", "minecraft:snout_armor_trim_smithing_template#minecraft:diamond", "minecraft:rib_armor_trim_smithing_template#minecraft:diamond", "minecraft:eye_armor_trim_smithing_template#minecraft:diamond", "minecraft:spire_armor_trim_smithing_template#minecraft:diamond");
}
