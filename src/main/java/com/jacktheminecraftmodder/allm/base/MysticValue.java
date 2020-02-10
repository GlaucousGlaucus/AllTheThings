package com.jacktheminecraftmodder.allm.base;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MinecartItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.ITeleporter;

import java.util.List;
import java.util.Map;

public interface MysticValue {

    int getMysticValue();

    boolean isModifier(ItemStack stack);

}
