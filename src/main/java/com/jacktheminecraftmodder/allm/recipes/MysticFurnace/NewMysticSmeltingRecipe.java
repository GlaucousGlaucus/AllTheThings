package com.jacktheminecraftmodder.allm.recipes.MysticFurnace;

import com.jacktheminecraftmodder.allm.Reference;
import com.jacktheminecraftmodder.allm.Register;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.*;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class NewMysticSmeltingRecipe implements IRecipe<IInventory> {

    public static final IRecipeType<NewMysticSmeltingRecipe> MYSTIC_SMELTING = new IRecipeType<NewMysticSmeltingRecipe>() {
        @Override
        public String toString() {
            return Reference.ALLOY_SMELTING.toString();
        }
    };

    protected float xp;
    protected Ingredient main;
    protected Ingredient modifier;
    private final ResourceLocation id;
    protected ItemStack result;
    protected int resultTime;

    public NewMysticSmeltingRecipe(ResourceLocation id) {
        this.id = id;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> nonnulllist = NonNullList.create();
        nonnulllist.add(this.main);
        nonnulllist.add(this.modifier);
        return nonnulllist;
    }

    @Override
    public ItemStack getIcon() {
        return new ItemStack(Register.MYSTIC_FURNACE.get());
    }

    public float getXp() {
        return xp;
    }

    public int getResultTime() {
        return resultTime;
    }

    @Override
    public boolean matches(IInventory inv, World worldIn) {
        return true;
    }

    private boolean matchHelper(ItemStack stack) {
        if (!stack.isEmpty()) {
            boolean match = false;
            for (Ingredient ingredient : getIngredients()) {
                if (ingredient.test(stack)) {
                    match = true;
                    break;
                }
            }
            if (!match) {
                return false;
            }
        }

        return true;
    }

    @Override
    public ItemStack getCraftingResult(IInventory inv) {
        return result.copy();
    }

    @Override
    public boolean canFit(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getRecipeOutput() {
        return result;
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return Register.MYSTIC_SMELTING_SERIALIZER.get();
    }

    @Override
    public IRecipeType<?> getType() {
        return MYSTIC_SMELTING;
    }
}
