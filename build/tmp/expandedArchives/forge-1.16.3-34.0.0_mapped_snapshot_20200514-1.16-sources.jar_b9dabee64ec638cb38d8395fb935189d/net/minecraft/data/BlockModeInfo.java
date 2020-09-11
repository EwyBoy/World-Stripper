package net.minecraft.data;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.function.Function;

public class BlockModeInfo<T> {
   private final String field_240210_a_;
   private final Function<T, JsonElement> field_240211_b_;

   public BlockModeInfo(String p_i232543_1_, Function<T, JsonElement> p_i232543_2_) {
      this.field_240210_a_ = p_i232543_1_;
      this.field_240211_b_ = p_i232543_2_;
   }

   public BlockModeInfo<T>.Field func_240213_a_(T p_240213_1_) {
      return new BlockModeInfo.Field(p_240213_1_);
   }

   public String toString() {
      return this.field_240210_a_;
   }

   public class Field {
      private final T field_240216_b_;

      public Field(T p_i232544_2_) {
         this.field_240216_b_ = p_i232544_2_;
      }

      public void func_240217_a_(JsonObject p_240217_1_) {
         p_240217_1_.add(BlockModeInfo.this.field_240210_a_, BlockModeInfo.this.field_240211_b_.apply(this.field_240216_b_));
      }

      public String toString() {
         return BlockModeInfo.this.field_240210_a_ + "=" + this.field_240216_b_;
      }
   }
}