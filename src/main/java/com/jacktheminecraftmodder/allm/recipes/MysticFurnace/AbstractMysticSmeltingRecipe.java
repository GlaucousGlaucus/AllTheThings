package com.jacktheminecraftmodder.allm.recipes.MysticFurnace;

import com.jacktheminecraftmodder.allm.base.RecipeBase;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import java.util.function.BiPredicate;

public abstract class AbstractMysticSmeltingRecipe implements IRecipe<IInventory>, BiPredicate<ItemStack, ItemStack> {

    protected final IRecipeType<?> type;
    protected final ResourceLocation id;
    protected final String group;
    protected final Ingredient ingredient;
    protected final Ingredient modifier;
    protected final ItemStack result;
    protected final float experience;
    protected final int cookTime;

    public AbstractMysticSmeltingRecipe(IRecipeType<?> typeIn, ResourceLocation idIn, String groupIn, Ingredient ingredientIn, Ingredient modifierIn, ItemStack resultIn, float experienceIn, int cookTimeIn) {
        this.type = typeIn;
        this.id = idIn;
        this.group = groupIn;
        this.ingredient = ingredientIn;
        this.modifier = modifierIn;
        this.result = resultIn;
        this.experience = experienceIn;
        this.cookTime = cookTimeIn;
    }

    @Override
    public boolean test(ItemStack stack, ItemStack stack2) {
        return ingredient.test(stack) && modifier.test(stack2);
    }

    public boolean matches(IInventory inv, World worldIn) {
        return true;
    }

    @Override
    public ItemStack getCraftingResult(IInventory inv) {
        return this.result.copy();
    }

    public boolean canFit(int width, int height) {
        return true;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> nonnulllist = NonNullList.create();
        nonnulllist.add(this.ingredient);
        return nonnulllist;
    }

    public Ingredient getMainIngredient() {
        return ingredient;
    }

    public float getExperience() {
        return this.experience;
    }

    @Override
    public ItemStack getRecipeOutput() {
        return this.result;
    }

    @Override
    public String getGroup() {
        return this.group;
    }

    public int getCookTime() {
        return this.cookTime;
    }

    @Override
    public ResourceLocation getId() {
        return this.id;
    }

    @Override
    public IRecipeType<?> getType() {
        return this.type;
    }

}
