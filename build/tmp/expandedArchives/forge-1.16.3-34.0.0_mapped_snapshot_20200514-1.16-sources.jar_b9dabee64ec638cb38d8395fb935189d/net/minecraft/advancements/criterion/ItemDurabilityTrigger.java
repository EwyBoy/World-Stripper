package net.minecraft.advancements.criterion;

import com.google.gson.JsonObject;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.ConditionArrayParser;
import net.minecraft.loot.ConditionArraySerializer;
import net.minecraft.util.ResourceLocation;

public class ItemDurabilityTrigger extends AbstractCriterionTrigger<ItemDurabilityTrigger.Instance> {
   private static final ResourceLocation ID = new ResourceLocation("item_durability_changed");

   public ResourceLocation getId() {
      return ID;
   }

   public ItemDurabilityTrigger.Instance func_230241_b_(JsonObject p_230241_1_, EntityPredicate.AndPredicate p_230241_2_, ConditionArrayParser p_230241_3_) {
      ItemPredicate itempredicate = ItemPredicate.deserialize(p_230241_1_.get("item"));
      MinMaxBounds.IntBound minmaxbounds$intbound = MinMaxBounds.IntBound.fromJson(p_230241_1_.get("durability"));
      MinMaxBounds.IntBound minmaxbounds$intbound1 = MinMaxBounds.IntBound.fromJson(p_230241_1_.get("delta"));
      return new ItemDurabilityTrigger.Instance(p_230241_2_, itempredicate, minmaxbounds$intbound, minmaxbounds$intbound1);
   }

   public void trigger(ServerPlayerEntity player, ItemStack itemIn, int newDurability) {
      this.func_235959_a_(player, (p_226653_2_) -> {
         return p_226653_2_.test(itemIn, newDurability);
      });
   }

   public static class Instance extends CriterionInstance {
      private final ItemPredicate item;
      private final MinMaxBounds.IntBound durability;
      private final MinMaxBounds.IntBound delta;

      public Instance(EntityPredicate.AndPredicate p_i231598_1_, ItemPredicate p_i231598_2_, MinMaxBounds.IntBound p_i231598_3_, MinMaxBounds.IntBound p_i231598_4_) {
         super(ItemDurabilityTrigger.ID, p_i231598_1_);
         this.item = p_i231598_2_;
         this.durability = p_i231598_3_;
         this.delta = p_i231598_4_;
      }

      public static ItemDurabilityTrigger.Instance func_234816_a_(EntityPredicate.AndPredicate p_234816_0_, ItemPredicate p_234816_1_, MinMaxBounds.IntBound p_234816_2_) {
         return new ItemDurabilityTrigger.Instance(p_234816_0_, p_234816_1_, p_234816_2_, MinMaxBounds.IntBound.UNBOUNDED);
      }

      public boolean test(ItemStack item, int p_193197_2_) {
         if (!this.item.test(item)) {
            return false;
         } else if (!this.durability.test(item.getMaxDamage() - p_193197_2_)) {
            return false;
         } else {
            return this.delta.test(item.getDamage() - p_193197_2_);
         }
      }

      public JsonObject func_230240_a_(ConditionArraySerializer p_230240_1_) {
         JsonObject jsonobject = super.func_230240_a_(p_230240_1_);
         jsonobject.add("item", this.item.serialize());
         jsonobject.add("durability", this.durability.serialize());
         jsonobject.add("delta", this.delta.serialize());
         return jsonobject;
      }
   }
}