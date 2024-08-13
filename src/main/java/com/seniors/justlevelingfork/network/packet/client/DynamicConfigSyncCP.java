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


    public DynamicConfigSyncCP(){
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
    }

    public DynamicConfigSyncCP(FriendlyByteBuf buffer){
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
    }

    public void toBytes(FriendlyByteBuf buffer) {
        buffer.writeInt(this.playersMaxGlobalLevel);
        String result = convertArraysToString(attackPassiveLevels, attackKnockbackPassiveLevels, maxHealthPassiveLevels, knockbackResistancePassiveLevels, movementSpeedPassiveLevels, projectileDamagePassiveLevels, armorPassiveLevels, armorToughnessPassiveLevels, attackSpeedPassiveLevels, entityReachPassiveLevels, blockReachPassiveLevels, breakSpeedPassiveLevels, beneficialEffectPassiveLevels, magicResistPassiveLevels, criticalDamagePassiveLevels, luckPassiveLevels);
        buffer.writeUtf(result);
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

                HandlerCommonConfig.HANDLER.save();
            }
        });
        context.setPacketHandled(true);
    }

    public static void sendToPlayer(Player player) {
        ServerNetworking.sendToPlayer(new DynamicConfigSyncCP(), (ServerPlayer) player);
    }

}
