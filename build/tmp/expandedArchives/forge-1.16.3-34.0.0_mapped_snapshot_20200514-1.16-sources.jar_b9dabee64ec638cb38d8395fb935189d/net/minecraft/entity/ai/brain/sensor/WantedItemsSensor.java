package net.minecraft.entity.ai.brain.sensor;

import com.google.common.collect.ImmutableSet;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.world.server.ServerWorld;

public class WantedItemsSensor extends Sensor<MobEntity> {
   public Set<MemoryModuleType<?>> getUsedMemories() {
      return ImmutableSet.of(MemoryModuleType.field_234076_J_);
   }

   protected void update(ServerWorld worldIn, MobEntity entityIn) {
      Brain<?> brain = entityIn.getBrain();
      List<ItemEntity> list = worldIn.getEntitiesWithinAABB(ItemEntity.class, entityIn.getBoundingBox().grow(8.0D, 4.0D, 8.0D), (p_234123_0_) -> {
         return true;
      });
      list.sort(Comparator.comparingDouble(entityIn::getDistanceSq));
      Optional<ItemEntity> optional = list.stream().filter((p_234124_1_) -> {
         return entityIn.func_230293_i_(p_234124_1_.getItem());
      }).filter((p_234122_1_) -> {
         return p_234122_1_.func_233562_a_(entityIn, 9.0D);
      }).filter(entityIn::canEntityBeSeen).findFirst();
      brain.setMemory(MemoryModuleType.field_234076_J_, optional);
   }
}