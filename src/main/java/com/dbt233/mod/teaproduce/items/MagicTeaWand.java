package com.dbt233.mod.teaproduce.items;

import net.minecraft.ChatFormatting;
import net.minecraft.core.GlobalPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
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
        return false;
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level level, @NotNull Player player, @NotNull InteractionHand interactionHand) {
        var lastDeathLocation = player.getLastDeathLocation();
        if (lastDeathLocation.isEmpty()) {
            return super.use(level, player, interactionHand);
        }
        ResourceKey<Level> lastDimension = lastDeathLocation.get().dimension();
        if (level.isClientSide()) {
            return super.use(level, player, interactionHand);
        }



        if (lastDimension != player.getLevel().dimension()) {
            player.sendSystemMessage(Component
                    .translatable("chat.teaproduce.teleport_wrong_dimension",
                            Component.literal(lastDimension.location().toString())
                            .withStyle(ChatFormatting.AQUA)));
            return super.use(level, player, interactionHand);
        }


        ServerLevel serverLevel = level.getServer().getLevel(lastDimension);
        ServerPlayer serverPlayer = player instanceof ServerPlayer ? (ServerPlayer) player : null;
        if (serverLevel != null && serverPlayer != null) {
            GlobalPos deathPos = serverPlayer.getLastDeathLocation().orElse(null);
            if (deathPos == null) {
                player.sendSystemMessage(Component.translatable("chat.teaproduce.teleport_unsuccessfully"));
                return InteractionResultHolder.fail(player.getItemInHand(interactionHand));
            }
            double x = deathPos.pos().getX()+0.5d, y = deathPos.pos().getY(), z = deathPos.pos().getZ()+0.5d;
            player.teleportTo(x, y, z);
            player.sendSystemMessage(Component.translatable("chat.teaproduce.teleport_successfully",
                        Component.literal(String.valueOf(x)).withStyle(ChatFormatting.AQUA),
                        Component.literal(String.valueOf(y)).withStyle(ChatFormatting.AQUA),
                        Component.literal(String.valueOf(z)).withStyle(ChatFormatting.AQUA)
                ));
            if (!player.getAbilities().instabuild) {
                player.hurt(player.damageSources().fall(), 4.0f);
                player.getItemInHand(interactionHand).shrink(1);
                player.resetFallDistance();
            }
            player.getCooldowns().addCooldown(this, 20);

        }
        return InteractionResultHolder.success(player.getItemInHand(interactionHand));
    }
}
