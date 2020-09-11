package net.minecraft.world.biome;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Map.Entry;
import java.util.function.Supplier;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.Util;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.carver.ConfiguredCarver;
import net.minecraft.world.gen.carver.ICarverConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilders;
import net.minecraft.world.gen.surfacebuilders.ISurfaceBuilderConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BiomeGenerationSettings {
   public static final Logger field_242479_a = LogManager.getLogger();
   public static final BiomeGenerationSettings field_242480_b = new BiomeGenerationSettings(() -> {
      return ConfiguredSurfaceBuilders.field_244184_p;
   }, ImmutableMap.of(), ImmutableList.of(), ImmutableList.of());
   public static final MapCodec<BiomeGenerationSettings> field_242481_c = RecordCodecBuilder.mapCodec((p_242495_0_) -> {
      return p_242495_0_.group(ConfiguredSurfaceBuilder.field_244393_b_.fieldOf("surface_builder").forGetter((p_242501_0_) -> {
         return p_242501_0_.field_242482_d;
      }), Codec.simpleMap(GenerationStage.Carving.field_236074_c_, ConfiguredCarver.field_242759_c.promotePartial(Util.func_240982_a_("Carver: ", field_242479_a::error)), IStringSerializable.func_233025_a_(GenerationStage.Carving.values())).fieldOf("carvers").forGetter((p_242499_0_) -> {
         return p_242499_0_.field_242483_e;
      }), ConfiguredFeature.field_242764_c.promotePartial(Util.func_240982_a_("Feature: ", field_242479_a::error)).listOf().fieldOf("features").forGetter((p_242497_0_) -> {
         return p_242497_0_.field_242484_f;
      }), StructureFeature.field_242770_c.promotePartial(Util.func_240982_a_("Structure start: ", field_242479_a::error)).fieldOf("starts").forGetter((p_242488_0_) -> {
         return p_242488_0_.field_242485_g;
      })).apply(p_242495_0_, BiomeGenerationSettings::new);
   });
   private final Supplier<ConfiguredSurfaceBuilder<?>> field_242482_d;
   private final Map<GenerationStage.Carving, List<Supplier<ConfiguredCarver<?>>>> field_242483_e;
   private final List<List<Supplier<ConfiguredFeature<?, ?>>>> field_242484_f;
   private final List<Supplier<StructureFeature<?, ?>>> field_242485_g;
   private final List<ConfiguredFeature<?, ?>> field_242486_h;

   private BiomeGenerationSettings(Supplier<ConfiguredSurfaceBuilder<?>> p_i241935_1_, Map<GenerationStage.Carving, List<Supplier<ConfiguredCarver<?>>>> p_i241935_2_, List<List<Supplier<ConfiguredFeature<?, ?>>>> p_i241935_3_, List<Supplier<StructureFeature<?, ?>>> p_i241935_4_) {
      this.field_242482_d = p_i241935_1_;
      this.field_242483_e = p_i241935_2_;
      this.field_242484_f = p_i241935_3_;
      this.field_242485_g = p_i241935_4_;
      this.field_242486_h = p_i241935_3_.stream().flatMap(Collection::stream).map(Supplier::get).flatMap(ConfiguredFeature::func_242768_d).filter((p_242490_0_) -> {
         return p_242490_0_.feature == Feature.FLOWER;
      }).collect(ImmutableList.toImmutableList());
   }

   public List<Supplier<ConfiguredCarver<?>>> func_242489_a(GenerationStage.Carving p_242489_1_) {
      return this.field_242483_e.getOrDefault(p_242489_1_, ImmutableList.of());
   }

   public boolean func_242493_a(Structure<?> p_242493_1_) {
      return this.field_242485_g.stream().anyMatch((p_242494_1_) -> {
         return (p_242494_1_.get()).field_236268_b_ == p_242493_1_;
      });
   }

   public Collection<Supplier<StructureFeature<?, ?>>> func_242487_a() {
      return this.field_242485_g;
   }

   public StructureFeature<?, ?> func_242491_a(StructureFeature<?, ?> p_242491_1_) {
      return DataFixUtils.orElse(this.field_242485_g.stream().map(Supplier::get).filter((p_242492_1_) -> {
         return p_242492_1_.field_236268_b_ == p_242491_1_.field_236268_b_;
      }).findAny(), p_242491_1_);
   }

   public List<ConfiguredFeature<?, ?>> func_242496_b() {
      return this.field_242486_h;
   }

   public List<List<Supplier<ConfiguredFeature<?, ?>>>> func_242498_c() {
      return this.field_242484_f;
   }

   public Supplier<ConfiguredSurfaceBuilder<?>> func_242500_d() {
      return this.field_242482_d;
   }

   public ISurfaceBuilderConfig func_242502_e() {
      return this.field_242482_d.get().getConfig();
   }

   public static class Builder {
      private Optional<Supplier<ConfiguredSurfaceBuilder<?>>> field_242504_a = Optional.empty();
      private final Map<GenerationStage.Carving, List<Supplier<ConfiguredCarver<?>>>> field_242505_b = Maps.newLinkedHashMap();
      private final List<List<Supplier<ConfiguredFeature<?, ?>>>> field_242506_c = Lists.newArrayList();
      private final List<Supplier<StructureFeature<?, ?>>> field_242507_d = Lists.newArrayList();

      public BiomeGenerationSettings.Builder func_242517_a(ConfiguredSurfaceBuilder<?> p_242517_1_) {
         return this.func_242519_a(() -> {
            return p_242517_1_;
         });
      }

      public BiomeGenerationSettings.Builder func_242519_a(Supplier<ConfiguredSurfaceBuilder<?>> p_242519_1_) {
         this.field_242504_a = Optional.of(p_242519_1_);
         return this;
      }

      public BiomeGenerationSettings.Builder func_242513_a(GenerationStage.Decoration p_242513_1_, ConfiguredFeature<?, ?> p_242513_2_) {
         return this.func_242510_a(p_242513_1_.ordinal(), () -> {
            return p_242513_2_;
         });
      }

      public BiomeGenerationSettings.Builder func_242510_a(int p_242510_1_, Supplier<ConfiguredFeature<?, ?>> p_242510_2_) {
         this.func_242509_a(p_242510_1_);
         this.field_242506_c.get(p_242510_1_).add(p_242510_2_);
         return this;
      }

      public <C extends ICarverConfig> BiomeGenerationSettings.Builder func_242512_a(GenerationStage.Carving p_242512_1_, ConfiguredCarver<C> p_242512_2_) {
         this.field_242505_b.computeIfAbsent(p_242512_1_, (p_242511_0_) -> {
            return Lists.newArrayList();
         }).add(() -> {
            return p_242512_2_;
         });
         return this;
      }

      public BiomeGenerationSettings.Builder func_242516_a(StructureFeature<?, ?> p_242516_1_) {
         this.field_242507_d.add(() -> {
            return p_242516_1_;
         });
         return this;
      }

      private void func_242509_a(int p_242509_1_) {
         while(this.field_242506_c.size() <= p_242509_1_) {
            this.field_242506_c.add(Lists.newArrayList());
         }

      }

      public BiomeGenerationSettings func_242508_a() {
         return new BiomeGenerationSettings(this.field_242504_a.orElseThrow(() -> {
            return new IllegalStateException("Missing surface builder");
         }), this.field_242505_b.entrySet().stream().collect(ImmutableMap.toImmutableMap(Entry::getKey, (p_242518_0_) -> {
            return ImmutableList.copyOf((Collection)p_242518_0_.getValue());
         })), this.field_242506_c.stream().map(ImmutableList::copyOf).collect(ImmutableList.toImmutableList()), ImmutableList.copyOf(this.field_242507_d));
      }
   }
}