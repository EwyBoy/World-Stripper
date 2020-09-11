package net.minecraft.advancements.criterion;

import com.google.gson.JsonObject;
import net.minecraft.advancements.ICriterionInstance;
import net.minecraft.advancements.ICriterionTrigger;
import net.minecraft.advancements.PlayerAdvancements;
import net.minecraft.loot.ConditionArrayParser;
import net.minecraft.loot.ConditionArraySerializer;
import net.minecraft.util.ResourceLocation;

public class ImpossibleTrigger implements ICriterionTrigger<ImpossibleTrigger.Instance> {
   private static final ResourceLocation ID = new ResourceLocation("impossible");

   public ResourceLocation getId() {
      return ID;
   }

   public void addListener(PlayerAdvancements playerAdvancementsIn, ICriterionTrigger.Listener<ImpossibleTrigger.Instance> listener) {
   }

   public void removeListener(PlayerAdvancements playerAdvancementsIn, ICriterionTrigger.Listener<ImpossibleTrigger.Instance> listener) {
   }

   public void removeAllListeners(PlayerAdvancements playerAdvancementsIn) {
   }

   public ImpossibleTrigger.Instance func_230307_a_(JsonObject p_230307_1_, ConditionArrayParser p_230307_2_) {
      return new ImpossibleTrigger.Instance();
   }

   public static class Instance implements ICriterionInstance {
      public ResourceLocation getId() {
         return ImpossibleTrigger.ID;
      }

      public JsonObject func_230240_a_(ConditionArraySerializer p_230240_1_) {
         return new JsonObject();
      }
   }
}