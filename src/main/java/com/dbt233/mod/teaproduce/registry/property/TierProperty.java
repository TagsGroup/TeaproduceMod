package com.dbt233.mod.teaproduce.registry.property;

import com.dbt233.mod.teaproduce.registry.ItemRegistry;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.ForgeTier;

public class TierProperty {
    public static final ForgeTier MAGIC_PURPLE =
            new ForgeTier(3, 250 /*耐久*/, 6f/*等于攻击速度基础*/, 2f /*攻击力翻倍数*/, 14,
                    BlockTags.NEEDS_STONE_TOOL, () -> Ingredient.of(ItemRegistry.MAGIC_PURPLE_INGOT.get()));
}
