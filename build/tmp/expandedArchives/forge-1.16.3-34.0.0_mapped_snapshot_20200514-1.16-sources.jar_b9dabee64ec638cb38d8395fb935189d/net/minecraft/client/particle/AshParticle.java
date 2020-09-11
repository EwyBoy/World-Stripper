package net.minecraft.client.particle;

import net.minecraft.client.world.ClientWorld;
import net.minecraft.particles.BasicParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class AshParticle extends RisingParticle {
   protected AshParticle(ClientWorld p_i232339_1_, double p_i232339_2_, double p_i232339_4_, double p_i232339_6_, double p_i232339_8_, double p_i232339_10_, double p_i232339_12_, float p_i232339_14_, IAnimatedSprite p_i232339_15_) {
      super(p_i232339_1_, p_i232339_2_, p_i232339_4_, p_i232339_6_, 0.1F, -0.1F, 0.1F, p_i232339_8_, p_i232339_10_, p_i232339_12_, p_i232339_14_, p_i232339_15_, 0.5F, 20, -0.004D, false);
   }

   @OnlyIn(Dist.CLIENT)
   public static class Factory implements IParticleFactory<BasicParticleType> {
      private final IAnimatedSprite field_239174_a_;

      public Factory(IAnimatedSprite p_i232340_1_) {
         this.field_239174_a_ = p_i232340_1_;
      }

      public Particle makeParticle(BasicParticleType typeIn, ClientWorld worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
         return new AshParticle(worldIn, x, y, z, 0.0D, 0.0D, 0.0D, 1.0F, this.field_239174_a_);
      }
   }
}