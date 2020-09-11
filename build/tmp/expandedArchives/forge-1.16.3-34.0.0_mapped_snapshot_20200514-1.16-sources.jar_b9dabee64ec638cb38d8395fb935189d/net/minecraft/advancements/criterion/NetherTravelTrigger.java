package net.minecraft.advancements.criterion;

import com.google.gson.JsonObject;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.loot.ConditionArrayParser;
import net.minecraft.loot.ConditionArraySerializer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.server.ServerWorld;

public class NetherTravelTrigger extends AbstractCriterionTrigger<NetherTravelTrigger.Instance> {
   private static final ResourceLocation ID = new ResourceLocation("nether_travel");

   public ResourceLocation getId() {
      return ID;
   }

   public NetherTravelTrigger.Instance func_230241_b_(JsonObject p_230241_1_, EntityPredicate.AndPredicate p_230241_2_, ConditionArrayParser p_230241_3_) {
      LocationPredicate locationpredicate = LocationPredicate.deserialize(p_230241_1_.get("entered"));
      LocationPredicate locationpredicate1 = LocationPredicate.deserialize(p_230241_1_.get("exited"));
      DistancePredicate distancepredicate = DistancePredicate.deserialize(p_230241_1_.get("distance"));
      return new NetherTravelTrigger.Instance(p_230241_2_, locationpredicate, locationpredicate1, distancepredicate);
   }

   public void trigger(ServerPlayerEntity player, Vector3d enteredNetherPosition) {
      this.func_235959_a_(player, (p_226945_2_) -> {
         return p_226945_2_.test(player.getServerWorld(), enteredNetherPosition, player.getPosX(), player.getPosY(), player.getPosZ());
      });
   }

   public static class Instance extends CriterionInstance {
      private final LocationPredicate entered;
      private final LocationPredicate exited;
      private final DistancePredicate distance;

      public Instance(EntityPredicate.AndPredicate p_i231785_1_, LocationPredicate p_i231785_2_, LocationPredicate p_i231785_3_, DistancePredicate p_i231785_4_) {
         super(NetherTravelTrigger.ID, p_i231785_1_);
         this.entered = p_i231785_2_;
         this.exited = p_i231785_3_;
         this.distance = p_i231785_4_;
      }

      public static NetherTravelTrigger.Instance forDistance(DistancePredicate p_203933_0_) {
         return new NetherTravelTrigger.Instance(EntityPredicate.AndPredicate.field_234582_a_, LocationPredicate.ANY, LocationPredicate.ANY, p_203933_0_);
      }

      public boolean test(ServerWorld world, Vector3d enteredNetherPosition, double x, double y, double z) {
         if (!this.entered.test(world, enteredNetherPosition.x, enteredNetherPosition.y, enteredNetherPosition.z)) {
            return false;
         } else if (!this.exited.test(world, x, y, z)) {
            return false;
         } else {
            return this.distance.test(enteredNetherPosition.x, enteredNetherPosition.y, enteredNetherPosition.z, x, y, z);
         }
      }

      public JsonObject func_230240_a_(ConditionArraySerializer p_230240_1_) {
         JsonObject jsonobject = super.func_230240_a_(p_230240_1_);
         jsonobject.add("entered", this.entered.serialize());
         jsonobject.add("exited", this.exited.serialize());
         jsonobject.add("distance", this.distance.serialize());
         return jsonobject;
      }
   }
}