package net.minecraft.entity.ai.brain.task;

import com.google.common.collect.ImmutableMap;
import java.util.Optional;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.BrainUtil;
import net.minecraft.entity.ai.brain.memory.MemoryModuleStatus;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.world.server.ServerWorld;

public class AnimalBreedTask extends Task<AnimalEntity> {
   private final EntityType<? extends AnimalEntity> field_233842_b_;
   private final float field_233843_c_;
   private long field_233844_d_;

   public AnimalBreedTask(EntityType<? extends AnimalEntity> p_i231506_1_, float p_i231506_2_) {
      super(ImmutableMap.of(MemoryModuleType.VISIBLE_MOBS, MemoryModuleStatus.VALUE_PRESENT, MemoryModuleType.BREED_TARGET, MemoryModuleStatus.VALUE_ABSENT, MemoryModuleType.WALK_TARGET, MemoryModuleStatus.REGISTERED, MemoryModuleType.LOOK_TARGET, MemoryModuleStatus.REGISTERED), 325);
      this.field_233842_b_ = p_i231506_1_;
      this.field_233843_c_ = p_i231506_2_;
   }

   protected boolean shouldExecute(ServerWorld worldIn, AnimalEntity owner) {
      return owner.isInLove() && this.func_233849_c_(owner).isPresent();
   }

   protected void startExecuting(ServerWorld worldIn, AnimalEntity entityIn, long gameTimeIn) {
      AnimalEntity animalentity = this.func_233849_c_(entityIn).get();
      entityIn.getBrain().setMemory(MemoryModuleType.BREED_TARGET, animalentity);
      animalentity.getBrain().setMemory(MemoryModuleType.BREED_TARGET, entityIn);
      BrainUtil.lookApproachEachOther(entityIn, animalentity, this.field_233843_c_);
      int i = 275 + entityIn.getRNG().nextInt(50);
      this.field_233844_d_ = gameTimeIn + (long)i;
   }

   protected boolean shouldContinueExecuting(ServerWorld worldIn, AnimalEntity entityIn, long gameTimeIn) {
      if (!this.func_233848_b_(entityIn)) {
         return false;
      } else {
         AnimalEntity animalentity = this.func_233846_a_(entityIn);
         return animalentity.isAlive() && entityIn.canMateWith(animalentity) && BrainUtil.canSee(entityIn.getBrain(), animalentity) && gameTimeIn <= this.field_233844_d_;
      }
   }

   protected void updateTask(ServerWorld worldIn, AnimalEntity owner, long gameTime) {
      AnimalEntity animalentity = this.func_233846_a_(owner);
      BrainUtil.lookApproachEachOther(owner, animalentity, this.field_233843_c_);
      if (owner.func_233562_a_(animalentity, 3.0D)) {
         if (gameTime >= this.field_233844_d_) {
            owner.func_234177_a_(worldIn, animalentity);
            owner.getBrain().removeMemory(MemoryModuleType.BREED_TARGET);
            animalentity.getBrain().removeMemory(MemoryModuleType.BREED_TARGET);
         }

      }
   }

   protected void resetTask(ServerWorld worldIn, AnimalEntity entityIn, long gameTimeIn) {
      entityIn.getBrain().removeMemory(MemoryModuleType.BREED_TARGET);
      entityIn.getBrain().removeMemory(MemoryModuleType.WALK_TARGET);
      entityIn.getBrain().removeMemory(MemoryModuleType.LOOK_TARGET);
      this.field_233844_d_ = 0L;
   }

   private AnimalEntity func_233846_a_(AnimalEntity p_233846_1_) {
      return (AnimalEntity)p_233846_1_.getBrain().getMemory(MemoryModuleType.BREED_TARGET).get();
   }

   private boolean func_233848_b_(AnimalEntity p_233848_1_) {
      Brain<?> brain = p_233848_1_.getBrain();
      return brain.hasMemory(MemoryModuleType.BREED_TARGET) && brain.getMemory(MemoryModuleType.BREED_TARGET).get().getType() == this.field_233842_b_;
   }

   private Optional<? extends AnimalEntity> func_233849_c_(AnimalEntity p_233849_1_) {
      return p_233849_1_.getBrain().getMemory(MemoryModuleType.VISIBLE_MOBS).get().stream().filter((p_233847_1_) -> {
         return p_233847_1_.getType() == this.field_233842_b_;
      }).map((p_233845_0_) -> {
         return (AnimalEntity)p_233845_0_;
      }).filter(p_233849_1_::canMateWith).findFirst();
   }
}