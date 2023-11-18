package com.dbt233.mod.teaproduce.blocks.screen;

import com.dbt233.mod.teaproduce.blocks.gui.MagicTeaBarrelMenu;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.MenuAccess;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;

import static com.dbt233.mod.teaproduce.Utils.MODID;

public class MagicTeaBarrelScreen extends AbstractContainerScreen<MagicTeaBarrelMenu> implements MenuAccess<MagicTeaBarrelMenu> {
    private final ResourceLocation TEXTURE = new ResourceLocation(MODID, "textures/gui/container/magic_tea_barrel");

    public MagicTeaBarrelScreen(MagicTeaBarrelMenu p_97741_, Inventory p_97742_, Component p_97743_) {
        super(p_97741_, p_97742_, p_97743_);
        this.imageHeight = MagicTeaBarrelMenu.ySize;
        this.imageWidth  = MagicTeaBarrelMenu.xSize;
        this.passEvents  = false;
    }

    @Override
    public void render(@NotNull PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        this.renderTooltip(matrixStack, mouseX, mouseY);
    }

    @Override
    protected void renderLabels(@NotNull PoseStack matrixStack, int mouseX, int mouseY) {
//        super.renderLabels(matrixStack, mouseX, mouseY);
        this.font.draw(matrixStack, this.title, 8.0f, 6.0f, 4210752);
        this.font.draw(matrixStack, this.playerInventoryTitle, 8.0f, (float) (this.imageHeight - 4 * 18), 4210752);
    }

    @Override
    protected void renderBg(@NotNull PoseStack poseStack, float partialTicks, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1f, 1f, 1f,1f);
        RenderSystem.setShaderTexture(0, TEXTURE);

        int x = (this.width - this.imageWidth) / 2;
        int y = (this.height - this.imageHeight) / 2;

        int textureYSize = 258;
        int textureXSize = 176;
        blit(poseStack, x, y, this.imageWidth, this.imageHeight, textureXSize, textureYSize);

    }
}
