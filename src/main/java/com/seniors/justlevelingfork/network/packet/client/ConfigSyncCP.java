package com.seniors.justlevelingfork.network.packet.client;

import com.seniors.justlevelingfork.JustLevelingFork;
import com.seniors.justlevelingfork.config.models.LockItem;
import com.seniors.justlevelingfork.handler.HandlerAptitude;
import com.seniors.justlevelingfork.handler.HandlerLockItemsConfig;
import com.seniors.justlevelingfork.network.ServerNetworking;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/**
 * Client packet to notify client of locked items of the serer.
 * Must only be sent on player join.
 */
public class ConfigSyncCP {
    private final byte[] stringListBytes;

    public ConfigSyncCP(byte[] array) {
        stringListBytes = array;
    }

    public ConfigSyncCP(FriendlyByteBuf buffer) {
        int readableBytes = buffer.readableBytes();
        stringListBytes = new byte[readableBytes];
        buffer.readBytes(stringListBytes);
    }

    public void toBytes(FriendlyByteBuf buffer) {
        buffer.writeBytes(this.stringListBytes);
    }

    @SuppressWarnings("unchecked")
    public void handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            LocalPlayer localPlayer = (Minecraft.getInstance()).player;
            if(localPlayer != null){
                List<String> lockItemsStrings = new ArrayList<>();
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(stringListBytes);
                try (ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream)) {
                    lockItemsStrings = (List<String>) objectInputStream.readObject();
                } catch (IOException | ClassNotFoundException e) {
                    JustLevelingFork.getLOGGER().error(">> Error writing config to the client. Exception: {}", e.getMessage());
                }
                List<LockItem> lockItemList = new ArrayList<>();
                lockItemsStrings.forEach(c -> lockItemList.add(LockItem.getLockItemFromString(c, new LockItem())));

                HandlerAptitude.UpdateLockItems(lockItemList);
            }
        });
        context.setPacketHandled(true);
    }

    public static void sendToPlayer(Player player) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        List<String> stringList = new ArrayList<>();
        for (LockItem lockItem : HandlerLockItemsConfig.HANDLER.instance().lockItemList) {
            stringList.add(lockItem.toString());
        }
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream)) {
            objectOutputStream.writeObject(stringList);
        } catch (IOException e) {
            JustLevelingFork.getLOGGER().error(">> Error sending configuration to the client. Exception: {}", e.getMessage());
        }

        ServerNetworking.sendToPlayer(new ConfigSyncCP(byteArrayOutputStream.toByteArray()), (ServerPlayer) player);
    }
}
