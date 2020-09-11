package net.minecraft.client.particle;

import net.minecraft.client.world.ClientWorld;
import net.minecraft.particles.BasicParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SplashParticle extends RainParticle {
   private SplashParticle(ClientWorld p_i232433_1_, double p_i232433_2_, double p_i232433_4_, double p_i232433_6_, double p_i232433_8_, double p_i232433_10_, double p_i232433_12_) {
      super(p_i232433_1_, p_i232433_2_, p_i232433_4_, p_i232433_6_);
      this.particleGravity = 0.04F;
      if (p_i232433_10_ == 0.0D && (p_i232433_8_ != 0.0D || p_i232433_12_ != 0.0D)) {
         this.motionX = p_i232433_8_;
         this.motionY = 0.1D;
         this.motionZ = p_i232433_12_;
      }

   }

   @OnlyIn(Dist.CLIENT)
   public static class Factory implements IParticleFactory<BasicParticleType> {
      private final IAnimatedSprite spriteSet;

      public Factory(IAnimatedSprite p_i50679_1_) {
         this.spriteSet = p_i50679_1_;
      }

      public Particle makeParticle(BasicParticleType typeIn, ClientWorld worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
         SplashParticle splashparticle = new SplashParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed);
         splashparticle.selectSpriteRandomly(this.spriteSet);
         return splashparticle;
      }
   }
}