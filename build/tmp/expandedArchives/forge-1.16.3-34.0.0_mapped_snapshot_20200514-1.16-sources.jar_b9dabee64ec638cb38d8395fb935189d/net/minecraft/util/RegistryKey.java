package net.minecraft.util;

import com.google.common.collect.Maps;
import java.util.Collections;
import java.util.Map;
import java.util.function.Function;
import net.minecraft.util.registry.Registry;

public class RegistryKey<T> implements Comparable<RegistryKey<?>> {
   private static final Map<String, RegistryKey<?>> field_240898_a_ = Collections.synchronizedMap(Maps.newIdentityHashMap());
   private final ResourceLocation field_240899_b_;
   private final ResourceLocation field_240900_c_;

   public static <T> RegistryKey<T> func_240903_a_(RegistryKey<? extends Registry<T>> p_240903_0_, ResourceLocation p_240903_1_) {
      return func_240905_a_(p_240903_0_.field_240900_c_, p_240903_1_);
   }

   public static <T> RegistryKey<Registry<T>> func_240904_a_(ResourceLocation p_240904_0_) {
      return func_240905_a_(Registry.field_239706_f_, p_240904_0_);
   }

   private static <T> RegistryKey<T> func_240905_a_(ResourceLocation p_240905_0_, ResourceLocation p_240905_1_) {
      String s = (p_240905_0_ + ":" + p_240905_1_).intern();
      return (RegistryKey<T>)field_240898_a_.computeIfAbsent(s, (p_240906_2_) -> {
         return new RegistryKey(p_240905_0_, p_240905_1_);
      });
   }

   private RegistryKey(ResourceLocation p_i232592_1_, ResourceLocation p_i232592_2_) {
      this.field_240899_b_ = p_i232592_1_;
      this.field_240900_c_ = p_i232592_2_;
   }

   public String toString() {
      return "ResourceKey[" + this.field_240899_b_ + " / " + this.field_240900_c_ + ']';
   }

   public boolean func_244356_a(RegistryKey<? extends Registry<?>> p_244356_1_) {
      return this.field_240899_b_.equals(p_244356_1_.func_240901_a_());
   }

   public ResourceLocation func_240901_a_() {
      return this.field_240900_c_;
   }

   public static <T> Function<ResourceLocation, RegistryKey<T>> func_240902_a_(RegistryKey<? extends Registry<T>> p_240902_0_) {
      return (p_240907_1_) -> {
         return func_240903_a_(p_240902_0_, p_240907_1_);
      };
   }

   public ResourceLocation getRegistryName() {
      return this.field_240899_b_;
   }

   @Override
   public int compareTo(RegistryKey<?> o) {
      int ret = this.getRegistryName().compareTo(o.getRegistryName());
      if (ret == 0) ret = this.func_240901_a_().compareTo(o.func_240901_a_());
      return ret;
   }
}