package net.minecraft.advancements.criterion;

import com.google.gson.JsonObject;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.loot.ConditionArrayParser;
import net.minecraft.loot.ConditionArraySerializer;
import net.minecraft.loot.LootContext;
import net.minecraft.util.ResourceLocation;

public class CuredZombieVillagerTrigger extends AbstractCriterionTrigger<CuredZombieVillagerTrigger.Instance> {
   private static final ResourceLocation ID = new ResourceLocation("cured_zombie_villager");

   public ResourceLocation getId() {
      return ID;
   }

   public CuredZombieVillagerTrigger.Instance func_230241_b_(JsonObject p_230241_1_, EntityPredicate.AndPredicate p_230241_2_, ConditionArrayParser p_230241_3_) {
      EntityPredicate.AndPredicate entitypredicate$andpredicate = EntityPredicate.AndPredicate.func_234587_a_(p_230241_1_, "zombie", p_230241_3_);
      EntityPredicate.AndPredicate entitypredicate$andpredicate1 = EntityPredicate.AndPredicate.func_234587_a_(p_230241_1_, "villager", p_230241_3_);
      return new CuredZombieVillagerTrigger.Instance(p_230241_2_, entitypredicate$andpredicate, entitypredicate$andpredicate1);
   }

   public void trigger(ServerPlayerEntity player, ZombieEntity zombie, VillagerEntity villager) {
      LootContext lootcontext = EntityPredicate.func_234575_b_(player, zombie);
      LootContext lootcontext1 = EntityPredicate.func_234575_b_(player, villager);
      this.func_235959_a_(player, (p_233969_2_) -> {
         return p_233969_2_.func_233970_a_(lootcontext, lootcontext1);
      });
   }

   public static class Instance extends CriterionInstance {
      private final EntityPredicate.AndPredicate zombie;
      private final EntityPredicate.AndPredicate villager;

      public Instance(EntityPredicate.AndPredicate p_i231535_1_, EntityPredicate.AndPredicate p_i231535_2_, EntityPredicate.AndPredicate p_i231535_3_) {
         super(CuredZombieVillagerTrigger.ID, p_i231535_1_);
         this.zombie = p_i231535_2_;
         this.villager = p_i231535_3_;
      }

      public static CuredZombieVillagerTrigger.Instance any() {
         return new CuredZombieVillagerTrigger.Instance(EntityPredicate.AndPredicate.field_234582_a_, EntityPredicate.AndPredicate.field_234582_a_, EntityPredicate.AndPredicate.field_234582_a_);
      }

      public boolean func_233970_a_(LootContext p_233970_1_, LootContext p_233970_2_) {
         if (!this.zombie.func_234588_a_(p_233970_1_)) {
            return false;
         } else {
            return this.villager.func_234588_a_(p_233970_2_);
         }
      }

      public JsonObject func_230240_a_(ConditionArraySerializer p_230240_1_) {
         JsonObject jsonobject = super.func_230240_a_(p_230240_1_);
         jsonobject.add("zombie", this.zombie.func_234586_a_(p_230240_1_));
         jsonobject.add("villager", this.villager.func_234586_a_(p_230240_1_));
         return jsonobject;
      }
   }
}