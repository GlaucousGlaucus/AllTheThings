package com.jacktheminecraftmodder.allm.base;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public abstract class RecipeBase implements IRecipe<IInventory> {

    protected final IRecipeType<?> type;
    protected final ResourceLocation id;

    public RecipeBase(IRecipeType<?> type, ResourceLocation id) {
        this.type = type;
        this.id = id;
    }

    @Override
    public IRecipeType<?> getType() {
        return type;
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public boolean isDynamic() {
        return true;
    }

    @Override
    public boolean matches(IInventory inv, World worldIn) {
        return true;
    }

    @Override
    public boolean canFit(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getRecipeOutput() {
        return ItemStack.EMPTY;
    }
}
