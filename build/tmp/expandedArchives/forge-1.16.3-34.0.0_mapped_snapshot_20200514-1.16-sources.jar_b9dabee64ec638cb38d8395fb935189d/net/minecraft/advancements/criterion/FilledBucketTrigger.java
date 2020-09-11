package net.minecraft.advancements.criterion;

import com.google.gson.JsonObject;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.ConditionArrayParser;
import net.minecraft.loot.ConditionArraySerializer;
import net.minecraft.util.ResourceLocation;

public class FilledBucketTrigger extends AbstractCriterionTrigger<FilledBucketTrigger.Instance> {
   private static final ResourceLocation ID = new ResourceLocation("filled_bucket");

   public ResourceLocation getId() {
      return ID;
   }

   public FilledBucketTrigger.Instance func_230241_b_(JsonObject p_230241_1_, EntityPredicate.AndPredicate p_230241_2_, ConditionArrayParser p_230241_3_) {
      ItemPredicate itempredicate = ItemPredicate.deserialize(p_230241_1_.get("item"));
      return new FilledBucketTrigger.Instance(p_230241_2_, itempredicate);
   }

   public void trigger(ServerPlayerEntity p_204817_1_, ItemStack p_204817_2_) {
      this.func_235959_a_(p_204817_1_, (p_226627_1_) -> {
         return p_226627_1_.test(p_204817_2_);
      });
   }

   public static class Instance extends CriterionInstance {
      private final ItemPredicate item;

      public Instance(EntityPredicate.AndPredicate p_i231585_1_, ItemPredicate p_i231585_2_) {
         super(FilledBucketTrigger.ID, p_i231585_1_);
         this.item = p_i231585_2_;
      }

      public static FilledBucketTrigger.Instance forItem(ItemPredicate p_204827_0_) {
         return new FilledBucketTrigger.Instance(EntityPredicate.AndPredicate.field_234582_a_, p_204827_0_);
      }

      public boolean test(ItemStack p_204826_1_) {
         return this.item.test(p_204826_1_);
      }

      public JsonObject func_230240_a_(ConditionArraySerializer p_230240_1_) {
         JsonObject jsonobject = super.func_230240_a_(p_230240_1_);
         jsonobject.add("item", this.item.serialize());
         return jsonobject;
      }
   }
}