package com.jacktheminecraftmodder.allm.world.trees;/*package com.jacktheminecraftmodder.allm.world.trees;

import com.jacktheminecraftmodder.allm.Register;
import net.minecraft.block.trees.Tree;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;

import javax.annotation.Nullable;
import java.util.Random;

public class WishingTree extends Tree {

    public WishingTree(){}

    @Nullable
    @Override
    protected ConfiguredFeature<TreeFeatureConfig, ?> getTreeFeature(Random random, boolean b) {
        return random.nextInt(10) == 0 ? Register.WISHING_TREE_FEATURE.withConfiguration(b ? DefaultBiomeFeatures.FANCY_TREE_WITH_MORE_BEEHIVES_CONFIG : DefaultBiomeFeatures.FANCY_TREE_CONFIG) : Feature.NORMAL_TREE.withConfiguration(b ? DefaultBiomeFeatures.OAK_TREE_WITH_MORE_BEEHIVES_CONFIG : DefaultBiomeFeatures.OAK_TREE_CONFIG);
    }
}*/
