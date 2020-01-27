package com.jacktheminecraftmodder.allm.init;

import com.jacktheminecraftmodder.allm.AllModMain;
import com.jacktheminecraftmodder.allm.Reference;
import com.jacktheminecraftmodder.allm.init.list.RecipeList;
import com.jacktheminecraftmodder.allm.recipes.MysticFurnace.MysticFurnaceRecipe;
import com.jacktheminecraftmodder.allm.recipes.MysticFurnace.MysticSmeltingRecipeSerializer;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class RecipeInit {
    @Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {
        @SubscribeEvent
        public static void onRecipeRegistry(final RegistryEvent.Register<IRecipeSerializer<?>> event) {

            event.getRegistry().registerAll(
                    RecipeList.MYSTIC_SMELTING = new MysticSmeltingRecipeSerializer<>(MysticFurnaceRecipe::new, 50)
            );

            AllModMain.LOGGER.info("Recipe init -- Recipes Registered!");
        }
    }

    private static ResourceLocation location(String name)
    {
        return new ResourceLocation(Reference.MOD_ID, name);
    }

}
