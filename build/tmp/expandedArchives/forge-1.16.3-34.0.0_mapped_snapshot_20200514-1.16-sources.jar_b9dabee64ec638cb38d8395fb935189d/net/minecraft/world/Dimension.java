package net.minecraft.world;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.mojang.serialization.Codec;
import com.mojang.serialization.Lifecycle;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.function.Supplier;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.SimpleRegistry;
import net.minecraft.world.biome.provider.EndBiomeProvider;
import net.minecraft.world.biome.provider.NetherBiomeProvider;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.DimensionSettings;
import net.minecraft.world.gen.NoiseChunkGenerator;

public final class Dimension {
   public static final Codec<Dimension> field_236052_a_ = RecordCodecBuilder.create((p_236061_0_) -> {
      return p_236061_0_.group(DimensionType.field_236002_f_.fieldOf("type").forGetter(Dimension::func_236059_a_), ChunkGenerator.field_235948_a_.fieldOf("generator").forGetter(Dimension::func_236064_c_)).apply(p_236061_0_, p_236061_0_.stable(Dimension::new));
   });
   public static final RegistryKey<Dimension> field_236053_b_ = RegistryKey.func_240903_a_(Registry.field_239700_af_, new ResourceLocation("overworld"));
   public static final RegistryKey<Dimension> field_236054_c_ = RegistryKey.func_240903_a_(Registry.field_239700_af_, new ResourceLocation("the_nether"));
   public static final RegistryKey<Dimension> field_236055_d_ = RegistryKey.func_240903_a_(Registry.field_239700_af_, new ResourceLocation("the_end"));
   private static final LinkedHashSet<RegistryKey<Dimension>> field_236056_e_ = Sets.newLinkedHashSet(ImmutableList.of(field_236053_b_, field_236054_c_, field_236055_d_));
   private final Supplier<DimensionType> field_236057_f_;
   private final ChunkGenerator field_236058_g_;

   public Dimension(Supplier<DimensionType> p_i231900_1_, ChunkGenerator p_i231900_2_) {
      this.field_236057_f_ = p_i231900_1_;
      this.field_236058_g_ = p_i231900_2_;
   }

   public Supplier<DimensionType> func_236059_a_() {
      return this.field_236057_f_;
   }

   public DimensionType func_236063_b_() {
      return this.field_236057_f_.get();
   }

   public ChunkGenerator func_236064_c_() {
      return this.field_236058_g_;
   }

   public static SimpleRegistry<Dimension> func_236062_a_(SimpleRegistry<Dimension> p_236062_0_) {
      SimpleRegistry<Dimension> simpleregistry = new SimpleRegistry<>(Registry.field_239700_af_, Lifecycle.experimental());

      for(RegistryKey<Dimension> registrykey : field_236056_e_) {
         Dimension dimension = p_236062_0_.func_230516_a_(registrykey);
         if (dimension != null) {
            simpleregistry.register(registrykey, dimension, p_236062_0_.func_241876_d(dimension));
         }
      }

      for(Entry<RegistryKey<Dimension>, Dimension> entry : p_236062_0_.func_239659_c_()) {
         RegistryKey<Dimension> registrykey1 = entry.getKey();
         if (!field_236056_e_.contains(registrykey1)) {
            simpleregistry.register(registrykey1, entry.getValue(), p_236062_0_.func_241876_d(entry.getValue()));
         }
      }

      return simpleregistry;
   }

   public static boolean func_236060_a_(long p_236060_0_, SimpleRegistry<Dimension> p_236060_2_) {
      List<Entry<RegistryKey<Dimension>, Dimension>> list = Lists.newArrayList(p_236060_2_.func_239659_c_());
      if (list.size() != field_236056_e_.size()) {
         return false;
      } else {
         Entry<RegistryKey<Dimension>, Dimension> entry = list.get(0);
         Entry<RegistryKey<Dimension>, Dimension> entry1 = list.get(1);
         Entry<RegistryKey<Dimension>, Dimension> entry2 = list.get(2);
         if (entry.getKey() == field_236053_b_ && entry1.getKey() == field_236054_c_ && entry2.getKey() == field_236055_d_) {
            if (!entry.getValue().func_236063_b_().func_242714_a(DimensionType.field_236004_h_) && entry.getValue().func_236063_b_() != DimensionType.field_241498_j_) {
               return false;
            } else if (!entry1.getValue().func_236063_b_().func_242714_a(DimensionType.field_236005_i_)) {
               return false;
            } else if (!entry2.getValue().func_236063_b_().func_242714_a(DimensionType.field_236006_j_)) {
               return false;
            } else if (entry1.getValue().func_236064_c_() instanceof NoiseChunkGenerator && entry2.getValue().func_236064_c_() instanceof NoiseChunkGenerator) {
               NoiseChunkGenerator noisechunkgenerator = (NoiseChunkGenerator)entry1.getValue().func_236064_c_();
               NoiseChunkGenerator noisechunkgenerator1 = (NoiseChunkGenerator)entry2.getValue().func_236064_c_();
               if (!noisechunkgenerator.func_236088_a_(p_236060_0_, DimensionSettings.field_242736_e)) {
                  return false;
               } else if (!noisechunkgenerator1.func_236088_a_(p_236060_0_, DimensionSettings.field_242737_f)) {
                  return false;
               } else if (!(noisechunkgenerator.getBiomeProvider() instanceof NetherBiomeProvider)) {
                  return false;
               } else {
                  NetherBiomeProvider netherbiomeprovider = (NetherBiomeProvider)noisechunkgenerator.getBiomeProvider();
                  if (!netherbiomeprovider.func_235280_b_(p_236060_0_)) {
                     return false;
                  } else if (!(noisechunkgenerator1.getBiomeProvider() instanceof EndBiomeProvider)) {
                     return false;
                  } else {
                     EndBiomeProvider endbiomeprovider = (EndBiomeProvider)noisechunkgenerator1.getBiomeProvider();
                     return endbiomeprovider.func_235318_b_(p_236060_0_);
                  }
               }
            } else {
               return false;
            }
         } else {
            return false;
         }
      }
   }
}