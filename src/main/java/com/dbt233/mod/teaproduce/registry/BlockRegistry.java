package com.dbt233.mod.teaproduce.registry;

import com.dbt233.mod.teaproduce.blocks.MagicTeaBarrel.MagicTeaBarrel;
import com.google.common.collect.Sets;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.LinkedHashSet;
import java.util.function.Supplier;

import static com.dbt233.mod.teaproduce.Utils.MODID;

public class BlockRegistry {
    public static final LinkedHashSet<RegistryObject<Block>> MODEL_SIMPLE_BLOCKS = Sets.newLinkedHashSet();

    public static RegistryObject<Block> registrySimpleBlock(String name, Supplier<Block> supplier) {
        RegistryObject<Block> block = BLOCKS.register(name, supplier);
        MODEL_SIMPLE_BLOCKS.add(block);
        return block;
    }

    public static RegistryObject<Block> registryBlock(String name, Supplier<Block> supplier) {
        return BLOCKS.register(name, supplier);
    }
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);
    public static final RegistryObject<Block> BOXED_GREEN_TEA_LEAVES = registryBlock("boxed_green_tea_leaves", () -> new Block(BlockBehaviour.Properties.copy(Blocks.HAY_BLOCK)));
    public static final RegistryObject<Block> MAGIC_TEA_BARREL = registryBlock("magic_tea_barrel", () -> new MagicTeaBarrel(BlockBehaviour.Properties.copy(Blocks.BARREL)));
    public static final RegistryObject<Block> MAGIC_PURPLE_ORE = registrySimpleBlock("magic_purple_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_ORE).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> DEEPSLATE_MAGIC_PURPLE_ORE = registrySimpleBlock("deepslate_magic_purple_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE_IRON_ORE).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> MAGIC_PURPLE_BLOCK = registrySimpleBlock("magic_purple_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).requiresCorrectToolForDrops()));

}
