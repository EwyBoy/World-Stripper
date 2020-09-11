package net.minecraft.advancements.criterion;

import com.google.gson.JsonObject;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.loot.ConditionArrayParser;
import net.minecraft.loot.ConditionArraySerializer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3d;

public class LevitationTrigger extends AbstractCriterionTrigger<LevitationTrigger.Instance> {
   private static final ResourceLocation ID = new ResourceLocation("levitation");

   public ResourceLocation getId() {
      return ID;
   }

   public LevitationTrigger.Instance func_230241_b_(JsonObject p_230241_1_, EntityPredicate.AndPredicate p_230241_2_, ConditionArrayParser p_230241_3_) {
      DistancePredicate distancepredicate = DistancePredicate.deserialize(p_230241_1_.get("distance"));
      MinMaxBounds.IntBound minmaxbounds$intbound = MinMaxBounds.IntBound.fromJson(p_230241_1_.get("duration"));
      return new LevitationTrigger.Instance(p_230241_2_, distancepredicate, minmaxbounds$intbound);
   }

   public void trigger(ServerPlayerEntity player, Vector3d startPos, int duration) {
      this.func_235959_a_(player, (p_226852_3_) -> {
         return p_226852_3_.test(player, startPos, duration);
      });
   }

   public static class Instance extends CriterionInstance {
      private final DistancePredicate distance;
      private final MinMaxBounds.IntBound duration;

      public Instance(EntityPredicate.AndPredicate p_i231638_1_, DistancePredicate p_i231638_2_, MinMaxBounds.IntBound p_i231638_3_) {
         super(LevitationTrigger.ID, p_i231638_1_);
         this.distance = p_i231638_2_;
         this.duration = p_i231638_3_;
      }

      public static LevitationTrigger.Instance forDistance(DistancePredicate p_203930_0_) {
         return new LevitationTrigger.Instance(EntityPredicate.AndPredicate.field_234582_a_, p_203930_0_, MinMaxBounds.IntBound.UNBOUNDED);
      }

      public boolean test(ServerPlayerEntity player, Vector3d startPos, int durationIn) {
         if (!this.distance.test(startPos.x, startPos.y, startPos.z, player.getPosX(), player.getPosY(), player.getPosZ())) {
            return false;
         } else {
            return this.duration.test(durationIn);
         }
      }

      public JsonObject func_230240_a_(ConditionArraySerializer p_230240_1_) {
         JsonObject jsonobject = super.func_230240_a_(p_230240_1_);
         jsonobject.add("distance", this.distance.serialize());
         jsonobject.add("duration", this.duration.serialize());
         return jsonobject;
      }
   }
}