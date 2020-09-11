package net.minecraft.advancements.criterion;

import com.google.gson.JsonObject;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.loot.ConditionArrayParser;
import net.minecraft.loot.ConditionArraySerializer;
import net.minecraft.loot.LootContext;
import net.minecraft.util.ResourceLocation;

public class ChanneledLightningTrigger extends AbstractCriterionTrigger<ChanneledLightningTrigger.Instance> {
   private static final ResourceLocation ID = new ResourceLocation("channeled_lightning");

   public ResourceLocation getId() {
      return ID;
   }

   public ChanneledLightningTrigger.Instance func_230241_b_(JsonObject p_230241_1_, EntityPredicate.AndPredicate p_230241_2_, ConditionArrayParser p_230241_3_) {
      EntityPredicate.AndPredicate[] aentitypredicate$andpredicate = EntityPredicate.AndPredicate.func_234592_b_(p_230241_1_, "victims", p_230241_3_);
      return new ChanneledLightningTrigger.Instance(p_230241_2_, aentitypredicate$andpredicate);
   }

   public void trigger(ServerPlayerEntity player, Collection<? extends Entity> entityTriggered) {
      List<LootContext> list = entityTriggered.stream().map((p_233674_1_) -> {
         return EntityPredicate.func_234575_b_(player, p_233674_1_);
      }).collect(Collectors.toList());
      this.func_235959_a_(player, (p_233673_1_) -> {
         return p_233673_1_.func_233676_a_(list);
      });
   }

   public static class Instance extends CriterionInstance {
      private final EntityPredicate.AndPredicate[] victims;

      public Instance(EntityPredicate.AndPredicate p_i231493_1_, EntityPredicate.AndPredicate[] p_i231493_2_) {
         super(ChanneledLightningTrigger.ID, p_i231493_1_);
         this.victims = p_i231493_2_;
      }

      public static ChanneledLightningTrigger.Instance channeledLightning(EntityPredicate... p_204824_0_) {
         return new ChanneledLightningTrigger.Instance(EntityPredicate.AndPredicate.field_234582_a_, Stream.of(p_204824_0_).map(EntityPredicate.AndPredicate::func_234585_a_).toArray((p_233675_0_) -> {
            return new EntityPredicate.AndPredicate[p_233675_0_];
         }));
      }

      public boolean func_233676_a_(Collection<? extends LootContext> p_233676_1_) {
         for(EntityPredicate.AndPredicate entitypredicate$andpredicate : this.victims) {
            boolean flag = false;

            for(LootContext lootcontext : p_233676_1_) {
               if (entitypredicate$andpredicate.func_234588_a_(lootcontext)) {
                  flag = true;
                  break;
               }
            }

            if (!flag) {
               return false;
            }
         }

         return true;
      }

      public JsonObject func_230240_a_(ConditionArraySerializer p_230240_1_) {
         JsonObject jsonobject = super.func_230240_a_(p_230240_1_);
         jsonobject.add("victims", EntityPredicate.AndPredicate.func_234590_a_(this.victims, p_230240_1_));
         return jsonobject;
      }
   }
}