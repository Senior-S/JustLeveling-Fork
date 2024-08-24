package com.seniors.justlevelingfork.network.packet.client;

import com.seniors.justlevelingfork.handler.HandlerCommonConfig;
import com.seniors.justlevelingfork.network.ServerNetworking;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent;

import java.util.Arrays;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * Client packet to update dynamic config options.
 * Can be sent multiple times during a player session.
 */
public class DynamicConfigSyncCP {

    private final int aptitudeMaxLevel;
    private final int playersMaxGlobalLevel;
    private final int[] attackPassiveLevels;
    private final int[] attackKnockbackPassiveLevels;
    private final int[] maxHealthPassiveLevels;
    private final int[] knockbackResistancePassiveLevels;
    private final int[] movementSpeedPassiveLevels;
    private final int[] projectileDamagePassiveLevels;
    private final int[] armorPassiveLevels;
    private final int[] armorToughnessPassiveLevels;
    private final int[] attackSpeedPassiveLevels;
    private final int[] entityReachPassiveLevels;
    private final int[] blockReachPassiveLevels;
    private final int[] breakSpeedPassiveLevels;
    private final int[] beneficialEffectPassiveLevels;
    private final int[] magicResistPassiveLevels;
    private final int[] criticalDamagePassiveLevels;
    private final int[] luckPassiveLevels;

    private final int oneHandedRequiredLevel;
    private final int fightingSpiritRequiredLevel;
    private final int berserkerRequiredLevel;
    private final int athleticsRequiredLevel;
    private final int turtleShieldRequiredLevel;
    private final int lionHeartRequiredLevel;
    private final int quickRepositionRequiredLevel;
    private final int stealthMasteryRequiredLevel;
    private final int catEyesRequiredLevel;
    private final int snowWalkerRequiredLevel;
    private final int counterattackRequiredLevel;
    private final int diamondSkinRequiredLevel;
    private final int scholarRequiredLevel;
    private final int hagglerRequiredLevel;
    private final int alchemyManipulationRequiredLevel;
    private final int obsidianSmasherRequiredLevel;
    private final int treasureHunterRequiredLevel;
    private final int convergenceRequiredLevel;
    private final int safePortRequiredLevel;
    private final int lifeEaterRequiredLevel;
    private final int wornholeStorageRequiredLevel;
    private final int criticalRollRequiredLevel;
    private final int luckyDropRequiredLevel;
    private final int limitBreakerRequiredLevel;


