package com.seniors.justlevelingfork.network.packet.common;

import com.seniors.justlevelingfork.network.packet.JustLevelingPacket;

import com.seniors.justlevelingfork.network.ServerNetworking;
import com.seniors.justlevelingfork.registry.RegistrySkills;


import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.inventory.PlayerEnderChestContainer;


public class OpenEnderChestSP implements JustLevelingPacket {
    public OpenEnderChestSP() {
    }

    public void handle(ServerPlayer sender) {
            ServerPlayer player = sender;

            if (player != null) {
                PlayerEnderChestContainer enderChest = player.getEnderChestInventory();
                SimpleMenuProvider enderChestContainer = new SimpleMenuProvider((id, pl, b) -> ChestMenu.threeRows(id, pl, enderChest), Component.translatable(RegistrySkills.WORMHOLE_STORAGE.get().getKey()));
                player.openMenu(enderChestContainer);
            }
    }

    public OpenEnderChestSP(FriendlyByteBuf buffer) {
    }

    public void toBytes(FriendlyByteBuf buffer) {
    }

    public static void send() {
        ServerNetworking.sendToServer(new OpenEnderChestSP());
    }
}


