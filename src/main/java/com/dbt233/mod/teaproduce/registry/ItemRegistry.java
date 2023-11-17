package com.dbt233.mod.teaproduce.registry;

import com.dbt233.mod.teaproduce.items.GreenTeaLeaf;
import com.dbt233.mod.teaproduce.items.MagicTeaWand;
import com.dbt233.mod.teaproduce.registry.property.FoodProperty;
import com.dbt233.mod.teaproduce.registry.property.TierProperty;
import com.google.common.collect.Sets;
import net.minecraft.world.item.*;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.LinkedHashSet;
import java.util.function.Supplier;

import static com.dbt233.mod.teaproduce.Utils.MODID;

public class ItemRegistry {
    public static final LinkedHashSet<RegistryObject<Item>> CREATIVE_TAB_ITEMS = Sets.newLinkedHashSet();
    public static final LinkedHashSet<RegistryObject<Item>> MODEL_SIMPLE_ITEMS = Sets.newLinkedHashSet();
    public static final LinkedHashSet<RegistryObject<Item>> MODEL_HANDHELD_ITEMS = Sets.newLinkedHashSet();
    public static final LinkedHashSet<RegistryObject<Item>> MODEL_BLOCK_ITEMS = Sets.newLinkedHashSet();
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
    public static RegistryObject<Item> registryItem(String name, Supplier<Item> supplier) {
        RegistryObject<Item> item = ITEMS.register(name, supplier);
        CREATIVE_TAB_ITEMS.add(item);
        return item;
    }

    public static RegistryObject<Item> registrySimpleItem(String name, Supplier<Item> supplier) {
        RegistryObject<Item> item = ITEMS.register(name, supplier);
        MODEL_SIMPLE_ITEMS.add(item);
        CREATIVE_TAB_ITEMS.add(item);
        return item;
    }
    public static RegistryObject<Item> registryHandheldItem(String name, Supplier<Item> supplier) {
        RegistryObject<Item> item = ITEMS.register(name, supplier);
        MODEL_HANDHELD_ITEMS.add(item);
        CREATIVE_TAB_ITEMS.add(item);
        return item;
    }
    public static RegistryObject<Item> registryBlockItem(String name, Supplier<Item> supplier) {
        RegistryObject<Item> item = ITEMS.register(name, supplier);
        MODEL_BLOCK_ITEMS.add(item);
        CREATIVE_TAB_ITEMS.add(item);
        return item;
    }

    public static final RegistryObject<Item> GREEN_TEA_LEAF = registrySimpleItem("green_tea_leaf", () -> new GreenTeaLeaf(new Item.Properties().food(FoodProperty.GREEN_TEA_LEAF)));
    public static final RegistryObject<Item> MAGIC_TEA_CHARGE = registrySimpleItem("magic_tea_charge", () -> new Item(new Item.Properties().stacksTo(16).rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> MAGIC_TEA_WAND = registryHandheldItem("magic_tea_wand", () -> new MagicTeaWand(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> BOXED_GREEN_TEA_LEAVES = registryBlockItem("boxed_green_tea_leaves", () -> new BlockItem(BlockRegistry.BOXED_GREEN_TEA_LEAVES.get(), new Item.Properties()));
    public static final RegistryObject<Item> MAGIC_TEA_BARREL = registryBlockItem("magic_tea_barrel", () -> new BlockItem(BlockRegistry.MAGIC_TEA_BARREL.get(), new Item.Properties()));
    public static final RegistryObject<Item> MAGIC_PURPLE_ORE = registryItem("magic_purple_ore", () -> new BlockItem(BlockRegistry.MAGIC_PURPLE_ORE.get(), new Item.Properties()));
    public static final RegistryObject<Item> RAW_MAGIC_PURPLE = registrySimpleItem("raw_magic_purple", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> MAGIC_PURPLE_INGOT = registrySimpleItem("magic_purple_ingot", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> MAGIC_PURPLE_NUGGET = registrySimpleItem("magic_purple_nugget", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> MAGIC_PURPLE_BLOCK = registryItem("magic_purple_block", () -> new BlockItem(BlockRegistry.MAGIC_PURPLE_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<Item> DEEPSLATE_MAGIC_PURPLE_ORE = registryItem("deepslate_magic_purple_ore", () -> new BlockItem(BlockRegistry.DEEPSLATE_MAGIC_PURPLE_ORE.get(), new Item.Properties()));
    public static final RegistryObject<Item> MAGIC_PURPLE_SWORD = registryHandheldItem("magic_purple_sword", () -> new SwordItem(TierProperty.MAGIC_PURPLE, 3 /*攻击伤害基础*/, -2.4f/*攻击速度加成*/, new Item.Properties()));
    public static final RegistryObject<Item> MAGIC_PURPLE_AXE = registryHandheldItem("magic_purple_axe", () -> new AxeItem(TierProperty.MAGIC_PURPLE, 6f, -3.1f, new Item.Properties()));
    public static final RegistryObject<Item> MAGIC_PURPLE_SHOVEL = registryHandheldItem("magic_purple_shovel", () -> new ShovelItem(TierProperty.MAGIC_PURPLE, 1.5f, -3.0f, new Item.Properties()));
    public static final RegistryObject<Item> MAGIC_PURPLE_HOE = registryHandheldItem("magic_purple_hoe", () -> new HoeItem(TierProperty.MAGIC_PURPLE, -2, -1f, new Item.Properties()));
    public static final RegistryObject<Item> MAGIC_PURPLE_PICKAXE = registryHandheldItem("magic_purple_pickaxe", () -> new PickaxeItem(TierProperty.MAGIC_PURPLE, 1, -2.8f, new Item.Properties()));

}
