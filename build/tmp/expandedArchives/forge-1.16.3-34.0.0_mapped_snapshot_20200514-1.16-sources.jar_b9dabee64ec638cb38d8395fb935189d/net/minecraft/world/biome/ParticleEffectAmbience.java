package net.minecraft.world.biome;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Random;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ParticleEffectAmbience {
   public static final Codec<ParticleEffectAmbience> field_235041_a_ = RecordCodecBuilder.create((p_235046_0_) -> {
      return p_235046_0_.group(ParticleTypes.field_239821_au_.fieldOf("options").forGetter((p_235048_0_) -> {
         return p_235048_0_.field_235042_b_;
      }), Codec.FLOAT.fieldOf("probability").forGetter((p_235045_0_) -> {
         return p_235045_0_.field_235043_c_;
      })).apply(p_235046_0_, ParticleEffectAmbience::new);
   });
   private final IParticleData field_235042_b_;
   private final float field_235043_c_;

   public ParticleEffectAmbience(IParticleData p_i231629_1_, float p_i231629_2_) {
      this.field_235042_b_ = p_i231629_1_;
      this.field_235043_c_ = p_i231629_2_;
   }

   @OnlyIn(Dist.CLIENT)
   public IParticleData func_235044_a_() {
      return this.field_235042_b_;
   }

   @OnlyIn(Dist.CLIENT)
   public boolean func_235047_a_(Random p_235047_1_) {
      return p_235047_1_.nextFloat() <= this.field_235043_c_;
   }
}