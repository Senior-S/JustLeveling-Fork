package com.seniors.justlevelingfork.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.gui.ForgeIngameGui;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

@Mixin({ForgeIngameGui.class})
public abstract class MixForgeGui extends Gui {
    @Unique
    private final ForgeIngameGui this$class = (ForgeIngameGui) (Object) this;

    public MixForgeGui(Minecraft mc) {
        super(mc);
    }

    @Inject(method = {"renderAir"}, at = {@At("HEAD")}, remap = false, cancellable = true)
    private void renderAirs(int width, int height, PoseStack poseStack, CallbackInfo info) {
        info.cancel();
        this.minecraft.getProfiler().push("air");
        Player player = (Player) this.minecraft.getCameraEntity();
        RenderSystem.enableBlend();
        int left = width / 2 + 91;
        int top = height - this.this$class.right_height;

        int air = Objects.requireNonNull(player).getAirSupply();
        if (player.isEyeInFluid(FluidTags.WATER) || air < player.getMaxAirSupply()) {
            int full = Mth.ceil((double)(air - 2) * 10.0D / (double)player.getMaxAirSupply());
            int partial = Mth.ceil((double)air * 10.0D / (double)player.getMaxAirSupply()) - full;

            for(int i = 0; i < full + partial; ++i) {
                RenderSystem.setShaderTexture(0, GUI_ICONS_LOCATION);
                this.blit(poseStack, left - i * 8 - 9, top, i < full ? 16 : 25, 18, 9, 9);
            }

            this.this$class.right_height += 10;
        }

        RenderSystem.disableBlend();
        this.minecraft.getProfiler().pop();
    }
}