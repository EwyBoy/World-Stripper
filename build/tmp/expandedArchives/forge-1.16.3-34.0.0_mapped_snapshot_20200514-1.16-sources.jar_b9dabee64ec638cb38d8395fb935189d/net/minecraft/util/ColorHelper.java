package net.minecraft.util;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ColorHelper {
   public static class PackedColor {
      @OnlyIn(Dist.CLIENT)
      public static int func_233004_a_(int p_233004_0_) {
         return p_233004_0_ >>> 24;
      }

      public static int func_233007_b_(int p_233007_0_) {
         return p_233007_0_ >> 16 & 255;
      }

      public static int func_233008_c_(int p_233008_0_) {
         return p_233008_0_ >> 8 & 255;
      }

      public static int func_233009_d_(int p_233009_0_) {
         return p_233009_0_ & 255;
      }

      @OnlyIn(Dist.CLIENT)
      public static int func_233006_a_(int p_233006_0_, int p_233006_1_, int p_233006_2_, int p_233006_3_) {
         return p_233006_0_ << 24 | p_233006_1_ << 16 | p_233006_2_ << 8 | p_233006_3_;
      }

      @OnlyIn(Dist.CLIENT)
      public static int func_233005_a_(int p_233005_0_, int p_233005_1_) {
         return func_233006_a_(func_233004_a_(p_233005_0_) * func_233004_a_(p_233005_1_) / 255, func_233007_b_(p_233005_0_) * func_233007_b_(p_233005_1_) / 255, func_233008_c_(p_233005_0_) * func_233008_c_(p_233005_1_) / 255, func_233009_d_(p_233005_0_) * func_233009_d_(p_233005_1_) / 255);
      }
   }
}