package net.minecraft.util;

import com.mojang.serialization.Codec;
import java.util.Arrays;
import java.util.UUID;

public final class UUIDCodec {
   public static final Codec<UUID> field_239775_a_ = Codec.INT_STREAM.comapFlatMap((p_239778_0_) -> {
      return Util.func_240987_a_(p_239778_0_, 4).map(UUIDCodec::func_239779_a_);
   }, (p_239780_0_) -> {
      return Arrays.stream(func_239777_a_(p_239780_0_));
   });

   public static UUID func_239779_a_(int[] p_239779_0_) {
      return new UUID((long)p_239779_0_[0] << 32 | (long)p_239779_0_[1] & 4294967295L, (long)p_239779_0_[2] << 32 | (long)p_239779_0_[3] & 4294967295L);
   }

   public static int[] func_239777_a_(UUID p_239777_0_) {
      long i = p_239777_0_.getMostSignificantBits();
      long j = p_239777_0_.getLeastSignificantBits();
      return func_239776_a_(i, j);
   }

   private static int[] func_239776_a_(long p_239776_0_, long p_239776_2_) {
      return new int[]{(int)(p_239776_0_ >> 32), (int)p_239776_0_, (int)(p_239776_2_ >> 32), (int)p_239776_2_};
   }
}