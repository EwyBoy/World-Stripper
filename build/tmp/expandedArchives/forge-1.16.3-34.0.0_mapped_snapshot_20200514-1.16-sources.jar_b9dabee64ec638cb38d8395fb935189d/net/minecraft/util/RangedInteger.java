package net.minecraft.util;

import java.util.Random;

public class RangedInteger {
   private final int field_233014_a_;
   private final int field_233015_b_;

   public RangedInteger(int p_i231439_1_, int p_i231439_2_) {
      if (p_i231439_2_ < p_i231439_1_) {
         throw new IllegalArgumentException("max must be >= minInclusive! Given minInclusive: " + p_i231439_1_ + ", Given max: " + p_i231439_2_);
      } else {
         this.field_233014_a_ = p_i231439_1_;
         this.field_233015_b_ = p_i231439_2_;
      }
   }

   public static RangedInteger func_233017_a_(int p_233017_0_, int p_233017_1_) {
      return new RangedInteger(p_233017_0_, p_233017_1_);
   }

   public int func_233018_a_(Random p_233018_1_) {
      return this.field_233014_a_ == this.field_233015_b_ ? this.field_233014_a_ : p_233018_1_.nextInt(this.field_233015_b_ - this.field_233014_a_ + 1) + this.field_233014_a_;
   }

   public int func_233016_a_() {
      return this.field_233014_a_;
   }

   public int func_233019_b_() {
      return this.field_233015_b_;
   }

   public String toString() {
      return "IntRange[" + this.field_233014_a_ + "-" + this.field_233015_b_ + "]";
   }
}