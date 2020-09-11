package net.minecraft.advancements.criterion;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.passive.CatEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.loot.ConditionArrayParser;
import net.minecraft.loot.ConditionArraySerializer;
import net.minecraft.loot.FishingPredicate;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootParameterSets;
import net.minecraft.loot.LootParameters;
import net.minecraft.loot.conditions.EntityHasProperty;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.loot.conditions.LootConditionManager;
import net.minecraft.scoreboard.Team;
import net.minecraft.tags.ITag;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.server.ServerWorld;

public class EntityPredicate {
   public static final EntityPredicate ANY = new EntityPredicate(EntityTypePredicate.ANY, DistancePredicate.ANY, LocationPredicate.ANY, MobEffectsPredicate.ANY, NBTPredicate.ANY, EntityFlagsPredicate.ALWAYS_TRUE, EntityEquipmentPredicate.ANY, PlayerPredicate.field_226989_a_, FishingPredicate.field_234635_a_, (String)null, (ResourceLocation)null);
   private final EntityTypePredicate type;
   private final DistancePredicate distance;
   private final LocationPredicate location;
   private final MobEffectsPredicate effects;
   private final NBTPredicate nbt;
   private final EntityFlagsPredicate flags;
   private final EntityEquipmentPredicate equipment;
   private final PlayerPredicate player;
   private final FishingPredicate field_234572_j_;
   private final EntityPredicate field_234573_k_;
   private final EntityPredicate field_234574_l_;
   @Nullable
   private final String team;
   @Nullable
   private final ResourceLocation catType;

   private EntityPredicate(EntityTypePredicate p_i241236_1_, DistancePredicate p_i241236_2_, LocationPredicate p_i241236_3_, MobEffectsPredicate p_i241236_4_, NBTPredicate p_i241236_5_, EntityFlagsPredicate p_i241236_6_, EntityEquipmentPredicate p_i241236_7_, PlayerPredicate p_i241236_8_, FishingPredicate p_i241236_9_, @Nullable String p_i241236_10_, @Nullable ResourceLocation p_i241236_11_) {
      this.type = p_i241236_1_;
      this.distance = p_i241236_2_;
      this.location = p_i241236_3_;
      this.effects = p_i241236_4_;
      this.nbt = p_i241236_5_;
      this.flags = p_i241236_6_;
      this.equipment = p_i241236_7_;
      this.player = p_i241236_8_;
      this.field_234572_j_ = p_i241236_9_;
      this.field_234573_k_ = this;
      this.field_234574_l_ = this;
      this.team = p_i241236_10_;
      this.catType = p_i241236_11_;
   }

   private EntityPredicate(EntityTypePredicate p_i231578_1_, DistancePredicate p_i231578_2_, LocationPredicate p_i231578_3_, MobEffectsPredicate p_i231578_4_, NBTPredicate p_i231578_5_, EntityFlagsPredicate p_i231578_6_, EntityEquipmentPredicate p_i231578_7_, PlayerPredicate p_i231578_8_, FishingPredicate p_i231578_9_, EntityPredicate p_i231578_10_, EntityPredicate p_i231578_11_, @Nullable String p_i231578_12_, @Nullable ResourceLocation p_i231578_13_) {
      this.type = p_i231578_1_;
      this.distance = p_i231578_2_;
      this.location = p_i231578_3_;
      this.effects = p_i231578_4_;
      this.nbt = p_i231578_5_;
      this.flags = p_i231578_6_;
      this.equipment = p_i231578_7_;
      this.player = p_i231578_8_;
      this.field_234572_j_ = p_i231578_9_;
      this.field_234573_k_ = p_i231578_10_;
      this.field_234574_l_ = p_i231578_11_;
      this.team = p_i231578_12_;
      this.catType = p_i231578_13_;
   }

   public boolean test(ServerPlayerEntity player, @Nullable Entity entity) {
      return this.func_217993_a(player.getServerWorld(), player.getPositionVec(), entity);
   }

