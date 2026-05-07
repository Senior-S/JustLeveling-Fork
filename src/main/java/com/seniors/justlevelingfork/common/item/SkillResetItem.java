package com.seniors.justlevelingfork.common.item;

import com.seniors.justlevelingfork.common.capability.AptitudeCapability;
import com.seniors.justlevelingfork.handler.HandlerCommonConfig;
import com.seniors.justlevelingfork.network.packet.common.AptitudeLevelUpSP;
import com.seniors.justlevelingfork.network.packet.client.SyncAptitudeCapabilityCP;
import com.seniors.justlevelingfork.registry.RegistryAttributes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class SkillResetItem extends Item {
    public SkillResetItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (level.isClientSide) {
            return InteractionResultHolder.success(stack);
        }

        AptitudeCapability capability = AptitudeCapability.get(player);
        if (capability == null) {
            return InteractionResultHolder.fail(stack);
        }

        int refundedExperience = HandlerCommonConfig.HANDLER.instance().skillResetRefundsSpentLevels
                ? capability.getSpentAptitudeExperience()
                : 0;
        capability.resetSkills();
        if (refundedExperience > 0 && !player.getAbilities().instabuild) {
            AptitudeLevelUpSP.addPlayerXP(player, refundedExperience);
        }

        if (player instanceof ServerPlayer serverPlayer) {
            RegistryAttributes.modifierAttributes(serverPlayer);
            SyncAptitudeCapabilityCP.send(serverPlayer);
        }

        if (!player.getAbilities().instabuild) {
            stack.shrink(1);
        }
        player.displayClientMessage(Component.translatable("message.justlevelingfork.skills_reset"), true);
        return InteractionResultHolder.consume(stack);
    }
}
