package net.minecraft.world.biome.provider;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Function3;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.doubles.DoubleArrayList;
import it.unimi.dsi.fastutil.doubles.DoubleList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryLookupCodec;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeRegistry;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.gen.MaxMinNoiseMixer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class NetherBiomeProvider extends BiomeProvider {
   private static final NetherBiomeProvider.Noise field_242596_g = new NetherBiomeProvider.Noise(-7, ImmutableList.of(1.0D, 1.0D));
   public static final MapCodec<NetherBiomeProvider> field_235262_e_ = RecordCodecBuilder.mapCodec((p_242602_0_) -> {
      return p_242602_0_.group(Codec.LONG.fieldOf("seed").forGetter((p_235286_0_) -> {
         return p_235286_0_.field_235270_m_;
      }), RecordCodecBuilder.<Pair<Biome.Attributes, Supplier<Biome>>>create((p_235282_0_) -> {
         return p_235282_0_.group(Biome.Attributes.field_235104_a_.fieldOf("parameters").forGetter(Pair::getFirst), Biome.field_235051_b_.fieldOf("biome").forGetter(Pair::getSecond)).apply(p_235282_0_, Pair::of);
      }).listOf().fieldOf("biomes").forGetter((p_235284_0_) -> {
         return p_235284_0_.field_235268_k_;
      }), NetherBiomeProvider.Noise.field_242609_a.fieldOf("temperature_noise").forGetter((p_242608_0_) -> {
         return p_242608_0_.field_242597_h;
      }), NetherBiomeProvider.Noise.field_242609_a.fieldOf("humidity_noise").forGetter((p_242607_0_) -> {
         return p_242607_0_.field_242598_i;
      }), NetherBiomeProvider.Noise.field_242609_a.fieldOf("altitude_noise").forGetter((p_242606_0_) -> {
         return p_242606_0_.field_242599_j;
      }), NetherBiomeProvider.Noise.field_242609_a.fieldOf("weirdness_noise").forGetter((p_242604_0_) -> {
         return p_242604_0_.field_242600_k;
      })).apply(p_242602_0_, NetherBiomeProvider::new);
   });
   public static final Codec<NetherBiomeProvider> field_235263_f_ = Codec.mapEither(NetherBiomeProvider.DefaultBuilder.field_242624_a, field_235262_e_).xmap((p_235277_0_) -> {
      return p_235277_0_.map(NetherBiomeProvider.DefaultBuilder::func_242635_d, Function.identity());
   }, (p_235275_0_) -> {
      return p_235275_0_.func_242605_d().map(Either::<NetherBiomeProvider.DefaultBuilder, NetherBiomeProvider>left).orElseGet(() -> {
         return Either.right(p_235275_0_);
      });
   }).codec();
   private final NetherBiomeProvider.Noise field_242597_h;
   private final NetherBiomeProvider.Noise field_242598_i;
   private final NetherBiomeProvider.Noise field_242599_j;
   private final NetherBiomeProvider.Noise field_242600_k;
   private final MaxMinNoiseMixer field_235264_g_;
   private final MaxMinNoiseMixer field_235265_h_;
   private final MaxMinNoiseMixer field_235266_i_;
   private final MaxMinNoiseMixer field_235267_j_;
   private final List<Pair<Biome.Attributes, Supplier<Biome>>> field_235268_k_;
   private final boolean field_235269_l_;
   private final long field_235270_m_;
   private final Optional<Pair<Registry<Biome>, NetherBiomeProvider.Preset>> field_235271_n_;

   private NetherBiomeProvider(long p_i231640_1_, List<Pair<Biome.Attributes, Supplier<Biome>>> p_i231640_3_, Optional<Pair<Registry<Biome>, NetherBiomeProvider.Preset>> p_i231640_4_) {
      this(p_i231640_1_, p_i231640_3_, field_242596_g, field_242596_g, field_242596_g, field_242596_g, p_i231640_4_);
   }

   private NetherBiomeProvider(long p_i241951_1_, List<Pair<Biome.Attributes, Supplier<Biome>>> p_i241951_3_, NetherBiomeProvider.Noise p_i241951_4_, NetherBiomeProvider.Noise p_i241951_5_, NetherBiomeProvider.Noise p_i241951_6_, NetherBiomeProvider.Noise p_i241951_7_) {
      this(p_i241951_1_, p_i241951_3_, p_i241951_4_, p_i241951_5_, p_i241951_6_, p_i241951_7_, Optional.empty());
   }

   private NetherBiomeProvider(long p_i241952_1_, List<Pair<Biome.Attributes, Supplier<Biome>>> p_i241952_3_, NetherBiomeProvider.Noise p_i241952_4_, NetherBiomeProvider.Noise p_i241952_5_, NetherBiomeProvider.Noise p_i241952_6_, NetherBiomeProvider.Noise p_i241952_7_, Optional<Pair<Registry<Biome>, NetherBiomeProvider.Preset>> p_i241952_8_) {
      super(p_i241952_3_.stream().map(Pair::getSecond));
      this.field_235270_m_ = p_i241952_1_;
      this.field_235271_n_ = p_i241952_8_;
      this.field_242597_h = p_i241952_4_;
      this.field_242598_i = p_i241952_5_;
      this.field_242599_j = p_i241952_6_;
      this.field_242600_k = p_i241952_7_;
      this.field_235264_g_ = MaxMinNoiseMixer.func_242930_a(new SharedSeedRandom(p_i241952_1_), p_i241952_4_.func_242612_a(), p_i241952_4_.func_242614_b());
      this.field_235265_h_ = MaxMinNoiseMixer.func_242930_a(new SharedSeedRandom(p_i241952_1_ + 1L), p_i241952_5_.func_242612_a(), p_i241952_5_.func_242614_b());
      this.field_235266_i_ = MaxMinNoiseMixer.func_242930_a(new SharedSeedRandom(p_i241952_1_ + 2L), p_i241952_6_.func_242612_a(), p_i241952_6_.func_242614_b());
      this.field_235267_j_ = MaxMinNoiseMixer.func_242930_a(new SharedSeedRandom(p_i241952_1_ + 3L), p_i241952_7_.func_242612_a(), p_i241952_7_.func_242614_b());
      this.field_235268_k_ = p_i241952_3_;
      this.field_235269_l_ = false;
   }

   protected Codec<? extends BiomeProvider> func_230319_a_() {
      return field_235263_f_;
   }

   @OnlyIn(Dist.CLIENT)
   public BiomeProvider func_230320_a_(long p_230320_1_) {
      return new NetherBiomeProvider(p_230320_1_, this.field_235268_k_, this.field_242597_h, this.field_242598_i, this.field_242599_j, this.field_242600_k, this.field_235271_n_);
   }

   private Optional<NetherBiomeProvider.DefaultBuilder> func_242605_d() {
      return this.field_235271_n_.map((p_242601_1_) -> {
         return new NetherBiomeProvider.DefaultBuilder(p_242601_1_.getSecond(), p_242601_1_.getFirst(), this.field_235270_m_);
      });
   }

   public Biome getNoiseBiome(int x, int y, int z) {
      int i = this.field_235269_l_ ? y : 0;
      Biome.Attributes biome$attributes = new Biome.Attributes((float)this.field_235264_g_.func_237211_a_((double)x, (double)i, (double)z), (float)this.field_235265_h_.func_237211_a_((double)x, (double)i, (double)z), (float)this.field_235266_i_.func_237211_a_((double)x, (double)i, (double)z), (float)this.field_235267_j_.func_237211_a_((double)x, (double)i, (double)z), 0.0F);
      return this.field_235268_k_.stream().min(Comparator.comparing((p_235272_1_) -> {
         return p_235272_1_.getFirst().func_235110_a_(biome$attributes);
      })).map(Pair::getSecond).map(Supplier::get).orElse(BiomeRegistry.field_244201_b);
   }

   public boolean func_235280_b_(long p_235280_1_) {
      return this.field_235270_m_ == p_235280_1_ && this.field_235271_n_.isPresent() && Objects.equals(this.field_235271_n_.get().getSecond(), NetherBiomeProvider.Preset.field_235288_b_);
   }

   static final class DefaultBuilder {
      public static final MapCodec<NetherBiomeProvider.DefaultBuilder> field_242624_a = RecordCodecBuilder.mapCodec((p_242630_0_) -> {
         return p_242630_0_.group(ResourceLocation.field_240908_a_.flatXmap((p_242631_0_) -> {
            return Optional.ofNullable(NetherBiomeProvider.Preset.field_235289_c_.get(p_242631_0_)).map(DataResult::success).orElseGet(() -> {
               return DataResult.error("Unknown preset: " + p_242631_0_);
            });
         }, (p_242629_0_) -> {
            return DataResult.success(p_242629_0_.field_235290_d_);
         }).fieldOf("preset").stable().forGetter(NetherBiomeProvider.DefaultBuilder::func_242628_a), RegistryLookupCodec.func_244331_a(Registry.field_239720_u_).forGetter(NetherBiomeProvider.DefaultBuilder::func_242632_b), Codec.LONG.fieldOf("seed").stable().forGetter(NetherBiomeProvider.DefaultBuilder::func_242634_c)).apply(p_242630_0_, p_242630_0_.stable(NetherBiomeProvider.DefaultBuilder::new));
      });
      private final NetherBiomeProvider.Preset field_242625_b;
      private final Registry<Biome> field_242626_c;
      private final long field_242627_d;

      private DefaultBuilder(NetherBiomeProvider.Preset p_i241956_1_, Registry<Biome> p_i241956_2_, long p_i241956_3_) {
         this.field_242625_b = p_i241956_1_;
         this.field_242626_c = p_i241956_2_;
         this.field_242627_d = p_i241956_3_;
      }

      public NetherBiomeProvider.Preset func_242628_a() {
         return this.field_242625_b;
      }

      public Registry<Biome> func_242632_b() {
         return this.field_242626_c;
      }

      public long func_242634_c() {
         return this.field_242627_d;
      }

      public NetherBiomeProvider func_242635_d() {
         return this.field_242625_b.func_242619_a(this.field_242626_c, this.field_242627_d);
      }
   }

   static class Noise {
      private final int field_242610_b;
      private final DoubleList field_242611_c;
      public static final Codec<NetherBiomeProvider.Noise> field_242609_a = RecordCodecBuilder.create((p_242613_0_) -> {
         return p_242613_0_.group(Codec.INT.fieldOf("firstOctave").forGetter(NetherBiomeProvider.Noise::func_242612_a), Codec.DOUBLE.listOf().fieldOf("amplitudes").forGetter(NetherBiomeProvider.Noise::func_242614_b)).apply(p_242613_0_, NetherBiomeProvider.Noise::new);
      });

      public Noise(int p_i241954_1_, List<Double> p_i241954_2_) {
         this.field_242610_b = p_i241954_1_;
         this.field_242611_c = new DoubleArrayList(p_i241954_2_);
      }

      public int func_242612_a() {
         return this.field_242610_b;
      }

      public DoubleList func_242614_b() {
         return this.field_242611_c;
      }
   }

   public static class Preset {
      private static final Map<ResourceLocation, NetherBiomeProvider.Preset> field_235289_c_ = Maps.newHashMap();
      public static final NetherBiomeProvider.Preset field_235288_b_ = new NetherBiomeProvider.Preset(new ResourceLocation("nether"), (p_242617_0_, p_242617_1_, p_242617_2_) -> {
         return new NetherBiomeProvider(p_242617_2_, ImmutableList.of(Pair.of(new Biome.Attributes(0.0F, 0.0F, 0.0F, 0.0F, 0.0F), () -> {
            return p_242617_1_.func_243576_d(Biomes.field_235254_j_);
         }), Pair.of(new Biome.Attributes(0.0F, -0.5F, 0.0F, 0.0F, 0.0F), () -> {
            return p_242617_1_.func_243576_d(Biomes.field_235252_ay_);
         }), Pair.of(new Biome.Attributes(0.4F, 0.0F, 0.0F, 0.0F, 0.0F), () -> {
            return p_242617_1_.func_243576_d(Biomes.field_235253_az_);
         }), Pair.of(new Biome.Attributes(0.0F, 0.5F, 0.0F, 0.0F, 0.375F), () -> {
            return p_242617_1_.func_243576_d(Biomes.field_235250_aA_);
         }), Pair.of(new Biome.Attributes(-0.5F, 0.0F, 0.0F, 0.0F, 0.175F), () -> {
            return p_242617_1_.func_243576_d(Biomes.field_235251_aB_);
         })), Optional.of(Pair.of(p_242617_1_, p_242617_0_)));
      });
      private final ResourceLocation field_235290_d_;
      private final Function3<NetherBiomeProvider.Preset, Registry<Biome>, Long, NetherBiomeProvider> field_235291_e_;

      public Preset(ResourceLocation p_i241955_1_, Function3<NetherBiomeProvider.Preset, Registry<Biome>, Long, NetherBiomeProvider> p_i241955_2_) {
         this.field_235290_d_ = p_i241955_1_;
         this.field_235291_e_ = p_i241955_2_;
         field_235289_c_.put(p_i241955_1_, this);
      }

      public NetherBiomeProvider func_242619_a(Registry<Biome> p_242619_1_, long p_242619_2_) {
         return this.field_235291_e_.apply(this, p_242619_1_, p_242619_2_);
      }
   }
}