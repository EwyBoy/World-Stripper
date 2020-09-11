package net.minecraft.client.particle;

import net.minecraft.client.world.ClientWorld;
import net.minecraft.particles.BasicParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ReversePortalParticle extends PortalParticle {
   private ReversePortalParticle(ClientWorld p_i232418_1_, double p_i232418_2_, double p_i232418_4_, double p_i232418_6_, double p_i232418_8_, double p_i232418_10_, double p_i232418_12_) {
      super(p_i232418_1_, p_i232418_2_, p_i232418_4_, p_i232418_6_, p_i232418_8_, p_i232418_10_, p_i232418_12_);
      this.particleScale = (float)((double)this.particleScale * 1.5D);
      this.maxAge = (int)(Math.random() * 2.0D) + 60;
   }

   public float getScale(float scaleFactor) {
      float f = 1.0F - ((float)this.age + scaleFactor) / ((float)this.maxAge * 1.5F);
      return this.particleScale * f;
   }

   public void tick() {
      this.prevPosX = this.posX;
      this.prevPosY = this.posY;
      this.prevPosZ = this.posZ;
      if (this.age++ >= this.maxAge) {
         this.setExpired();
      } else {
         float f = (float)this.age / (float)this.maxAge;
         this.posX += this.motionX * (double)f;
         this.posY += this.motionY * (double)f;
         this.posZ += this.motionZ * (double)f;
      }
   }

   @OnlyIn(Dist.CLIENT)
   public static class Factory implements IParticleFactory<BasicParticleType> {
      private final IAnimatedSprite field_239196_a_;

      public Factory(IAnimatedSprite p_i232420_1_) {
         this.field_239196_a_ = p_i232420_1_;
      }

      public Particle makeParticle(BasicParticleType typeIn, ClientWorld worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
         ReversePortalParticle reverseportalparticle = new ReversePortalParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed);
         reverseportalparticle.selectSpriteRandomly(this.field_239196_a_);
         return reverseportalparticle;
      }
   }
}