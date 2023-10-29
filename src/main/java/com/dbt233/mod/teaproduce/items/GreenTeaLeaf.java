package com.dbt233.mod.teaproduce.items;

import com.mojang.logging.LogUtils;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.slf4j.Logger;

import java.util.Random;

public class GreenTeaLeaf extends Item {
    private static final Logger LOGGER = LogUtils.getLogger();
    public static final Random random = new Random();
    public GreenTeaLeaf(Properties properties) {
        super(properties);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack itemStack, Level level, LivingEntity livingEntity) {
        if (livingEntity instanceof Player) {
            Player player = (Player) livingEntity;
            int rand = Math.abs(random.nextInt()) % 10;
            if (rand == 1) {
                player.giveExperiencePoints(1);
            }

        }
        return super.finishUsingItem(itemStack, level, livingEntity);
    }
}