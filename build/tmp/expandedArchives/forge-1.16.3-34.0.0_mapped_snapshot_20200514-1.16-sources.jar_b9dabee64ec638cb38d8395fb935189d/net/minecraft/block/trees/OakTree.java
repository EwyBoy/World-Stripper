package net.minecraft.block.trees;

import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Features;

public class OakTree extends Tree {
   /**
    * Get a {@link net.minecraft.world.gen.feature.ConfiguredFeature} of tree
    */
   @Nullable
   protected ConfiguredFeature<BaseTreeFeatureConfig, ?> getTreeFeature(Random randomIn, boolean p_225546_2_) {
      if (randomIn.nextInt(10) == 0) {
         return p_225546_2_ ? Features.field_243922_ce : Features.field_243869_bO;
      } else {
         return p_225546_2_ ? Features.field_243879_bY : Features.field_243862_bH;
      }
   }
}