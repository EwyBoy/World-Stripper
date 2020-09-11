package net.minecraft.util.math;

import java.util.List;
import java.util.Optional;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.util.math.vector.Vector3d;

public class EntityPosWrapper implements IPosWrapper {
   private final Entity entity;
   private final boolean field_233894_b_;

   public EntityPosWrapper(Entity p_i231516_1_, boolean p_i231516_2_) {
      this.entity = p_i231516_1_;
      this.field_233894_b_ = p_i231516_2_;
   }

   public Vector3d getPos() {
      return this.field_233894_b_ ? this.entity.getPositionVec().add(0.0D, (double)this.entity.getEyeHeight(), 0.0D) : this.entity.getPositionVec();
   }

   public BlockPos getBlockPos() {
      return this.entity.func_233580_cy_();
   }

   public boolean isVisibleTo(LivingEntity p_220610_1_) {
      if (!(this.entity instanceof LivingEntity)) {
         return true;
      } else {
         Optional<List<LivingEntity>> optional = p_220610_1_.getBrain().getMemory(MemoryModuleType.VISIBLE_MOBS);
         return this.entity.isAlive() && optional.isPresent() && optional.get().contains(this.entity);
      }
   }

   public String toString() {
      return "EntityTracker for " + this.entity;
   }
}