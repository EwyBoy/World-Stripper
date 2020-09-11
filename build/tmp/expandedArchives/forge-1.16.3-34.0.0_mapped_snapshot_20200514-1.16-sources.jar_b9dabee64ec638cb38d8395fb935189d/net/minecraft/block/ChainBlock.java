package net.minecraft.block;

import javax.annotation.Nullable;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;

public class ChainBlock extends RotatedPillarBlock implements IWaterLoggable {
   public static final BooleanProperty field_235484_b_ = BlockStateProperties.WATERLOGGED;
   protected static final VoxelShape field_242659_b = Block.makeCuboidShape(6.5D, 0.0D, 6.5D, 9.5D, 16.0D, 9.5D);
   protected static final VoxelShape field_242660_c = Block.makeCuboidShape(6.5D, 6.5D, 0.0D, 9.5D, 9.5D, 16.0D);
   protected static final VoxelShape field_242661_d = Block.makeCuboidShape(0.0D, 6.5D, 6.5D, 16.0D, 9.5D, 9.5D);

   public ChainBlock(AbstractBlock.Properties p_i241175_1_) {
      super(p_i241175_1_);
      this.setDefaultState(this.stateContainer.getBaseState().with(field_235484_b_, Boolean.valueOf(false)).with(AXIS, Direction.Axis.Y));
   }

   public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
      switch((Direction.Axis)state.get(AXIS)) {
      case X:
      default:
         return field_242661_d;
      case Z:
         return field_242660_c;
      case Y:
         return field_242659_b;
      }
   }

   @Nullable
   public BlockState getStateForPlacement(BlockItemUseContext context) {
      FluidState fluidstate = context.getWorld().getFluidState(context.getPos());
      boolean flag = fluidstate.getFluid() == Fluids.WATER;
      return super.getStateForPlacement(context).with(field_235484_b_, Boolean.valueOf(flag));
   }

   /**
    * Update the provided state given the provided neighbor facing and neighbor state, returning a new state.
    * For example, fences make their connections to the passed in state if possible, and wet concrete powder immediately
    * returns its solidified counterpart.
    * Note that this method should ideally consider only the specific face passed in.
    */
   public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
      if (stateIn.get(field_235484_b_)) {
         worldIn.getPendingFluidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickRate(worldIn));
      }

      return super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
   }

   protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
      builder.add(field_235484_b_).add(AXIS);
   }

   public FluidState getFluidState(BlockState state) {
      return state.get(field_235484_b_) ? Fluids.WATER.getStillFluidState(false) : super.getFluidState(state);
   }

   public boolean allowsMovement(BlockState state, IBlockReader worldIn, BlockPos pos, PathType type) {
      return false;
   }
}