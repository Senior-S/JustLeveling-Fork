package com.seniors.justlevelingfork.network.packet.client;

import com.seniors.justlevelingfork.JustLevelingFork;
import com.seniors.justlevelingfork.config.models.TitleModel;
import com.seniors.justlevelingfork.handler.HandlerTitlesConfig;
import com.seniors.justlevelingfork.network.ServerNetworking;
import com.seniors.justlevelingfork.registry.RegistryTitles;
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

public class TitlesSyncCP {
    private final byte[] stringListBytes;

    public TitlesSyncCP(byte[] array) {
        stringListBytes = array;
    }

    public TitlesSyncCP(FriendlyByteBuf buffer) {
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
                List<String> titlesStringList = new ArrayList<>();
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(stringListBytes);
                try (ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream)) {
                    titlesStringList = (List<String>) objectInputStream.readObject();
                } catch (IOException | ClassNotFoundException e) {
                    JustLevelingFork.getLOGGER().error(">> Error writing config to the client. Exception: {}", e.getMessage());
                }
                List<TitleModel> titleModelList = new ArrayList<>();
                titlesStringList.forEach(c -> {
                    TitleModel titleModel = new TitleModel(c);
                    titleModelList.add(titleModel);
                    titleModel.Registry(RegistryTitles.TITLES);
                });

                HandlerTitlesConfig.HANDLER.instance().titleList = titleModelList;
                HandlerTitlesConfig.HANDLER.save();
            }
        });
        context.setPacketHandled(true);
    }

    public static void sendToPlayer(Player player) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        List<String> stringList = new ArrayList<>();
        for (TitleModel titleModel : HandlerTitlesConfig.HANDLER.instance().titleList) {
            stringList.add(titleModel.toString());
        }
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream)) {
            objectOutputStream.writeObject(stringList);
        } catch (IOException e) {
            JustLevelingFork.getLOGGER().error(">> Error sending titles to the client. Exception: {}", e.getMessage());
        }

        ServerNetworking.sendToPlayer(new TitlesSyncCP(byteArrayOutputStream.toByteArray()), (ServerPlayer) player);
    }
}
