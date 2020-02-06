package com.jacktheminecraftmodder.allm;

import com.jacktheminecraftmodder.allm.base.MysticValue;
import com.jacktheminecraftmodder.allm.base.MysticValues;
import com.jacktheminecraftmodder.allm.setup.*;
import com.jacktheminecraftmodder.allm.world.OreGeneration;
import net.minecraft.client.GameSettings;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.client.util.InputMappings;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Tuple;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import top.theillusivec4.curios.api.CuriosAPI;
import top.theillusivec4.curios.api.imc.CurioIMCMessage;

import java.util.ArrayList;
import java.util.List;

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

       // OreGeneration.setupOreGen();

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

    @SubscribeEvent
    public static void addTooltips(ItemTooltipEvent event) {
        if (event.getPlayer() != null && !event.getItemStack().isEmpty() && event.getItemStack().getItem() instanceof Item) {

            final List<ITextComponent> tooltip = event.getToolTip();
            final KeyBinding keyBindSneak = Minecraft.getInstance().gameSettings.keyBindSneak;

            // Check if the sneak key is pressed down. If so show the descriptions.
            if (InputMappings.isKeyDown(Minecraft.getInstance().getMainWindow().getHandle(), keyBindSneak.getKey().getKeyCode())) {

                final List<Item> enchants = getEnchantments(event.getItemStack());

                // Add descriptions for all the enchantments.
                for (final Item enchant : enchants) {

                    tooltip.add(new StringTextComponent(TextFormatting.GRAY + "tooltip.enchdesc.name" + ": "));
                }
            }

            // Let the player know they can press sneak to see stuff.
            else {
                tooltip.add(new StringTextComponent(TextFormatting.GRAY + I18n.format("tooltip.enchdesc.activate", TextFormatting.LIGHT_PURPLE, I18n.format(keyBindSneak.getTranslationKey()), TextFormatting.GRAY)));
            }
        }
    }

    /**
     * Get the list of enchantments from an enchanted book itemstack.
     *
     * @param stack The stack to get enchantments from.
     * @return The enchantments on the item.
     */
    private static List<Item> getEnchantments (ItemStack stack) {

        final List<Item> enchantments = new ArrayList<>();

        for (int i = 0; i < enchantments.size(); ++i) {
            final Item enchantment = ForgeRegistries.ITEMS.getValue(ResourceLocation.tryCreate("id"));
                enchantments.add(enchantment);
        }

        return enchantments;
    }

}
