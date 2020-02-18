package com.jacktheminecraftmodder.allm.setup;

import com.jacktheminecraftmodder.allm.Reference;
import com.jacktheminecraftmodder.allm.Register;
import com.jacktheminecraftmodder.allm.client.gui.BioGenScreen;
import com.jacktheminecraftmodder.allm.client.gui.ElectricAlloyFurnaceScreen;
import com.jacktheminecraftmodder.allm.client.gui.MysticFurnaceScreen;
import net.minecraft.client.gui.ScreenManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientSetup {

    public static void init(final FMLClientSetupEvent event) {

        // TileEntity Rendering Registrations goes here

        // Screen Registrations goes here

        ScreenManager.registerFactory(Register.BIO_GENERATOR_CONTAINER.get(), BioGenScreen::new);
        ScreenManager.registerFactory(Register.ELECTRIC_ALLOY_FURNACE_CONTAINER.get(), ElectricAlloyFurnaceScreen::new);
        ScreenManager.registerFactory(Register.MYSTIC_FURNACE_CONTAINER.get(), MysticFurnaceScreen::new);

        // Entity Registrations goes here

    }

    @SubscribeEvent
    public static void onItemColor(ColorHandlerEvent.Item event) {
       // event.getItemColors().register((stack, i) -> 0xff0000, Registration.WEIRDMOB_EGG.get());
    }

}
