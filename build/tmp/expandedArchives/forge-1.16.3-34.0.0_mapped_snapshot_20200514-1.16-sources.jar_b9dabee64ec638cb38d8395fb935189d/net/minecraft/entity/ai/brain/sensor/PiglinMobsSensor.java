package net.minecraft.entity.ai.brain.sensor;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CampfireBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.boss.WitherEntity;
import net.minecraft.entity.monster.HoglinEntity;
import net.minecraft.entity.monster.WitherSkeletonEntity;
import net.minecraft.entity.monster.piglin.AbstractPiglinEntity;
import net.minecraft.entity.monster.piglin.PiglinBruteEntity;
import net.minecraft.entity.monster.piglin.PiglinEntity;
import net.minecraft.entity.monster.piglin.PiglinTasks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;

public class PiglinMobsSensor extends Sensor<LivingEntity> {
   public Set<MemoryModuleType<?>> getUsedMemories() {
      return ImmutableSet.of(MemoryModuleType.VISIBLE_MOBS, MemoryModuleType.MOBS, MemoryModuleType.field_234077_K_, MemoryModuleType.field_234088_V_, MemoryModuleType.field_234096_ad_, MemoryModuleType.field_234085_S_, MemoryModuleType.field_234086_T_, MemoryModuleType.field_234090_X_, MemoryModuleType.field_234089_W_, MemoryModuleType.field_234094_ab_, MemoryModuleType.field_234095_ac_, MemoryModuleType.field_234098_af_);
   }

   protected void update(ServerWorld worldIn, LivingEntity entityIn) {
      Brain<?> brain = entityIn.getBrain();
      brain.setMemory(MemoryModuleType.field_234098_af_, func_234126_c_(worldIn, entityIn));
      Optional<MobEntity> optional = Optional.empty();
      Optional<HoglinEntity> optional1 = Optional.empty();
      Optional<HoglinEntity> optional2 = Optional.empty();
      Optional<PiglinEntity> optional3 = Optional.empty();
      Optional<LivingEntity> optional4 = Optional.empty();
      Optional<PlayerEntity> optional5 = Optional.empty();
      Optional<PlayerEntity> optional6 = Optional.empty();
      int i = 0;
      List<AbstractPiglinEntity> list = Lists.newArrayList();
      List<AbstractPiglinEntity> list1 = Lists.newArrayList();

      for(LivingEntity livingentity : brain.getMemory(MemoryModuleType.VISIBLE_MOBS).orElse(ImmutableList.of())) {
         if (livingentity instanceof HoglinEntity) {
            HoglinEntity hoglinentity = (HoglinEntity)livingentity;
            if (hoglinentity.isChild() && !optional2.isPresent()) {
               optional2 = Optional.of(hoglinentity);
            } else if (hoglinentity.func_234363_eJ_()) {
               ++i;
               if (!optional1.isPresent() && hoglinentity.func_234365_eM_()) {
                  optional1 = Optional.of(hoglinentity);
               }
            }
         } else if (livingentity instanceof PiglinBruteEntity) {
            list.add((PiglinBruteEntity)livingentity);
         } else if (livingentity instanceof PiglinEntity) {
            PiglinEntity piglinentity = (PiglinEntity)livingentity;
            if (piglinentity.isChild() && !optional3.isPresent()) {
               optional3 = Optional.of(piglinentity);
            } else if (piglinentity.func_242337_eM()) {
               list.add(piglinentity);
            }
         } else if (livingentity instanceof PlayerEntity) {
            PlayerEntity playerentity = (PlayerEntity)livingentity;
            if (!optional5.isPresent() && EntityPredicates.field_233583_f_.test(livingentity) && !PiglinTasks.func_234460_a_(playerentity)) {
               optional5 = Optional.of(playerentity);
            }

            if (!optional6.isPresent() && !playerentity.isSpectator() && PiglinTasks.func_234482_b_(playerentity)) {
               optional6 = Optional.of(playerentity);
            }
         } else if (optional.isPresent() || !(livingentity instanceof WitherSkeletonEntity) && !(livingentity instanceof WitherEntity)) {
            if (!optional4.isPresent() && PiglinTasks.func_234459_a_(livingentity.getType())) {
               optional4 = Optional.of(livingentity);
            }
         } else {
            optional = Optional.of((MobEntity)livingentity);
         }
      }

      for(LivingEntity livingentity1 : brain.getMemory(MemoryModuleType.MOBS).orElse(ImmutableList.of())) {
         if (livingentity1 instanceof AbstractPiglinEntity && ((AbstractPiglinEntity)livingentity1).func_242337_eM()) {
            list1.add((AbstractPiglinEntity)livingentity1);
         }
      }

      brain.setMemory(MemoryModuleType.field_234077_K_, optional);
      brain.setMemory(MemoryModuleType.field_234085_S_, optional1);
      brain.setMemory(MemoryModuleType.field_234086_T_, optional2);
      brain.setMemory(MemoryModuleType.field_234093_aa_, optional4);
      brain.setMemory(MemoryModuleType.field_234088_V_, optional5);
      brain.setMemory(MemoryModuleType.field_234096_ad_, optional6);
      brain.setMemory(MemoryModuleType.field_234089_W_, list1);
      brain.setMemory(MemoryModuleType.field_234090_X_, list);
      brain.setMemory(MemoryModuleType.field_234094_ab_, list.size());
      brain.setMemory(MemoryModuleType.field_234095_ac_, i);
   }

   private static Optional<BlockPos> func_234126_c_(ServerWorld p_234126_0_, LivingEntity p_234126_1_) {
      return BlockPos.func_239584_a_(p_234126_1_.func_233580_cy_(), 8, 4, (p_234125_1_) -> {
         return func_241391_a_(p_234126_0_, p_234125_1_);
      });
   }

   private static boolean func_241391_a_(ServerWorld p_241391_0_, BlockPos p_241391_1_) {
      BlockState blockstate = p_241391_0_.getBlockState(p_241391_1_);
      boolean flag = blockstate.func_235714_a_(BlockTags.field_232865_O_);
      return flag && blockstate.isIn(Blocks.field_235367_mf_) ? CampfireBlock.func_226915_i_(blockstate) : flag;
   }
}