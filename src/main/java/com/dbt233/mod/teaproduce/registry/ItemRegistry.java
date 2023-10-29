package com.dbt233.mod.teaproduce.registry;

import com.dbt233.mod.teaproduce.items.GreenTeaLeaf;
import com.dbt233.mod.teaproduce.items.MagicTeaWand;
import com.dbt233.mod.teaproduce.registry.property.FoodProperty;
import com.google.common.collect.Sets;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
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

    public static final RegistryObject<Item> MAGIC_PURPLE_ORE = registryBlockItem("magic_purple_ore", () -> new BlockItem(BlockRegistry.MAGIC_PURPLE_ORE.get(), new Item.Properties()));
}
