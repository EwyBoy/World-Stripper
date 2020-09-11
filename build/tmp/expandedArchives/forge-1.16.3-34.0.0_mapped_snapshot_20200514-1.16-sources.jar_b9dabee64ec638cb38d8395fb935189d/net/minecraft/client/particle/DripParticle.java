package net.minecraft.client.particle;

import net.minecraft.client.world.ClientWorld;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class DripParticle extends SpriteTexturedParticle {
   private final Fluid fluid;
   protected boolean field_239177_a_;

   private DripParticle(ClientWorld p_i232361_1_, double p_i232361_2_, double p_i232361_4_, double p_i232361_6_, Fluid p_i232361_8_) {
      super(p_i232361_1_, p_i232361_2_, p_i232361_4_, p_i232361_6_);
      this.setSize(0.01F, 0.01F);
      this.particleGravity = 0.06F;
      this.fluid = p_i232361_8_;
   }

   public IParticleRenderType getRenderType() {
      return IParticleRenderType.PARTICLE_SHEET_OPAQUE;
   }

   public int getBrightnessForRender(float partialTick) {
      return this.field_239177_a_ ? 240 : super.getBrightnessForRender(partialTick);
   }

   public void tick() {
      this.prevPosX = this.posX;
      this.prevPosY = this.posY;
      this.prevPosZ = this.posZ;
      this.func_217576_g();
      if (!this.isExpired) {
         this.motionY -= (double)this.particleGravity;
         this.move(this.motionX, this.motionY, this.motionZ);
         this.func_217577_h();
         if (!this.isExpired) {
            this.motionX *= (double)0.98F;
            this.motionY *= (double)0.98F;
            this.motionZ *= (double)0.98F;
            BlockPos blockpos = new BlockPos(this.posX, this.posY, this.posZ);
            FluidState fluidstate = this.world.getFluidState(blockpos);
            if (fluidstate.getFluid() == this.fluid && this.posY < (double)((float)blockpos.getY() + fluidstate.getActualHeight(this.world, blockpos))) {
               this.setExpired();
            }

         }
      }
   }

   protected void func_217576_g() {
      if (this.maxAge-- <= 0) {
         this.setExpired();
      }

   }

   protected void func_217577_h() {
   }

   @OnlyIn(Dist.CLIENT)
   static class Dripping extends DripParticle {
      private final IParticleData field_217579_C;

      private Dripping(ClientWorld p_i232365_1_, double p_i232365_2_, double p_i232365_4_, double p_i232365_6_, Fluid p_i232365_8_, IParticleData p_i232365_9_) {
         super(p_i232365_1_, p_i232365_2_, p_i232365_4_, p_i232365_6_, p_i232365_8_);
         this.field_217579_C = p_i232365_9_;
         this.particleGravity *= 0.02F;
         this.maxAge = 40;
      }

      protected void func_217576_g() {
         if (this.maxAge-- <= 0) {
            this.setExpired();
            this.world.addParticle(this.field_217579_C, this.posX, this.posY, this.posZ, this.motionX, this.motionY, this.motionZ);
         }

      }

      protected void func_217577_h() {
         this.motionX *= 0.02D;
         this.motionY *= 0.02D;
         this.motionZ *= 0.02D;
      }
   }

   @OnlyIn(Dist.CLIENT)
   public static class DrippingHoneyFactory implements IParticleFactory<BasicParticleType> {
      protected final IAnimatedSprite field_228337_a_;

      public DrippingHoneyFactory(IAnimatedSprite p_i225960_1_) {
         this.field_228337_a_ = p_i225960_1_;
      }

      public Particle makeParticle(BasicParticleType typeIn, ClientWorld worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
         DripParticle.Dripping dripparticle$dripping = new DripParticle.Dripping(worldIn, x, y, z, Fluids.EMPTY, ParticleTypes.FALLING_HONEY);
         dripparticle$dripping.particleGravity *= 0.01F;
         dripparticle$dripping.maxAge = 100;
         dripparticle$dripping.setColor(0.622F, 0.508F, 0.082F);
         dripparticle$dripping.selectSpriteRandomly(this.field_228337_a_);
         return dripparticle$dripping;
      }
   }

   @OnlyIn(Dist.CLIENT)
   static class DrippingLava extends DripParticle.Dripping {
      private DrippingLava(ClientWorld p_i232363_1_, double p_i232363_2_, double p_i232363_4_, double p_i232363_6_, Fluid p_i232363_8_, IParticleData p_i232363_9_) {
         super(p_i232363_1_, p_i232363_2_, p_i232363_4_, p_i232363_6_, p_i232363_8_, p_i232363_9_);
      }

      protected void func_217576_g() {
         this.particleRed = 1.0F;
         this.particleGreen = 16.0F / (float)(40 - this.maxAge + 16);
         this.particleBlue = 4.0F / (float)(40 - this.maxAge + 8);
         super.func_217576_g();
      }
   }

   @OnlyIn(Dist.CLIENT)
   public static class DrippingLavaFactory implements IParticleFactory<BasicParticleType> {
      protected final IAnimatedSprite spriteSet;

      public DrippingLavaFactory(IAnimatedSprite p_i50505_1_) {
         this.spriteSet = p_i50505_1_;
      }

      public Particle makeParticle(BasicParticleType typeIn, ClientWorld worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
         DripParticle.DrippingLava dripparticle$drippinglava = new DripParticle.DrippingLava(worldIn, x, y, z, Fluids.LAVA, ParticleTypes.FALLING_LAVA);
         dripparticle$drippinglava.selectSpriteRandomly(this.spriteSet);
         return dripparticle$drippinglava;
      }
   }

   @OnlyIn(Dist.CLIENT)
   public static class DrippingObsidianTearFactory implements IParticleFactory<BasicParticleType> {
      protected final IAnimatedSprite field_239179_a_;

      public DrippingObsidianTearFactory(IAnimatedSprite p_i232376_1_) {
         this.field_239179_a_ = p_i232376_1_;
      }

      public Particle makeParticle(BasicParticleType typeIn, ClientWorld worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
         DripParticle.Dripping dripparticle$dripping = new DripParticle.Dripping(worldIn, x, y, z, Fluids.EMPTY, ParticleTypes.field_239817_aq_);
         dripparticle$dripping.field_239177_a_ = true;
         dripparticle$dripping.particleGravity *= 0.01F;
         dripparticle$dripping.maxAge = 100;
         dripparticle$dripping.setColor(0.51171875F, 0.03125F, 0.890625F);
         dripparticle$dripping.selectSpriteRandomly(this.field_239179_a_);
         return dripparticle$dripping;
      }
   }

   @OnlyIn(Dist.CLIENT)
   public static class DrippingWaterFactory implements IParticleFactory<BasicParticleType> {
      protected final IAnimatedSprite spriteSet;

      public DrippingWaterFactory(IAnimatedSprite p_i50502_1_) {
         this.spriteSet = p_i50502_1_;
      }

      public Particle makeParticle(BasicParticleType typeIn, ClientWorld worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
         DripParticle dripparticle = new DripParticle.Dripping(worldIn, x, y, z, Fluids.WATER, ParticleTypes.FALLING_WATER);
         dripparticle.setColor(0.2F, 0.3F, 1.0F);
         dripparticle.selectSpriteRandomly(this.spriteSet);
         return dripparticle;
      }
   }

   @OnlyIn(Dist.CLIENT)
   public static class FallingHoneyFactory implements IParticleFactory<BasicParticleType> {
      protected final IAnimatedSprite field_228336_a_;

      public FallingHoneyFactory(IAnimatedSprite p_i225959_1_) {
         this.field_228336_a_ = p_i225959_1_;
      }

      public Particle makeParticle(BasicParticleType typeIn, ClientWorld worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
         DripParticle dripparticle = new DripParticle.FallingHoneyParticle(worldIn, x, y, z, Fluids.EMPTY, ParticleTypes.LANDING_HONEY);
         dripparticle.particleGravity = 0.01F;
         dripparticle.setColor(0.582F, 0.448F, 0.082F);
         dripparticle.selectSpriteRandomly(this.field_228336_a_);
         return dripparticle;
      }
   }

   @OnlyIn(Dist.CLIENT)
   static class FallingHoneyParticle extends DripParticle.FallingLiquidParticle {
      private FallingHoneyParticle(ClientWorld p_i232373_1_, double p_i232373_2_, double p_i232373_4_, double p_i232373_6_, Fluid p_i232373_8_, IParticleData p_i232373_9_) {
         super(p_i232373_1_, p_i232373_2_, p_i232373_4_, p_i232373_6_, p_i232373_8_, p_i232373_9_);
      }

      protected void func_217577_h() {
         if (this.onGround) {
            this.setExpired();
            this.world.addParticle(this.field_228335_a_, this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D);
            this.world.playSound(this.posX + 0.5D, this.posY, this.posZ + 0.5D, SoundEvents.BLOCK_BEEHIVE_DROP, SoundCategory.BLOCKS, 0.3F + this.world.rand.nextFloat() * 2.0F / 3.0F, 1.0F, false);
         }

      }
   }

   @OnlyIn(Dist.CLIENT)
   public static class FallingLavaFactory implements IParticleFactory<BasicParticleType> {
      protected final IAnimatedSprite spriteSet;

      public FallingLavaFactory(IAnimatedSprite p_i50506_1_) {
         this.spriteSet = p_i50506_1_;
      }

      public Particle makeParticle(BasicParticleType typeIn, ClientWorld worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
         DripParticle dripparticle = new DripParticle.FallingLiquidParticle(worldIn, x, y, z, Fluids.LAVA, ParticleTypes.LANDING_LAVA);
         dripparticle.setColor(1.0F, 0.2857143F, 0.083333336F);
         dripparticle.selectSpriteRandomly(this.spriteSet);
         return dripparticle;
      }
   }

   @OnlyIn(Dist.CLIENT)
   static class FallingLiquidParticle extends DripParticle.FallingNectarParticle {
      protected final IParticleData field_228335_a_;

      private FallingLiquidParticle(ClientWorld p_i232369_1_, double p_i232369_2_, double p_i232369_4_, double p_i232369_6_, Fluid p_i232369_8_, IParticleData p_i232369_9_) {
         super(p_i232369_1_, p_i232369_2_, p_i232369_4_, p_i232369_6_, p_i232369_8_);
         this.field_228335_a_ = p_i232369_9_;
      }

      protected void func_217577_h() {
         if (this.onGround) {
            this.setExpired();
            this.world.addParticle(this.field_228335_a_, this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D);
         }

      }
   }

   @OnlyIn(Dist.CLIENT)
   public static class FallingNectarFactory implements IParticleFactory<BasicParticleType> {
      protected final IAnimatedSprite field_228339_a_;

      public FallingNectarFactory(IAnimatedSprite p_i225962_1_) {
         this.field_228339_a_ = p_i225962_1_;
      }

      public Particle makeParticle(BasicParticleType typeIn, ClientWorld worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
         DripParticle dripparticle = new DripParticle.FallingNectarParticle(worldIn, x, y, z, Fluids.EMPTY);
         dripparticle.maxAge = (int)(16.0D / (Math.random() * 0.8D + 0.2D));
         dripparticle.particleGravity = 0.007F;
         dripparticle.setColor(0.92F, 0.782F, 0.72F);
         dripparticle.selectSpriteRandomly(this.field_228339_a_);
         return dripparticle;
      }
   }

   @OnlyIn(Dist.CLIENT)
   static class FallingNectarParticle extends DripParticle {
      private FallingNectarParticle(ClientWorld p_i232371_1_, double p_i232371_2_, double p_i232371_4_, double p_i232371_6_, Fluid p_i232371_8_) {
         super(p_i232371_1_, p_i232371_2_, p_i232371_4_, p_i232371_6_, p_i232371_8_);
         this.maxAge = (int)(64.0D / (Math.random() * 0.8D + 0.2D));
      }

      protected void func_217577_h() {
         if (this.onGround) {
            this.setExpired();
         }

      }
   }

   @OnlyIn(Dist.CLIENT)
   public static class FallingObsidianTearFactory implements IParticleFactory<BasicParticleType> {
      protected final IAnimatedSprite field_239178_a_;

      public FallingObsidianTearFactory(IAnimatedSprite p_i232375_1_) {
         this.field_239178_a_ = p_i232375_1_;
      }

      public Particle makeParticle(BasicParticleType typeIn, ClientWorld worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
         DripParticle dripparticle = new DripParticle.FallingLiquidParticle(worldIn, x, y, z, Fluids.EMPTY, ParticleTypes.field_239818_ar_);
         dripparticle.field_239177_a_ = true;
         dripparticle.particleGravity = 0.01F;
         dripparticle.setColor(0.51171875F, 0.03125F, 0.890625F);
         dripparticle.selectSpriteRandomly(this.field_239178_a_);
         return dripparticle;
      }
   }

   @OnlyIn(Dist.CLIENT)
   public static class FallingWaterFactory implements IParticleFactory<BasicParticleType> {
      protected final IAnimatedSprite spriteSet;

      public FallingWaterFactory(IAnimatedSprite p_i50503_1_) {
         this.spriteSet = p_i50503_1_;
      }

      public Particle makeParticle(BasicParticleType typeIn, ClientWorld worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
         DripParticle dripparticle = new DripParticle.FallingLiquidParticle(worldIn, x, y, z, Fluids.WATER, ParticleTypes.SPLASH);
         dripparticle.setColor(0.2F, 0.3F, 1.0F);
         dripparticle.selectSpriteRandomly(this.spriteSet);
         return dripparticle;
      }
   }

   @OnlyIn(Dist.CLIENT)
   static class Landing extends DripParticle {
      private Landing(ClientWorld p_i232367_1_, double p_i232367_2_, double p_i232367_4_, double p_i232367_6_, Fluid p_i232367_8_) {
         super(p_i232367_1_, p_i232367_2_, p_i232367_4_, p_i232367_6_, p_i232367_8_);
         this.maxAge = (int)(16.0D / (Math.random() * 0.8D + 0.2D));
      }
   }

   @OnlyIn(Dist.CLIENT)
   public static class LandingHoneyFactory implements IParticleFactory<BasicParticleType> {
      protected final IAnimatedSprite field_228338_a_;

      public LandingHoneyFactory(IAnimatedSprite p_i225961_1_) {
         this.field_228338_a_ = p_i225961_1_;
      }

      public Particle makeParticle(BasicParticleType typeIn, ClientWorld worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
         DripParticle dripparticle = new DripParticle.Landing(worldIn, x, y, z, Fluids.EMPTY);
         dripparticle.maxAge = (int)(128.0D / (Math.random() * 0.8D + 0.2D));
         dripparticle.setColor(0.522F, 0.408F, 0.082F);
         dripparticle.selectSpriteRandomly(this.field_228338_a_);
         return dripparticle;
      }
   }

   @OnlyIn(Dist.CLIENT)
   public static class LandingLavaFactory implements IParticleFactory<BasicParticleType> {
      protected final IAnimatedSprite spriteSet;

      public LandingLavaFactory(IAnimatedSprite p_i50504_1_) {
         this.spriteSet = p_i50504_1_;
      }

      public Particle makeParticle(BasicParticleType typeIn, ClientWorld worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
         DripParticle dripparticle = new DripParticle.Landing(worldIn, x, y, z, Fluids.LAVA);
         dripparticle.setColor(1.0F, 0.2857143F, 0.083333336F);
         dripparticle.selectSpriteRandomly(this.spriteSet);
         return dripparticle;
      }
   }

   @OnlyIn(Dist.CLIENT)
   public static class LandingObsidianTearFactory implements IParticleFactory<BasicParticleType> {
      protected final IAnimatedSprite field_239180_a_;

      public LandingObsidianTearFactory(IAnimatedSprite p_i232377_1_) {
         this.field_239180_a_ = p_i232377_1_;
      }

      public Particle makeParticle(BasicParticleType typeIn, ClientWorld worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
         DripParticle dripparticle = new DripParticle.Landing(worldIn, x, y, z, Fluids.EMPTY);
         dripparticle.field_239177_a_ = true;
         dripparticle.maxAge = (int)(28.0D / (Math.random() * 0.8D + 0.2D));
         dripparticle.setColor(0.51171875F, 0.03125F, 0.890625F);
         dripparticle.selectSpriteRandomly(this.field_239180_a_);
         return dripparticle;
      }
   }
}