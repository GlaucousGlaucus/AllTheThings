package com.jacktheminecraftmodder.allm.base.blocks;

import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.LockableTileEntity;
import net.minecraft.tileentity.TileEntityType;

public abstract class AbstractBaseTileEntityWithEnergy extends LockableTileEntity implements ITickableTileEntity {

    private static final String ENERGY_TAG = "energy";
    private int lastEnergy = -1;

    protected AbstractBaseTileEntityWithEnergy(TileEntityType<?> type) {
        super(type);
    }



}