   public boolean func_217993_a(ServerWorld p_217993_1_, @Nullable Vector3d p_217993_2_, @Nullable Entity p_217993_3_) {
      if (this == ANY) {
         return true;
      } else if (p_217993_3_ == null) {
         return false;
      } else if (!this.type.test(p_217993_3_.getType())) {
         return false;
      } else {
         if (p_217993_2_ == null) {
            if (this.distance != DistancePredicate.ANY) {
               return false;
            }
         } else if (!this.distance.test(p_217993_2_.x, p_217993_2_.y, p_217993_2_.z, p_217993_3_.getPosX(), p_217993_3_.getPosY(), p_217993_3_.getPosZ())) {
            return false;
         }

         if (!this.location.test(p_217993_1_, p_217993_3_.getPosX(), p_217993_3_.getPosY(), p_217993_3_.getPosZ())) {
            return false;
         } else if (!this.effects.test(p_217993_3_)) {
            return false;
         } else if (!this.nbt.test(p_217993_3_)) {
            return false;
         } else if (!this.flags.test(p_217993_3_)) {
            return false;
         } else if (!this.equipment.test(p_217993_3_)) {
            return false;
         } else if (!this.player.func_226998_a_(p_217993_3_)) {
            return false;
         } else if (!this.field_234572_j_.func_234638_a_(p_217993_3_)) {
            return false;
         } else if (!this.field_234573_k_.func_217993_a(p_217993_1_, p_217993_2_, p_217993_3_.getRidingEntity())) {
            return false;
         } else if (!this.field_234574_l_.func_217993_a(p_217993_1_, p_217993_2_, p_217993_3_ instanceof MobEntity ? ((MobEntity)p_217993_3_).getAttackTarget() : null)) {
            return false;
         } else {
            if (this.team != null) {
               Team team = p_217993_3_.getTeam();
               if (team == null || !this.team.equals(team.getName())) {
                  return false;
               }
            }

            return this.catType == null || p_217993_3_ instanceof CatEntity && ((CatEntity)p_217993_3_).getCatTypeName().equals(this.catType);
         }
      }
   }

   public static EntityPredicate deserialize(@Nullable JsonElement element) {
      if (element != null && !element.isJsonNull()) {
         JsonObject jsonobject = JSONUtils.getJsonObject(element, "entity");
         EntityTypePredicate entitytypepredicate = EntityTypePredicate.deserialize(jsonobject.get("type"));
         DistancePredicate distancepredicate = DistancePredicate.deserialize(jsonobject.get("distance"));
         LocationPredicate locationpredicate = LocationPredicate.deserialize(jsonobject.get("location"));
         MobEffectsPredicate mobeffectspredicate = MobEffectsPredicate.deserialize(jsonobject.get("effects"));
         NBTPredicate nbtpredicate = NBTPredicate.deserialize(jsonobject.get("nbt"));
         EntityFlagsPredicate entityflagspredicate = EntityFlagsPredicate.deserialize(jsonobject.get("flags"));
         EntityEquipmentPredicate entityequipmentpredicate = EntityEquipmentPredicate.deserialize(jsonobject.get("equipment"));
         PlayerPredicate playerpredicate = PlayerPredicate.func_227000_a_(jsonobject.get("player"));
         FishingPredicate fishingpredicate = FishingPredicate.func_234639_a_(jsonobject.get("fishing_hook"));
         EntityPredicate entitypredicate = deserialize(jsonobject.get("vehicle"));
         EntityPredicate entitypredicate1 = deserialize(jsonobject.get("targeted_entity"));
         String s = JSONUtils.getString(jsonobject, "team", (String)null);
         ResourceLocation resourcelocation = jsonobject.has("catType") ? new ResourceLocation(JSONUtils.getString(jsonobject, "catType")) : null;
         return (new EntityPredicate.Builder()).type(entitytypepredicate).distance(distancepredicate).location(locationpredicate).effects(mobeffectspredicate).nbt(nbtpredicate).flags(entityflagspredicate).equipment(entityequipmentpredicate).func_226613_a_(playerpredicate).func_234580_a_(fishingpredicate).func_226614_a_(s).func_234579_a_(entitypredicate).func_234581_b_(entitypredicate1).func_217988_b(resourcelocation).build();
      } else {
         return ANY;
      }
   }

   public JsonElement serialize() {
      if (this == ANY) {
         return JsonNull.INSTANCE;
      } else {
         JsonObject jsonobject = new JsonObject();
         jsonobject.add("type", this.type.serialize());
         jsonobject.add("distance", this.distance.serialize());
         jsonobject.add("location", this.location.serialize());
         jsonobject.add("effects", this.effects.serialize());
         jsonobject.add("nbt", this.nbt.serialize());
         jsonobject.add("flags", this.flags.serialize());
         jsonobject.add("equipment", this.equipment.serialize());
         jsonobject.add("player", this.player.serialize());
         jsonobject.add("fishing_hook", this.field_234572_j_.func_234637_a_());
         jsonobject.add("vehicle", this.field_234573_k_.serialize());
         jsonobject.add("targeted_entity", this.field_234574_l_.serialize());
         jsonobject.addProperty("team", this.team);
         if (this.catType != null) {
            jsonobject.addProperty("catType", this.catType.toString());
         }

         return jsonobject;
      }
   }

