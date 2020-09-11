package net.minecraft.util.registry;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import java.util.Optional;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.datafix.DelegatingDynamicOps;

public class WorldGenSettingsExport<T> extends DelegatingDynamicOps<T> {
   private final DynamicRegistries field_240895_b_;

   public static <T> WorldGenSettingsExport<T> func_240896_a_(DynamicOps<T> p_240896_0_, DynamicRegistries p_240896_1_) {
      return new WorldGenSettingsExport<>(p_240896_0_, p_240896_1_);
   }

   private WorldGenSettingsExport(DynamicOps<T> p_i232591_1_, DynamicRegistries p_i232591_2_) {
      super(p_i232591_1_);
      this.field_240895_b_ = p_i232591_2_;
   }

   protected <E> DataResult<T> func_241811_a_(E p_241811_1_, T p_241811_2_, RegistryKey<? extends Registry<E>> p_241811_3_, Codec<E> p_241811_4_) {
      Optional<MutableRegistry<E>> optional = this.field_240895_b_.func_230521_a_(p_241811_3_);
      if (optional.isPresent()) {
         MutableRegistry<E> mutableregistry = optional.get();
         Optional<RegistryKey<E>> optional1 = mutableregistry.func_230519_c_(p_241811_1_);
         if (optional1.isPresent()) {
            RegistryKey<E> registrykey = optional1.get();
            return ResourceLocation.field_240908_a_.encode(registrykey.func_240901_a_(), this.field_240857_a_, p_241811_2_);
         }
      }

      return p_241811_4_.encode(p_241811_1_, this, p_241811_2_);
   }
}