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

public class EnterBlockTrigger extends AbstractCriterionTrigger<EnterBlockTrigger.Instance> {
   private static final ResourceLocation ID = new ResourceLocation("enter_block");

   public ResourceLocation getId() {
      return ID;
   }

   public EnterBlockTrigger.Instance func_230241_b_(JsonObject p_230241_1_, EntityPredicate.AndPredicate p_230241_2_, ConditionArrayParser p_230241_3_) {
      Block block = func_226550_a_(p_230241_1_);
      StatePropertiesPredicate statepropertiespredicate = StatePropertiesPredicate.deserializeProperties(p_230241_1_.get("state"));
      if (block != null) {
         statepropertiespredicate.forEachNotPresent(block.getStateContainer(), (p_226548_1_) -> {
            throw new JsonSyntaxException("Block " + block + " has no property " + p_226548_1_);
         });
      }

      return new EnterBlockTrigger.Instance(p_230241_2_, block, statepropertiespredicate);
   }

   @Nullable
   private static Block func_226550_a_(JsonObject p_226550_0_) {
      if (p_226550_0_.has("block")) {
         ResourceLocation resourcelocation = new ResourceLocation(JSONUtils.getString(p_226550_0_, "block"));
         return Registry.BLOCK.func_241873_b(resourcelocation).orElseThrow(() -> {
            return new JsonSyntaxException("Unknown block type '" + resourcelocation + "'");
         });
      } else {
         return null;
      }
   }

   public void trigger(ServerPlayerEntity player, BlockState state) {
      this.func_235959_a_(player, (p_226549_1_) -> {
         return p_226549_1_.test(state);
      });
   }

   public static class Instance extends CriterionInstance {
      private final Block block;
      private final StatePropertiesPredicate properties;

      public Instance(EntityPredicate.AndPredicate p_i231560_1_, @Nullable Block p_i231560_2_, StatePropertiesPredicate p_i231560_3_) {
         super(EnterBlockTrigger.ID, p_i231560_1_);
         this.block = p_i231560_2_;
         this.properties = p_i231560_3_;
      }

      public static EnterBlockTrigger.Instance forBlock(Block p_203920_0_) {
         return new EnterBlockTrigger.Instance(EntityPredicate.AndPredicate.field_234582_a_, p_203920_0_, StatePropertiesPredicate.EMPTY);
      }

      public JsonObject func_230240_a_(ConditionArraySerializer p_230240_1_) {
         JsonObject jsonobject = super.func_230240_a_(p_230240_1_);
         if (this.block != null) {
            jsonobject.addProperty("block", Registry.BLOCK.getKey(this.block).toString());
         }

         jsonobject.add("state", this.properties.toJsonElement());
         return jsonobject;
      }

      public boolean test(BlockState state) {
         if (this.block != null && !state.isIn(this.block)) {
            return false;
         } else {
            return this.properties.matches(state);
         }
      }
   }
}