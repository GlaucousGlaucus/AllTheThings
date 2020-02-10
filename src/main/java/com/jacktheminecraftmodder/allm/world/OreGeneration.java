package com.jacktheminecraftmodder.allm.world;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.placement.CountRangeConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.registries.ForgeRegistries;

import static com.jacktheminecraftmodder.allm.Register.DRIED_LUCK_ORE;
import static com.jacktheminecraftmodder.allm.Register.ENCHANTED_EARTH_BIOME;
import static net.minecraft.world.gen.GenerationStage.Decoration.UNDERGROUND_ORES;
import static net.minecraft.world.gen.feature.Feature.ORE;
import static net.minecraft.world.gen.feature.OreFeatureConfig.FillerBlockType.NATURAL_STONE;

public class OreGeneration {

    public static void setupOreGen() {

        for(Biome biome : ForgeRegistries.BIOMES)
        {
                biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, DRIED_LUCK_ORE.get().getDefaultState(), 8)).func_227228_a_(Placement.COUNT_RANGE.func_227446_a_(new CountRangeConfig(10, 20, 0, 100))));
        }

        ENCHANTED_EARTH_BIOME.get().addFeature(UNDERGROUND_ORES, ORE.withConfiguration(new OreFeatureConfig(NATURAL_STONE, DRIED_LUCK_ORE.get().getDefaultState(), 8)).func_227228_a_(Placement.COUNT_RANGE.func_227446_a_(new CountRangeConfig(10, 20, 0, 100))));
    }

}
