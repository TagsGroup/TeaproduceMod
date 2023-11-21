package com.dbt233.mod.teaproduce.blocks.block_entities.dry_rack;

import com.dbt233.mod.teaproduce.registry.BlockEntityRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec2;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class DryRackBlockEntity extends BlockEntity {
    private ItemStackHandler inventory = new ItemStackHandler(4) {
        @Override
        protected int getStackLimit(int slot, @NotNull ItemStack stack) {
            return 1;
        }
    };
    public DryRackBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(BlockEntityRegistry.DRY_RACK_BLOCK_ENTITY.get(), blockPos, blockState);
    }
    public boolean addItem(ItemStack itemStack) {
        for (int slot = 0; slot < this.inventory.getSlots(); slot++) {
            if(this.inventory.getStackInSlot(slot).isEmpty()) {
                this.inventory.setStackInSlot(slot, itemStack.split(1));
                setChanged();
                return true;
            }
        }
        return false;
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        if (nbt.contains("Inventory")) {
            this.inventory.deserializeNBT(nbt.getCompound("Inventory"));
        } else {
            this.inventory.deserializeNBT(nbt);
        }

    }
    private CompoundTag writeItems(CompoundTag nbt) {
        nbt.put("Inventory", this.inventory.serializeNBT());
        super.saveAdditional(nbt);
        return nbt;
    }
    @Override
    public CompoundTag getUpdateTag() {
        return this.writeItems(new CompoundTag());
    }
    @Override
    protected void saveAdditional(CompoundTag nbt) {
        this.writeItems(nbt);
        super.saveAdditional(nbt);
    }
    public ItemStackHandler getInventory() {
        return this.inventory;
    }
    public Vec2 getItemOffset(int slot) {
        float x = 0.2f;
        float y = 0.2f;
        Vec2[] offset = new Vec2[] {
                new Vec2(x,  y), new Vec2(-x,  y),
                new Vec2(x, -y), new Vec2(-x, -y)
        };
        return offset[slot];
    }
    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public void setChanged() {
        super.setChanged();
        if (this.level != null) {
            this.level.sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), Block.UPDATE_CLIENTS);
        }
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        this.load(Objects.requireNonNull(pkt.getTag()));
    }
}
