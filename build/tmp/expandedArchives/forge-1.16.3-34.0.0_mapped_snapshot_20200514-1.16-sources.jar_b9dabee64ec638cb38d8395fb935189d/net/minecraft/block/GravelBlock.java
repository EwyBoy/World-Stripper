package net.minecraft.block;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class GravelBlock extends FallingBlock {
   public GravelBlock(AbstractBlock.Properties properties) {
      super(properties);
   }

   @OnlyIn(Dist.CLIENT)
   public int getDustColor(BlockState state, IBlockReader p_189876_2_, BlockPos p_189876_3_) {
      return -8356741;
   }
}