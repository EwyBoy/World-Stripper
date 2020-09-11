package net.minecraft.entity.ai.brain.task;

import com.google.common.collect.ImmutableMap;
import java.util.function.BiPredicate;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.memory.MemoryModuleStatus;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.world.GameRules;
import net.minecraft.world.server.ServerWorld;

public class EndAttackTask extends Task<LivingEntity> {
   private final int field_233978_b_;
   private final BiPredicate<LivingEntity, LivingEntity> field_233979_c_;

   public EndAttackTask(int p_i231538_1_, BiPredicate<LivingEntity, LivingEntity> p_i231538_2_) {
      super(ImmutableMap.of(MemoryModuleType.field_234103_o_, MemoryModuleStatus.VALUE_PRESENT, MemoryModuleType.field_234078_L_, MemoryModuleStatus.REGISTERED, MemoryModuleType.field_234083_Q_, MemoryModuleStatus.VALUE_ABSENT, MemoryModuleType.field_234084_R_, MemoryModuleStatus.REGISTERED));
      this.field_233978_b_ = p_i231538_1_;
      this.field_233979_c_ = p_i231538_2_;
   }

   protected boolean shouldExecute(ServerWorld worldIn, LivingEntity owner) {
      return this.func_233980_a_(owner).func_233643_dh_();
   }

   protected void startExecuting(ServerWorld worldIn, LivingEntity entityIn, long gameTimeIn) {
      LivingEntity livingentity = this.func_233980_a_(entityIn);
      if (this.field_233979_c_.test(entityIn, livingentity)) {
         entityIn.getBrain().func_233696_a_(MemoryModuleType.field_234084_R_, true, (long)this.field_233978_b_);
      }

      entityIn.getBrain().func_233696_a_(MemoryModuleType.field_234083_Q_, livingentity.func_233580_cy_(), (long)this.field_233978_b_);
      if (livingentity.getType() != EntityType.PLAYER || worldIn.getGameRules().getBoolean(GameRules.field_234895_F_)) {
         entityIn.getBrain().removeMemory(MemoryModuleType.field_234103_o_);
         entityIn.getBrain().removeMemory(MemoryModuleType.field_234078_L_);
      }

   }

   private LivingEntity func_233980_a_(LivingEntity p_233980_1_) {
      return p_233980_1_.getBrain().getMemory(MemoryModuleType.field_234103_o_).get();
   }
}