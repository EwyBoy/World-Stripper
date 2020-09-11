package net.minecraft.client.particle;

import net.minecraft.client.world.ClientWorld;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CritParticle extends SpriteTexturedParticle {
   private CritParticle(ClientWorld p_i232357_1_, double p_i232357_2_, double p_i232357_4_, double p_i232357_6_, double p_i232357_8_, double p_i232357_10_, double p_i232357_12_) {
      super(p_i232357_1_, p_i232357_2_, p_i232357_4_, p_i232357_6_, 0.0D, 0.0D, 0.0D);
      this.motionX *= (double)0.1F;
      this.motionY *= (double)0.1F;
      this.motionZ *= (double)0.1F;
      this.motionX += p_i232357_8_ * 0.4D;
      this.motionY += p_i232357_10_ * 0.4D;
      this.motionZ += p_i232357_12_ * 0.4D;
      float f = (float)(Math.random() * (double)0.3F + (double)0.6F);
      this.particleRed = f;
      this.particleGreen = f;
      this.particleBlue = f;
      this.particleScale *= 0.75F;
      this.maxAge = Math.max((int)(6.0D / (Math.random() * 0.8D + 0.6D)), 1);
      this.canCollide = false;
      this.tick();
   }

   public float getScale(float scaleFactor) {
      return this.particleScale * MathHelper.clamp(((float)this.age + scaleFactor) / (float)this.maxAge * 32.0F, 0.0F, 1.0F);
   }

   public void tick() {
      this.prevPosX = this.posX;
      this.prevPosY = this.posY;
      this.prevPosZ = this.posZ;
      if (this.age++ >= this.maxAge) {
         this.setExpired();
      } else {
         this.move(this.motionX, this.motionY, this.motionZ);
         this.particleGreen = (float)((double)this.particleGreen * 0.96D);
         this.particleBlue = (float)((double)this.particleBlue * 0.9D);
         this.motionX *= (double)0.7F;
         this.motionY *= (double)0.7F;
         this.motionZ *= (double)0.7F;
         this.motionY -= (double)0.02F;
         if (this.onGround) {
            this.motionX *= (double)0.7F;
            this.motionZ *= (double)0.7F;
         }

      }
   }

   public IParticleRenderType getRenderType() {
      return IParticleRenderType.PARTICLE_SHEET_OPAQUE;
   }

   @OnlyIn(Dist.CLIENT)
   public static class DamageIndicatorFactory implements IParticleFactory<BasicParticleType> {
      private final IAnimatedSprite spriteSet;

      public DamageIndicatorFactory(IAnimatedSprite p_i50589_1_) {
         this.spriteSet = p_i50589_1_;
      }

      public Particle makeParticle(BasicParticleType typeIn, ClientWorld worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
         CritParticle critparticle = new CritParticle(worldIn, x, y, z, xSpeed, ySpeed + 1.0D, zSpeed);
         critparticle.setMaxAge(20);
         critparticle.selectSpriteRandomly(this.spriteSet);
         return critparticle;
      }
   }

   @OnlyIn(Dist.CLIENT)
   public static class Factory implements IParticleFactory<BasicParticleType> {
      private final IAnimatedSprite spriteSet;

      public Factory(IAnimatedSprite p_i50587_1_) {
         this.spriteSet = p_i50587_1_;
      }

      public Particle makeParticle(BasicParticleType typeIn, ClientWorld worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
         CritParticle critparticle = new CritParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed);
         critparticle.selectSpriteRandomly(this.spriteSet);
         return critparticle;
      }
   }

   @OnlyIn(Dist.CLIENT)
   public static class MagicFactory implements IParticleFactory<BasicParticleType> {
      private final IAnimatedSprite spriteSet;

      public MagicFactory(IAnimatedSprite p_i50588_1_) {
         this.spriteSet = p_i50588_1_;
      }

      public Particle makeParticle(BasicParticleType typeIn, ClientWorld worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
         CritParticle critparticle = new CritParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed);
         critparticle.particleRed *= 0.3F;
         critparticle.particleGreen *= 0.8F;
         critparticle.selectSpriteRandomly(this.spriteSet);
         return critparticle;
      }
   }
}