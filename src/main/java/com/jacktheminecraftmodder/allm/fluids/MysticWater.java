package com.jacktheminecraftmodder.allm.fluids;

import com.jacktheminecraftmodder.allm.Reference;
import com.jacktheminecraftmodder.allm.Register;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.IFluidState;
import net.minecraft.item.Item;
import net.minecraft.state.StateContainer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.ForgeFlowingFluid;

public abstract class MysticWater extends ForgeFlowingFluid {

    protected MysticWater() {
        super(new Properties(() -> Register.MYSTIC_WATER.get(),
                () -> Register.MYSTIC_WATER_FLOWING.get(),
                FluidAttributes.builder(new ResourceLocation(Reference.MOD_ID, "block/mystic_water"),
                        new ResourceLocation(Reference.MOD_ID, "block/mystic_water_flowing"))
                        .luminosity(15)
                        .sound(SoundEvents.ITEM_BUCKET_FILL, SoundEvents.ITEM_BUCKET_EMPTY)
                        .viscosity(1000)
                        .density(80)
                        .color(0x25fffa)
                        .temperature(7)
        )
                .block(() -> Register.MYSTIC_WATER_BLOCK.get()));
    }

    @Override
    protected boolean canSourcesMultiply() {
        return true;
    }

    @Override
    public Item getFilledBucket() {
        return Register.MYSTIC_WATER_BUCKET.get();
    }

    public static class Flowing extends MysticWater {

        public Flowing() {
        }

        @Override
        protected void fillStateContainer(StateContainer.Builder<Fluid, IFluidState> builder) {
            super.fillStateContainer(builder);
            builder.add(LEVEL_1_8);
        }

        @Override
        public boolean isSource(IFluidState state) {
            return false;
        }

        @Override
        public int getLevel(IFluidState state) {
            return state.get(LEVEL_1_8);
        }
    }

    public static class Source extends MysticWater {

        public Source() {

        }

        @Override
        public boolean isSource(IFluidState state) {
            return true;
        }

        @Override
        public int getLevel(IFluidState p_207192_1_) {
            return 8;
        }
    }
}
