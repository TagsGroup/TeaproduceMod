package com.dbt233.mod.teaproduce.enchantments;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class FallingThunderEnchantment extends Enchantment {

    private static final int maxLvl = 3;

    public FallingThunderEnchantment(Rarity p_44676_, EnchantmentCategory p_44677_, EquipmentSlot... p_44678_) {
        super(p_44676_, p_44677_, p_44678_);
    }

    @Override
    public int getMaxLevel() {
        return maxLvl;
    }

    @Override
    public void doPostAttack(LivingEntity entity, Entity target, int lvl) {
        if (!entity.level.isClientSide()) {
            ServerLevel world = (ServerLevel) entity.level;
            BlockPos blockPos = target.blockPosition();
            for (int i = 0; i < lvl*2; i++) {
                EntityType.LIGHTNING_BOLT.spawn(world, blockPos, MobSpawnType.TRIGGERED);
            }
        }
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        return true;
    }
}
