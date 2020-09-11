package net.minecraft.world;

import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.fluid.FluidState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;

public final class Blockreader implements IBlockReader {
   private final BlockState[] field_234996_a_;

   public Blockreader(BlockState[] p_i231623_1_) {
      this.field_234996_a_ = p_i231623_1_;
   }

   @Nullable
   public TileEntity getTileEntity(BlockPos pos) {
      return null;
   }

   public BlockState getBlockState(BlockPos pos) {
      int i = pos.getY();
      return i >= 0 && i < this.field_234996_a_.length ? this.field_234996_a_[i] : Blocks.AIR.getDefaultState();
   }

   public FluidState getFluidState(BlockPos pos) {
      return this.getBlockState(pos).getFluidState();
   }
}