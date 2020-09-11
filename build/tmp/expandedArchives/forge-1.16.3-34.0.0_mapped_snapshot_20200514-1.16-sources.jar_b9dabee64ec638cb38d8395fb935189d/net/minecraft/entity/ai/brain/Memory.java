package net.minecraft.entity.ai.brain;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Optional;

public class Memory<T> {
   private final T field_234062_a_;
   private long field_234063_b_;

   public Memory(T p_i231551_1_, long p_i231551_2_) {
      this.field_234062_a_ = p_i231551_1_;
      this.field_234063_b_ = p_i231551_2_;
   }

   public void func_234064_a_() {
      if (this.func_234074_e_()) {
         --this.field_234063_b_;
      }

   }

   public static <T> Memory<T> func_234068_a_(T p_234068_0_) {
      return new Memory<>(p_234068_0_, Long.MAX_VALUE);
   }

   public static <T> Memory<T> func_234069_a_(T p_234069_0_, long p_234069_1_) {
      return new Memory<>(p_234069_0_, p_234069_1_);
   }

   public T func_234072_c_() {
      return this.field_234062_a_;
   }

   public boolean func_234073_d_() {
      return this.field_234063_b_ <= 0L;
   }

   public String toString() {
      return this.field_234062_a_.toString() + (this.func_234074_e_() ? " (ttl: " + this.field_234063_b_ + ")" : "");
   }

   public boolean func_234074_e_() {
      return this.field_234063_b_ != Long.MAX_VALUE;
   }

   public static <T> Codec<Memory<T>> func_234066_a_(Codec<T> p_234066_0_) {
      return RecordCodecBuilder.create((p_234067_1_) -> {
         return p_234067_1_.group(p_234066_0_.fieldOf("value").forGetter((p_234071_0_) -> {
            return p_234071_0_.field_234062_a_;
         }), Codec.LONG.optionalFieldOf("ttl").forGetter((p_234065_0_) -> {
            return p_234065_0_.func_234074_e_() ? Optional.of(p_234065_0_.field_234063_b_) : Optional.empty();
         })).apply(p_234067_1_, (p_234070_0_, p_234070_1_) -> {
            return new Memory<>(p_234070_0_, p_234070_1_.orElse(Long.MAX_VALUE));
         });
      });
   }
}