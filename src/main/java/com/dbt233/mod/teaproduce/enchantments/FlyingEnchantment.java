package com.dbt233.mod.teaproduce.enchantments;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class FlyingEnchantment extends Enchantment {

    private static final int maxLvl = 3;
    private static final double scale = 1.5;
    public FlyingEnchantment(Rarity rarity, EnchantmentCategory enchantmentCategory, EquipmentSlot... equipmentSlots) {
        super(rarity, enchantmentCategory, equipmentSlots);
    }

    @Override
    public int getMaxLevel() {
        return maxLvl;
    }

    @Override
    public void doPostAttack(LivingEntity entity, Entity target, int lvl) {
        if (!entity.level.isClientSide()) {
            target.setPos(target.getX(), target.getY() + lvl * scale, target.getZ());
        }
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        return true;
    }
}


