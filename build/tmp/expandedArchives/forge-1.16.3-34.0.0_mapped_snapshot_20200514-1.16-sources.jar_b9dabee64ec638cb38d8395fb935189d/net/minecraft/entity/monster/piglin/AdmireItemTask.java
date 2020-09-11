package net.minecraft.entity.monster.piglin;

import com.google.common.collect.ImmutableMap;
import net.minecraft.entity.ai.brain.memory.MemoryModuleStatus;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.world.server.ServerWorld;

public class AdmireItemTask<E extends PiglinEntity> extends Task<E> {
   private final int field_234540_b_;

   public AdmireItemTask(int p_i231573_1_) {
      super(ImmutableMap.of(MemoryModuleType.field_234076_J_, MemoryModuleStatus.VALUE_PRESENT, MemoryModuleType.field_234080_N_, MemoryModuleStatus.VALUE_ABSENT, MemoryModuleType.field_234081_O_, MemoryModuleStatus.VALUE_ABSENT, MemoryModuleType.field_242311_P, MemoryModuleStatus.VALUE_ABSENT));
      this.field_234540_b_ = p_i231573_1_;
   }

   protected boolean shouldExecute(ServerWorld worldIn, E owner) {
      ItemEntity itementity = owner.getBrain().getMemory(MemoryModuleType.field_234076_J_).get();
      return PiglinTasks.func_234480_a_(itementity.getItem().getItem());
   }

   protected void startExecuting(ServerWorld worldIn, E entityIn, long gameTimeIn) {
      entityIn.getBrain().func_233696_a_(MemoryModuleType.field_234080_N_, true, (long)this.field_234540_b_);
   }
}