package com.jacktheminecraftmodder.allm.recipes;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.jacktheminecraftmodder.allm.Reference;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipe;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;

public class MysticSmeltingRecipeSerializer<T extends AbstractMysticSmeltingRecipe> extends net.minecraftforge.registries.ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<T> {

    private final int processTime;
    private final MysticSmeltingRecipeSerializer.IFactory<T> IAbstractVORecipeFactory;


    public MysticSmeltingRecipeSerializer(MysticSmeltingRecipeSerializer.IFactory<T> factoryIn, int processTimeIn) {
        this.processTime = processTimeIn;
        this.IAbstractVORecipeFactory = factoryIn;
        this.setRegistryName(Reference.MOD_ID, "mystic_smelting");
    }


    @Override
    public T read(ResourceLocation recipeId, JsonObject json) {

        //get the recipe group tag
        String s = JSONUtils.getString(json, "group", "");

        //get the ingredient for the recipe
        JsonElement jsonelement = (JsonElement)(JSONUtils.isJsonArray(json, "ingredient") ? JSONUtils.getJsonArray(json, "ingredient") : JSONUtils.getJsonObject(json, "ingredient"));
        Ingredient ingredient = Ingredient.deserialize(jsonelement);

        JsonElement jsonelement1 = (JsonElement)(JSONUtils.isJsonArray(json, "modifier") ? JSONUtils.getJsonArray(json, "modifier") : JSONUtils.getJsonObject(json, "modifier"));
        Ingredient modifier = Ingredient.deserialize(jsonelement1);

        //Forge: Check if primitive string to keep vanilla or a object which can contain a count field.
        if (!json.has("result")) throw new com.google.gson.JsonSyntaxException("Missing result, expected to find a string or object");

        //Get the result item and the amount of them
        ItemStack itemstack = ShapedRecipe.deserializeItem(JSONUtils.getJsonObject(json, "result"));

        //get the experience amount
        float f = JSONUtils.getFloat(json, "experience", 0.0F);

        //get the process time
        int i = JSONUtils.getInt(json, "processtime", this.processTime);

        return this.IAbstractVORecipeFactory.create(recipeId, s, ingredient, modifier, itemstack, f, i);
    }


    @Override
    public T read(ResourceLocation recipeId, PacketBuffer buffer) {

        String s = buffer.readString(32767);
        Ingredient ingredient = Ingredient.read(buffer);
        Ingredient modifier = Ingredient.read(buffer);
        ItemStack itemstack = buffer.readItemStack();
        float f = buffer.readFloat();
        int i = buffer.readVarInt();

        return this.IAbstractVORecipeFactory.create(recipeId, s, ingredient, modifier, itemstack, f, i);
    }


    @Override
    public void write(PacketBuffer buffer, T recipe) {

        buffer.writeString(recipe.group);
        recipe.ingredient.write(buffer);
        recipe.modifier.write(buffer);
        buffer.writeItemStack(recipe.result);
        buffer.writeFloat(recipe.experience);
        buffer.writeVarInt(recipe.processTime);
    }


    public interface IFactory<T extends AbstractMysticSmeltingRecipe> {

        T create(ResourceLocation resourceLocation, String s, Ingredient ingredient, Ingredient modifier, ItemStack itemStack, float experienceIn, int processTimeIn);
    }

}
