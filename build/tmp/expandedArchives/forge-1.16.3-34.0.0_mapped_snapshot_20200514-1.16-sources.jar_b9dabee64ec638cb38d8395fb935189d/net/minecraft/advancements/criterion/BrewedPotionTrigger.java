package net.minecraft.advancements.criterion;

import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import javax.annotation.Nullable;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.loot.ConditionArrayParser;
import net.minecraft.loot.ConditionArraySerializer;
import net.minecraft.potion.Potion;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

public class BrewedPotionTrigger extends AbstractCriterionTrigger<BrewedPotionTrigger.Instance> {
   private static final ResourceLocation ID = new ResourceLocation("brewed_potion");

   public ResourceLocation getId() {
      return ID;
   }

   public BrewedPotionTrigger.Instance func_230241_b_(JsonObject p_230241_1_, EntityPredicate.AndPredicate p_230241_2_, ConditionArrayParser p_230241_3_) {
      Potion potion = null;
      if (p_230241_1_.has("potion")) {
         ResourceLocation resourcelocation = new ResourceLocation(JSONUtils.getString(p_230241_1_, "potion"));
         potion = Registry.POTION.func_241873_b(resourcelocation).orElseThrow(() -> {
            return new JsonSyntaxException("Unknown potion '" + resourcelocation + "'");
         });
      }

      return new BrewedPotionTrigger.Instance(p_230241_2_, potion);
   }

   public void trigger(ServerPlayerEntity player, Potion potionIn) {
      this.func_235959_a_(player, (p_226301_1_) -> {
         return p_226301_1_.test(potionIn);
      });
   }

   public static class Instance extends CriterionInstance {
      private final Potion potion;

      public Instance(EntityPredicate.AndPredicate p_i231487_1_, @Nullable Potion p_i231487_2_) {
         super(BrewedPotionTrigger.ID, p_i231487_1_);
         this.potion = p_i231487_2_;
      }

      public static BrewedPotionTrigger.Instance brewedPotion() {
         return new BrewedPotionTrigger.Instance(EntityPredicate.AndPredicate.field_234582_a_, (Potion)null);
      }

      public boolean test(Potion potion) {
         return this.potion == null || this.potion == potion;
      }

      public JsonObject func_230240_a_(ConditionArraySerializer p_230240_1_) {
         JsonObject jsonobject = super.func_230240_a_(p_230240_1_);
         if (this.potion != null) {
            jsonobject.addProperty("potion", Registry.POTION.getKey(this.potion).toString());
         }

         return jsonobject;
      }
   }
}