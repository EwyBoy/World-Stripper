package net.minecraft.util.registry;

import com.google.common.collect.Maps;
import com.mojang.serialization.Lifecycle;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeRegistry;
import net.minecraft.world.gen.DimensionSettings;
import net.minecraft.world.gen.carver.ConfiguredCarver;
import net.minecraft.world.gen.carver.ConfiguredCarvers;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Features;
import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraft.world.gen.feature.jigsaw.JigsawPattern;
import net.minecraft.world.gen.feature.jigsaw.JigsawPatternRegistry;
import net.minecraft.world.gen.feature.structure.StructureFeatures;
import net.minecraft.world.gen.feature.template.ProcessorLists;
import net.minecraft.world.gen.feature.template.StructureProcessorList;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilders;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class WorldGenRegistries {
   protected static final Logger field_243649_a = LogManager.getLogger();
   private static final Map<ResourceLocation, Supplier<?>> field_243659_k = Maps.newLinkedHashMap();
   private static final MutableRegistry<MutableRegistry<?>> field_243660_l = new SimpleRegistry<>(RegistryKey.func_240904_a_(new ResourceLocation("root")), Lifecycle.experimental());
   public static final Registry<? extends Registry<?>> field_243650_b = field_243660_l;
   public static final Registry<ConfiguredSurfaceBuilder<?>> field_243651_c = func_243667_a(Registry.field_243550_as, () -> {
      return ConfiguredSurfaceBuilders.field_244184_p;
   });
   public static final Registry<ConfiguredCarver<?>> field_243652_d = func_243667_a(Registry.field_243551_at, () -> {
      return ConfiguredCarvers.field_243767_a;
   });
   public static final Registry<ConfiguredFeature<?, ?>> field_243653_e = func_243667_a(Registry.field_243552_au, () -> {
      return Features.field_243862_bH;
   });
   public static final Registry<StructureFeature<?, ?>> field_243654_f = func_243667_a(Registry.field_243553_av, () -> {
      return StructureFeatures.field_244136_b;
   });
   public static final Registry<StructureProcessorList> field_243655_g = func_243667_a(Registry.field_243554_aw, () -> {
      return ProcessorLists.field_244102_b;
   });
   public static final Registry<JigsawPattern> field_243656_h = func_243667_a(Registry.field_243555_ax, JigsawPatternRegistry::func_244093_a);
   @Deprecated public static final Registry<Biome> field_243657_i = forge(Registry.field_239720_u_, () -> {
      return BiomeRegistry.field_244200_a;
   });
   public static final Registry<DimensionSettings> field_243658_j = func_243667_a(Registry.field_243549_ar, DimensionSettings::func_242746_i);

   private static <T> Registry<T> func_243667_a(RegistryKey<? extends Registry<T>> p_243667_0_, Supplier<T> p_243667_1_) {
      return func_243665_a(p_243667_0_, Lifecycle.stable(), p_243667_1_);
   }

   private static <T extends net.minecraftforge.registries.IForgeRegistryEntry<T>> Registry<T> forge(RegistryKey<? extends Registry<T>> key, Supplier<T> def) {
      return func_243666_a(key, net.minecraftforge.registries.GameData.getWrapper(key, Lifecycle.stable()), def, Lifecycle.stable());
   }

   private static <T> Registry<T> func_243665_a(RegistryKey<? extends Registry<T>> p_243665_0_, Lifecycle p_243665_1_, Supplier<T> p_243665_2_) {
      return func_243666_a(p_243665_0_, new SimpleRegistry<>(p_243665_0_, p_243665_1_), p_243665_2_, p_243665_1_);
   }

   private static <T, R extends MutableRegistry<T>> R func_243666_a(RegistryKey<? extends Registry<T>> p_243666_0_, R p_243666_1_, Supplier<T> p_243666_2_, Lifecycle p_243666_3_) {
      ResourceLocation resourcelocation = p_243666_0_.func_240901_a_();
      field_243659_k.put(resourcelocation, p_243666_2_);
      MutableRegistry<R> mutableregistry = (MutableRegistry<R>)field_243660_l;
      return (R)mutableregistry.register((RegistryKey)p_243666_0_, p_243666_1_, p_243666_3_);
   }

   public static <T> T func_243663_a(Registry<? super T> p_243663_0_, String p_243663_1_, T p_243663_2_) {
      return func_243664_a(p_243663_0_, new ResourceLocation(p_243663_1_), p_243663_2_);
   }

   public static <V, T extends V> T func_243664_a(Registry<V> p_243664_0_, ResourceLocation p_243664_1_, T p_243664_2_) {
      return ((MutableRegistry<V>)p_243664_0_).register(RegistryKey.func_240903_a_(p_243664_0_.func_243578_f(), p_243664_1_), p_243664_2_, Lifecycle.stable());
   }

   public static <V, T extends V> T func_243662_a(Registry<V> p_243662_0_, int p_243662_1_, RegistryKey<V> p_243662_2_, T p_243662_3_) {
      return ((MutableRegistry<V>)p_243662_0_).register(p_243662_1_, p_243662_2_, p_243662_3_, Lifecycle.stable());
   }

   public static void func_243661_a() {
   }

   static {
      field_243659_k.forEach((p_243668_0_, p_243668_1_) -> {
         if (p_243668_1_.get() == null) {
            field_243649_a.error("Unable to bootstrap registry '{}'", (Object)p_243668_0_);
         }

      });
      Registry.func_239738_a_(field_243660_l);
   }
}