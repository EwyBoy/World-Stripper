package net.minecraft.entity.monster.piglin;

import com.google.common.collect.ImmutableMap;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.memory.MemoryModuleStatus;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.world.server.ServerWorld;

public class FinishedHuntTask<E extends PiglinEntity> extends Task<E> {
   public FinishedHuntTask() {
      super(ImmutableMap.of(MemoryModuleType.field_234103_o_, MemoryModuleStatus.VALUE_PRESENT, MemoryModuleType.field_234082_P_, MemoryModuleStatus.REGISTERED));
   }

   protected void startExecuting(ServerWorld worldIn, E entityIn, long gameTimeIn) {
      if (this.func_234539_a_(entityIn)) {
         PiglinTasks.func_234518_h_(entityIn);
      }

   }

   private boolean func_234539_a_(E p_234539_1_) {
      LivingEntity livingentity = p_234539_1_.getBrain().getMemory(MemoryModuleType.field_234103_o_).get();
      return livingentity.getType() == EntityType.field_233588_G_ && livingentity.func_233643_dh_();
   }
}