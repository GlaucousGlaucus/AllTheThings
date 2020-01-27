package com.jacktheminecraftmodder.allm.init.list;

import com.jacktheminecraftmodder.allm.recipes.MysticFurnace.MysticFurnaceRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraftforge.registries.ObjectHolder;

public class RecipeList {
    @ObjectHolder("allm:mystic_smelting")
    public static IRecipeSerializer<MysticFurnaceRecipe> MYSTIC_SMELTING;
}
