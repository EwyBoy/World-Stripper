package net.minecraft.advancements.criterion;

import com.google.gson.JsonObject;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.ConditionArrayParser;
import net.minecraft.loot.ConditionArraySerializer;
import net.minecraft.util.ResourceLocation;

public class EnchantedItemTrigger extends AbstractCriterionTrigger<EnchantedItemTrigger.Instance> {
   private static final ResourceLocation ID = new ResourceLocation("enchanted_item");

   public ResourceLocation getId() {
      return ID;
   }

   public EnchantedItemTrigger.Instance func_230241_b_(JsonObject p_230241_1_, EntityPredicate.AndPredicate p_230241_2_, ConditionArrayParser p_230241_3_) {
      ItemPredicate itempredicate = ItemPredicate.deserialize(p_230241_1_.get("item"));
      MinMaxBounds.IntBound minmaxbounds$intbound = MinMaxBounds.IntBound.fromJson(p_230241_1_.get("levels"));
      return new EnchantedItemTrigger.Instance(p_230241_2_, itempredicate, minmaxbounds$intbound);
   }

   public void trigger(ServerPlayerEntity player, ItemStack item, int levelsSpent) {
      this.func_235959_a_(player, (p_226528_2_) -> {
         return p_226528_2_.test(item, levelsSpent);
      });
   }

   public static class Instance extends CriterionInstance {
      private final ItemPredicate item;
      private final MinMaxBounds.IntBound levels;

      public Instance(EntityPredicate.AndPredicate p_i231556_1_, ItemPredicate p_i231556_2_, MinMaxBounds.IntBound p_i231556_3_) {
         super(EnchantedItemTrigger.ID, p_i231556_1_);
         this.item = p_i231556_2_;
         this.levels = p_i231556_3_;
      }

      public static EnchantedItemTrigger.Instance any() {
         return new EnchantedItemTrigger.Instance(EntityPredicate.AndPredicate.field_234582_a_, ItemPredicate.ANY, MinMaxBounds.IntBound.UNBOUNDED);
      }

      public boolean test(ItemStack item, int levelsIn) {
         if (!this.item.test(item)) {
            return false;
         } else {
            return this.levels.test(levelsIn);
         }
      }

      public JsonObject func_230240_a_(ConditionArraySerializer p_230240_1_) {
         JsonObject jsonobject = super.func_230240_a_(p_230240_1_);
         jsonobject.add("item", this.item.serialize());
         jsonobject.add("levels", this.levels.serialize());
         return jsonobject;
      }
   }
}