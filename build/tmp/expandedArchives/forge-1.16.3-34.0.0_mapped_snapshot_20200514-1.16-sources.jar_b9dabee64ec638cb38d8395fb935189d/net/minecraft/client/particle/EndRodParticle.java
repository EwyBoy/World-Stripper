package net.minecraft.client.particle;

import net.minecraft.client.world.ClientWorld;
import net.minecraft.particles.BasicParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class EndRodParticle extends SimpleAnimatedParticle {
   private EndRodParticle(ClientWorld p_i232382_1_, double p_i232382_2_, double p_i232382_4_, double p_i232382_6_, double p_i232382_8_, double p_i232382_10_, double p_i232382_12_, IAnimatedSprite p_i232382_14_) {
      super(p_i232382_1_, p_i232382_2_, p_i232382_4_, p_i232382_6_, p_i232382_14_, -5.0E-4F);
      this.motionX = p_i232382_8_;
      this.motionY = p_i232382_10_;
      this.motionZ = p_i232382_12_;
      this.particleScale *= 0.75F;
      this.maxAge = 60 + this.rand.nextInt(12);
      this.setColorFade(15916745);
      this.selectSpriteWithAge(p_i232382_14_);
   }

   public void move(double x, double y, double z) {
      this.setBoundingBox(this.getBoundingBox().offset(x, y, z));
      this.resetPositionToBB();
   }

   @OnlyIn(Dist.CLIENT)
   public static class Factory implements IParticleFactory<BasicParticleType> {
      private final IAnimatedSprite spriteSet;

      public Factory(IAnimatedSprite p_i50058_1_) {
         this.spriteSet = p_i50058_1_;
      }

      public Particle makeParticle(BasicParticleType typeIn, ClientWorld worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
         return new EndRodParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, this.spriteSet);
      }
   }
}