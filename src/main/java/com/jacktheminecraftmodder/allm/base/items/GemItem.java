package com.jacktheminecraftmodder.allm.base.items;

import com.jacktheminecraftmodder.allm.Register;
import com.jacktheminecraftmodder.allm.setup.ModSetup;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class GemItem extends Item {

    public GemItem() {
        super(new Properties().group(ModSetup.ALL_THE_THINGS).defaultMaxDamage(0));
    }

    @Override
    public boolean hasEffect(ItemStack stack) {
        return true;
    }

    @Override
    public boolean isDamageable() {
        return false;
    }

    @Override
    public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        super.inventoryTick(stack, worldIn, entityIn, itemSlot, isSelected);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        if (Screen.hasShiftDown()) {
            tooltip.add(new StringTextComponent(TextFormatting.LIGHT_PURPLE + "DO NOT LOSE THIS ITEM"));
        } else {
            tooltip.add(new StringTextComponent("Hold <Shift> for info"));
        }
        if (Screen.hasControlDown()) {
            tooltip.add(new StringTextComponent(TextFormatting.LIGHT_PURPLE + "Stat:"));
        } else {
            tooltip.add(new StringTextComponent("Hold <Ctrl> for Stats"));
        }
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        if (this == Register.FORTUNE_STONE.get()) {
            playerIn.addPotionEffect(new EffectInstance(Effects.SPEED, 100, 1, true, true));
        }
        if (this == Register.WEIRD_STONE.get()) {
            playerIn.addPotionEffect(new EffectInstance(Register.PARALYSED_EFFECT.get(), 100, 1, true, true));
        }
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }
}
