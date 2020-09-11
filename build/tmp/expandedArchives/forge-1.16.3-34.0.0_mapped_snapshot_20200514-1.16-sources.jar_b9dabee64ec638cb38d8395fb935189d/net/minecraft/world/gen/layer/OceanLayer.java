package net.minecraft.world.gen.layer;

import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.ImprovedNoiseGenerator;
import net.minecraft.world.gen.layer.traits.IAreaTransformer0;

public enum OceanLayer implements IAreaTransformer0 {
   INSTANCE;

   public int apply(INoiseRandom p_215735_1_, int p_215735_2_, int p_215735_3_) {
      ImprovedNoiseGenerator improvednoisegenerator = p_215735_1_.getNoiseGenerator();
      double d0 = improvednoisegenerator.func_215456_a((double)p_215735_2_ / 8.0D, (double)p_215735_3_ / 8.0D, 0.0D, 0.0D, 0.0D);
      if (d0 > 0.4D) {
         return 44;
      } else if (d0 > 0.2D) {
         return 45;
      } else if (d0 < -0.4D) {
         return 10;
      } else {
         return d0 < -0.2D ? 46 : 0;
      }
   }
}