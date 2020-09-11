package net.minecraft.entity.passive.horse;

import java.util.Arrays;
import java.util.Comparator;

public enum CoatTypes {
   NONE(0),
   WHITE(1),
   WHITE_FIELD(2),
   WHITE_DOTS(3),
   BLACK_DOTS(4);

   private static final CoatTypes[] field_234245_f_ = Arrays.stream(values()).sorted(Comparator.comparingInt(CoatTypes::func_234247_a_)).toArray((p_234249_0_) -> {
      return new CoatTypes[p_234249_0_];
   });
   private final int field_234246_g_;

   private CoatTypes(int p_i231558_3_) {
      this.field_234246_g_ = p_i231558_3_;
   }

   public int func_234247_a_() {
      return this.field_234246_g_;
   }

   public static CoatTypes func_234248_a_(int p_234248_0_) {
      return field_234245_f_[p_234248_0_ % field_234245_f_.length];
   }
}