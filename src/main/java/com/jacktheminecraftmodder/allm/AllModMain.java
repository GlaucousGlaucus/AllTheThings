package com.jacktheminecraftmodder.allm;

import com.jacktheminecraftmodder.allm.setup.*;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Tuple;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import top.theillusivec4.curios.api.CuriosAPI;
import top.theillusivec4.curios.api.imc.CurioIMCMessage;

@Mod("allm")
public class AllModMain
{

    public static final Logger LOGGER = LogManager.getLogger();
    public static IProxy Proxy = DistExecutor.runForDist(() -> () -> new ClientProxy(), () -> () -> new ServerProxy());
    private static boolean curiosLoaded = false;


    public AllModMain() {

        // IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, Config.CLIENT_CONFIG);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.COMMON_CONFIG);

        Register.init();

        FMLJavaModLoadingContext.get().getModEventBus().addListener(ModSetup::init);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(ClientSetup::init);

        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onEnqueueIMC);

        curiosLoaded = ModList.get().isLoaded("curios");

      //  RecipeSerializerInit.RECIPE_SERIALIZERS.register(modEventBus);
    }

    private void onEnqueueIMC(InterModEnqueueEvent event)
    {
        if(!curiosLoaded)
            return;

        //InterModComms.sendTo("curios", CuriosAPI.IMC.REGISTER_TYPE, () -> new CurioIMCMessage("allm").setSize(2));
        //InterModComms.sendTo("curios", CuriosAPI.IMC.REGISTER_ICON, () -> new Tuple<>("allm", new ResourceLocation(Reference.MOD_ID, "item/empty_backpack_slot")));
    }

}
