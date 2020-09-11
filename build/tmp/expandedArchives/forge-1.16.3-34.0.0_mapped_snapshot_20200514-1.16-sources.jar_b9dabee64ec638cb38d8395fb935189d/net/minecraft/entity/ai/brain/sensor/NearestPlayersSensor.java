package net.minecraft.entity.ai.brain.sensor;

import com.google.common.collect.ImmutableSet;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.EntityPredicates;
import net.minecraft.world.server.ServerWorld;

public class NearestPlayersSensor extends Sensor<LivingEntity> {
   public Set<MemoryModuleType<?>> getUsedMemories() {
      return ImmutableSet.of(MemoryModuleType.NEAREST_PLAYERS, MemoryModuleType.NEAREST_VISIBLE_PLAYER, MemoryModuleType.field_234102_l_);
   }

   protected void update(ServerWorld worldIn, LivingEntity entityIn) {
      List<PlayerEntity> list = worldIn.getPlayers().stream().filter(EntityPredicates.NOT_SPECTATING).filter((p_220979_1_) -> {
         return entityIn.func_233562_a_(p_220979_1_, 16.0D);
      }).sorted(Comparator.comparingDouble(entityIn::getDistanceSq)).collect(Collectors.toList());
      Brain<?> brain = entityIn.getBrain();
      brain.setMemory(MemoryModuleType.NEAREST_PLAYERS, list);
      List<PlayerEntity> list1 = list.stream().filter((p_234128_1_) -> {
         return func_242316_a(entityIn, p_234128_1_);
      }).collect(Collectors.toList());
      brain.setMemory(MemoryModuleType.NEAREST_VISIBLE_PLAYER, list1.isEmpty() ? null : list1.get(0));
      Optional<PlayerEntity> optional = list1.stream().filter(EntityPredicates.field_233583_f_).findFirst();
      brain.setMemory(MemoryModuleType.field_234102_l_, optional);
   }
}