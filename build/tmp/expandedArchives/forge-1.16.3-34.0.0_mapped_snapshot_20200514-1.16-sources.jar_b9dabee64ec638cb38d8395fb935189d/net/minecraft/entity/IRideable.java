package net.minecraft.entity;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;

public interface IRideable {
   boolean boost();

   void func_230267_a__(Vector3d p_230267_1_);

   float func_230265_N__();

   default boolean func_233622_a_(MobEntity p_233622_1_, BoostHelper p_233622_2_, Vector3d p_233622_3_) {
      if (!p_233622_1_.isAlive()) {
         return false;
      } else {
         Entity entity = p_233622_1_.getPassengers().isEmpty() ? null : p_233622_1_.getPassengers().get(0);
         if (p_233622_1_.isBeingRidden() && p_233622_1_.canBeSteered() && entity instanceof PlayerEntity) {
            p_233622_1_.rotationYaw = entity.rotationYaw;
            p_233622_1_.prevRotationYaw = p_233622_1_.rotationYaw;
            p_233622_1_.rotationPitch = entity.rotationPitch * 0.5F;
            p_233622_1_.setRotation(p_233622_1_.rotationYaw, p_233622_1_.rotationPitch);
            p_233622_1_.renderYawOffset = p_233622_1_.rotationYaw;
            p_233622_1_.rotationYawHead = p_233622_1_.rotationYaw;
            p_233622_1_.stepHeight = 1.0F;
            p_233622_1_.jumpMovementFactor = p_233622_1_.getAIMoveSpeed() * 0.1F;
            if (p_233622_2_.field_233610_a_ && p_233622_2_.field_233611_b_++ > p_233622_2_.field_233612_c_) {
               p_233622_2_.field_233610_a_ = false;
            }

            if (p_233622_1_.canPassengerSteer()) {
               float f = this.func_230265_N__();
               if (p_233622_2_.field_233610_a_) {
                  f += f * 1.15F * MathHelper.sin((float)p_233622_2_.field_233611_b_ / (float)p_233622_2_.field_233612_c_ * (float)Math.PI);
               }

               p_233622_1_.setAIMoveSpeed(f);
               this.func_230267_a__(new Vector3d(0.0D, 0.0D, 1.0D));
               p_233622_1_.newPosRotationIncrements = 0;
            } else {
               p_233622_1_.func_233629_a_(p_233622_1_, false);
               p_233622_1_.setMotion(Vector3d.ZERO);
            }

            return true;
         } else {
            p_233622_1_.stepHeight = 0.5F;
            p_233622_1_.jumpMovementFactor = 0.02F;
            this.func_230267_a__(p_233622_3_);
            return false;
         }
      }
   }
}