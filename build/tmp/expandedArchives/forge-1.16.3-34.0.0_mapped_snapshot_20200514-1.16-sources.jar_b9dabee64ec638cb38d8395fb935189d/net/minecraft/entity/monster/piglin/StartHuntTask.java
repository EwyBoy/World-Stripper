package net.minecraft.entity.monster.piglin;

import com.google.common.collect.ImmutableMap;
import net.minecraft.entity.ai.brain.memory.MemoryModuleStatus;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.entity.monster.HoglinEntity;
import net.minecraft.world.server.ServerWorld;

public class StartHuntTask<E extends PiglinEntity> extends Task<E> {
   public StartHuntTask() {
      super(ImmutableMap.of(MemoryModuleType.field_234085_S_, MemoryModuleStatus.VALUE_PRESENT, MemoryModuleType.field_234078_L_, MemoryModuleStatus.VALUE_ABSENT, MemoryModuleType.field_234082_P_, MemoryModuleStatus.VALUE_ABSENT, MemoryModuleType.field_234090_X_, MemoryModuleStatus.REGISTERED));
   }

   protected boolean shouldExecute(ServerWorld worldIn, PiglinEntity owner) {
      return !owner.isChild() && !PiglinTasks.func_234508_e_(owner);
   }

   protected void startExecuting(ServerWorld worldIn, E entityIn, long gameTimeIn) {
      HoglinEntity hoglinentity = entityIn.getBrain().getMemory(MemoryModuleType.field_234085_S_).get();
      PiglinTasks.func_234497_c_(entityIn, hoglinentity);
      PiglinTasks.func_234518_h_(entityIn);
      PiglinTasks.func_234487_b_(entityIn, hoglinentity);
      PiglinTasks.func_234512_f_(entityIn);
   }
}