package net.minecraft.data;

import com.google.gson.JsonObject;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.ICriterionInstance;
import net.minecraft.advancements.IRequirementsStrategy;
import net.minecraft.advancements.criterion.RecipeUnlockedTrigger;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

public class SmithingRecipeBuilder {
   private final Ingredient field_240497_a_;
   private final Ingredient field_240498_b_;
   private final Item field_240499_c_;
   private final Advancement.Builder field_240500_d_ = Advancement.Builder.builder();
   private final IRecipeSerializer<?> field_240501_e_;

   public SmithingRecipeBuilder(IRecipeSerializer<?> p_i232549_1_, Ingredient p_i232549_2_, Ingredient p_i232549_3_, Item p_i232549_4_) {
      this.field_240501_e_ = p_i232549_1_;
      this.field_240497_a_ = p_i232549_2_;
      this.field_240498_b_ = p_i232549_3_;
      this.field_240499_c_ = p_i232549_4_;
   }

   public static SmithingRecipeBuilder func_240502_a_(Ingredient p_240502_0_, Ingredient p_240502_1_, Item p_240502_2_) {
      return new SmithingRecipeBuilder(IRecipeSerializer.field_234826_u_, p_240502_0_, p_240502_1_, p_240502_2_);
   }

   public SmithingRecipeBuilder func_240503_a_(String p_240503_1_, ICriterionInstance p_240503_2_) {
      this.field_240500_d_.withCriterion(p_240503_1_, p_240503_2_);
      return this;
   }

   public void func_240504_a_(Consumer<IFinishedRecipe> p_240504_1_, String p_240504_2_) {
      this.func_240505_a_(p_240504_1_, new ResourceLocation(p_240504_2_));
   }

   public void func_240505_a_(Consumer<IFinishedRecipe> p_240505_1_, ResourceLocation p_240505_2_) {
      this.func_240506_a_(p_240505_2_);
      this.field_240500_d_.withParentId(new ResourceLocation("recipes/root")).withCriterion("has_the_recipe", RecipeUnlockedTrigger.func_235675_a_(p_240505_2_)).withRewards(AdvancementRewards.Builder.recipe(p_240505_2_)).withRequirementsStrategy(IRequirementsStrategy.OR);
      p_240505_1_.accept(new SmithingRecipeBuilder.Result(p_240505_2_, this.field_240501_e_, this.field_240497_a_, this.field_240498_b_, this.field_240499_c_, this.field_240500_d_, new ResourceLocation(p_240505_2_.getNamespace(), "recipes/" + this.field_240499_c_.getGroup().getPath() + "/" + p_240505_2_.getPath())));
   }

   private void func_240506_a_(ResourceLocation p_240506_1_) {
      if (this.field_240500_d_.getCriteria().isEmpty()) {
         throw new IllegalStateException("No way of obtaining recipe " + p_240506_1_);
      }
   }

   public static class Result implements IFinishedRecipe {
      private final ResourceLocation field_240507_a_;
      private final Ingredient field_240508_b_;
      private final Ingredient field_240509_c_;
      private final Item field_240510_d_;
      private final Advancement.Builder field_240511_e_;
      private final ResourceLocation field_240512_f_;
      private final IRecipeSerializer<?> field_240513_g_;

      public Result(ResourceLocation p_i232550_1_, IRecipeSerializer<?> p_i232550_2_, Ingredient p_i232550_3_, Ingredient p_i232550_4_, Item p_i232550_5_, Advancement.Builder p_i232550_6_, ResourceLocation p_i232550_7_) {
         this.field_240507_a_ = p_i232550_1_;
         this.field_240513_g_ = p_i232550_2_;
         this.field_240508_b_ = p_i232550_3_;
         this.field_240509_c_ = p_i232550_4_;
         this.field_240510_d_ = p_i232550_5_;
         this.field_240511_e_ = p_i232550_6_;
         this.field_240512_f_ = p_i232550_7_;
      }

      public void serialize(JsonObject json) {
         json.add("base", this.field_240508_b_.serialize());
         json.add("addition", this.field_240509_c_.serialize());
         JsonObject jsonobject = new JsonObject();
         jsonobject.addProperty("item", Registry.ITEM.getKey(this.field_240510_d_).toString());
         json.add("result", jsonobject);
      }

      /**
       * Gets the ID for the recipe.
       */
      public ResourceLocation getID() {
         return this.field_240507_a_;
      }

      public IRecipeSerializer<?> getSerializer() {
         return this.field_240513_g_;
      }

      /**
       * Gets the JSON for the advancement that unlocks this recipe. Null if there is no advancement.
       */
      @Nullable
      public JsonObject getAdvancementJson() {
         return this.field_240511_e_.serialize();
      }

      /**
       * Gets the ID for the advancement associated with this recipe. Should not be null if {@link #getAdvancementJson}
       * is non-null.
       */
      @Nullable
      public ResourceLocation getAdvancementID() {
         return this.field_240512_f_;
      }
   }
}