package net.minecraft.block;

import net.minecraft.util.IStringSerializable;

public enum WallHeight implements IStringSerializable {
   NONE("none"),
   LOW("low"),
   TALL("tall");

   private final String field_235922_d_;

   private WallHeight(String p_i231882_3_) {
      this.field_235922_d_ = p_i231882_3_;
   }

   public String toString() {
      return this.func_176610_l();
   }

   public String func_176610_l() {
      return this.field_235922_d_;
   }
}