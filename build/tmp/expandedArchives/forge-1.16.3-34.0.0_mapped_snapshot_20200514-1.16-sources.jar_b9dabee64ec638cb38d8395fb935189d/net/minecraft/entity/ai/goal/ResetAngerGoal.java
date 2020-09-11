package net.minecraft.entity.ai.goal;

import java.util.List;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.IAngerable;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.GameRules;

public class ResetAngerGoal<T extends MobEntity & IAngerable> extends Goal {
   private final T field_241383_a_;
   private final boolean field_241384_b_;
   private int field_241385_c_;

   public ResetAngerGoal(T p_i241234_1_, boolean p_i241234_2_) {
      this.field_241383_a_ = p_i241234_1_;
      this.field_241384_b_ = p_i241234_2_;
   }

   /**
    * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
    * method as well.
    */
   public boolean shouldExecute() {
      return this.field_241383_a_.world.getGameRules().getBoolean(GameRules.field_234896_G_) && this.func_241388_g_();
   }

   private boolean func_241388_g_() {
      return this.field_241383_a_.getRevengeTarget() != null && this.field_241383_a_.getRevengeTarget().getType() == EntityType.PLAYER && this.field_241383_a_.getRevengeTimer() > this.field_241385_c_;
   }

   /**
    * Execute a one shot task or start executing a continuous task
    */
   public void startExecuting() {
      this.field_241385_c_ = this.field_241383_a_.getRevengeTimer();
      this.field_241383_a_.func_241355_J__();
      if (this.field_241384_b_) {
         this.func_241389_h_().stream().filter((p_241387_1_) -> {
            return p_241387_1_ != this.field_241383_a_;
         }).map((p_241386_0_) -> {
            return (IAngerable)p_241386_0_;
         }).forEach(IAngerable::func_241355_J__);
      }

      super.startExecuting();
   }

   private List<MobEntity> func_241389_h_() {
      double d0 = this.field_241383_a_.func_233637_b_(Attributes.field_233819_b_);
      AxisAlignedBB axisalignedbb = AxisAlignedBB.func_241549_a_(this.field_241383_a_.getPositionVec()).grow(d0, 10.0D, d0);
      return this.field_241383_a_.world.getLoadedEntitiesWithinAABB(this.field_241383_a_.getClass(), axisalignedbb);
   }
}