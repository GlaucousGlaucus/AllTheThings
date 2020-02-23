package com.jacktheminecraftmodder.allm.world;

import com.jacktheminecraftmodder.allm.config.OreGenConfig;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage.Decoration;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.placement.CountRangeConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.registries.ForgeRegistries;

import static com.jacktheminecraftmodder.allm.Register.DRIED_LUCK_ORE;
import static com.jacktheminecraftmodder.allm.Register.ENCHANTED_EARTH_BIOME;
import static com.jacktheminecraftmodder.allm.config.OreGenConfig.generate_ench_earth;
import static com.jacktheminecraftmodder.allm.config.OreGenConfig.generate_overworld;
import static net.minecraft.world.gen.feature.Feature.ORE;
import static net.minecraft.world.gen.feature.OreFeatureConfig.FillerBlockType.NATURAL_STONE;

public class OreGeneration {

    public static Block dried_luck_ore_block = DRIED_LUCK_ORE.get().getBlock();
    private static final BlockState dried_luck_ore = dried_luck_ore_block.getDefaultState();

    public static void setupOreGen() {

        if (generate_overworld.get()) {
            for (Biome biome : ForgeRegistries.BIOMES) {
                biome.addFeature(Decoration.UNDERGROUND_ORES, Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, dried_luck_ore, OreGenConfig.luck_ore_chance.get())).withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(10, 20, 0, 100))));
            }
        }

        if (generate_ench_earth.get()) {
            ENCHANTED_EARTH_BIOME.get().addFeature(Decoration.UNDERGROUND_ORES, ORE.withConfiguration(new OreFeatureConfig(NATURAL_STONE, DRIED_LUCK_ORE.get().getDefaultState(), OreGenConfig.luck_ore_chance.get())).withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(10, 20, 0, 100))));
        }
    }

}
