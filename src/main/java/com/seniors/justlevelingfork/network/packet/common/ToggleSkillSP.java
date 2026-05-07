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
            ServerPlayer player = sender;

            if (player != null) {
                AptitudeCapability capability = AptitudeCapability.get(player);

                Skill skill = RegistrySkills.getSkill(this.skill);
                boolean setToggleSkills = capability.getToggleSkill(skill);
                capability.setToggleSkill(skill, !setToggleSkills);
                SyncAptitudeCapabilityCP.send(player);
            }
    }

    public static void send(Skill skill, boolean toggle) {
        ServerNetworking.sendToServer(new ToggleSkillSP(skill, toggle));
    }
}


