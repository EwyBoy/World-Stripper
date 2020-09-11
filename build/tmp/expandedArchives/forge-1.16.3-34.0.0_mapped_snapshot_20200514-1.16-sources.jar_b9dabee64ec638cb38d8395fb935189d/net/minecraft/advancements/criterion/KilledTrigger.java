package net.minecraft.advancements.criterion;

import com.google.gson.JsonObject;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.loot.ConditionArrayParser;
import net.minecraft.loot.ConditionArraySerializer;
import net.minecraft.loot.LootContext;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;

public class KilledTrigger extends AbstractCriterionTrigger<KilledTrigger.Instance> {
   private final ResourceLocation id;

   public KilledTrigger(ResourceLocation id) {
      this.id = id;
   }

   public ResourceLocation getId() {
      return this.id;
   }

   public KilledTrigger.Instance func_230241_b_(JsonObject p_230241_1_, EntityPredicate.AndPredicate p_230241_2_, ConditionArrayParser p_230241_3_) {
      return new KilledTrigger.Instance(this.id, p_230241_2_, EntityPredicate.AndPredicate.func_234587_a_(p_230241_1_, "entity", p_230241_3_), DamageSourcePredicate.deserialize(p_230241_1_.get("killing_blow")));
   }

   public void trigger(ServerPlayerEntity player, Entity entity, DamageSource source) {
      LootContext lootcontext = EntityPredicate.func_234575_b_(player, entity);
      this.func_235959_a_(player, (p_226846_3_) -> {
         return p_226846_3_.func_235050_a_(player, lootcontext, source);
      });
   }

   public static class Instance extends CriterionInstance {
      private final EntityPredicate.AndPredicate entity;
      private final DamageSourcePredicate killingBlow;

      public Instance(ResourceLocation p_i231630_1_, EntityPredicate.AndPredicate p_i231630_2_, EntityPredicate.AndPredicate p_i231630_3_, DamageSourcePredicate p_i231630_4_) {
         super(p_i231630_1_, p_i231630_2_);
         this.entity = p_i231630_3_;
         this.killingBlow = p_i231630_4_;
      }

      public static KilledTrigger.Instance playerKilledEntity(EntityPredicate.Builder p_203928_0_) {
         return new KilledTrigger.Instance(CriteriaTriggers.PLAYER_KILLED_ENTITY.id, EntityPredicate.AndPredicate.field_234582_a_, EntityPredicate.AndPredicate.func_234585_a_(p_203928_0_.build()), DamageSourcePredicate.ANY);
      }

      public static KilledTrigger.Instance playerKilledEntity() {
         return new KilledTrigger.Instance(CriteriaTriggers.PLAYER_KILLED_ENTITY.id, EntityPredicate.AndPredicate.field_234582_a_, EntityPredicate.AndPredicate.field_234582_a_, DamageSourcePredicate.ANY);
      }

      public static KilledTrigger.Instance playerKilledEntity(EntityPredicate.Builder p_203929_0_, DamageSourcePredicate.Builder p_203929_1_) {
         return new KilledTrigger.Instance(CriteriaTriggers.PLAYER_KILLED_ENTITY.id, EntityPredicate.AndPredicate.field_234582_a_, EntityPredicate.AndPredicate.func_234585_a_(p_203929_0_.build()), p_203929_1_.build());
      }

      public static KilledTrigger.Instance entityKilledPlayer() {
         return new KilledTrigger.Instance(CriteriaTriggers.ENTITY_KILLED_PLAYER.id, EntityPredicate.AndPredicate.field_234582_a_, EntityPredicate.AndPredicate.field_234582_a_, DamageSourcePredicate.ANY);
      }

      public boolean func_235050_a_(ServerPlayerEntity p_235050_1_, LootContext p_235050_2_, DamageSource p_235050_3_) {
         return !this.killingBlow.test(p_235050_1_, p_235050_3_) ? false : this.entity.func_234588_a_(p_235050_2_);
      }

      public JsonObject func_230240_a_(ConditionArraySerializer p_230240_1_) {
         JsonObject jsonobject = super.func_230240_a_(p_230240_1_);
         jsonobject.add("entity", this.entity.func_234586_a_(p_230240_1_));
         jsonobject.add("killing_blow", this.killingBlow.serialize());
         return jsonobject;
      }
   }
}