package net.minecraft.util.registry;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.Lifecycle;
import net.minecraft.util.RegistryKey;

public final class SimpleRegistryCodec<E> implements Codec<SimpleRegistry<E>> {
   private final Codec<SimpleRegistry<E>> field_240858_a_;
   private final RegistryKey<? extends Registry<E>> field_240859_b_;
   private final Codec<E> field_240860_c_;

   public static <E> SimpleRegistryCodec<E> func_241793_a_(RegistryKey<? extends Registry<E>> p_241793_0_, Lifecycle p_241793_1_, Codec<E> p_241793_2_) {
      return new SimpleRegistryCodec<>(p_241793_0_, p_241793_1_, p_241793_2_);
   }

   private SimpleRegistryCodec(RegistryKey<? extends Registry<E>> p_i232587_1_, Lifecycle p_i232587_2_, Codec<E> p_i232587_3_) {
      this.field_240858_a_ = SimpleRegistry.func_241745_c_(p_i232587_1_, p_i232587_2_, p_i232587_3_);
      this.field_240859_b_ = p_i232587_1_;
      this.field_240860_c_ = p_i232587_3_;
   }

   public <T> DataResult<T> encode(SimpleRegistry<E> p_encode_1_, DynamicOps<T> p_encode_2_, T p_encode_3_) {
      return this.field_240858_a_.encode(p_encode_1_, p_encode_2_, p_encode_3_);
   }

   public <T> DataResult<Pair<SimpleRegistry<E>, T>> decode(DynamicOps<T> p_decode_1_, T p_decode_2_) {
      DataResult<Pair<SimpleRegistry<E>, T>> dataresult = this.field_240858_a_.decode(p_decode_1_, p_decode_2_);
      return p_decode_1_ instanceof WorldSettingsImport ? dataresult.flatMap((p_240862_2_) -> {
         return ((WorldSettingsImport)p_decode_1_).func_241797_a_(p_240862_2_.getFirst(), this.field_240859_b_, this.field_240860_c_).map((p_240861_1_) -> {
            return Pair.of(p_240861_1_, (T)p_240862_2_.getSecond());
         });
      }) : dataresult;
   }

   public String toString() {
      return "RegistryDataPackCodec[" + this.field_240858_a_ + " " + this.field_240859_b_ + " " + this.field_240860_c_ + "]";
   }
}