package com.jacktheminecraftmodder.allm.datagen;

import com.jacktheminecraftmodder.allm.Register;
import net.minecraft.advancements.criterion.InventoryChangeTrigger;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.RecipeProvider;
import net.minecraft.data.ShapedRecipeBuilder;

import java.util.function.Consumer;

public class Recipes extends RecipeProvider {

    public Recipes(DataGenerator generatorIn) {
        super(generatorIn);
    }

    @Override
    protected void registerRecipes(Consumer<IFinishedRecipe> consumer) {
        ShapedRecipeBuilder.shapedRecipe(Register.TEST_BLOCK.get())
                .patternLine("AAA")
                .patternLine("AAA")
                .patternLine("AAA")
                .key('A', Register.TEST_ITEM.get())
                .setGroup("")
                .addCriterion("test_item", InventoryChangeTrigger.Instance.forItems(Register.TEST_ITEM.get()))
                .build(consumer);

    }
}
