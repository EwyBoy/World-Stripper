package net.minecraft.advancements.criterion;

import com.google.gson.JsonObject;
import net.minecraft.advancements.ICriterionInstance;
import net.minecraft.loot.ConditionArraySerializer;
import net.minecraft.util.ResourceLocation;

public abstract class CriterionInstance implements ICriterionInstance {
   private final ResourceLocation criterion;
   private final EntityPredicate.AndPredicate field_233382_b_;

   public CriterionInstance(ResourceLocation p_i231464_1_, EntityPredicate.AndPredicate p_i231464_2_) {
      this.criterion = p_i231464_1_;
      this.field_233382_b_ = p_i231464_2_;
   }

   public ResourceLocation getId() {
      return this.criterion;
   }

   protected EntityPredicate.AndPredicate func_233383_b_() {
      return this.field_233382_b_;
   }

   public JsonObject func_230240_a_(ConditionArraySerializer p_230240_1_) {
      JsonObject jsonobject = new JsonObject();
      jsonobject.add("player", this.field_233382_b_.func_234586_a_(p_230240_1_));
      return jsonobject;
   }

   public String toString() {
      return "AbstractCriterionInstance{criterion=" + this.criterion + '}';
   }
}