package net.minecraft.entity.monster.piglin;

import com.google.common.collect.ImmutableMap;
import java.util.Optional;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.memory.MemoryModuleStatus;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.world.server.ServerWorld;

public class StopReachingItemTask<E extends PiglinEntity> extends Task<E> {
   private final int field_242365_b;
   private final int field_242366_c;

   public StopReachingItemTask(int p_i241918_1_, int p_i241918_2_) {
      super(ImmutableMap.of(MemoryModuleType.field_234080_N_, MemoryModuleStatus.VALUE_PRESENT, MemoryModuleType.field_234076_J_, MemoryModuleStatus.VALUE_PRESENT, MemoryModuleType.field_242310_O, MemoryModuleStatus.REGISTERED, MemoryModuleType.field_242311_P, MemoryModuleStatus.REGISTERED));
      this.field_242365_b = p_i241918_1_;
      this.field_242366_c = p_i241918_2_;
   }

   protected boolean shouldExecute(ServerWorld worldIn, E owner) {
      return owner.getHeldItemOffhand().isEmpty();
   }

   protected void startExecuting(ServerWorld worldIn, E entityIn, long gameTimeIn) {
      Brain<PiglinEntity> brain = entityIn.getBrain();
      Optional<Integer> optional = brain.getMemory(MemoryModuleType.field_242310_O);
      if (!optional.isPresent()) {
         brain.setMemory(MemoryModuleType.field_242310_O, 0);
      } else {
         int i = optional.get();
         if (i > this.field_242365_b) {
            brain.removeMemory(MemoryModuleType.field_234080_N_);
            brain.removeMemory(MemoryModuleType.field_242310_O);
            brain.func_233696_a_(MemoryModuleType.field_242311_P, true, (long)this.field_242366_c);
         } else {
            brain.setMemory(MemoryModuleType.field_242310_O, i + 1);
         }
      }

   }
}