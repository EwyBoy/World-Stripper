package net.minecraft.advancements.criterion;

import com.google.gson.JsonObject;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.loot.ConditionArrayParser;
import net.minecraft.loot.ConditionArraySerializer;
import net.minecraft.tileentity.BeaconTileEntity;
import net.minecraft.util.ResourceLocation;

public class ConstructBeaconTrigger extends AbstractCriterionTrigger<ConstructBeaconTrigger.Instance> {
   private static final ResourceLocation ID = new ResourceLocation("construct_beacon");

   public ResourceLocation getId() {
      return ID;
   }

   public ConstructBeaconTrigger.Instance func_230241_b_(JsonObject p_230241_1_, EntityPredicate.AndPredicate p_230241_2_, ConditionArrayParser p_230241_3_) {
      MinMaxBounds.IntBound minmaxbounds$intbound = MinMaxBounds.IntBound.fromJson(p_230241_1_.get("level"));
      return new ConstructBeaconTrigger.Instance(p_230241_2_, minmaxbounds$intbound);
   }

   public void trigger(ServerPlayerEntity player, BeaconTileEntity beacon) {
      this.func_235959_a_(player, (p_226308_1_) -> {
         return p_226308_1_.test(beacon);
      });
   }

   public static class Instance extends CriterionInstance {
      private final MinMaxBounds.IntBound level;

      public Instance(EntityPredicate.AndPredicate p_i231507_1_, MinMaxBounds.IntBound p_i231507_2_) {
         super(ConstructBeaconTrigger.ID, p_i231507_1_);
         this.level = p_i231507_2_;
      }

      public static ConstructBeaconTrigger.Instance forLevel(MinMaxBounds.IntBound p_203912_0_) {
         return new ConstructBeaconTrigger.Instance(EntityPredicate.AndPredicate.field_234582_a_, p_203912_0_);
      }

      public boolean test(BeaconTileEntity beacon) {
         return this.level.test(beacon.getLevels());
      }

      public JsonObject func_230240_a_(ConditionArraySerializer p_230240_1_) {
         JsonObject jsonobject = super.func_230240_a_(p_230240_1_);
         jsonobject.add("level", this.level.serialize());
         return jsonobject;
      }
   }
}