package com.jacktheminecraftmodder.allm.base;

import com.jacktheminecraftmodder.allm.setup.ModSetup;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class ModifierItem extends Item implements MysticValue {

    private final Integer mystic_value;
    private final String description;
    private final TextFormatting format;

    public ModifierItem(int mystic_value, String description, TextFormatting format) {
        super(new Item.Properties().group(ModSetup.ALL_THE_THINGS));
        this.description = description;
        this.format = format;
        this.mystic_value = mystic_value;
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        if (Screen.hasShiftDown()) {
            tooltip.add(new StringTextComponent(format + description));
        } else {
            tooltip.add(new StringTextComponent("Hold <Shift> For more Info"));
        }
            tooltip.add(new StringTextComponent(TextFormatting.LIGHT_PURPLE + "Mystic Value :" + " " + mystic_value));

    }


    @Override
    public int getMysticValue() {
        return mystic_value;
    }

    @Override
    public boolean isModifier(ItemStack stack) {
        return true;
    }
}
