package net.minecraft.loot.functions;

import java.util.function.BiFunction;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.ILootSerializer;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootFunctionType;
import net.minecraft.loot.LootTypesManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

public class LootFunctionManager {
   public static final BiFunction<ItemStack, LootContext, ItemStack> IDENTITY = (p_216240_0_, p_216240_1_) -> {
      return p_216240_0_;
   };
   public static final LootFunctionType field_237429_b_ = func_237451_a_("set_count", new SetCount.Serializer());
   public static final LootFunctionType field_237430_c_ = func_237451_a_("enchant_with_levels", new EnchantWithLevels.Serializer());
   public static final LootFunctionType field_237431_d_ = func_237451_a_("enchant_randomly", new EnchantRandomly.Serializer());
   public static final LootFunctionType field_237432_e_ = func_237451_a_("set_nbt", new SetNBT.Serializer());
   public static final LootFunctionType field_237433_f_ = func_237451_a_("furnace_smelt", new Smelt.Serializer());
   public static final LootFunctionType field_237434_g_ = func_237451_a_("looting_enchant", new LootingEnchantBonus.Serializer());
   public static final LootFunctionType field_237435_h_ = func_237451_a_("set_damage", new SetDamage.Serializer());
   public static final LootFunctionType field_237436_i_ = func_237451_a_("set_attributes", new SetAttributes.Serializer());
   public static final LootFunctionType field_237437_j_ = func_237451_a_("set_name", new SetName.Serializer());
   public static final LootFunctionType field_237438_k_ = func_237451_a_("exploration_map", new ExplorationMap.Serializer());
   public static final LootFunctionType field_237439_l_ = func_237451_a_("set_stew_effect", new SetStewEffect.Serializer());
   public static final LootFunctionType field_237440_m_ = func_237451_a_("copy_name", new CopyName.Serializer());
   public static final LootFunctionType field_237441_n_ = func_237451_a_("set_contents", new SetContents.Serializer());
   public static final LootFunctionType field_237442_o_ = func_237451_a_("limit_count", new LimitCount.Serializer());
   public static final LootFunctionType field_237443_p_ = func_237451_a_("apply_bonus", new ApplyBonus.Serializer());
   public static final LootFunctionType field_237444_q_ = func_237451_a_("set_loot_table", new SetLootTable.Serializer());
   public static final LootFunctionType field_237445_r_ = func_237451_a_("explosion_decay", new ExplosionDecay.Serializer());
   public static final LootFunctionType field_237446_s_ = func_237451_a_("set_lore", new SetLore.Serializer());
   public static final LootFunctionType field_237447_t_ = func_237451_a_("fill_player_head", new FillPlayerHead.Serializer());
   public static final LootFunctionType field_237448_u_ = func_237451_a_("copy_nbt", new CopyNbt.Serializer());
   public static final LootFunctionType field_237449_v_ = func_237451_a_("copy_state", new CopyBlockState.Serializer());

   private static LootFunctionType func_237451_a_(String p_237451_0_, ILootSerializer<? extends ILootFunction> p_237451_1_) {
      return Registry.register(Registry.field_239694_aZ_, new ResourceLocation(p_237451_0_), new LootFunctionType(p_237451_1_));
   }

   public static Object func_237450_a_() {
      return LootTypesManager.func_237389_a_(Registry.field_239694_aZ_, "function", "function", ILootFunction::func_230425_b_).func_237395_a_();
   }

   public static BiFunction<ItemStack, LootContext, ItemStack> combine(BiFunction<ItemStack, LootContext, ItemStack>[] p_216241_0_) {
      switch(p_216241_0_.length) {
      case 0:
         return IDENTITY;
      case 1:
         return p_216241_0_[0];
      case 2:
         BiFunction<ItemStack, LootContext, ItemStack> bifunction = p_216241_0_[0];
         BiFunction<ItemStack, LootContext, ItemStack> bifunction1 = p_216241_0_[1];
         return (p_216239_2_, p_216239_3_) -> {
            return bifunction1.apply(bifunction.apply(p_216239_2_, p_216239_3_), p_216239_3_);
         };
      default:
         return (p_216238_1_, p_216238_2_) -> {
            for(BiFunction<ItemStack, LootContext, ItemStack> bifunction2 : p_216241_0_) {
               p_216238_1_ = bifunction2.apply(p_216238_1_, p_216238_2_);
            }

            return p_216238_1_;
         };
      }
   }
}