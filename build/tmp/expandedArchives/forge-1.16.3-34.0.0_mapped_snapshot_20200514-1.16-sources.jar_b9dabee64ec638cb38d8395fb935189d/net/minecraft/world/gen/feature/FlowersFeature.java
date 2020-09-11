package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;

public abstract class FlowersFeature<U extends IFeatureConfig> extends Feature<U> {
   public FlowersFeature(Codec<U> p_i231922_1_) {
      super(p_i231922_1_);
   }

   public boolean func_241855_a(ISeedReader p_241855_1_, ChunkGenerator p_241855_2_, Random p_241855_3_, BlockPos p_241855_4_, U p_241855_5_) {
      BlockState blockstate = this.getFlowerToPlace(p_241855_3_, p_241855_4_, p_241855_5_);
      int i = 0;

      for(int j = 0; j < this.func_225560_a_(p_241855_5_); ++j) {
         BlockPos blockpos = this.getNearbyPos(p_241855_3_, p_241855_4_, p_241855_5_);
         if (p_241855_1_.isAirBlock(blockpos) && blockpos.getY() < 255 && blockstate.isValidPosition(p_241855_1_, blockpos) && this.func_225559_a_(p_241855_1_, blockpos, p_241855_5_)) {
            p_241855_1_.setBlockState(blockpos, blockstate, 2);
            ++i;
         }
      }

      return i > 0;
   }

   public abstract boolean func_225559_a_(IWorld p_225559_1_, BlockPos p_225559_2_, U p_225559_3_);

   public abstract int func_225560_a_(U p_225560_1_);

   public abstract BlockPos getNearbyPos(Random p_225561_1_, BlockPos p_225561_2_, U p_225561_3_);

   public abstract BlockState getFlowerToPlace(Random p_225562_1_, BlockPos p_225562_2_, U p_225562_3_);
}