   public static LootContext func_234575_b_(ServerPlayerEntity p_234575_0_, Entity p_234575_1_) {
      return (new LootContext.Builder(p_234575_0_.getServerWorld())).withParameter(LootParameters.THIS_ENTITY, p_234575_1_).withParameter(LootParameters.field_237457_g_, p_234575_0_.getPositionVec()).withRandom(p_234575_0_.getRNG()).build(LootParameterSets.field_237454_j_);
   }

   public static class AndPredicate {
      public static final EntityPredicate.AndPredicate field_234582_a_ = new EntityPredicate.AndPredicate(new ILootCondition[0]);
      private final ILootCondition[] field_234583_b_;
      private final Predicate<LootContext> field_234584_c_;

      private AndPredicate(ILootCondition[] p_i231580_1_) {
         this.field_234583_b_ = p_i231580_1_;
         this.field_234584_c_ = LootConditionManager.and(p_i231580_1_);
      }

      public static EntityPredicate.AndPredicate func_234591_a_(ILootCondition... p_234591_0_) {
         return new EntityPredicate.AndPredicate(p_234591_0_);
      }

      public static EntityPredicate.AndPredicate func_234587_a_(JsonObject p_234587_0_, String p_234587_1_, ConditionArrayParser p_234587_2_) {
         JsonElement jsonelement = p_234587_0_.get(p_234587_1_);
         return func_234589_a_(p_234587_1_, p_234587_2_, jsonelement);
      }

      public static EntityPredicate.AndPredicate[] func_234592_b_(JsonObject p_234592_0_, String p_234592_1_, ConditionArrayParser p_234592_2_) {
         JsonElement jsonelement = p_234592_0_.get(p_234592_1_);
         if (jsonelement != null && !jsonelement.isJsonNull()) {
            JsonArray jsonarray = JSONUtils.getJsonArray(jsonelement, p_234592_1_);
            EntityPredicate.AndPredicate[] aentitypredicate$andpredicate = new EntityPredicate.AndPredicate[jsonarray.size()];

            for(int i = 0; i < jsonarray.size(); ++i) {
               aentitypredicate$andpredicate[i] = func_234589_a_(p_234592_1_ + "[" + i + "]", p_234592_2_, jsonarray.get(i));
            }

            return aentitypredicate$andpredicate;
         } else {
            return new EntityPredicate.AndPredicate[0];
         }
      }

      private static EntityPredicate.AndPredicate func_234589_a_(String p_234589_0_, ConditionArrayParser p_234589_1_, @Nullable JsonElement p_234589_2_) {
         if (p_234589_2_ != null && p_234589_2_.isJsonArray()) {
            ILootCondition[] ailootcondition = p_234589_1_.func_234050_a_(p_234589_2_.getAsJsonArray(), p_234589_1_.func_234049_a_().toString() + "/" + p_234589_0_, LootParameterSets.field_237454_j_);
            return new EntityPredicate.AndPredicate(ailootcondition);
         } else {
            EntityPredicate entitypredicate = EntityPredicate.deserialize(p_234589_2_);
            return func_234585_a_(entitypredicate);
         }
      }

      public static EntityPredicate.AndPredicate func_234585_a_(EntityPredicate p_234585_0_) {
         if (p_234585_0_ == EntityPredicate.ANY) {
            return field_234582_a_;
         } else {
            ILootCondition ilootcondition = EntityHasProperty.func_237477_a_(LootContext.EntityTarget.THIS, p_234585_0_).build();
            return new EntityPredicate.AndPredicate(new ILootCondition[]{ilootcondition});
         }
      }

      public boolean func_234588_a_(LootContext p_234588_1_) {
         return this.field_234584_c_.test(p_234588_1_);
      }

      public JsonElement func_234586_a_(ConditionArraySerializer p_234586_1_) {
         return (JsonElement)(this.field_234583_b_.length == 0 ? JsonNull.INSTANCE : p_234586_1_.func_235681_a_(this.field_234583_b_));
      }

