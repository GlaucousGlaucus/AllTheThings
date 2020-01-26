package com.jacktheminecraftmodder.allm.init;

import com.jacktheminecraftmodder.allm.AllModMain;
import com.jacktheminecraftmodder.allm.Reference;
import com.jacktheminecraftmodder.allm.content.Enchantments.DeathWalkerEnchantment;
import com.jacktheminecraftmodder.allm.init.list.EnchantmentList;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class EnchantmentInit {
    @Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {
        @SubscribeEvent
        public static void onEnchantmentRegistry(final RegistryEvent.Register<Enchantment> itemRegistryEvent) {

            itemRegistryEvent.getRegistry().registerAll(
                    EnchantmentList.death_walker = new DeathWalkerEnchantment(Enchantment.Rarity.VERY_RARE, EquipmentSlotType.FEET).setRegistryName(location("death_walker"))
            );

            AllModMain.LOGGER.info("E init -- Enchantments Registered!");
        }
    }

    private static ResourceLocation location(String name)
    {
        return new ResourceLocation(Reference.MOD_ID, name);
    }

}
