package com.dbt233.mod.teaproduce.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

import static com.dbt233.mod.teaproduce.Utils.MODID;
import static com.dbt233.mod.teaproduce.registry.ItemRegistry.*;

public class ItemModelDataProvider extends ItemModelProvider {
    public ItemModelDataProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        for (var item : MODEL_SIMPLE_ITEMS) {
            simpleItem(item);
        }
        for (var item : MODEL_HANDHELD_ITEMS) {
            handheldItem(item);
        }
        for (var item : MODEL_BLOCK_ITEMS) {
            blockItem(item);
        }
    }

    private ItemModelBuilder simpleItem(RegistryObject<Item> item) {
        String path = item.getId().getPath();
        return withExistingParent(path, new ResourceLocation("item/generated"))
                .texture("layer0", new ResourceLocation(MODID, "item/" + path));
    }

    private ItemModelBuilder handheldItem(RegistryObject<Item> item) {
        String path = item.getId().getPath();
        return withExistingParent(path, new ResourceLocation("item/handheld"))
                .texture("layer0", new ResourceLocation(MODID, "item/" + path));
    }

    private ItemModelBuilder blockItem(RegistryObject<Item> item) {
        String path = item.getId().getPath();
        return withExistingParent(path, new ResourceLocation(MODID, "block/" + path));
    }
}
