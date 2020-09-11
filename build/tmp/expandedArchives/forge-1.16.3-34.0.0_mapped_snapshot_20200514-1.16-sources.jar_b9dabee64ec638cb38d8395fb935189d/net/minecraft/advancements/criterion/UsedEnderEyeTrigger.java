package net.minecraft.advancements.criterion;

import com.google.gson.JsonObject;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.loot.ConditionArrayParser;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;

public class UsedEnderEyeTrigger extends AbstractCriterionTrigger<UsedEnderEyeTrigger.Instance> {
   private static final ResourceLocation ID = new ResourceLocation("used_ender_eye");

   public ResourceLocation getId() {
      return ID;
   }

   public UsedEnderEyeTrigger.Instance func_230241_b_(JsonObject p_230241_1_, EntityPredicate.AndPredicate p_230241_2_, ConditionArrayParser p_230241_3_) {
      MinMaxBounds.FloatBound minmaxbounds$floatbound = MinMaxBounds.FloatBound.fromJson(p_230241_1_.get("distance"));
      return new UsedEnderEyeTrigger.Instance(p_230241_2_, minmaxbounds$floatbound);
   }

   public void trigger(ServerPlayerEntity player, BlockPos pos) {
      double d0 = player.getPosX() - (double)pos.getX();
      double d1 = player.getPosZ() - (double)pos.getZ();
      double d2 = d0 * d0 + d1 * d1;
      this.func_235959_a_(player, (p_227325_2_) -> {
         return p_227325_2_.test(d2);
      });
   }

   public static class Instance extends CriterionInstance {
      private final MinMaxBounds.FloatBound distance;

      public Instance(EntityPredicate.AndPredicate p_i232030_1_, MinMaxBounds.FloatBound p_i232030_2_) {
         super(UsedEnderEyeTrigger.ID, p_i232030_1_);
         this.distance = p_i232030_2_;
      }

      public boolean test(double distanceSq) {
         return this.distance.testSquared(distanceSq);
      }
   }
}