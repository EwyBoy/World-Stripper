package net.minecraft.world.biome;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.stream.Collectors;
import net.minecraft.client.audio.BackgroundMusicSelector;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class BiomeAmbience {
   public static final Codec<BiomeAmbience> field_235204_a_ = RecordCodecBuilder.create((p_235215_0_) -> {
      return p_235215_0_.group(Codec.INT.fieldOf("fog_color").forGetter((p_235229_0_) -> {
         return p_235229_0_.field_235205_b_;
      }), Codec.INT.fieldOf("water_color").forGetter((p_235227_0_) -> {
         return p_235227_0_.field_235206_c_;
      }), Codec.INT.fieldOf("water_fog_color").forGetter((p_235225_0_) -> {
         return p_235225_0_.field_235207_d_;
      }), Codec.INT.fieldOf("sky_color").forGetter((p_242532_0_) -> {
         return p_242532_0_.field_242523_e;
      }), Codec.INT.optionalFieldOf("foliage_color").forGetter((p_244421_0_) -> {
         return p_244421_0_.field_242524_f;
      }), Codec.INT.optionalFieldOf("grass_color").forGetter((p_244426_0_) -> {
         return p_244426_0_.field_242525_g;
      }), BiomeAmbience.GrassColorModifier.field_242542_d.optionalFieldOf("grass_color_modifier", BiomeAmbience.GrassColorModifier.NONE).forGetter((p_242530_0_) -> {
         return p_242530_0_.field_242526_h;
      }), ParticleEffectAmbience.field_235041_a_.optionalFieldOf("particle").forGetter((p_235223_0_) -> {
         return p_235223_0_.field_235208_e_;
      }), SoundEvent.field_232678_a_.optionalFieldOf("ambient_sound").forGetter((p_235221_0_) -> {
         return p_235221_0_.field_235209_f_;
      }), MoodSoundAmbience.field_235026_a_.optionalFieldOf("mood_sound").forGetter((p_235219_0_) -> {
         return p_235219_0_.field_235210_g_;
      }), SoundAdditionsAmbience.field_235018_a_.optionalFieldOf("additions_sound").forGetter((p_235217_0_) -> {
         return p_235217_0_.field_235211_h_;
      }), BackgroundMusicSelector.field_232656_a_.optionalFieldOf("music").forGetter((p_244420_0_) -> {
         return p_244420_0_.field_235212_i_;
      })).apply(p_235215_0_, BiomeAmbience::new);
   });
   private final int field_235205_b_;
   private final int field_235206_c_;
   private final int field_235207_d_;
   private final int field_242523_e;
   private final Optional<Integer> field_242524_f;
   private final Optional<Integer> field_242525_g;
   private final BiomeAmbience.GrassColorModifier field_242526_h;
   private final Optional<ParticleEffectAmbience> field_235208_e_;
   private final Optional<SoundEvent> field_235209_f_;
   private final Optional<MoodSoundAmbience> field_235210_g_;
   private final Optional<SoundAdditionsAmbience> field_235211_h_;
   private final Optional<BackgroundMusicSelector> field_235212_i_;

   private BiomeAmbience(int p_i241938_1_, int p_i241938_2_, int p_i241938_3_, int p_i241938_4_, Optional<Integer> p_i241938_5_, Optional<Integer> p_i241938_6_, BiomeAmbience.GrassColorModifier p_i241938_7_, Optional<ParticleEffectAmbience> p_i241938_8_, Optional<SoundEvent> p_i241938_9_, Optional<MoodSoundAmbience> p_i241938_10_, Optional<SoundAdditionsAmbience> p_i241938_11_, Optional<BackgroundMusicSelector> p_i241938_12_) {
      this.field_235205_b_ = p_i241938_1_;
      this.field_235206_c_ = p_i241938_2_;
      this.field_235207_d_ = p_i241938_3_;
      this.field_242523_e = p_i241938_4_;
      this.field_242524_f = p_i241938_5_;
      this.field_242525_g = p_i241938_6_;
      this.field_242526_h = p_i241938_7_;
      this.field_235208_e_ = p_i241938_8_;
      this.field_235209_f_ = p_i241938_9_;
      this.field_235210_g_ = p_i241938_10_;
      this.field_235211_h_ = p_i241938_11_;
      this.field_235212_i_ = p_i241938_12_;
   }

   @OnlyIn(Dist.CLIENT)
   public int func_235213_a_() {
      return this.field_235205_b_;
   }

   @OnlyIn(Dist.CLIENT)
   public int func_235216_b_() {
      return this.field_235206_c_;
   }

   @OnlyIn(Dist.CLIENT)
   public int func_235218_c_() {
      return this.field_235207_d_;
   }

   @OnlyIn(Dist.CLIENT)
   public int func_242527_d() {
      return this.field_242523_e;
   }

   @OnlyIn(Dist.CLIENT)
   public Optional<Integer> func_242528_e() {
      return this.field_242524_f;
   }

   @OnlyIn(Dist.CLIENT)
   public Optional<Integer> func_242529_f() {
      return this.field_242525_g;
   }

   @OnlyIn(Dist.CLIENT)
   public BiomeAmbience.GrassColorModifier func_242531_g() {
      return this.field_242526_h;
   }

   @OnlyIn(Dist.CLIENT)
   public Optional<ParticleEffectAmbience> func_235220_d_() {
      return this.field_235208_e_;
   }

   @OnlyIn(Dist.CLIENT)
   public Optional<SoundEvent> func_235222_e_() {
      return this.field_235209_f_;
   }

   @OnlyIn(Dist.CLIENT)
   public Optional<MoodSoundAmbience> func_235224_f_() {
      return this.field_235210_g_;
   }

   @OnlyIn(Dist.CLIENT)
   public Optional<SoundAdditionsAmbience> func_235226_g_() {
      return this.field_235211_h_;
   }

   @OnlyIn(Dist.CLIENT)
   public Optional<BackgroundMusicSelector> func_235228_h_() {
      return this.field_235212_i_;
   }

   public static class Builder {
      private OptionalInt field_235230_a_ = OptionalInt.empty();
      private OptionalInt field_235231_b_ = OptionalInt.empty();
      private OptionalInt field_235232_c_ = OptionalInt.empty();
      private OptionalInt field_242533_d = OptionalInt.empty();
      private Optional<Integer> field_242534_e = Optional.empty();
      private Optional<Integer> field_242535_f = Optional.empty();
      private BiomeAmbience.GrassColorModifier field_242536_g = BiomeAmbience.GrassColorModifier.NONE;
      private Optional<ParticleEffectAmbience> field_235233_d_ = Optional.empty();
      private Optional<SoundEvent> field_235234_e_ = Optional.empty();
      private Optional<MoodSoundAmbience> field_235235_f_ = Optional.empty();
      private Optional<SoundAdditionsAmbience> field_235236_g_ = Optional.empty();
      private Optional<BackgroundMusicSelector> field_235237_h_ = Optional.empty();

      public BiomeAmbience.Builder func_235239_a_(int p_235239_1_) {
         this.field_235230_a_ = OptionalInt.of(p_235239_1_);
         return this;
      }

      public BiomeAmbience.Builder func_235246_b_(int p_235246_1_) {
         this.field_235231_b_ = OptionalInt.of(p_235246_1_);
         return this;
      }

      public BiomeAmbience.Builder func_235248_c_(int p_235248_1_) {
         this.field_235232_c_ = OptionalInt.of(p_235248_1_);
         return this;
      }

      public BiomeAmbience.Builder func_242539_d(int p_242539_1_) {
         this.field_242533_d = OptionalInt.of(p_242539_1_);
         return this;
      }

      public BiomeAmbience.Builder func_242540_e(int p_242540_1_) {
         this.field_242534_e = Optional.of(p_242540_1_);
         return this;
      }

      public BiomeAmbience.Builder func_242541_f(int p_242541_1_) {
         this.field_242535_f = Optional.of(p_242541_1_);
         return this;
      }

      public BiomeAmbience.Builder func_242537_a(BiomeAmbience.GrassColorModifier p_242537_1_) {
         this.field_242536_g = p_242537_1_;
         return this;
      }

      public BiomeAmbience.Builder func_235244_a_(ParticleEffectAmbience p_235244_1_) {
         this.field_235233_d_ = Optional.of(p_235244_1_);
         return this;
      }

      public BiomeAmbience.Builder func_235241_a_(SoundEvent p_235241_1_) {
         this.field_235234_e_ = Optional.of(p_235241_1_);
         return this;
      }

      public BiomeAmbience.Builder func_235243_a_(MoodSoundAmbience p_235243_1_) {
         this.field_235235_f_ = Optional.of(p_235243_1_);
         return this;
      }

      public BiomeAmbience.Builder func_235242_a_(SoundAdditionsAmbience p_235242_1_) {
         this.field_235236_g_ = Optional.of(p_235242_1_);
         return this;
      }

      public BiomeAmbience.Builder func_235240_a_(BackgroundMusicSelector p_235240_1_) {
         this.field_235237_h_ = Optional.of(p_235240_1_);
         return this;
      }

      public BiomeAmbience func_235238_a_() {
         return new BiomeAmbience(this.field_235230_a_.orElseThrow(() -> {
            return new IllegalStateException("Missing 'fog' color.");
         }), this.field_235231_b_.orElseThrow(() -> {
            return new IllegalStateException("Missing 'water' color.");
         }), this.field_235232_c_.orElseThrow(() -> {
            return new IllegalStateException("Missing 'water fog' color.");
         }), this.field_242533_d.orElseThrow(() -> {
            return new IllegalStateException("Missing 'sky' color.");
         }), this.field_242534_e, this.field_242535_f, this.field_242536_g, this.field_235233_d_, this.field_235234_e_, this.field_235235_f_, this.field_235236_g_, this.field_235237_h_);
      }
   }

   public static enum GrassColorModifier implements IStringSerializable {
      NONE("none") {
         @OnlyIn(Dist.CLIENT)
         public int func_241853_a(double p_241853_1_, double p_241853_3_, int p_241853_5_) {
            return p_241853_5_;
         }
      },
      DARK_FOREST("dark_forest") {
         @OnlyIn(Dist.CLIENT)
         public int func_241853_a(double p_241853_1_, double p_241853_3_, int p_241853_5_) {
            return (p_241853_5_ & 16711422) + 2634762 >> 1;
         }
      },
      SWAMP("swamp") {
         @OnlyIn(Dist.CLIENT)
         public int func_241853_a(double p_241853_1_, double p_241853_3_, int p_241853_5_) {
            double d0 = Biome.INFO_NOISE.noiseAt(p_241853_1_ * 0.0225D, p_241853_3_ * 0.0225D, false);
            return d0 < -0.1D ? 5011004 : 6975545;
         }
      };

      private final String field_242543_e;
      public static final Codec<BiomeAmbience.GrassColorModifier> field_242542_d = IStringSerializable.func_233023_a_(BiomeAmbience.GrassColorModifier::values, BiomeAmbience.GrassColorModifier::func_242546_a);
      private static final Map<String, BiomeAmbience.GrassColorModifier> field_242544_f = Arrays.stream(values()).collect(Collectors.toMap(BiomeAmbience.GrassColorModifier::func_242547_b, (p_242545_0_) -> {
         return p_242545_0_;
      }));

      @OnlyIn(Dist.CLIENT)
      public abstract int func_241853_a(double p_241853_1_, double p_241853_3_, int p_241853_5_);

      private GrassColorModifier(String p_i241940_3_) {
         this.field_242543_e = p_i241940_3_;
      }

      public String func_242547_b() {
         return this.field_242543_e;
      }

      public String func_176610_l() {
         return this.field_242543_e;
      }

      public static BiomeAmbience.GrassColorModifier func_242546_a(String p_242546_0_) {
         return field_242544_f.get(p_242546_0_);
      }
   }
}