package com.dbt233.mod.teaproduce.items;

import com.mojang.logging.LogUtils;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.slf4j.Logger;

import java.util.Random;

import static com.dbt233.mod.teaproduce.Utils.rand;

public class GreenTeaLeaf extends Item {
    private static final Logger LOGGER = LogUtils.getLogger();
    public GreenTeaLeaf(Properties properties) {
        super(properties);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack itemStack, Level level, LivingEntity livingEntity) {
        if (livingEntity instanceof Player) {
            Player player = (Player) livingEntity;
            int randn = Math.abs(rand.nextInt()) % 10;
            if (randn == 1) {
                player.giveExperiencePoints(1);
            }

        }
        return super.finishUsingItem(itemStack, level, livingEntity);
    }
}