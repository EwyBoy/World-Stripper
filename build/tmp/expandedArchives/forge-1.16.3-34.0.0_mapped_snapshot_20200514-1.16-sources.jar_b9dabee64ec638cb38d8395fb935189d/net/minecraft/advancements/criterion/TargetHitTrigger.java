package net.minecraft.advancements.criterion;

import com.google.gson.JsonObject;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.loot.ConditionArrayParser;
import net.minecraft.loot.ConditionArraySerializer;
import net.minecraft.loot.LootContext;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3d;

public class TargetHitTrigger extends AbstractCriterionTrigger<TargetHitTrigger.Instance> {
   private static final ResourceLocation field_236348_a_ = new ResourceLocation("target_hit");

   public ResourceLocation getId() {
      return field_236348_a_;
   }

   public TargetHitTrigger.Instance func_230241_b_(JsonObject p_230241_1_, EntityPredicate.AndPredicate p_230241_2_, ConditionArrayParser p_230241_3_) {
      MinMaxBounds.IntBound minmaxbounds$intbound = MinMaxBounds.IntBound.fromJson(p_230241_1_.get("signal_strength"));
      EntityPredicate.AndPredicate entitypredicate$andpredicate = EntityPredicate.AndPredicate.func_234587_a_(p_230241_1_, "projectile", p_230241_3_);
      return new TargetHitTrigger.Instance(p_230241_2_, minmaxbounds$intbound, entitypredicate$andpredicate);
   }

   public void func_236350_a_(ServerPlayerEntity p_236350_1_, Entity p_236350_2_, Vector3d p_236350_3_, int p_236350_4_) {
      LootContext lootcontext = EntityPredicate.func_234575_b_(p_236350_1_, p_236350_2_);
      this.func_235959_a_(p_236350_1_, (p_236349_3_) -> {
         return p_236349_3_.func_236355_a_(lootcontext, p_236350_3_, p_236350_4_);
      });
   }

   public static class Instance extends CriterionInstance {
      private final MinMaxBounds.IntBound field_236352_a_;
      private final EntityPredicate.AndPredicate field_236353_b_;

      public Instance(EntityPredicate.AndPredicate p_i231990_1_, MinMaxBounds.IntBound p_i231990_2_, EntityPredicate.AndPredicate p_i231990_3_) {
         super(TargetHitTrigger.field_236348_a_, p_i231990_1_);
         this.field_236352_a_ = p_i231990_2_;
         this.field_236353_b_ = p_i231990_3_;
      }

      public static TargetHitTrigger.Instance func_236354_a_(MinMaxBounds.IntBound p_236354_0_, EntityPredicate.AndPredicate p_236354_1_) {
         return new TargetHitTrigger.Instance(EntityPredicate.AndPredicate.field_234582_a_, p_236354_0_, p_236354_1_);
      }

      public JsonObject func_230240_a_(ConditionArraySerializer p_230240_1_) {
         JsonObject jsonobject = super.func_230240_a_(p_230240_1_);
         jsonobject.add("signal_strength", this.field_236352_a_.serialize());
         jsonobject.add("projectile", this.field_236353_b_.func_234586_a_(p_230240_1_));
         return jsonobject;
      }

      public boolean func_236355_a_(LootContext p_236355_1_, Vector3d p_236355_2_, int p_236355_3_) {
         if (!this.field_236352_a_.test(p_236355_3_)) {
            return false;
         } else {
            return this.field_236353_b_.func_234588_a_(p_236355_1_);
         }
      }
   }
}