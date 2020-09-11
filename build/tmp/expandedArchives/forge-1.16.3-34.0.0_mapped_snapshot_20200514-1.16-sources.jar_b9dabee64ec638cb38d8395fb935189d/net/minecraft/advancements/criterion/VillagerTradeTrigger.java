package net.minecraft.advancements.criterion;

import com.google.gson.JsonObject;
import net.minecraft.entity.merchant.villager.AbstractVillagerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.ConditionArrayParser;
import net.minecraft.loot.ConditionArraySerializer;
import net.minecraft.loot.LootContext;
import net.minecraft.util.ResourceLocation;

public class VillagerTradeTrigger extends AbstractCriterionTrigger<VillagerTradeTrigger.Instance> {
   private static final ResourceLocation ID = new ResourceLocation("villager_trade");

   public ResourceLocation getId() {
      return ID;
   }

   public VillagerTradeTrigger.Instance func_230241_b_(JsonObject p_230241_1_, EntityPredicate.AndPredicate p_230241_2_, ConditionArrayParser p_230241_3_) {
      EntityPredicate.AndPredicate entitypredicate$andpredicate = EntityPredicate.AndPredicate.func_234587_a_(p_230241_1_, "villager", p_230241_3_);
      ItemPredicate itempredicate = ItemPredicate.deserialize(p_230241_1_.get("item"));
      return new VillagerTradeTrigger.Instance(p_230241_2_, entitypredicate$andpredicate, itempredicate);
   }

   public void func_215114_a(ServerPlayerEntity p_215114_1_, AbstractVillagerEntity p_215114_2_, ItemStack p_215114_3_) {
      LootContext lootcontext = EntityPredicate.func_234575_b_(p_215114_1_, p_215114_2_);
      this.func_235959_a_(p_215114_1_, (p_227267_2_) -> {
         return p_227267_2_.func_236575_a_(lootcontext, p_215114_3_);
      });
   }

   public static class Instance extends CriterionInstance {
      private final EntityPredicate.AndPredicate villager;
      private final ItemPredicate item;

      public Instance(EntityPredicate.AndPredicate p_i232013_1_, EntityPredicate.AndPredicate p_i232013_2_, ItemPredicate p_i232013_3_) {
         super(VillagerTradeTrigger.ID, p_i232013_1_);
         this.villager = p_i232013_2_;
         this.item = p_i232013_3_;
      }

      public static VillagerTradeTrigger.Instance any() {
         return new VillagerTradeTrigger.Instance(EntityPredicate.AndPredicate.field_234582_a_, EntityPredicate.AndPredicate.field_234582_a_, ItemPredicate.ANY);
      }

      public boolean func_236575_a_(LootContext p_236575_1_, ItemStack p_236575_2_) {
         if (!this.villager.func_234588_a_(p_236575_1_)) {
            return false;
         } else {
            return this.item.test(p_236575_2_);
         }
      }

      public JsonObject func_230240_a_(ConditionArraySerializer p_230240_1_) {
         JsonObject jsonobject = super.func_230240_a_(p_230240_1_);
         jsonobject.add("item", this.item.serialize());
         jsonobject.add("villager", this.villager.func_234586_a_(p_230240_1_));
         return jsonobject;
      }
   }
}