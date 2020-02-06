package com.jacktheminecraftmodder.allm.setup;

import com.jacktheminecraftmodder.allm.Reference;
import com.jacktheminecraftmodder.allm.Register;
import com.jacktheminecraftmodder.allm.commands.ModCommands;
import com.jacktheminecraftmodder.allm.network.Networking;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ModSetup {

    public static final ItemGroup ALL_THE_THINGS = new ItemGroup("all_the_things") {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(Blocks.BEDROCK);
        }

        @Override
        public int getSlotColor() {
            return 0x246345;
        }
    };

    public static void init(final FMLCommonSetupEvent event) {
        Networking.registerMessages();
    }

    @SubscribeEvent
    public static void serverLoad(FMLServerStartingEvent event) {
        ModCommands.register(event.getCommandDispatcher());
    }

}
