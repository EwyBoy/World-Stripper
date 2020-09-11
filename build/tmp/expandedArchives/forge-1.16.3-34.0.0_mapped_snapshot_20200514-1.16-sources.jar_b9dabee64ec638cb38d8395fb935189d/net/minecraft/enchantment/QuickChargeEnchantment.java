package net.minecraft.enchantment;

import net.minecraft.inventory.EquipmentSlotType;

public class QuickChargeEnchantment extends Enchantment {
   public QuickChargeEnchantment(Enchantment.Rarity rarityIn, EquipmentSlotType... slotTypes) {
      super(rarityIn, EnchantmentType.CROSSBOW, slotTypes);
   }

   /**
    * Returns the minimal value of enchantability needed on the enchantment level passed.
    */
   public int getMinEnchantability(int enchantmentLevel) {
      return 12 + (enchantmentLevel - 1) * 20;
   }

   public int getMaxEnchantability(int enchantmentLevel) {
      return 50;
   }

   /**
    * Returns the maximum level that the enchantment can have.
    */
   public int getMaxLevel() {
      return 3;
   }
}