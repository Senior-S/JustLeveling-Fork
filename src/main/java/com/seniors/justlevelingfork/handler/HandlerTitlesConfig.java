package com.seniors.justlevelingfork.handler;

import com.google.gson.GsonBuilder;
import com.seniors.justlevelingfork.JustLevelingFork;
import com.seniors.justlevelingfork.config.Configuration;
import com.seniors.justlevelingfork.config.models.TitleModel;
import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;
import net.minecraft.resources.ResourceLocation;

import java.util.List;

public class HandlerTitlesConfig {

    public static ConfigClassHandler<HandlerTitlesConfig> HANDLER = ConfigClassHandler.createBuilder(HandlerTitlesConfig.class)
            .id(new ResourceLocation(JustLevelingFork.MOD_ID, "config"))
            .serializer(config -> GsonConfigSerializerBuilder.create(config)
                    .setPath(Configuration.getAbsoluteDirectory().resolve("justleveling-fork.titles.json5"))
                    .appendGsonBuilder(GsonBuilder::setPrettyPrinting)
                    .setJson5(true)
                    .build())
            .build();

    @SerialEntry(comment = "Titles list")
    // Every title defined here are the default titles from the original mod
    public List<TitleModel> titleList = List.of(new TitleModel(),
            new TitleModel("fighter", List.of("aptitude/Strength/greater_or_equal/16"), false),
            new TitleModel("fighter_great", List.of("aptitude/Strength/greater_or_equal/32"), false),
            new TitleModel("warrior", List.of("aptitude/Constitution/greater_or_equal/16"), false),
            new TitleModel("warrior_great", List.of("aptitude/Constitution/greater_or_equal/32"), false),
            new TitleModel("ranger", List.of("aptitude/Dexterity/greater_or_equal/16"), false),
            new TitleModel("ranger_great", List.of("aptitude/Dexterity/greater_or_equal/32"), false),
            new TitleModel("tank", List.of("aptitude/Strength/greater_or_equal/16"), false),
            new TitleModel("tank_great", List.of("aptitude/Defense/greater_or_equal/32"), false),
            new TitleModel("alchemist", List.of("aptitude/Intelligence/greater_or_equal/16"), false),
            new TitleModel("alchemist_great", List.of("aptitude/Intelligence/greater_or_equal/32"), false),
            new TitleModel("miner", List.of("aptitude/Building/greater_or_equal/16"), false),
            new TitleModel("miner_great", List.of("aptitude/Building/greater_or_equal/32"), false),
            new TitleModel("magician", List.of("aptitude/Magic/greater_or_equal/16"), false),
            new TitleModel("magician_great", List.of("aptitude/Magic/greater_or_equal/32"), false),
            new TitleModel("lucky_one", List.of("aptitude/Luck/greater_or_equal/16"), false),
            new TitleModel("lucky_one_great", List.of("aptitude/Luck/greater_or_equal/32"), false),
            new TitleModel("dragon_slayer", List.of("EntityKilled/ender_dragon/greater_or_equal/10"), false),
            new TitleModel("player_killer", List.of("EntityKilled/player/greater_or_equal/100"), false),
            new TitleModel("mob_killer", List.of("stat/mob_kills/greater_or_equal/100"), false),
            new TitleModel("mob_killer_great", List.of("stat/mob_kills/greater_or_equal/1000"), false),
            new TitleModel("mob_killer_master", List.of("stat/mob_kills/greater_or_equal/10000"), false),
            new TitleModel("hero", List.of("stat/raid_win/greater_or_equal/100"), false),
            new TitleModel("villain", List.of("EntityKilled/villager/greater_or_equal/100"), false),
            new TitleModel("fisherman", List.of("stat/fish_caught/greater_or_equal/100"), false),
            new TitleModel("fisherman_great", List.of("stat/fish_caught/greater_or_equal/1000"), false),
            new TitleModel("fisherman_master", List.of("stat/fish_caught/greater_or_equal/10000"), false),
            new TitleModel("enchanter", List.of("stat/enchant_item/greater_or_equal/10"), false),
            new TitleModel("enchanter_great", List.of("stat/enchant_item/greater_or_equal/100"), false),
            new TitleModel("enchanter_master", List.of("stat/enchant_item/greater_or_equal/1000"), false),
            new TitleModel("survivor", List.of("stat/time_since_death/greater_or_equal/100"), false),
            new TitleModel("businessman", List.of("stat/traded_with_villager/greater_or_equal/100"), false),
            new TitleModel("driver_boat", List.of("stat/boat_one_cm/greater_or_equal/1000000"), false),
            new TitleModel("driver_cart", List.of("stat/minecart_one_cm/greater_or_equal/1000000"), false),
            new TitleModel("rider_horse", List.of("stat/horse_one_cm/greater_or_equal/1000000"), false),
            new TitleModel("rider_pig", List.of("stat/pig_one_cm/greater_or_equal/1000000"), false),
            new TitleModel("rider_strider", List.of("stat/strider_one_cm/greater_or_equal/1000000"), false),
            new TitleModel("traveler_nether", List.of("special/dimension/equals/minecraft:the_nether"), false),
            new TitleModel("traveler_end", List.of("special/dimension/equals/minecraft:the_end"), false)
    );
}
