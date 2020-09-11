package net.minecraft.advancements.criterion;

import com.google.gson.JsonObject;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.ConditionArrayParser;
import net.minecraft.loot.ConditionArraySerializer;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;

public class UsedTotemTrigger extends AbstractCriterionTrigger<UsedTotemTrigger.Instance> {
   private static final ResourceLocation ID = new ResourceLocation("used_totem");

   public ResourceLocation getId() {
      return ID;
   }

   public UsedTotemTrigger.Instance func_230241_b_(JsonObject p_230241_1_, EntityPredicate.AndPredicate p_230241_2_, ConditionArrayParser p_230241_3_) {
      ItemPredicate itempredicate = ItemPredicate.deserialize(p_230241_1_.get("item"));
      return new UsedTotemTrigger.Instance(p_230241_2_, itempredicate);
   }

   public void trigger(ServerPlayerEntity player, ItemStack item) {
      this.func_235959_a_(player, (p_227409_1_) -> {
         return p_227409_1_.test(item);
      });
   }

   public static class Instance extends CriterionInstance {
      private final ItemPredicate item;

      public Instance(EntityPredicate.AndPredicate p_i232051_1_, ItemPredicate p_i232051_2_) {
         super(UsedTotemTrigger.ID, p_i232051_1_);
         this.item = p_i232051_2_;
      }

      public static UsedTotemTrigger.Instance usedTotem(IItemProvider p_203941_0_) {
         return new UsedTotemTrigger.Instance(EntityPredicate.AndPredicate.field_234582_a_, ItemPredicate.Builder.create().item(p_203941_0_).build());
      }

      public boolean test(ItemStack item) {
         return this.item.test(item);
      }

      public JsonObject func_230240_a_(ConditionArraySerializer p_230240_1_) {
         JsonObject jsonobject = super.func_230240_a_(p_230240_1_);
         jsonobject.add("item", this.item.serialize());
         return jsonobject;
      }
   }
}