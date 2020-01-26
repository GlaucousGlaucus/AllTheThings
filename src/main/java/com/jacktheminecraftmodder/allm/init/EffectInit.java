package com.jacktheminecraftmodder.allm.init;

import com.jacktheminecraftmodder.allm.AllModMain;
import com.jacktheminecraftmodder.allm.Reference;
import com.jacktheminecraftmodder.allm.content.Potions.ParalysedEffect;
import com.jacktheminecraftmodder.allm.init.list.EffectList;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class EffectInit {

    @Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {
        @SubscribeEvent
        public static void onEffectsRegistry(final RegistryEvent.Register<Effect> effectRegister) {

            effectRegister.getRegistry().registerAll(
                    EffectList.paralysed = new ParalysedEffect(EffectType.HARMFUL, 5578058).setRegistryName(location("paralysed"))
            );

            AllModMain.LOGGER.info("Effect init -- All Effects Registered!");
        }
    }

    private static ResourceLocation location(String name)
    {
        return new ResourceLocation(Reference.MOD_ID, name);
    }

}
