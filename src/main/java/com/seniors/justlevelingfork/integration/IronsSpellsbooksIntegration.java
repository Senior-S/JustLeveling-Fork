package com.seniors.justlevelingfork.integration;

import com.seniors.justlevelingfork.common.capability.AptitudeCapability;
import com.seniors.justlevelingfork.handler.HandlerCommonConfig;
import io.redspace.ironsspellbooks.api.events.SpellPreCastEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;

public class IronsSpellsbooksIntegration {

    public static boolean isModLoaded() {
        return ModList.get().isLoaded("irons_spellbooks");
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onSpellCastEvent(SpellPreCastEvent event){
        Player player = event.getEntity();

        String spellId = event.getSpellId();

        if(HandlerCommonConfig.HANDLER.instance().logSpellIds){
            player.sendSystemMessage(Component.literal(String.format("[JLFork] >> Spell ID: %s", spellId)));
        }

        if (!player.isCreative()) {
            AptitudeCapability provider = AptitudeCapability.get(player);
            if (!provider.canUseSpecificID(player, spellId)) {
                event.setCanceled(true);
            }
        }
    }


}
