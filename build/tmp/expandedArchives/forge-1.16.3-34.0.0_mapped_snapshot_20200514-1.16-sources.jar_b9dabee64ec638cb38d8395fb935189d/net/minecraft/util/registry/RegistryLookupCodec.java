package net.minecraft.util.registry;

import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.MapLike;
import com.mojang.serialization.RecordBuilder;
import java.util.stream.Stream;
import net.minecraft.util.RegistryKey;

public final class RegistryLookupCodec<E> extends MapCodec<Registry<E>> {
   private final RegistryKey<? extends Registry<E>> field_244330_a;

   public static <E> RegistryLookupCodec<E> func_244331_a(RegistryKey<? extends Registry<E>> p_244331_0_) {
      return new RegistryLookupCodec<>(p_244331_0_);
   }

   private RegistryLookupCodec(RegistryKey<? extends Registry<E>> p_i242091_1_) {
      this.field_244330_a = p_i242091_1_;
   }

   public <T> RecordBuilder<T> encode(Registry<E> p_encode_1_, DynamicOps<T> p_encode_2_, RecordBuilder<T> p_encode_3_) {
      return p_encode_3_;
   }

   public <T> DataResult<Registry<E>> decode(DynamicOps<T> p_decode_1_, MapLike<T> p_decode_2_) {
      return p_decode_1_ instanceof WorldSettingsImport ? ((WorldSettingsImport)p_decode_1_).func_244340_a(this.field_244330_a) : DataResult.error("Not a registry ops");
   }

   public String toString() {
      return "RegistryLookupCodec[" + this.field_244330_a + "]";
   }

   public <T> Stream<T> keys(DynamicOps<T> p_keys_1_) {
      return Stream.empty();
   }
}