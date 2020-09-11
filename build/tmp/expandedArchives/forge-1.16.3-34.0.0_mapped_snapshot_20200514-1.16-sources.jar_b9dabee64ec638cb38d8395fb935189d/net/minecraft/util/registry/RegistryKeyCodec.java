package net.minecraft.util.registry;

import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import java.util.List;
import java.util.function.Supplier;
import net.minecraft.util.RegistryKey;

public final class RegistryKeyCodec<E> implements Codec<Supplier<E>> {
   private final RegistryKey<? extends Registry<E>> field_240864_a_;
   private final Codec<E> field_240865_b_;
   private final boolean field_244322_c;

   public static <E> RegistryKeyCodec<E> func_241794_a_(RegistryKey<? extends Registry<E>> p_241794_0_, Codec<E> p_241794_1_) {
      return func_244325_a(p_241794_0_, p_241794_1_, true);
   }

   public static <E> Codec<List<Supplier<E>>> func_244328_b(RegistryKey<? extends Registry<E>> p_244328_0_, Codec<E> p_244328_1_) {
      return Codec.either(func_244325_a(p_244328_0_, p_244328_1_, false).listOf(), p_244328_1_.<Supplier<E>>xmap((p_244329_0_) -> {
         return () -> {
            return p_244329_0_;
         };
      }, Supplier::get).listOf()).xmap((p_244323_0_) -> {
         return p_244323_0_.map((p_244327_0_) -> {
            return p_244327_0_;
         }, (p_244324_0_) -> {
            return p_244324_0_;
         });
      }, Either::left);
   }

   private static <E> RegistryKeyCodec<E> func_244325_a(RegistryKey<? extends Registry<E>> p_244325_0_, Codec<E> p_244325_1_, boolean p_244325_2_) {
      return new RegistryKeyCodec<>(p_244325_0_, p_244325_1_, p_244325_2_);
   }

   private RegistryKeyCodec(RegistryKey<? extends Registry<E>> p_i242090_1_, Codec<E> p_i242090_2_, boolean p_i242090_3_) {
      this.field_240864_a_ = p_i242090_1_;
      this.field_240865_b_ = p_i242090_2_;
      this.field_244322_c = p_i242090_3_;
   }

   public <T> DataResult<T> encode(Supplier<E> p_encode_1_, DynamicOps<T> p_encode_2_, T p_encode_3_) {
      return p_encode_2_ instanceof WorldGenSettingsExport ? ((WorldGenSettingsExport)p_encode_2_).func_241811_a_(p_encode_1_.get(), p_encode_3_, this.field_240864_a_, this.field_240865_b_) : this.field_240865_b_.encode(p_encode_1_.get(), p_encode_2_, p_encode_3_);
   }

   public <T> DataResult<Pair<Supplier<E>, T>> decode(DynamicOps<T> p_decode_1_, T p_decode_2_) {
      return p_decode_1_ instanceof WorldSettingsImport ? ((WorldSettingsImport)p_decode_1_).func_241802_a_(p_decode_2_, this.field_240864_a_, this.field_240865_b_, this.field_244322_c) : this.field_240865_b_.decode(p_decode_1_, p_decode_2_).map((p_240866_0_) -> {
         return p_240866_0_.mapFirst((p_240867_0_) -> {
            return () -> {
               return p_240867_0_;
            };
         });
      });
   }

   public String toString() {
      return "RegistryFileCodec[" + this.field_240864_a_ + " " + this.field_240865_b_ + "]";
   }
}