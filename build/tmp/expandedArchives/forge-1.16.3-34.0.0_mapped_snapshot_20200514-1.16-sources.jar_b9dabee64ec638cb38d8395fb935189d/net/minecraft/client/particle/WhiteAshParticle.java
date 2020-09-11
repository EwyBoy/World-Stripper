package net.minecraft.client.particle;

import java.util.Random;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particles.BasicParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class WhiteAshParticle extends RisingParticle {
   protected WhiteAshParticle(ClientWorld p_i232459_1_, double p_i232459_2_, double p_i232459_4_, double p_i232459_6_, double p_i232459_8_, double p_i232459_10_, double p_i232459_12_, float p_i232459_14_, IAnimatedSprite p_i232459_15_) {
      super(p_i232459_1_, p_i232459_2_, p_i232459_4_, p_i232459_6_, 0.1F, -0.1F, 0.1F, p_i232459_8_, p_i232459_10_, p_i232459_12_, p_i232459_14_, p_i232459_15_, 0.0F, 20, -5.0E-4D, false);
      this.particleRed = 0.7294118F;
      this.particleGreen = 0.69411767F;
      this.particleBlue = 0.7607843F;
   }

   @OnlyIn(Dist.CLIENT)
   public static class Factory implements IParticleFactory<BasicParticleType> {
      private final IAnimatedSprite field_239202_a_;

      public Factory(IAnimatedSprite p_i232460_1_) {
         this.field_239202_a_ = p_i232460_1_;
      }

      public Particle makeParticle(BasicParticleType typeIn, ClientWorld worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
         Random random = worldIn.rand;
         double d0 = (double)random.nextFloat() * -1.9D * (double)random.nextFloat() * 0.1D;
         double d1 = (double)random.nextFloat() * -0.5D * (double)random.nextFloat() * 0.1D * 5.0D;
         double d2 = (double)random.nextFloat() * -1.9D * (double)random.nextFloat() * 0.1D;
         return new WhiteAshParticle(worldIn, x, y, z, d0, d1, d2, 1.0F, this.field_239202_a_);
      }
   }
}