package com.jacktheminecraftmodder.allm.base;

import com.google.common.collect.Maps;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

import java.util.*;

public class MysticValues implements MysticValue {

    public final int mystic_value;
    public final boolean isModifier;

    public MysticValues(int mystic_value, boolean isModifier) {
        this.mystic_value = mystic_value;
        this.isModifier = isModifier;
    }

    public static Map<Item, Integer> getMysticValues() {
        Map<Item, Integer> items = Maps.newLinkedHashMap();
        items.put(Items.DIAMOND, 2000);
        return items;
    }

    @Override
    public int getMysticValue() {
        return mystic_value;
    }

    @Override
    public boolean isModifier(ItemStack stack) {
        return isModifier;
    }
}
