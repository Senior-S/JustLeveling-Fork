package com.seniors.justlevelingfork.mixin;

import com.seniors.justlevelingfork.registry.RegistrySkills;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.trading.MerchantOffer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({Villager.class})
public abstract class MixVillager {
    @Unique
    private final Villager this$class = (Villager) (Object) this;

    @Inject(method = {"updateSpecialPrices"}, at = {@At("TAIL")})
    public void updateSpecialPrices(Player player, CallbackInfo info) {
        if (player != null && RegistrySkills.HAGGLER.get().isEnabled(player))
            for (MerchantOffer merchantoffer : this.this$class.getOffers()) {
                double d0 = RegistrySkills.HAGGLER.get().getValue()[0] / 100.0D;
                int j = (int) Math.floor(d0 * merchantoffer.getBaseCostA().getCount());
                merchantoffer.addToSpecialPriceDiff(-Math.max(j, 1));
            }
    }
}


