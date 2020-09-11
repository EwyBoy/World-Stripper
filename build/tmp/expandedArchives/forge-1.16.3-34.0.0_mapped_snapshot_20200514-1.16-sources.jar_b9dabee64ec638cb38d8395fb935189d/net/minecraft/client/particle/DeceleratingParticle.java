package net.minecraft.client.particle;

import net.minecraft.client.world.ClientWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class DeceleratingParticle extends SpriteTexturedParticle {
   protected DeceleratingParticle(ClientWorld p_i232421_1_, double p_i232421_2_, double p_i232421_4_, double p_i232421_6_, double p_i232421_8_, double p_i232421_10_, double p_i232421_12_) {
      super(p_i232421_1_, p_i232421_2_, p_i232421_4_, p_i232421_6_, p_i232421_8_, p_i232421_10_, p_i232421_12_);
      this.motionX = this.motionX * (double)0.01F + p_i232421_8_;
      this.motionY = this.motionY * (double)0.01F + p_i232421_10_;
      this.motionZ = this.motionZ * (double)0.01F + p_i232421_12_;
      this.posX += (double)((this.rand.nextFloat() - this.rand.nextFloat()) * 0.05F);
      this.posY += (double)((this.rand.nextFloat() - this.rand.nextFloat()) * 0.05F);
      this.posZ += (double)((this.rand.nextFloat() - this.rand.nextFloat()) * 0.05F);
      this.maxAge = (int)(8.0D / (Math.random() * 0.8D + 0.2D)) + 4;
   }

   public void tick() {
      this.prevPosX = this.posX;
      this.prevPosY = this.posY;
      this.prevPosZ = this.posZ;
      if (this.age++ >= this.maxAge) {
         this.setExpired();
      } else {
         this.move(this.motionX, this.motionY, this.motionZ);
         this.motionX *= (double)0.96F;
         this.motionY *= (double)0.96F;
         this.motionZ *= (double)0.96F;
         if (this.onGround) {
            this.motionX *= (double)0.7F;
            this.motionZ *= (double)0.7F;
         }

      }
   }
}