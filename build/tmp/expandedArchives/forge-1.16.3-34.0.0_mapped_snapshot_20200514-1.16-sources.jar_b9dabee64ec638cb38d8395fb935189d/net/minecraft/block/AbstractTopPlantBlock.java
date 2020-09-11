package net.minecraft.block;

import java.util.Random;
import net.minecraft.fluid.Fluids;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public abstract class AbstractTopPlantBlock extends AbstractPlantBlock implements IGrowable {
   public static final IntegerProperty field_235502_d_ = BlockStateProperties.AGE_0_25;
   private final double field_235503_e_;

   protected AbstractTopPlantBlock(AbstractBlock.Properties p_i241180_1_, Direction p_i241180_2_, VoxelShape p_i241180_3_, boolean p_i241180_4_, double p_i241180_5_) {
      super(p_i241180_1_, p_i241180_2_, p_i241180_3_, p_i241180_4_);
      this.field_235503_e_ = p_i241180_5_;
      this.setDefaultState(this.stateContainer.getBaseState().with(field_235502_d_, Integer.valueOf(0)));
   }

   public BlockState func_235504_a_(IWorld p_235504_1_) {
      return this.getDefaultState().with(field_235502_d_, Integer.valueOf(p_235504_1_.getRandom().nextInt(25)));
   }

   /**
    * Returns whether or not this block is of a type that needs random ticking. Called for ref-counting purposes by
    * ExtendedBlockStorage in order to broadly cull a chunk from the random chunk update list for efficiency's sake.
    */
   public boolean ticksRandomly(BlockState state) {
      return state.get(field_235502_d_) < 25;
   }

   /**
    * Performs a random tick on a block.
    */
   public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random) {
      if (state.get(field_235502_d_) < 25 && net.minecraftforge.common.ForgeHooks.onCropsGrowPre(worldIn, pos.offset(this.field_235498_a_), worldIn.getBlockState(pos.offset(this.field_235498_a_)),random.nextDouble() < this.field_235503_e_)) {
         BlockPos blockpos = pos.offset(this.field_235498_a_);
         if (this.func_230334_h_(worldIn.getBlockState(blockpos))) {
            worldIn.setBlockState(blockpos, state.func_235896_a_(field_235502_d_));
            net.minecraftforge.common.ForgeHooks.onCropsGrowPost(worldIn, blockpos, worldIn.getBlockState(blockpos));
         }
      }

   }

   /**
    * Update the provided state given the provided neighbor facing and neighbor state, returning a new state.
    * For example, fences make their connections to the passed in state if possible, and wet concrete powder immediately
    * returns its solidified counterpart.
    * Note that this method should ideally consider only the specific face passed in.
    */
   public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
      if (facing == this.field_235498_a_.getOpposite() && !stateIn.isValidPosition(worldIn, currentPos)) {
         worldIn.getPendingBlockTicks().scheduleTick(currentPos, this, 1);
      }

      if (facing != this.field_235498_a_ || !facingState.isIn(this) && !facingState.isIn(this.func_230330_d_())) {
         if (this.field_235499_b_) {
            worldIn.getPendingFluidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickRate(worldIn));
         }

         return super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
      } else {
         return this.func_230330_d_().getDefaultState();
      }
   }

   protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
      builder.add(field_235502_d_);
   }

   /**
    * Whether this IGrowable can grow
    */
   public boolean canGrow(IBlockReader worldIn, BlockPos pos, BlockState state, boolean isClient) {
      return this.func_230334_h_(worldIn.getBlockState(pos.offset(this.field_235498_a_)));
   }

   public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, BlockState state) {
      return true;
   }

   public void grow(ServerWorld worldIn, Random rand, BlockPos pos, BlockState state) {
      BlockPos blockpos = pos.offset(this.field_235498_a_);
      int i = Math.min(state.get(field_235502_d_) + 1, 25);
      int j = this.func_230332_a_(rand);

      for(int k = 0; k < j && this.func_230334_h_(worldIn.getBlockState(blockpos)); ++k) {
         worldIn.setBlockState(blockpos, state.with(field_235502_d_, Integer.valueOf(i)));
         blockpos = blockpos.offset(this.field_235498_a_);
         i = Math.min(i + 1, 25);
      }

   }

   protected abstract int func_230332_a_(Random p_230332_1_);

   protected abstract boolean func_230334_h_(BlockState p_230334_1_);

   protected AbstractTopPlantBlock func_230331_c_() {
      return this;
   }
}