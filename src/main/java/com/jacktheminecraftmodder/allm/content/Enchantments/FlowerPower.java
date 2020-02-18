package com.jacktheminecraftmodder.allm.content.Enchantments;

import com.jacktheminecraftmodder.allm.Reference;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.jacktheminecraftmodder.allm.Register.FLOWER_POWER;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
public class FlowerPower extends Enchantment {
    public FlowerPower(EquipmentSlotType[] slots) {
        super(Rarity.VERY_RARE, EnchantmentType.BREAKABLE, slots);
    }

    @Override
    public int getMinEnchantability(int enchantmentLevel) {
        return 1;
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
        return 4;
    }

    @Override
    protected boolean canApplyTogether(Enchantment ench) {
        return super.canApplyTogether(ench) && ench != Enchantments.MENDING || ench != Enchantments.INFINITY;
    }

    private static final EquipmentSlotType[] list = new EquipmentSlotType[]{EquipmentSlotType.HEAD,
            EquipmentSlotType.CHEST, EquipmentSlotType.LEGS, EquipmentSlotType.FEET,
            EquipmentSlotType.MAINHAND, EquipmentSlotType.OFFHAND};


    private static boolean isMystic(BlockPos pos, World worldIn, PlayerEntity player, int level) {
        boolean a = false;
        float f = (float)Math.min(16, 2 + level);
        for (BlockPos blockpos : BlockPos.getAllInBoxMutable(pos.add(5.0D, 0.0D, 5.0D), pos.add(-5.0D, 0.0D, -5.0D))) {
             if (blockpos.withinDistance(player.getPositionVec(), 5.0D)) {
                BlockState blockState = worldIn.getBlockState(blockpos);
                if (blockState.getMaterial() == Material.PLANTS)
                    a = true;
            }
        }
        return a;
    }

    @SubscribeEvent
    public static void repairItem(TickEvent.PlayerTickEvent event) {
        PlayerEntity p = event.player;
        if (p.world.isRemote)return;
        for (EquipmentSlotType slot : list) {
            ItemStack stack = p.getItemStackFromSlot(slot);
            int level = EnchantmentHelper.getEnchantmentLevel(FLOWER_POWER.get(), stack);
            if (level == 0) continue;
            if (isMystic(p.getPosition(), p.world, p, level) && stack.isDamaged())
                stack.setDamage(stack.getDamage()-level);
        }
    }
}
