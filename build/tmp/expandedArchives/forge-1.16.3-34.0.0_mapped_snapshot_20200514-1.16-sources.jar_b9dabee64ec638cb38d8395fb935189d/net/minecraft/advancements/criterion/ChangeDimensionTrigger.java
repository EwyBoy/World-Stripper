package net.minecraft.advancements.criterion;

import com.google.gson.JsonObject;
import javax.annotation.Nullable;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.loot.ConditionArrayParser;
import net.minecraft.loot.ConditionArraySerializer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

public class ChangeDimensionTrigger extends AbstractCriterionTrigger<ChangeDimensionTrigger.Instance> {
   private static final ResourceLocation ID = new ResourceLocation("changed_dimension");

   public ResourceLocation getId() {
      return ID;
   }

   public ChangeDimensionTrigger.Instance func_230241_b_(JsonObject p_230241_1_, EntityPredicate.AndPredicate p_230241_2_, ConditionArrayParser p_230241_3_) {
      RegistryKey<World> registrykey = p_230241_1_.has("from") ? RegistryKey.func_240903_a_(Registry.field_239699_ae_, new ResourceLocation(JSONUtils.getString(p_230241_1_, "from"))) : null;
      RegistryKey<World> registrykey1 = p_230241_1_.has("to") ? RegistryKey.func_240903_a_(Registry.field_239699_ae_, new ResourceLocation(JSONUtils.getString(p_230241_1_, "to"))) : null;
      return new ChangeDimensionTrigger.Instance(p_230241_2_, registrykey, registrykey1);
   }

   public void func_233551_a_(ServerPlayerEntity p_233551_1_, RegistryKey<World> p_233551_2_, RegistryKey<World> p_233551_3_) {
      this.func_235959_a_(p_233551_1_, (p_233550_2_) -> {
         return p_233550_2_.func_233553_b_(p_233551_2_, p_233551_3_);
      });
   }

   public static class Instance extends CriterionInstance {
      @Nullable
      private final RegistryKey<World> from;
      @Nullable
      private final RegistryKey<World> to;

      public Instance(EntityPredicate.AndPredicate p_i231488_1_, @Nullable RegistryKey<World> p_i231488_2_, @Nullable RegistryKey<World> p_i231488_3_) {
         super(ChangeDimensionTrigger.ID, p_i231488_1_);
         this.from = p_i231488_2_;
         this.to = p_i231488_3_;
      }

      public static ChangeDimensionTrigger.Instance func_233552_a_(RegistryKey<World> p_233552_0_) {
         return new ChangeDimensionTrigger.Instance(EntityPredicate.AndPredicate.field_234582_a_, (RegistryKey<World>)null, p_233552_0_);
      }

      public boolean func_233553_b_(RegistryKey<World> p_233553_1_, RegistryKey<World> p_233553_2_) {
         if (this.from != null && this.from != p_233553_1_) {
            return false;
         } else {
            return this.to == null || this.to == p_233553_2_;
         }
      }

      public JsonObject func_230240_a_(ConditionArraySerializer p_230240_1_) {
         JsonObject jsonobject = super.func_230240_a_(p_230240_1_);
         if (this.from != null) {
            jsonobject.addProperty("from", this.from.func_240901_a_().toString());
         }

         if (this.to != null) {
            jsonobject.addProperty("to", this.to.func_240901_a_().toString());
         }

         return jsonobject;
      }
   }
}