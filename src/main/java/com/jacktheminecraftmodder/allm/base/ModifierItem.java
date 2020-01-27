package com.jacktheminecraftmodder.allm.base;

import com.jacktheminecraftmodder.allm.setup.ModSetup;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class ModifierItem extends Item {

    private final String description;
    private final TextFormatting format;

    public ModifierItem(String description, TextFormatting format) {
        super(new Item.Properties().group(ModSetup.ALL_THE_THINGS));
        this.description = description;
        this.format = format;
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        tooltip.add(new StringTextComponent(format + description));
    }
}
