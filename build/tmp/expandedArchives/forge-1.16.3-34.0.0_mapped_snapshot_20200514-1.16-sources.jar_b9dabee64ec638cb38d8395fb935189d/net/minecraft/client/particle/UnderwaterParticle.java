package net.minecraft.client.particle;

import java.util.Random;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particles.BasicParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class UnderwaterParticle extends SpriteTexturedParticle {
   private UnderwaterParticle(ClientWorld p_i232437_1_, double p_i232437_2_, double p_i232437_4_, double p_i232437_6_) {
      super(p_i232437_1_, p_i232437_2_, p_i232437_4_ - 0.125D, p_i232437_6_);
      this.particleRed = 0.4F;
      this.particleGreen = 0.4F;
      this.particleBlue = 0.7F;
      this.setSize(0.01F, 0.01F);
      this.particleScale *= this.rand.nextFloat() * 0.6F + 0.2F;
      this.maxAge = (int)(16.0D / (Math.random() * 0.8D + 0.2D));
      this.canCollide = false;
   }

   private UnderwaterParticle(ClientWorld p_i232438_1_, double p_i232438_2_, double p_i232438_4_, double p_i232438_6_, double p_i232438_8_, double p_i232438_10_, double p_i232438_12_) {
      super(p_i232438_1_, p_i232438_2_, p_i232438_4_ - 0.125D, p_i232438_6_, p_i232438_8_, p_i232438_10_, p_i232438_12_);
      this.setSize(0.01F, 0.01F);
      this.particleScale *= this.rand.nextFloat() * 0.6F + 0.6F;
      this.maxAge = (int)(16.0D / (Math.random() * 0.8D + 0.2D));
      this.canCollide = false;
   }

   public IParticleRenderType getRenderType() {
      return IParticleRenderType.PARTICLE_SHEET_OPAQUE;
   }

   public void tick() {
      this.prevPosX = this.posX;
      this.prevPosY = this.posY;
      this.prevPosZ = this.posZ;
      if (this.maxAge-- <= 0) {
         this.setExpired();
      } else {
         this.move(this.motionX, this.motionY, this.motionZ);
      }
   }

   @OnlyIn(Dist.CLIENT)
   public static class CrimsonSporeFactory implements IParticleFactory<BasicParticleType> {
      private final IAnimatedSprite field_239199_a_;

      public CrimsonSporeFactory(IAnimatedSprite p_i232441_1_) {
         this.field_239199_a_ = p_i232441_1_;
      }

      public Particle makeParticle(BasicParticleType typeIn, ClientWorld worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
         Random random = worldIn.rand;
         double d0 = random.nextGaussian() * (double)1.0E-6F;
         double d1 = random.nextGaussian() * (double)1.0E-4F;
         double d2 = random.nextGaussian() * (double)1.0E-6F;
         UnderwaterParticle underwaterparticle = new UnderwaterParticle(worldIn, x, y, z, d0, d1, d2);
         underwaterparticle.selectSpriteRandomly(this.field_239199_a_);
         underwaterparticle.setColor(0.9F, 0.4F, 0.5F);
         return underwaterparticle;
      }
   }

   @OnlyIn(Dist.CLIENT)
   public static class UnderwaterFactory implements IParticleFactory<BasicParticleType> {
      private final IAnimatedSprite field_239200_a_;

      public UnderwaterFactory(IAnimatedSprite p_i232442_1_) {
         this.field_239200_a_ = p_i232442_1_;
      }

      public Particle makeParticle(BasicParticleType typeIn, ClientWorld worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
         UnderwaterParticle underwaterparticle = new UnderwaterParticle(worldIn, x, y, z);
         underwaterparticle.selectSpriteRandomly(this.field_239200_a_);
         return underwaterparticle;
      }
   }

   @OnlyIn(Dist.CLIENT)
   public static class WarpedSporeFactory implements IParticleFactory<BasicParticleType> {
      private final IAnimatedSprite field_239201_a_;

      public WarpedSporeFactory(IAnimatedSprite p_i232443_1_) {
         this.field_239201_a_ = p_i232443_1_;
      }

      public Particle makeParticle(BasicParticleType typeIn, ClientWorld worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
         double d0 = (double)worldIn.rand.nextFloat() * -1.9D * (double)worldIn.rand.nextFloat() * 0.1D;
         UnderwaterParticle underwaterparticle = new UnderwaterParticle(worldIn, x, y, z, 0.0D, d0, 0.0D);
         underwaterparticle.selectSpriteRandomly(this.field_239201_a_);
         underwaterparticle.setColor(0.1F, 0.1F, 0.3F);
         underwaterparticle.setSize(0.001F, 0.001F);
         return underwaterparticle;
      }
   }
}