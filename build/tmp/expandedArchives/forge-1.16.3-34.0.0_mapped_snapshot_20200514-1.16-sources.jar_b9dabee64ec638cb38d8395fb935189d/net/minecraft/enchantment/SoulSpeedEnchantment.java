package net.minecraft.enchantment;

import net.minecraft.inventory.EquipmentSlotType;

public class SoulSpeedEnchantment extends Enchantment {
   public SoulSpeedEnchantment(Enchantment.Rarity p_i231601_1_, EquipmentSlotType... p_i231601_2_) {
      super(p_i231601_1_, EnchantmentType.ARMOR_FEET, p_i231601_2_);
   }

   /**
    * Returns the minimal value of enchantability needed on the enchantment level passed.
    */
   public int getMinEnchantability(int enchantmentLevel) {
      return enchantmentLevel * 10;
   }

   public int getMaxEnchantability(int enchantmentLevel) {
      return this.getMinEnchantability(enchantmentLevel) + 15;
   }

   public boolean isTreasureEnchantment() {
      return true;
   }

   public boolean func_230309_h_() {
      return false;
   }

   public boolean func_230310_i_() {
      return false;
   }

   /**
    * Returns the maximum level that the enchantment can have.
    */
   public int getMaxLevel() {
      return 3;
   }
}