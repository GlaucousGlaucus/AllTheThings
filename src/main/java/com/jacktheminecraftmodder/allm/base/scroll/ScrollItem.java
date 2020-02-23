package com.jacktheminecraftmodder.allm.base.scroll;

import com.jacktheminecraftmodder.allm.Register;
import com.jacktheminecraftmodder.allm.client.gui.ScrollScreen;
import com.jacktheminecraftmodder.allm.setup.ModSetup;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.*;
import net.minecraft.world.World;

public class ScrollItem extends Item {

    public ScrollItem() {
        super(new Properties().group(ModSetup.ALL_THE_THINGS));
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        if (this == Register.SCROLL.get()) {
            ScrollScreen.readScroll();
        } else if (this == Register.DUNGEON_SCROLL.get()) {

        }
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext context) {
        if (this == Register.DUNGEON_SCROLL.get()) {
            World world = context.getWorld();
            PlayerEntity playerEntity = context.getPlayer();
            double x = playerEntity.getPosX();
            double y = playerEntity.getPosY();
            double z = playerEntity.getPosZ();
            SoundEvent soundEvent = SoundEvents.ENTITY_ENDER_DRAGON_GROWL;
            world.playSound(x, y, z, soundEvent, SoundCategory.HOSTILE, 10.0F, 1, false);
        }
        return super.onItemUse(context);
    }

    @Override
    public boolean hasEffect(ItemStack stack) {
        return this == Register.DUNGEON_SCROLL.get();
    }
}
