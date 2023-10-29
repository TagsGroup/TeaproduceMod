package com.dbt233.mod.teaproduce.items;

import net.minecraft.ChatFormatting;
import net.minecraft.core.GlobalPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class MagicTeaWand extends Item {

    public MagicTeaWand(Properties properties) {
        super(properties);
    }

    @Override
    public boolean isEnchantable(@NotNull ItemStack itemStack) {
        return true;
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level level, @NotNull Player player, @NotNull InteractionHand interactionHand) {
        if (level.isClientSide()) {
            return super.use(level, player, interactionHand);
        }
        ServerLevel serverLevel = level.getServer().getLevel(Level.OVERWORLD);
        ServerPlayer serverPlayer = player instanceof ServerPlayer ? (ServerPlayer) player : null;
        if (serverLevel != null && serverPlayer != null) {
            GlobalPos deathPos = serverPlayer.getLastDeathLocation().orElse(null);
            if (deathPos != null) {
                double x = deathPos.pos().getX()+0.5d, y = deathPos.pos().getY(), z = deathPos.pos().getZ()+0.5d;
                player.teleportTo(x, y, z);
                player.hurt(player.damageSources().fall(), 4.0f);
                player.sendSystemMessage(Component.translatable("chat.teaproduce.teleport_successfully",
                        Component.literal(String.valueOf(x)).withStyle(ChatFormatting.AQUA),
                        Component.literal(String.valueOf(y)).withStyle(ChatFormatting.AQUA),
                        Component.literal(String.valueOf(z)).withStyle(ChatFormatting.AQUA)
                ));
            } else {
                player.sendSystemMessage(Component.translatable("chat.teaproduce.teleport_unsuccessfully"));
                return InteractionResultHolder.fail(player.getItemInHand(interactionHand));
            }
        }
        return InteractionResultHolder.success(player.getItemInHand(interactionHand));
    }
}
