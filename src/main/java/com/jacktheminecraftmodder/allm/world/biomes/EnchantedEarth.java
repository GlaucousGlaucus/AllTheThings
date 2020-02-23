package com.jacktheminecraftmodder.allm.world.biomes;

import net.minecraft.block.Blocks;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;

public class EnchantedEarth extends Biome {

    public EnchantedEarth() {
        super(new Builder()
                .surfaceBuilder(new ConfiguredSurfaceBuilder<SurfaceBuilderConfig>(SurfaceBuilder.DEFAULT,
                        new SurfaceBuilderConfig(Blocks.DIAMOND_BLOCK.getDefaultState(),
                                Blocks.MAGMA_BLOCK.getDefaultState(),
                                Blocks.EMERALD_ORE.getDefaultState())))
                .precipitation(RainType.RAIN)
                .category(Category.PLAINS)
                .downfall(0.9f)
                .depth(0.125F)
                .temperature(7.5f)
                .waterColor(0x7D0B98)
                .waterFogColor(0x7D0B98)
                .scale(0.05f)
                .parent(null)
        );

        DefaultBiomeFeatures.addCarvers(this);
        DefaultBiomeFeatures.addOres(this);
        DefaultBiomeFeatures.addLakes(this);
        DefaultBiomeFeatures.addMonsterRooms(this);
    }

}
