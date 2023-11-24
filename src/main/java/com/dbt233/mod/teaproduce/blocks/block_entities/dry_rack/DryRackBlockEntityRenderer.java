package com.dbt233.mod.teaproduce.blocks.block_entities.dry_rack;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.Vec2;
import net.minecraftforge.items.ItemStackHandler;

public class DryRackBlockEntityRenderer implements BlockEntityRenderer<DryRackBlockEntity> {
    public DryRackBlockEntityRenderer(BlockEntityRendererProvider.Context context) {

    }
    @Override
    public void render(DryRackBlockEntity dryRack, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int combinedLight, int combinedOverLay) {
        Direction direction = dryRack.getBlockState().getValue(BlockStateProperties.HORIZONTAL_FACING).getOpposite();
        ItemStackHandler inventory = dryRack.getInventory();
        int posLong = (int) dryRack.getBlockPos().asLong();

        for (int slot = 0; slot < inventory.getSlots(); slot++) {
            ItemStack itemStack = inventory.getStackInSlot(slot);
            if (!itemStack.isEmpty()) {
                poseStack.pushPose();
                poseStack.translate(0.5d, 9d/16d, 0.5d);
                float yRot = -direction.toYRot();
                poseStack.mulPose(Axis.YP.rotationDegrees(yRot));
                poseStack.mulPose(Axis.XP.rotationDegrees(90.0f));
                Vec2 itemOffset = dryRack.getItemOffset(slot);
                poseStack.translate(itemOffset.x, itemOffset.y, 0.0d);
                poseStack.scale(0.35f, 0.35f, 0.35f);
                if (dryRack.getLevel() != null) {
                    Minecraft.getInstance().getItemRenderer()
                            .renderStatic(itemStack, ItemDisplayContext.FIXED, LevelRenderer.getLightColor(dryRack.getLevel(), dryRack.getBlockPos()),
                                    combinedOverLay, poseStack, buffer, dryRack.getLevel(), posLong + slot);
                }
                poseStack.popPose();
            }

        }
    }
}
