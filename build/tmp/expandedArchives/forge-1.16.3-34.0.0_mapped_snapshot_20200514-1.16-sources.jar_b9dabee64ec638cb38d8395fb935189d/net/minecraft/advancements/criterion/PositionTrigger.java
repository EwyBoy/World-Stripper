package net.minecraft.advancements.criterion;

import com.google.gson.JsonObject;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.loot.ConditionArrayParser;
import net.minecraft.loot.ConditionArraySerializer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.server.ServerWorld;

public class PositionTrigger extends AbstractCriterionTrigger<PositionTrigger.Instance> {
   private final ResourceLocation id;

   public PositionTrigger(ResourceLocation id) {
      this.id = id;
   }

   public ResourceLocation getId() {
      return this.id;
   }

   public PositionTrigger.Instance func_230241_b_(JsonObject p_230241_1_, EntityPredicate.AndPredicate p_230241_2_, ConditionArrayParser p_230241_3_) {
      JsonObject jsonobject = JSONUtils.getJsonObject(p_230241_1_, "location", p_230241_1_);
      LocationPredicate locationpredicate = LocationPredicate.deserialize(jsonobject);
      return new PositionTrigger.Instance(this.id, p_230241_2_, locationpredicate);
   }

   public void trigger(ServerPlayerEntity player) {
      this.func_235959_a_(player, (p_226923_1_) -> {
         return p_226923_1_.test(player.getServerWorld(), player.getPosX(), player.getPosY(), player.getPosZ());
      });
   }

   public static class Instance extends CriterionInstance {
      private final LocationPredicate location;

      public Instance(ResourceLocation p_i231661_1_, EntityPredicate.AndPredicate p_i231661_2_, LocationPredicate p_i231661_3_) {
         super(p_i231661_1_, p_i231661_2_);
         this.location = p_i231661_3_;
      }

      public static PositionTrigger.Instance forLocation(LocationPredicate p_203932_0_) {
         return new PositionTrigger.Instance(CriteriaTriggers.LOCATION.id, EntityPredicate.AndPredicate.field_234582_a_, p_203932_0_);
      }

      public static PositionTrigger.Instance sleptInBed() {
         return new PositionTrigger.Instance(CriteriaTriggers.SLEPT_IN_BED.id, EntityPredicate.AndPredicate.field_234582_a_, LocationPredicate.ANY);
      }

      public static PositionTrigger.Instance func_215120_d() {
         return new PositionTrigger.Instance(CriteriaTriggers.HERO_OF_THE_VILLAGE.id, EntityPredicate.AndPredicate.field_234582_a_, LocationPredicate.ANY);
      }

      public boolean test(ServerWorld world, double x, double y, double z) {
         return this.location.test(world, x, y, z);
      }

      public JsonObject func_230240_a_(ConditionArraySerializer p_230240_1_) {
         JsonObject jsonobject = super.func_230240_a_(p_230240_1_);
         jsonobject.add("location", this.location.serialize());
         return jsonobject;
      }
   }
}