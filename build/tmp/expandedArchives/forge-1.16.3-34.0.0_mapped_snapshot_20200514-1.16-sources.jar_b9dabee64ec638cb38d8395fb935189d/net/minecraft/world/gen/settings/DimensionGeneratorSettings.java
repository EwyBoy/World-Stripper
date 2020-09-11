package net.minecraft.world.gen.settings;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.Lifecycle;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.OptionalLong;
import java.util.Properties;
import java.util.Random;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.function.Supplier;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.SimpleRegistry;
import net.minecraft.world.Dimension;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.OverworldBiomeProvider;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.DebugChunkGenerator;
import net.minecraft.world.gen.DimensionSettings;
import net.minecraft.world.gen.FlatChunkGenerator;
import net.minecraft.world.gen.FlatGenerationSettings;
import net.minecraft.world.gen.NoiseChunkGenerator;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DimensionGeneratorSettings {
   public static final Codec<DimensionGeneratorSettings> field_236201_a_ = RecordCodecBuilder.<DimensionGeneratorSettings>create((p_236214_0_) -> {
      return p_236214_0_.group(Codec.LONG.fieldOf("seed").stable().forGetter(DimensionGeneratorSettings::func_236221_b_), Codec.BOOL.fieldOf("generate_features").orElse(true).stable().forGetter(DimensionGeneratorSettings::func_236222_c_), Codec.BOOL.fieldOf("bonus_chest").orElse(false).stable().forGetter(DimensionGeneratorSettings::func_236223_d_), SimpleRegistry.func_241744_b_(Registry.field_239700_af_, Lifecycle.stable(), Dimension.field_236052_a_).xmap(Dimension::func_236062_a_, Function.identity()).fieldOf("dimensions").forGetter(DimensionGeneratorSettings::func_236224_e_), Codec.STRING.optionalFieldOf("legacy_custom_options").stable().forGetter((p_236213_0_) -> {
         return p_236213_0_.field_236209_i_;
      })).apply(p_236214_0_, p_236214_0_.stable(DimensionGeneratorSettings::new));
   }).comapFlatMap(DimensionGeneratorSettings::func_236233_n_, Function.identity());
   private static final Logger field_236203_c_ = LogManager.getLogger();
   private final long field_236205_e_;
   private final boolean field_236206_f_;
   private final boolean field_236207_g_;
   private final SimpleRegistry<Dimension> field_236208_h_;
   private final Optional<String> field_236209_i_;

   private DataResult<DimensionGeneratorSettings> func_236233_n_() {
      Dimension dimension = this.field_236208_h_.func_230516_a_(Dimension.field_236053_b_);
      if (dimension == null) {
         return DataResult.error("Overworld settings missing");
      } else {
         return this.func_236234_o_() ? DataResult.success(this, Lifecycle.stable()) : DataResult.success(this);
      }
   }

   private boolean func_236234_o_() {
      return Dimension.func_236060_a_(this.field_236205_e_, this.field_236208_h_);
   }

   public DimensionGeneratorSettings(long p_i231914_1_, boolean p_i231914_3_, boolean p_i231914_4_, SimpleRegistry<Dimension> p_i231914_5_) {
      this(p_i231914_1_, p_i231914_3_, p_i231914_4_, p_i231914_5_, Optional.empty());
      Dimension dimension = p_i231914_5_.func_230516_a_(Dimension.field_236053_b_);
      if (dimension == null) {
         throw new IllegalStateException("Overworld settings missing");
      }
   }

   private DimensionGeneratorSettings(long p_i231915_1_, boolean p_i231915_3_, boolean p_i231915_4_, SimpleRegistry<Dimension> p_i231915_5_, Optional<String> p_i231915_6_) {
      this.field_236205_e_ = p_i231915_1_;
      this.field_236206_f_ = p_i231915_3_;
      this.field_236207_g_ = p_i231915_4_;
      this.field_236208_h_ = p_i231915_5_;
      this.field_236209_i_ = p_i231915_6_;
   }

   public static DimensionGeneratorSettings func_242752_a(DynamicRegistries p_242752_0_) {
      Registry<Biome> registry = p_242752_0_.func_243612_b(Registry.field_239720_u_);
      int i = "North Carolina".hashCode();
      Registry<DimensionType> registry1 = p_242752_0_.func_243612_b(Registry.field_239698_ad_);
      Registry<DimensionSettings> registry2 = p_242752_0_.func_243612_b(Registry.field_243549_ar);
      return new DimensionGeneratorSettings((long)i, true, true, func_242749_a(registry1, DimensionType.func_242718_a(registry1, registry, registry2, (long)i), func_242750_a(registry, registry2, (long)i)));
   }

   public static DimensionGeneratorSettings func_242751_a(Registry<DimensionType> p_242751_0_, Registry<Biome> p_242751_1_, Registry<DimensionSettings> p_242751_2_) {
      long i = (new Random()).nextLong();
      return new DimensionGeneratorSettings(i, true, false, func_242749_a(p_242751_0_, DimensionType.func_242718_a(p_242751_0_, p_242751_1_, p_242751_2_, i), func_242750_a(p_242751_1_, p_242751_2_, i)));
   }

   public static NoiseChunkGenerator func_242750_a(Registry<Biome> p_242750_0_, Registry<DimensionSettings> p_242750_1_, long p_242750_2_) {
      return new NoiseChunkGenerator(new OverworldBiomeProvider(p_242750_2_, false, false, p_242750_0_), p_242750_2_, () -> {
         return p_242750_1_.func_243576_d(DimensionSettings.field_242734_c);
      });
   }

   public long func_236221_b_() {
      return this.field_236205_e_;
   }

   public boolean func_236222_c_() {
      return this.field_236206_f_;
   }

   public boolean func_236223_d_() {
      return this.field_236207_g_;
   }

   public static SimpleRegistry<Dimension> func_242749_a(Registry<DimensionType> p_242749_0_, SimpleRegistry<Dimension> p_242749_1_, ChunkGenerator p_242749_2_) {
      Dimension dimension = p_242749_1_.func_230516_a_(Dimension.field_236053_b_);
      Supplier<DimensionType> supplier = () -> {
         return dimension == null ? p_242749_0_.func_243576_d(DimensionType.field_235999_c_) : dimension.func_236063_b_();
      };
      return func_241520_a_(p_242749_1_, supplier, p_242749_2_);
   }

   public static SimpleRegistry<Dimension> func_241520_a_(SimpleRegistry<Dimension> p_241520_0_, Supplier<DimensionType> p_241520_1_, ChunkGenerator p_241520_2_) {
      SimpleRegistry<Dimension> simpleregistry = new SimpleRegistry<>(Registry.field_239700_af_, Lifecycle.experimental());
      simpleregistry.register(Dimension.field_236053_b_, new Dimension(p_241520_1_, p_241520_2_), Lifecycle.stable());

      for(Entry<RegistryKey<Dimension>, Dimension> entry : p_241520_0_.func_239659_c_()) {
         RegistryKey<Dimension> registrykey = entry.getKey();
         if (registrykey != Dimension.field_236053_b_) {
            simpleregistry.register(registrykey, entry.getValue(), p_241520_0_.func_241876_d(entry.getValue()));
         }
      }

      return simpleregistry;
   }

   public SimpleRegistry<Dimension> func_236224_e_() {
      return this.field_236208_h_;
   }

   public ChunkGenerator func_236225_f_() {
      Dimension dimension = this.field_236208_h_.func_230516_a_(Dimension.field_236053_b_);
      if (dimension == null) {
         throw new IllegalStateException("Overworld settings missing");
      } else {
         return dimension.func_236064_c_();
      }
   }

   public ImmutableSet<RegistryKey<World>> func_236226_g_() {
      return this.func_236224_e_().func_239659_c_().stream().map((p_236218_0_) -> {
         return RegistryKey.func_240903_a_(Registry.field_239699_ae_, p_236218_0_.getKey().func_240901_a_());
      }).collect(ImmutableSet.toImmutableSet());
   }

   public boolean func_236227_h_() {
      return this.func_236225_f_() instanceof DebugChunkGenerator;
   }

   public boolean func_236228_i_() {
      return this.func_236225_f_() instanceof FlatChunkGenerator;
   }

   @OnlyIn(Dist.CLIENT)
   public boolean func_236229_j_() {
      return this.field_236209_i_.isPresent();
   }

   public DimensionGeneratorSettings func_236230_k_() {
      return new DimensionGeneratorSettings(this.field_236205_e_, this.field_236206_f_, true, this.field_236208_h_, this.field_236209_i_);
   }

   @OnlyIn(Dist.CLIENT)
   public DimensionGeneratorSettings func_236231_l_() {
      return new DimensionGeneratorSettings(this.field_236205_e_, !this.field_236206_f_, this.field_236207_g_, this.field_236208_h_);
   }

   @OnlyIn(Dist.CLIENT)
   public DimensionGeneratorSettings func_236232_m_() {
      return new DimensionGeneratorSettings(this.field_236205_e_, this.field_236206_f_, !this.field_236207_g_, this.field_236208_h_);
   }

   public static DimensionGeneratorSettings func_242753_a(DynamicRegistries p_242753_0_, Properties p_242753_1_) {
      String s = MoreObjects.firstNonNull((String)p_242753_1_.get("generator-settings"), "");
      p_242753_1_.put("generator-settings", s);
      String s1 = MoreObjects.firstNonNull((String)p_242753_1_.get("level-seed"), "");
      p_242753_1_.put("level-seed", s1);
      String s2 = (String)p_242753_1_.get("generate-structures");
      boolean flag = s2 == null || Boolean.parseBoolean(s2);
      p_242753_1_.put("generate-structures", Objects.toString(flag));
      String s3 = (String)p_242753_1_.get("level-type");
      String s4 = Optional.ofNullable(s3).map((p_236217_0_) -> {
         return p_236217_0_.toLowerCase(Locale.ROOT);
      }).orElse("default");
      p_242753_1_.put("level-type", s4);
      long i = (new Random()).nextLong();
      if (!s1.isEmpty()) {
         try {
            long j = Long.parseLong(s1);
            if (j != 0L) {
               i = j;
            }
         } catch (NumberFormatException numberformatexception) {
            i = (long)s1.hashCode();
         }
      }

      Registry<DimensionType> registry2 = p_242753_0_.func_243612_b(Registry.field_239698_ad_);
      Registry<Biome> registry = p_242753_0_.func_243612_b(Registry.field_239720_u_);
      Registry<DimensionSettings> registry1 = p_242753_0_.func_243612_b(Registry.field_243549_ar);
      SimpleRegistry<Dimension> simpleregistry = DimensionType.func_242718_a(registry2, registry, registry1, i);
      byte b0 = -1;
      switch(s4.hashCode()) {
      case -1100099890:
         if (s4.equals("largebiomes")) {
            b0 = 3;
         }
         break;
      case 3145593:
         if (s4.equals("flat")) {
            b0 = 0;
         }
         break;
      case 1045526590:
         if (s4.equals("debug_all_block_states")) {
            b0 = 1;
         }
         break;
      case 1271599715:
         if (s4.equals("amplified")) {
            b0 = 2;
         }
      }

      switch(b0) {
      case 0:
         JsonObject jsonobject = !s.isEmpty() ? JSONUtils.fromJson(s) : new JsonObject();
         Dynamic<JsonElement> dynamic = new Dynamic<>(JsonOps.INSTANCE, jsonobject);
         return new DimensionGeneratorSettings(i, flag, false, func_242749_a(registry2, simpleregistry, new FlatChunkGenerator(FlatGenerationSettings.field_236932_a_.parse(dynamic).resultOrPartial(field_236203_c_::error).orElseGet(() -> {
            return FlatGenerationSettings.func_242869_a(registry);
         }))));
      case 1:
         return new DimensionGeneratorSettings(i, flag, false, func_242749_a(registry2, simpleregistry, new DebugChunkGenerator(registry)));
      case 2:
         return new DimensionGeneratorSettings(i, flag, false, func_242749_a(registry2, simpleregistry, new NoiseChunkGenerator(new OverworldBiomeProvider(i, false, false, registry), i, () -> {
            return registry1.func_243576_d(DimensionSettings.field_242735_d);
         })));
      case 3:
         return new DimensionGeneratorSettings(i, flag, false, func_242749_a(registry2, simpleregistry, new NoiseChunkGenerator(new OverworldBiomeProvider(i, false, true, registry), i, () -> {
            return registry1.func_243576_d(DimensionSettings.field_242734_c);
         })));
      default:
         return new DimensionGeneratorSettings(i, flag, false, func_242749_a(registry2, simpleregistry, func_242750_a(registry, registry1, i)));
      }
   }

   @OnlyIn(Dist.CLIENT)
   public DimensionGeneratorSettings func_236220_a_(boolean p_236220_1_, OptionalLong p_236220_2_) {
      long i = p_236220_2_.orElse(this.field_236205_e_);
      SimpleRegistry<Dimension> simpleregistry;
      if (p_236220_2_.isPresent()) {
         simpleregistry = new SimpleRegistry<>(Registry.field_239700_af_, Lifecycle.experimental());
         long j = p_236220_2_.getAsLong();

         for(Entry<RegistryKey<Dimension>, Dimension> entry : this.field_236208_h_.func_239659_c_()) {
            RegistryKey<Dimension> registrykey = entry.getKey();
            simpleregistry.register(registrykey, new Dimension(entry.getValue().func_236059_a_(), entry.getValue().func_236064_c_().func_230349_a_(j)), this.field_236208_h_.func_241876_d(entry.getValue()));
         }
      } else {
         simpleregistry = this.field_236208_h_;
      }

      DimensionGeneratorSettings dimensiongeneratorsettings;
      if (this.func_236227_h_()) {
         dimensiongeneratorsettings = new DimensionGeneratorSettings(i, false, false, simpleregistry);
      } else {
         dimensiongeneratorsettings = new DimensionGeneratorSettings(i, this.func_236222_c_(), this.func_236223_d_() && !p_236220_1_, simpleregistry);
      }

      return dimensiongeneratorsettings;
   }
}