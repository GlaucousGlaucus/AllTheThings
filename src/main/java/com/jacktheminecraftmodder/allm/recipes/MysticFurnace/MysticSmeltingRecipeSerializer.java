package com.jacktheminecraftmodder.allm.recipes.MysticFurnace;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipe;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.ParametersAreNonnullByDefault;

public class MysticSmeltingRecipeSerializer<N> extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<NewMysticSmeltingRecipe> {

    private final int conversionTime;
    public MysticSmeltingRecipeSerializer(int processTimeIn) {
        this.conversionTime = processTimeIn;
    }

    @ParametersAreNonnullByDefault
    @Override
    public NewMysticSmeltingRecipe read(ResourceLocation recipeId, JsonObject json) {
        NewMysticSmeltingRecipe recipe = new NewMysticSmeltingRecipe(recipeId);
        JsonElement main = (JsonElement)(JSONUtils.isJsonArray(json, "main") ?
                JSONUtils.getJsonArray(json, "main") :
                JSONUtils.getJsonObject(json, "main"));
        Ingredient mainI = Ingredient.deserialize(main);
        JsonElement modifier = (JsonElement)(JSONUtils.isJsonArray(json, "modifier") ?
                JSONUtils.getJsonArray(json, "modifier") :
                JSONUtils.getJsonObject(json, "modifier"));
        Ingredient Modifier = Ingredient.deserialize(modifier);
        if (!json.has("result")) throw new com.google.gson.JsonSyntaxException("Missing result, expected to find a string or object");
        ItemStack itemstack = ShapedRecipe.deserializeItem(JSONUtils.getJsonObject(json, "result"));
        float f = JSONUtils.getFloat(json, "experience", 0.0F);
        int i = JSONUtils.getInt(json, "conversionTime", this.conversionTime);
        return recipe;
    }

    @ParametersAreNonnullByDefault
    @Override
    public NewMysticSmeltingRecipe read(ResourceLocation recipeId, PacketBuffer buffer) {
        NewMysticSmeltingRecipe recipe = new NewMysticSmeltingRecipe(recipeId);
        recipe.main = Ingredient.read(buffer);
        recipe.modifier = Ingredient.read(buffer);
        recipe.result = buffer.readItemStack();
        recipe.xp = buffer.readFloat();
        recipe.resultTime = buffer.readVarInt();
        return recipe;
    }

    @Override
    public void write(PacketBuffer buffer, NewMysticSmeltingRecipe recipe) {

        recipe.main.write(buffer);
        recipe.modifier.write(buffer);
        buffer.writeItemStack(recipe.result);
        buffer.writeFloat(recipe.xp);
        buffer.writeVarInt(recipe.resultTime);
    }

}
