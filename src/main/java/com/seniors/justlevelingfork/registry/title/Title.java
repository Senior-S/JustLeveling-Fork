package com.seniors.justlevelingfork.registry.title;

import com.seniors.justlevelingfork.client.core.Utils;
import com.seniors.justlevelingfork.common.capability.AptitudeCapability;
import com.seniors.justlevelingfork.handler.HandlerConfigClient;
import com.seniors.justlevelingfork.network.packet.client.SyncAptitudeCapabilityCP;
import com.seniors.justlevelingfork.network.packet.client.TitleOverlayCP;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

import java.util.ArrayList;
import java.util.List;

public class Title {
    private final ResourceLocation key;
    public final boolean Requirement;
    public final boolean HideRequirements;

    public Title(ResourceLocation key, boolean requirement, boolean hideRequirements) {
        this.key = key;
        this.Requirement = requirement;
        this.HideRequirements = hideRequirements;
    }

    public Title get() {
        return this;
    }


    public String getMod() {
        return this.key.getNamespace();
    }

    public String getName() {
        return this.key.getPath();
    }

    public String getKey() {
        return "title." + this.key.toLanguageKey();
    }

    public String getDescription() {
        return getKey() + ".description";
    }

    public boolean getRequirement() {
        return AptitudeCapability.get().getLockTitle(this);
    }

    public boolean getRequirement(Player player) {
        return AptitudeCapability.get(player).getLockTitle(this);
    }

    public void setRequirement(ServerPlayer serverPlayer, boolean check) {
        if (!getRequirement(serverPlayer) && check) {
            TitleOverlayCP.send(serverPlayer, this);
            AptitudeCapability.get(serverPlayer).setUnlockTitle(this, true);
            SyncAptitudeCapabilityCP.send(serverPlayer);
        }
    }

    public List<Component> tooltip() {
        List<Component> list = new ArrayList<>();
        list.add(Component.empty().append(Component.translatable("title.justlevelingfork.requirement_description").withStyle(ChatFormatting.GOLD)).append(Component.translatable(getDescription()).withStyle(ChatFormatting.GRAY)));
        if (HandlerConfigClient.showTitleModName.get())
            list.add(Component.literal(Utils.getModName(getMod())).withStyle(ChatFormatting.BLUE).withStyle(ChatFormatting.ITALIC));
        return list;
    }
}


