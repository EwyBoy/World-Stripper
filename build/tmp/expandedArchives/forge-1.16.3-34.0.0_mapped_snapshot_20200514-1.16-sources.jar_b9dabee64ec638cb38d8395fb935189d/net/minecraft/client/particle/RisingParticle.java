package net.minecraft.client.particle;

import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RisingParticle extends SpriteTexturedParticle {
   private final IAnimatedSprite field_239175_a_;
   private final double field_239176_b_;

   protected RisingParticle(ClientWorld p_i232345_1_, double p_i232345_2_, double p_i232345_4_, double p_i232345_6_, float p_i232345_8_, float p_i232345_9_, float p_i232345_10_, double p_i232345_11_, double p_i232345_13_, double p_i232345_15_, float p_i232345_17_, IAnimatedSprite p_i232345_18_, float p_i232345_19_, int p_i232345_20_, double p_i232345_21_, boolean p_i232345_23_) {
      super(p_i232345_1_, p_i232345_2_, p_i232345_4_, p_i232345_6_, 0.0D, 0.0D, 0.0D);
      this.field_239176_b_ = p_i232345_21_;
      this.field_239175_a_ = p_i232345_18_;
      this.motionX *= (double)p_i232345_8_;
      this.motionY *= (double)p_i232345_9_;
      this.motionZ *= (double)p_i232345_10_;
      this.motionX += p_i232345_11_;
      this.motionY += p_i232345_13_;
      this.motionZ += p_i232345_15_;
      float f = p_i232345_1_.rand.nextFloat() * p_i232345_19_;
      this.particleRed = f;
      this.particleGreen = f;
      this.particleBlue = f;
      this.particleScale *= 0.75F * p_i232345_17_;
      this.maxAge = (int)((double)p_i232345_20_ / ((double)p_i232345_1_.rand.nextFloat() * 0.8D + 0.2D));
      this.maxAge = (int)((float)this.maxAge * p_i232345_17_);
      this.maxAge = Math.max(this.maxAge, 1);
      this.selectSpriteWithAge(p_i232345_18_);
      this.canCollide = p_i232345_23_;
   }

   public IParticleRenderType getRenderType() {
      return IParticleRenderType.PARTICLE_SHEET_OPAQUE;
   }

   public float getScale(float scaleFactor) {
      return this.particleScale * MathHelper.clamp(((float)this.age + scaleFactor) / (float)this.maxAge * 32.0F, 0.0F, 1.0F);
   }

   public void tick() {
      this.prevPosX = this.posX;
      this.prevPosY = this.posY;
      this.prevPosZ = this.posZ;
      if (this.age++ >= this.maxAge) {
         this.setExpired();
      } else {
         this.selectSpriteWithAge(this.field_239175_a_);
         this.motionY += this.field_239176_b_;
         this.move(this.motionX, this.motionY, this.motionZ);
         if (this.posY == this.prevPosY) {
            this.motionX *= 1.1D;
            this.motionZ *= 1.1D;
         }

         this.motionX *= (double)0.96F;
         this.motionY *= (double)0.96F;
         this.motionZ *= (double)0.96F;
         if (this.onGround) {
            this.motionX *= (double)0.7F;
            this.motionZ *= (double)0.7F;
         }

      }
   }
}