    public DynamicConfigSyncCP(){
        aptitudeMaxLevel = HandlerCommonConfig.HANDLER.instance().aptitudeMaxLevel;
        playersMaxGlobalLevel = HandlerCommonConfig.HANDLER.instance().playersMaxGlobalLevel;
        attackPassiveLevels = HandlerCommonConfig.HANDLER.instance().attackPassiveLevels ;
        attackKnockbackPassiveLevels = HandlerCommonConfig.HANDLER.instance().attackKnockbackPassiveLevels;
        maxHealthPassiveLevels = HandlerCommonConfig.HANDLER.instance().maxHealthPassiveLevels;
        knockbackResistancePassiveLevels = HandlerCommonConfig.HANDLER.instance().knockbackResistancePassiveLevels;
        movementSpeedPassiveLevels = HandlerCommonConfig.HANDLER.instance().movementSpeedPassiveLevels;
        projectileDamagePassiveLevels = HandlerCommonConfig.HANDLER.instance().projectileDamagePassiveLevels;
        armorPassiveLevels = HandlerCommonConfig.HANDLER.instance().armorPassiveLevels;
        armorToughnessPassiveLevels = HandlerCommonConfig.HANDLER.instance().armorToughnessPassiveLevels;
        attackSpeedPassiveLevels = HandlerCommonConfig.HANDLER.instance().attackSpeedPassiveLevels;
        entityReachPassiveLevels = HandlerCommonConfig.HANDLER.instance().entityReachPassiveLevels;
        blockReachPassiveLevels = HandlerCommonConfig.HANDLER.instance().blockReachPassiveLevels;
        breakSpeedPassiveLevels = HandlerCommonConfig.HANDLER.instance().breakSpeedPassiveLevels;
        beneficialEffectPassiveLevels = HandlerCommonConfig.HANDLER.instance().beneficialEffectPassiveLevels;
        magicResistPassiveLevels = HandlerCommonConfig.HANDLER.instance().magicResistPassiveLevels;
        criticalDamagePassiveLevels = HandlerCommonConfig.HANDLER.instance().criticalDamagePassiveLevels;
        luckPassiveLevels = HandlerCommonConfig.HANDLER.instance().luckPassiveLevels;

        oneHandedRequiredLevel = HandlerCommonConfig.HANDLER.instance().oneHandedRequiredLevel;
        fightingSpiritRequiredLevel = HandlerCommonConfig.HANDLER.instance().fightingSpiritRequiredLevel;
        berserkerRequiredLevel = HandlerCommonConfig.HANDLER.instance().berserkerRequiredLevel;
        athleticsRequiredLevel = HandlerCommonConfig.HANDLER.instance().athleticsRequiredLevel;
        turtleShieldRequiredLevel = HandlerCommonConfig.HANDLER.instance().turtleShieldRequiredLevel;
        lionHeartRequiredLevel = HandlerCommonConfig.HANDLER.instance().lionHeartRequiredLevel;
        quickRepositionRequiredLevel = HandlerCommonConfig.HANDLER.instance().quickRepositionRequiredLevel;
        stealthMasteryRequiredLevel = HandlerCommonConfig.HANDLER.instance().stealthMasteryRequiredLevel;
        catEyesRequiredLevel = HandlerCommonConfig.HANDLER.instance().catEyesRequiredLevel;
        snowWalkerRequiredLevel = HandlerCommonConfig.HANDLER.instance().snowWalkerRequiredLevel;
        counterattackRequiredLevel = HandlerCommonConfig.HANDLER.instance().counterattackRequiredLevel;
        diamondSkinRequiredLevel = HandlerCommonConfig.HANDLER.instance().diamondSkinRequiredLevel;
        scholarRequiredLevel = HandlerCommonConfig.HANDLER.instance().scholarRequiredLevel;
        hagglerRequiredLevel = HandlerCommonConfig.HANDLER.instance().hagglerRequiredLevel;
        alchemyManipulationRequiredLevel = HandlerCommonConfig.HANDLER.instance().alchemyManipulationRequiredLevel;
        obsidianSmasherRequiredLevel = HandlerCommonConfig.HANDLER.instance().obsidianSmasherRequiredLevel;
        treasureHunterRequiredLevel = HandlerCommonConfig.HANDLER.instance().treasureHunterRequiredLevel;
        convergenceRequiredLevel = HandlerCommonConfig.HANDLER.instance().convergenceRequiredLevel;
        safePortRequiredLevel = HandlerCommonConfig.HANDLER.instance().safePortRequiredLevel;
        lifeEaterRequiredLevel = HandlerCommonConfig.HANDLER.instance().lifeEaterRequiredLevel;
        wornholeStorageRequiredLevel = HandlerCommonConfig.HANDLER.instance().wornholeStorageRequiredLevel;
        criticalRollRequiredLevel = HandlerCommonConfig.HANDLER.instance().criticalRollRequiredLevel;
        luckyDropRequiredLevel = HandlerCommonConfig.HANDLER.instance().luckyDropRequiredLevel;
        limitBreakerRequiredLevel = HandlerCommonConfig.HANDLER.instance().limitBreakerRequiredLevel;
    }

