package com.jacktheminecraftmodder.allm.recipes.MysticFurnace;

import com.jacktheminecraftmodder.allm.Register;
import com.jacktheminecraftmodder.allm.init.list.RecipeList;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;

public class MysticFurnaceRecipe extends AbstractMysticSmeltingRecipe {

    public MysticFurnaceRecipe(ResourceLocation idIn, String groupIn, Ingredient ingredientIn, Ingredient modifierIn, ItemStack resultIn, float experienceIn, int cookTimeIn) {
        super(mystic_smelting, idIn, groupIn, ingredientIn, modifierIn, resultIn, experienceIn, cookTimeIn);
    }

    public static final IRecipeType<MysticFurnaceRecipe> mystic_smelting = IRecipeType.register("mystic_smelting");

    @Override
    public ItemStack getIcon() {
        return new ItemStack(Register.MYSTIC_FURNACE.get());
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return IRecipeSerializer.register("mystic_smelting", RecipeList.MYSTIC_SMELTING);
    }

}
