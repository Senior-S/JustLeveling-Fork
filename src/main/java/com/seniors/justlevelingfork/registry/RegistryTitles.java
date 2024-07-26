package com.seniors.justlevelingfork.registry;

import com.seniors.justlevelingfork.common.capability.AptitudeCapability;
import com.seniors.justlevelingfork.registry.title.Title;

import java.util.function.Supplier;
import java.util.stream.Collectors;

import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;
import net.minecraftforge.registries.RegistryObject;

public class RegistryTitles {
    public static final ResourceKey<Registry<Title>> TITLES_KEY = ResourceKey.createRegistryKey(new ResourceLocation("justlevelingfork", "titles"));
    public static final DeferredRegister<Title> TITLES = DeferredRegister.create(TITLES_KEY, "justlevelingfork");
    public static final Supplier<IForgeRegistry<Title>> TITLES_REGISTRY = TITLES.makeRegistry(() -> (new RegistryBuilder()).disableSaving());

    public static final RegistryObject<Title> TITLELESS = TITLES.register("titleless", () -> register("titleless", true));
    public static final RegistryObject<Title> ROCKIE = TITLES.register("rockie", () -> register("rockie", true));
    public static final RegistryObject<Title> FIGHTER = TITLES.register("fighter", () -> register("fighter", false));
    public static final RegistryObject<Title> GREAT_FIGHTER = TITLES.register("fighter_great", () -> register("fighter_great", false));
    public static final RegistryObject<Title> WARRIOR = TITLES.register("warrior", () -> register("warrior", false));
    public static final RegistryObject<Title> GREAT_WARRIOR = TITLES.register("warrior_great", () -> register("warrior_great", false));
    public static final RegistryObject<Title> RANGER = TITLES.register("ranger", () -> register("ranger", false));
    public static final RegistryObject<Title> GREAT_RANGER = TITLES.register("ranger_great", () -> register("ranger_great", false));
    public static final RegistryObject<Title> TANK = TITLES.register("tank", () -> register("tank", false));
    public static final RegistryObject<Title> GREAT_TANK = TITLES.register("tank_great", () -> register("tank_great", false));
    public static final RegistryObject<Title> ALCHEMIST = TITLES.register("alchemist", () -> register("alchemist", false));
    public static final RegistryObject<Title> GREAT_ALCHEMIST = TITLES.register("alchemist_great", () -> register("alchemist_great", false));
    public static final RegistryObject<Title> MINER = TITLES.register("miner", () -> register("miner", false));
    public static final RegistryObject<Title> GREAT_MINER = TITLES.register("miner_great", () -> register("miner_great", false));
    public static final RegistryObject<Title> MAGICIAN = TITLES.register("magician", () -> register("magician", false));
    public static final RegistryObject<Title> GREAT_MAGICIAN = TITLES.register("magician_great", () -> register("magician_great", false));
    public static final RegistryObject<Title> LUCKY_ONE = TITLES.register("lucky_one", () -> register("lucky_one", false));
    public static final RegistryObject<Title> GREAT_LUCKY_ONE = TITLES.register("lucky_one_great", () -> register("lucky_one_great", false));
    public static final RegistryObject<Title> DRAGON_SLAYER = TITLES.register("dragon_slayer", () -> register("dragon_slayer", false));
    public static final RegistryObject<Title> PLAYER_KILLER = TITLES.register("player_killer", () -> register("player_killer", false));
    public static final RegistryObject<Title> MOB_KILLER = TITLES.register("mob_killer", () -> register("mob_killer", false));
    public static final RegistryObject<Title> GREAT_MOB_KILLER = TITLES.register("mob_killer_great", () -> register("mob_killer_great", false));
    public static final RegistryObject<Title> MASTER_MOB_KILLER = TITLES.register("mob_killer_master", () -> register("mob_killer_master", false));
    public static final RegistryObject<Title> HERO = TITLES.register("hero", () -> register("hero", false));
    public static final RegistryObject<Title> VILLAIN = TITLES.register("villain", () -> register("villain", false));
    public static final RegistryObject<Title> FISHERMAN = TITLES.register("fisherman", () -> register("fisherman", false));
    public static final RegistryObject<Title> GREAT_FISHERMAN = TITLES.register("fisherman_great", () -> register("fisherman_great", false));
    public static final RegistryObject<Title> MASTER_FISHERMAN = TITLES.register("fisherman_master", () -> register("fisherman_master", false));
    public static final RegistryObject<Title> ENCHANTER = TITLES.register("enchanter", () -> register("enchanter", false));
    public static final RegistryObject<Title> GREAT_ENCHANTER = TITLES.register("enchanter_great", () -> register("enchanter_great", false));
    public static final RegistryObject<Title> MASTER_ENCHANTER = TITLES.register("enchanter_master", () -> register("enchanter_master", false));
    public static final RegistryObject<Title> SURVIVOR = TITLES.register("survivor", () -> register("survivor", false));
    public static final RegistryObject<Title> BUSINESSMAN = TITLES.register("businessman", () -> register("businessman", false));
    public static final RegistryObject<Title> BOAT_DRIVER = TITLES.register("driver_boat", () -> register("driver_boat", false));
    public static final RegistryObject<Title> CART_DRIVER = TITLES.register("driver_cart", () -> register("driver_cart", false));
    public static final RegistryObject<Title> HORSE_RIDER = TITLES.register("rider_horse", () -> register("rider_horse", false));
    public static final RegistryObject<Title> PIG_RIDER = TITLES.register("rider_pig", () -> register("rider_pig", false));
    public static final RegistryObject<Title> STRIDER_RIDER = TITLES.register("rider_strider", () -> register("rider_strider", false));
    public static final RegistryObject<Title> NETHER_TRAVELER = TITLES.register("traveler_nether", () -> register("traveler_nether", false));
    public static final RegistryObject<Title> END_TRAVELER = TITLES.register("traveler_end", () -> register("traveler_end", false));
    public static final RegistryObject<Title> ADMIN = TITLES.register("administrator", () -> register("administrator", false));

