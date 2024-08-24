package com.seniors.justlevelingfork.network.packet.common;

import com.seniors.justlevelingfork.network.ServerNetworking;
import com.seniors.justlevelingfork.registry.RegistrySkills;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.inventory.PlayerEnderChestContainer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;


public class OpenEnderChestSP {
    public OpenEnderChestSP() {
    }

    public void handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();

            if (player != null) {
                PlayerEnderChestContainer enderChest = player.getEnderChestInventory();
                SimpleMenuProvider enderChestContainer = new SimpleMenuProvider((id, pl, b) -> ChestMenu.threeRows(id, pl, enderChest), new TranslatableComponent(RegistrySkills.WORMHOLE_STORAGE.get().getKey()));
                player.openMenu(enderChestContainer);
            }
        });
        context.setPacketHandled(true);
    }

    public OpenEnderChestSP(FriendlyByteBuf buffer) {
    }

    public void toBytes(FriendlyByteBuf buffer) {
    }

    public static void send() {
        ServerNetworking.sendToServer(new OpenEnderChestSP());
    }
}


