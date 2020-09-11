package net.minecraft.advancements.criterion;

import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.ConditionArrayParser;
import net.minecraft.loot.ConditionArraySerializer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.server.ServerWorld;

public class PlacedBlockTrigger extends AbstractCriterionTrigger<PlacedBlockTrigger.Instance> {
   private static final ResourceLocation ID = new ResourceLocation("placed_block");

   public ResourceLocation getId() {
      return ID;
   }

   public PlacedBlockTrigger.Instance func_230241_b_(JsonObject p_230241_1_, EntityPredicate.AndPredicate p_230241_2_, ConditionArrayParser p_230241_3_) {
      Block block = func_226950_a_(p_230241_1_);
      StatePropertiesPredicate statepropertiespredicate = StatePropertiesPredicate.deserializeProperties(p_230241_1_.get("state"));
      if (block != null) {
         statepropertiespredicate.forEachNotPresent(block.getStateContainer(), (p_226948_1_) -> {
            throw new JsonSyntaxException("Block " + block + " has no property " + p_226948_1_ + ":");
         });
      }

      LocationPredicate locationpredicate = LocationPredicate.deserialize(p_230241_1_.get("location"));
      ItemPredicate itempredicate = ItemPredicate.deserialize(p_230241_1_.get("item"));
      return new PlacedBlockTrigger.Instance(p_230241_2_, block, statepropertiespredicate, locationpredicate, itempredicate);
   }

   @Nullable
   private static Block func_226950_a_(JsonObject p_226950_0_) {
      if (p_226950_0_.has("block")) {
         ResourceLocation resourcelocation = new ResourceLocation(JSONUtils.getString(p_226950_0_, "block"));
         return Registry.BLOCK.func_241873_b(resourcelocation).orElseThrow(() -> {
            return new JsonSyntaxException("Unknown block type '" + resourcelocation + "'");
         });
      } else {
         return null;
      }
   }

   public void trigger(ServerPlayerEntity player, BlockPos pos, ItemStack item) {
      BlockState blockstate = player.getServerWorld().getBlockState(pos);
      this.func_235959_a_(player, (p_226949_4_) -> {
         return p_226949_4_.test(blockstate, pos, player.getServerWorld(), item);
      });
   }

   public static class Instance extends CriterionInstance {
      private final Block block;
      private final StatePropertiesPredicate properties;
      private final LocationPredicate location;
      private final ItemPredicate item;

      public Instance(EntityPredicate.AndPredicate p_i231810_1_, @Nullable Block p_i231810_2_, StatePropertiesPredicate p_i231810_3_, LocationPredicate p_i231810_4_, ItemPredicate p_i231810_5_) {
         super(PlacedBlockTrigger.ID, p_i231810_1_);
         this.block = p_i231810_2_;
         this.properties = p_i231810_3_;
         this.location = p_i231810_4_;
         this.item = p_i231810_5_;
      }

      public static PlacedBlockTrigger.Instance placedBlock(Block p_203934_0_) {
         return new PlacedBlockTrigger.Instance(EntityPredicate.AndPredicate.field_234582_a_, p_203934_0_, StatePropertiesPredicate.EMPTY, LocationPredicate.ANY, ItemPredicate.ANY);
      }

      public boolean test(BlockState state, BlockPos pos, ServerWorld world, ItemStack item) {
         if (this.block != null && !state.isIn(this.block)) {
            return false;
         } else if (!this.properties.matches(state)) {
            return false;
         } else if (!this.location.test(world, (float)pos.getX(), (float)pos.getY(), (float)pos.getZ())) {
            return false;
         } else {
            return this.item.test(item);
         }
      }

      public JsonObject func_230240_a_(ConditionArraySerializer p_230240_1_) {
         JsonObject jsonobject = super.func_230240_a_(p_230240_1_);
         if (this.block != null) {
            jsonobject.addProperty("block", Registry.BLOCK.getKey(this.block).toString());
         }

         jsonobject.add("state", this.properties.toJsonElement());
         jsonobject.add("location", this.location.serialize());
         jsonobject.add("item", this.item.serialize());
         return jsonobject;
      }
   }
}