package net.minecraft.util.math;

import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.vector.Vector3d;

public class BlockPosWrapper implements IPosWrapper {
   private final BlockPos field_220612_a;
   private final Vector3d field_220613_b;

   public BlockPosWrapper(BlockPos p_i50371_1_) {
      this.field_220612_a = p_i50371_1_;
      this.field_220613_b = Vector3d.func_237489_a_(p_i50371_1_);
   }

   public Vector3d getPos() {
      return this.field_220613_b;
   }

   public BlockPos getBlockPos() {
      return this.field_220612_a;
   }

   public boolean isVisibleTo(LivingEntity p_220610_1_) {
      return true;
   }

   public String toString() {
      return "BlockPosTracker{blockPos=" + this.field_220612_a + ", centerPosition=" + this.field_220613_b + '}';
   }
}