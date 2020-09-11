package net.minecraft.tags;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class TagRegistryManager {
   private static final Map<ResourceLocation, TagRegistry<?>> field_242190_a = Maps.newHashMap();

   public static <T> TagRegistry<T> func_242196_a(ResourceLocation p_242196_0_, Function<ITagCollectionSupplier, ITagCollection<T>> p_242196_1_) {
      TagRegistry<T> tagregistry = new TagRegistry<>(p_242196_1_);
      TagRegistry<?> tagregistry1 = field_242190_a.putIfAbsent(p_242196_0_, tagregistry);
      if (tagregistry1 != null) {
         throw new IllegalStateException("Duplicate entry for static tag collection: " + p_242196_0_);
      } else {
         return tagregistry;
      }
   }

   public static void func_242193_a(ITagCollectionSupplier p_242193_0_) {
      field_242190_a.values().forEach((p_242194_1_) -> {
         p_242194_1_.func_242188_a(p_242193_0_);
      });
   }

   @OnlyIn(Dist.CLIENT)
   public static void func_242191_a() {
      field_242190_a.values().forEach(TagRegistry::func_232932_a_);
   }

   public static Multimap<ResourceLocation, ResourceLocation> func_242198_b(ITagCollectionSupplier p_242198_0_) {
      Multimap<ResourceLocation, ResourceLocation> multimap = HashMultimap.create();
      field_242190_a.forEach((p_242195_2_, p_242195_3_) -> {
         multimap.putAll(p_242195_2_, p_242195_3_.func_242189_b(p_242198_0_));
      });
      return multimap;
   }

   public static void func_242197_b() {
      TagRegistry[] atagregistry = new TagRegistry[]{BlockTags.collection, ItemTags.collection, FluidTags.collection, EntityTypeTags.tagCollection};
      boolean flag = Stream.of(atagregistry).anyMatch((p_242192_0_) -> {
         return !field_242190_a.containsValue(p_242192_0_);
      });
      if (flag) {
         throw new IllegalStateException("Missing helper registrations");
      }
   }

   @javax.annotation.Nullable
   public static TagRegistry<?> get(ResourceLocation rl) {
      return field_242190_a.get(rl);
   }

   public static Multimap<ResourceLocation, ResourceLocation> validateVanillaTags(ITagCollectionSupplier tagCollectionSupplier) {
      Multimap<ResourceLocation, ResourceLocation> missingTags = HashMultimap.create();
      for (java.util.Map.Entry<ResourceLocation, TagRegistry<?>> entry : field_242190_a.entrySet()) {
         if (!net.minecraftforge.common.ForgeTagHandler.getCustomTagTypeNames().contains(entry.getKey())) {
            missingTags.putAll(entry.getKey(), entry.getValue().func_242189_b(tagCollectionSupplier));
         }
      }
      return missingTags;
   }

   public static void fetchCustomTagTypes(ITagCollectionSupplier tagCollectionSupplier) {
      net.minecraftforge.common.ForgeTagHandler.getCustomTagTypeNames().forEach(tagRegistry -> field_242190_a.get(tagRegistry).func_242188_a(tagCollectionSupplier));
   }
}