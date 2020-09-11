package net.minecraft.loot;

import net.minecraft.loot.conditions.ILootCondition;

public class GroupLootEntry extends ParentedLootEntry {
   GroupLootEntry(LootEntry[] p_i51257_1_, ILootCondition[] p_i51257_2_) {
      super(p_i51257_1_, p_i51257_2_);
   }

   public LootPoolEntryType func_230420_a_() {
      return LootEntryManager.field_237417_h_;
   }

   protected ILootEntry combineChildren(ILootEntry[] p_216146_1_) {
      switch(p_216146_1_.length) {
      case 0:
         return field_216140_b;
      case 1:
         return p_216146_1_[0];
      case 2:
         ILootEntry ilootentry = p_216146_1_[0];
         ILootEntry ilootentry1 = p_216146_1_[1];
         return (p_216151_2_, p_216151_3_) -> {
            ilootentry.expand(p_216151_2_, p_216151_3_);
            ilootentry1.expand(p_216151_2_, p_216151_3_);
            return true;
         };
      default:
         return (p_216152_1_, p_216152_2_) -> {
            for(ILootEntry ilootentry2 : p_216146_1_) {
               ilootentry2.expand(p_216152_1_, p_216152_2_);
            }

            return true;
         };
      }
   }
}