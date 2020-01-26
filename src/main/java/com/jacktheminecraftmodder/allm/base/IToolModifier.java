package com.jacktheminecraftmodder.allm.base;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;

public interface IToolModifier {

    String MysticValue(ItemStack tool, CompoundNBT mysticValue);

    boolean canApplyTogether(IToolModifier iToolModifier);

    boolean canApplyTogether(Enchantment enchantment);



}
