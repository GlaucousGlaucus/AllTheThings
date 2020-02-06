package com.jacktheminecraftmodder.allm.util;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraftforge.energy.EnergyStorage;

import java.util.function.Predicate;

public class Utility {

    /**
     * Cache all the directions instead of calling Direction.values()
     * each time (because each call creates a new Direction[] which is wasteful)
     * TODO: change to Direction.VALUES once it's ATed
     */
    public static final Direction[] DIRECTIONS = Direction.values();

    /**
     * This method calculates a comparator output for how "full" the energy storage is.
     *
     * @param energy The energy storage to test.
     * @return A redstone value in the range [0,15] representing how "full" this energy storage is.
     */
    public static int calcRedstoneFromEnergyStorage(final EnergyStorage energy) {
        if (energy == null)
            return 0;
        return Math.round(energy.getEnergyStored() / ((float) energy.getMaxEnergyStored()) * 15F);
    }

    public static int getTotalCount(IInventory inventory, Predicate<ItemStack> ingredient) {
        int total = 0;
        for (int i = 0; i < inventory.getSizeInventory(); ++i) {
            ItemStack stack = inventory.getStackInSlot(i);
            if (!stack.isEmpty() && ingredient.test(stack)) {
                total += stack.getCount();
            }
        }
        return total;
    }

    public static void consumeItems(IInventory inventory, Predicate<ItemStack> ingredient, int amount) {
        int amountLeft = amount;
        for (int i = 0; i < inventory.getSizeInventory(); ++i) {
            ItemStack stack = inventory.getStackInSlot(i);
            if (!stack.isEmpty() && ingredient.test(stack)) {
                int toRemove = Math.min(amountLeft, stack.getCount());

                stack.shrink(toRemove);
                if (stack.isEmpty()) {
                    inventory.setInventorySlotContents(i, ItemStack.EMPTY);
                }

                amountLeft -= toRemove;
                if (amountLeft == 0) {
                    return;
                }
            }
        }
    }

}
