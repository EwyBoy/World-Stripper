package net.minecraft.advancements.criterion;

import com.google.gson.JsonObject;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.ConditionArrayParser;
import net.minecraft.loot.ConditionArraySerializer;
import net.minecraft.loot.LootContext;
import net.minecraft.util.ResourceLocation;

public class PlayerEntityInteractionTrigger extends AbstractCriterionTrigger<PlayerEntityInteractionTrigger.Instance> {
   private static final ResourceLocation field_241474_a_ = new ResourceLocation("player_interacted_with_entity");

   public ResourceLocation getId() {
      return field_241474_a_;
   }

   protected PlayerEntityInteractionTrigger.Instance func_230241_b_(JsonObject p_230241_1_, EntityPredicate.AndPredicate p_230241_2_, ConditionArrayParser p_230241_3_) {
      ItemPredicate itempredicate = ItemPredicate.deserialize(p_230241_1_.get("item"));
      EntityPredicate.AndPredicate entitypredicate$andpredicate = EntityPredicate.AndPredicate.func_234587_a_(p_230241_1_, "entity", p_230241_3_);
      return new PlayerEntityInteractionTrigger.Instance(p_230241_2_, itempredicate, entitypredicate$andpredicate);
   }

   public void func_241476_a_(ServerPlayerEntity p_241476_1_, ItemStack p_241476_2_, Entity p_241476_3_) {
      LootContext lootcontext = EntityPredicate.func_234575_b_(p_241476_1_, p_241476_3_);
      this.func_235959_a_(p_241476_1_, (p_241475_2_) -> {
         return p_241475_2_.func_241481_a_(p_241476_2_, lootcontext);
      });
   }

   public static class Instance extends CriterionInstance {
      private final ItemPredicate field_241478_a_;
      private final EntityPredicate.AndPredicate field_241479_b_;

      public Instance(EntityPredicate.AndPredicate p_i241240_1_, ItemPredicate p_i241240_2_, EntityPredicate.AndPredicate p_i241240_3_) {
         super(PlayerEntityInteractionTrigger.field_241474_a_, p_i241240_1_);
         this.field_241478_a_ = p_i241240_2_;
         this.field_241479_b_ = p_i241240_3_;
      }

      public static PlayerEntityInteractionTrigger.Instance func_241480_a_(EntityPredicate.AndPredicate p_241480_0_, ItemPredicate.Builder p_241480_1_, EntityPredicate.AndPredicate p_241480_2_) {
         return new PlayerEntityInteractionTrigger.Instance(p_241480_0_, p_241480_1_.build(), p_241480_2_);
      }

      public boolean func_241481_a_(ItemStack p_241481_1_, LootContext p_241481_2_) {
         return !this.field_241478_a_.test(p_241481_1_) ? false : this.field_241479_b_.func_234588_a_(p_241481_2_);
      }

      public JsonObject func_230240_a_(ConditionArraySerializer p_230240_1_) {
         JsonObject jsonobject = super.func_230240_a_(p_230240_1_);
         jsonobject.add("item", this.field_241478_a_.serialize());
         jsonobject.add("entity", this.field_241479_b_.func_234586_a_(p_230240_1_));
         return jsonobject;
      }
   }
}