package com.jacktheminecraftmodder.allm.content.Tileentities;

import com.jacktheminecraftmodder.allm.Register;
import com.jacktheminecraftmodder.allm.base.ModifierItem;
import com.jacktheminecraftmodder.allm.content.containers.MysticFurnaceContainer;
import com.jacktheminecraftmodder.allm.recipes.MysticFurnace.AbstractMysticSmeltingRecipe;
import com.jacktheminecraftmodder.allm.recipes.MysticFurnace.MysticFurnaceRecipe;
import com.jacktheminecraftmodder.allm.util.ModEnergyStorage;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.RangedWrapper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Optional;

public class MysticFurnaceTile extends TileEntity implements ITickableTileEntity, INamedContainerProvider {

    public static final int INPUT_SLOT = 0;
    public static final int OUTPUT_SLOT = 1;
    public static final int MODIFIER_SLOT = 2;


    private static final String INVENTORY_TAG = "inventory";
    private static final String SMELT_TIME_LEFT_TAG = "smeltTimeLeft";
    private static final String MAX_SMELT_TIME_TAG = "maxSmeltTime";
    private static final String ENERGY_TAG = "energy";

    public short smeltTimeLeft = -1;
    public short maxSmeltTime = -1;
    private int lastEnergy = -1;

    public final ItemStackHandler inventory = new ItemStackHandler(3) {


        @Override
        public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
            switch (slot) {
                case INPUT_SLOT:
                    return isInput(stack);
                case MODIFIER_SLOT:
                    return isModifier(stack) && stack.getItem() instanceof ModifierItem;
                case OUTPUT_SLOT:
                    return isOutput(stack);
                default:
                    return false;
            }
        }

