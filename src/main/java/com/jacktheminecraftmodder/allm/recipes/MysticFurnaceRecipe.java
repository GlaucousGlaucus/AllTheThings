package com.jacktheminecraftmodder.allm.recipes;

import com.google.gson.JsonObject;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.*;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nullable;

public class MysticFurnaceRecipe implements IRecipe<IInventory> {

    public static final Serializer SERIALIZER = new Serializer();
    public static final IRecipeType<MysticFurnaceRecipe> RECIPE_TYPE = new IRecipeType<MysticFurnaceRecipe>() {

    };
    protected final ResourceLocation id;
    protected Ingredient ingredient;
    protected ItemStack result;
    protected float experience;
    protected int cookTime;

    public MysticFurnaceRecipe(ResourceLocation idIn) {
        this.id = idIn;
    }

    public boolean matches(IInventory inv, World worldIn) {
        return this.ingredient.test(inv.getStackInSlot(0));
    }

    public ItemStack getCraftingResult(IInventory inv) {
        return this.result.copy();
    }

    /**
     * Used to determine if this recipe can fit in a grid of the given width/height
     */
    public boolean canFit(int width, int height) {
        return true;
    }

    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> nonnulllist = NonNullList.create();
        nonnulllist.add(this.ingredient);
        return nonnulllist;
    }

    /**
     * Gets the experience of this recipe
     */
    public float getExperience() {
        return this.experience;
    }

    /**
     * Get the result of this recipe, usually for display purposes (e.g. recipe book). If your recipe has more than one
     * possible result (e.g. it's dynamic and depends on its inputs), then return an empty stack.
     */
    public ItemStack getRecipeOutput() {
        return this.result;
    }

    public int getCookTime() {
        return cookTime;
    }

    public ResourceLocation getId() {
        return id;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return SERIALIZER;
    }

    public IRecipeType<?> getType() {
        return RECIPE_TYPE;
    }

    public static class Serializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<MysticFurnaceRecipe> {

        @Override
        public MysticFurnaceRecipe read(ResourceLocation recipeId, JsonObject json) {
            MysticFurnaceRecipe recipe = new MysticFurnaceRecipe(recipeId);
            recipe.result = ShapedRecipe.deserializeItem(JSONUtils.getJsonObject(json, "result"));
            recipe.cookTime = JSONUtils.getInt(json, "process_time", 50);
            recipe.experience = JSONUtils.getFloat(json, "xp", 0.0F);
            recipe.ingredient = Ingredient.deserialize(JSONUtils.getJsonObject(json, "ingredient"));
            return recipe;
        }

        @Nullable
        @Override
        public MysticFurnaceRecipe read(ResourceLocation recipeId, PacketBuffer buffer) {
            MysticFurnaceRecipe recipe = new MysticFurnaceRecipe(recipeId);
            recipe.cookTime = buffer.readVarInt();
            recipe.result = buffer.readItemStack();
            recipe.ingredient = Ingredient.read(buffer);
            return recipe;
        }

        @Override
        public void write(PacketBuffer buffer, MysticFurnaceRecipe recipe) {
            buffer.writeVarInt(recipe.cookTime);
            buffer.writeItemStack(recipe.result);
            buffer.writeFloat(recipe.experience);
            recipe.ingredient.write(buffer);
        }
    }

}
