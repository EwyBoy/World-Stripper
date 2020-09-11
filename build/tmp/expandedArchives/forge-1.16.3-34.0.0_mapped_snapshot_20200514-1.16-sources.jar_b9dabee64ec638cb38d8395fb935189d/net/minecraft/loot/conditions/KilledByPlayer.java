package net.minecraft.loot.conditions;

import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.Set;
import net.minecraft.loot.ILootSerializer;
import net.minecraft.loot.LootConditionType;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootParameter;
import net.minecraft.loot.LootParameters;

public class KilledByPlayer implements ILootCondition {
   private static final KilledByPlayer INSTANCE = new KilledByPlayer();

   private KilledByPlayer() {
   }

   public LootConditionType func_230419_b_() {
      return LootConditionManager.field_237463_f_;
   }

   public Set<LootParameter<?>> getRequiredParameters() {
      return ImmutableSet.of(LootParameters.LAST_DAMAGE_PLAYER);
   }

   public boolean test(LootContext p_test_1_) {
      return p_test_1_.has(LootParameters.LAST_DAMAGE_PLAYER);
   }

   public static ILootCondition.IBuilder builder() {
      return () -> {
         return INSTANCE;
      };
   }

   public static class Serializer implements ILootSerializer<KilledByPlayer> {
      public void func_230424_a_(JsonObject p_230424_1_, KilledByPlayer p_230424_2_, JsonSerializationContext p_230424_3_) {
      }

      public KilledByPlayer func_230423_a_(JsonObject p_230423_1_, JsonDeserializationContext p_230423_2_) {
         return KilledByPlayer.INSTANCE;
      }
   }
}