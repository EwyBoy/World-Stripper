package net.minecraft.entity.ai.brain.task;

import com.google.common.collect.ImmutableMap;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.ai.brain.BrainUtil;
import net.minecraft.entity.ai.brain.memory.MemoryModuleStatus;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.util.RangedInteger;
import net.minecraft.world.server.ServerWorld;

public class ChildFollowNearestAdultTask<E extends AgeableEntity> extends Task<E> {
   private final RangedInteger field_233850_b_;
   private final float field_233851_c_;

   public ChildFollowNearestAdultTask(RangedInteger p_i231508_1_, float p_i231508_2_) {
      super(ImmutableMap.of(MemoryModuleType.field_234075_I_, MemoryModuleStatus.VALUE_PRESENT, MemoryModuleType.WALK_TARGET, MemoryModuleStatus.VALUE_ABSENT));
      this.field_233850_b_ = p_i231508_1_;
      this.field_233851_c_ = p_i231508_2_;
   }

   protected boolean shouldExecute(ServerWorld worldIn, E owner) {
      if (!owner.isChild()) {
         return false;
      } else {
         AgeableEntity ageableentity = this.func_233852_a_(owner);
         return owner.func_233562_a_(ageableentity, (double)(this.field_233850_b_.func_233019_b_() + 1)) && !owner.func_233562_a_(ageableentity, (double)this.field_233850_b_.func_233016_a_());
      }
   }

   protected void startExecuting(ServerWorld worldIn, E entityIn, long gameTimeIn) {
      BrainUtil.func_233860_a_(entityIn, this.func_233852_a_(entityIn), this.field_233851_c_, this.field_233850_b_.func_233016_a_() - 1);
   }

   private AgeableEntity func_233852_a_(E p_233852_1_) {
      return p_233852_1_.getBrain().getMemory(MemoryModuleType.field_234075_I_).get();
   }
}