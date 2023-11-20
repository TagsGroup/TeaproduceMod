package com.dbt233.mod.teaproduce.items;

import com.dbt233.mod.teaproduce.registry.ItemRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Block;

import java.util.Random;

public class MagicTeaExpFinder extends Item {
    Random rand = new Random(System.currentTimeMillis());
    public MagicTeaExpFinder(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        if(context.getLevel().getBlockState(context.getClickedPos())
                .getBlock().getDescriptionId().contains("ore")) {
            Player player = context.getPlayer();
            if (player != null) {
                player.getCooldowns().addCooldown(this, 20);
                player.giveExperiencePoints(rand.nextInt(5, 20));
                if (rand.nextInt(0, 2) == 0) {
                    context.getItemInHand().shrink(1);
                    player.getInventory().add(new ItemStack(ItemRegistry.MAGIC_PURPLE_GEM.get()));
                }
                return InteractionResult.sidedSuccess(context.getLevel().isClientSide());
            }
        }
        return InteractionResult.FAIL;
    }
}
