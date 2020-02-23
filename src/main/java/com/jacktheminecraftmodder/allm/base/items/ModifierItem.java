package com.jacktheminecraftmodder.allm.base.items;

import com.jacktheminecraftmodder.allm.base.IMysticValue;
import com.jacktheminecraftmodder.allm.setup.ModSetup;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class ModifierItem extends Item implements IMysticValue {

    public static boolean hasMysticValU;
    private final String description;
    private final TextFormatting format;

    public ModifierItem(String description, TextFormatting format, boolean hasMysticVal) {
        super(new Properties().group(ModSetup.ALL_THE_THINGS));
        this.description = description;
        this.format = format;
        hasMysticValU = hasMysticVal;
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        if (Screen.hasShiftDown()) {
            tooltip.add(new StringTextComponent(format + description));
        } else {
            tooltip.add(new StringTextComponent("Hold <Shift> For more Info"));
        }
    }

    @Override
    public boolean hasMysticValue() {
        return hasMysticValU;
    }
}
