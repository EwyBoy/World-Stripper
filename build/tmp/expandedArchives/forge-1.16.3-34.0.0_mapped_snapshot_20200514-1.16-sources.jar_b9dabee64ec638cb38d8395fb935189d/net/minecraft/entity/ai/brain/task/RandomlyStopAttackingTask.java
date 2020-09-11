package net.minecraft.entity.ai.brain.task;

import com.google.common.collect.ImmutableMap;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.memory.MemoryModuleStatus;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.world.server.ServerWorld;

public class RandomlyStopAttackingTask extends Task<LivingEntity> {
   private final int field_233858_b_;

   public RandomlyStopAttackingTask(MemoryModuleType<?> p_i231510_1_, int p_i231510_2_) {
      super(ImmutableMap.of(MemoryModuleType.field_234103_o_, MemoryModuleStatus.REGISTERED, MemoryModuleType.field_234099_ag_, MemoryModuleStatus.VALUE_ABSENT, p_i231510_1_, MemoryModuleStatus.VALUE_PRESENT));
      this.field_233858_b_ = p_i231510_2_;
   }

   protected void startExecuting(ServerWorld worldIn, LivingEntity entityIn, long gameTimeIn) {
      entityIn.getBrain().func_233696_a_(MemoryModuleType.field_234099_ag_, true, (long)this.field_233858_b_);
      entityIn.getBrain().removeMemory(MemoryModuleType.field_234103_o_);
   }
}