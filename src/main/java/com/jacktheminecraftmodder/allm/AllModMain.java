package com.jacktheminecraftmodder.allm;

import com.jacktheminecraftmodder.allm.config.Config;
import com.jacktheminecraftmodder.allm.setup.*;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod("allm")
public class AllModMain {

    public static final Logger LOGGER = LogManager.getLogger();
    public static IProxy Proxy = DistExecutor.runForDist(() -> () -> new ClientProxy(), () -> () -> new ServerProxy());


    public AllModMain() {

        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, Config.SERVER, "mystical_adventures_config_server_.toml");
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.CLIENT, "mystical_adventures_config_client_.toml");

        Register.init();

        FMLJavaModLoadingContext.get().getModEventBus().addListener(ModSetup::init);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(ClientSetup::init);
    }

}
