package net.minecraft.entity.ai.brain.task;

import com.google.common.collect.ImmutableMap;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.ai.brain.memory.MemoryModuleStatus;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.memory.WalkTarget;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;

public class WalkRandomlyInsideTask extends Task<CreatureEntity> {
   private final float field_233911_b_;

   public WalkRandomlyInsideTask(float p_i50364_1_) {
      super(ImmutableMap.of(MemoryModuleType.WALK_TARGET, MemoryModuleStatus.VALUE_ABSENT));
      this.field_233911_b_ = p_i50364_1_;
   }

   protected boolean shouldExecute(ServerWorld worldIn, CreatureEntity owner) {
      return !worldIn.canSeeSky(owner.func_233580_cy_());
   }

   protected void startExecuting(ServerWorld worldIn, CreatureEntity entityIn, long gameTimeIn) {
      BlockPos blockpos = entityIn.func_233580_cy_();
      List<BlockPos> list = BlockPos.getAllInBox(blockpos.add(-1, -1, -1), blockpos.add(1, 1, 1)).map(BlockPos::toImmutable).collect(Collectors.toList());
      Collections.shuffle(list);
      Optional<BlockPos> optional = list.stream().filter((p_220428_1_) -> {
         return !worldIn.canSeeSky(p_220428_1_);
      }).filter((p_220427_2_) -> {
         return worldIn.isTopSolid(p_220427_2_, entityIn);
      }).filter((p_220429_2_) -> {
         return worldIn.hasNoCollisions(entityIn);
      }).findFirst();
      optional.ifPresent((p_220430_2_) -> {
         entityIn.getBrain().setMemory(MemoryModuleType.WALK_TARGET, new WalkTarget(p_220430_2_, this.field_233911_b_, 0));
      });
   }
}