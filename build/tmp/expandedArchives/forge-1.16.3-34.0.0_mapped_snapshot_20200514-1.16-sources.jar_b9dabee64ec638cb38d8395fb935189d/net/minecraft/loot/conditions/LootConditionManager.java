package net.minecraft.loot.conditions;

import java.util.function.Predicate;
import net.minecraft.loot.ILootSerializer;
import net.minecraft.loot.LootConditionType;
import net.minecraft.loot.LootTypesManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

public class LootConditionManager {
   public static final LootConditionType field_237458_a_ = func_237475_a_("inverted", new Inverted.Serializer());
   public static final LootConditionType field_237459_b_ = func_237475_a_("alternative", new Alternative.Serializer());
   public static final LootConditionType field_237460_c_ = func_237475_a_("random_chance", new RandomChance.Serializer());
   public static final LootConditionType field_237461_d_ = func_237475_a_("random_chance_with_looting", new RandomChanceWithLooting.Serializer());
   public static final LootConditionType field_237462_e_ = func_237475_a_("entity_properties", new EntityHasProperty.Serializer());
   public static final LootConditionType field_237463_f_ = func_237475_a_("killed_by_player", new KilledByPlayer.Serializer());
   public static final LootConditionType field_237464_g_ = func_237475_a_("entity_scores", new EntityHasScore.Serializer());
   public static final LootConditionType field_237465_h_ = func_237475_a_("block_state_property", new BlockStateProperty.Serializer());
   public static final LootConditionType field_237466_i_ = func_237475_a_("match_tool", new MatchTool.Serializer());
   public static final LootConditionType field_237467_j_ = func_237475_a_("table_bonus", new TableBonus.Serializer());
   public static final LootConditionType field_237468_k_ = func_237475_a_("survives_explosion", new SurvivesExplosion.Serializer());
   public static final LootConditionType field_237469_l_ = func_237475_a_("damage_source_properties", new DamageSourceProperties.Serializer());
   public static final LootConditionType field_237470_m_ = func_237475_a_("location_check", new LocationCheck.Serializer());
   public static final LootConditionType field_237471_n_ = func_237475_a_("weather_check", new WeatherCheck.Serializer());
   public static final LootConditionType field_237472_o_ = func_237475_a_("reference", new Reference.Serializer());
   public static final LootConditionType field_237473_p_ = func_237475_a_("time_check", new TimeCheck.Serializer());

   private static LootConditionType func_237475_a_(String p_237475_0_, ILootSerializer<? extends ILootCondition> p_237475_1_) {
      return Registry.register(Registry.field_239704_ba_, new ResourceLocation(p_237475_0_), new LootConditionType(p_237475_1_));
   }

   public static Object func_237474_a_() {
      return LootTypesManager.func_237389_a_(Registry.field_239704_ba_, "condition", "condition", ILootCondition::func_230419_b_).func_237395_a_();
   }

   public static <T> Predicate<T> and(Predicate<T>[] p_216305_0_) {
      switch(p_216305_0_.length) {
      case 0:
         return (p_216304_0_) -> {
            return true;
         };
      case 1:
         return p_216305_0_[0];
      case 2:
         return p_216305_0_[0].and(p_216305_0_[1]);
      default:
         return (p_216307_1_) -> {
            for(Predicate<T> predicate : p_216305_0_) {
               if (!predicate.test(p_216307_1_)) {
                  return false;
               }
            }

            return true;
         };
      }
   }

   public static <T> Predicate<T> or(Predicate<T>[] p_216306_0_) {
      switch(p_216306_0_.length) {
      case 0:
         return (p_216308_0_) -> {
            return false;
         };
      case 1:
         return p_216306_0_[0];
      case 2:
         return p_216306_0_[0].or(p_216306_0_[1]);
      default:
         return (p_216309_1_) -> {
            for(Predicate<T> predicate : p_216306_0_) {
               if (predicate.test(p_216309_1_)) {
                  return true;
               }
            }

            return false;
         };
      }
   }
}