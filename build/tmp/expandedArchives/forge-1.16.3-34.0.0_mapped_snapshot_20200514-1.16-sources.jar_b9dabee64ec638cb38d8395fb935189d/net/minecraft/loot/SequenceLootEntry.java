package net.minecraft.loot;

import net.minecraft.loot.conditions.ILootCondition;

public class SequenceLootEntry extends ParentedLootEntry {
   SequenceLootEntry(LootEntry[] p_i51250_1_, ILootCondition[] p_i51250_2_) {
      super(p_i51250_1_, p_i51250_2_);
   }

   public LootPoolEntryType func_230420_a_() {
      return LootEntryManager.field_237416_g_;
   }

   protected ILootEntry combineChildren(ILootEntry[] p_216146_1_) {
      switch(p_216146_1_.length) {
      case 0:
         return field_216140_b;
      case 1:
         return p_216146_1_[0];
      case 2:
         return p_216146_1_[0].sequence(p_216146_1_[1]);
      default:
         return (p_216153_1_, p_216153_2_) -> {
            for(ILootEntry ilootentry : p_216146_1_) {
               if (!ilootentry.expand(p_216153_1_, p_216153_2_)) {
                  return false;
               }
            }

            return true;
         };
      }
   }
}