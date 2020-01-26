package com.jacktheminecraftmodder.allm.init;

import com.jacktheminecraftmodder.allm.AllModMain;
import com.jacktheminecraftmodder.allm.Reference;
import com.jacktheminecraftmodder.allm.init.list.EffectList;
import com.jacktheminecraftmodder.allm.init.list.PotionList;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class PotionInit {

    @Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {
        @SubscribeEvent
        public static void onEffectsRegistry(final RegistryEvent.Register<Potion> effectRegister) {

            effectRegister.getRegistry().registerAll(
                    PotionList.paralysed = new Potion(new EffectInstance(EffectList.paralysed, 9000)).setRegistryName(location("paralysed"))
            );

            AllModMain.LOGGER.info("Effect init -- All Effects Registered!");
        }
    }

    private static ResourceLocation location(String name)
    {
        return new ResourceLocation(Reference.MOD_ID, name);
    }

}
