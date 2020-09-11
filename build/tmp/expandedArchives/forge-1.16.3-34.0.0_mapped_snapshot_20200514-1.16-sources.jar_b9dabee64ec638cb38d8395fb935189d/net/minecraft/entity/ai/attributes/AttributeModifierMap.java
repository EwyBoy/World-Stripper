package net.minecraft.entity.ai.attributes;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.util.registry.Registry;

public class AttributeModifierMap {
   private final Map<Attribute, ModifiableAttributeInstance> field_233802_a_;

   public AttributeModifierMap(Map<Attribute, ModifiableAttributeInstance> p_i231503_1_) {
      this.field_233802_a_ = ImmutableMap.copyOf(p_i231503_1_);
   }

   private ModifiableAttributeInstance func_233810_d_(Attribute p_233810_1_) {
      ModifiableAttributeInstance modifiableattributeinstance = this.field_233802_a_.get(p_233810_1_);
      if (modifiableattributeinstance == null) {
         throw new IllegalArgumentException("Can't find attribute " + Registry.field_239692_aP_.getKey(p_233810_1_));
      } else {
         return modifiableattributeinstance;
      }
   }

   public double func_233804_a_(Attribute p_233804_1_) {
      return this.func_233810_d_(p_233804_1_).getValue();
   }

   public double func_233807_b_(Attribute p_233807_1_) {
      return this.func_233810_d_(p_233807_1_).getBaseValue();
   }

   public double func_233805_a_(Attribute p_233805_1_, UUID p_233805_2_) {
      AttributeModifier attributemodifier = this.func_233810_d_(p_233805_1_).getModifier(p_233805_2_);
      if (attributemodifier == null) {
         throw new IllegalArgumentException("Can't find modifier " + p_233805_2_ + " on attribute " + Registry.field_239692_aP_.getKey(p_233805_1_));
      } else {
         return attributemodifier.getAmount();
      }
   }

   @Nullable
   public ModifiableAttributeInstance func_233806_a_(Consumer<ModifiableAttributeInstance> p_233806_1_, Attribute p_233806_2_) {
      ModifiableAttributeInstance modifiableattributeinstance = this.field_233802_a_.get(p_233806_2_);
      if (modifiableattributeinstance == null) {
         return null;
      } else {
         ModifiableAttributeInstance modifiableattributeinstance1 = new ModifiableAttributeInstance(p_233806_2_, p_233806_1_);
         modifiableattributeinstance1.func_233763_a_(modifiableattributeinstance);
         return modifiableattributeinstance1;
      }
   }

   public static AttributeModifierMap.MutableAttribute func_233803_a_() {
      return new AttributeModifierMap.MutableAttribute();
   }

   public boolean func_233809_c_(Attribute p_233809_1_) {
      return this.field_233802_a_.containsKey(p_233809_1_);
   }

   public boolean func_233808_b_(Attribute p_233808_1_, UUID p_233808_2_) {
      ModifiableAttributeInstance modifiableattributeinstance = this.field_233802_a_.get(p_233808_1_);
      return modifiableattributeinstance != null && modifiableattributeinstance.getModifier(p_233808_2_) != null;
   }

   public static class MutableAttribute {
      private final Map<Attribute, ModifiableAttributeInstance> field_233811_a_ = Maps.newHashMap();
      private boolean field_233812_b_;

      private ModifiableAttributeInstance func_233817_b_(Attribute p_233817_1_) {
         ModifiableAttributeInstance modifiableattributeinstance = new ModifiableAttributeInstance(p_233817_1_, (p_233816_2_) -> {
            if (this.field_233812_b_) {
               throw new UnsupportedOperationException("Tried to change value for default attribute instance: " + Registry.field_239692_aP_.getKey(p_233817_1_));
            }
         });
         this.field_233811_a_.put(p_233817_1_, modifiableattributeinstance);
         return modifiableattributeinstance;
      }

      public AttributeModifierMap.MutableAttribute func_233814_a_(Attribute p_233814_1_) {
         this.func_233817_b_(p_233814_1_);
         return this;
      }

      public AttributeModifierMap.MutableAttribute func_233815_a_(Attribute p_233815_1_, double p_233815_2_) {
         ModifiableAttributeInstance modifiableattributeinstance = this.func_233817_b_(p_233815_1_);
         modifiableattributeinstance.setBaseValue(p_233815_2_);
         return this;
      }

      public AttributeModifierMap func_233813_a_() {
         this.field_233812_b_ = true;
         return new AttributeModifierMap(this.field_233811_a_);
      }
   }
}