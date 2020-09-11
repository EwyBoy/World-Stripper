package net.minecraft.util.registry;

import com.mojang.serialization.Lifecycle;
import java.util.OptionalInt;
import net.minecraft.util.RegistryKey;

public abstract class MutableRegistry<T> extends Registry<T> {
   public MutableRegistry(RegistryKey<? extends Registry<T>> p_i232512_1_, Lifecycle p_i232512_2_) {
      super(p_i232512_1_, p_i232512_2_);
   }

   public abstract <V extends T> V register(int id, RegistryKey<T> name, V instance, Lifecycle p_218382_4_);

   public abstract <V extends T> V register(RegistryKey<T> name, V instance, Lifecycle p_218381_3_);

   public abstract <V extends T> V func_241874_a(OptionalInt p_241874_1_, RegistryKey<T> p_241874_2_, V p_241874_3_, Lifecycle p_241874_4_);
}