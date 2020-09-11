package net.minecraft.block;

import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;

public class KelpTopBlock extends AbstractTopPlantBlock implements ILiquidContainer {
   protected static final VoxelShape SHAPE = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 9.0D, 16.0D);

   public KelpTopBlock(AbstractBlock.Properties builder) {
      super(builder, Direction.UP, SHAPE, true, 0.14D);
   }

   protected boolean func_230334_h_(BlockState p_230334_1_) {
      return p_230334_1_.isIn(Blocks.WATER);
   }

   protected Block func_230330_d_() {
      return Blocks.KELP_PLANT;
   }

   protected boolean func_230333_c_(Block p_230333_1_) {
      return p_230333_1_ != Blocks.MAGMA_BLOCK;
   }

   public boolean canContainFluid(IBlockReader worldIn, BlockPos pos, BlockState state, Fluid fluidIn) {
      return false;
   }

   public boolean receiveFluid(IWorld worldIn, BlockPos pos, BlockState state, FluidState fluidStateIn) {
      return false;
   }

   protected int func_230332_a_(Random p_230332_1_) {
      return 1;
   }

   @Nullable
   public BlockState getStateForPlacement(BlockItemUseContext context) {
      FluidState fluidstate = context.getWorld().getFluidState(context.getPos());
      return fluidstate.isTagged(FluidTags.WATER) && fluidstate.getLevel() == 8 ? super.getStateForPlacement(context) : null;
   }

   public FluidState getFluidState(BlockState state) {
      return Fluids.WATER.getStillFluidState(false);
   }
}