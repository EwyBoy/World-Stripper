package net.minecraft.client.particle;

import net.minecraft.client.world.ClientWorld;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class FlameParticle extends DeceleratingParticle {
   private FlameParticle(ClientWorld p_i232392_1_, double p_i232392_2_, double p_i232392_4_, double p_i232392_6_, double p_i232392_8_, double p_i232392_10_, double p_i232392_12_) {
      super(p_i232392_1_, p_i232392_2_, p_i232392_4_, p_i232392_6_, p_i232392_8_, p_i232392_10_, p_i232392_12_);
   }

   public IParticleRenderType getRenderType() {
      return IParticleRenderType.PARTICLE_SHEET_OPAQUE;
   }

   public void move(double x, double y, double z) {
      this.setBoundingBox(this.getBoundingBox().offset(x, y, z));
      this.resetPositionToBB();
   }

   public float getScale(float scaleFactor) {
      float f = ((float)this.age + scaleFactor) / (float)this.maxAge;
      return this.particleScale * (1.0F - f * f * 0.5F);
   }

   public int getBrightnessForRender(float partialTick) {
      float f = ((float)this.age + partialTick) / (float)this.maxAge;
      f = MathHelper.clamp(f, 0.0F, 1.0F);
      int i = super.getBrightnessForRender(partialTick);
      int j = i & 255;
      int k = i >> 16 & 255;
      j = j + (int)(f * 15.0F * 16.0F);
      if (j > 240) {
         j = 240;
      }

      return j | k << 16;
   }

   @OnlyIn(Dist.CLIENT)
   public static class Factory implements IParticleFactory<BasicParticleType> {
      private final IAnimatedSprite spriteSet;

      public Factory(IAnimatedSprite p_i50823_1_) {
         this.spriteSet = p_i50823_1_;
      }

      public Particle makeParticle(BasicParticleType typeIn, ClientWorld worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
         FlameParticle flameparticle = new FlameParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed);
         flameparticle.selectSpriteRandomly(this.spriteSet);
         return flameparticle;
      }
   }
}