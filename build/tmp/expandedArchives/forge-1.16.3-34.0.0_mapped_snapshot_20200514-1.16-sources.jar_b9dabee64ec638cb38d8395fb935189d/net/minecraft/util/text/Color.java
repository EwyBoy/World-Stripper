package net.minecraft.util.text;

import com.google.common.collect.ImmutableMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public final class Color {
   private static final Map<TextFormatting, Color> field_240738_a_ = Stream.of(TextFormatting.values()).filter(TextFormatting::isColor).collect(ImmutableMap.toImmutableMap(Function.identity(), (p_240748_0_) -> {
      return new Color(p_240748_0_.getColor(), p_240748_0_.getFriendlyName());
   }));
   private static final Map<String, Color> field_240739_b_ = field_240738_a_.values().stream().collect(ImmutableMap.toImmutableMap((p_240746_0_) -> {
      return p_240746_0_.field_240741_d_;
   }, Function.identity()));
   private final int field_240740_c_;
   @Nullable
   private final String field_240741_d_;

   private Color(int p_i232573_1_, String p_i232573_2_) {
      this.field_240740_c_ = p_i232573_1_;
      this.field_240741_d_ = p_i232573_2_;
   }

   private Color(int p_i232572_1_) {
      this.field_240740_c_ = p_i232572_1_;
      this.field_240741_d_ = null;
   }

   @OnlyIn(Dist.CLIENT)
   public int func_240742_a_() {
      return this.field_240740_c_;
   }

   public String func_240747_b_() {
      return this.field_240741_d_ != null ? this.field_240741_d_ : this.func_240749_c_();
   }

   private String func_240749_c_() {
      return String.format("#%06X", this.field_240740_c_);
   }

   public boolean equals(Object p_equals_1_) {
      if (this == p_equals_1_) {
         return true;
      } else if (p_equals_1_ != null && this.getClass() == p_equals_1_.getClass()) {
         Color color = (Color)p_equals_1_;
         return this.field_240740_c_ == color.field_240740_c_;
      } else {
         return false;
      }
   }

   public int hashCode() {
      return Objects.hash(this.field_240740_c_, this.field_240741_d_);
   }

   public String toString() {
      return this.field_240741_d_ != null ? this.field_240741_d_ : this.func_240749_c_();
   }

   @Nullable
   public static Color func_240744_a_(TextFormatting p_240744_0_) {
      return field_240738_a_.get(p_240744_0_);
   }

   public static Color func_240743_a_(int p_240743_0_) {
      return new Color(p_240743_0_);
   }

   @Nullable
   public static Color func_240745_a_(String p_240745_0_) {
      if (p_240745_0_.startsWith("#")) {
         try {
            int i = Integer.parseInt(p_240745_0_.substring(1), 16);
            return func_240743_a_(i);
         } catch (NumberFormatException numberformatexception) {
            return null;
         }
      } else {
         return field_240739_b_.get(p_240745_0_);
      }
   }
}