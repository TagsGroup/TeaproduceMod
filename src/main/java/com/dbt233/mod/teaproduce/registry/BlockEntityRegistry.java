package com.dbt233.mod.teaproduce.registry;

import com.dbt233.mod.teaproduce.blocks.entity.MagicTeaBarrelBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.dbt233.mod.teaproduce.Utils.MODID;

public class BlockEntityRegistry {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, MODID);
    public static final RegistryObject<BlockEntityType<?>> MAGIC_TEA_BARREL_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("magic_tea_barrel", () ->
                    BlockEntityType.Builder.of(MagicTeaBarrelBlockEntity::new,
                            BlockRegistry.MAGIC_TEA_BARREL.get()).build(null));

}
