package net.minecraft.util;

import net.minecraft.util.text.Style;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@FunctionalInterface
public interface ICharacterConsumer {
   @OnlyIn(Dist.CLIENT)
   boolean accept(int p_accept_1_, Style p_accept_2_, int p_accept_3_);
}