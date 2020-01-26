package com.jacktheminecraftmodder.allm.setup;

import com.jacktheminecraftmodder.allm.Reference;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ModSetup {

    public static final ItemGroup ALL_THE_THINGS = new ItemGroup("all_the_things") {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(Blocks.BEDROCK);
        }
    };

    public static void init(final FMLCommonSetupEvent event) {

    }

}