    public DynamicConfigSyncCP(FriendlyByteBuf buffer){
        aptitudeMaxLevel = buffer.readInt();
        playersMaxGlobalLevel = buffer.readInt();
        String[] allLevels = buffer.readUtf().split("-");
        int[] levels;
        levels = Arrays.stream(allLevels[0].split(",")).mapToInt(Integer::parseInt).toArray();
        attackPassiveLevels = levels;
        levels = Arrays.stream(allLevels[1].split(",")).mapToInt(Integer::parseInt).toArray();
        attackKnockbackPassiveLevels = levels;
        levels = Arrays.stream(allLevels[2].split(",")).mapToInt(Integer::parseInt).toArray();
        maxHealthPassiveLevels = levels;
        levels = Arrays.stream(allLevels[3].split(",")).mapToInt(Integer::parseInt).toArray();
        knockbackResistancePassiveLevels = levels;
        levels = Arrays.stream(allLevels[4].split(",")).mapToInt(Integer::parseInt).toArray();
        movementSpeedPassiveLevels = levels;
        levels = Arrays.stream(allLevels[5].split(",")).mapToInt(Integer::parseInt).toArray();
        projectileDamagePassiveLevels = levels;
        levels = Arrays.stream(allLevels[6].split(",")).mapToInt(Integer::parseInt).toArray();
        armorPassiveLevels = levels;
        levels = Arrays.stream(allLevels[7].split(",")).mapToInt(Integer::parseInt).toArray();
        armorToughnessPassiveLevels = levels;
        levels = Arrays.stream(allLevels[8].split(",")).mapToInt(Integer::parseInt).toArray();
        attackSpeedPassiveLevels = levels;
        levels = Arrays.stream(allLevels[9].split(",")).mapToInt(Integer::parseInt).toArray();
        entityReachPassiveLevels = levels;
        levels = Arrays.stream(allLevels[10].split(",")).mapToInt(Integer::parseInt).toArray();
        blockReachPassiveLevels = levels;
        levels = Arrays.stream(allLevels[11].split(",")).mapToInt(Integer::parseInt).toArray();
        breakSpeedPassiveLevels = levels;
        levels = Arrays.stream(allLevels[12].split(",")).mapToInt(Integer::parseInt).toArray();
        beneficialEffectPassiveLevels = levels;
        levels = Arrays.stream(allLevels[13].split(",")).mapToInt(Integer::parseInt).toArray();
        magicResistPassiveLevels = levels;
        levels = Arrays.stream(allLevels[14].split(",")).mapToInt(Integer::parseInt).toArray();
        criticalDamagePassiveLevels = levels;
        levels = Arrays.stream(allLevels[15].split(",")).mapToInt(Integer::parseInt).toArray();
        luckPassiveLevels = levels;

        oneHandedRequiredLevel = buffer.readInt();
        fightingSpiritRequiredLevel = buffer.readInt();
        berserkerRequiredLevel = buffer.readInt();
        athleticsRequiredLevel = buffer.readInt();
        turtleShieldRequiredLevel = buffer.readInt();
        lionHeartRequiredLevel = buffer.readInt();
        quickRepositionRequiredLevel = buffer.readInt();
        stealthMasteryRequiredLevel = buffer.readInt();
        catEyesRequiredLevel = buffer.readInt();
        snowWalkerRequiredLevel = buffer.readInt();
        counterattackRequiredLevel = buffer.readInt();
        diamondSkinRequiredLevel = buffer.readInt();
        scholarRequiredLevel = buffer.readInt();
        hagglerRequiredLevel = buffer.readInt();
        alchemyManipulationRequiredLevel = buffer.readInt();
        obsidianSmasherRequiredLevel = buffer.readInt();
        treasureHunterRequiredLevel = buffer.readInt();
        convergenceRequiredLevel = buffer.readInt();
        safePortRequiredLevel = buffer.readInt();
        lifeEaterRequiredLevel = buffer.readInt();
        wornholeStorageRequiredLevel = buffer.readInt();
        criticalRollRequiredLevel = buffer.readInt();
        luckyDropRequiredLevel = buffer.readInt();
        limitBreakerRequiredLevel = buffer.readInt();
    }

