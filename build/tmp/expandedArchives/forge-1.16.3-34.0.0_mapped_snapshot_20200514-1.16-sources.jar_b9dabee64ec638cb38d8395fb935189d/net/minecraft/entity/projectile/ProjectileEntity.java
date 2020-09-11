package net.minecraft.entity.projectile;

import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public abstract class ProjectileEntity extends Entity {
   private UUID field_234609_b_;
   private int field_234610_c_;
   private boolean field_234611_d_;

   ProjectileEntity(EntityType<? extends ProjectileEntity> p_i231584_1_, World p_i231584_2_) {
      super(p_i231584_1_, p_i231584_2_);
   }

   public void setShooter(@Nullable Entity entityIn) {
      if (entityIn != null) {
         this.field_234609_b_ = entityIn.getUniqueID();
         this.field_234610_c_ = entityIn.getEntityId();
      }

   }

   @Nullable
   public Entity func_234616_v_() {
      if (this.field_234609_b_ != null && this.world instanceof ServerWorld) {
         return ((ServerWorld)this.world).getEntityByUuid(this.field_234609_b_);
      } else {
         return this.field_234610_c_ != 0 ? this.world.getEntityByID(this.field_234610_c_) : null;
      }
   }

   protected void writeAdditional(CompoundNBT compound) {
      if (this.field_234609_b_ != null) {
         compound.putUniqueId("Owner", this.field_234609_b_);
      }

      if (this.field_234611_d_) {
         compound.putBoolean("LeftOwner", true);
      }

   }

   /**
    * (abstract) Protected helper method to read subclass entity data from NBT.
    */
   protected void readAdditional(CompoundNBT compound) {
      if (compound.hasUniqueId("Owner")) {
         this.field_234609_b_ = compound.getUniqueId("Owner");
      }

      this.field_234611_d_ = compound.getBoolean("LeftOwner");
   }

   /**
    * Called to update the entity's position/logic.
    */
   public void tick() {
      if (!this.field_234611_d_) {
         this.field_234611_d_ = this.func_234615_h_();
      }

      super.tick();
   }

   private boolean func_234615_h_() {
      Entity entity = this.func_234616_v_();
      if (entity != null) {
         for(Entity entity1 : this.world.getEntitiesInAABBexcluding(this, this.getBoundingBox().expand(this.getMotion()).grow(1.0D), (p_234613_0_) -> {
            return !p_234613_0_.isSpectator() && p_234613_0_.canBeCollidedWith();
         })) {
            if (entity1.getLowestRidingEntity() == entity.getLowestRidingEntity()) {
               return false;
            }
         }
      }

      return true;
   }

   /**
    * Similar to setArrowHeading, it's point the throwable entity to a x, y, z direction.
    */
   public void shoot(double x, double y, double z, float velocity, float inaccuracy) {
      Vector3d vector3d = (new Vector3d(x, y, z)).normalize().add(this.rand.nextGaussian() * (double)0.0075F * (double)inaccuracy, this.rand.nextGaussian() * (double)0.0075F * (double)inaccuracy, this.rand.nextGaussian() * (double)0.0075F * (double)inaccuracy).scale((double)velocity);
      this.setMotion(vector3d);
      float f = MathHelper.sqrt(horizontalMag(vector3d));
      this.rotationYaw = (float)(MathHelper.atan2(vector3d.x, vector3d.z) * (double)(180F / (float)Math.PI));
      this.rotationPitch = (float)(MathHelper.atan2(vector3d.y, (double)f) * (double)(180F / (float)Math.PI));
      this.prevRotationYaw = this.rotationYaw;
      this.prevRotationPitch = this.rotationPitch;
   }

   public void func_234612_a_(Entity p_234612_1_, float p_234612_2_, float p_234612_3_, float p_234612_4_, float p_234612_5_, float p_234612_6_) {
      float f = -MathHelper.sin(p_234612_3_ * ((float)Math.PI / 180F)) * MathHelper.cos(p_234612_2_ * ((float)Math.PI / 180F));
      float f1 = -MathHelper.sin((p_234612_2_ + p_234612_4_) * ((float)Math.PI / 180F));
      float f2 = MathHelper.cos(p_234612_3_ * ((float)Math.PI / 180F)) * MathHelper.cos(p_234612_2_ * ((float)Math.PI / 180F));
      this.shoot((double)f, (double)f1, (double)f2, p_234612_5_, p_234612_6_);
      Vector3d vector3d = p_234612_1_.getMotion();
      this.setMotion(this.getMotion().add(vector3d.x, p_234612_1_.func_233570_aj_() ? 0.0D : vector3d.y, vector3d.z));
   }

   /**
    * Called when this EntityFireball hits a block or entity.
    */
   protected void onImpact(RayTraceResult result) {
      RayTraceResult.Type raytraceresult$type = result.getType();
      if (raytraceresult$type == RayTraceResult.Type.ENTITY) {
         this.onEntityHit((EntityRayTraceResult)result);
      } else if (raytraceresult$type == RayTraceResult.Type.BLOCK) {
         this.func_230299_a_((BlockRayTraceResult)result);
      }

   }

   /**
    * Called when the arrow hits an entity
    */
   protected void onEntityHit(EntityRayTraceResult p_213868_1_) {
   }

   protected void func_230299_a_(BlockRayTraceResult p_230299_1_) {
      BlockState blockstate = this.world.getBlockState(p_230299_1_.getPos());
      blockstate.onProjectileCollision(this.world, blockstate, p_230299_1_, this);
   }

   /**
    * Updates the entity motion clientside, called by packets from the server
    */
   @OnlyIn(Dist.CLIENT)
   public void setVelocity(double x, double y, double z) {
      this.setMotion(x, y, z);
      if (this.prevRotationPitch == 0.0F && this.prevRotationYaw == 0.0F) {
         float f = MathHelper.sqrt(x * x + z * z);
         this.rotationPitch = (float)(MathHelper.atan2(y, (double)f) * (double)(180F / (float)Math.PI));
         this.rotationYaw = (float)(MathHelper.atan2(x, z) * (double)(180F / (float)Math.PI));
         this.prevRotationPitch = this.rotationPitch;
         this.prevRotationYaw = this.rotationYaw;
         this.setLocationAndAngles(this.getPosX(), this.getPosY(), this.getPosZ(), this.rotationYaw, this.rotationPitch);
      }

   }

   protected boolean func_230298_a_(Entity p_230298_1_) {
      if (!p_230298_1_.isSpectator() && p_230298_1_.isAlive() && p_230298_1_.canBeCollidedWith()) {
         Entity entity = this.func_234616_v_();
         return entity == null || this.field_234611_d_ || !entity.isRidingSameEntity(p_230298_1_);
      } else {
         return false;
      }
   }

   protected void func_234617_x_() {
      Vector3d vector3d = this.getMotion();
      float f = MathHelper.sqrt(horizontalMag(vector3d));
      this.rotationPitch = func_234614_e_(this.prevRotationPitch, (float)(MathHelper.atan2(vector3d.y, (double)f) * (double)(180F / (float)Math.PI)));
      this.rotationYaw = func_234614_e_(this.prevRotationYaw, (float)(MathHelper.atan2(vector3d.x, vector3d.z) * (double)(180F / (float)Math.PI)));
   }

   protected static float func_234614_e_(float p_234614_0_, float p_234614_1_) {
      while(p_234614_1_ - p_234614_0_ < -180.0F) {
         p_234614_0_ -= 360.0F;
      }

      while(p_234614_1_ - p_234614_0_ >= 180.0F) {
         p_234614_0_ += 360.0F;
      }

      return MathHelper.lerp(0.2F, p_234614_0_, p_234614_1_);
   }
}