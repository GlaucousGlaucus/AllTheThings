package com.jacktheminecraftmodder.allm.content.containers.inventory;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public class MysticCraftingInventory implements IInventory {

    private final NonNullList<ItemStack> stackList;
    private final int width;
    private final int height;
    private final Container eventHandler;

    public MysticCraftingInventory(Container eventHandler, int width, int height) {
        this.stackList = NonNullList.withSize(width * height, ItemStack.EMPTY);
        this.width = width;
        this.height = height;
        this.eventHandler = eventHandler;
    }

    @Override
    public int getSizeInventory() {
        return this.stackList.size();
    }

    @Override
    public boolean isEmpty() {
        for (ItemStack itemStack : this.stackList) {
            if (!itemStack.isEmpty()){
                return false;
            }
        }

        return true;
    }

    @Override
    public ItemStack getStackInSlot(int index) {
        return index >= this.getSizeInventory() ? ItemStack.EMPTY : this.stackList.get(index) ;
    }

    @Override
    public ItemStack decrStackSize(int index, int count) {
        ItemStack itemstack = ItemStackHelper.getAndSplit(this.stackList, index, count);
        if (!itemstack.isEmpty()) {
            this.eventHandler.onCraftMatrixChanged(this);
        }

        return itemstack;
    }

    @Override
    public ItemStack removeStackFromSlot(int index) {
        return ItemStackHelper.getAndRemove(this.stackList, index);
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack stack) {

    }

    @Override
    public void markDirty() {

    }

    @Override
    public boolean isUsableByPlayer(PlayerEntity player) {
        return false;
    }

    @Override
    public void clear() {

    }
}
