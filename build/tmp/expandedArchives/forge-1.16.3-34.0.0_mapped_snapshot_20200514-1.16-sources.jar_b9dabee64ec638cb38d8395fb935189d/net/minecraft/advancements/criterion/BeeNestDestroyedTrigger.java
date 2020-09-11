package net.minecraft.advancements.criterion;

import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.ConditionArrayParser;
import net.minecraft.loot.ConditionArraySerializer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

public class BeeNestDestroyedTrigger extends AbstractCriterionTrigger<BeeNestDestroyedTrigger.Instance> {
   private static final ResourceLocation ID = new ResourceLocation("bee_nest_destroyed");

   public ResourceLocation getId() {
      return ID;
   }

   public BeeNestDestroyedTrigger.Instance func_230241_b_(JsonObject p_230241_1_, EntityPredicate.AndPredicate p_230241_2_, ConditionArrayParser p_230241_3_) {
      Block block = func_226221_a_(p_230241_1_);
      ItemPredicate itempredicate = ItemPredicate.deserialize(p_230241_1_.get("item"));
      MinMaxBounds.IntBound minmaxbounds$intbound = MinMaxBounds.IntBound.fromJson(p_230241_1_.get("num_bees_inside"));
      return new BeeNestDestroyedTrigger.Instance(p_230241_2_, block, itempredicate, minmaxbounds$intbound);
   }

   @Nullable
   private static Block func_226221_a_(JsonObject p_226221_0_) {
      if (p_226221_0_.has("block")) {
         ResourceLocation resourcelocation = new ResourceLocation(JSONUtils.getString(p_226221_0_, "block"));
         return Registry.BLOCK.func_241873_b(resourcelocation).orElseThrow(() -> {
            return new JsonSyntaxException("Unknown block type '" + resourcelocation + "'");
         });
      } else {
         return null;
      }
   }

   public void test(ServerPlayerEntity p_226223_1_, Block p_226223_2_, ItemStack p_226223_3_, int p_226223_4_) {
      this.func_235959_a_(p_226223_1_, (p_226220_3_) -> {
         return p_226220_3_.func_226228_a_(p_226223_2_, p_226223_3_, p_226223_4_);
      });
   }

   public static class Instance extends CriterionInstance {
      @Nullable
      private final Block field_226225_a_;
      private final ItemPredicate field_226226_b_;
      private final MinMaxBounds.IntBound field_226227_c_;

      public Instance(EntityPredicate.AndPredicate p_i231471_1_, @Nullable Block p_i231471_2_, ItemPredicate p_i231471_3_, MinMaxBounds.IntBound p_i231471_4_) {
         super(BeeNestDestroyedTrigger.ID, p_i231471_1_);
         this.field_226225_a_ = p_i231471_2_;
         this.field_226226_b_ = p_i231471_3_;
         this.field_226227_c_ = p_i231471_4_;
      }

      public static BeeNestDestroyedTrigger.Instance func_226229_a_(Block p_226229_0_, ItemPredicate.Builder p_226229_1_, MinMaxBounds.IntBound p_226229_2_) {
         return new BeeNestDestroyedTrigger.Instance(EntityPredicate.AndPredicate.field_234582_a_, p_226229_0_, p_226229_1_.build(), p_226229_2_);
      }

      public boolean func_226228_a_(Block p_226228_1_, ItemStack p_226228_2_, int p_226228_3_) {
         if (this.field_226225_a_ != null && p_226228_1_ != this.field_226225_a_) {
            return false;
         } else {
            return !this.field_226226_b_.test(p_226228_2_) ? false : this.field_226227_c_.test(p_226228_3_);
         }
      }

      public JsonObject func_230240_a_(ConditionArraySerializer p_230240_1_) {
         JsonObject jsonobject = super.func_230240_a_(p_230240_1_);
         if (this.field_226225_a_ != null) {
            jsonobject.addProperty("block", Registry.BLOCK.getKey(this.field_226225_a_).toString());
         }

         jsonobject.add("item", this.field_226226_b_.serialize());
         jsonobject.add("num_bees_inside", this.field_226227_c_.serialize());
         return jsonobject;
      }
   }
}