package net.minecraft.advancements.criterion;

import com.google.gson.JsonObject;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.loot.ConditionArrayParser;
import net.minecraft.loot.ConditionArraySerializer;
import net.minecraft.loot.LootContext;
import net.minecraft.util.ResourceLocation;

public class SummonedEntityTrigger extends AbstractCriterionTrigger<SummonedEntityTrigger.Instance> {
   private static final ResourceLocation ID = new ResourceLocation("summoned_entity");

   public ResourceLocation getId() {
      return ID;
   }

   public SummonedEntityTrigger.Instance func_230241_b_(JsonObject p_230241_1_, EntityPredicate.AndPredicate p_230241_2_, ConditionArrayParser p_230241_3_) {
      EntityPredicate.AndPredicate entitypredicate$andpredicate = EntityPredicate.AndPredicate.func_234587_a_(p_230241_1_, "entity", p_230241_3_);
      return new SummonedEntityTrigger.Instance(p_230241_2_, entitypredicate$andpredicate);
   }

   public void trigger(ServerPlayerEntity player, Entity entity) {
      LootContext lootcontext = EntityPredicate.func_234575_b_(player, entity);
      this.func_235959_a_(player, (p_227229_1_) -> {
         return p_227229_1_.func_236273_a_(lootcontext);
      });
   }

   public static class Instance extends CriterionInstance {
      private final EntityPredicate.AndPredicate entity;

      public Instance(EntityPredicate.AndPredicate p_i231938_1_, EntityPredicate.AndPredicate p_i231938_2_) {
         super(SummonedEntityTrigger.ID, p_i231938_1_);
         this.entity = p_i231938_2_;
      }

      public static SummonedEntityTrigger.Instance summonedEntity(EntityPredicate.Builder p_203937_0_) {
         return new SummonedEntityTrigger.Instance(EntityPredicate.AndPredicate.field_234582_a_, EntityPredicate.AndPredicate.func_234585_a_(p_203937_0_.build()));
      }

      public boolean func_236273_a_(LootContext p_236273_1_) {
         return this.entity.func_234588_a_(p_236273_1_);
      }

      public JsonObject func_230240_a_(ConditionArraySerializer p_230240_1_) {
         JsonObject jsonobject = super.func_230240_a_(p_230240_1_);
         jsonobject.add("entity", this.entity.func_234586_a_(p_230240_1_));
         return jsonobject;
      }
   }
}