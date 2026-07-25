package com.seniors.justlevelingfork.network.packet.common;

import com.seniors.justlevelingfork.network.packet.JustLevelingPacket;

import com.seniors.justlevelingfork.common.capability.AptitudeCapability;
import com.seniors.justlevelingfork.network.ServerNetworking;
import com.seniors.justlevelingfork.network.packet.client.SyncAptitudeCapabilityCP;
import com.seniors.justlevelingfork.registry.RegistrySkills;
import com.seniors.justlevelingfork.registry.skills.Skill;


import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;

public class ToggleSkillSP implements JustLevelingPacket {
    private final String skill;
    private final boolean toggle;

    public ToggleSkillSP(Skill skill, boolean toggle) {
        this.skill = skill.getName();
        this.toggle = toggle;
    }

    public ToggleSkillSP(FriendlyByteBuf buffer) {
        this.skill = buffer.readUtf();
        this.toggle = buffer.readBoolean();
    }

    public void toBytes(FriendlyByteBuf buffer) {
        buffer.writeUtf(this.skill);
        buffer.writeBoolean(this.toggle);
    }

    public void handle(ServerPlayer sender) {
        if (sender == null) {
            return;
        }

        AptitudeCapability capability = AptitudeCapability.get(sender);
        Skill skill = RegistrySkills.getSkill(this.skill);
        if (capability == null || skill == null) {
            return;
        }

        if (this.toggle && !skill.getToggle(sender)) {
            return;
        }

        capability.setToggleSkill(skill, this.toggle);
        SyncAptitudeCapabilityCP.send(sender);
    }

    public static void send(Skill skill, boolean toggle) {
        ServerNetworking.sendToServer(new ToggleSkillSP(skill, toggle));
    }
}


