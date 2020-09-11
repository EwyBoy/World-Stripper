package net.minecraft.world.biome;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.longs.Long2FloatLinkedOpenHashMap;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.client.audio.BackgroundMusicSelector;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.ReportedException;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.math.SectionPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKeyCodec;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.FoliageColors;
import net.minecraft.world.GrassColors;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.LightType;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.PerlinNoiseGenerator;
import net.minecraft.world.gen.WorldGenRegion;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.StructureManager;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilder;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class Biome extends net.minecraftforge.registries.ForgeRegistryEntry<Biome> {
   public static final Logger LOGGER = LogManager.getLogger();
   public static final Codec<Biome> field_242418_b = RecordCodecBuilder.create((p_235064_0_) -> {
      return p_235064_0_.group(Biome.Climate.field_242459_a.forGetter((p_242446_0_) -> {
         return p_242446_0_.field_242423_j;
      }), Biome.Category.field_235102_r_.fieldOf("category").forGetter((p_235087_0_) -> {
         return p_235087_0_.category;
      }), Codec.FLOAT.fieldOf("depth").forGetter((p_235086_0_) -> {
         return p_235086_0_.depth;
      }), Codec.FLOAT.fieldOf("scale").forGetter((p_235085_0_) -> {
         return p_235085_0_.scale;
      }), BiomeAmbience.field_235204_a_.fieldOf("effects").forGetter((p_242444_0_) -> {
         return p_242444_0_.field_235052_p_;
      }), BiomeGenerationSettings.field_242481_c.forGetter((p_242443_0_) -> {
         return p_242443_0_.field_242424_k;
      }), MobSpawnInfo.field_242552_c.forGetter((p_242442_0_) -> {
         return p_242442_0_.field_242425_l;
      })).apply(p_235064_0_, Biome::new);
   });
   public static final Codec<Biome> field_242419_c = RecordCodecBuilder.create((p_242432_0_) -> {
      return p_242432_0_.group(Biome.Climate.field_242459_a.forGetter((p_242441_0_) -> {
         return p_242441_0_.field_242423_j;
      }), Biome.Category.field_235102_r_.fieldOf("category").forGetter((p_242439_0_) -> {
         return p_242439_0_.category;
      }), Codec.FLOAT.fieldOf("depth").forGetter((p_242438_0_) -> {
         return p_242438_0_.depth;
      }), Codec.FLOAT.fieldOf("scale").forGetter((p_242434_0_) -> {
         return p_242434_0_.scale;
      }), BiomeAmbience.field_235204_a_.fieldOf("effects").forGetter((p_242429_0_) -> {
         return p_242429_0_.field_235052_p_;
      })).apply(p_242432_0_, (p_242428_0_, p_242428_1_, p_242428_2_, p_242428_3_, p_242428_4_) -> {
         return new Biome(p_242428_0_, p_242428_1_, p_242428_2_, p_242428_3_, p_242428_4_, BiomeGenerationSettings.field_242480_b, MobSpawnInfo.field_242551_b);
      });
   });
   public static final Codec<Supplier<Biome>> field_235051_b_ = RegistryKeyCodec.func_241794_a_(Registry.field_239720_u_, field_242418_b);
   public static final Codec<List<Supplier<Biome>>> field_242420_e = RegistryKeyCodec.func_244328_b(Registry.field_239720_u_, field_242418_b);
   private final Map<Integer, List<Structure<?>>> field_242421_g = Registry.STRUCTURE_FEATURE.stream().collect(Collectors.groupingBy((p_242435_0_) -> {
      return p_242435_0_.func_236396_f_().ordinal();
   }));
   private static final PerlinNoiseGenerator TEMPERATURE_NOISE = new PerlinNoiseGenerator(new SharedSeedRandom(1234L), ImmutableList.of(0));
   private static final PerlinNoiseGenerator field_242422_i = new PerlinNoiseGenerator(new SharedSeedRandom(3456L), ImmutableList.of(-2, -1, 0));
   public static final PerlinNoiseGenerator INFO_NOISE = new PerlinNoiseGenerator(new SharedSeedRandom(2345L), ImmutableList.of(0));
   private final Biome.Climate field_242423_j;
   private final BiomeGenerationSettings field_242424_k;
   private final MobSpawnInfo field_242425_l;
   private final float depth;
   private final float scale;
   private final Biome.Category category;
   private final BiomeAmbience field_235052_p_;
   private final ThreadLocal<Long2FloatLinkedOpenHashMap> field_225488_v = ThreadLocal.withInitial(() -> {
      return Util.make(() -> {
         Long2FloatLinkedOpenHashMap long2floatlinkedopenhashmap = new Long2FloatLinkedOpenHashMap(1024, 0.25F) {
            protected void rehash(int p_rehash_1_) {
            }
         };
         long2floatlinkedopenhashmap.defaultReturnValue(Float.NaN);
         return long2floatlinkedopenhashmap;
      });
   });

   private Biome(Biome.Climate p_i241927_1_, Biome.Category p_i241927_2_, float p_i241927_3_, float p_i241927_4_, BiomeAmbience p_i241927_5_, BiomeGenerationSettings p_i241927_6_, MobSpawnInfo p_i241927_7_) {
      this.field_242423_j = p_i241927_1_;
      this.field_242424_k = p_i241927_6_;
      this.field_242425_l = p_i241927_7_;
      this.category = p_i241927_2_;
      this.depth = p_i241927_3_;
      this.scale = p_i241927_4_;
      this.field_235052_p_ = p_i241927_5_;
   }

   @OnlyIn(Dist.CLIENT)
   public int getSkyColor() {
      return this.field_235052_p_.func_242527_d();
   }

   public MobSpawnInfo func_242433_b() {
      return this.field_242425_l;
   }

   public Biome.RainType getPrecipitation() {
      return this.field_242423_j.field_242460_b;
   }

   /**
    * Checks to see if the rainfall level of the biome is extremely high
    */
   public boolean isHighHumidity() {
      return this.getDownfall() > 0.85F;
   }

   private float func_242437_b(BlockPos p_242437_1_) {
      float f = this.field_242423_j.field_242462_d.func_241852_a(p_242437_1_, this.func_242445_k());
      if (p_242437_1_.getY() > 64) {
         float f1 = (float)(TEMPERATURE_NOISE.noiseAt((double)((float)p_242437_1_.getX() / 8.0F), (double)((float)p_242437_1_.getZ() / 8.0F), false) * 4.0D);
         return f - (f1 + (float)p_242437_1_.getY() - 64.0F) * 0.05F / 30.0F;
      } else {
         return f;
      }
   }

   public final float getTemperature(BlockPos pos) {
      long i = pos.toLong();
      Long2FloatLinkedOpenHashMap long2floatlinkedopenhashmap = this.field_225488_v.get();
      float f = long2floatlinkedopenhashmap.get(i);
      if (!Float.isNaN(f)) {
         return f;
      } else {
         float f1 = this.func_242437_b(pos);
         if (long2floatlinkedopenhashmap.size() == 1024) {
            long2floatlinkedopenhashmap.removeFirstFloat();
         }

         long2floatlinkedopenhashmap.put(i, f1);
         return f1;
      }
   }

   public boolean doesWaterFreeze(IWorldReader worldIn, BlockPos pos) {
      return this.doesWaterFreeze(worldIn, pos, true);
   }

   public boolean doesWaterFreeze(IWorldReader worldIn, BlockPos water, boolean mustBeAtEdge) {
      if (this.getTemperature(water) >= 0.15F) {
         return false;
      } else {
         if (water.getY() >= 0 && water.getY() < 256 && worldIn.getLightFor(LightType.BLOCK, water) < 10) {
            BlockState blockstate = worldIn.getBlockState(water);
            FluidState fluidstate = worldIn.getFluidState(water);
            if (fluidstate.getFluid() == Fluids.WATER && blockstate.getBlock() instanceof FlowingFluidBlock) {
               if (!mustBeAtEdge) {
                  return true;
               }

               boolean flag = worldIn.hasWater(water.west()) && worldIn.hasWater(water.east()) && worldIn.hasWater(water.north()) && worldIn.hasWater(water.south());
               if (!flag) {
                  return true;
               }
            }
         }

         return false;
      }
   }

   public boolean doesSnowGenerate(IWorldReader worldIn, BlockPos pos) {
      if (this.getTemperature(pos) >= 0.15F) {
         return false;
      } else {
         if (pos.getY() >= 0 && pos.getY() < 256 && worldIn.getLightFor(LightType.BLOCK, pos) < 10) {
            BlockState blockstate = worldIn.getBlockState(pos);
            if (blockstate.isAir(worldIn, pos) && Blocks.SNOW.getDefaultState().isValidPosition(worldIn, pos)) {
               return true;
            }
         }

         return false;
      }
   }

   public BiomeGenerationSettings func_242440_e() {
      return this.field_242424_k;
   }

   public void func_242427_a(StructureManager p_242427_1_, ChunkGenerator p_242427_2_, WorldGenRegion p_242427_3_, long p_242427_4_, SharedSeedRandom p_242427_6_, BlockPos p_242427_7_) {
      List<List<Supplier<ConfiguredFeature<?, ?>>>> list = this.field_242424_k.func_242498_c();
      int i = GenerationStage.Decoration.values().length;

      for(int j = 0; j < i; ++j) {
         int k = 0;
         if (p_242427_1_.func_235005_a_()) {
            for(Structure<?> structure : this.field_242421_g.getOrDefault(j, Collections.emptyList())) {
               p_242427_6_.setFeatureSeed(p_242427_4_, k, j);
               int l = p_242427_7_.getX() >> 4;
               int i1 = p_242427_7_.getZ() >> 4;
               int j1 = l << 4;
               int k1 = i1 << 4;

               try {
                  p_242427_1_.func_235011_a_(SectionPos.from(p_242427_7_), structure).forEach((p_242426_8_) -> {
                     p_242426_8_.func_230366_a_(p_242427_3_, p_242427_1_, p_242427_2_, p_242427_6_, new MutableBoundingBox(j1, k1, j1 + 15, k1 + 15), new ChunkPos(l, i1));
                  });
               } catch (Exception exception) {
                  CrashReport crashreport = CrashReport.makeCrashReport(exception, "Feature placement");
                  crashreport.makeCategory("Feature").addDetail("Id", Registry.STRUCTURE_FEATURE.getKey(structure)).addDetail("Description", () -> {
                     return structure.toString();
                  });
                  throw new ReportedException(crashreport);
               }

               ++k;
            }
         }

         if (list.size() > j) {
            for(Supplier<ConfiguredFeature<?, ?>> supplier : list.get(j)) {
               ConfiguredFeature<?, ?> configuredfeature = supplier.get();
               p_242427_6_.setFeatureSeed(p_242427_4_, k, j);

               try {
                  configuredfeature.func_242765_a(p_242427_3_, p_242427_2_, p_242427_6_, p_242427_7_);
               } catch (Exception exception1) {
                  CrashReport crashreport1 = CrashReport.makeCrashReport(exception1, "Feature placement");
                  crashreport1.makeCategory("Feature").addDetail("Id", Registry.FEATURE.getKey(configuredfeature.feature)).addDetail("Config", configuredfeature.config).addDetail("Description", () -> {
                     return configuredfeature.feature.toString();
                  });
                  throw new ReportedException(crashreport1);
               }

               ++k;
            }
         }
      }

   }

   @OnlyIn(Dist.CLIENT)
   public int func_235080_i_() {
      return this.field_235052_p_.func_235213_a_();
   }

   @OnlyIn(Dist.CLIENT)
   public int getGrassColor(double posX, double posZ) {
      int i = this.field_235052_p_.func_242529_f().orElseGet(this::func_242448_v);
      return this.field_235052_p_.func_242531_g().func_241853_a(posX, posZ, i);
   }

   @OnlyIn(Dist.CLIENT)
   private int func_242448_v() {
      double d0 = (double)MathHelper.clamp(this.field_242423_j.field_242461_c, 0.0F, 1.0F);
      double d1 = (double)MathHelper.clamp(this.field_242423_j.field_242463_e, 0.0F, 1.0F);
      return GrassColors.get(d0, d1);
   }

   @OnlyIn(Dist.CLIENT)
   public int getFoliageColor() {
      return this.field_235052_p_.func_242528_e().orElseGet(this::func_242449_w);
   }

   @OnlyIn(Dist.CLIENT)
   private int func_242449_w() {
      double d0 = (double)MathHelper.clamp(this.field_242423_j.field_242461_c, 0.0F, 1.0F);
      double d1 = (double)MathHelper.clamp(this.field_242423_j.field_242463_e, 0.0F, 1.0F);
      return FoliageColors.get(d0, d1);
   }

   public void buildSurface(Random random, IChunk chunkIn, int x, int z, int startHeight, double noise, BlockState defaultBlock, BlockState defaultFluid, int seaLevel, long seed) {
      ConfiguredSurfaceBuilder<?> configuredsurfacebuilder = this.field_242424_k.func_242500_d().get();
      configuredsurfacebuilder.setSeed(seed);
      configuredsurfacebuilder.buildSurface(random, chunkIn, this, x, z, startHeight, noise, defaultBlock, defaultFluid, seaLevel, seed);
   }

   public final float getDepth() {
      return this.depth;
   }

   /**
    * Gets a floating point representation of this biome's rainfall
    */
   public final float getDownfall() {
      return this.field_242423_j.field_242463_e;
   }

   public final float getScale() {
      return this.scale;
   }

   public final float func_242445_k() {
      return this.field_242423_j.field_242461_c;
   }

   public BiomeAmbience func_235089_q_() {
      return this.field_235052_p_;
   }

   @OnlyIn(Dist.CLIENT)
   public final int getWaterColor() {
      return this.field_235052_p_.func_235216_b_();
   }

   @OnlyIn(Dist.CLIENT)
   public final int getWaterFogColor() {
      return this.field_235052_p_.func_235218_c_();
   }

   @OnlyIn(Dist.CLIENT)
   public Optional<ParticleEffectAmbience> func_235090_t_() {
      return this.field_235052_p_.func_235220_d_();
   }

   @OnlyIn(Dist.CLIENT)
   public Optional<SoundEvent> func_235091_u_() {
      return this.field_235052_p_.func_235222_e_();
   }

   @OnlyIn(Dist.CLIENT)
   public Optional<MoodSoundAmbience> func_235092_v_() {
      return this.field_235052_p_.func_235224_f_();
   }

   @OnlyIn(Dist.CLIENT)
   public Optional<SoundAdditionsAmbience> func_235093_w_() {
      return this.field_235052_p_.func_235226_g_();
   }

   @OnlyIn(Dist.CLIENT)
   public Optional<BackgroundMusicSelector> func_235094_x_() {
      return this.field_235052_p_.func_235228_h_();
   }

   public final Biome.Category getCategory() {
      return this.category;
   }

   public String toString() {
      ResourceLocation resourcelocation = WorldGenRegistries.field_243657_i.getKey(this);
      return resourcelocation == null ? super.toString() : resourcelocation.toString();
   }

   public static class Attributes {
      public static final Codec<Biome.Attributes> field_235104_a_ = RecordCodecBuilder.create((p_235111_0_) -> {
         return p_235111_0_.group(Codec.floatRange(-2.0F, 2.0F).fieldOf("temperature").forGetter((p_235116_0_) -> {
            return p_235116_0_.field_235105_b_;
         }), Codec.floatRange(-2.0F, 2.0F).fieldOf("humidity").forGetter((p_235115_0_) -> {
            return p_235115_0_.field_235106_c_;
         }), Codec.floatRange(-2.0F, 2.0F).fieldOf("altitude").forGetter((p_235114_0_) -> {
            return p_235114_0_.field_235107_d_;
         }), Codec.floatRange(-2.0F, 2.0F).fieldOf("weirdness").forGetter((p_235113_0_) -> {
            return p_235113_0_.field_235108_e_;
         }), Codec.floatRange(0.0F, 1.0F).fieldOf("offset").forGetter((p_235112_0_) -> {
            return p_235112_0_.field_235109_f_;
         })).apply(p_235111_0_, Biome.Attributes::new);
      });
      private final float field_235105_b_;
      private final float field_235106_c_;
      private final float field_235107_d_;
      private final float field_235108_e_;
      private final float field_235109_f_;

      public Attributes(float p_i231632_1_, float p_i231632_2_, float p_i231632_3_, float p_i231632_4_, float p_i231632_5_) {
         this.field_235105_b_ = p_i231632_1_;
         this.field_235106_c_ = p_i231632_2_;
         this.field_235107_d_ = p_i231632_3_;
         this.field_235108_e_ = p_i231632_4_;
         this.field_235109_f_ = p_i231632_5_;
      }

      public boolean equals(Object p_equals_1_) {
         if (this == p_equals_1_) {
            return true;
         } else if (p_equals_1_ != null && this.getClass() == p_equals_1_.getClass()) {
            Biome.Attributes biome$attributes = (Biome.Attributes)p_equals_1_;
            if (Float.compare(biome$attributes.field_235105_b_, this.field_235105_b_) != 0) {
               return false;
            } else if (Float.compare(biome$attributes.field_235106_c_, this.field_235106_c_) != 0) {
               return false;
            } else if (Float.compare(biome$attributes.field_235107_d_, this.field_235107_d_) != 0) {
               return false;
            } else {
               return Float.compare(biome$attributes.field_235108_e_, this.field_235108_e_) == 0;
            }
         } else {
            return false;
         }
      }

      public int hashCode() {
         int i = this.field_235105_b_ != 0.0F ? Float.floatToIntBits(this.field_235105_b_) : 0;
         i = 31 * i + (this.field_235106_c_ != 0.0F ? Float.floatToIntBits(this.field_235106_c_) : 0);
         i = 31 * i + (this.field_235107_d_ != 0.0F ? Float.floatToIntBits(this.field_235107_d_) : 0);
         return 31 * i + (this.field_235108_e_ != 0.0F ? Float.floatToIntBits(this.field_235108_e_) : 0);
      }

      public float func_235110_a_(Biome.Attributes p_235110_1_) {
         return (this.field_235105_b_ - p_235110_1_.field_235105_b_) * (this.field_235105_b_ - p_235110_1_.field_235105_b_) + (this.field_235106_c_ - p_235110_1_.field_235106_c_) * (this.field_235106_c_ - p_235110_1_.field_235106_c_) + (this.field_235107_d_ - p_235110_1_.field_235107_d_) * (this.field_235107_d_ - p_235110_1_.field_235107_d_) + (this.field_235108_e_ - p_235110_1_.field_235108_e_) * (this.field_235108_e_ - p_235110_1_.field_235108_e_) + (this.field_235109_f_ - p_235110_1_.field_235109_f_) * (this.field_235109_f_ - p_235110_1_.field_235109_f_);
      }
   }

   public static class Builder {
      @Nullable
      private Biome.RainType precipitation;
      @Nullable
      private Biome.Category category;
      @Nullable
      private Float depth;
      @Nullable
      private Float scale;
      @Nullable
      private Float temperature;
      private Biome.TemperatureModifier field_242452_f = Biome.TemperatureModifier.NONE;
      @Nullable
      private Float downfall;
      @Nullable
      private BiomeAmbience field_235096_j_;
      @Nullable
      private MobSpawnInfo field_242453_i;
      @Nullable
      private BiomeGenerationSettings field_242454_j;

      public Biome.Builder precipitation(Biome.RainType precipitationIn) {
         this.precipitation = precipitationIn;
         return this;
      }

      public Biome.Builder category(Biome.Category biomeCategory) {
         this.category = biomeCategory;
         return this;
      }

      public Biome.Builder depth(float depthIn) {
         this.depth = depthIn;
         return this;
      }

      public Biome.Builder scale(float scaleIn) {
         this.scale = scaleIn;
         return this;
      }

      public Biome.Builder temperature(float temperatureIn) {
         this.temperature = temperatureIn;
         return this;
      }

      public Biome.Builder downfall(float downfallIn) {
         this.downfall = downfallIn;
         return this;
      }

      public Biome.Builder func_235097_a_(BiomeAmbience p_235097_1_) {
         this.field_235096_j_ = p_235097_1_;
         return this;
      }

      public Biome.Builder func_242458_a(MobSpawnInfo p_242458_1_) {
         this.field_242453_i = p_242458_1_;
         return this;
      }

      public Biome.Builder func_242457_a(BiomeGenerationSettings p_242457_1_) {
         this.field_242454_j = p_242457_1_;
         return this;
      }

      public Biome.Builder func_242456_a(Biome.TemperatureModifier p_242456_1_) {
         this.field_242452_f = p_242456_1_;
         return this;
      }

      public Biome func_242455_a() {
         if (this.precipitation != null && this.category != null && this.depth != null && this.scale != null && this.temperature != null && this.downfall != null && this.field_235096_j_ != null && this.field_242453_i != null && this.field_242454_j != null) {
            return new Biome(new Biome.Climate(this.precipitation, this.temperature, this.field_242452_f, this.downfall), this.category, this.depth, this.scale, this.field_235096_j_, this.field_242454_j, this.field_242453_i);
         } else {
            throw new IllegalStateException("You are missing parameters to build a proper biome\n" + this);
         }
      }

      public String toString() {
         return "BiomeBuilder{\nprecipitation=" + this.precipitation + ",\nbiomeCategory=" + this.category + ",\ndepth=" + this.depth + ",\nscale=" + this.scale + ",\ntemperature=" + this.temperature + ",\ntemperatureModifier=" + this.field_242452_f + ",\ndownfall=" + this.downfall + ",\nspecialEffects=" + this.field_235096_j_ + ",\nmobSpawnSettings=" + this.field_242453_i + ",\ngenerationSettings=" + this.field_242454_j + ",\n" + '}';
      }
   }

   public static enum Category implements IStringSerializable {
      NONE("none"),
      TAIGA("taiga"),
      EXTREME_HILLS("extreme_hills"),
      JUNGLE("jungle"),
      MESA("mesa"),
      PLAINS("plains"),
      SAVANNA("savanna"),
      ICY("icy"),
      THEEND("the_end"),
      BEACH("beach"),
      FOREST("forest"),
      OCEAN("ocean"),
      DESERT("desert"),
      RIVER("river"),
      SWAMP("swamp"),
      MUSHROOM("mushroom"),
      NETHER("nether");

      public static final Codec<Biome.Category> field_235102_r_ = IStringSerializable.func_233023_a_(Biome.Category::values, Biome.Category::func_235103_a_);
      private static final Map<String, Biome.Category> BY_NAME = Arrays.stream(values()).collect(Collectors.toMap(Biome.Category::getName, (p_222353_0_) -> {
         return p_222353_0_;
      }));
      private final String name;

      private Category(String name) {
         this.name = name;
      }

      public String getName() {
         return this.name;
      }

      public static Biome.Category func_235103_a_(String p_235103_0_) {
         return BY_NAME.get(p_235103_0_);
      }

      public String func_176610_l() {
         return this.name;
      }
   }

   static class Climate {
      public static final MapCodec<Biome.Climate> field_242459_a = RecordCodecBuilder.mapCodec((p_242465_0_) -> {
         return p_242465_0_.group(Biome.RainType.field_235121_d_.fieldOf("precipitation").forGetter((p_242472_0_) -> {
            return p_242472_0_.field_242460_b;
         }), Codec.FLOAT.fieldOf("temperature").forGetter((p_242471_0_) -> {
            return p_242471_0_.field_242461_c;
         }), Biome.TemperatureModifier.field_242473_c.optionalFieldOf("temperature_modifier", Biome.TemperatureModifier.NONE).forGetter((p_242470_0_) -> {
            return p_242470_0_.field_242462_d;
         }), Codec.FLOAT.fieldOf("downfall").forGetter((p_242469_0_) -> {
            return p_242469_0_.field_242463_e;
         })).apply(p_242465_0_, Biome.Climate::new);
      });
      private final Biome.RainType field_242460_b;
      private final float field_242461_c;
      private final Biome.TemperatureModifier field_242462_d;
      private final float field_242463_e;

      private Climate(Biome.RainType p_i241929_1_, float p_i241929_2_, Biome.TemperatureModifier p_i241929_3_, float p_i241929_4_) {
         this.field_242460_b = p_i241929_1_;
         this.field_242461_c = p_i241929_2_;
         this.field_242462_d = p_i241929_3_;
         this.field_242463_e = p_i241929_4_;
      }
   }

   public static enum RainType implements IStringSerializable {
      NONE("none"),
      RAIN("rain"),
      SNOW("snow");

      public static final Codec<Biome.RainType> field_235121_d_ = IStringSerializable.func_233023_a_(Biome.RainType::values, Biome.RainType::func_235122_a_);
      private static final Map<String, Biome.RainType> BY_NAME = Arrays.stream(values()).collect(Collectors.toMap(Biome.RainType::getName, (p_222360_0_) -> {
         return p_222360_0_;
      }));
      private final String name;

      private RainType(String name) {
         this.name = name;
      }

      public String getName() {
         return this.name;
      }

      public static Biome.RainType func_235122_a_(String p_235122_0_) {
         return BY_NAME.get(p_235122_0_);
      }

      public String func_176610_l() {
         return this.name;
      }
   }

   public static enum TemperatureModifier implements IStringSerializable {
      NONE("none") {
         public float func_241852_a(BlockPos p_241852_1_, float p_241852_2_) {
            return p_241852_2_;
         }
      },
      FROZEN("frozen") {
         public float func_241852_a(BlockPos p_241852_1_, float p_241852_2_) {
            double d0 = Biome.field_242422_i.noiseAt((double)p_241852_1_.getX() * 0.05D, (double)p_241852_1_.getZ() * 0.05D, false) * 7.0D;
            double d1 = Biome.INFO_NOISE.noiseAt((double)p_241852_1_.getX() * 0.2D, (double)p_241852_1_.getZ() * 0.2D, false);
            double d2 = d0 + d1;
            if (d2 < 0.3D) {
               double d3 = Biome.INFO_NOISE.noiseAt((double)p_241852_1_.getX() * 0.09D, (double)p_241852_1_.getZ() * 0.09D, false);
               if (d3 < 0.8D) {
                  return 0.2F;
               }
            }

            return p_241852_2_;
         }
      };

      private final String field_242474_d;
      public static final Codec<Biome.TemperatureModifier> field_242473_c = IStringSerializable.func_233023_a_(Biome.TemperatureModifier::values, Biome.TemperatureModifier::func_242477_a);
      private static final Map<String, Biome.TemperatureModifier> field_242475_e = Arrays.stream(values()).collect(Collectors.toMap(Biome.TemperatureModifier::func_242478_b, (p_242476_0_) -> {
         return p_242476_0_;
      }));

      public abstract float func_241852_a(BlockPos p_241852_1_, float p_241852_2_);

      private TemperatureModifier(String p_i241931_3_) {
         this.field_242474_d = p_i241931_3_;
      }

      public String func_242478_b() {
         return this.field_242474_d;
      }

      public String func_176610_l() {
         return this.field_242474_d;
      }

      public static Biome.TemperatureModifier func_242477_a(String p_242477_0_) {
         return field_242475_e.get(p_242477_0_);
      }
   }
}