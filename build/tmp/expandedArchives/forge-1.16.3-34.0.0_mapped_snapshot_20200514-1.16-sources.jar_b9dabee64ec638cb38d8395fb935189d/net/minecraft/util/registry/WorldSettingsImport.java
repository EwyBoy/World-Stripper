package net.minecraft.util.registry;

import com.google.common.base.Suppliers;
import com.google.common.collect.Maps;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Decoder;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.Encoder;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.Lifecycle;
import com.mojang.serialization.DataResult.PartialResult;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenCustomHashMap;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import net.minecraft.resources.IResource;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.datafix.DelegatingDynamicOps;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class WorldSettingsImport<T> extends DelegatingDynamicOps<T> {
   private static final Logger field_240870_b_ = LogManager.getLogger();
   private final WorldSettingsImport.IResourceAccess field_244332_c;
   private final DynamicRegistries.Impl field_240872_d_;
   private final Map<RegistryKey<? extends Registry<?>>, WorldSettingsImport.ResultMap<?>> field_240873_e_;
   private final WorldSettingsImport<JsonElement> field_244333_f;

   public static <T> WorldSettingsImport<T> func_244335_a(DynamicOps<T> p_244335_0_, IResourceManager p_244335_1_, DynamicRegistries.Impl p_244335_2_) {
      return func_244336_a(p_244335_0_, WorldSettingsImport.IResourceAccess.func_244345_a(p_244335_1_), p_244335_2_);
   }

   public static <T> WorldSettingsImport<T> func_244336_a(DynamicOps<T> p_244336_0_, WorldSettingsImport.IResourceAccess p_244336_1_, DynamicRegistries.Impl p_244336_2_) {
      WorldSettingsImport<T> worldsettingsimport = new WorldSettingsImport<>(p_244336_0_, p_244336_1_, p_244336_2_, Maps.newIdentityHashMap());
      DynamicRegistries.func_243608_a(p_244336_2_, worldsettingsimport);
      return worldsettingsimport;
   }

   private WorldSettingsImport(DynamicOps<T> p_i242092_1_, WorldSettingsImport.IResourceAccess p_i242092_2_, DynamicRegistries.Impl p_i242092_3_, IdentityHashMap<RegistryKey<? extends Registry<?>>, WorldSettingsImport.ResultMap<?>> p_i242092_4_) {
      super(p_i242092_1_);
      this.field_244332_c = p_i242092_2_;
      this.field_240872_d_ = p_i242092_3_;
      this.field_240873_e_ = p_i242092_4_;
      this.field_244333_f = p_i242092_1_ == JsonOps.INSTANCE ? (WorldSettingsImport<JsonElement>)this : new WorldSettingsImport<>(JsonOps.INSTANCE, p_i242092_2_, p_i242092_3_, p_i242092_4_);
   }

   protected <E> DataResult<Pair<Supplier<E>, T>> func_241802_a_(T p_241802_1_, RegistryKey<? extends Registry<E>> p_241802_2_, Codec<E> p_241802_3_, boolean p_241802_4_) {
      Optional<MutableRegistry<E>> optional = this.field_240872_d_.func_230521_a_(p_241802_2_);
      if (!optional.isPresent()) {
         return DataResult.error("Unknown registry: " + p_241802_2_);
      } else {
         MutableRegistry<E> mutableregistry = optional.get();
         DataResult<Pair<ResourceLocation, T>> dataresult = ResourceLocation.field_240908_a_.decode(this.field_240857_a_, p_241802_1_);
         if (!dataresult.result().isPresent()) {
            return !p_241802_4_ ? DataResult.error("Inline definitions not allowed here") : p_241802_3_.decode(this, p_241802_1_).map((p_240874_0_) -> {
               return p_240874_0_.mapFirst((p_240891_0_) -> {
                  return () -> {
                     return p_240891_0_;
                  };
               });
            });
         } else {
            Pair<ResourceLocation, T> pair = dataresult.result().get();
            ResourceLocation resourcelocation = pair.getFirst();
            return this.func_241805_a_(p_241802_2_, mutableregistry, p_241802_3_, resourcelocation).map((p_240875_1_) -> {
               return Pair.of(p_240875_1_, pair.getSecond());
            });
         }
      }
   }

   public <E> DataResult<SimpleRegistry<E>> func_241797_a_(SimpleRegistry<E> p_241797_1_, RegistryKey<? extends Registry<E>> p_241797_2_, Codec<E> p_241797_3_) {
      Collection<ResourceLocation> collection = this.field_244332_c.func_241880_a(p_241797_2_);
      DataResult<SimpleRegistry<E>> dataresult = DataResult.success(p_241797_1_, Lifecycle.stable());
      String s = p_241797_2_.func_240901_a_().getPath() + "/";

      for(ResourceLocation resourcelocation : collection) {
         String s1 = resourcelocation.getPath();
         if (!s1.endsWith(".json")) {
            field_240870_b_.warn("Skipping resource {} since it is not a json file", (Object)resourcelocation);
         } else if (!s1.startsWith(s)) {
            field_240870_b_.warn("Skipping resource {} since it does not have a registry name prefix", (Object)resourcelocation);
         } else {
            String s2 = s1.substring(s.length(), s1.length() - ".json".length());
            ResourceLocation resourcelocation1 = new ResourceLocation(resourcelocation.getNamespace(), s2);
            dataresult = dataresult.flatMap((p_240885_4_) -> {
               return this.func_241805_a_(p_241797_2_, p_240885_4_, p_241797_3_, resourcelocation1).map((p_240877_1_) -> {
                  return p_240885_4_;
               });
            });
         }
      }

      return dataresult.setPartial(p_241797_1_);
   }

   private <E> DataResult<Supplier<E>> func_241805_a_(RegistryKey<? extends Registry<E>> p_241805_1_, MutableRegistry<E> p_241805_2_, Codec<E> p_241805_3_, ResourceLocation p_241805_4_) {
      RegistryKey<E> registrykey = RegistryKey.func_240903_a_(p_241805_1_, p_241805_4_);
      WorldSettingsImport.ResultMap<E> resultmap = this.func_240884_a_(p_241805_1_);
      DataResult<Supplier<E>> dataresult = resultmap.field_240893_a_.get(registrykey);
      if (dataresult != null) {
         return dataresult;
      } else {
         Supplier<E> supplier = Suppliers.memoize(() -> {
            E e = p_241805_2_.func_230516_a_(registrykey);
            if (e == null) {
               throw new RuntimeException("Error during recursive registry parsing, element resolved too early: " + registrykey);
            } else {
               return e;
            }
         });
         resultmap.field_240893_a_.put(registrykey, DataResult.success(supplier));
         DataResult<Pair<E, OptionalInt>> dataresult1 = this.field_244332_c.func_241879_a(this.field_244333_f, p_241805_1_, registrykey, p_241805_3_);
         Optional<Pair<E, OptionalInt>> optional = dataresult1.result();
         if (optional.isPresent()) {
            Pair<E, OptionalInt> pair = optional.get();
            p_241805_2_.func_241874_a(pair.getSecond(), registrykey, pair.getFirst(), dataresult1.lifecycle());
         }

         DataResult<Supplier<E>> dataresult2;
         if (!optional.isPresent() && p_241805_2_.func_230516_a_(registrykey) != null) {
            dataresult2 = DataResult.success(() -> {
               return p_241805_2_.func_230516_a_(registrykey);
            }, Lifecycle.stable());
         } else {
            dataresult2 = dataresult1.map((p_244339_2_) -> {
               return () -> {
                  return p_241805_2_.func_230516_a_(registrykey);
               };
            });
         }

         resultmap.field_240893_a_.put(registrykey, dataresult2);
         return dataresult2;
      }
   }

   private <E> WorldSettingsImport.ResultMap<E> func_240884_a_(RegistryKey<? extends Registry<E>> p_240884_1_) {
      return (WorldSettingsImport.ResultMap<E>)this.field_240873_e_.computeIfAbsent(p_240884_1_, (p_244344_0_) -> {
         return new WorldSettingsImport.ResultMap();
      });
   }

   protected <E> DataResult<Registry<E>> func_244340_a(RegistryKey<? extends Registry<E>> p_244340_1_) {
      return (DataResult)this.field_240872_d_.func_230521_a_(p_244340_1_).map((p_244337_0_) -> {
         return DataResult.success(p_244337_0_, p_244337_0_.func_241875_b());
      }).orElseGet(() -> {
         return DataResult.error("Unknown registry: " + p_244340_1_);
      });
   }

   public interface IResourceAccess {
      Collection<ResourceLocation> func_241880_a(RegistryKey<? extends Registry<?>> p_241880_1_);

      <E> DataResult<Pair<E, OptionalInt>> func_241879_a(DynamicOps<JsonElement> p_241879_1_, RegistryKey<? extends Registry<E>> p_241879_2_, RegistryKey<E> p_241879_3_, Decoder<E> p_241879_4_);

      static WorldSettingsImport.IResourceAccess func_244345_a(final IResourceManager p_244345_0_) {
         return new WorldSettingsImport.IResourceAccess() {
            public Collection<ResourceLocation> func_241880_a(RegistryKey<? extends Registry<?>> p_241880_1_) {
               return p_244345_0_.getAllResourceLocations(p_241880_1_.func_240901_a_().getPath(), (p_244348_0_) -> {
                  return p_244348_0_.endsWith(".json");
               });
            }

            public <E> DataResult<Pair<E, OptionalInt>> func_241879_a(DynamicOps<JsonElement> p_241879_1_, RegistryKey<? extends Registry<E>> p_241879_2_, RegistryKey<E> p_241879_3_, Decoder<E> p_241879_4_) {
               ResourceLocation resourcelocation = p_241879_3_.func_240901_a_();
               ResourceLocation resourcelocation1 = new ResourceLocation(resourcelocation.getNamespace(), p_241879_2_.func_240901_a_().getPath() + "/" + resourcelocation.getPath() + ".json");

               try (
                  IResource iresource = p_244345_0_.getResource(resourcelocation1);
                  Reader reader = new InputStreamReader(iresource.getInputStream(), StandardCharsets.UTF_8);
               ) {
                  JsonParser jsonparser = new JsonParser();
                  JsonElement jsonelement = jsonparser.parse(reader);
                  return p_241879_4_.parse(p_241879_1_, jsonelement).map((p_244347_0_) -> {
                     return Pair.of(p_244347_0_, OptionalInt.empty());
                  });
               } catch (JsonIOException | JsonSyntaxException | IOException ioexception) {
                  return DataResult.error("Failed to parse " + resourcelocation1 + " file: " + ioexception.getMessage());
               }
            }

            public String toString() {
               return "ResourceAccess[" + p_244345_0_ + "]";
            }
         };
      }

      public static final class RegistryAccess implements WorldSettingsImport.IResourceAccess {
         private final Map<RegistryKey<?>, JsonElement> field_244349_a = Maps.newIdentityHashMap();
         private final Object2IntMap<RegistryKey<?>> field_244350_b = new Object2IntOpenCustomHashMap<>(Util.identityHashStrategy());
         private final Map<RegistryKey<?>, Lifecycle> field_244351_c = Maps.newIdentityHashMap();

         public <E> void func_244352_a(DynamicRegistries.Impl p_244352_1_, RegistryKey<E> p_244352_2_, Encoder<E> p_244352_3_, int p_244352_4_, E p_244352_5_, Lifecycle p_244352_6_) {
            DataResult<JsonElement> dataresult = p_244352_3_.encodeStart(WorldGenSettingsExport.func_240896_a_(JsonOps.INSTANCE, p_244352_1_), p_244352_5_);
            Optional<PartialResult<JsonElement>> optional = dataresult.error();
            if (optional.isPresent()) {
               WorldSettingsImport.field_240870_b_.error("Error adding element: {}", (Object)optional.get().message());
            } else {
               this.field_244349_a.put(p_244352_2_, dataresult.result().get());
               this.field_244350_b.put(p_244352_2_, p_244352_4_);
               this.field_244351_c.put(p_244352_2_, p_244352_6_);
            }
         }

         public Collection<ResourceLocation> func_241880_a(RegistryKey<? extends Registry<?>> p_241880_1_) {
            return this.field_244349_a.keySet().stream().filter((p_244355_1_) -> {
               return p_244355_1_.func_244356_a(p_241880_1_);
            }).map((p_244354_1_) -> {
               return new ResourceLocation(p_244354_1_.func_240901_a_().getNamespace(), p_241880_1_.func_240901_a_().getPath() + "/" + p_244354_1_.func_240901_a_().getPath() + ".json");
            }).collect(Collectors.toList());
         }

         public <E> DataResult<Pair<E, OptionalInt>> func_241879_a(DynamicOps<JsonElement> p_241879_1_, RegistryKey<? extends Registry<E>> p_241879_2_, RegistryKey<E> p_241879_3_, Decoder<E> p_241879_4_) {
            JsonElement jsonelement = this.field_244349_a.get(p_241879_3_);
            return jsonelement == null ? DataResult.error("Unknown element: " + p_241879_3_) : p_241879_4_.parse(p_241879_1_, jsonelement).setLifecycle(this.field_244351_c.get(p_241879_3_)).map((p_244353_2_) -> {
               return Pair.of(p_244353_2_, OptionalInt.of(this.field_244350_b.getInt(p_241879_3_)));
            });
         }
      }
   }

   static final class ResultMap<E> {
      private final Map<RegistryKey<E>, DataResult<Supplier<E>>> field_240893_a_ = Maps.newIdentityHashMap();

      private ResultMap() {
      }
   }
}