package net.minecraft.state;

import com.google.common.collect.ArrayTable;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.collect.Table;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.annotation.Nullable;

public abstract class StateHolder<O, S> {
   private static final Function<Entry<Property<?>, Comparable<?>>, String> field_235890_a_ = new Function<Entry<Property<?>, Comparable<?>>, String>() {
      public String apply(@Nullable Entry<Property<?>, Comparable<?>> p_apply_1_) {
         if (p_apply_1_ == null) {
            return "<NULL>";
         } else {
            Property<?> property = p_apply_1_.getKey();
            return property.getName() + "=" + this.func_235905_a_(property, p_apply_1_.getValue());
         }
      }

      private <T extends Comparable<T>> String func_235905_a_(Property<T> p_235905_1_, Comparable<?> p_235905_2_) {
         return p_235905_1_.getName((T)p_235905_2_);
      }
   };
   protected final O field_235892_c_;
   private final ImmutableMap<Property<?>, Comparable<?>> field_235891_b_;
   private Table<Property<?>, Comparable<?>, S> field_235894_e_;
   protected final MapCodec<S> field_235893_d_;

   protected StateHolder(O p_i231879_1_, ImmutableMap<Property<?>, Comparable<?>> p_i231879_2_, MapCodec<S> p_i231879_3_) {
      this.field_235892_c_ = p_i231879_1_;
      this.field_235891_b_ = p_i231879_2_;
      this.field_235893_d_ = p_i231879_3_;
   }

   public <T extends Comparable<T>> S func_235896_a_(Property<T> p_235896_1_) {
      return this.with(p_235896_1_, func_235898_a_(p_235896_1_.getAllowedValues(), this.get(p_235896_1_)));
   }

   protected static <T> T func_235898_a_(Collection<T> p_235898_0_, T p_235898_1_) {
      Iterator<T> iterator = p_235898_0_.iterator();

      while(iterator.hasNext()) {
         if (iterator.next().equals(p_235898_1_)) {
            if (iterator.hasNext()) {
               return iterator.next();
            }

            return p_235898_0_.iterator().next();
         }
      }

      return iterator.next();
   }

   public String toString() {
      StringBuilder stringbuilder = new StringBuilder();
      stringbuilder.append(this.field_235892_c_);
      if (!this.getValues().isEmpty()) {
         stringbuilder.append('[');
         stringbuilder.append(this.getValues().entrySet().stream().map(field_235890_a_).collect(Collectors.joining(",")));
         stringbuilder.append(']');
      }

      return stringbuilder.toString();
   }

   public Collection<Property<?>> func_235904_r_() {
      return Collections.unmodifiableCollection(this.field_235891_b_.keySet());
   }

   public <T extends Comparable<T>> boolean func_235901_b_(Property<T> p_235901_1_) {
      return this.field_235891_b_.containsKey(p_235901_1_);
   }

   /**
    * Get the value of the given Property for this BlockState
    */
   public <T extends Comparable<T>> T get(Property<T> property) {
      Comparable<?> comparable = this.field_235891_b_.get(property);
      if (comparable == null) {
         throw new IllegalArgumentException("Cannot get property " + property + " as it does not exist in " + this.field_235892_c_);
      } else {
         return property.getValueClass().cast(comparable);
      }
   }

   public <T extends Comparable<T>> Optional<T> func_235903_d_(Property<T> p_235903_1_) {
      Comparable<?> comparable = this.field_235891_b_.get(p_235903_1_);
      return comparable == null ? Optional.empty() : Optional.of(p_235903_1_.getValueClass().cast(comparable));
   }

   public <T extends Comparable<T>, V extends T> S with(Property<T> property, V value) {
      Comparable<?> comparable = this.field_235891_b_.get(property);
      if (comparable == null) {
         throw new IllegalArgumentException("Cannot set property " + property + " as it does not exist in " + this.field_235892_c_);
      } else if (comparable == value) {
         return (S)this;
      } else {
         S s = this.field_235894_e_.get(property, value);
         if (s == null) {
            throw new IllegalArgumentException("Cannot set property " + property + " to " + value + " on " + this.field_235892_c_ + ", it is not an allowed value");
         } else {
            return s;
         }
      }
   }

   public void func_235899_a_(Map<Map<Property<?>, Comparable<?>>, S> p_235899_1_) {
      if (this.field_235894_e_ != null) {
         throw new IllegalStateException();
      } else {
         Table<Property<?>, Comparable<?>, S> table = HashBasedTable.create();

         for(Entry<Property<?>, Comparable<?>> entry : this.field_235891_b_.entrySet()) {
            Property<?> property = entry.getKey();

            for(Comparable<?> comparable : property.getAllowedValues()) {
               if (comparable != entry.getValue()) {
                  table.put(property, comparable, p_235899_1_.get(this.func_235902_b_(property, comparable)));
               }
            }
         }

         this.field_235894_e_ = (Table<Property<?>, Comparable<?>, S>)(table.isEmpty() ? table : ArrayTable.create(table));
      }
   }

   private Map<Property<?>, Comparable<?>> func_235902_b_(Property<?> p_235902_1_, Comparable<?> p_235902_2_) {
      Map<Property<?>, Comparable<?>> map = Maps.newHashMap(this.field_235891_b_);
      map.put(p_235902_1_, p_235902_2_);
      return map;
   }

   public ImmutableMap<Property<?>, Comparable<?>> getValues() {
      return this.field_235891_b_;
   }

   protected static <O, S extends StateHolder<O, S>> Codec<S> func_235897_a_(Codec<O> p_235897_0_, Function<O, S> p_235897_1_) {
      return p_235897_0_.dispatch("Name", (p_235895_0_) -> {
         return p_235895_0_.field_235892_c_;
      }, (p_235900_1_) -> {
         S s = p_235897_1_.apply(p_235900_1_);
         return s.getValues().isEmpty() ? Codec.unit(s) : s.field_235893_d_.fieldOf("Properties").codec();
      });
   }
}