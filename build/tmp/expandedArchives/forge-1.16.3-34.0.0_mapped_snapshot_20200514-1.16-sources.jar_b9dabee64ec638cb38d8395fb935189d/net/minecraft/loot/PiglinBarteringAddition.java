package net.minecraft.loot;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Items;
import net.minecraft.loot.functions.EnchantRandomly;
import net.minecraft.loot.functions.SetCount;
import net.minecraft.loot.functions.SetNBT;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;

public class PiglinBarteringAddition implements Consumer<BiConsumer<ResourceLocation, LootTable.Builder>> {
   public void accept(BiConsumer<ResourceLocation, LootTable.Builder> p_accept_1_) {
      p_accept_1_.accept(LootTables.field_237385_ay_, LootTable.builder().addLootPool(LootPool.builder().rolls(ConstantRange.of(1)).addEntry(ItemLootEntry.builder(Items.BOOK).weight(5).acceptFunction((new EnchantRandomly.Builder()).func_237424_a_(Enchantments.field_234847_l_))).addEntry(ItemLootEntry.builder(Items.IRON_BOOTS).weight(8).acceptFunction((new EnchantRandomly.Builder()).func_237424_a_(Enchantments.field_234847_l_))).addEntry(ItemLootEntry.builder(Items.POTION).weight(8).acceptFunction(SetNBT.builder(Util.make(new CompoundNBT(), (p_239833_0_) -> {
         p_239833_0_.putString("Potion", "minecraft:fire_resistance");
      })))).addEntry(ItemLootEntry.builder(Items.SPLASH_POTION).weight(8).acceptFunction(SetNBT.builder(Util.make(new CompoundNBT(), (p_239832_0_) -> {
         p_239832_0_.putString("Potion", "minecraft:fire_resistance");
      })))).addEntry(ItemLootEntry.builder(Items.POTION).weight(10).acceptFunction(SetNBT.builder(Util.make(new CompoundNBT(), (p_243684_0_) -> {
         p_243684_0_.putString("Potion", "minecraft:water");
      })))).addEntry(ItemLootEntry.builder(Items.IRON_NUGGET).weight(10).acceptFunction(SetCount.builder(RandomValueRange.of(10.0F, 36.0F)))).addEntry(ItemLootEntry.builder(Items.ENDER_PEARL).weight(10).acceptFunction(SetCount.builder(RandomValueRange.of(2.0F, 4.0F)))).addEntry(ItemLootEntry.builder(Items.STRING).weight(20).acceptFunction(SetCount.builder(RandomValueRange.of(3.0F, 9.0F)))).addEntry(ItemLootEntry.builder(Items.QUARTZ).weight(20).acceptFunction(SetCount.builder(RandomValueRange.of(5.0F, 12.0F)))).addEntry(ItemLootEntry.builder(Items.OBSIDIAN).weight(40)).addEntry(ItemLootEntry.builder(Items.field_234797_rz_).weight(40).acceptFunction(SetCount.builder(RandomValueRange.of(1.0F, 3.0F)))).addEntry(ItemLootEntry.builder(Items.FIRE_CHARGE).weight(40)).addEntry(ItemLootEntry.builder(Items.LEATHER).weight(40).acceptFunction(SetCount.builder(RandomValueRange.of(2.0F, 4.0F)))).addEntry(ItemLootEntry.builder(Items.SOUL_SAND).weight(40).acceptFunction(SetCount.builder(RandomValueRange.of(2.0F, 8.0F)))).addEntry(ItemLootEntry.builder(Items.NETHER_BRICK).weight(40).acceptFunction(SetCount.builder(RandomValueRange.of(2.0F, 8.0F)))).addEntry(ItemLootEntry.builder(Items.SPECTRAL_ARROW).weight(40).acceptFunction(SetCount.builder(RandomValueRange.of(6.0F, 12.0F)))).addEntry(ItemLootEntry.builder(Items.GRAVEL).weight(40).acceptFunction(SetCount.builder(RandomValueRange.of(8.0F, 16.0F)))).addEntry(ItemLootEntry.builder(Items.field_234777_rA_).weight(40).acceptFunction(SetCount.builder(RandomValueRange.of(8.0F, 16.0F))))));
   }
}