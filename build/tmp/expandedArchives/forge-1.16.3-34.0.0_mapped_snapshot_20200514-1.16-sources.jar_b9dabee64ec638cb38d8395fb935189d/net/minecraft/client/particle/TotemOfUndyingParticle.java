package net.minecraft.client.particle;

import net.minecraft.client.world.ClientWorld;
import net.minecraft.particles.BasicParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class TotemOfUndyingParticle extends SimpleAnimatedParticle {
   private TotemOfUndyingParticle(ClientWorld p_i232449_1_, double p_i232449_2_, double p_i232449_4_, double p_i232449_6_, double p_i232449_8_, double p_i232449_10_, double p_i232449_12_, IAnimatedSprite p_i232449_14_) {
      super(p_i232449_1_, p_i232449_2_, p_i232449_4_, p_i232449_6_, p_i232449_14_, -0.05F);
      this.motionX = p_i232449_8_;
      this.motionY = p_i232449_10_;
      this.motionZ = p_i232449_12_;
      this.particleScale *= 0.75F;
      this.maxAge = 60 + this.rand.nextInt(12);
      this.selectSpriteWithAge(p_i232449_14_);
      if (this.rand.nextInt(4) == 0) {
         this.setColor(0.6F + this.rand.nextFloat() * 0.2F, 0.6F + this.rand.nextFloat() * 0.3F, this.rand.nextFloat() * 0.2F);
      } else {
         this.setColor(0.1F + this.rand.nextFloat() * 0.2F, 0.4F + this.rand.nextFloat() * 0.3F, this.rand.nextFloat() * 0.2F);
      }

      this.setBaseAirFriction(0.6F);
   }

   @OnlyIn(Dist.CLIENT)
   public static class Factory implements IParticleFactory<BasicParticleType> {
      private final IAnimatedSprite spriteSet;

      public Factory(IAnimatedSprite p_i50316_1_) {
         this.spriteSet = p_i50316_1_;
      }

      public Particle makeParticle(BasicParticleType typeIn, ClientWorld worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
         return new TotemOfUndyingParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, this.spriteSet);
      }
   }
}