package com.jacktheminecraftmodder.allm.content.Potions;

import com.jacktheminecraftmodder.allm.init.list.EffectList;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraft.potion.Potion;

public class ParalysedEffect extends Effect {

    public ParalysedEffect(EffectType typeIn, int liquidColorIn) {
        super(typeIn, liquidColorIn);
    }

    @Override
    public void performEffect(LivingEntity entityLivingBaseIn, int amplifier) {
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        return super.isReady(duration, amplifier);
    }
}
