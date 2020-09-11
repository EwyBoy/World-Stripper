package net.minecraft.tags;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import java.util.List;
import java.util.Set;

public class Tag<T> implements ITag<T> {
   private final ImmutableList<T> field_241282_b_;
   private final Set<T> field_241283_c_;
   @VisibleForTesting
   protected final Class<?> field_241281_a_;

   protected Tag(Set<T> p_i241226_1_, Class<?> p_i241226_2_) {
      this.field_241281_a_ = p_i241226_2_;
      this.field_241283_c_ = p_i241226_1_;
      this.field_241282_b_ = ImmutableList.copyOf(p_i241226_1_);
   }

   public static <T> Tag<T> func_241284_a_() {
      return new Tag<>(ImmutableSet.of(), Void.class);
   }

   public static <T> Tag<T> func_241286_a_(Set<T> p_241286_0_) {
      return new Tag<>(p_241286_0_, func_241287_c_(p_241286_0_));
   }

   public boolean func_230235_a_(T p_230235_1_) {
      return this.field_241281_a_.isInstance(p_230235_1_) && this.field_241283_c_.contains(p_230235_1_);
   }

   public List<T> func_230236_b_() {
      return this.field_241282_b_;
   }

   private static <T> Class<?> func_241287_c_(Set<T> p_241287_0_) {
      if (p_241287_0_.isEmpty()) {
         return Void.class;
      } else {
         Class<?> oclass = null;

         for(T t : p_241287_0_) {
            if (oclass == null) {
               oclass = t.getClass();
            } else {
               oclass = func_241285_a_(oclass, t.getClass());
            }
         }

         return oclass;
      }
   }

   private static Class<?> func_241285_a_(Class<?> p_241285_0_, Class<?> p_241285_1_) {
      while(!p_241285_0_.isAssignableFrom(p_241285_1_)) {
         p_241285_0_ = p_241285_0_.getSuperclass();
      }

      return p_241285_0_;
   }
}