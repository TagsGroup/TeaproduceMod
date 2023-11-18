package com.dbt233.mod.teaproduce.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

import static com.dbt233.mod.teaproduce.Utils.MODID;
import static com.dbt233.mod.teaproduce.registry.BlockRegistry.MODEL_SIMPLE_BLOCKS;

public class BlockStateDataProvider extends BlockStateProvider {

    public BlockStateDataProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        for (var block : MODEL_SIMPLE_BLOCKS) {
            cubeAllBlockWithItem(block);
        }
    }

    public void cubeAllBlockWithItem(RegistryObject<Block> block) {
        simpleBlockWithItem(block.get(), cubeAll(block.get()));
    }
}
