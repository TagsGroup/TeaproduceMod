package com.dbt233.mod.teaproduce.blocks.entity;

import com.dbt233.mod.teaproduce.blocks.MagicTeaBarrel;
import com.dbt233.mod.teaproduce.blocks.gui.MagicTeaBarrelMenu;
import com.dbt233.mod.teaproduce.registry.BlockEntityRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.ContainerOpenersCounter;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class MagicTeaBarrelBlockEntity extends RandomizableContainerBlockEntity {

    public static final int SIZE = 81;

    private NonNullList<ItemStack>  items = NonNullList.withSize(SIZE, ItemStack.EMPTY);
    private final ContainerOpenersCounter openersCounter = new ContainerOpenersCounter() {
        @Override
        protected void onOpen(@NotNull Level level, @NotNull BlockPos blockPos, @NotNull BlockState blockState) {
            MagicTeaBarrelBlockEntity.this.playSound(blockState, SoundEvents.BARREL_OPEN);
            MagicTeaBarrelBlockEntity.this.updateBlockState(blockState, true);
        }

        @Override
        protected void onClose(@NotNull Level level, @NotNull BlockPos blockPos, @NotNull BlockState blockState) {
            MagicTeaBarrelBlockEntity.this.playSound(blockState, SoundEvents.BARREL_CLOSE);
            MagicTeaBarrelBlockEntity.this.updateBlockState(blockState, false);
        }

        @Override
        protected void openerCountChanged(@NotNull Level level, @NotNull BlockPos blockPos, @NotNull BlockState blockState, int i1, int i2) {

        }

        @Override
        protected boolean isOwnContainer(Player player) {
            if (player.containerMenu instanceof MagicTeaBarrelMenu) {
                Container container = ((MagicTeaBarrelMenu) player.containerMenu).getContainer();
                return container == MagicTeaBarrelBlockEntity.this;
            }
            return false;
        }

    };
    public MagicTeaBarrelBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(BlockEntityRegistry.MAGIC_TEA_BARREL_BLOCK_ENTITY.get(), blockPos, blockState);
    }

    @Override
    protected @NotNull NonNullList<ItemStack> getItems() {
        return this.items;
    }

    @Override
    protected void setItems(@NotNull NonNullList<ItemStack> items) {
        this.items = items;
    }

    @Override
    protected @NotNull Component getDefaultName() {
        return Component.translatable("block.teaproduce.magic_tea_barrel");
    }

    @Override
    protected @NotNull AbstractContainerMenu createMenu(int i, @NotNull Inventory inventory) {
        return MagicTeaBarrelMenu.createMagicTeaBarrelMenu(i, inventory, this);
    }

    @Override
    public int getContainerSize() {
        return SIZE;
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag compoundTag) {
        super.saveAdditional(compoundTag);
        if (!this.trySaveLootTable(compoundTag)) {
            ContainerHelper.saveAllItems(compoundTag, this.items);
        }
    }

    @Override
    public void load(@NotNull CompoundTag compoundTag) {
        super.load(compoundTag);
        this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        if (!this.tryLoadLootTable(compoundTag)) {
            ContainerHelper.loadAllItems(compoundTag, this.items);
        }
    }

    @Override
    public void stopOpen(@NotNull Player player) {
        if (!this.remove && !player.isSpectator()) {
            this.openersCounter.decrementOpeners(player, this.getLevel(), this.getBlockPos(), this.getBlockState());
        }
    }

    @Override
    public void startOpen(@NotNull Player player) {
        if (!this.remove && !player.isSpectator()) {
            this.openersCounter.incrementOpeners(player, this.getLevel(), this.getBlockPos(), this.getBlockState());
        }
    }

    public void recheckOpen() {
        if (!this.remove) {
            this.openersCounter.recheckOpeners(this.getLevel(), this.getBlockPos(), this.getBlockState());
        }
    }

    void updateBlockState(BlockState p_58607_, boolean p_58608_) {
        this.level.setBlock(this.getBlockPos(), p_58607_.setValue(MagicTeaBarrel.OPEN, p_58608_), 3);
    }

    void playSound(BlockState blockState, SoundEvent event) {
        Vec3i vec3i = blockState.getValue(MagicTeaBarrel.FACING).getNormal();
        double d0 = (double)this.worldPosition.getX() + 0.5D + (double)vec3i.getX() / 2.0D;
        double d1 = (double)this.worldPosition.getY() + 0.5D + (double)vec3i.getY() / 2.0D;
        double d2 = (double)this.worldPosition.getZ() + 0.5D + (double)vec3i.getZ() / 2.0D;
        this.level.playSound(null, d0, d1, d2, event, SoundSource.BLOCKS, 0.5F, this.level.random.nextFloat() * 0.1F + 0.9F);
    }
}