    public static void load(IEventBus eventBus) {
        TITLES.register(eventBus);
    }

    private static Title register(String name, boolean requirement) {
        ResourceLocation key = new ResourceLocation("justlevelingfork", name);
        return new Title(key, requirement);
    }

    public static Title getTitle(String titleName) {
        return TITLES_REGISTRY.get().getValues().stream().collect(Collectors.toMap(Title::getName, Title::get)).get(titleName);
    }

    public static void syncTitles(ServerPlayer serverPlayer) {
        serverPlayerTitles(serverPlayer);
        serverPlayer.getCapability(RegistryCapabilities.APTITUDE).ifPresent(aptitudeCapability -> {
            Title title = getTitle(AptitudeCapability.get(serverPlayer).getPlayerTitle());
            if (title != null) {
                serverPlayer.setCustomName(Component.translatable(title.getKey()));
            }
        });
    }

    public static void serverPlayerTitles(ServerPlayer serverPlayer) {
        if (!serverPlayer.isDeadOrDying())
            serverPlayer.getCapability(RegistryCapabilities.APTITUDE).ifPresent(capability -> {
                FIGHTER.get().setRequirement(serverPlayer, (AptitudeCapability.get(serverPlayer).getAptitudeLevel(RegistryAptitudes.STRENGTH.get()) >= 16));
                GREAT_FIGHTER.get().setRequirement(serverPlayer, (AptitudeCapability.get(serverPlayer).getAptitudeLevel(RegistryAptitudes.STRENGTH.get()) >= 32));
                WARRIOR.get().setRequirement(serverPlayer, (AptitudeCapability.get(serverPlayer).getAptitudeLevel(RegistryAptitudes.CONSTITUTION.get()) >= 16));
                GREAT_WARRIOR.get().setRequirement(serverPlayer, (AptitudeCapability.get(serverPlayer).getAptitudeLevel(RegistryAptitudes.CONSTITUTION.get()) >= 32));
                RANGER.get().setRequirement(serverPlayer, (AptitudeCapability.get(serverPlayer).getAptitudeLevel(RegistryAptitudes.DEXTERITY.get()) >= 16));
                GREAT_RANGER.get().setRequirement(serverPlayer, (AptitudeCapability.get(serverPlayer).getAptitudeLevel(RegistryAptitudes.DEXTERITY.get()) >= 32));
                TANK.get().setRequirement(serverPlayer, (AptitudeCapability.get(serverPlayer).getAptitudeLevel(RegistryAptitudes.DEFENSE.get()) >= 16));
                GREAT_TANK.get().setRequirement(serverPlayer, (AptitudeCapability.get(serverPlayer).getAptitudeLevel(RegistryAptitudes.DEFENSE.get()) >= 32));
                ALCHEMIST.get().setRequirement(serverPlayer, (AptitudeCapability.get(serverPlayer).getAptitudeLevel(RegistryAptitudes.INTELLIGENCE.get()) >= 16));
                GREAT_ALCHEMIST.get().setRequirement(serverPlayer, (AptitudeCapability.get(serverPlayer).getAptitudeLevel(RegistryAptitudes.INTELLIGENCE.get()) >= 32));
                MINER.get().setRequirement(serverPlayer, (AptitudeCapability.get(serverPlayer).getAptitudeLevel(RegistryAptitudes.BUILDING.get()) >= 16));
                GREAT_MINER.get().setRequirement(serverPlayer, (AptitudeCapability.get(serverPlayer).getAptitudeLevel(RegistryAptitudes.BUILDING.get()) >= 32));
                MAGICIAN.get().setRequirement(serverPlayer, (AptitudeCapability.get(serverPlayer).getAptitudeLevel(RegistryAptitudes.MAGIC.get()) >= 16));
                GREAT_MAGICIAN.get().setRequirement(serverPlayer, (AptitudeCapability.get(serverPlayer).getAptitudeLevel(RegistryAptitudes.MAGIC.get()) >= 32));
                LUCKY_ONE.get().setRequirement(serverPlayer, (AptitudeCapability.get(serverPlayer).getAptitudeLevel(RegistryAptitudes.LUCK.get()) >= 16));
                GREAT_LUCKY_ONE.get().setRequirement(serverPlayer, (AptitudeCapability.get(serverPlayer).getAptitudeLevel(RegistryAptitudes.LUCK.get()) >= 32));
                DRAGON_SLAYER.get().setRequirement(serverPlayer, (serverPlayer.getStats().getValue(Stats.ENTITY_KILLED.get(EntityType.ENDER_DRAGON)) >= 10));
                PLAYER_KILLER.get().setRequirement(serverPlayer, (serverPlayer.getStats().getValue(Stats.ENTITY_KILLED.get(EntityType.PLAYER)) >= 100));
                MOB_KILLER.get().setRequirement(serverPlayer, (serverPlayer.getStats().getValue(Stats.CUSTOM, Stats.MOB_KILLS) >= 100));
                GREAT_MOB_KILLER.get().setRequirement(serverPlayer, (serverPlayer.getStats().getValue(Stats.CUSTOM, Stats.MOB_KILLS) >= 1000));
                MASTER_MOB_KILLER.get().setRequirement(serverPlayer, (serverPlayer.getStats().getValue(Stats.CUSTOM, Stats.MOB_KILLS) >= 10000));
                HERO.get().setRequirement(serverPlayer, (serverPlayer.getStats().getValue(Stats.CUSTOM, Stats.RAID_WIN) >= 10));
                VILLAIN.get().setRequirement(serverPlayer, (serverPlayer.getStats().getValue(Stats.ENTITY_KILLED.get(EntityType.VILLAGER)) >= 100));
                FISHERMAN.get().setRequirement(serverPlayer, (serverPlayer.getStats().getValue(Stats.CUSTOM, Stats.FISH_CAUGHT) >= 100));
                GREAT_FISHERMAN.get().setRequirement(serverPlayer, (serverPlayer.getStats().getValue(Stats.CUSTOM, Stats.FISH_CAUGHT) >= 1000));
                MASTER_FISHERMAN.get().setRequirement(serverPlayer, (serverPlayer.getStats().getValue(Stats.CUSTOM, Stats.FISH_CAUGHT) >= 10000));
                ENCHANTER.get().setRequirement(serverPlayer, (serverPlayer.getStats().getValue(Stats.CUSTOM, Stats.ENCHANT_ITEM) >= 10));
                GREAT_ENCHANTER.get().setRequirement(serverPlayer, (serverPlayer.getStats().getValue(Stats.CUSTOM, Stats.ENCHANT_ITEM) >= 100));
                MASTER_ENCHANTER.get().setRequirement(serverPlayer, (serverPlayer.getStats().getValue(Stats.CUSTOM, Stats.ENCHANT_ITEM) >= 1000));
                SURVIVOR.get().setRequirement(serverPlayer, (serverPlayer.getStats().getValue(Stats.CUSTOM, Stats.TIME_SINCE_DEATH) >= 2400000));
                BUSINESSMAN.get().setRequirement(serverPlayer, (serverPlayer.getStats().getValue(Stats.CUSTOM, Stats.TRADED_WITH_VILLAGER) >= 100));
                BOAT_DRIVER.get().setRequirement(serverPlayer, (serverPlayer.getStats().getValue(Stats.CUSTOM, Stats.BOAT_ONE_CM) >= 1000000));
                CART_DRIVER.get().setRequirement(serverPlayer, (serverPlayer.getStats().getValue(Stats.CUSTOM, Stats.MINECART_ONE_CM) >= 1000000));
                HORSE_RIDER.get().setRequirement(serverPlayer, (serverPlayer.getStats().getValue(Stats.CUSTOM, Stats.HORSE_ONE_CM) >= 1000000));
                PIG_RIDER.get().setRequirement(serverPlayer, (serverPlayer.getStats().getValue(Stats.CUSTOM, Stats.PIG_ONE_CM) >= 1000000));
                STRIDER_RIDER.get().setRequirement(serverPlayer, (serverPlayer.getStats().getValue(Stats.CUSTOM, Stats.STRIDER_ONE_CM) >= 1000000));
                NETHER_TRAVELER.get().setRequirement(serverPlayer, serverPlayer.level().dimension().equals(Level.NETHER));
                END_TRAVELER.get().setRequirement(serverPlayer, serverPlayer.level().dimension().equals(Level.END));
                ADMIN.get().setRequirement(serverPlayer, serverPlayer.hasPermissions(2));
            });
    }
}


