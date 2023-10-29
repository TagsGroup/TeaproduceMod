package com.dbt233.mod.teaproduce.registry.property;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;

public class FoodProperty {
    public static final FoodProperties GREEN_TEA_LEAF = (new FoodProperties.Builder()).alwaysEat().fast().nutrition(1).saturationMod(0.3F).build();
}
