package com.jacktheminecraftmodder.allm;

import net.minecraft.util.ResourceLocation;

public class Reference {
    public static final String MOD_ID = "allm";

    public static final ResourceLocation MYSTIC_SMELTING = rl("mystic_smelting");

    public static ResourceLocation rl(String path) {
        return new ResourceLocation(MOD_ID, path);
    }
}
