package net.minecraft.client.particle;

import net.minecraft.client.world.ClientWorld;
import net.minecraft.particles.BasicParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SoulParticle extends DeceleratingParticle {
   private final IAnimatedSprite field_239197_a_;

   private SoulParticle(ClientWorld p_i232426_1_, double p_i232426_2_, double p_i232426_4_, double p_i232426_6_, double p_i232426_8_, double p_i232426_10_, double p_i232426_12_, IAnimatedSprite p_i232426_14_) {
      super(p_i232426_1_, p_i232426_2_, p_i232426_4_, p_i232426_6_, p_i232426_8_, p_i232426_10_, p_i232426_12_);
      this.field_239197_a_ = p_i232426_14_;
      this.multipleParticleScaleBy(1.5F);
      this.selectSpriteWithAge(p_i232426_14_);
   }

   public IParticleRenderType getRenderType() {
      return IParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
   }

   public void tick() {
      super.tick();
      if (!this.isExpired) {
         this.selectSpriteWithAge(this.field_239197_a_);
      }

   }

   @OnlyIn(Dist.CLIENT)
   public static class Factory implements IParticleFactory<BasicParticleType> {
      private final IAnimatedSprite field_239198_a_;

      public Factory(IAnimatedSprite p_i232428_1_) {
         this.field_239198_a_ = p_i232428_1_;
      }

      public Particle makeParticle(BasicParticleType typeIn, ClientWorld worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
         SoulParticle soulparticle = new SoulParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, this.field_239198_a_);
         soulparticle.setAlphaF(1.0F);
         return soulparticle;
      }
   }
}