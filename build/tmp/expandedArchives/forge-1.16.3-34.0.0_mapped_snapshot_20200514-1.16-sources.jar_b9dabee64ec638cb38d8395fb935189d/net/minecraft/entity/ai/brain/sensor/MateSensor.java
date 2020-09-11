package net.minecraft.entity.ai.brain.sensor;

import com.google.common.collect.ImmutableSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.world.server.ServerWorld;

public class MateSensor extends Sensor<AgeableEntity> {
   public Set<MemoryModuleType<?>> getUsedMemories() {
      return ImmutableSet.of(MemoryModuleType.field_234075_I_, MemoryModuleType.VISIBLE_MOBS);
   }

   protected void update(ServerWorld worldIn, AgeableEntity entityIn) {
      entityIn.getBrain().getMemory(MemoryModuleType.VISIBLE_MOBS).ifPresent((p_234118_2_) -> {
         this.func_234116_a_(entityIn, p_234118_2_);
      });
   }

   private void func_234116_a_(AgeableEntity p_234116_1_, List<LivingEntity> p_234116_2_) {
      Optional<AgeableEntity> optional = p_234116_2_.stream().filter((p_234115_1_) -> {
         return p_234115_1_.getType() == p_234116_1_.getType();
      }).map((p_234117_0_) -> {
         return (AgeableEntity)p_234117_0_;
      }).filter((p_234114_0_) -> {
         return !p_234114_0_.isChild();
      }).findFirst();
      p_234116_1_.getBrain().setMemory(MemoryModuleType.field_234075_I_, optional);
   }
}