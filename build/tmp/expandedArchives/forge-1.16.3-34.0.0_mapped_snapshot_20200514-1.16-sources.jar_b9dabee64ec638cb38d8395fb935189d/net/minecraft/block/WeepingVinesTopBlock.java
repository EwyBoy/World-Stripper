package net.minecraft.block;

import java.util.Random;
import net.minecraft.util.Direction;
import net.minecraft.util.math.shapes.VoxelShape;

public class WeepingVinesTopBlock extends AbstractTopPlantBlock {
   protected static final VoxelShape field_235636_e_ = Block.makeCuboidShape(4.0D, 9.0D, 4.0D, 12.0D, 16.0D, 12.0D);

   public WeepingVinesTopBlock(AbstractBlock.Properties p_i241194_1_) {
      super(p_i241194_1_, Direction.DOWN, field_235636_e_, false, 0.1D);
   }

   protected int func_230332_a_(Random p_230332_1_) {
      return PlantBlockHelper.func_235515_a_(p_230332_1_);
   }

   protected Block func_230330_d_() {
      return Blocks.field_235385_my_;
   }

   protected boolean func_230334_h_(BlockState p_230334_1_) {
      return PlantBlockHelper.func_235514_a_(p_230334_1_);
   }
}