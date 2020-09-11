package net.minecraft.block;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class SandBlock extends FallingBlock {
   private final int dustColor;

   public SandBlock(int dustColorIn, AbstractBlock.Properties properties) {
      super(properties);
      this.dustColor = dustColorIn;
   }

   @OnlyIn(Dist.CLIENT)
   public int getDustColor(BlockState state, IBlockReader p_189876_2_, BlockPos p_189876_3_) {
      return this.dustColor;
   }
}