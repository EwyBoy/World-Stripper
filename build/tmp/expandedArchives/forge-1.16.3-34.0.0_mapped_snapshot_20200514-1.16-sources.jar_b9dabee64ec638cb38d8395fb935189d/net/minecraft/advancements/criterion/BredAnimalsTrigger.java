package net.minecraft.advancements.criterion;

import com.google.gson.JsonObject;
import javax.annotation.Nullable;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.loot.ConditionArrayParser;
import net.minecraft.loot.ConditionArraySerializer;
import net.minecraft.loot.LootContext;
import net.minecraft.util.ResourceLocation;

public class BredAnimalsTrigger extends AbstractCriterionTrigger<BredAnimalsTrigger.Instance> {
   private static final ResourceLocation ID = new ResourceLocation("bred_animals");

   public ResourceLocation getId() {
      return ID;
   }

   public BredAnimalsTrigger.Instance func_230241_b_(JsonObject p_230241_1_, EntityPredicate.AndPredicate p_230241_2_, ConditionArrayParser p_230241_3_) {
      EntityPredicate.AndPredicate entitypredicate$andpredicate = EntityPredicate.AndPredicate.func_234587_a_(p_230241_1_, "parent", p_230241_3_);
      EntityPredicate.AndPredicate entitypredicate$andpredicate1 = EntityPredicate.AndPredicate.func_234587_a_(p_230241_1_, "partner", p_230241_3_);
      EntityPredicate.AndPredicate entitypredicate$andpredicate2 = EntityPredicate.AndPredicate.func_234587_a_(p_230241_1_, "child", p_230241_3_);
      return new BredAnimalsTrigger.Instance(p_230241_2_, entitypredicate$andpredicate, entitypredicate$andpredicate1, entitypredicate$andpredicate2);
   }

   public void trigger(ServerPlayerEntity player, AnimalEntity parent1, AnimalEntity parent2, @Nullable AgeableEntity child) {
      LootContext lootcontext = EntityPredicate.func_234575_b_(player, parent1);
      LootContext lootcontext1 = EntityPredicate.func_234575_b_(player, parent2);
      LootContext lootcontext2 = child != null ? EntityPredicate.func_234575_b_(player, child) : null;
      this.func_235959_a_(player, (p_233510_3_) -> {
         return p_233510_3_.func_233511_a_(lootcontext, lootcontext1, lootcontext2);
      });
   }

   public static class Instance extends CriterionInstance {
      private final EntityPredicate.AndPredicate parent;
      private final EntityPredicate.AndPredicate partner;
      private final EntityPredicate.AndPredicate child;

      public Instance(EntityPredicate.AndPredicate p_i231484_1_, EntityPredicate.AndPredicate p_i231484_2_, EntityPredicate.AndPredicate p_i231484_3_, EntityPredicate.AndPredicate p_i231484_4_) {
         super(BredAnimalsTrigger.ID, p_i231484_1_);
         this.parent = p_i231484_2_;
         this.partner = p_i231484_3_;
         this.child = p_i231484_4_;
      }

      public static BredAnimalsTrigger.Instance any() {
         return new BredAnimalsTrigger.Instance(EntityPredicate.AndPredicate.field_234582_a_, EntityPredicate.AndPredicate.field_234582_a_, EntityPredicate.AndPredicate.field_234582_a_, EntityPredicate.AndPredicate.field_234582_a_);
      }

      public static BredAnimalsTrigger.Instance forParent(EntityPredicate.Builder p_203909_0_) {
         return new BredAnimalsTrigger.Instance(EntityPredicate.AndPredicate.field_234582_a_, EntityPredicate.AndPredicate.field_234582_a_, EntityPredicate.AndPredicate.field_234582_a_, EntityPredicate.AndPredicate.func_234585_a_(p_203909_0_.build()));
      }

      public static BredAnimalsTrigger.Instance func_241332_a_(EntityPredicate p_241332_0_, EntityPredicate p_241332_1_, EntityPredicate p_241332_2_) {
         return new BredAnimalsTrigger.Instance(EntityPredicate.AndPredicate.field_234582_a_, EntityPredicate.AndPredicate.func_234585_a_(p_241332_0_), EntityPredicate.AndPredicate.func_234585_a_(p_241332_1_), EntityPredicate.AndPredicate.func_234585_a_(p_241332_2_));
      }

      public boolean func_233511_a_(LootContext p_233511_1_, LootContext p_233511_2_, @Nullable LootContext p_233511_3_) {
         if (this.child == EntityPredicate.AndPredicate.field_234582_a_ || p_233511_3_ != null && this.child.func_234588_a_(p_233511_3_)) {
            return this.parent.func_234588_a_(p_233511_1_) && this.partner.func_234588_a_(p_233511_2_) || this.parent.func_234588_a_(p_233511_2_) && this.partner.func_234588_a_(p_233511_1_);
         } else {
            return false;
         }
      }

      public JsonObject func_230240_a_(ConditionArraySerializer p_230240_1_) {
         JsonObject jsonobject = super.func_230240_a_(p_230240_1_);
         jsonobject.add("parent", this.parent.func_234586_a_(p_230240_1_));
         jsonobject.add("partner", this.partner.func_234586_a_(p_230240_1_));
         jsonobject.add("child", this.child.func_234586_a_(p_230240_1_));
         return jsonobject;
      }
   }
}