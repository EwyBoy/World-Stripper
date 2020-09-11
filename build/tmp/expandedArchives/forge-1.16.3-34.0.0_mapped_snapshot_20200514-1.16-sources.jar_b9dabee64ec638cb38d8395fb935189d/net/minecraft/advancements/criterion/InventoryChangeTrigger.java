package net.minecraft.advancements.criterion;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.List;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.ConditionArrayParser;
import net.minecraft.loot.ConditionArraySerializer;
import net.minecraft.potion.Potion;
import net.minecraft.tags.ITag;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;

public class InventoryChangeTrigger extends AbstractCriterionTrigger<InventoryChangeTrigger.Instance> {
   private static final ResourceLocation ID = new ResourceLocation("inventory_changed");

   public ResourceLocation getId() {
      return ID;
   }

   public InventoryChangeTrigger.Instance func_230241_b_(JsonObject p_230241_1_, EntityPredicate.AndPredicate p_230241_2_, ConditionArrayParser p_230241_3_) {
      JsonObject jsonobject = JSONUtils.getJsonObject(p_230241_1_, "slots", new JsonObject());
      MinMaxBounds.IntBound minmaxbounds$intbound = MinMaxBounds.IntBound.fromJson(jsonobject.get("occupied"));
      MinMaxBounds.IntBound minmaxbounds$intbound1 = MinMaxBounds.IntBound.fromJson(jsonobject.get("full"));
      MinMaxBounds.IntBound minmaxbounds$intbound2 = MinMaxBounds.IntBound.fromJson(jsonobject.get("empty"));
      ItemPredicate[] aitempredicate = ItemPredicate.deserializeArray(p_230241_1_.get("items"));
      return new InventoryChangeTrigger.Instance(p_230241_2_, minmaxbounds$intbound, minmaxbounds$intbound1, minmaxbounds$intbound2, aitempredicate);
   }

   public void func_234803_a_(ServerPlayerEntity p_234803_1_, PlayerInventory p_234803_2_, ItemStack p_234803_3_) {
      int i = 0;
      int j = 0;
      int k = 0;

      for(int l = 0; l < p_234803_2_.getSizeInventory(); ++l) {
         ItemStack itemstack = p_234803_2_.getStackInSlot(l);
         if (itemstack.isEmpty()) {
            ++j;
         } else {
            ++k;
            if (itemstack.getCount() >= itemstack.getMaxStackSize()) {
               ++i;
            }
         }
      }

      this.func_234804_a_(p_234803_1_, p_234803_2_, p_234803_3_, i, j, k);
   }

   private void func_234804_a_(ServerPlayerEntity p_234804_1_, PlayerInventory p_234804_2_, ItemStack p_234804_3_, int p_234804_4_, int p_234804_5_, int p_234804_6_) {
      this.func_235959_a_(p_234804_1_, (p_234802_5_) -> {
         return p_234802_5_.func_234805_a_(p_234804_2_, p_234804_3_, p_234804_4_, p_234804_5_, p_234804_6_);
      });
   }

   public static class Instance extends CriterionInstance {
      private final MinMaxBounds.IntBound occupied;
      private final MinMaxBounds.IntBound full;
      private final MinMaxBounds.IntBound empty;
      private final ItemPredicate[] items;

      public Instance(EntityPredicate.AndPredicate p_i231597_1_, MinMaxBounds.IntBound p_i231597_2_, MinMaxBounds.IntBound p_i231597_3_, MinMaxBounds.IntBound p_i231597_4_, ItemPredicate[] p_i231597_5_) {
         super(InventoryChangeTrigger.ID, p_i231597_1_);
         this.occupied = p_i231597_2_;
         this.full = p_i231597_3_;
         this.empty = p_i231597_4_;
         this.items = p_i231597_5_;
      }

      public static InventoryChangeTrigger.Instance forItems(ItemPredicate... p_203923_0_) {
         return new InventoryChangeTrigger.Instance(EntityPredicate.AndPredicate.field_234582_a_, MinMaxBounds.IntBound.UNBOUNDED, MinMaxBounds.IntBound.UNBOUNDED, MinMaxBounds.IntBound.UNBOUNDED, p_203923_0_);
      }

      public static InventoryChangeTrigger.Instance forItems(IItemProvider... p_203922_0_) {
         ItemPredicate[] aitempredicate = new ItemPredicate[p_203922_0_.length];

         for(int i = 0; i < p_203922_0_.length; ++i) {
            aitempredicate[i] = new ItemPredicate((ITag<Item>)null, p_203922_0_[i].asItem(), MinMaxBounds.IntBound.UNBOUNDED, MinMaxBounds.IntBound.UNBOUNDED, EnchantmentPredicate.field_226534_b_, EnchantmentPredicate.field_226534_b_, (Potion)null, NBTPredicate.ANY);
         }

         return forItems(aitempredicate);
      }

      public JsonObject func_230240_a_(ConditionArraySerializer p_230240_1_) {
         JsonObject jsonobject = super.func_230240_a_(p_230240_1_);
         if (!this.occupied.isUnbounded() || !this.full.isUnbounded() || !this.empty.isUnbounded()) {
            JsonObject jsonobject1 = new JsonObject();
            jsonobject1.add("occupied", this.occupied.serialize());
            jsonobject1.add("full", this.full.serialize());
            jsonobject1.add("empty", this.empty.serialize());
            jsonobject.add("slots", jsonobject1);
         }

         if (this.items.length > 0) {
            JsonArray jsonarray = new JsonArray();

            for(ItemPredicate itempredicate : this.items) {
               jsonarray.add(itempredicate.serialize());
            }

            jsonobject.add("items", jsonarray);
         }

         return jsonobject;
      }

      public boolean func_234805_a_(PlayerInventory p_234805_1_, ItemStack p_234805_2_, int p_234805_3_, int p_234805_4_, int p_234805_5_) {
         if (!this.full.test(p_234805_3_)) {
            return false;
         } else if (!this.empty.test(p_234805_4_)) {
            return false;
         } else if (!this.occupied.test(p_234805_5_)) {
            return false;
         } else {
            int i = this.items.length;
            if (i == 0) {
               return true;
            } else if (i != 1) {
               List<ItemPredicate> list = new ObjectArrayList<>(this.items);
               int j = p_234805_1_.getSizeInventory();

               for(int k = 0; k < j; ++k) {
                  if (list.isEmpty()) {
                     return true;
                  }

                  ItemStack itemstack = p_234805_1_.getStackInSlot(k);
                  if (!itemstack.isEmpty()) {
                     list.removeIf((p_234806_1_) -> {
                        return p_234806_1_.test(itemstack);
                     });
                  }
               }

               return list.isEmpty();
            } else {
               return !p_234805_2_.isEmpty() && this.items[0].test(p_234805_2_);
            }
         }
      }
   }
}