    public void toBytes(FriendlyByteBuf buffer) {
        buffer.writeInt(this.aptitudeMaxLevel);
        buffer.writeInt(this.playersMaxGlobalLevel);
        String result = convertArraysToString(attackPassiveLevels, attackKnockbackPassiveLevels, maxHealthPassiveLevels, knockbackResistancePassiveLevels, movementSpeedPassiveLevels, projectileDamagePassiveLevels, armorPassiveLevels, armorToughnessPassiveLevels, attackSpeedPassiveLevels, entityReachPassiveLevels, blockReachPassiveLevels, breakSpeedPassiveLevels, beneficialEffectPassiveLevels, magicResistPassiveLevels, criticalDamagePassiveLevels, luckPassiveLevels);
        buffer.writeUtf(result);
        buffer.writeInt(oneHandedRequiredLevel);
        buffer.writeInt(fightingSpiritRequiredLevel);
        buffer.writeInt(berserkerRequiredLevel);
        buffer.writeInt(athleticsRequiredLevel);
        buffer.writeInt(turtleShieldRequiredLevel);
        buffer.writeInt(lionHeartRequiredLevel);
        buffer.writeInt(quickRepositionRequiredLevel);
        buffer.writeInt(stealthMasteryRequiredLevel);
        buffer.writeInt(catEyesRequiredLevel);
        buffer.writeInt(snowWalkerRequiredLevel);
        buffer.writeInt(counterattackRequiredLevel);
        buffer.writeInt(diamondSkinRequiredLevel);
        buffer.writeInt(scholarRequiredLevel);
        buffer.writeInt(hagglerRequiredLevel);
        buffer.writeInt(alchemyManipulationRequiredLevel);
        buffer.writeInt(obsidianSmasherRequiredLevel);
        buffer.writeInt(treasureHunterRequiredLevel);
        buffer.writeInt(convergenceRequiredLevel);
        buffer.writeInt(safePortRequiredLevel);
        buffer.writeInt(lifeEaterRequiredLevel);
        buffer.writeInt(wornholeStorageRequiredLevel);
        buffer.writeInt(criticalRollRequiredLevel);
        buffer.writeInt(luckyDropRequiredLevel);
        buffer.writeInt(limitBreakerRequiredLevel);
    }

    private String convertArraysToString(int[]... arrays) {
        return Arrays.stream(arrays)
                .map(array -> Arrays.stream(array)
                        .mapToObj(String::valueOf)
                        .collect(Collectors.joining(",")))
                .collect(Collectors.joining("-"));
    }

