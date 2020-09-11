package net.minecraft.block.trees;

import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.server.ServerWorld;

public abstract class Tree {
   /**
    * Get a {@link net.minecraft.world.gen.feature.ConfiguredFeature} of tree
    */
   @Nullable
   protected abstract ConfiguredFeature<BaseTreeFeatureConfig, ?> getTreeFeature(Random randomIn, boolean p_225546_2_);

   public boolean func_230339_a_(ServerWorld p_230339_1_, ChunkGenerator p_230339_2_, BlockPos p_230339_3_, BlockState p_230339_4_, Random p_230339_5_) {
      ConfiguredFeature<BaseTreeFeatureConfig, ?> configuredfeature = this.getTreeFeature(p_230339_5_, this.func_230140_a_(p_230339_1_, p_230339_3_));
      if (configuredfeature == null) {
         return false;
      } else {
         p_230339_1_.setBlockState(p_230339_3_, Blocks.AIR.getDefaultState(), 4);
         configuredfeature.config.forcePlacement();
         if (configuredfeature.func_242765_a(p_230339_1_, p_230339_2_, p_230339_5_, p_230339_3_)) {
            return true;
         } else {
            p_230339_1_.setBlockState(p_230339_3_, p_230339_4_, 4);
            return false;
         }
      }
   }

   private boolean func_230140_a_(IWorld p_230140_1_, BlockPos p_230140_2_) {
      for(BlockPos blockpos : BlockPos.Mutable.getAllInBoxMutable(p_230140_2_.down().north(2).west(2), p_230140_2_.up().south(2).east(2))) {
         if (p_230140_1_.getBlockState(blockpos).func_235714_a_(BlockTags.FLOWERS)) {
            return true;
         }
      }

      return false;
   }
}