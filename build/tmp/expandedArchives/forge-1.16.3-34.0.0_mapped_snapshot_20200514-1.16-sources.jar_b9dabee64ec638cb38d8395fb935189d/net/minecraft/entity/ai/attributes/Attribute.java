package net.minecraft.entity.ai.attributes;

public class Attribute extends net.minecraftforge.registries.ForgeRegistryEntry<Attribute> {
   private final double field_233750_a_;
   private boolean field_233751_b_;
   private final String field_233752_c_;

   protected Attribute(String p_i231500_1_, double p_i231500_2_) {
      this.field_233750_a_ = p_i231500_2_;
      this.field_233752_c_ = p_i231500_1_;
   }

   public double getDefaultValue() {
      return this.field_233750_a_;
   }

   public boolean getShouldWatch() {
      return this.field_233751_b_;
   }

   public Attribute func_233753_a_(boolean p_233753_1_) {
      this.field_233751_b_ = p_233753_1_;
      return this;
   }

   public double clampValue(double value) {
      return value;
   }

   public String func_233754_c_() {
      return this.field_233752_c_;
   }
}