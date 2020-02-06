package com.jacktheminecraftmodder.allm.recipes.MysticFurnace;

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
import net.minecraftforge.registries.ForgeRegistryEntry;

public class MysticSmeltingRecipeSerializer<T extends AbstractMysticSmeltingRecipe> extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<T> {

    private final int conversionTime;
    private final MysticSmeltingRecipeSerializer.IFactory<T> factory;
    public MysticSmeltingRecipeSerializer(MysticSmeltingRecipeSerializer.IFactory<T> factoryIn, int processTimeIn) {
        this.conversionTime = processTimeIn;
        this.factory = factoryIn;
        this.setRegistryName(Reference.MOD_ID, "mystic_smelting");
    }
    @Override
    public T read(ResourceLocation recipeId, JsonObject json) {

        //get the recipe group tag
        String s = JSONUtils.getString(json, "group", "");

        //get the ingredient for the recipe
        JsonElement main = (JsonElement)(JSONUtils.isJsonArray(json, "main") ?
                JSONUtils.getJsonArray(json, "main") :
                JSONUtils.getJsonObject(json, "main"));
        Ingredient mainI = Ingredient.deserialize(main);

        JsonElement modifier = (JsonElement)(JSONUtils.isJsonArray(json, "modifier") ?
                JSONUtils.getJsonArray(json, "modifier") :
                JSONUtils.getJsonObject(json, "modifier"));
        Ingredient Modifier = Ingredient.deserialize(modifier);

        //Forge: Check if primitive string to keep vanilla or a object which can contain a count field.
        if (!json.has("result")) throw new com.google.gson.JsonSyntaxException("Missing result, expected to find a string or object");

        //Get the result item and the amount of them
        ItemStack itemstack = ShapedRecipe.deserializeItem(JSONUtils.getJsonObject(json, "result"));

        //get the experience amount
        float f = JSONUtils.getFloat(json, "experience", 0.0F);

        //get the process time
        int i = JSONUtils.getInt(json, "conversionTime", this.conversionTime);

        return this.factory.create(recipeId, s, mainI, Modifier, itemstack, f, i);
    }
    @Override
    public T read(ResourceLocation recipeId, PacketBuffer buffer) {

        String s = buffer.readString(32767);
        Ingredient main = Ingredient.read(buffer);
        Ingredient modifier = Ingredient.read(buffer);
        ItemStack itemstack = buffer.readItemStack();
        float f = buffer.readFloat();
        int i = buffer.readVarInt();

        return this.factory.create(recipeId, s, main, modifier, itemstack, f, i);
    }
    @Override
    public void write(PacketBuffer buffer, T recipe) {

        buffer.writeString(recipe.group);
        recipe.ingredient.write(buffer);
        recipe.modifier.write(buffer);
        buffer.writeItemStack(recipe.result);
        buffer.writeFloat(recipe.experience);
        buffer.writeVarInt(recipe.cookTime);
    }
    public interface IFactory<T extends AbstractMysticSmeltingRecipe> {
        T create(ResourceLocation resourceLocation, String s, Ingredient ingredient, Ingredient modifier, ItemStack itemStack, float experienceIn, int processTimeIn);
    }

}
