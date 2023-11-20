package com.dbt233.mod.teaproduce.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

import static com.dbt233.mod.teaproduce.Utils.rand;

public class MagicPurpleOre extends Block {

    public MagicPurpleOre(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull InteractionResult
        use(BlockState blockState, Level level, BlockPos blockPos,
            Player player, InteractionHand interactionHand, BlockHitResult result) {
        float playerHealth = player.getHealth();
        float playerMaxHealth = player.getMaxHealth();
        if (player.getAbilities().instabuild) {
            return InteractionResult.PASS;
        }
        if (level.isClientSide()) {
            return InteractionResult.SUCCESS;
        }
        player.giveExperiencePoints(rand.nextInt(5, 10));
        if (rand.nextInt(0, 2) == 1) {
            level.destroyBlock(blockPos, true, player);
        }

        return InteractionResult.SUCCESS;
    }

    @Override
    public int getExpDrop(BlockState state, LevelReader level, RandomSource randomSource, BlockPos pos, int fortuneLevel, int silkTouchLevel) {
        return rand.nextInt(2);
    }
}
