package net.minecraft.entity.ai.attributes;

import net.minecraft.util.math.MathHelper;

public class RangedAttribute extends Attribute {
   private final double minimumValue;
   private final double maximumValue;

   public RangedAttribute(String p_i231504_1_, double p_i231504_2_, double p_i231504_4_, double p_i231504_6_) {
      super(p_i231504_1_, p_i231504_2_);
      this.minimumValue = p_i231504_4_;
      this.maximumValue = p_i231504_6_;
      if (p_i231504_4_ > p_i231504_6_) {
         throw new IllegalArgumentException("Minimum value cannot be bigger than maximum value!");
      } else if (p_i231504_2_ < p_i231504_4_) {
         throw new IllegalArgumentException("Default value cannot be lower than minimum value!");
      } else if (p_i231504_2_ > p_i231504_6_) {
         throw new IllegalArgumentException("Default value cannot be bigger than maximum value!");
      }
   }

   public double clampValue(double value) {
      return MathHelper.clamp(value, this.minimumValue, this.maximumValue);
   }
}