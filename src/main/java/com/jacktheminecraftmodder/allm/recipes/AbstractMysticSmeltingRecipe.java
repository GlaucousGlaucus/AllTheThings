package com.jacktheminecraftmodder.allm.recipes;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public abstract class AbstractMysticSmeltingRecipe implements IRecipe<IInventory> {

    protected final IRecipeType<?> type;
    protected final ResourceLocation id;
    protected final String group;
    protected final Ingredient ingredient;
    protected final Ingredient modifier;
    protected final ItemStack result;
    protected final float experience;
    protected final int processTime;


    public AbstractMysticSmeltingRecipe(IRecipeType<?> typeIn, ResourceLocation idIn, String groupIn, Ingredient ingredientIn, Ingredient modifierIn,ItemStack resultIn, float experienceIn, int processTimeIn) {
        this.type = typeIn;
        this.id = idIn;
        this.group = groupIn;
        this.ingredient = ingredientIn;
        this.modifier = modifierIn;
        this.result = resultIn;
        this.experience = experienceIn;
        this.processTime = processTimeIn;
    }


    @Override
    public boolean matches(IInventory inv, World worldIn) {
        return this.ingredient.test(inv.getStackInSlot(0)) && this.modifier.test(inv.getStackInSlot(2));
    }


    @Override
    public ItemStack getCraftingResult(IInventory inv) {
        return this.result.copy();
    }


    public int getProcessTime() {
        return processTime;
    }


    public float getExperience() {
        return this.experience;
    }


    @Override
    public boolean canFit(int width, int height) {
        //Used to determine if this recipe can fit in a grid of the given width/height

        return true;
    }


    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> nonnulllist = NonNullList.create();
        nonnulllist.add(this.ingredient);
        return nonnulllist;
    }


    @Override
    public ItemStack getRecipeOutput() {
        return this.result;
    }


    @Override
    public ResourceLocation getId() {
        return this.id;
    }


    @Override
    public IRecipeType<?> getType() {
        return type;
    }

}
