package com.jacktheminecraftmodder.allm.content.Tileentities;

import com.google.common.collect.Maps;
import com.jacktheminecraftmodder.allm.Register;
import com.jacktheminecraftmodder.allm.content.containers.BioGeneratorContainer;
import com.jacktheminecraftmodder.allm.util.ModEnergyStorage;
import com.jacktheminecraftmodder.allm.util.Utility;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Map;

public class BioGenTileEntity extends TileEntity implements ITickableTileEntity, INamedContainerProvider {

    public static final int FUEL_SLOT = 0;
    private static final String INVENTORY_TAG = "inventory";
    private static final String ENERGY_TAG = "energy";

    public final ItemStackHandler inventory = new ItemStackHandler(1) {
        @Override
        public boolean isItemValid(final int slot, @Nonnull final ItemStack stack) {
            return  stack.getItem() == Items.ROTTEN_FLESH ||
                    stack.getItem() == Items.COAL ||
                    stack.getItem() == Items.POPPY ||
                    stack.getItem() == Items.WITHER_ROSE
                    ;
        }

        @Override
        protected void onContentsChanged(int slot) {
            super.onContentsChanged(slot);
            BioGenTileEntity.this.markDirty();
        }
    };
    public final ModEnergyStorage energy = new ModEnergyStorage(100_000);
    private final LazyOptional<ItemStackHandler> invCapabilityExternal = LazyOptional.of(() -> this.inventory);
    private final LazyOptional<EnergyStorage> energyCapabilityExternal = LazyOptional.of(() -> this.energy);
    private int lastEnergy = -1;

    public BioGenTileEntity() {
        super(Register.BIO_GEN_TILE.get());
    }

    @Override
    public void tick() {
        final World world = this.world;
        if (world == null || world.isRemote)
            return;

        final BlockPos pos = this.pos;
        final ModEnergyStorage energy = this.energy;
        final int slot = 0;
        int itemEnergy = 0;

        final ItemStack fuelStack = this.inventory.getStackInSlot(FUEL_SLOT);
        if (!fuelStack.isEmpty() && energy.getEnergyStored() != 100_000) {

            if (fuelStack.getItem() == Items.POPPY) {
                itemEnergy += 100;
                fuelStack.shrink(1);
            }
            else if (fuelStack.getItem() == Items.WITHER_ROSE) {
                itemEnergy += 1000;
                fuelStack.shrink(1);
            }

            if (itemEnergy > 0)
                energy.receiveEnergy(itemEnergy, false);
        }

        for (final BlockPos blockPos : BlockPos.getAllInBoxMutable(pos.add(-5, -5, -5), pos.add(5, 5, 5))) {
            final BlockState blockState = world.getBlockState(blockPos);
            // final IFluidState fluidState = world.getFluidState(blockPos);

            int blockMysticEnergy = 0;

            if (blockState.getBlock() == Blocks.ENCHANTING_TABLE)
                blockMysticEnergy += 100;
            else if (blockState.getBlock() == Blocks.WHEAT)
                blockMysticEnergy += 10;
            else if (blockState.getBlock() == Blocks.CARROTS)
                blockMysticEnergy += 20;
            else if (blockState.getBlock() == Blocks.POTATOES)
                blockMysticEnergy += 30;

            if (blockMysticEnergy > 0)
                energy.receiveEnergy(blockMysticEnergy, false);

        }

        final int transferAmountPerTick = 100;
        // Skip trying to transfer if there isn't enough energy to transfer
        if (energy.getEnergyStored() >= transferAmountPerTick) {
            for (Direction direction : Utility.DIRECTIONS) {
                final TileEntity te = world.getTileEntity(pos.offset(direction));
                if (te == null) {
                    continue;
                }
                te.getCapability(CapabilityEnergy.ENERGY, direction).ifPresent(otherTileEnergy -> {
                    if (!otherTileEnergy.canReceive()) {
                        // Optimisation: Skip this tile if it can't receive
                        return;
                    }
                    energy.extractEnergy(
                            otherTileEnergy.receiveEnergy(
                                    energy.extractEnergy(100, true),
                                    false
                            ),
                            false
                    );
                });
            }
        }

        // If the energy has changed.
        if (lastEnergy != energy.getEnergyStored()) {

            // "markDirty" tells vanilla that the chunk containing the tile entity has
            // changed and means the game will save the chunk to disk later.
            this.markDirty();

            // Notify clients of a block update.
            // This will result in the packet from getUpdatePacket being sent to the client
            // and our energy being synced.
            final BlockState blockState = this.getBlockState();
            // Flag 2: Send the change to clients
            world.notifyBlockUpdate(pos, blockState, blockState, 2);

            // Update the last synced energy to the current energy
            lastEnergy = energy.getEnergyStored();
        }
    }

