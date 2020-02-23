package com.jacktheminecraftmodder.allm.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class OreGenConfig {

    public static ForgeConfigSpec.IntValue luck_ore_chance;
    public static ForgeConfigSpec.BooleanValue generate_overworld;
    public static ForgeConfigSpec.BooleanValue generate_nether;
    public static ForgeConfigSpec.BooleanValue generate_ench_earth;

    public static void init(ForgeConfigSpec.Builder server, ForgeConfigSpec.Builder client)
    {
        server.comment("Oregen Config");

        luck_ore_chance = server
                .comment("Maximum number of ore veins of the tutorial ore that can spawn in one chunk.")
                .defineInRange("oregen.tutorial_chance", 20, 1, 1000000);

        generate_overworld = server
                .comment("Decide if you want Tutorial Mod ores to spawn in the overworld")
                .define("oregen.generate_overworld", true);

        generate_nether = server
                .comment("Decide if you want Tutorial Mod ores to spawn in the nether")
                .define("oregen.generate_nether", false);

        generate_ench_earth = server
                .comment("Decide if you want Tutorial Mod ores to spawn in the Enchanted Earth")
                .define("oregen.generate_nether", false);
    }

}
