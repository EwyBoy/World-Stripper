package net.minecraft.entity.ai.brain.sensor;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.monster.HoglinEntity;
import net.minecraft.entity.monster.piglin.PiglinEntity;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;

public class HoglinMobsSensor extends Sensor<HoglinEntity> {
   public Set<MemoryModuleType<?>> getUsedMemories() {
      return ImmutableSet.of(MemoryModuleType.VISIBLE_MOBS, MemoryModuleType.field_234098_af_, MemoryModuleType.field_234092_Z_, MemoryModuleType.field_234091_Y_, MemoryModuleType.field_234094_ab_, MemoryModuleType.field_234095_ac_);
   }

   protected void update(ServerWorld worldIn, HoglinEntity entityIn) {
      Brain<?> brain = entityIn.getBrain();
      brain.setMemory(MemoryModuleType.field_234098_af_, this.func_234120_b_(worldIn, entityIn));
      Optional<PiglinEntity> optional = Optional.empty();
      int i = 0;
      List<HoglinEntity> list = Lists.newArrayList();

      for(LivingEntity livingentity : brain.getMemory(MemoryModuleType.VISIBLE_MOBS).orElse(Lists.newArrayList())) {
         if (livingentity instanceof PiglinEntity && !livingentity.isChild()) {
            ++i;
            if (!optional.isPresent()) {
               optional = Optional.of((PiglinEntity)livingentity);
            }
         }

         if (livingentity instanceof HoglinEntity && !livingentity.isChild()) {
            list.add((HoglinEntity)livingentity);
         }
      }

      brain.setMemory(MemoryModuleType.field_234092_Z_, optional);
      brain.setMemory(MemoryModuleType.field_234091_Y_, list);
      brain.setMemory(MemoryModuleType.field_234094_ab_, i);
      brain.setMemory(MemoryModuleType.field_234095_ac_, list.size());
   }

   private Optional<BlockPos> func_234120_b_(ServerWorld p_234120_1_, HoglinEntity p_234120_2_) {
      return BlockPos.func_239584_a_(p_234120_2_.func_233580_cy_(), 8, 4, (p_234119_1_) -> {
         return p_234120_1_.getBlockState(p_234119_1_).func_235714_a_(BlockTags.field_232879_au_);
      });
   }
}