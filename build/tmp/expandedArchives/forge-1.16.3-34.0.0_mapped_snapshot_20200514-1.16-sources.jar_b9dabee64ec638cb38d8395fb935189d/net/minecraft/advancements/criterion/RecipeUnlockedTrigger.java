package net.minecraft.advancements.criterion;

import com.google.gson.JsonObject;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.loot.ConditionArrayParser;
import net.minecraft.loot.ConditionArraySerializer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;

public class RecipeUnlockedTrigger extends AbstractCriterionTrigger<RecipeUnlockedTrigger.Instance> {
   private static final ResourceLocation ID = new ResourceLocation("recipe_unlocked");

   public ResourceLocation getId() {
      return ID;
   }

   public RecipeUnlockedTrigger.Instance func_230241_b_(JsonObject p_230241_1_, EntityPredicate.AndPredicate p_230241_2_, ConditionArrayParser p_230241_3_) {
      ResourceLocation resourcelocation = new ResourceLocation(JSONUtils.getString(p_230241_1_, "recipe"));
      return new RecipeUnlockedTrigger.Instance(p_230241_2_, resourcelocation);
   }

   public void trigger(ServerPlayerEntity player, IRecipe<?> recipe) {
      this.func_235959_a_(player, (p_227018_1_) -> {
         return p_227018_1_.test(recipe);
      });
   }

   public static RecipeUnlockedTrigger.Instance func_235675_a_(ResourceLocation p_235675_0_) {
      return new RecipeUnlockedTrigger.Instance(EntityPredicate.AndPredicate.field_234582_a_, p_235675_0_);
   }

   public static class Instance extends CriterionInstance {
      private final ResourceLocation recipe;

      public Instance(EntityPredicate.AndPredicate p_i231865_1_, ResourceLocation p_i231865_2_) {
         super(RecipeUnlockedTrigger.ID, p_i231865_1_);
         this.recipe = p_i231865_2_;
      }

      public JsonObject func_230240_a_(ConditionArraySerializer p_230240_1_) {
         JsonObject jsonobject = super.func_230240_a_(p_230240_1_);
         jsonobject.addProperty("recipe", this.recipe.toString());
         return jsonobject;
      }

      public boolean test(IRecipe<?> recipe) {
         return this.recipe.equals(recipe.getId());
      }
   }
}