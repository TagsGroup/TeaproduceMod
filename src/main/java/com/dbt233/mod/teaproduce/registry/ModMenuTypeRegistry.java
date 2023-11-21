package com.dbt233.mod.teaproduce.registry;

import com.dbt233.mod.teaproduce.blocks.block_entities.magic_tea_barrel.MagicTeaBarrelMenu;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.dbt233.mod.teaproduce.Utils.MODID;

public class ModMenuTypeRegistry {
    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(ForgeRegistries.MENU_TYPES, MODID);

    public static final RegistryObject<MenuType<MagicTeaBarrelMenu>> MAGIC_TEA_BARREL = MENUS.register("magic_tea_barrel",
            () -> new MenuType<>(MagicTeaBarrelMenu::createMagicTeaBarrelMenu, FeatureFlags.REGISTRY.allFlags()));
}
