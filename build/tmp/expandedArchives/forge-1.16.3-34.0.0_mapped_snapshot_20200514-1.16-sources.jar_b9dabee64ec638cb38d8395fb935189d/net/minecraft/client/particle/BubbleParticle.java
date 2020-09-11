package net.minecraft.client.particle;

import net.minecraft.client.world.ClientWorld;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BubbleParticle extends SpriteTexturedParticle {
   private BubbleParticle(ClientWorld p_i232351_1_, double p_i232351_2_, double p_i232351_4_, double p_i232351_6_, double p_i232351_8_, double p_i232351_10_, double p_i232351_12_) {
      super(p_i232351_1_, p_i232351_2_, p_i232351_4_, p_i232351_6_);
      this.setSize(0.02F, 0.02F);
      this.particleScale *= this.rand.nextFloat() * 0.6F + 0.2F;
      this.motionX = p_i232351_8_ * (double)0.2F + (Math.random() * 2.0D - 1.0D) * (double)0.02F;
      this.motionY = p_i232351_10_ * (double)0.2F + (Math.random() * 2.0D - 1.0D) * (double)0.02F;
      this.motionZ = p_i232351_12_ * (double)0.2F + (Math.random() * 2.0D - 1.0D) * (double)0.02F;
      this.maxAge = (int)(8.0D / (Math.random() * 0.8D + 0.2D));
   }

   public void tick() {
      this.prevPosX = this.posX;
      this.prevPosY = this.posY;
      this.prevPosZ = this.posZ;
      if (this.maxAge-- <= 0) {
         this.setExpired();
      } else {
         this.motionY += 0.002D;
         this.move(this.motionX, this.motionY, this.motionZ);
         this.motionX *= (double)0.85F;
         this.motionY *= (double)0.85F;
         this.motionZ *= (double)0.85F;
         if (!this.world.getFluidState(new BlockPos(this.posX, this.posY, this.posZ)).isTagged(FluidTags.WATER)) {
            this.setExpired();
         }

      }
   }

   public IParticleRenderType getRenderType() {
      return IParticleRenderType.PARTICLE_SHEET_OPAQUE;
   }

   @OnlyIn(Dist.CLIENT)
   public static class Factory implements IParticleFactory<BasicParticleType> {
      private final IAnimatedSprite spriteSet;

      public Factory(IAnimatedSprite p_i50227_1_) {
         this.spriteSet = p_i50227_1_;
      }

      public Particle makeParticle(BasicParticleType typeIn, ClientWorld worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
         BubbleParticle bubbleparticle = new BubbleParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed);
         bubbleparticle.selectSpriteRandomly(this.spriteSet);
         return bubbleparticle;
      }
   }
}