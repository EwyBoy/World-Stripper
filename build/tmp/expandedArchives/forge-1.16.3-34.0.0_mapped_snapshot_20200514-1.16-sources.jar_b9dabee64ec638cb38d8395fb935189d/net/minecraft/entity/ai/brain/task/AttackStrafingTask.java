package net.minecraft.entity.ai.brain.task;

import com.google.common.collect.ImmutableMap;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.brain.memory.MemoryModuleStatus;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.util.math.EntityPosWrapper;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.server.ServerWorld;

public class AttackStrafingTask<E extends MobEntity> extends Task<E> {
   private final int field_233853_b_;
   private final float field_233854_c_;

   public AttackStrafingTask(int p_i231509_1_, float p_i231509_2_) {
      super(ImmutableMap.of(MemoryModuleType.WALK_TARGET, MemoryModuleStatus.VALUE_ABSENT, MemoryModuleType.LOOK_TARGET, MemoryModuleStatus.REGISTERED, MemoryModuleType.field_234103_o_, MemoryModuleStatus.VALUE_PRESENT, MemoryModuleType.VISIBLE_MOBS, MemoryModuleStatus.VALUE_PRESENT));
      this.field_233853_b_ = p_i231509_1_;
      this.field_233854_c_ = p_i231509_2_;
   }

   protected boolean shouldExecute(ServerWorld worldIn, E owner) {
      return this.func_233855_a_(owner) && this.func_233856_b_(owner);
   }

   protected void startExecuting(ServerWorld worldIn, E entityIn, long gameTimeIn) {
      entityIn.getBrain().setMemory(MemoryModuleType.LOOK_TARGET, new EntityPosWrapper(this.func_233857_c_(entityIn), true));
      entityIn.getMoveHelper().strafe(-this.field_233854_c_, 0.0F);
      entityIn.rotationYaw = MathHelper.func_219800_b(entityIn.rotationYaw, entityIn.rotationYawHead, 0.0F);
   }

   private boolean func_233855_a_(E p_233855_1_) {
      return p_233855_1_.getBrain().getMemory(MemoryModuleType.VISIBLE_MOBS).get().contains(this.func_233857_c_(p_233855_1_));
   }

   private boolean func_233856_b_(E p_233856_1_) {
      return this.func_233857_c_(p_233856_1_).func_233562_a_(p_233856_1_, (double)this.field_233853_b_);
   }

   private LivingEntity func_233857_c_(E p_233857_1_) {
      return p_233857_1_.getBrain().getMemory(MemoryModuleType.field_234103_o_).get();
   }
}