package net.minecraft.block;

import java.util.Optional;
import java.util.Random;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public abstract class AbstractBodyPlantBlock extends AbstractPlantBlock implements IGrowable {
   protected AbstractBodyPlantBlock(AbstractBlock.Properties p_i241179_1_, Direction p_i241179_2_, VoxelShape p_i241179_3_, boolean p_i241179_4_) {
      super(p_i241179_1_, p_i241179_2_, p_i241179_3_, p_i241179_4_);
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

      AbstractTopPlantBlock abstracttopplantblock = this.func_230331_c_();
      if (facing == this.field_235498_a_) {
         Block block = facingState.getBlock();
         if (block != this && block != abstracttopplantblock) {
            return abstracttopplantblock.func_235504_a_(worldIn);
         }
      }

      if (this.field_235499_b_) {
         worldIn.getPendingFluidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickRate(worldIn));
      }

      return super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
   }

   public ItemStack getItem(IBlockReader worldIn, BlockPos pos, BlockState state) {
      return new ItemStack(this.func_230331_c_());
   }

   /**
    * Whether this IGrowable can grow
    */
   public boolean canGrow(IBlockReader worldIn, BlockPos pos, BlockState state, boolean isClient) {
      Optional<BlockPos> optional = this.func_235501_b_(worldIn, pos, state);
      return optional.isPresent() && this.func_230331_c_().func_230334_h_(worldIn.getBlockState(optional.get().offset(this.field_235498_a_)));
   }

   public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, BlockState state) {
      return true;
   }

   public void grow(ServerWorld worldIn, Random rand, BlockPos pos, BlockState state) {
      Optional<BlockPos> optional = this.func_235501_b_(worldIn, pos, state);
      if (optional.isPresent()) {
         BlockState blockstate = worldIn.getBlockState(optional.get());
         ((AbstractTopPlantBlock)blockstate.getBlock()).grow(worldIn, rand, optional.get(), blockstate);
      }

   }

   private Optional<BlockPos> func_235501_b_(IBlockReader p_235501_1_, BlockPos p_235501_2_, BlockState p_235501_3_) {
      BlockPos blockpos = p_235501_2_;

      Block block;
      do {
         blockpos = blockpos.offset(this.field_235498_a_);
         block = p_235501_1_.getBlockState(blockpos).getBlock();
      } while(block == p_235501_3_.getBlock());

      return block == this.func_230331_c_() ? Optional.of(blockpos) : Optional.empty();
   }

   public boolean isReplaceable(BlockState state, BlockItemUseContext useContext) {
      boolean flag = super.isReplaceable(state, useContext);
      return flag && useContext.getItem().getItem() == this.func_230331_c_().asItem() ? false : flag;
   }

   protected Block func_230330_d_() {
      return this;
   }
}