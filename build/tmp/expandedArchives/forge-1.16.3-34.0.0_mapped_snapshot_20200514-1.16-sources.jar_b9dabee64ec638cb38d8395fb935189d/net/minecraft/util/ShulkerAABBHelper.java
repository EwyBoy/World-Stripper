package net.minecraft.util;

import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShapes;

public class ShulkerAABBHelper {
   public static AxisAlignedBB func_233539_a_(BlockPos p_233539_0_, Direction p_233539_1_) {
      return VoxelShapes.fullCube().getBoundingBox().expand((double)(0.5F * (float)p_233539_1_.getXOffset()), (double)(0.5F * (float)p_233539_1_.getYOffset()), (double)(0.5F * (float)p_233539_1_.getZOffset())).contract((double)p_233539_1_.getXOffset(), (double)p_233539_1_.getYOffset(), (double)p_233539_1_.getZOffset()).offset(p_233539_0_.offset(p_233539_1_));
   }
}