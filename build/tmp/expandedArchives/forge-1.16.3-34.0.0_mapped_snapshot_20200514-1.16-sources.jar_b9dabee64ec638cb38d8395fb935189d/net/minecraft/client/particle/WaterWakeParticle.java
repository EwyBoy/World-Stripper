package net.minecraft.client.particle;

import net.minecraft.client.world.ClientWorld;
import net.minecraft.particles.BasicParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class WaterWakeParticle extends SpriteTexturedParticle {
   private final IAnimatedSprite field_217589_C;

   private WaterWakeParticle(ClientWorld p_i232454_1_, double p_i232454_2_, double p_i232454_4_, double p_i232454_6_, double p_i232454_8_, double p_i232454_10_, double p_i232454_12_, IAnimatedSprite p_i232454_14_) {
      super(p_i232454_1_, p_i232454_2_, p_i232454_4_, p_i232454_6_, 0.0D, 0.0D, 0.0D);
      this.field_217589_C = p_i232454_14_;
      this.motionX *= (double)0.3F;
      this.motionY = Math.random() * (double)0.2F + (double)0.1F;
      this.motionZ *= (double)0.3F;
      this.setSize(0.01F, 0.01F);
      this.maxAge = (int)(8.0D / (Math.random() * 0.8D + 0.2D));
      this.selectSpriteWithAge(p_i232454_14_);
      this.particleGravity = 0.0F;
      this.motionX = p_i232454_8_;
      this.motionY = p_i232454_10_;
      this.motionZ = p_i232454_12_;
   }

   public IParticleRenderType getRenderType() {
      return IParticleRenderType.PARTICLE_SHEET_OPAQUE;
   }

   public void tick() {
      this.prevPosX = this.posX;
      this.prevPosY = this.posY;
      this.prevPosZ = this.posZ;
      int i = 60 - this.maxAge;
      if (this.maxAge-- <= 0) {
         this.setExpired();
      } else {
         this.motionY -= (double)this.particleGravity;
         this.move(this.motionX, this.motionY, this.motionZ);
         this.motionX *= (double)0.98F;
         this.motionY *= (double)0.98F;
         this.motionZ *= (double)0.98F;
         float f = (float)i * 0.001F;
         this.setSize(f, f);
         this.setSprite(this.field_217589_C.get(i % 4, 4));
      }
   }

   @OnlyIn(Dist.CLIENT)
   public static class Factory implements IParticleFactory<BasicParticleType> {
      private final IAnimatedSprite spriteSet;

      public Factory(IAnimatedSprite p_i51267_1_) {
         this.spriteSet = p_i51267_1_;
      }

      public Particle makeParticle(BasicParticleType typeIn, ClientWorld worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
         return new WaterWakeParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, this.spriteSet);
      }
   }
}