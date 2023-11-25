package com.dbt233.mod.teaproduce.blocks.block_entities.dry_rack;

import com.dbt233.mod.teaproduce.registry.BlockEntityRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.Containers;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CampfireCookingRecipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec2;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.Optional;

import static com.dbt233.mod.teaproduce.Utils.rand;

public class DryRackBlockEntity extends BlockEntity {
    private final NonNullList<ItemStack> items = NonNullList.withSize(4, ItemStack.EMPTY);
    private final RecipeManager.CachedCheck<Container, CampfireCookingRecipe> quickCheck = RecipeManager.createCheck(RecipeType.CAMPFIRE_COOKING);
    private int[] dryingTime = new int[4];
    private int[] dryingProgress = new int[4];

    public Optional<CampfireCookingRecipe> getDryableRecipe(ItemStack itemStack) {
        return this.items.stream().noneMatch(ItemStack::isEmpty) ? Optional.empty() : this.quickCheck.getRecipeFor(new SimpleContainer(itemStack), this.level);
    }
    public DryRackBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(BlockEntityRegistry.DRY_RACK_BLOCK_ENTITY.get(), blockPos, blockState);
    }
    public boolean changeItem(Entity entity, ItemStack placeItemStack, CampfireCookingRecipe recipe) {
        for (int slot = 0; slot < this.items.size(); slot++) {
            System.out.println("OK!");
            if (this.items.get(slot).isEmpty()) {
                this.items.set(slot, placeItemStack.split(1));
                this.dryingTime[slot] = recipe.getCookingTime();
                this.dryingProgress[slot] = 0;
                this.level.gameEvent(GameEvent.BLOCK_CHANGE, this.getBlockPos(), GameEvent.Context.of(entity, this.getBlockState()));
                setChanged();
                return true;
            }
        }
        return false;
    }

    public static void particleTick(Level level, BlockPos pos, BlockState state, DryRackBlockEntity blockEntity) {
        RandomSource randomsource = level.random;
        if (randomsource.nextFloat() < 0.11F) {
            for(int i = 0; i < randomsource.nextInt(2) + 2; ++i) {
                DryRack.makeParticles(level, pos);
            }
        }

        int l = state.getValue(DryRack.FACING).get2DDataValue();

        for(int j = 0; j < blockEntity.items.size(); ++j) {
            if (!blockEntity.items.get(j).isEmpty() && randomsource.nextFloat() < 0.2F) {
                Direction direction = Direction.from2DDataValue(Math.floorMod(j + l, 4));
                float f = 0.3125F;
                double d0 = (double)pos.getX() + 0.5D - (double)((float)direction.getStepX() * 0.3125F) + (double)((float)direction.getClockWise().getStepX() * 0.3125F);
                double d1 = (double)pos.getY() + 0.5D;
                double d2 = (double)pos.getZ() + 0.5D - (double)((float)direction.getStepZ() * 0.3125F) + (double)((float)direction.getClockWise().getStepZ() * 0.3125F);

                if (rand.nextInt(0, 5) == 0) {
                    level.addParticle(ParticleTypes.LAVA, d0, d1, d2, 0.0D, 5.0E-4D, 0.0D);
                }
            }
        }

    }

    public static void dryTick(Level level, BlockPos blockPos, BlockState blockState, DryRackBlockEntity blockEntity) {
        boolean flag = false;

        for(int i = 0; i < blockEntity.items.size(); ++i) {
            ItemStack itemstack = blockEntity.items.get(i);
            if (!itemstack.isEmpty()) {
                flag = true;
                int j = blockEntity.dryingProgress[i]++;
                if (blockEntity.dryingProgress[i] >= blockEntity.dryingTime[i]) {
                    Container container = new SimpleContainer(itemstack);
                    ItemStack itemstack1 = blockEntity.quickCheck.getRecipeFor(container, level).map((cookingRecipe) ->
                            cookingRecipe.assemble(container, level.registryAccess())
                    ).orElse(itemstack);
                    if (itemstack1.isItemEnabled(level.enabledFeatures())) {
                        Containers.dropItemStack(level, blockPos.getX(), blockPos.getY(), blockPos.getZ(), itemstack1);
                        blockEntity.items.set(i, ItemStack.EMPTY);
                        level.sendBlockUpdated(blockPos, blockState, blockState, Block.UPDATE_ALL);
                        level.gameEvent(GameEvent.BLOCK_CHANGE, blockPos, GameEvent.Context.of(blockState));
                    }
                }
            }
        }
        if (flag) {
            setChanged(level, blockPos, blockState);
        }
    }


    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        this.items.clear();
        ContainerHelper.loadAllItems(nbt, items);
        if (nbt.contains("DryingTime",Tag.TAG_INT_ARRAY)) {
            this.dryingTime = nbt.getIntArray("DryingTime");
        }
        if (nbt.contains("DryingProcess", Tag.TAG_INT_ARRAY)) {
            this.dryingProgress = nbt.getIntArray("DryingProcess");
        }
    }
    private CompoundTag writeItems(CompoundTag nbt) {
        ContainerHelper.saveAllItems(nbt, items);
        nbt.putIntArray("DryingTime", this.dryingTime);
        nbt.putIntArray("DryingProcess", this.dryingProgress);
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
