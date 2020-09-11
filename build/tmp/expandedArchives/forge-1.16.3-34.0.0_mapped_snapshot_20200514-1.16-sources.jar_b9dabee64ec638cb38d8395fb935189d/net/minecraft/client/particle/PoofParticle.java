package net.minecraft.client.particle;

import net.minecraft.client.world.ClientWorld;
import net.minecraft.particles.BasicParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class PoofParticle extends SpriteTexturedParticle {
   private final IAnimatedSprite field_217581_C;

   protected PoofParticle(ClientWorld p_i232384_1_, double p_i232384_2_, double p_i232384_4_, double p_i232384_6_, double p_i232384_8_, double p_i232384_10_, double p_i232384_12_, IAnimatedSprite p_i232384_14_) {
      super(p_i232384_1_, p_i232384_2_, p_i232384_4_, p_i232384_6_);
      this.field_217581_C = p_i232384_14_;
      this.motionX = p_i232384_8_ + (Math.random() * 2.0D - 1.0D) * (double)0.05F;
      this.motionY = p_i232384_10_ + (Math.random() * 2.0D - 1.0D) * (double)0.05F;
      this.motionZ = p_i232384_12_ + (Math.random() * 2.0D - 1.0D) * (double)0.05F;
      float f = this.rand.nextFloat() * 0.3F + 0.7F;
      this.particleRed = f;
      this.particleGreen = f;
      this.particleBlue = f;
      this.particleScale = 0.1F * (this.rand.nextFloat() * this.rand.nextFloat() * 6.0F + 1.0F);
      this.maxAge = (int)(16.0D / ((double)this.rand.nextFloat() * 0.8D + 0.2D)) + 2;
      this.selectSpriteWithAge(p_i232384_14_);
   }

   public IParticleRenderType getRenderType() {
      return IParticleRenderType.PARTICLE_SHEET_OPAQUE;
   }

   public void tick() {
      this.prevPosX = this.posX;
      this.prevPosY = this.posY;
      this.prevPosZ = this.posZ;
      if (this.age++ >= this.maxAge) {
         this.setExpired();
      } else {
         this.selectSpriteWithAge(this.field_217581_C);
         this.motionY += 0.004D;
         this.move(this.motionX, this.motionY, this.motionZ);
         this.motionX *= (double)0.9F;
         this.motionY *= (double)0.9F;
         this.motionZ *= (double)0.9F;
         if (this.onGround) {
            this.motionX *= (double)0.7F;
            this.motionZ *= (double)0.7F;
         }

      }
   }

   @OnlyIn(Dist.CLIENT)
   public static class Factory implements IParticleFactory<BasicParticleType> {
      private final IAnimatedSprite spriteSet;

      public Factory(IAnimatedSprite p_i49913_1_) {
         this.spriteSet = p_i49913_1_;
      }

      public Particle makeParticle(BasicParticleType typeIn, ClientWorld worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
         return new PoofParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, this.spriteSet);
      }
   }
}