package net.minecraft.entity.ai.brain.sensor;

import com.google.common.collect.ImmutableSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.world.server.ServerWorld;

public class GolemLastSeenSensor extends Sensor<LivingEntity> {
   public GolemLastSeenSensor() {
      this(200);
   }

   public GolemLastSeenSensor(int p_i51525_1_) {
      super(p_i51525_1_);
   }

   protected void update(ServerWorld worldIn, LivingEntity entityIn) {
      func_242312_a(entityIn);
   }

   public Set<MemoryModuleType<?>> getUsedMemories() {
      return ImmutableSet.of(MemoryModuleType.MOBS);
   }

   public static void func_242312_a(LivingEntity p_242312_0_) {
      Optional<List<LivingEntity>> optional = p_242312_0_.getBrain().getMemory(MemoryModuleType.MOBS);
      if (optional.isPresent()) {
         boolean flag = optional.get().stream().anyMatch((p_223546_0_) -> {
            return p_223546_0_.getType().equals(EntityType.IRON_GOLEM);
         });
         if (flag) {
            func_242313_b(p_242312_0_);
         }

      }
   }

   public static void func_242313_b(LivingEntity p_242313_0_) {
      p_242313_0_.getBrain().func_233696_a_(MemoryModuleType.field_242309_E, true, 600L);
   }
}