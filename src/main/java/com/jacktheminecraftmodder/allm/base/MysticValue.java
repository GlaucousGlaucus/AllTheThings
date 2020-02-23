package com.jacktheminecraftmodder.allm.base;

import com.google.common.collect.Maps;
import com.jacktheminecraftmodder.allm.Register;
import net.minecraft.item.Item;
import net.minecraft.util.IItemProvider;

import java.util.Map;

public class MysticValue implements IMysticValue {

    public static int mysticValue;

    public MysticValue() {
    }

    public static void init() {
        System.out.println(GetMysticValue(Register.FORTUNE.get()));
    }

    public static int GetMysticValue(Item item) {
        return mysticValue = getMysticValues().get(item.getItem());
    }

    private static Map<Item, Integer> getMysticValues() {
        Map<Item, Integer> values = Maps.newLinkedHashMap();
        //Modifier
        setMysticValue(values, Register.FORTUNE.get(), 50);
        setMysticValue(values, Register.TEST_MODIFIER.get(), 50);
        return values;
    }

    private static void setMysticValue(Map<Item, Integer> map, IItemProvider itemProvider, int mystic_value) {
        map.put(itemProvider.asItem() , mystic_value);
    }

    @Override
    public boolean hasMysticValue() {
        return true;
    }
}
