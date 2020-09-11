package net.minecraft.world.biome.provider;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryLookupCodec;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.gen.SimplexNoiseGenerator;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class EndBiomeProvider extends BiomeProvider {
   public static final Codec<EndBiomeProvider> field_235314_e_ = RecordCodecBuilder.create((p_242647_0_) -> {
      return p_242647_0_.group(RegistryLookupCodec.func_244331_a(Registry.field_239720_u_).forGetter((p_242648_0_) -> {
         return p_242648_0_.field_242640_g;
      }), Codec.LONG.fieldOf("seed").stable().forGetter((p_242646_0_) -> {
         return p_242646_0_.field_235315_h_;
      })).apply(p_242647_0_, p_242647_0_.stable(EndBiomeProvider::new));
   });
   private final SimplexNoiseGenerator generator;
   private final Registry<Biome> field_242640_g;
   private final long field_235315_h_;
   private final Biome field_242641_i;
   private final Biome field_242642_j;
   private final Biome field_242643_k;
   private final Biome field_242644_l;
   private final Biome field_242645_m;

   public EndBiomeProvider(Registry<Biome> p_i241959_1_, long p_i241959_2_) {
      this(p_i241959_1_, p_i241959_2_, p_i241959_1_.func_243576_d(Biomes.THE_END), p_i241959_1_.func_243576_d(Biomes.END_HIGHLANDS), p_i241959_1_.func_243576_d(Biomes.END_MIDLANDS), p_i241959_1_.func_243576_d(Biomes.SMALL_END_ISLANDS), p_i241959_1_.func_243576_d(Biomes.END_BARRENS));
   }

   private EndBiomeProvider(Registry<Biome> p_i241960_1_, long p_i241960_2_, Biome p_i241960_4_, Biome p_i241960_5_, Biome p_i241960_6_, Biome p_i241960_7_, Biome p_i241960_8_) {
      super(ImmutableList.of(p_i241960_4_, p_i241960_5_, p_i241960_6_, p_i241960_7_, p_i241960_8_));
      this.field_242640_g = p_i241960_1_;
      this.field_235315_h_ = p_i241960_2_;
      this.field_242641_i = p_i241960_4_;
      this.field_242642_j = p_i241960_5_;
      this.field_242643_k = p_i241960_6_;
      this.field_242644_l = p_i241960_7_;
      this.field_242645_m = p_i241960_8_;
      SharedSeedRandom sharedseedrandom = new SharedSeedRandom(p_i241960_2_);
      sharedseedrandom.skip(17292);
      this.generator = new SimplexNoiseGenerator(sharedseedrandom);
   }

   protected Codec<? extends BiomeProvider> func_230319_a_() {
      return field_235314_e_;
   }

   @OnlyIn(Dist.CLIENT)
   public BiomeProvider func_230320_a_(long p_230320_1_) {
      return new EndBiomeProvider(this.field_242640_g, p_230320_1_, this.field_242641_i, this.field_242642_j, this.field_242643_k, this.field_242644_l, this.field_242645_m);
   }

   public Biome getNoiseBiome(int x, int y, int z) {
      int i = x >> 2;
      int j = z >> 2;
      if ((long)i * (long)i + (long)j * (long)j <= 4096L) {
         return this.field_242641_i;
      } else {
         float f = func_235317_a_(this.generator, i * 2 + 1, j * 2 + 1);
         if (f > 40.0F) {
            return this.field_242642_j;
         } else if (f >= 0.0F) {
            return this.field_242643_k;
         } else {
            return f < -20.0F ? this.field_242644_l : this.field_242645_m;
         }
      }
   }

   public boolean func_235318_b_(long p_235318_1_) {
      return this.field_235315_h_ == p_235318_1_;
   }

   public static float func_235317_a_(SimplexNoiseGenerator p_235317_0_, int p_235317_1_, int p_235317_2_) {
      int i = p_235317_1_ / 2;
      int j = p_235317_2_ / 2;
      int k = p_235317_1_ % 2;
      int l = p_235317_2_ % 2;
      float f = 100.0F - MathHelper.sqrt((float)(p_235317_1_ * p_235317_1_ + p_235317_2_ * p_235317_2_)) * 8.0F;
      f = MathHelper.clamp(f, -100.0F, 80.0F);

      for(int i1 = -12; i1 <= 12; ++i1) {
         for(int j1 = -12; j1 <= 12; ++j1) {
            long k1 = (long)(i + i1);
            long l1 = (long)(j + j1);
            if (k1 * k1 + l1 * l1 > 4096L && p_235317_0_.getValue((double)k1, (double)l1) < (double)-0.9F) {
               float f1 = (MathHelper.abs((float)k1) * 3439.0F + MathHelper.abs((float)l1) * 147.0F) % 13.0F + 9.0F;
               float f2 = (float)(k - i1 * 2);
               float f3 = (float)(l - j1 * 2);
               float f4 = 100.0F - MathHelper.sqrt(f2 * f2 + f3 * f3) * f1;
               f4 = MathHelper.clamp(f4, -100.0F, 80.0F);
               f = Math.max(f, f4);
            }
         }
      }

      return f;
   }
}