package net.minecraft.advancements.criterion;

import com.google.gson.JsonObject;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.loot.ConditionArrayParser;
import net.minecraft.loot.ConditionArraySerializer;
import net.minecraft.loot.LootContext;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;

public class PlayerHurtEntityTrigger extends AbstractCriterionTrigger<PlayerHurtEntityTrigger.Instance> {
   private static final ResourceLocation ID = new ResourceLocation("player_hurt_entity");

   public ResourceLocation getId() {
      return ID;
   }

   public PlayerHurtEntityTrigger.Instance func_230241_b_(JsonObject p_230241_1_, EntityPredicate.AndPredicate p_230241_2_, ConditionArrayParser p_230241_3_) {
      DamagePredicate damagepredicate = DamagePredicate.deserialize(p_230241_1_.get("damage"));
      EntityPredicate.AndPredicate entitypredicate$andpredicate = EntityPredicate.AndPredicate.func_234587_a_(p_230241_1_, "entity", p_230241_3_);
      return new PlayerHurtEntityTrigger.Instance(p_230241_2_, damagepredicate, entitypredicate$andpredicate);
   }

   public void trigger(ServerPlayerEntity player, Entity entityIn, DamageSource source, float amountDealt, float amountTaken, boolean blocked) {
      LootContext lootcontext = EntityPredicate.func_234575_b_(player, entityIn);
      this.func_235959_a_(player, (p_226956_6_) -> {
         return p_226956_6_.func_235609_a_(player, lootcontext, source, amountDealt, amountTaken, blocked);
      });
   }

   public static class Instance extends CriterionInstance {
      private final DamagePredicate damage;
      private final EntityPredicate.AndPredicate entity;

      public Instance(EntityPredicate.AndPredicate p_i241190_1_, DamagePredicate p_i241190_2_, EntityPredicate.AndPredicate p_i241190_3_) {
         super(PlayerHurtEntityTrigger.ID, p_i241190_1_);
         this.damage = p_i241190_2_;
         this.entity = p_i241190_3_;
      }

      public static PlayerHurtEntityTrigger.Instance forDamage(DamagePredicate.Builder p_203936_0_) {
         return new PlayerHurtEntityTrigger.Instance(EntityPredicate.AndPredicate.field_234582_a_, p_203936_0_.build(), EntityPredicate.AndPredicate.field_234582_a_);
      }

      public boolean func_235609_a_(ServerPlayerEntity p_235609_1_, LootContext p_235609_2_, DamageSource p_235609_3_, float p_235609_4_, float p_235609_5_, boolean p_235609_6_) {
         if (!this.damage.test(p_235609_1_, p_235609_3_, p_235609_4_, p_235609_5_, p_235609_6_)) {
            return false;
         } else {
            return this.entity.func_234588_a_(p_235609_2_);
         }
      }

      public JsonObject func_230240_a_(ConditionArraySerializer p_230240_1_) {
         JsonObject jsonobject = super.func_230240_a_(p_230240_1_);
         jsonobject.add("damage", this.damage.serialize());
         jsonobject.add("entity", this.entity.func_234586_a_(p_230240_1_));
         return jsonobject;
      }
   }
}