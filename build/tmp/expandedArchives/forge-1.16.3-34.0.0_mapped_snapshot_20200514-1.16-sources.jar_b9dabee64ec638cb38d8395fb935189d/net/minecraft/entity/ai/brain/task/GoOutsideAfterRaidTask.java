package net.minecraft.entity.ai.brain.task;

import net.minecraft.entity.LivingEntity;
import net.minecraft.world.raid.Raid;
import net.minecraft.world.server.ServerWorld;

public class GoOutsideAfterRaidTask extends MoveToSkylightTask {
   public GoOutsideAfterRaidTask(float p_i50365_1_) {
      super(p_i50365_1_);
   }

   protected boolean shouldExecute(ServerWorld worldIn, LivingEntity owner) {
      Raid raid = worldIn.findRaid(owner.func_233580_cy_());
      return raid != null && raid.isVictory() && super.shouldExecute(worldIn, owner);
   }
}