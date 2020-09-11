package net.minecraft.util.registry;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.Lifecycle;
import com.mojang.serialization.codecs.UnboundedMapCodec;
import java.util.Map;
import java.util.Optional;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.world.DimensionType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.DimensionSettings;
import net.minecraft.world.gen.carver.ConfiguredCarver;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraft.world.gen.feature.jigsaw.JigsawPattern;
import net.minecraft.world.gen.feature.template.IStructureProcessorType;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class DynamicRegistries {
   private static final Logger field_243598_a = LogManager.getLogger();
   private static final Map<RegistryKey<? extends Registry<?>>, DynamicRegistries.CodecHolder<?>> field_243599_b = Util.make(() -> {
      Builder<RegistryKey<? extends Registry<?>>, DynamicRegistries.CodecHolder<?>> builder = ImmutableMap.builder();
      func_243602_a(builder, Registry.field_239698_ad_, DimensionType.field_235997_a_, DimensionType.field_235997_a_);
      func_243602_a(builder, Registry.field_239720_u_, Biome.field_242418_b, Biome.field_242419_c);
      func_243601_a(builder, Registry.field_243550_as, ConfiguredSurfaceBuilder.field_237168_a_);
      func_243601_a(builder, Registry.field_243551_at, ConfiguredCarver.field_236235_a_);
      func_243601_a(builder, Registry.field_243552_au, ConfiguredFeature.field_242763_a);
      func_243601_a(builder, Registry.field_243553_av, StructureFeature.field_236267_a_);
      func_243601_a(builder, Registry.field_243554_aw, IStructureProcessorType.field_242921_l);
      func_243601_a(builder, Registry.field_243555_ax, JigsawPattern.field_236852_a_);
      func_243601_a(builder, Registry.field_243549_ar, DimensionSettings.field_236097_a_);
      return builder.build();
   });
   private static final DynamicRegistries.Impl field_243600_c = Util.make(() -> {
      DynamicRegistries.Impl dynamicregistries$impl = new DynamicRegistries.Impl();
      DimensionType.func_236027_a_(dynamicregistries$impl);
      field_243599_b.keySet().stream().filter((p_243616_0_) -> {
         return !p_243616_0_.equals(Registry.field_239698_ad_);
      }).forEach((p_243611_1_) -> {
         func_243609_a(dynamicregistries$impl, p_243611_1_);
      });
      return dynamicregistries$impl;
   });

   public abstract <E> Optional<MutableRegistry<E>> func_230521_a_(RegistryKey<? extends Registry<E>> p_230521_1_);

   public <E> MutableRegistry<E> func_243612_b(RegistryKey<? extends Registry<E>> p_243612_1_) {
      return this.func_230521_a_(p_243612_1_).orElseThrow(() -> {
         return new IllegalStateException("Missing registry: " + p_243612_1_);
      });
   }

   public Registry<DimensionType> func_230520_a_() {
      return this.func_243612_b(Registry.field_239698_ad_);
   }

   private static <E> void func_243601_a(Builder<RegistryKey<? extends Registry<?>>, DynamicRegistries.CodecHolder<?>> p_243601_0_, RegistryKey<? extends Registry<E>> p_243601_1_, Codec<E> p_243601_2_) {
      p_243601_0_.put(p_243601_1_, new DynamicRegistries.CodecHolder<>(p_243601_1_, p_243601_2_, (Codec<E>)null));
   }

   private static <E> void func_243602_a(Builder<RegistryKey<? extends Registry<?>>, DynamicRegistries.CodecHolder<?>> p_243602_0_, RegistryKey<? extends Registry<E>> p_243602_1_, Codec<E> p_243602_2_, Codec<E> p_243602_3_) {
      p_243602_0_.put(p_243602_1_, new DynamicRegistries.CodecHolder<>(p_243602_1_, p_243602_2_, p_243602_3_));
   }

   public static DynamicRegistries.Impl func_239770_b_() {
      DynamicRegistries.Impl dynamicregistries$impl = new DynamicRegistries.Impl();
      WorldSettingsImport.IResourceAccess.RegistryAccess worldsettingsimport$iresourceaccess$registryaccess = new WorldSettingsImport.IResourceAccess.RegistryAccess();

      for(DynamicRegistries.CodecHolder<?> codecholder : field_243599_b.values()) {
         func_243607_a(dynamicregistries$impl, worldsettingsimport$iresourceaccess$registryaccess, codecholder);
      }

      WorldSettingsImport.func_244336_a(JsonOps.INSTANCE, worldsettingsimport$iresourceaccess$registryaccess, dynamicregistries$impl);
      return dynamicregistries$impl;
   }

   private static <E> void func_243607_a(DynamicRegistries.Impl p_243607_0_, WorldSettingsImport.IResourceAccess.RegistryAccess p_243607_1_, DynamicRegistries.CodecHolder<E> p_243607_2_) {
      RegistryKey<? extends Registry<E>> registrykey = p_243607_2_.func_243622_a();
      boolean flag = !registrykey.equals(Registry.field_243549_ar) && !registrykey.equals(Registry.field_239698_ad_);
      Registry<E> registry = field_243600_c.func_243612_b(registrykey);
      MutableRegistry<E> mutableregistry = p_243607_0_.func_243612_b(registrykey);

      for(Entry<RegistryKey<E>, E> entry : registry.func_239659_c_()) {
         E e = entry.getValue();
         if (flag) {
            p_243607_1_.func_244352_a(field_243600_c, entry.getKey(), p_243607_2_.func_243623_b(), registry.getId(e), e, registry.func_241876_d(e));
         } else {
            mutableregistry.register(registry.getId(e), entry.getKey(), e, registry.func_241876_d(e));
         }
      }

   }

   private static <R extends Registry<?>> void func_243609_a(DynamicRegistries.Impl p_243609_0_, RegistryKey<R> p_243609_1_) {
      Registry<R> registry = (Registry<R>)WorldGenRegistries.field_243650_b;
      Registry<?> registry1 = registry.func_230516_a_(p_243609_1_);
      if (registry1 == null) {
         throw new IllegalStateException("Missing builtin registry: " + p_243609_1_);
      } else {
         func_243606_a(p_243609_0_, registry1);
      }
   }

   private static <E> void func_243606_a(DynamicRegistries.Impl p_243606_0_, Registry<E> p_243606_1_) {
      MutableRegistry<E> mutableregistry = p_243606_0_.<E>func_230521_a_(p_243606_1_.func_243578_f()).orElseThrow(() -> {
         return new IllegalStateException("Missing registry: " + p_243606_1_.func_243578_f());
      });

      for(Entry<RegistryKey<E>, E> entry : p_243606_1_.func_239659_c_()) {
         E e = entry.getValue();
         mutableregistry.register(p_243606_1_.getId(e), entry.getKey(), e, p_243606_1_.func_241876_d(e));
      }

   }

   public static void func_243608_a(DynamicRegistries.Impl p_243608_0_, WorldSettingsImport<?> p_243608_1_) {
      for(DynamicRegistries.CodecHolder<?> codecholder : field_243599_b.values()) {
         func_243610_a(p_243608_1_, p_243608_0_, codecholder);
      }

   }

   private static <E> void func_243610_a(WorldSettingsImport<?> p_243610_0_, DynamicRegistries.Impl p_243610_1_, DynamicRegistries.CodecHolder<E> p_243610_2_) {
      RegistryKey<? extends Registry<E>> registrykey = p_243610_2_.func_243622_a();
      SimpleRegistry<E> simpleregistry = Optional.ofNullable((SimpleRegistry<E>)p_243610_1_.field_243627_b.get(registrykey)).map((p_243604_0_) -> {
         return p_243604_0_;
      }).orElseThrow(() -> {
         return new IllegalStateException("Missing registry: " + registrykey);
      });
      DataResult<SimpleRegistry<E>> dataresult = p_243610_0_.func_241797_a_(simpleregistry, p_243610_2_.func_243622_a(), p_243610_2_.func_243623_b());
      dataresult.error().ifPresent((p_243603_0_) -> {
         field_243598_a.error("Error loading registry data: {}", (Object)p_243603_0_.message());
      });
   }

   static final class CodecHolder<E> {
      private final RegistryKey<? extends Registry<E>> field_243619_a;
      private final Codec<E> field_243620_b;
      @Nullable
      private final Codec<E> field_243621_c;

      public CodecHolder(RegistryKey<? extends Registry<E>> p_i242073_1_, Codec<E> p_i242073_2_, @Nullable Codec<E> p_i242073_3_) {
         this.field_243619_a = p_i242073_1_;
         this.field_243620_b = p_i242073_2_;
         this.field_243621_c = p_i242073_3_;
      }

      public RegistryKey<? extends Registry<E>> func_243622_a() {
         return this.field_243619_a;
      }

      public Codec<E> func_243623_b() {
         return this.field_243620_b;
      }

      @Nullable
      public Codec<E> func_243624_c() {
         return this.field_243621_c;
      }

      public boolean func_243625_d() {
         return this.field_243621_c != null;
      }
   }

   public static final class Impl extends DynamicRegistries {
      public static final Codec<DynamicRegistries.Impl> field_243626_a = func_243637_d();
      private final Map<? extends RegistryKey<? extends Registry<?>>, ? extends SimpleRegistry<?>> field_243627_b;

      private static <E> Codec<DynamicRegistries.Impl> func_243637_d() {
         Codec<RegistryKey<? extends Registry<E>>> codec = ResourceLocation.field_240908_a_.xmap(RegistryKey::func_240904_a_, RegistryKey::func_240901_a_);
         Codec<SimpleRegistry<E>> codec1 = codec.partialDispatch("type", (p_243634_0_) -> {
            return DataResult.success(p_243634_0_.func_243578_f());
         }, (p_243640_0_) -> {
            return func_243636_c(p_243640_0_).map((p_243633_1_) -> {
               return SimpleRegistry.func_243539_a(p_243640_0_, Lifecycle.experimental(), p_243633_1_);
            });
         });
         UnboundedMapCodec<? extends RegistryKey<? extends Registry<?>>, ? extends SimpleRegistry<?>> unboundedmapcodec = Codec.unboundedMap(codec, codec1);
         return func_243628_a(unboundedmapcodec);
      }

      private static <K extends RegistryKey<? extends Registry<?>>, V extends SimpleRegistry<?>> Codec<DynamicRegistries.Impl> func_243628_a(UnboundedMapCodec<K, V> p_243628_0_) {
         return p_243628_0_.xmap(DynamicRegistries.Impl::new, (p_243635_0_) -> {
            return ((java.util.Set<Map.Entry<K, V>>)(Object)(p_243635_0_.field_243627_b.entrySet())).stream().filter((p_243632_0_) -> {
               return DynamicRegistries.field_243599_b.get(p_243632_0_.getKey()).func_243625_d();
            }).collect(ImmutableMap.toImmutableMap(Entry::getKey, Entry::getValue));
         });
      }

      private static <E> DataResult<? extends Codec<E>> func_243636_c(RegistryKey<? extends Registry<E>> p_243636_0_) {
         return Optional.ofNullable((CodecHolder<E>)DynamicRegistries.field_243599_b.get(p_243636_0_)).map((p_243630_0_) -> {
            return p_243630_0_.func_243624_c();
         }).map(DataResult::success).orElseGet(() -> {
            return DataResult.error("Unknown or not serializable registry: " + p_243636_0_);
         });
      }

      public Impl() {
         this(DynamicRegistries.field_243599_b.keySet().stream().collect(Collectors.toMap(Function.identity(), DynamicRegistries.Impl::func_243638_d)));
      }

      private Impl(Map<? extends RegistryKey<? extends Registry<?>>, ? extends SimpleRegistry<?>> p_i242074_1_) {
         this.field_243627_b = p_i242074_1_;
      }

      private static <E> SimpleRegistry<?> func_243638_d(RegistryKey<? extends Registry<?>> p_243638_0_) {
         return new SimpleRegistry(p_243638_0_, Lifecycle.stable());
      }

      public <E> Optional<MutableRegistry<E>> func_230521_a_(RegistryKey<? extends Registry<E>> p_230521_1_) {
         return Optional.ofNullable((MutableRegistry<E>)this.field_243627_b.get(p_230521_1_)).map((p_243629_0_) -> {
            return p_243629_0_;
         });
      }
   }
}