        @Override
        protected void onContentsChanged(final int slot) {
            super.onContentsChanged(slot);
            MysticFurnaceTile.this.markDirty();
        }
    };
    public final ModEnergyStorage energy = new ModEnergyStorage(100_000);


    private final LazyOptional<ItemStackHandler> inventoryCapabilityExternal = LazyOptional.of(() -> this.inventory);
    private final LazyOptional<IItemHandlerModifiable> inventoryCapabilityExternalUpAndSides = LazyOptional.of(() -> new RangedWrapper(this.inventory, INPUT_SLOT, INPUT_SLOT + 1));
    private final LazyOptional<IItemHandlerModifiable> inventoryCapabilityExternalDown = LazyOptional.of(() -> new RangedWrapper(this.inventory, OUTPUT_SLOT, OUTPUT_SLOT + 1));
    private final LazyOptional<EnergyStorage> energyCapabilityExternal = LazyOptional.of(() -> this.energy);

    public MysticFurnaceTile() {
        super(Register.MYSTIC_FURNACE_TILE.get());
    }


    private boolean isInput(final ItemStack stack) {
        if (stack.isEmpty())
            return false;
        return getRecipeForInput(stack).isPresent();
    }

    private boolean isModifier(final ItemStack stack) {
        if (stack.isEmpty())
            return false;
        return getRecipeForModifier(stack).isPresent();
    }

    /**
     * @return If the stack's item is equal to the result of smelting our input
     */
    private boolean isOutput(final ItemStack stack) {
        final Optional<ItemStack> result = getResult(inventory.getStackInSlot(INPUT_SLOT));
        return result.isPresent() && ItemStack.areItemsEqual(result.get(), stack);
    }

    /**
     * @return The smelting recipe for the input stack
     */
    private Optional<MysticFurnaceRecipe> getRecipeForInput(final ItemStack input) {
        return getRecipe(new Inventory(input));
    }

    /**
     * @return The smelting recipe for the modifier stack
     */
    private Optional<MysticFurnaceRecipe> getRecipeForModifier(final ItemStack modifier) {
        return getRecipe(new Inventory(modifier));
    }

    /**
     * @return The smelting recipe for the inventory
     */
    private Optional<MysticFurnaceRecipe> getRecipe(final IInventory inventory) {
        return world.getRecipeManager().getRecipe(MysticFurnaceRecipe.mystic_smelting , inventory, world);
    }

    private Optional<ItemStack> getResult(final ItemStack input) {
        final Inventory inventory = new Inventory(input);
        return getRecipe(inventory).map(recipe -> recipe.getCraftingResult(inventory));
    }

    @Override
    public void tick() {
        if (world == null || world.isRemote)
            return;
        final ItemStack input = inventory.getStackInSlot(INPUT_SLOT);
        final ItemStack modifier = inventory.getStackInSlot(MODIFIER_SLOT);
        final ItemStack result = getResult(input).orElse(ItemStack.EMPTY);

        if (!result.isEmpty() && isInput(input) && isInput(modifier)) {
            final boolean canInsertResultIntoOutput = inventory.insertItem(OUTPUT_SLOT, result, true).isEmpty();
            if (canInsertResultIntoOutput) {
                // Energy consuming code
                final int energySmeltCostPerTick = 5;
                boolean hasEnergy = false;
                if (energy.extractEnergy(energySmeltCostPerTick, true) == energySmeltCostPerTick) {
                    hasEnergy = true;
                    energy.extractEnergy(energySmeltCostPerTick, false);
                }
                if (hasEnergy) {
                    if (smeltTimeLeft == -1) { // Item has not been smelted before
                        smeltTimeLeft = maxSmeltTime = getSmeltTime(input);
                    } else { // Item was already being smelted
                        --smeltTimeLeft;
                        if (smeltTimeLeft == 0) {
                            inventory.insertItem(OUTPUT_SLOT, result, false);
                            if (input.hasContainerItem() && modifier.hasContainerItem()) {
                                final ItemStack Input = input.getContainerItem();
                                final ItemStack Modifier = modifier.getContainerItem();
                                input.shrink(1); // Shrink now to make space in the slot.
                                modifier.shrink(1);
                                insertOrDropStack(INPUT_SLOT, Input);
                                insertOrDropStack(MODIFIER_SLOT, Modifier);
                            } else {
                                input.shrink(1);
                                modifier.shrink(1);
                            }
                            inventory.setStackInSlot(INPUT_SLOT, input); // Update the data
                            inventory.setStackInSlot(MODIFIER_SLOT, modifier);
                            smeltTimeLeft = -1; // Set to -1 so we smelt the next stack on the next tick
                        }
                    }
                } else // No energy -> add to smelt time left to simulate cooling
                    if (smeltTimeLeft < maxSmeltTime)
                        ++smeltTimeLeft;
            }
        } else // We have an invalid input stack (somehow)
            smeltTimeLeft = maxSmeltTime = -1;

        if (lastEnergy != energy.getEnergyStored()) {
            this.markDirty();
            final BlockState blockState = this.getBlockState();
            world.notifyBlockUpdate(pos, blockState, blockState, 2);
            lastEnergy = energy.getEnergyStored();
        }
    }

    private void insertOrDropStack(final int slot, final ItemStack stack) {
        final boolean canInsertContainerItemIntoSlot = inventory.insertItem(slot, stack, true).isEmpty();
        if (canInsertContainerItemIntoSlot)
            inventory.insertItem(slot, stack, false);
        else // Drop the stack if we can't insert it
            InventoryHelper.spawnItemStack(world, pos.getX(), pos.getY(), pos.getZ(), stack);
    }

    private short getSmeltTime(final ItemStack input) {
        return getRecipeForInput(input)
                .map(AbstractMysticSmeltingRecipe::getCookTime)
                .orElse(100)
                .shortValue();
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull final Capability<T> cap, @Nullable final Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            if (side == null)
                return inventoryCapabilityExternal.cast();
            switch (side) {
                case DOWN:
                    return inventoryCapabilityExternalDown.cast();
                case UP:
                case NORTH:
                case SOUTH:
                case WEST:
                case EAST:
                    return inventoryCapabilityExternalUpAndSides.cast();
            }
        }
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
        if (world != null && !world.isRemote);
        lastEnergy = energy.getEnergyStored();
    }

    /**
     * Read saved data from disk into the tile.
     */
    @Override
    public void read(final CompoundNBT compound) {
        super.read(compound);
        this.inventory.deserializeNBT(compound.getCompound(INVENTORY_TAG));
        this.smeltTimeLeft = compound.getShort(SMELT_TIME_LEFT_TAG);
        this.maxSmeltTime = compound.getShort(MAX_SMELT_TIME_TAG);
        this.energy.setEnergy(compound.getInt(ENERGY_TAG));
    }

    /**
     * Write data from the tile into a compound tag for saving to disk.
     */
    @Nonnull
    @Override
    public CompoundNBT write(final CompoundNBT compound) {
        super.write(compound);
        compound.put(INVENTORY_TAG, this.inventory.serializeNBT());
        compound.putShort(SMELT_TIME_LEFT_TAG, this.smeltTimeLeft);
        compound.putShort(MAX_SMELT_TIME_TAG, this.maxSmeltTime);
        compound.putInt(ENERGY_TAG, this.energy.getEnergyStored());
        return compound;
    }

    /**
     * Retrieves packet to send to the client whenever this Tile Entity is re-synced via World#notifyBlockUpdate.
     * This packet comes back client-side in {@link #onDataPacket}
     */
    @Nullable
    public SUpdateTileEntityPacket getUpdatePacket() {
        final CompoundNBT tag = new CompoundNBT();
        tag.putInt(ENERGY_TAG, this.energy.getEnergyStored());
        //We pass 0 for tileEntityTypeIn because we have a modded TE. See ClientPlayNetHandler#handleUpdateTileEntity(SUpdateTileEntityPacket)
        return new SUpdateTileEntityPacket(this.pos, 0, tag);
    }

    @Nonnull
    public CompoundNBT getUpdateTag() {
        return this.write(new CompoundNBT());
    }

    /**
     * Invalidates our tile entity
     */
    @Override
    public void remove() {
        super.remove();
        inventoryCapabilityExternal.invalidate();
        energyCapabilityExternal.invalidate();
    }

    @Nonnull
    @Override
    public ITextComponent getDisplayName() {
        return new TranslationTextComponent(Register.MYSTIC_FURNACE.get().getTranslationKey());
    }

    @Nonnull
    @Override
    public Container createMenu(final int windowId, final PlayerInventory inventory, final PlayerEntity player) {
        return new MysticFurnaceContainer(windowId, inventory, this);
    }


}
