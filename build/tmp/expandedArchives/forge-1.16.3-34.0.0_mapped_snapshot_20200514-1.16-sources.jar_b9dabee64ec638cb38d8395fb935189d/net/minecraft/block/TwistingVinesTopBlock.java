package net.minecraft.block;

import java.util.Random;
import net.minecraft.util.Direction;
import net.minecraft.util.math.shapes.VoxelShape;

public class TwistingVinesTopBlock extends AbstractTopPlantBlock {
   public static final VoxelShape field_235610_e_ = Block.makeCuboidShape(4.0D, 0.0D, 4.0D, 12.0D, 15.0D, 12.0D);

   public TwistingVinesTopBlock(AbstractBlock.Properties p_i241191_1_) {
      super(p_i241191_1_, Direction.UP, field_235610_e_, false, 0.1D);
   }

   protected int func_230332_a_(Random p_230332_1_) {
      return PlantBlockHelper.func_235515_a_(p_230332_1_);
   }

   protected Block func_230330_d_() {
      return Blocks.field_235342_mA_;
   }

   protected boolean func_230334_h_(BlockState p_230334_1_) {
      return PlantBlockHelper.func_235514_a_(p_230334_1_);
   }
}