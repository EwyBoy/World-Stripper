package net.minecraft.block;

import java.util.Random;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class NetherrackBlock extends Block implements IGrowable {
   public NetherrackBlock(AbstractBlock.Properties p_i241183_1_) {
      super(p_i241183_1_);
   }

   /**
    * Whether this IGrowable can grow
    */
   public boolean canGrow(IBlockReader worldIn, BlockPos pos, BlockState state, boolean isClient) {
      if (!worldIn.getBlockState(pos.up()).propagatesSkylightDown(worldIn, pos)) {
         return false;
      } else {
         for(BlockPos blockpos : BlockPos.getAllInBoxMutable(pos.add(-1, -1, -1), pos.add(1, 1, 1))) {
            if (worldIn.getBlockState(blockpos).func_235714_a_(BlockTags.field_232873_an_)) {
               return true;
            }
         }

         return false;
      }
   }

   public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, BlockState state) {
      return true;
   }

   public void grow(ServerWorld worldIn, Random rand, BlockPos pos, BlockState state) {
      boolean flag = false;
      boolean flag1 = false;

      for(BlockPos blockpos : BlockPos.getAllInBoxMutable(pos.add(-1, -1, -1), pos.add(1, 1, 1))) {
         BlockState blockstate = worldIn.getBlockState(blockpos);
         if (blockstate.isIn(Blocks.field_235372_ml_)) {
            flag1 = true;
         }

         if (blockstate.isIn(Blocks.field_235381_mu_)) {
            flag = true;
         }

         if (flag1 && flag) {
            break;
         }
      }

      if (flag1 && flag) {
         worldIn.setBlockState(pos, rand.nextBoolean() ? Blocks.field_235372_ml_.getDefaultState() : Blocks.field_235381_mu_.getDefaultState(), 3);
      } else if (flag1) {
         worldIn.setBlockState(pos, Blocks.field_235372_ml_.getDefaultState(), 3);
      } else if (flag) {
         worldIn.setBlockState(pos, Blocks.field_235381_mu_.getDefaultState(), 3);
      }

   }
}