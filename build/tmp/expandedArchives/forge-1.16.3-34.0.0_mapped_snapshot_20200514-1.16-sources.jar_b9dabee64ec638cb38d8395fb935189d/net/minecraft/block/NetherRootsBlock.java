package net.minecraft.block;

import net.minecraft.tags.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;

public class NetherRootsBlock extends BushBlock {
   protected static final VoxelShape field_235572_a_ = Block.makeCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 13.0D, 14.0D);

   public NetherRootsBlock(AbstractBlock.Properties p_i241186_1_) {
      super(p_i241186_1_);
   }

   public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
      return field_235572_a_;
   }

   protected boolean isValidGround(BlockState state, IBlockReader worldIn, BlockPos pos) {
      return state.func_235714_a_(BlockTags.field_232873_an_) || state.isIn(Blocks.field_235336_cN_) || super.isValidGround(state, worldIn, pos);
   }

   /**
    * Get the OffsetType for this Block. Determines if the model is rendered slightly offset.
    */
   public AbstractBlock.OffsetType getOffsetType() {
      return AbstractBlock.OffsetType.XZ;
   }
}