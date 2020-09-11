package net.minecraft.enchantment;

import net.minecraft.block.Block;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.BowItem;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.FishingRodItem;
import net.minecraft.item.Item;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolItem;
import net.minecraft.item.TridentItem;

public enum EnchantmentType implements net.minecraftforge.common.IExtensibleEnum {
   ARMOR {
      /**
       * Return true if the item passed can be enchanted by a enchantment of this type.
       */
      public boolean canEnchantItem(Item itemIn) {
         return itemIn instanceof ArmorItem;
      }
   },
   ARMOR_FEET {
      /**
       * Return true if the item passed can be enchanted by a enchantment of this type.
       */
      public boolean canEnchantItem(Item itemIn) {
         return itemIn instanceof ArmorItem && ((ArmorItem)itemIn).getEquipmentSlot() == EquipmentSlotType.FEET;
      }
   },
   ARMOR_LEGS {
      /**
       * Return true if the item passed can be enchanted by a enchantment of this type.
       */
      public boolean canEnchantItem(Item itemIn) {
         return itemIn instanceof ArmorItem && ((ArmorItem)itemIn).getEquipmentSlot() == EquipmentSlotType.LEGS;
      }
   },
   ARMOR_CHEST {
      /**
       * Return true if the item passed can be enchanted by a enchantment of this type.
       */
      public boolean canEnchantItem(Item itemIn) {
         return itemIn instanceof ArmorItem && ((ArmorItem)itemIn).getEquipmentSlot() == EquipmentSlotType.CHEST;
      }
   },
   ARMOR_HEAD {
      /**
       * Return true if the item passed can be enchanted by a enchantment of this type.
       */
      public boolean canEnchantItem(Item itemIn) {
         return itemIn instanceof ArmorItem && ((ArmorItem)itemIn).getEquipmentSlot() == EquipmentSlotType.HEAD;
      }
   },
   WEAPON {
      /**
       * Return true if the item passed can be enchanted by a enchantment of this type.
       */
      public boolean canEnchantItem(Item itemIn) {
         return itemIn instanceof SwordItem;
      }
   },
   DIGGER {
      /**
       * Return true if the item passed can be enchanted by a enchantment of this type.
       */
      public boolean canEnchantItem(Item itemIn) {
         return itemIn instanceof ToolItem;
      }
   },
   FISHING_ROD {
      /**
       * Return true if the item passed can be enchanted by a enchantment of this type.
       */
      public boolean canEnchantItem(Item itemIn) {
         return itemIn instanceof FishingRodItem;
      }
   },
   TRIDENT {
      /**
       * Return true if the item passed can be enchanted by a enchantment of this type.
       */
      public boolean canEnchantItem(Item itemIn) {
         return itemIn instanceof TridentItem;
      }
   },
   BREAKABLE {
      /**
       * Return true if the item passed can be enchanted by a enchantment of this type.
       */
      public boolean canEnchantItem(Item itemIn) {
         return itemIn.isDamageable();
      }
   },
   BOW {
      /**
       * Return true if the item passed can be enchanted by a enchantment of this type.
       */
      public boolean canEnchantItem(Item itemIn) {
         return itemIn instanceof BowItem;
      }
   },
   WEARABLE {
      /**
       * Return true if the item passed can be enchanted by a enchantment of this type.
       */
      public boolean canEnchantItem(Item itemIn) {
         return itemIn instanceof IArmorVanishable || Block.getBlockFromItem(itemIn) instanceof IArmorVanishable;
      }
   },
   CROSSBOW {
      /**
       * Return true if the item passed can be enchanted by a enchantment of this type.
       */
      public boolean canEnchantItem(Item itemIn) {
         return itemIn instanceof CrossbowItem;
      }
   },
   VANISHABLE {
      /**
       * Return true if the item passed can be enchanted by a enchantment of this type.
       */
      public boolean canEnchantItem(Item itemIn) {
         return itemIn instanceof IVanishable || Block.getBlockFromItem(itemIn) instanceof IVanishable || BREAKABLE.canEnchantItem(itemIn);
      }
   };

   private EnchantmentType() {
   }

   private java.util.function.Predicate<Item> delegate;
   private EnchantmentType(java.util.function.Predicate<Item> delegate) {
      this.delegate = delegate;
   }

   public static EnchantmentType create(String name, java.util.function.Predicate<Item> delegate) {
      throw new IllegalStateException("Enum not extended");
   }

   /**
    * Return true if the item passed can be enchanted by a enchantment of this type.
    */
   public boolean canEnchantItem(Item itemIn) {
      return this.delegate == null ? false : this.delegate.test(itemIn);
   }
}