package net.minecraft.advancements.criterion;

import com.google.gson.JsonObject;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.ConditionArrayParser;
import net.minecraft.loot.ConditionArraySerializer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;

public class RightClickBlockWithItemTrigger extends AbstractCriterionTrigger<RightClickBlockWithItemTrigger.Instance> {
   private static final ResourceLocation field_234849_a_ = new ResourceLocation("item_used_on_block");

   public ResourceLocation getId() {
      return field_234849_a_;
   }

   public RightClickBlockWithItemTrigger.Instance func_230241_b_(JsonObject p_230241_1_, EntityPredicate.AndPredicate p_230241_2_, ConditionArrayParser p_230241_3_) {
      LocationPredicate locationpredicate = LocationPredicate.deserialize(p_230241_1_.get("location"));
      ItemPredicate itempredicate = ItemPredicate.deserialize(p_230241_1_.get("item"));
      return new RightClickBlockWithItemTrigger.Instance(p_230241_2_, locationpredicate, itempredicate);
   }

   public void test(ServerPlayerEntity p_226695_1_, BlockPos p_226695_2_, ItemStack p_226695_3_) {
      BlockState blockstate = p_226695_1_.getServerWorld().getBlockState(p_226695_2_);
      this.func_235959_a_(p_226695_1_, (p_226694_4_) -> {
         return p_226694_4_.func_226700_a_(blockstate, p_226695_1_.getServerWorld(), p_226695_2_, p_226695_3_);
      });
   }

   public static class Instance extends CriterionInstance {
      private final LocationPredicate field_234851_a_;
      private final ItemPredicate field_226698_c_;

      public Instance(EntityPredicate.AndPredicate p_i231602_1_, LocationPredicate p_i231602_2_, ItemPredicate p_i231602_3_) {
         super(RightClickBlockWithItemTrigger.field_234849_a_, p_i231602_1_);
         this.field_234851_a_ = p_i231602_2_;
         this.field_226698_c_ = p_i231602_3_;
      }

      public static RightClickBlockWithItemTrigger.Instance func_234852_a_(LocationPredicate.Builder p_234852_0_, ItemPredicate.Builder p_234852_1_) {
         return new RightClickBlockWithItemTrigger.Instance(EntityPredicate.AndPredicate.field_234582_a_, p_234852_0_.build(), p_234852_1_.build());
      }

      public boolean func_226700_a_(BlockState p_226700_1_, ServerWorld p_226700_2_, BlockPos p_226700_3_, ItemStack p_226700_4_) {
         return !this.field_234851_a_.test(p_226700_2_, (double)p_226700_3_.getX() + 0.5D, (double)p_226700_3_.getY() + 0.5D, (double)p_226700_3_.getZ() + 0.5D) ? false : this.field_226698_c_.test(p_226700_4_);
      }

      public JsonObject func_230240_a_(ConditionArraySerializer p_230240_1_) {
         JsonObject jsonobject = super.func_230240_a_(p_230240_1_);
         jsonobject.add("location", this.field_234851_a_.serialize());
         jsonobject.add("item", this.field_226698_c_.serialize());
         return jsonobject;
      }
   }
}