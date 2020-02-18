package com.jacktheminecraftmodder.allm.content.Enchantments;

import com.jacktheminecraftmodder.allm.Reference;
import com.jacktheminecraftmodder.allm.Register;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.world.World;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
public class DeathWalkerEnchantment extends Enchantment {
    public DeathWalkerEnchantment(Rarity rarityIn, EquipmentSlotType... slots) {
        super(rarityIn, EnchantmentType.ARMOR_FEET, slots);
    }

    @Override
    public int getMinEnchantability(int enchantmentLevel) {
        return enchantmentLevel * 10;
    }

    @Override
    public int getMaxEnchantability(int enchantmentLevel) {
        return this.getMinEnchantability(enchantmentLevel) + 15;
    }

    @Override
    public boolean isTreasureEnchantment() {
        return true;
    }

    @Override
    public int getMaxLevel() {
        return 2;
    }

    @Override
    protected boolean canApplyTogether (Enchantment ench){
        return super.canApplyTogether(ench) && ench != Enchantments.FROST_WALKER;
    }

    //TODO : Make this work


    public static void FreezeLava(PlayerEntity playerEntity, BlockPos pos) {
       // PlayerEntity playerEntity = tickEvent.player;
        World worldIn = playerEntity.world;
        int level = EnchantmentHelper.getMaxEnchantmentLevel(Register.DEATH_WALKER.get(), playerEntity);
            if (playerEntity.onGround) {
                BlockState blockstate = Blocks.MAGMA_BLOCK.getDefaultState();
                float f = (float) Math.min(16, 2 + level);
                BlockPos.Mutable blockpos$mutable = new BlockPos.Mutable();
                for (BlockPos blockpos : BlockPos.getAllInBoxMutable(pos.add((double) (-f), -1.0D, (double) (-f)), pos.add((double) f, -1.0D, (double) f))) {
                    if (blockpos.withinDistance(playerEntity.getPositionVec(), (double) f)) {
                        blockpos$mutable.setPos(blockpos.getX(), blockpos.getY() + 1, blockpos.getZ());
                        BlockState blockstate1 = worldIn.getBlockState(blockpos$mutable);
                        if (blockstate1.isAir(worldIn, blockpos$mutable)) {
                            BlockState blockstate2 = worldIn.getBlockState(blockpos);
                            boolean isFull = blockstate2.getBlock() == Blocks.LAVA && blockstate2.get(FlowingFluidBlock.LEVEL) == 0; //TODO: Forge, modded waters?
                            if (blockstate2.getMaterial() == Material.LAVA && isFull && blockstate.isValidPosition(worldIn, blockpos) && worldIn.func_226663_a_(blockstate, blockpos, ISelectionContext.dummy()) && !net.minecraftforge.event.ForgeEventFactory.onBlockPlace(playerEntity, new net.minecraftforge.common.util.BlockSnapshot(worldIn, blockpos, blockstate2), net.minecraft.util.Direction.UP)) {
                                worldIn.setBlockState(blockpos, blockstate);
                                //  worldIn.getPendingBlockTicks().scheduleTick(blockpos, Blocks.FROSTED_ICE, MathHelper.nextInt(living.getRNG(), 60, 120));
                            }
                        }
                    }
                }
            }
    }

    @SubscribeEvent
    public static void LavaWalk(TickEvent.PlayerTickEvent tickEvent) {
        PlayerEntity playerEntity = tickEvent.player;
        int i = EnchantmentHelper.getMaxEnchantmentLevel(Register.DEATH_WALKER.get(), playerEntity);
        if (i > 0) {
            FreezeLava(playerEntity, playerEntity.getPosition());
        }
    }
}