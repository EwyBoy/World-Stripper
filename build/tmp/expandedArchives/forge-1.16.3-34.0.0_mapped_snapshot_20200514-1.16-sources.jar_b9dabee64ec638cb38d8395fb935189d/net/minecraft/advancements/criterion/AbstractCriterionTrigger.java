package net.minecraft.advancements.criterion;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonObject;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import net.minecraft.advancements.ICriterionTrigger;
import net.minecraft.advancements.PlayerAdvancements;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.loot.ConditionArrayParser;
import net.minecraft.loot.LootContext;

public abstract class AbstractCriterionTrigger<T extends CriterionInstance> implements ICriterionTrigger<T> {
   private final Map<PlayerAdvancements, Set<ICriterionTrigger.Listener<T>>> field_227069_a_ = Maps.newIdentityHashMap();

   public final void addListener(PlayerAdvancements playerAdvancementsIn, ICriterionTrigger.Listener<T> listener) {
      this.field_227069_a_.computeIfAbsent(playerAdvancementsIn, (p_227072_0_) -> {
         return Sets.newHashSet();
      }).add(listener);
   }

   public final void removeListener(PlayerAdvancements playerAdvancementsIn, ICriterionTrigger.Listener<T> listener) {
      Set<ICriterionTrigger.Listener<T>> set = this.field_227069_a_.get(playerAdvancementsIn);
      if (set != null) {
         set.remove(listener);
         if (set.isEmpty()) {
            this.field_227069_a_.remove(playerAdvancementsIn);
         }
      }

   }

   public final void removeAllListeners(PlayerAdvancements playerAdvancementsIn) {
      this.field_227069_a_.remove(playerAdvancementsIn);
   }

   protected abstract T func_230241_b_(JsonObject p_230241_1_, EntityPredicate.AndPredicate p_230241_2_, ConditionArrayParser p_230241_3_);

   public final T func_230307_a_(JsonObject p_230307_1_, ConditionArrayParser p_230307_2_) {
      EntityPredicate.AndPredicate entitypredicate$andpredicate = EntityPredicate.AndPredicate.func_234587_a_(p_230307_1_, "player", p_230307_2_);
      return this.func_230241_b_(p_230307_1_, entitypredicate$andpredicate, p_230307_2_);
   }

   protected void func_235959_a_(ServerPlayerEntity p_235959_1_, Predicate<T> p_235959_2_) {
      PlayerAdvancements playeradvancements = p_235959_1_.getAdvancements();
      Set<ICriterionTrigger.Listener<T>> set = this.field_227069_a_.get(playeradvancements);
      if (set != null && !set.isEmpty()) {
         LootContext lootcontext = EntityPredicate.func_234575_b_(p_235959_1_, p_235959_1_);
         List<ICriterionTrigger.Listener<T>> list = null;

         for(ICriterionTrigger.Listener<T> listener : set) {
            T t = listener.getCriterionInstance();
            if (t.func_233383_b_().func_234588_a_(lootcontext) && p_235959_2_.test(t)) {
               if (list == null) {
                  list = Lists.newArrayList();
               }

               list.add(listener);
            }
         }

         if (list != null) {
            for(ICriterionTrigger.Listener<T> listener1 : list) {
               listener1.grantCriterion(playeradvancements);
            }
         }

      }
   }
}