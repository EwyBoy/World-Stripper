package net.minecraft.advancements.criterion;

import com.google.gson.JsonObject;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.ConditionArrayParser;
import net.minecraft.loot.ConditionArraySerializer;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;

public class ShotCrossbowTrigger extends AbstractCriterionTrigger<ShotCrossbowTrigger.Instance> {
   private static final ResourceLocation ID = new ResourceLocation("shot_crossbow");

   public ResourceLocation getId() {
      return ID;
   }

   public ShotCrossbowTrigger.Instance func_230241_b_(JsonObject p_230241_1_, EntityPredicate.AndPredicate p_230241_2_, ConditionArrayParser p_230241_3_) {
      ItemPredicate itempredicate = ItemPredicate.deserialize(p_230241_1_.get("item"));
      return new ShotCrossbowTrigger.Instance(p_230241_2_, itempredicate);
   }

   public void func_215111_a(ServerPlayerEntity shooter, ItemStack stack) {
      this.func_235959_a_(shooter, (p_227037_1_) -> {
         return p_227037_1_.func_215121_a(stack);
      });
   }

   public static class Instance extends CriterionInstance {
      private final ItemPredicate itemPredicate;

      public Instance(EntityPredicate.AndPredicate p_i231880_1_, ItemPredicate p_i231880_2_) {
         super(ShotCrossbowTrigger.ID, p_i231880_1_);
         this.itemPredicate = p_i231880_2_;
      }

      public static ShotCrossbowTrigger.Instance create(IItemProvider itemProvider) {
         return new ShotCrossbowTrigger.Instance(EntityPredicate.AndPredicate.field_234582_a_, ItemPredicate.Builder.create().item(itemProvider).build());
      }

      public boolean func_215121_a(ItemStack p_215121_1_) {
         return this.itemPredicate.test(p_215121_1_);
      }

      public JsonObject func_230240_a_(ConditionArraySerializer p_230240_1_) {
         JsonObject jsonobject = super.func_230240_a_(p_230240_1_);
         jsonobject.add("item", this.itemPredicate.serialize());
         return jsonobject;
      }
   }
}