package com.dbt233.mod.teaproduce.blocks.block_entities.dry_rack;

import com.dbt233.mod.teaproduce.registry.BlockEntityRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CampfireCookingRecipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec2;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.Optional;

public class DryRackBlockEntity extends BlockEntity {
    private final NonNullList<ItemStack> items = NonNullList.withSize(4, ItemStack.EMPTY);
    private final RecipeManager.CachedCheck<Container, CampfireCookingRecipe> quickCheck = RecipeManager.createCheck(RecipeType.CAMPFIRE_COOKING);
    private int[] dryingTime = new int[4];
    private int[] dryingProcess = new int[4];
    private int usedSlot = 0;
    public Optional<CampfireCookingRecipe> getDryableRecipe(ItemStack itemStack) {
        return this.items.stream().noneMatch(ItemStack::isEmpty) ? Optional.empty() : this.quickCheck.getRecipeFor(new SimpleContainer(itemStack), this.level);
    }
    public DryRackBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(BlockEntityRegistry.DRY_RACK_BLOCK_ENTITY.get(), blockPos, blockState);
    }
    public boolean changeItem(Entity entity, ItemStack itemStack, CampfireCookingRecipe recipe) {
        if (!itemStack.isEmpty() && usedSlot < 4) {
            this.items.set(usedSlot, itemStack.split(1));
            this.dryingTime[usedSlot] = recipe.getCookingTime();
            this.dryingProcess[usedSlot] = 0;
            this.level.gameEvent(GameEvent.BLOCK_CHANGE, this.getBlockPos(), GameEvent.Context.of(entity, this.getBlockState()));
            usedSlot++;
            setChanged();
            return true;
        }
        return false;
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        this.items.clear();
        ContainerHelper.loadAllItems(nbt, items);
        if (nbt.contains("UsedSlot", Tag.TAG_INT)) {
            this.usedSlot = nbt.getInt("UsedSlot");
        }
        if (nbt.contains("DryingTime",Tag.TAG_INT_ARRAY)) {
            this.dryingTime = nbt.getIntArray("DryingTime");
        }
        if (nbt.contains("DryingProcess", Tag.TAG_INT_ARRAY)) {
            this.dryingProcess = nbt.getIntArray("DryingProcess");
        }
    }
    private CompoundTag writeItems(CompoundTag nbt) {
        ContainerHelper.saveAllItems(nbt, items);
        nbt.putInt("UsedSlot", this.usedSlot);
        nbt.putIntArray("DryingTime", this.dryingTime);
        nbt.putIntArray("DryingProcess", this.dryingProcess);
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
    }
    public NonNullList<ItemStack> getItems() {
        return this.items;
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
        this.level.sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), Block.UPDATE_CLIENTS);
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        this.load(Objects.requireNonNull(pkt.getTag()));
    }
}
