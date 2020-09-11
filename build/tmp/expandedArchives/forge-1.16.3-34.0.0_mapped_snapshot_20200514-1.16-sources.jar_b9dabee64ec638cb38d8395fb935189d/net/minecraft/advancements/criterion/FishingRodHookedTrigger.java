package net.minecraft.advancements.criterion;

import com.google.gson.JsonObject;
import java.util.Collection;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.projectile.FishingBobberEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.ConditionArrayParser;
import net.minecraft.loot.ConditionArraySerializer;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootParameters;
import net.minecraft.util.ResourceLocation;

public class FishingRodHookedTrigger extends AbstractCriterionTrigger<FishingRodHookedTrigger.Instance> {
   private static final ResourceLocation ID = new ResourceLocation("fishing_rod_hooked");

   public ResourceLocation getId() {
      return ID;
   }

   public FishingRodHookedTrigger.Instance func_230241_b_(JsonObject p_230241_1_, EntityPredicate.AndPredicate p_230241_2_, ConditionArrayParser p_230241_3_) {
      ItemPredicate itempredicate = ItemPredicate.deserialize(p_230241_1_.get("rod"));
      EntityPredicate.AndPredicate entitypredicate$andpredicate = EntityPredicate.AndPredicate.func_234587_a_(p_230241_1_, "entity", p_230241_3_);
      ItemPredicate itempredicate1 = ItemPredicate.deserialize(p_230241_1_.get("item"));
      return new FishingRodHookedTrigger.Instance(p_230241_2_, itempredicate, entitypredicate$andpredicate, itempredicate1);
   }

   public void trigger(ServerPlayerEntity p_204820_1_, ItemStack p_204820_2_, FishingBobberEntity p_204820_3_, Collection<ItemStack> p_204820_4_) {
      LootContext lootcontext = EntityPredicate.func_234575_b_(p_204820_1_, (Entity)(p_204820_3_.func_234607_k_() != null ? p_204820_3_.func_234607_k_() : p_204820_3_));
      this.func_235959_a_(p_204820_1_, (p_234658_3_) -> {
         return p_234658_3_.func_234659_a_(p_204820_2_, lootcontext, p_204820_4_);
      });
   }

   public static class Instance extends CriterionInstance {
      private final ItemPredicate rod;
      private final EntityPredicate.AndPredicate entity;
      private final ItemPredicate item;

      public Instance(EntityPredicate.AndPredicate p_i231592_1_, ItemPredicate p_i231592_2_, EntityPredicate.AndPredicate p_i231592_3_, ItemPredicate p_i231592_4_) {
         super(FishingRodHookedTrigger.ID, p_i231592_1_);
         this.rod = p_i231592_2_;
         this.entity = p_i231592_3_;
         this.item = p_i231592_4_;
      }

      public static FishingRodHookedTrigger.Instance create(ItemPredicate p_204829_0_, EntityPredicate p_204829_1_, ItemPredicate p_204829_2_) {
         return new FishingRodHookedTrigger.Instance(EntityPredicate.AndPredicate.field_234582_a_, p_204829_0_, EntityPredicate.AndPredicate.func_234585_a_(p_204829_1_), p_204829_2_);
      }

      public boolean func_234659_a_(ItemStack p_234659_1_, LootContext p_234659_2_, Collection<ItemStack> p_234659_3_) {
         if (!this.rod.test(p_234659_1_)) {
            return false;
         } else if (!this.entity.func_234588_a_(p_234659_2_)) {
            return false;
         } else {
            if (this.item != ItemPredicate.ANY) {
               boolean flag = false;
               Entity entity = p_234659_2_.get(LootParameters.THIS_ENTITY);
               if (entity instanceof ItemEntity) {
                  ItemEntity itementity = (ItemEntity)entity;
                  if (this.item.test(itementity.getItem())) {
                     flag = true;
                  }
               }

               for(ItemStack itemstack : p_234659_3_) {
                  if (this.item.test(itemstack)) {
                     flag = true;
                     break;
                  }
               }

               if (!flag) {
                  return false;
               }
            }

            return true;
         }
      }

      public JsonObject func_230240_a_(ConditionArraySerializer p_230240_1_) {
         JsonObject jsonobject = super.func_230240_a_(p_230240_1_);
         jsonobject.add("rod", this.rod.serialize());
         jsonobject.add("entity", this.entity.func_234586_a_(p_230240_1_));
         jsonobject.add("item", this.item.serialize());
         return jsonobject;
      }
   }
}