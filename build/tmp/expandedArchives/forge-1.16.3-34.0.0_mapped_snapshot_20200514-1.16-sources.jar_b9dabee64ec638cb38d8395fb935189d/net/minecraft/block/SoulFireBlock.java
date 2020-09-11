package net.minecraft.block;

import net.minecraft.tags.BlockTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;

public class SoulFireBlock extends AbstractFireBlock {
   public SoulFireBlock(AbstractBlock.Properties p_i241187_1_) {
      super(p_i241187_1_, 2.0F);
   }

   /**
    * Update the provided state given the provided neighbor facing and neighbor state, returning a new state.
    * For example, fences make their connections to the passed in state if possible, and wet concrete powder immediately
    * returns its solidified counterpart.
    * Note that this method should ideally consider only the specific face passed in.
    */
   public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
      return this.isValidPosition(stateIn, worldIn, currentPos) ? this.getDefaultState() : Blocks.AIR.getDefaultState();
   }

   public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
      return func_235577_c_(worldIn.getBlockState(pos.down()).getBlock());
   }

   public static boolean func_235577_c_(Block p_235577_0_) {
      return p_235577_0_.isIn(BlockTags.field_232880_av_);
   }

   protected boolean canBurn(BlockState p_196446_1_) {
      return true;
   }
}