    private void insertOrDropStack(final int slot, final ItemStack stack) {
        final boolean canInsertContainerItemIntoSlot = inventory.insertItem(slot, stack, true).isEmpty();
        if (canInsertContainerItemIntoSlot)
            inventory.insertItem(slot, stack, false);
        else
            InventoryHelper.spawnItemStack(world, pos.getX(), pos.getY(), pos.getZ(), stack);
    }


    public static Map<Item, Integer> getMysticValues() {
        Map<Item, Integer> map = Maps.newLinkedHashMap();
        addMysticValue(map, Items.WITHER_ROSE, 100);
        return map;
    }

    private static void addMysticValue(Map<Item, Integer> map, IItemProvider itemProvider, int mystic_valIn) {
        map.put(itemProvider.asItem(), mystic_valIn);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
            return invCapabilityExternal.cast();
        if (cap == CapabilityEnergy.ENERGY)
            return energyCapabilityExternal.cast();
        return super.getCapability(cap, side);
    }

    @Override
    public void onDataPacket(final NetworkManager net, final SUpdateTileEntityPacket pkt) {
        this.energy.setEnergy(pkt.getNbtCompound().getInt(ENERGY_TAG));
    }

    @Override
    public void onLoad() {
        super.onLoad();
        // We set this in onLoad instead of the constructor so that TileEntities
        // constructed from NBT (saved tile entities) have this set to the proper value
        if (world != null && !world.isRemote)
            lastEnergy = energy.getEnergyStored();
    }

    @Override
    public void read(final CompoundNBT compound) {
        super.read(compound);
        this.inventory.deserializeNBT(compound.getCompound(INVENTORY_TAG));
        this.energy.setEnergy(compound.getInt(ENERGY_TAG));
    }

    @Nonnull
    @Override
    public CompoundNBT write(final CompoundNBT compound) {
        super.write(compound);
        compound.put(INVENTORY_TAG, this.inventory.serializeNBT());
        compound.putInt(ENERGY_TAG, this.energy.getEnergyStored());
        return compound;
    }

    @Nullable
    public SUpdateTileEntityPacket getUpdatePacket() {
        final CompoundNBT tag = new CompoundNBT();
        tag.putInt(ENERGY_TAG, this.energy.getEnergyStored());
        return new SUpdateTileEntityPacket(this.pos, 0, tag);
    }

    @Nonnull
    public CompoundNBT getUpdateTag() {
        return this.write(new CompoundNBT());
    }

    @Override
    public void remove() {
        super.remove();
        // We need to invalidate our capability references so that any cached references (by other mods) don't
        // continue to reference our capabilities and try to use them and/or prevent them from being garbage collected
        invCapabilityExternal.invalidate();
        energyCapabilityExternal.invalidate();
    }

    @Nonnull
    @Override
    public ITextComponent getDisplayName() {
        return new TranslationTextComponent(Register.BIO_GENERATOR.get().getTranslationKey());
    }

    @Nonnull
    @Override
    public Container createMenu(final int windowId, final PlayerInventory inventory, final PlayerEntity player) {
        return new BioGeneratorContainer(windowId, inventory, this);
    }

}
