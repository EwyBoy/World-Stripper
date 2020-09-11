package net.minecraft.client.audio;

import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import java.util.Optional;
import java.util.Random;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeManager;
import net.minecraft.world.biome.MoodSoundAmbience;
import net.minecraft.world.biome.SoundAdditionsAmbience;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BiomeSoundHandler implements IAmbientSoundHandler {
   private final ClientPlayerEntity field_239510_a_;
   private final SoundHandler field_239511_b_;
   private final BiomeManager field_239512_c_;
   private final Random field_239513_d_;
   private Object2ObjectArrayMap<Biome, BiomeSoundHandler.Sound> field_239514_e_ = new Object2ObjectArrayMap<>();
   private Optional<MoodSoundAmbience> field_239515_f_ = Optional.empty();
   private Optional<SoundAdditionsAmbience> field_239516_g_ = Optional.empty();
   private float field_239517_h_;
   private Biome field_239518_i_;

   public BiomeSoundHandler(ClientPlayerEntity p_i232488_1_, SoundHandler p_i232488_2_, BiomeManager p_i232488_3_) {
      this.field_239513_d_ = p_i232488_1_.world.getRandom();
      this.field_239510_a_ = p_i232488_1_;
      this.field_239511_b_ = p_i232488_2_;
      this.field_239512_c_ = p_i232488_3_;
   }

   public float func_239523_b_() {
      return this.field_239517_h_;
   }

   public void tick() {
      this.field_239514_e_.values().removeIf(TickableSound::isDonePlaying);
      Biome biome = this.field_239512_c_.func_235198_a_(this.field_239510_a_.getPosX(), this.field_239510_a_.getPosY(), this.field_239510_a_.getPosZ());
      if (biome != this.field_239518_i_) {
         this.field_239518_i_ = biome;
         this.field_239515_f_ = biome.func_235092_v_();
         this.field_239516_g_ = biome.func_235093_w_();
         this.field_239514_e_.values().forEach(BiomeSoundHandler.Sound::func_239526_p_);
         biome.func_235091_u_().ifPresent((p_239522_2_) -> {
            BiomeSoundHandler.Sound biomesoundhandler$sound = this.field_239514_e_.compute(biome, (p_239519_2_, p_239519_3_) -> {
               if (p_239519_3_ == null) {
                  p_239519_3_ = new BiomeSoundHandler.Sound(p_239522_2_);
                  this.field_239511_b_.play(p_239519_3_);
               }

               p_239519_3_.func_239527_q_();
               return p_239519_3_;
            });
         });
      }

      this.field_239516_g_.ifPresent((p_239520_1_) -> {
         if (this.field_239513_d_.nextDouble() < p_239520_1_.func_235024_b_()) {
            this.field_239511_b_.play(SimpleSound.func_239530_b_(p_239520_1_.func_235021_a_()));
         }

      });
      this.field_239515_f_.ifPresent((p_239521_1_) -> {
         World world = this.field_239510_a_.world;
         int i = p_239521_1_.func_235037_c_() * 2 + 1;
         BlockPos blockpos = new BlockPos(this.field_239510_a_.getPosX() + (double)this.field_239513_d_.nextInt(i) - (double)p_239521_1_.func_235037_c_(), this.field_239510_a_.getPosYEye() + (double)this.field_239513_d_.nextInt(i) - (double)p_239521_1_.func_235037_c_(), this.field_239510_a_.getPosZ() + (double)this.field_239513_d_.nextInt(i) - (double)p_239521_1_.func_235037_c_());
         int j = world.getLightFor(LightType.SKY, blockpos);
         if (j > 0) {
            this.field_239517_h_ -= (float)j / (float)world.getMaxLightLevel() * 0.001F;
         } else {
            this.field_239517_h_ -= (float)(world.getLightFor(LightType.BLOCK, blockpos) - 1) / (float)p_239521_1_.func_235035_b_();
         }

         if (this.field_239517_h_ >= 1.0F) {
            double d0 = (double)blockpos.getX() + 0.5D;
            double d1 = (double)blockpos.getY() + 0.5D;
            double d2 = (double)blockpos.getZ() + 0.5D;
            double d3 = d0 - this.field_239510_a_.getPosX();
            double d4 = d1 - this.field_239510_a_.getPosYEye();
            double d5 = d2 - this.field_239510_a_.getPosZ();
            double d6 = (double)MathHelper.sqrt(d3 * d3 + d4 * d4 + d5 * d5);
            double d7 = d6 + p_239521_1_.func_235039_d_();
            SimpleSound simplesound = SimpleSound.func_239531_b_(p_239521_1_.func_235032_a_(), this.field_239510_a_.getPosX() + d3 / d6 * d7, this.field_239510_a_.getPosYEye() + d4 / d6 * d7, this.field_239510_a_.getPosZ() + d5 / d6 * d7);
            this.field_239511_b_.play(simplesound);
            this.field_239517_h_ = 0.0F;
         } else {
            this.field_239517_h_ = Math.max(this.field_239517_h_, 0.0F);
         }

      });
   }

   @OnlyIn(Dist.CLIENT)
   public static class Sound extends TickableSound {
      private int field_239524_n_;
      private int field_239525_o_;

      public Sound(SoundEvent p_i232489_1_) {
         super(p_i232489_1_, SoundCategory.AMBIENT);
         this.repeat = true;
         this.repeatDelay = 0;
         this.volume = 1.0F;
         this.global = true;
      }

      public void tick() {
         if (this.field_239525_o_ < 0) {
            this.func_239509_o_();
         }

         this.field_239525_o_ += this.field_239524_n_;
         this.volume = MathHelper.clamp((float)this.field_239525_o_ / 40.0F, 0.0F, 1.0F);
      }

      public void func_239526_p_() {
         this.field_239525_o_ = Math.min(this.field_239525_o_, 40);
         this.field_239524_n_ = -1;
      }

      public void func_239527_q_() {
         this.field_239525_o_ = Math.max(0, this.field_239525_o_);
         this.field_239524_n_ = 1;
      }
   }
}