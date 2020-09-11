package net.minecraft.block;

import java.util.Random;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.Features;
import net.minecraft.world.gen.feature.NetherVegetationFeature;
import net.minecraft.world.gen.feature.TwistingVineFeature;
import net.minecraft.world.lighting.LightEngine;
import net.minecraft.world.server.ServerWorld;

public class NyliumBlock extends Block implements IGrowable {
   public NyliumBlock(AbstractBlock.Properties p_i241184_1_) {
      super(p_i241184_1_);
   }

   private static boolean func_235516_b_(BlockState p_235516_0_, IWorldReader p_235516_1_, BlockPos p_235516_2_) {
      BlockPos blockpos = p_235516_2_.up();
      BlockState blockstate = p_235516_1_.getBlockState(blockpos);
      int i = LightEngine.func_215613_a(p_235516_1_, p_235516_0_, p_235516_2_, blockstate, blockpos, Direction.UP, blockstate.getOpacity(p_235516_1_, blockpos));
      return i < p_235516_1_.getMaxLightLevel();
   }

   /**
    * Performs a random tick on a block.
    */
   public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random) {
      if (!func_235516_b_(state, worldIn, pos)) {
         worldIn.setBlockState(pos, Blocks.NETHERRACK.getDefaultState());
      }

   }

   /**
    * Whether this IGrowable can grow
    */
   public boolean canGrow(IBlockReader worldIn, BlockPos pos, BlockState state, boolean isClient) {
      return worldIn.getBlockState(pos.up()).isAir();
   }

   public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, BlockState state) {
      return true;
   }

   public void grow(ServerWorld worldIn, Random rand, BlockPos pos, BlockState state) {
      BlockState blockstate = worldIn.getBlockState(pos);
      BlockPos blockpos = pos.up();
      if (blockstate.isIn(Blocks.field_235381_mu_)) {
         NetherVegetationFeature.func_236325_a_(worldIn, rand, blockpos, Features.Configs.field_243987_k, 3, 1);
      } else if (blockstate.isIn(Blocks.field_235372_ml_)) {
         NetherVegetationFeature.func_236325_a_(worldIn, rand, blockpos, Features.Configs.field_243988_l, 3, 1);
         NetherVegetationFeature.func_236325_a_(worldIn, rand, blockpos, Features.Configs.field_243989_m, 3, 1);
         if (rand.nextInt(8) == 0) {
            TwistingVineFeature.func_236423_a_(worldIn, rand, blockpos, 3, 1, 2);
         }
      }

   }
}