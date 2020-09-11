package net.minecraft.entity.ai.brain.task;

import com.google.common.collect.ImmutableMap;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.BrainUtil;
import net.minecraft.entity.ai.brain.memory.MemoryModuleStatus;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.memory.WalkTarget;
import net.minecraft.util.math.EntityPosWrapper;
import net.minecraft.world.server.ServerWorld;

public class MoveToTargetTask extends Task<MobEntity> {
   private final float field_233966_b_;

   public MoveToTargetTask(float p_i231534_1_) {
      super(ImmutableMap.of(MemoryModuleType.WALK_TARGET, MemoryModuleStatus.REGISTERED, MemoryModuleType.LOOK_TARGET, MemoryModuleStatus.REGISTERED, MemoryModuleType.field_234103_o_, MemoryModuleStatus.VALUE_PRESENT, MemoryModuleType.VISIBLE_MOBS, MemoryModuleStatus.REGISTERED));
      this.field_233966_b_ = p_i231534_1_;
   }

   protected void startExecuting(ServerWorld worldIn, MobEntity entityIn, long gameTimeIn) {
      LivingEntity livingentity = entityIn.getBrain().getMemory(MemoryModuleType.field_234103_o_).get();
      if (BrainUtil.func_233876_c_(entityIn, livingentity) && BrainUtil.func_233869_a_(entityIn, livingentity, 1)) {
         this.func_233967_a_(entityIn);
      } else {
         this.func_233968_a_(entityIn, livingentity);
      }

   }

   private void func_233968_a_(LivingEntity p_233968_1_, LivingEntity p_233968_2_) {
      Brain brain = p_233968_1_.getBrain();
      brain.setMemory(MemoryModuleType.LOOK_TARGET, new EntityPosWrapper(p_233968_2_, true));
      WalkTarget walktarget = new WalkTarget(new EntityPosWrapper(p_233968_2_, false), this.field_233966_b_, 0);
      brain.setMemory(MemoryModuleType.WALK_TARGET, walktarget);
   }

   private void func_233967_a_(LivingEntity p_233967_1_) {
      p_233967_1_.getBrain().removeMemory(MemoryModuleType.WALK_TARGET);
   }
}