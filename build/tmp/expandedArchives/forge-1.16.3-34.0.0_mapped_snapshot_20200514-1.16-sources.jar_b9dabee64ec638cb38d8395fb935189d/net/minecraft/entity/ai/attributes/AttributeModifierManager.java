package net.minecraft.entity.ai.attributes;

import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AttributeModifierManager {
   private static final Logger field_233774_a_ = LogManager.getLogger();
   private final Map<Attribute, ModifiableAttributeInstance> field_233775_b_ = Maps.newHashMap();
   private final Set<ModifiableAttributeInstance> field_233776_c_ = Sets.newHashSet();
   private final AttributeModifierMap field_233777_d_;

   public AttributeModifierManager(AttributeModifierMap p_i231502_1_) {
      this.field_233777_d_ = p_i231502_1_;
   }

   private void func_233783_a_(ModifiableAttributeInstance p_233783_1_) {
      if (p_233783_1_.getAttribute().getShouldWatch()) {
         this.field_233776_c_.add(p_233783_1_);
      }

   }

   public Set<ModifiableAttributeInstance> func_233778_a_() {
      return this.field_233776_c_;
   }

   public Collection<ModifiableAttributeInstance> func_233789_b_() {
      return this.field_233775_b_.values().stream().filter((p_233796_0_) -> {
         return p_233796_0_.getAttribute().getShouldWatch();
      }).collect(Collectors.toList());
   }

   @Nullable
   public ModifiableAttributeInstance func_233779_a_(Attribute p_233779_1_) {
      return this.field_233775_b_.computeIfAbsent(p_233779_1_, (p_233798_1_) -> {
         return this.field_233777_d_.func_233806_a_(this::func_233783_a_, p_233798_1_);
      });
   }

   public boolean func_233790_b_(Attribute p_233790_1_) {
      return this.field_233775_b_.get(p_233790_1_) != null || this.field_233777_d_.func_233809_c_(p_233790_1_);
   }

   public boolean func_233782_a_(Attribute p_233782_1_, UUID p_233782_2_) {
      ModifiableAttributeInstance modifiableattributeinstance = this.field_233775_b_.get(p_233782_1_);
      return modifiableattributeinstance != null ? modifiableattributeinstance.getModifier(p_233782_2_) != null : this.field_233777_d_.func_233808_b_(p_233782_1_, p_233782_2_);
   }

   public double func_233795_c_(Attribute p_233795_1_) {
      ModifiableAttributeInstance modifiableattributeinstance = this.field_233775_b_.get(p_233795_1_);
      return modifiableattributeinstance != null ? modifiableattributeinstance.getValue() : this.field_233777_d_.func_233804_a_(p_233795_1_);
   }

   public double func_233797_d_(Attribute p_233797_1_) {
      ModifiableAttributeInstance modifiableattributeinstance = this.field_233775_b_.get(p_233797_1_);
      return modifiableattributeinstance != null ? modifiableattributeinstance.getBaseValue() : this.field_233777_d_.func_233807_b_(p_233797_1_);
   }

   public double func_233791_b_(Attribute p_233791_1_, UUID p_233791_2_) {
      ModifiableAttributeInstance modifiableattributeinstance = this.field_233775_b_.get(p_233791_1_);
      return modifiableattributeinstance != null ? modifiableattributeinstance.getModifier(p_233791_2_).getAmount() : this.field_233777_d_.func_233805_a_(p_233791_1_, p_233791_2_);
   }

   public void func_233785_a_(Multimap<Attribute, AttributeModifier> p_233785_1_) {
      p_233785_1_.asMap().forEach((p_233781_1_, p_233781_2_) -> {
         ModifiableAttributeInstance modifiableattributeinstance = this.field_233775_b_.get(p_233781_1_);
         if (modifiableattributeinstance != null) {
            p_233781_2_.forEach(modifiableattributeinstance::removeModifier);
         }

      });
   }

   public void func_233793_b_(Multimap<Attribute, AttributeModifier> p_233793_1_) {
      p_233793_1_.forEach((p_233780_1_, p_233780_2_) -> {
         ModifiableAttributeInstance modifiableattributeinstance = this.func_233779_a_(p_233780_1_);
         if (modifiableattributeinstance != null) {
            modifiableattributeinstance.removeModifier(p_233780_2_);
            modifiableattributeinstance.func_233767_b_(p_233780_2_);
         }

      });
   }

   @OnlyIn(Dist.CLIENT)
   public void func_233784_a_(AttributeModifierManager p_233784_1_) {
      p_233784_1_.field_233775_b_.values().forEach((p_233792_1_) -> {
         ModifiableAttributeInstance modifiableattributeinstance = this.func_233779_a_(p_233792_1_.getAttribute());
         if (modifiableattributeinstance != null) {
            modifiableattributeinstance.func_233763_a_(p_233792_1_);
         }

      });
   }

   public ListNBT func_233794_c_() {
      ListNBT listnbt = new ListNBT();

      for(ModifiableAttributeInstance modifiableattributeinstance : this.field_233775_b_.values()) {
         listnbt.add(modifiableattributeinstance.func_233772_g_());
      }

      return listnbt;
   }

   public void func_233788_a_(ListNBT p_233788_1_) {
      for(int i = 0; i < p_233788_1_.size(); ++i) {
         CompoundNBT compoundnbt = p_233788_1_.getCompound(i);
         String s = compoundnbt.getString("Name");
         Util.acceptOrElse(Registry.field_239692_aP_.func_241873_b(ResourceLocation.tryCreate(s)), (p_233787_2_) -> {
            ModifiableAttributeInstance modifiableattributeinstance = this.func_233779_a_(p_233787_2_);
            if (modifiableattributeinstance != null) {
               modifiableattributeinstance.func_233765_a_(compoundnbt);
            }

         }, () -> {
            field_233774_a_.warn("Ignoring unknown attribute '{}'", (Object)s);
         });
      }

   }
}