package net.minecraft.block;

import net.minecraft.util.Direction;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class BreakableBlock extends Block {
   public BreakableBlock(AbstractBlock.Properties properties) {
      super(properties);
   }

   @OnlyIn(Dist.CLIENT)
   public boolean isSideInvisible(BlockState state, BlockState adjacentBlockState, Direction side) {
      return adjacentBlockState.isIn(this) ? true : super.isSideInvisible(state, adjacentBlockState, side);
   }
}