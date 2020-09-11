package net.minecraft.advancements.criterion;

import com.google.gson.JsonObject;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.loot.ConditionArrayParser;
import net.minecraft.loot.ConditionArraySerializer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;

public class EntityHurtPlayerTrigger extends AbstractCriterionTrigger<EntityHurtPlayerTrigger.Instance> {
   private static final ResourceLocation ID = new ResourceLocation("entity_hurt_player");

   public ResourceLocation getId() {
      return ID;
   }

   public EntityHurtPlayerTrigger.Instance func_230241_b_(JsonObject p_230241_1_, EntityPredicate.AndPredicate p_230241_2_, ConditionArrayParser p_230241_3_) {
      DamagePredicate damagepredicate = DamagePredicate.deserialize(p_230241_1_.get("damage"));
      return new EntityHurtPlayerTrigger.Instance(p_230241_2_, damagepredicate);
   }

   public void trigger(ServerPlayerEntity player, DamageSource source, float amountDealt, float amountTaken, boolean wasBlocked) {
      this.func_235959_a_(player, (p_226603_5_) -> {
         return p_226603_5_.test(player, source, amountDealt, amountTaken, wasBlocked);
      });
   }

   public static class Instance extends CriterionInstance {
      private final DamagePredicate damage;

      public Instance(EntityPredicate.AndPredicate p_i231572_1_, DamagePredicate p_i231572_2_) {
         super(EntityHurtPlayerTrigger.ID, p_i231572_1_);
         this.damage = p_i231572_2_;
      }

      public static EntityHurtPlayerTrigger.Instance forDamage(DamagePredicate.Builder p_203921_0_) {
         return new EntityHurtPlayerTrigger.Instance(EntityPredicate.AndPredicate.field_234582_a_, p_203921_0_.build());
      }

      public boolean test(ServerPlayerEntity player, DamageSource source, float amountDealt, float amountTaken, boolean wasBlocked) {
         return this.damage.test(player, source, amountDealt, amountTaken, wasBlocked);
      }

      public JsonObject func_230240_a_(ConditionArraySerializer p_230240_1_) {
         JsonObject jsonobject = super.func_230240_a_(p_230240_1_);
         jsonobject.add("damage", this.damage.serialize());
         return jsonobject;
      }
   }
}