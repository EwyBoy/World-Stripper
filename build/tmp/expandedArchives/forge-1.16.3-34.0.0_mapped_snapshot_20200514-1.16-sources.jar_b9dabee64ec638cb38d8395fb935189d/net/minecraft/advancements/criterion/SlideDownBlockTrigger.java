package net.minecraft.advancements.criterion;

import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.loot.ConditionArrayParser;
import net.minecraft.loot.ConditionArraySerializer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

public class SlideDownBlockTrigger extends AbstractCriterionTrigger<SlideDownBlockTrigger.Instance> {
   private static final ResourceLocation field_227147_a_ = new ResourceLocation("slide_down_block");

   public ResourceLocation getId() {
      return field_227147_a_;
   }

   public SlideDownBlockTrigger.Instance func_230241_b_(JsonObject p_230241_1_, EntityPredicate.AndPredicate p_230241_2_, ConditionArrayParser p_230241_3_) {
      Block block = func_227150_a_(p_230241_1_);
      StatePropertiesPredicate statepropertiespredicate = StatePropertiesPredicate.deserializeProperties(p_230241_1_.get("state"));
      if (block != null) {
         statepropertiespredicate.forEachNotPresent(block.getStateContainer(), (p_227148_1_) -> {
            throw new JsonSyntaxException("Block " + block + " has no property " + p_227148_1_);
         });
      }

      return new SlideDownBlockTrigger.Instance(p_230241_2_, block, statepropertiespredicate);
   }

   @Nullable
   private static Block func_227150_a_(JsonObject p_227150_0_) {
      if (p_227150_0_.has("block")) {
         ResourceLocation resourcelocation = new ResourceLocation(JSONUtils.getString(p_227150_0_, "block"));
         return Registry.BLOCK.func_241873_b(resourcelocation).orElseThrow(() -> {
            return new JsonSyntaxException("Unknown block type '" + resourcelocation + "'");
         });
      } else {
         return null;
      }
   }

   public void func_227152_a_(ServerPlayerEntity p_227152_1_, BlockState p_227152_2_) {
      this.func_235959_a_(p_227152_1_, (p_227149_1_) -> {
         return p_227149_1_.func_227157_a_(p_227152_2_);
      });
   }

   public static class Instance extends CriterionInstance {
      private final Block field_227154_a_;
      private final StatePropertiesPredicate field_227155_b_;

      public Instance(EntityPredicate.AndPredicate p_i231896_1_, @Nullable Block p_i231896_2_, StatePropertiesPredicate p_i231896_3_) {
         super(SlideDownBlockTrigger.field_227147_a_, p_i231896_1_);
         this.field_227154_a_ = p_i231896_2_;
         this.field_227155_b_ = p_i231896_3_;
      }

      public static SlideDownBlockTrigger.Instance func_227156_a_(Block p_227156_0_) {
         return new SlideDownBlockTrigger.Instance(EntityPredicate.AndPredicate.field_234582_a_, p_227156_0_, StatePropertiesPredicate.EMPTY);
      }

      public JsonObject func_230240_a_(ConditionArraySerializer p_230240_1_) {
         JsonObject jsonobject = super.func_230240_a_(p_230240_1_);
         if (this.field_227154_a_ != null) {
            jsonobject.addProperty("block", Registry.BLOCK.getKey(this.field_227154_a_).toString());
         }

         jsonobject.add("state", this.field_227155_b_.toJsonElement());
         return jsonobject;
      }

      public boolean func_227157_a_(BlockState p_227157_1_) {
         if (this.field_227154_a_ != null && !p_227157_1_.isIn(this.field_227154_a_)) {
            return false;
         } else {
            return this.field_227155_b_.matches(p_227157_1_);
         }
      }
   }
}