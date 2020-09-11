package net.minecraft.advancements.criterion;

import com.google.gson.JsonObject;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.loot.ConditionArrayParser;
import net.minecraft.loot.ConditionArraySerializer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;

public class PlayerGeneratesContainerLootTrigger extends AbstractCriterionTrigger<PlayerGeneratesContainerLootTrigger.Instance> {
   private static final ResourceLocation field_235476_a_ = new ResourceLocation("player_generates_container_loot");

   public ResourceLocation getId() {
      return field_235476_a_;
   }

   protected PlayerGeneratesContainerLootTrigger.Instance func_230241_b_(JsonObject p_230241_1_, EntityPredicate.AndPredicate p_230241_2_, ConditionArrayParser p_230241_3_) {
      ResourceLocation resourcelocation = new ResourceLocation(JSONUtils.getString(p_230241_1_, "loot_table"));
      return new PlayerGeneratesContainerLootTrigger.Instance(p_230241_2_, resourcelocation);
   }

   public void func_235478_a_(ServerPlayerEntity p_235478_1_, ResourceLocation p_235478_2_) {
      this.func_235959_a_(p_235478_1_, (p_235477_1_) -> {
         return p_235477_1_.func_235482_b_(p_235478_2_);
      });
   }

   public static class Instance extends CriterionInstance {
      private final ResourceLocation field_235480_a_;

      public Instance(EntityPredicate.AndPredicate p_i231684_1_, ResourceLocation p_i231684_2_) {
         super(PlayerGeneratesContainerLootTrigger.field_235476_a_, p_i231684_1_);
         this.field_235480_a_ = p_i231684_2_;
      }

      public static PlayerGeneratesContainerLootTrigger.Instance func_235481_a_(ResourceLocation p_235481_0_) {
         return new PlayerGeneratesContainerLootTrigger.Instance(EntityPredicate.AndPredicate.field_234582_a_, p_235481_0_);
      }

      public boolean func_235482_b_(ResourceLocation p_235482_1_) {
         return this.field_235480_a_.equals(p_235482_1_);
      }

      public JsonObject func_230240_a_(ConditionArraySerializer p_230240_1_) {
         JsonObject jsonobject = super.func_230240_a_(p_230240_1_);
         jsonobject.addProperty("loot_table", this.field_235480_a_.toString());
         return jsonobject;
      }
   }
}