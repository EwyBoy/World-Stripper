package net.minecraft.client.particle;

import net.minecraft.client.world.ClientWorld;
import net.minecraft.particles.BasicParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SmokeParticle extends RisingParticle {
   protected SmokeParticle(ClientWorld p_i232425_1_, double p_i232425_2_, double p_i232425_4_, double p_i232425_6_, double p_i232425_8_, double p_i232425_10_, double p_i232425_12_, float p_i232425_14_, IAnimatedSprite p_i232425_15_) {
      super(p_i232425_1_, p_i232425_2_, p_i232425_4_, p_i232425_6_, 0.1F, 0.1F, 0.1F, p_i232425_8_, p_i232425_10_, p_i232425_12_, p_i232425_14_, p_i232425_15_, 0.3F, 8, 0.004D, true);
   }

   @OnlyIn(Dist.CLIENT)
   public static class Factory implements IParticleFactory<BasicParticleType> {
      private final IAnimatedSprite spriteSet;

      public Factory(IAnimatedSprite p_i51045_1_) {
         this.spriteSet = p_i51045_1_;
      }

      public Particle makeParticle(BasicParticleType typeIn, ClientWorld worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
         return new SmokeParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, 1.0F, this.spriteSet);
      }
   }
}