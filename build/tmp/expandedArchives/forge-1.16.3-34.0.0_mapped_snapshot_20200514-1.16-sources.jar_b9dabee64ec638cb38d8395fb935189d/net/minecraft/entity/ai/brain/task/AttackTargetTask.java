package net.minecraft.entity.ai.brain.task;

import com.google.common.collect.ImmutableMap;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.brain.BrainUtil;
import net.minecraft.entity.ai.brain.memory.MemoryModuleStatus;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.item.ShootableItem;
import net.minecraft.util.Hand;
import net.minecraft.world.server.ServerWorld;

public class AttackTargetTask extends Task<MobEntity> {
   private final int field_233920_b_;

   public AttackTargetTask(int p_i231523_1_) {
      super(ImmutableMap.of(MemoryModuleType.LOOK_TARGET, MemoryModuleStatus.REGISTERED, MemoryModuleType.field_234103_o_, MemoryModuleStatus.VALUE_PRESENT, MemoryModuleType.field_234104_p_, MemoryModuleStatus.VALUE_ABSENT));
      this.field_233920_b_ = p_i231523_1_;
   }

   protected boolean shouldExecute(ServerWorld worldIn, MobEntity owner) {
      LivingEntity livingentity = this.func_233923_b_(owner);
      return !this.func_233921_a_(owner) && BrainUtil.func_233876_c_(owner, livingentity) && BrainUtil.func_233874_b_(owner, livingentity);
   }

   private boolean func_233921_a_(MobEntity p_233921_1_) {
      return p_233921_1_.func_233634_a_((p_233922_1_) -> {
         return p_233922_1_ instanceof ShootableItem && p_233921_1_.func_230280_a_((ShootableItem)p_233922_1_);
      });
   }

   protected void startExecuting(ServerWorld worldIn, MobEntity entityIn, long gameTimeIn) {
      LivingEntity livingentity = this.func_233923_b_(entityIn);
      BrainUtil.lookAt(entityIn, livingentity);
      entityIn.swingArm(Hand.MAIN_HAND);
      entityIn.attackEntityAsMob(livingentity);
      entityIn.getBrain().func_233696_a_(MemoryModuleType.field_234104_p_, true, (long)this.field_233920_b_);
   }

   private LivingEntity func_233923_b_(MobEntity p_233923_1_) {
      return p_233923_1_.getBrain().getMemory(MemoryModuleType.field_234103_o_).get();
   }
}