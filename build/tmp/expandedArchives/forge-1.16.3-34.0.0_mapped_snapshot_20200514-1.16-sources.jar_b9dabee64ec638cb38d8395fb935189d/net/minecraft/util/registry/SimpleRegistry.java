package net.minecraft.util.registry;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Iterators;
import com.google.common.collect.Maps;
import com.google.common.collect.ImmutableList.Builder;
import com.mojang.serialization.Codec;
import com.mojang.serialization.Lifecycle;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenCustomHashMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.Random;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.commons.lang3.Validate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SimpleRegistry<T> extends MutableRegistry<T> {
   protected static final Logger LOGGER0 = LogManager.getLogger();
   private final ObjectList<T> field_243533_bf = new ObjectArrayList<>(256);
   private final Object2IntMap<T> field_243534_bg = new Object2IntOpenCustomHashMap<>(Util.identityHashStrategy());
   private final BiMap<ResourceLocation, T> registryObjects;
   private final BiMap<RegistryKey<T>, T> field_239649_bb_;
   private final Map<T, Lifecycle> field_243535_bj;
   private Lifecycle field_243536_bk;
   protected Object[] values;
   private int nextFreeId;

   public SimpleRegistry(RegistryKey<? extends Registry<T>> p_i232509_1_, Lifecycle p_i232509_2_) {
      super(p_i232509_1_, p_i232509_2_);
      this.field_243534_bg.defaultReturnValue(-1);
      this.registryObjects = HashBiMap.create();
      this.field_239649_bb_ = HashBiMap.create();
      this.field_243535_bj = Maps.newIdentityHashMap();
      this.field_243536_bk = p_i232509_2_;
   }

   public static <T> MapCodec<SimpleRegistry.Entry<T>> func_243541_a(RegistryKey<? extends Registry<T>> p_243541_0_, MapCodec<T> p_243541_1_) {
      return RecordCodecBuilder.mapCodec((p_243542_2_) -> {
         return p_243542_2_.group(ResourceLocation.field_240908_a_.xmap(RegistryKey.func_240902_a_(p_243541_0_), RegistryKey::func_240901_a_).fieldOf("name").forGetter((p_243545_0_) -> {
            return p_243545_0_.field_243546_a;
         }), Codec.INT.fieldOf("id").forGetter((p_243543_0_) -> {
            return p_243543_0_.field_243547_b;
         }), p_243541_1_.forGetter((p_243538_0_) -> {
            return p_243538_0_.field_243548_c;
         })).apply(p_243542_2_, SimpleRegistry.Entry::new);
      });
   }

   public <V extends T> V register(int id, RegistryKey<T> name, V instance, Lifecycle p_218382_4_) {
      return this.func_243537_a(id, name, instance, p_218382_4_, true);
   }

   private <V extends T> V func_243537_a(int p_243537_1_, RegistryKey<T> p_243537_2_, V p_243537_3_, Lifecycle p_243537_4_, boolean p_243537_5_) {
      Validate.notNull(p_243537_2_);
      Validate.notNull((T)p_243537_3_);
      this.field_243533_bf.size(Math.max(this.field_243533_bf.size(), p_243537_1_ + 1));
      this.field_243533_bf.set(p_243537_1_, p_243537_3_);
      this.field_243534_bg.put((T)p_243537_3_, p_243537_1_);
      this.values = null;
      if (p_243537_5_ && this.field_239649_bb_.containsKey(p_243537_2_)) {
         LOGGER0.debug("Adding duplicate key '{}' to registry", (Object)p_243537_2_);
      }

      if (this.registryObjects.containsValue(p_243537_3_)) {
         LOGGER0.error("Adding duplicate value '{}' to registry", p_243537_3_);
      }

      this.registryObjects.put(p_243537_2_.func_240901_a_(), (T)p_243537_3_);
      this.field_239649_bb_.put(p_243537_2_, (T)p_243537_3_);
      this.field_243535_bj.put((T)p_243537_3_, p_243537_4_);
      this.field_243536_bk = this.field_243536_bk.add(p_243537_4_);
      if (this.nextFreeId <= p_243537_1_) {
         this.nextFreeId = p_243537_1_ + 1;
      }

      return p_243537_3_;
   }

   public <V extends T> V register(RegistryKey<T> name, V instance, Lifecycle p_218381_3_) {
      return this.register(this.nextFreeId, name, instance, p_218381_3_);
   }

   public <V extends T> V func_241874_a(OptionalInt p_241874_1_, RegistryKey<T> p_241874_2_, V p_241874_3_, Lifecycle p_241874_4_) {
      Validate.notNull(p_241874_2_);
      Validate.notNull((T)p_241874_3_);
      T t = this.field_239649_bb_.get(p_241874_2_);
      int i;
      if (t == null) {
         i = p_241874_1_.isPresent() ? p_241874_1_.getAsInt() : this.nextFreeId;
      } else {
         i = this.field_243534_bg.getInt(t);
         if (p_241874_1_.isPresent() && p_241874_1_.getAsInt() != i) {
            throw new IllegalStateException("ID mismatch");
         }

         this.field_243534_bg.removeInt(t);
         this.field_243535_bj.remove(t);
      }

      return this.func_243537_a(i, p_241874_2_, p_241874_3_, p_241874_4_, false);
   }

   /**
    * Gets the name we use to identify the given object.
    */
   @Nullable
   public ResourceLocation getKey(T value) {
      return this.registryObjects.inverse().get(value);
   }

   public Optional<RegistryKey<T>> func_230519_c_(T p_230519_1_) {
      return Optional.ofNullable(this.field_239649_bb_.inverse().get(p_230519_1_));
   }

   /**
    * Gets the integer ID we use to identify the given object.
    */
   public int getId(@Nullable T value) {
      return this.field_243534_bg.getInt(value);
   }

   @Nullable
   public T func_230516_a_(@Nullable RegistryKey<T> p_230516_1_) {
      return this.field_239649_bb_.get(p_230516_1_);
   }

   @Nullable
   public T getByValue(int value) {
      return (T)(value >= 0 && value < this.field_243533_bf.size() ? this.field_243533_bf.get(value) : null);
   }

   public Lifecycle func_241876_d(T p_241876_1_) {
      return this.field_243535_bj.get(p_241876_1_);
   }

   public Lifecycle func_241875_b() {
      return this.field_243536_bk;
   }

   public Iterator<T> iterator() {
      return Iterators.filter(this.field_243533_bf.iterator(), Objects::nonNull);
   }

   @Nullable
   public T getOrDefault(@Nullable ResourceLocation name) {
      return this.registryObjects.get(name);
   }

   /**
    * Gets all the keys recognized by this registry.
    */
   public Set<ResourceLocation> keySet() {
      return Collections.unmodifiableSet(this.registryObjects.keySet());
   }

   public Set<Map.Entry<RegistryKey<T>, T>> func_239659_c_() {
      return Collections.unmodifiableMap(this.field_239649_bb_).entrySet();
   }

   @Nullable
   public T getRandom(Random random) {
      if (this.values == null) {
         Collection<?> collection = this.registryObjects.values();
         if (collection.isEmpty()) {
            return (T)null;
         }

         this.values = collection.toArray(new Object[collection.size()]);
      }

      return Util.func_240989_a_((T[])this.values, random);
   }

   @OnlyIn(Dist.CLIENT)
   public boolean containsKey(ResourceLocation name) {
      return this.registryObjects.containsKey(name);
   }

   public static <T> Codec<SimpleRegistry<T>> func_243539_a(RegistryKey<? extends Registry<T>> p_243539_0_, Lifecycle p_243539_1_, Codec<T> p_243539_2_) {
      return func_243541_a(p_243539_0_, p_243539_2_.fieldOf("element")).codec().listOf().xmap((p_243540_2_) -> {
         SimpleRegistry<T> simpleregistry = new SimpleRegistry<>(p_243539_0_, p_243539_1_);

         for(SimpleRegistry.Entry<T> entry : p_243540_2_) {
            simpleregistry.register(entry.field_243547_b, entry.field_243546_a, entry.field_243548_c, p_243539_1_);
         }

         return simpleregistry;
      }, (p_243544_0_) -> {
         Builder<SimpleRegistry.Entry<T>> builder = ImmutableList.builder();

         for(T t : p_243544_0_) {
            builder.add(new SimpleRegistry.Entry<>(p_243544_0_.func_230519_c_(t).get(), p_243544_0_.getId(t), t));
         }

         return builder.build();
      });
   }

   public static <T> Codec<SimpleRegistry<T>> func_241744_b_(RegistryKey<? extends Registry<T>> p_241744_0_, Lifecycle p_241744_1_, Codec<T> p_241744_2_) {
      return SimpleRegistryCodec.func_241793_a_(p_241744_0_, p_241744_1_, p_241744_2_);
   }

   public static <T> Codec<SimpleRegistry<T>> func_241745_c_(RegistryKey<? extends Registry<T>> p_241745_0_, Lifecycle p_241745_1_, Codec<T> p_241745_2_) {
      return Codec.unboundedMap(ResourceLocation.field_240908_a_.xmap(RegistryKey.func_240902_a_(p_241745_0_), RegistryKey::func_240901_a_), p_241745_2_).xmap((p_239656_2_) -> {
         SimpleRegistry<T> simpleregistry = new SimpleRegistry<>(p_241745_0_, p_241745_1_);
         p_239656_2_.forEach((p_239653_2_, p_239653_3_) -> {
            simpleregistry.register(p_239653_2_, p_239653_3_, p_241745_1_);
         });
         return simpleregistry;
      }, (p_239651_0_) -> {
         return ImmutableMap.copyOf(p_239651_0_.field_239649_bb_);
      });
   }

   public static class Entry<T> {
      public final RegistryKey<T> field_243546_a;
      public final int field_243547_b;
      public final T field_243548_c;

      public Entry(RegistryKey<T> p_i242072_1_, int p_i242072_2_, T p_i242072_3_) {
         this.field_243546_a = p_i242072_1_;
         this.field_243547_b = p_i242072_2_;
         this.field_243548_c = p_i242072_3_;
      }
   }
}