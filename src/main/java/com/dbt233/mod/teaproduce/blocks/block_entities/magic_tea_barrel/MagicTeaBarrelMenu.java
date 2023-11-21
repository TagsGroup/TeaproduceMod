package com.dbt233.mod.teaproduce.blocks.block_entities.magic_tea_barrel;

import com.dbt233.mod.teaproduce.registry.ModMenuTypeRegistry;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class MagicTeaBarrelMenu extends AbstractContainerMenu {
    private final Container container;
    public static final int CON_ROWS = 9;
    public static final int COLS = 9;
    public static final int PLAYER_ROWS = 3;
    public static final int SLOT_A = 18;
    public static final int SIZE = CON_ROWS * CON_ROWS;
    public static final int xSize = MagicTeaBarrelScreen.textureXSize;
    public static final int ySize = MagicTeaBarrelScreen.textureYSize;

    public Container getContainer() {
        return this.container;
    }
    protected MagicTeaBarrelMenu(int containerId, Inventory playerInventory, Container container) {
        super(ModMenuTypeRegistry.MAGIC_TEA_BARREL.get(), containerId);
        checkContainerSize(container, SIZE);
        this.container = container;
        container.startOpen(playerInventory.player);
        int idx = 0;
        for (int row = 0; row < CON_ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                this.addSlot((new Slot(container, idx++, 8 + col * SLOT_A, SLOT_A + row * SLOT_A)));
            }
        }
        int i = (CON_ROWS - 4) * SLOT_A;
        for (int playerRow = 0; playerRow < PLAYER_ROWS; playerRow++) {
            for (int playerCol = 0; playerCol < COLS; playerCol++) {
                this.addSlot(new Slot(playerInventory, playerCol + playerRow * COLS + 9, 
                        8 + playerCol * SLOT_A, 103 + playerRow * SLOT_A + i));
            }
        }

        for (int hotbar = 0; hotbar < COLS; hotbar++) {
            this.addSlot(new Slot(playerInventory, hotbar, 8 + hotbar * 18, 161 + i));
        }
    }

    public static MagicTeaBarrelMenu createMagicTeaBarrelMenu(int id, Inventory playerInv, Container container) {
        return new MagicTeaBarrelMenu(id, playerInv, container);
    }

    public static MagicTeaBarrelMenu createMagicTeaBarrelMenu(int id, Inventory playerInv) {
        return new MagicTeaBarrelMenu(id, playerInv, new SimpleContainer(SIZE));
    }

    @Override
    public @NotNull ItemStack quickMoveStack(@NotNull Player player, int i) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(i);
        if (slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();
            if (i < SIZE) {
                if (!this.moveItemStackTo(itemstack1, SIZE, this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(itemstack1, 0, SIZE, false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.setByPlayer(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
        }

        return itemstack;
    }

    @Override
    public void removed(@NotNull Player player) {
        super.removed(player);
        this.container.stopOpen(player);

    }

    @Override
    public boolean stillValid(@NotNull Player player) {
        return this.container.stillValid(player);
    }
}
