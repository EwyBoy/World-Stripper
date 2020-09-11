package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.block.AbstractTopPlantBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;

public class WeepingVineFeature extends Feature<NoFeatureConfig> {
   private static final Direction[] field_236426_a_ = Direction.values();

   public WeepingVineFeature(Codec<NoFeatureConfig> p_i232004_1_) {
      super(p_i232004_1_);
   }

   public boolean func_241855_a(ISeedReader p_241855_1_, ChunkGenerator p_241855_2_, Random p_241855_3_, BlockPos p_241855_4_, NoFeatureConfig p_241855_5_) {
      if (!p_241855_1_.isAirBlock(p_241855_4_)) {
         return false;
      } else {
         BlockState blockstate = p_241855_1_.getBlockState(p_241855_4_.up());
         if (!blockstate.isIn(Blocks.NETHERRACK) && !blockstate.isIn(Blocks.NETHER_WART_BLOCK)) {
            return false;
         } else {
            this.func_236428_a_(p_241855_1_, p_241855_3_, p_241855_4_);
            this.func_236429_b_(p_241855_1_, p_241855_3_, p_241855_4_);
            return true;
         }
      }
   }

   private void func_236428_a_(IWorld p_236428_1_, Random p_236428_2_, BlockPos p_236428_3_) {
      p_236428_1_.setBlockState(p_236428_3_, Blocks.NETHER_WART_BLOCK.getDefaultState(), 2);
      BlockPos.Mutable blockpos$mutable = new BlockPos.Mutable();
      BlockPos.Mutable blockpos$mutable1 = new BlockPos.Mutable();

      for(int i = 0; i < 200; ++i) {
         blockpos$mutable.func_239621_a_(p_236428_3_, p_236428_2_.nextInt(6) - p_236428_2_.nextInt(6), p_236428_2_.nextInt(2) - p_236428_2_.nextInt(5), p_236428_2_.nextInt(6) - p_236428_2_.nextInt(6));
         if (p_236428_1_.isAirBlock(blockpos$mutable)) {
            int j = 0;

            for(Direction direction : field_236426_a_) {
               BlockState blockstate = p_236428_1_.getBlockState(blockpos$mutable1.func_239622_a_(blockpos$mutable, direction));
               if (blockstate.isIn(Blocks.NETHERRACK) || blockstate.isIn(Blocks.NETHER_WART_BLOCK)) {
                  ++j;
               }

               if (j > 1) {
                  break;
               }
            }

            if (j == 1) {
               p_236428_1_.setBlockState(blockpos$mutable, Blocks.NETHER_WART_BLOCK.getDefaultState(), 2);
            }
         }
      }

   }

   private void func_236429_b_(IWorld p_236429_1_, Random p_236429_2_, BlockPos p_236429_3_) {
      BlockPos.Mutable blockpos$mutable = new BlockPos.Mutable();

      for(int i = 0; i < 100; ++i) {
         blockpos$mutable.func_239621_a_(p_236429_3_, p_236429_2_.nextInt(8) - p_236429_2_.nextInt(8), p_236429_2_.nextInt(2) - p_236429_2_.nextInt(7), p_236429_2_.nextInt(8) - p_236429_2_.nextInt(8));
         if (p_236429_1_.isAirBlock(blockpos$mutable)) {
            BlockState blockstate = p_236429_1_.getBlockState(blockpos$mutable.up());
            if (blockstate.isIn(Blocks.NETHERRACK) || blockstate.isIn(Blocks.NETHER_WART_BLOCK)) {
               int j = MathHelper.nextInt(p_236429_2_, 1, 8);
               if (p_236429_2_.nextInt(6) == 0) {
                  j *= 2;
               }

               if (p_236429_2_.nextInt(5) == 0) {
                  j = 1;
               }

               int k = 17;
               int l = 25;
               func_236427_a_(p_236429_1_, p_236429_2_, blockpos$mutable, j, 17, 25);
            }
         }
      }

   }

   public static void func_236427_a_(IWorld p_236427_0_, Random p_236427_1_, BlockPos.Mutable p_236427_2_, int p_236427_3_, int p_236427_4_, int p_236427_5_) {
      for(int i = 0; i <= p_236427_3_; ++i) {
         if (p_236427_0_.isAirBlock(p_236427_2_)) {
            if (i == p_236427_3_ || !p_236427_0_.isAirBlock(p_236427_2_.down())) {
               p_236427_0_.setBlockState(p_236427_2_, Blocks.field_235384_mx_.getDefaultState().with(AbstractTopPlantBlock.field_235502_d_, Integer.valueOf(MathHelper.nextInt(p_236427_1_, p_236427_4_, p_236427_5_))), 2);
               break;
            }

            p_236427_0_.setBlockState(p_236427_2_, Blocks.field_235385_my_.getDefaultState(), 2);
         }

         p_236427_2_.move(Direction.DOWN);
      }

   }
}