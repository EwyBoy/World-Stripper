package net.minecraft.entity.ai.attributes;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import it.unimi.dsi.fastutil.objects.ObjectArraySet;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ModifiableAttributeInstance {
   /** The Attribute this is an instance of */
   private final Attribute genericAttribute;
   private final Map<AttributeModifier.Operation, Set<AttributeModifier>> mapByOperation = Maps.newEnumMap(AttributeModifier.Operation.class);
   private final Map<UUID, AttributeModifier> field_233757_c_ = new Object2ObjectArrayMap<>();
   private final Set<AttributeModifier> mapByUUID = new ObjectArraySet<>();
   private double field_233759_e_;
   private boolean field_233760_f_ = true;
   private double field_233761_g_;
   private final Consumer<ModifiableAttributeInstance> field_233762_h_;

   public ModifiableAttributeInstance(Attribute p_i231501_1_, Consumer<ModifiableAttributeInstance> p_i231501_2_) {
      this.genericAttribute = p_i231501_1_;
      this.field_233762_h_ = p_i231501_2_;
      this.field_233759_e_ = p_i231501_1_.getDefaultValue();
   }

   /**
    * Get the Attribute this is an instance of
    */
   public Attribute getAttribute() {
      return this.genericAttribute;
   }

   public double getBaseValue() {
      return this.field_233759_e_;
   }

   public void setBaseValue(double baseValue) {
      if (baseValue != this.field_233759_e_) {
         this.field_233759_e_ = baseValue;
         this.func_233771_d_();
      }
   }

   public Set<AttributeModifier> func_225504_a_(AttributeModifier.Operation p_225504_1_) {
      return this.mapByOperation.computeIfAbsent(p_225504_1_, (p_233768_0_) -> {
         return Sets.newHashSet();
      });
   }

   public Set<AttributeModifier> func_225505_c_() {
      return ImmutableSet.copyOf(this.field_233757_c_.values());
   }

   /**
    * Returns attribute modifier, if any, by the given UUID
    */
   @Nullable
   public AttributeModifier getModifier(UUID uuid) {
      return this.field_233757_c_.get(uuid);
   }

   public boolean hasModifier(AttributeModifier modifier) {
      return this.field_233757_c_.get(modifier.getID()) != null;
   }

   private void applyModifier(AttributeModifier modifier) {
      AttributeModifier attributemodifier = this.field_233757_c_.putIfAbsent(modifier.getID(), modifier);
      if (attributemodifier != null) {
         throw new IllegalArgumentException("Modifier is already applied on this attribute!");
      } else {
         this.func_225504_a_(modifier.getOperation()).add(modifier);
         this.func_233771_d_();
      }
   }

   public void func_233767_b_(AttributeModifier p_233767_1_) {
      this.applyModifier(p_233767_1_);
   }

   public void func_233769_c_(AttributeModifier p_233769_1_) {
      this.applyModifier(p_233769_1_);
      this.mapByUUID.add(p_233769_1_);
   }

   protected void func_233771_d_() {
      this.field_233760_f_ = true;
      this.field_233762_h_.accept(this);
   }

   public void removeModifier(AttributeModifier modifier) {
      this.func_225504_a_(modifier.getOperation()).remove(modifier);
      this.field_233757_c_.remove(modifier.getID());
      this.mapByUUID.remove(modifier);
      this.func_233771_d_();
   }

   public void removeModifier(UUID p_188479_1_) {
      AttributeModifier attributemodifier = this.getModifier(p_188479_1_);
      if (attributemodifier != null) {
         this.removeModifier(attributemodifier);
      }

   }

   public boolean func_233770_c_(UUID p_233770_1_) {
      AttributeModifier attributemodifier = this.getModifier(p_233770_1_);
      if (attributemodifier != null && this.mapByUUID.contains(attributemodifier)) {
         this.removeModifier(attributemodifier);
         return true;
      } else {
         return false;
      }
   }

   @OnlyIn(Dist.CLIENT)
   public void removeAllModifiers() {
      for(AttributeModifier attributemodifier : this.func_225505_c_()) {
         this.removeModifier(attributemodifier);
      }

   }

   public double getValue() {
      if (this.field_233760_f_) {
         this.field_233761_g_ = this.computeValue();
         this.field_233760_f_ = false;
      }

      return this.field_233761_g_;
   }

   private double computeValue() {
      double d0 = this.getBaseValue();

      for(AttributeModifier attributemodifier : this.func_220370_b(AttributeModifier.Operation.ADDITION)) {
         d0 += attributemodifier.getAmount();
      }

      double d1 = d0;

      for(AttributeModifier attributemodifier1 : this.func_220370_b(AttributeModifier.Operation.MULTIPLY_BASE)) {
         d1 += d0 * attributemodifier1.getAmount();
      }

      for(AttributeModifier attributemodifier2 : this.func_220370_b(AttributeModifier.Operation.MULTIPLY_TOTAL)) {
         d1 *= 1.0D + attributemodifier2.getAmount();
      }

      return this.genericAttribute.clampValue(d1);
   }

   private Collection<AttributeModifier> func_220370_b(AttributeModifier.Operation p_220370_1_) {
      return this.mapByOperation.getOrDefault(p_220370_1_, Collections.emptySet());
   }

   public void func_233763_a_(ModifiableAttributeInstance p_233763_1_) {
      this.field_233759_e_ = p_233763_1_.field_233759_e_;
      this.field_233757_c_.clear();
      this.field_233757_c_.putAll(p_233763_1_.field_233757_c_);
      this.mapByUUID.clear();
      this.mapByUUID.addAll(p_233763_1_.mapByUUID);
      this.mapByOperation.clear();
      p_233763_1_.mapByOperation.forEach((p_233764_1_, p_233764_2_) -> {
         this.func_225504_a_(p_233764_1_).addAll(p_233764_2_);
      });
      this.func_233771_d_();
   }

   public CompoundNBT func_233772_g_() {
      CompoundNBT compoundnbt = new CompoundNBT();
      compoundnbt.putString("Name", Registry.field_239692_aP_.getKey(this.genericAttribute).toString());
      compoundnbt.putDouble("Base", this.field_233759_e_);
      if (!this.mapByUUID.isEmpty()) {
         ListNBT listnbt = new ListNBT();

         for(AttributeModifier attributemodifier : this.mapByUUID) {
            listnbt.add(attributemodifier.func_233801_e_());
         }

         compoundnbt.put("Modifiers", listnbt);
      }

      return compoundnbt;
   }

   public void func_233765_a_(CompoundNBT p_233765_1_) {
      this.field_233759_e_ = p_233765_1_.getDouble("Base");
      if (p_233765_1_.contains("Modifiers", 9)) {
         ListNBT listnbt = p_233765_1_.getList("Modifiers", 10);

         for(int i = 0; i < listnbt.size(); ++i) {
            AttributeModifier attributemodifier = AttributeModifier.func_233800_a_(listnbt.getCompound(i));
            if (attributemodifier != null) {
               this.field_233757_c_.put(attributemodifier.getID(), attributemodifier);
               this.func_225504_a_(attributemodifier.getOperation()).add(attributemodifier);
               this.mapByUUID.add(attributemodifier);
            }
         }
      }

      this.func_233771_d_();
   }
}