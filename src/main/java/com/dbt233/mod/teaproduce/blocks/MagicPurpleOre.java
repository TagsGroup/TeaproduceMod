package com.dbt233.mod.teaproduce.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class MagicPurpleOre extends Block {

    public MagicPurpleOre(Properties properties) {
        super(properties);
    }
    Random rand = new Random(System.currentTimeMillis());

    @Override
    public @NotNull InteractionResult
        use(BlockState blockState, Level level, BlockPos blockPos,
            Player player, InteractionHand interactionHand, BlockHitResult result) {
        float playerHealth = player.getHealth();
        float playerMaxHealth = player.getMaxHealth();
        if (playerHealth / playerMaxHealth < 0.5f
            || player.isCreative() || player.isInvulnerable()) {
            return InteractionResult.PASS;
        }
        if (level.isClientSide()) {
            return InteractionResult.SUCCESS;
        }
        player.giveExperiencePoints(20);
        int saveHurtDuration = player.hurtDuration;
        player.setHealth(playerHealth / 2);
        return InteractionResult.SUCCESS;
    }

    @Override
    public int getExpDrop(BlockState state, LevelReader level, RandomSource randomSource, BlockPos pos, int fortuneLevel, int silkTouchLevel) {
        return rand.nextInt(2);
    }
}
