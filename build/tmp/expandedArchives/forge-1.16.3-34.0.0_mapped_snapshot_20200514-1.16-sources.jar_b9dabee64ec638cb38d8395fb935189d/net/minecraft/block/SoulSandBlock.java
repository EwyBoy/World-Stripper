package net.minecraft.block;

import java.util.Random;
import net.minecraft.pathfinding.PathType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class SoulSandBlock extends Block {
   protected static final VoxelShape SHAPE = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 14.0D, 16.0D);

   public SoulSandBlock(AbstractBlock.Properties properties) {
      super(properties);
   }

   public VoxelShape getCollisionShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
      return SHAPE;
   }

   public VoxelShape func_230335_e_(BlockState p_230335_1_, IBlockReader p_230335_2_, BlockPos p_230335_3_) {
      return VoxelShapes.fullCube();
   }

   public VoxelShape func_230322_a_(BlockState p_230322_1_, IBlockReader p_230322_2_, BlockPos p_230322_3_, ISelectionContext p_230322_4_) {
      return VoxelShapes.fullCube();
   }

   public void tick(BlockState state, ServerWorld worldIn, BlockPos pos, Random rand) {
      BubbleColumnBlock.placeBubbleColumn(worldIn, pos.up(), false);
   }

   /**
    * Update the provided state given the provided neighbor facing and neighbor state, returning a new state.
    * For example, fences make their connections to the passed in state if possible, and wet concrete powder immediately
    * returns its solidified counterpart.
    * Note that this method should ideally consider only the specific face passed in.
    */
   public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
      if (facing == Direction.UP && facingState.isIn(Blocks.WATER)) {
         worldIn.getPendingBlockTicks().scheduleTick(currentPos, this, 20);
      }

      return super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
   }

   public void onBlockAdded(BlockState state, World worldIn, BlockPos pos, BlockState oldState, boolean isMoving) {
      worldIn.getPendingBlockTicks().scheduleTick(pos, this, 20);
   }

   public boolean allowsMovement(BlockState state, IBlockReader worldIn, BlockPos pos, PathType type) {
      return false;
   }
}