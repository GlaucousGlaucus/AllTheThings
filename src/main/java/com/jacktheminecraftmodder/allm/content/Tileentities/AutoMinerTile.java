package com.jacktheminecraftmodder.allm.content.Tileentities;

import com.google.common.collect.ImmutableSet;
import com.jacktheminecraftmodder.allm.Register;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Set;

public class AutoMinerTile extends TileEntity implements ITickableTileEntity {

    private static final Set<Block> MINEABLE = ImmutableSet.of(
            Blocks.ANDESITE,
            Blocks.COBBLESTONE,
            Blocks.GRASS_BLOCK,
            Blocks.COBBLESTONE,
            Blocks.STONE,
            Blocks.DIORITE,
            Blocks.GRANITE,
            Blocks.MOSSY_COBBLESTONE,
            /*Ores*/
            Blocks.COAL_ORE,
            Blocks.IRON_ORE,
            Blocks.REDSTONE_ORE,
            Blocks.LAPIS_ORE,
            Blocks.DIAMOND_ORE,
            Blocks.OBSIDIAN
    );

    private static final Set<Material> MINABLE_MATERIAL = ImmutableSet.of(
            Material.CLAY,
            Material.SAND,
            Material.EARTH,
            Material.GLASS,
            Material.GOURD,
            Material.ROCK,
            Material.IRON,
            Material.SAND
    );

    private int counter;
    private int y = this.getPos().getY();

    public AutoMinerTile() {
        super(Register.AUTO_MINER_TILE.get());
    }

    @Override
    public void tick() {
        if (world.isRemote) {
            return;
        }

        if (counter > 0) {
            counter--;
            if (counter <= 0) {
                breakBlockBelow(this.getPos());
            }
        }
    }

    private void breakBlockBelow(BlockPos pos) {
        World world = this.getWorld();
        if (!world.isRemote) {
            for (BlockPos pos1 : BlockPos.getAllInBoxMutable(pos.add(1, -1, 1), pos.add(-1, y * -1, -1))) {
                final BlockState blockState = world.getBlockState(pos1);
                do {
                    y++;
                    if (blockState.getBlock() == MINEABLE) {
                        world.destroyBlock(pos1, true);
                    } else {
                        return;
                    }
                } while (blockState.getBlock() != Blocks.BEDROCK);
                if (blockState.getBlock() != Blocks.BEDROCK) {
                    y = this.getPos().getY();
                }
            }
        }
    }

    private int getDistanceFromBedrock(BlockPos pos) {
        int y = pos.getY();
        int l = pos.getY() - 1;
        for (BlockPos pos1 : BlockPos.getAllInBoxMutable(pos.add(0, -1, 0), pos.add(0, y * -1, 0))) {
            BlockState blockState = world.getBlockState(pos1);
            if (blockState.getBlock() == Blocks.BEDROCK) {
                l = y * -1;
            } else {
                do {
                    y++;
                } while (l != y * -1);
            }
        }
        return l;
    }

    private boolean locatedChest() {
        boolean located = false;
        World world = this.getWorld();
        BlockPos pos = this.getPos();
        for (BlockPos pos1 : BlockPos.getAllInBoxMutable(pos.add(1, 0, 1), pos.add(-1, 1, -1))) {
            BlockState b = world.getBlockState(pos1);
            if (b.getBlock() == Blocks.CHEST) {
                located = true;
            }
        }
        return located;
    }


    @Override
    public void markDirty() {
        super.markDirty();
    }
}
