package com.dbt233.mod.teaproduce.registry;

import com.dbt233.mod.teaproduce.blocks.MagicTeaBarrel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.dbt233.mod.teaproduce.Utils.MODID;

public class BlockRegistry {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);
    public static final RegistryObject<Block> BOXED_GREEN_TEA_LEAVES = BLOCKS.register("boxed_green_tea_leaves", () -> new Block(BlockBehaviour.Properties.copy(Blocks.HAY_BLOCK)));
    public static final RegistryObject<Block> MAGIC_TEA_BARREL = BLOCKS.register("magic_tea_barrel", () -> new MagicTeaBarrel(BlockBehaviour.Properties.copy(Blocks.BARREL)));
    public static final RegistryObject<Block> MAGIC_PURPLE_ORE = BLOCKS.register("magic_purple_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_ORE).requiresCorrectToolForDrops()));
}
