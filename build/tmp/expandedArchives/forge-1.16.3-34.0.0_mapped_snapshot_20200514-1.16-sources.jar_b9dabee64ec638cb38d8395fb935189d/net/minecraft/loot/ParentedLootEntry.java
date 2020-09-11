package net.minecraft.loot;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.function.Consumer;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.util.JSONUtils;

public abstract class ParentedLootEntry extends LootEntry {
   protected final LootEntry[] field_216147_c;
   private final ILootEntry field_216148_e;

   protected ParentedLootEntry(LootEntry[] p_i51262_1_, ILootCondition[] p_i51262_2_) {
      super(p_i51262_2_);
      this.field_216147_c = p_i51262_1_;
      this.field_216148_e = this.combineChildren(p_i51262_1_);
   }

   public void func_225579_a_(ValidationTracker p_225579_1_) {
      super.func_225579_a_(p_225579_1_);
      if (this.field_216147_c.length == 0) {
         p_225579_1_.addProblem("Empty children list");
      }

      for(int i = 0; i < this.field_216147_c.length; ++i) {
         this.field_216147_c[i].func_225579_a_(p_225579_1_.func_227534_b_(".entry[" + i + "]"));
      }

   }

   protected abstract ILootEntry combineChildren(ILootEntry[] p_216146_1_);

   public final boolean expand(LootContext p_expand_1_, Consumer<ILootGenerator> p_expand_2_) {
      return !this.test(p_expand_1_) ? false : this.field_216148_e.expand(p_expand_1_, p_expand_2_);
   }

   public static <T extends ParentedLootEntry> LootEntry.Serializer<T> func_237409_a_(final ParentedLootEntry.IFactory<T> p_237409_0_) {
      return new LootEntry.Serializer<T>() {
         public void func_230422_a_(JsonObject p_230422_1_, T p_230422_2_, JsonSerializationContext p_230422_3_) {
            p_230422_1_.add("children", p_230422_3_.serialize(p_230422_2_.field_216147_c));
         }

         public final T func_230421_b_(JsonObject p_230421_1_, JsonDeserializationContext p_230421_2_, ILootCondition[] p_230421_3_) {
            LootEntry[] alootentry = JSONUtils.deserializeClass(p_230421_1_, "children", p_230421_2_, LootEntry[].class);
            return p_237409_0_.create(alootentry, p_230421_3_);
         }
      };
   }

   @FunctionalInterface
   public interface IFactory<T extends ParentedLootEntry> {
      T create(LootEntry[] p_create_1_, ILootCondition[] p_create_2_);
   }
}