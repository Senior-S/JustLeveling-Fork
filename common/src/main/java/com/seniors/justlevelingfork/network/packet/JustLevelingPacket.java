package com.seniors.justlevelingfork.network.packet;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;

public interface JustLevelingPacket {
    void toBytes(FriendlyByteBuf buffer);

    void handle(ServerPlayer sender);
}
