package net.minecraft.block;

import net.minecraft.util.Direction;
import net.minecraft.util.math.shapes.VoxelShape;

public class WeepingVinesBlock extends AbstractBodyPlantBlock {
   public static final VoxelShape field_235637_d_ = Block.makeCuboidShape(1.0D, 0.0D, 1.0D, 15.0D, 16.0D, 15.0D);

   public WeepingVinesBlock(AbstractBlock.Properties p_i241195_1_) {
      super(p_i241195_1_, Direction.DOWN, field_235637_d_, false);
   }

   protected AbstractTopPlantBlock func_230331_c_() {
      return (AbstractTopPlantBlock)Blocks.field_235384_mx_;
   }
}