package net.minecraft.client.particle;

import net.minecraft.client.world.ClientWorld;
import net.minecraft.particles.BasicParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SpitParticle extends PoofParticle {
   private SpitParticle(ClientWorld p_i232431_1_, double p_i232431_2_, double p_i232431_4_, double p_i232431_6_, double p_i232431_8_, double p_i232431_10_, double p_i232431_12_, IAnimatedSprite p_i232431_14_) {
      super(p_i232431_1_, p_i232431_2_, p_i232431_4_, p_i232431_6_, p_i232431_8_, p_i232431_10_, p_i232431_12_, p_i232431_14_);
      this.particleGravity = 0.5F;
   }

   public void tick() {
      super.tick();
      this.motionY -= 0.004D + 0.04D * (double)this.particleGravity;
   }

   @OnlyIn(Dist.CLIENT)
   public static class Factory implements IParticleFactory<BasicParticleType> {
      private final IAnimatedSprite spriteSet;

      public Factory(IAnimatedSprite p_i50812_1_) {
         this.spriteSet = p_i50812_1_;
      }

      public Particle makeParticle(BasicParticleType typeIn, ClientWorld worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
         return new SpitParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, this.spriteSet);
      }
   }
}