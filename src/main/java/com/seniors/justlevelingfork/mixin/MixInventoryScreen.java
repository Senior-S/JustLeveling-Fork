package com.seniors.justlevelingfork.mixin;


import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.seniors.justlevelingfork.JustLevelingFork;
import com.seniors.justlevelingfork.client.core.Utils;
import com.seniors.justlevelingfork.client.gui.DrawTabs;
import com.seniors.justlevelingfork.network.packet.common.OpenEnderChestSP;
import com.seniors.justlevelingfork.registry.RegistrySkills;
import net.minecraft.client.gui.screens.inventory.EffectRenderingInventoryScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.InventoryMenu;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({InventoryScreen.class})
public abstract class MixInventoryScreen extends EffectRenderingInventoryScreen<InventoryMenu> {

    @Unique
    public boolean this$checkMouse = false;

    public MixInventoryScreen(Player player) {
        super(player.inventoryMenu, player.getInventory(), new TranslatableComponent("container.crafting"));
    }

    @Unique
    public boolean this$isMouseCheck = false;

    @Shadow
    public abstract RecipeBookComponent getRecipeBookComponent();

    @Inject(method = {"renderBg"}, at = {@At("TAIL")})
    private void render(PoseStack matrixStack, float delta, int mouseX, int mouseY, CallbackInfo info) {
        DrawTabs.render(this, matrixStack, mouseX, mouseY, 176, 166, getRecipeBookComponent().isVisible() ? 77 : 0);

        if (RegistrySkills.WORMHOLE_STORAGE.get().isEnabled()) {
            this.this$isMouseCheck = false;
            matrixStack.pushPose();
            int width = (getMinecraft().getWindow().getGuiScaledWidth() - 176) / 2;
            int height = (getMinecraft().getWindow().getGuiScaledHeight() - 166) / 2;
            int buttonX = width + 127 + (getRecipeBookComponent().isVisible() ? 77 : 0);
            int buttonY = height + 61;
            int checkButton = 0;
            if (Utils.checkMouse(buttonX, buttonY, mouseX, mouseY, 20, 18)) {
                checkButton = 18;
                this.this$isMouseCheck = true;
                if (this.this$checkMouse) {
                    OpenEnderChestSP.send();
                    Utils.playSound();
                    this.this$checkMouse = false;
                }
            }
            RenderSystem.enableBlend();
            RenderSystem.setShaderTexture(0, new ResourceLocation(JustLevelingFork.MOD_ID, "textures/skill/ender_chest_button.png"));
            blit(matrixStack, buttonX, buttonY, 0.0F, checkButton, 20, 18, 20, 36);
            matrixStack.popPose();
        }
    }

    @Inject(method = {"mouseClicked"}, at = {@At("HEAD")})
    private void mouseClicked(double mouseX, double mouseY, int button, CallbackInfoReturnable<Boolean> info) {
        if (button == 0 && this.this$isMouseCheck) this.this$checkMouse = true;
        DrawTabs.mouseClicked(button);
    }


    public void onClose() {
        this.this$checkMouse = false;
        DrawTabs.onClose();
        super.onClose();
    }
}

