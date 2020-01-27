package com.jacktheminecraftmodder.allm.recipes.MysticFurnace;

import com.jacktheminecraftmodder.allm.Register;
import com.jacktheminecraftmodder.allm.init.list.RecipeList;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;

import java.util.function.BiPredicate;

public class MysticFurnaceRecipe extends AbstractMysticSmeltingRecipe {

    public MysticFurnaceRecipe(ResourceLocation idIn, String groupIn, Ingredient ingredientIn, Ingredient modifierIn, ItemStack resultIn, float experienceIn, int cookTimeIn) {
        super(mystic_smelting, idIn, groupIn, ingredientIn, modifierIn, resultIn, experienceIn, cookTimeIn);
    }

    public static final IRecipeType<MysticFurnaceRecipe> mystic_smelting = IRecipeType.register("mystic_smelting");

    public ItemStack getIcon() {
        return new ItemStack(Blocks.FURNACE);
    }

    public IRecipeSerializer<?> getSerializer() {
        return IRecipeSerializer.SMELTING;
    }

}
