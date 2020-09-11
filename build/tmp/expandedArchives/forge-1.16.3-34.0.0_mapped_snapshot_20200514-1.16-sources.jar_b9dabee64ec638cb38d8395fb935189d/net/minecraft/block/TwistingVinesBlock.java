package net.minecraft.block;

import net.minecraft.util.Direction;
import net.minecraft.util.math.shapes.VoxelShape;

public class TwistingVinesBlock extends AbstractBodyPlantBlock {
   public static final VoxelShape field_235611_d_ = Block.makeCuboidShape(4.0D, 0.0D, 4.0D, 12.0D, 16.0D, 12.0D);

   public TwistingVinesBlock(AbstractBlock.Properties p_i241192_1_) {
      super(p_i241192_1_, Direction.UP, field_235611_d_, false);
   }

   protected AbstractTopPlantBlock func_230331_c_() {
      return (AbstractTopPlantBlock)Blocks.field_235386_mz_;
   }
}