    public void handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            LocalPlayer localPlayer = (Minecraft.getInstance()).player;
            if(localPlayer != null){
                HandlerCommonConfig.HANDLER.instance().aptitudeMaxLevel = this.aptitudeMaxLevel;
                HandlerCommonConfig.HANDLER.instance().playersMaxGlobalLevel = this.playersMaxGlobalLevel;
                HandlerCommonConfig.HANDLER.instance().attackPassiveLevels = this.attackPassiveLevels;
                HandlerCommonConfig.HANDLER.instance().attackKnockbackPassiveLevels = this.attackKnockbackPassiveLevels;
                HandlerCommonConfig.HANDLER.instance().maxHealthPassiveLevels = this.maxHealthPassiveLevels;
                HandlerCommonConfig.HANDLER.instance().knockbackResistancePassiveLevels = this.knockbackResistancePassiveLevels;
                HandlerCommonConfig.HANDLER.instance().movementSpeedPassiveLevels = this.movementSpeedPassiveLevels;
                HandlerCommonConfig.HANDLER.instance().projectileDamagePassiveLevels = this.projectileDamagePassiveLevels;
                HandlerCommonConfig.HANDLER.instance().armorPassiveLevels = this.armorPassiveLevels;
                HandlerCommonConfig.HANDLER.instance().armorToughnessPassiveLevels = this.armorToughnessPassiveLevels;
                HandlerCommonConfig.HANDLER.instance().attackSpeedPassiveLevels = this.attackSpeedPassiveLevels;
                HandlerCommonConfig.HANDLER.instance().entityReachPassiveLevels = this.entityReachPassiveLevels;
                HandlerCommonConfig.HANDLER.instance().blockReachPassiveLevels = this.blockReachPassiveLevels;
                HandlerCommonConfig.HANDLER.instance().breakSpeedPassiveLevels = this.breakSpeedPassiveLevels;
                HandlerCommonConfig.HANDLER.instance().beneficialEffectPassiveLevels = this.beneficialEffectPassiveLevels;
                HandlerCommonConfig.HANDLER.instance().magicResistPassiveLevels = this.magicResistPassiveLevels;
                HandlerCommonConfig.HANDLER.instance().criticalDamagePassiveLevels = this.criticalDamagePassiveLevels;
                HandlerCommonConfig.HANDLER.instance().luckPassiveLevels = this.luckPassiveLevels;

                HandlerCommonConfig.HANDLER.instance().oneHandedRequiredLevel = this.oneHandedRequiredLevel;
                HandlerCommonConfig.HANDLER.instance().fightingSpiritRequiredLevel = this.fightingSpiritRequiredLevel;
                HandlerCommonConfig.HANDLER.instance().berserkerRequiredLevel = this.berserkerRequiredLevel;
                HandlerCommonConfig.HANDLER.instance().athleticsRequiredLevel = this.athleticsRequiredLevel;
                HandlerCommonConfig.HANDLER.instance().turtleShieldRequiredLevel = this.turtleShieldRequiredLevel;
                HandlerCommonConfig.HANDLER.instance().lionHeartRequiredLevel = this.lionHeartRequiredLevel;
                HandlerCommonConfig.HANDLER.instance().quickRepositionRequiredLevel = this.quickRepositionRequiredLevel;
                HandlerCommonConfig.HANDLER.instance().stealthMasteryRequiredLevel = this.stealthMasteryRequiredLevel;
                HandlerCommonConfig.HANDLER.instance().catEyesRequiredLevel = this.catEyesRequiredLevel;
                HandlerCommonConfig.HANDLER.instance().snowWalkerRequiredLevel = this.snowWalkerRequiredLevel;
                HandlerCommonConfig.HANDLER.instance().counterattackRequiredLevel = this.counterattackRequiredLevel;
                HandlerCommonConfig.HANDLER.instance().diamondSkinRequiredLevel = this.diamondSkinRequiredLevel;
                HandlerCommonConfig.HANDLER.instance().scholarRequiredLevel = this.scholarRequiredLevel;
                HandlerCommonConfig.HANDLER.instance().hagglerRequiredLevel = this.hagglerRequiredLevel;
                HandlerCommonConfig.HANDLER.instance().alchemyManipulationRequiredLevel = this.alchemyManipulationRequiredLevel;
                HandlerCommonConfig.HANDLER.instance().obsidianSmasherRequiredLevel = this.obsidianSmasherRequiredLevel;
                HandlerCommonConfig.HANDLER.instance().treasureHunterRequiredLevel = this.treasureHunterRequiredLevel;
                HandlerCommonConfig.HANDLER.instance().convergenceRequiredLevel = this.convergenceRequiredLevel;
                HandlerCommonConfig.HANDLER.instance().safePortRequiredLevel = this.safePortRequiredLevel;
                HandlerCommonConfig.HANDLER.instance().lifeEaterRequiredLevel = this.lifeEaterRequiredLevel;
                HandlerCommonConfig.HANDLER.instance().wornholeStorageRequiredLevel = this.wornholeStorageRequiredLevel;
                HandlerCommonConfig.HANDLER.instance().criticalRollRequiredLevel = this.criticalRollRequiredLevel;
                HandlerCommonConfig.HANDLER.instance().luckyDropRequiredLevel = this.luckyDropRequiredLevel;
                HandlerCommonConfig.HANDLER.instance().limitBreakerRequiredLevel = this.limitBreakerRequiredLevel;

                HandlerCommonConfig.HANDLER.save();
            }
        });
        context.setPacketHandled(true);
    }

    public static void sendToPlayer(Player player) {
        ServerNetworking.sendToPlayer(new DynamicConfigSyncCP(), (ServerPlayer) player);
    }

    public static void sendToAllPlayers(){
        ServerNetworking.sendToAllClients(new DynamicConfigSyncCP());
    }

}