      public static JsonElement func_234590_a_(EntityPredicate.AndPredicate[] p_234590_0_, ConditionArraySerializer p_234590_1_) {
         if (p_234590_0_.length == 0) {
            return JsonNull.INSTANCE;
         } else {
            JsonArray jsonarray = new JsonArray();

            for(EntityPredicate.AndPredicate entitypredicate$andpredicate : p_234590_0_) {
               jsonarray.add(entitypredicate$andpredicate.func_234586_a_(p_234590_1_));
            }

            return jsonarray;
         }
      }
   }

   public static class Builder {
      private EntityTypePredicate type = EntityTypePredicate.ANY;
      private DistancePredicate distance = DistancePredicate.ANY;
      private LocationPredicate location = LocationPredicate.ANY;
      private MobEffectsPredicate effects = MobEffectsPredicate.ANY;
      private NBTPredicate nbt = NBTPredicate.ANY;
      private EntityFlagsPredicate flags = EntityFlagsPredicate.ALWAYS_TRUE;
      private EntityEquipmentPredicate equipment = EntityEquipmentPredicate.ANY;
      private PlayerPredicate field_226611_h_ = PlayerPredicate.field_226989_a_;
      private FishingPredicate field_234576_i_ = FishingPredicate.field_234635_a_;
      private EntityPredicate field_234577_j_ = EntityPredicate.ANY;
      private EntityPredicate field_234578_k_ = EntityPredicate.ANY;
      private String field_226612_i_;
      private ResourceLocation catType;

      public static EntityPredicate.Builder create() {
         return new EntityPredicate.Builder();
      }

      public EntityPredicate.Builder type(EntityType<?> typeIn) {
         this.type = EntityTypePredicate.func_217999_b(typeIn);
         return this;
      }

      public EntityPredicate.Builder type(ITag<EntityType<?>> typeIn) {
         this.type = EntityTypePredicate.func_217998_a(typeIn);
         return this;
      }

      public EntityPredicate.Builder func_217986_a(ResourceLocation catTypeIn) {
         this.catType = catTypeIn;
         return this;
      }

      public EntityPredicate.Builder type(EntityTypePredicate typeIn) {
         this.type = typeIn;
         return this;
      }

      public EntityPredicate.Builder distance(DistancePredicate distanceIn) {
         this.distance = distanceIn;
         return this;
      }

      public EntityPredicate.Builder location(LocationPredicate locationIn) {
         this.location = locationIn;
         return this;
      }

      public EntityPredicate.Builder effects(MobEffectsPredicate effectsIn) {
         this.effects = effectsIn;
         return this;
      }

      public EntityPredicate.Builder nbt(NBTPredicate nbtIn) {
         this.nbt = nbtIn;
         return this;
      }

      public EntityPredicate.Builder flags(EntityFlagsPredicate flagsIn) {
         this.flags = flagsIn;
         return this;
      }

      public EntityPredicate.Builder equipment(EntityEquipmentPredicate equipmentIn) {
         this.equipment = equipmentIn;
         return this;
      }

      public EntityPredicate.Builder func_226613_a_(PlayerPredicate p_226613_1_) {
         this.field_226611_h_ = p_226613_1_;
         return this;
      }

      public EntityPredicate.Builder func_234580_a_(FishingPredicate p_234580_1_) {
         this.field_234576_i_ = p_234580_1_;
         return this;
      }

      public EntityPredicate.Builder func_234579_a_(EntityPredicate p_234579_1_) {
         this.field_234577_j_ = p_234579_1_;
         return this;
      }

      public EntityPredicate.Builder func_234581_b_(EntityPredicate p_234581_1_) {
         this.field_234578_k_ = p_234581_1_;
         return this;
      }

      public EntityPredicate.Builder func_226614_a_(@Nullable String p_226614_1_) {
         this.field_226612_i_ = p_226614_1_;
         return this;
      }

      public EntityPredicate.Builder func_217988_b(@Nullable ResourceLocation catTypeIn) {
         this.catType = catTypeIn;
         return this;
      }

      public EntityPredicate build() {
         return new EntityPredicate(this.type, this.distance, this.location, this.effects, this.nbt, this.flags, this.equipment, this.field_226611_h_, this.field_234576_i_, this.field_234577_j_, this.field_234578_k_, this.field_226612_i_, this.catType);
      }
   }
}