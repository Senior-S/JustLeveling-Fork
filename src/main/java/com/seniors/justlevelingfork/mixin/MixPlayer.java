package com.seniors.justlevelingfork.mixin;

import com.seniors.justlevelingfork.registry.RegistrySkills;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin({Player.class})
public abstract class MixPlayer extends LivingEntity {
    @Unique
    private final Player this$class = (Player) (Object) this;

    protected MixPlayer(Level level) {
        super(EntityType.PLAYER, level);
    }


    public int getMaxAirSupply() {
        if (RegistrySkills.ATHLETICS.get().isEnabled(this.this$class)) {
            return (int) (300.0D * RegistrySkills.ATHLETICS.get().getValue()[0]);
        }
        return 300;
    }
}


