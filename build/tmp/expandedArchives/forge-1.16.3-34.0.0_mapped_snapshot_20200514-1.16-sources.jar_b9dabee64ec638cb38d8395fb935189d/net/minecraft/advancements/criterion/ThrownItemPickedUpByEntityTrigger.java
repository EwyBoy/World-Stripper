package net.minecraft.advancements.criterion;

import com.google.gson.JsonObject;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.ConditionArrayParser;
import net.minecraft.loot.ConditionArraySerializer;
import net.minecraft.loot.LootContext;
import net.minecraft.util.ResourceLocation;

public class ThrownItemPickedUpByEntityTrigger extends AbstractCriterionTrigger<ThrownItemPickedUpByEntityTrigger.Instance> {
   private static final ResourceLocation field_234829_a_ = new ResourceLocation("thrown_item_picked_up_by_entity");

   public ResourceLocation getId() {
      return field_234829_a_;
   }

   protected ThrownItemPickedUpByEntityTrigger.Instance func_230241_b_(JsonObject p_230241_1_, EntityPredicate.AndPredicate p_230241_2_, ConditionArrayParser p_230241_3_) {
      ItemPredicate itempredicate = ItemPredicate.deserialize(p_230241_1_.get("item"));
      EntityPredicate.AndPredicate entitypredicate$andpredicate = EntityPredicate.AndPredicate.func_234587_a_(p_230241_1_, "entity", p_230241_3_);
      return new ThrownItemPickedUpByEntityTrigger.Instance(p_230241_2_, itempredicate, entitypredicate$andpredicate);
   }

   public void func_234830_a_(ServerPlayerEntity p_234830_1_, ItemStack p_234830_2_, Entity p_234830_3_) {
      LootContext lootcontext = EntityPredicate.func_234575_b_(p_234830_1_, p_234830_3_);
      this.func_235959_a_(p_234830_1_, (p_234831_3_) -> {
         return p_234831_3_.func_234836_a_(p_234830_1_, p_234830_2_, lootcontext);
      });
   }

   public static class Instance extends CriterionInstance {
      private final ItemPredicate field_234833_a_;
      private final EntityPredicate.AndPredicate field_234834_b_;

      public Instance(EntityPredicate.AndPredicate p_i231599_1_, ItemPredicate p_i231599_2_, EntityPredicate.AndPredicate p_i231599_3_) {
         super(ThrownItemPickedUpByEntityTrigger.field_234829_a_, p_i231599_1_);
         this.field_234833_a_ = p_i231599_2_;
         this.field_234834_b_ = p_i231599_3_;
      }

      public static ThrownItemPickedUpByEntityTrigger.Instance func_234835_a_(EntityPredicate.AndPredicate p_234835_0_, ItemPredicate.Builder p_234835_1_, EntityPredicate.AndPredicate p_234835_2_) {
         return new ThrownItemPickedUpByEntityTrigger.Instance(p_234835_0_, p_234835_1_.build(), p_234835_2_);
      }

      public boolean func_234836_a_(ServerPlayerEntity p_234836_1_, ItemStack p_234836_2_, LootContext p_234836_3_) {
         if (!this.field_234833_a_.test(p_234836_2_)) {
            return false;
         } else {
            return this.field_234834_b_.func_234588_a_(p_234836_3_);
         }
      }

      public JsonObject func_230240_a_(ConditionArraySerializer p_230240_1_) {
         JsonObject jsonobject = super.func_230240_a_(p_230240_1_);
         jsonobject.add("item", this.field_234833_a_.serialize());
         jsonobject.add("entity", this.field_234834_b_.func_234586_a_(p_230240_1_));
         return jsonobject;
      }
   }
}