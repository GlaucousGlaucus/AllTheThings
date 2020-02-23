package com.jacktheminecraftmodder.allm.base;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public enum SpecialAbilities {

    HEALTH_BOOST(),
    STRENGTH_BOOST();

    SpecialAbilities() {}

    public static void HealthBoost(PlayerEntity player, World world) {
        player.setHealth(20);
    }

}
