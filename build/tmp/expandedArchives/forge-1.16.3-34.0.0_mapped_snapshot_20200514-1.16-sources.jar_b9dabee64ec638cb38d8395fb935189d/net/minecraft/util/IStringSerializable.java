package net.minecraft.util;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.Keyable;
import java.util.Arrays;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Supplier;
import java.util.function.ToIntFunction;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public interface IStringSerializable {
   String func_176610_l();

   static <E extends Enum<E> & IStringSerializable> Codec<E> func_233023_a_(Supplier<E[]> p_233023_0_, Function<? super String, ? extends E> p_233023_1_) {
      E[] ae = p_233023_0_.get();
      return func_233024_a_(Enum::ordinal, (p_233026_1_) -> {
         return ae[p_233026_1_];
      }, p_233023_1_);
   }

   static <E extends IStringSerializable> Codec<E> func_233024_a_(final ToIntFunction<E> p_233024_0_, final IntFunction<E> p_233024_1_, final Function<? super String, ? extends E> p_233024_2_) {
      return new Codec<E>() {
         public <T> DataResult<T> encode(E p_encode_1_, DynamicOps<T> p_encode_2_, T p_encode_3_) {
            return p_encode_2_.compressMaps() ? p_encode_2_.mergeToPrimitive(p_encode_3_, p_encode_2_.createInt(p_233024_0_.applyAsInt(p_encode_1_))) : p_encode_2_.mergeToPrimitive(p_encode_3_, p_encode_2_.createString(p_encode_1_.func_176610_l()));
         }

         public <T> DataResult<Pair<E, T>> decode(DynamicOps<T> p_decode_1_, T p_decode_2_) {
            return p_decode_1_.compressMaps() ? p_decode_1_.getNumberValue(p_decode_2_).flatMap((p_233034_1_) -> {
               return Optional.ofNullable(p_233024_1_.apply(p_233034_1_.intValue())).map(DataResult::success).orElseGet(() -> {
                  return DataResult.error("Unknown element id: " + p_233034_1_);
               });
            }).map((p_233035_1_) -> {
               return Pair.of(p_233035_1_, p_decode_1_.empty());
            }) : p_decode_1_.getStringValue(p_decode_2_).flatMap((p_233033_1_) -> {
               return Optional.ofNullable(p_233024_2_.apply(p_233033_1_)).map(DataResult::success).orElseGet(() -> {
                  return DataResult.error("Unknown element name: " + p_233033_1_);
               });
            }).map((p_233030_1_) -> {
               return Pair.of(p_233030_1_, p_decode_1_.empty());
            });
         }

         public String toString() {
            return "StringRepresentable[" + p_233024_0_ + "]";
         }
      };
   }

   static Keyable func_233025_a_(final IStringSerializable[] p_233025_0_) {
      return new Keyable() {
         public <T> Stream<T> keys(DynamicOps<T> p_keys_1_) {
            return p_keys_1_.compressMaps() ? IntStream.range(0, p_233025_0_.length).mapToObj(p_keys_1_::createInt) : Arrays.stream(p_233025_0_).map(IStringSerializable::func_176610_l).map(p_keys_1_::createString);
         }
      };
   }
}