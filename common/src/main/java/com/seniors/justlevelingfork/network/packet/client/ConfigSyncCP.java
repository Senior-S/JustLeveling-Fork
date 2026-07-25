package com.seniors.justlevelingfork.network.packet.client;

import com.seniors.justlevelingfork.network.packet.JustLevelingPacket;

import com.seniors.justlevelingfork.client.core.ClientHooks;
import com.seniors.justlevelingfork.config.models.LockItem;
import com.seniors.justlevelingfork.handler.HandlerAptitude;
import com.seniors.justlevelingfork.handler.HandlerLockItemsConfig;
import com.seniors.justlevelingfork.network.ServerNetworking;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Client packet that synchronizes server lock-item rules.
 */
public class ConfigSyncCP implements JustLevelingPacket {
    private static final int MAX_LOCK_ITEMS = 10_000;
    private static final int MAX_LOCK_ITEM_LENGTH = 2_048;
    private final List<String> lockItems;

    public ConfigSyncCP(List<String> lockItems) {
        if (lockItems.size() > MAX_LOCK_ITEMS) {
            throw new IllegalArgumentException("Too many lock-item entries: " + lockItems.size());
        }
        this.lockItems = List.copyOf(lockItems);
    }

    public ConfigSyncCP(FriendlyByteBuf buffer) {
        int size = buffer.readVarInt();
        if (size < 0 || size > MAX_LOCK_ITEMS) {
            throw new IllegalArgumentException("Invalid lock-item entry count: " + size);
        }

        List<String> decodedItems = new ArrayList<>(size);
        for (int index = 0; index < size; index++) {
            decodedItems.add(buffer.readUtf(MAX_LOCK_ITEM_LENGTH));
        }
        this.lockItems = List.copyOf(decodedItems);
    }

    public void toBytes(FriendlyByteBuf buffer) {
        buffer.writeVarInt(this.lockItems.size());
        for (String lockItem : this.lockItems) {
            buffer.writeUtf(lockItem, MAX_LOCK_ITEM_LENGTH);
        }
    }

    public void handle(ServerPlayer sender) {
        if (ClientHooks.hasClientPlayer()) {
            List<LockItem> lockItemList = new ArrayList<>();
            this.lockItems.forEach(value -> lockItemList.add(LockItem.getLockItemFromString(value, new LockItem())));

            HandlerAptitude.UpdateLockItems(lockItemList);
        }
    }

    public static void sendToPlayer(Player player) {
        ServerNetworking.sendToPlayer(createCurrentConfigPacket(), (ServerPlayer) player);
    }

    public static void sendToAllPlayers() {
        ServerNetworking.sendToAllClients(createCurrentConfigPacket());
    }

    private static ConfigSyncCP createCurrentConfigPacket() {
        List<String> stringList = new ArrayList<>();
        for (LockItem lockItem : HandlerLockItemsConfig.HANDLER.instance().lockItemList) {
            stringList.add(lockItem.toString());
        }

        return new ConfigSyncCP(stringList);
    }
}
