package net.minecraft.client.particle;

import net.minecraft.client.world.ClientWorld;
import net.minecraft.particles.RedstoneParticleData;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RedstoneParticle extends SpriteTexturedParticle {
   private final IAnimatedSprite field_217575_C;

   private RedstoneParticle(ClientWorld p_i232378_1_, double p_i232378_2_, double p_i232378_4_, double p_i232378_6_, double p_i232378_8_, double p_i232378_10_, double p_i232378_12_, RedstoneParticleData p_i232378_14_, IAnimatedSprite p_i232378_15_) {
      super(p_i232378_1_, p_i232378_2_, p_i232378_4_, p_i232378_6_, p_i232378_8_, p_i232378_10_, p_i232378_12_);
      this.field_217575_C = p_i232378_15_;
      this.motionX *= (double)0.1F;
      this.motionY *= (double)0.1F;
      this.motionZ *= (double)0.1F;
      float f = (float)Math.random() * 0.4F + 0.6F;
      this.particleRed = ((float)(Math.random() * (double)0.2F) + 0.8F) * p_i232378_14_.getRed() * f;
      this.particleGreen = ((float)(Math.random() * (double)0.2F) + 0.8F) * p_i232378_14_.getGreen() * f;
      this.particleBlue = ((float)(Math.random() * (double)0.2F) + 0.8F) * p_i232378_14_.getBlue() * f;
      this.particleScale *= 0.75F * p_i232378_14_.getAlpha();
      int i = (int)(8.0D / (Math.random() * 0.8D + 0.2D));
      this.maxAge = (int)Math.max((float)i * p_i232378_14_.getAlpha(), 1.0F);
      this.selectSpriteWithAge(p_i232378_15_);
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
         this.selectSpriteWithAge(this.field_217575_C);
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

   @OnlyIn(Dist.CLIENT)
   public static class Factory implements IParticleFactory<RedstoneParticleData> {
      private final IAnimatedSprite spriteSet;

      public Factory(IAnimatedSprite p_i50477_1_) {
         this.spriteSet = p_i50477_1_;
      }

      public Particle makeParticle(RedstoneParticleData typeIn, ClientWorld worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
         return new RedstoneParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, typeIn, this.spriteSet);
      }
   }
}