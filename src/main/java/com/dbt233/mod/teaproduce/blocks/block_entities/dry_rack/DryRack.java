package com.dbt233.mod.teaproduce.blocks.block_entities.dry_rack;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.crafting.CampfireCookingRecipe;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class DryRack extends BaseEntityBlock {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static VoxelShape SHAPE = box(0d,7d,0d,16d,9d,16d);
    static {
        VoxelShape[] shapes = {
                box(0d,0d,0d,2d,7d,2d),
                box(14d,0d,0d,16d,7d,2d),
                box(14d,0d,14d,16d,7d,16d),
                box(0d,0d,14d,2d,7d,16d),
                box(0d,9d,0d, 16d,10d,1d),
                box(15d,9d,1d,16d,10d,15d),
                box(0d,9d,15d,16d,10d,16d),
                box(0d,9d,1d,1d,10d,15d)};
        for (VoxelShape voxelShape : shapes) {
            SHAPE = Shapes.or(SHAPE, voxelShape);
        }
    }
    public DryRack(Properties properties) {
        super(properties);
        this.registerDefaultState(this.getStateDefinition().any().setValue(FACING, Direction.NORTH));
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new DryRackBlockEntity(blockPos, blockState);
    }

    @Override
    public VoxelShape getShape(BlockState p_60555_, BlockGetter p_60556_, BlockPos p_60557_, CollisionContext p_60558_) {
        return SHAPE;
    }

    @Override
    public InteractionResult use(BlockState blockState, Level level, BlockPos blockPos, Player player,
                                 InteractionHand interactionHand, BlockHitResult blockHitResult) {
        BlockEntity blockEntity = level.getBlockEntity(blockPos);
        if (blockEntity instanceof DryRackBlockEntity dryRackBlockEntity) {
            ItemStack itemStack = player.getMainHandItem();
            Optional<CampfireCookingRecipe> recipe = dryRackBlockEntity.getDryableRecipe(itemStack);
            if (recipe.isPresent()) {
                if (!level.isClientSide && dryRackBlockEntity.changeItem(player, itemStack, recipe.get())) {
                    return InteractionResult.SUCCESS;
                }
            }
        }
        return InteractionResult.CONSUME;
    }

    @Override
    public RenderShape getRenderShape(BlockState p_49232_) {
        return RenderShape.MODEL;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }
    @Override
    public BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }
    @Override
    public BlockState rotate(BlockState state, Rotation direction) {
        return state.setValue(FACING, direction.rotate(state.getValue(FACING)));
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    public void onRemove(BlockState blockState, Level level, BlockPos blockPos, BlockState blockState2, boolean b) {
        if (!blockState.is(blockState2.getBlock())) {
            BlockEntity blockEntity = level.getBlockEntity(blockPos);
            if (blockEntity instanceof DryRackBlockEntity) {
                NonNullList<ItemStack> items = ((DryRackBlockEntity) blockEntity).getItems();
                for (int slot = 0; slot < items.size(); slot++) {
                    level.addFreshEntity(new ItemEntity(level, blockPos.getX(), blockPos.getY(), blockPos.getZ(), items.get(slot)));
//                    level.playSound(null, blockPos, SoundEvents.ANVIL_PLACE, SoundSource.BLOCKS);
                }
            }
            level.updateNeighbourForOutputSignal(blockPos, this);
        }
        super.onRemove(blockState, level, blockPos, blockState2, b);
    }
}
