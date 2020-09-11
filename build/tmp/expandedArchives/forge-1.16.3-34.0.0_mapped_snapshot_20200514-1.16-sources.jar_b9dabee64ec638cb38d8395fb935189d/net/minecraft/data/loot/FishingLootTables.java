package net.minecraft.data.loot;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.advancements.criterion.LocationPredicate;
import net.minecraft.block.Blocks;
import net.minecraft.item.Items;
import net.minecraft.loot.ConstantRange;
import net.minecraft.loot.FishingPredicate;
import net.minecraft.loot.ItemLootEntry;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.RandomValueRange;
import net.minecraft.loot.TableLootEntry;
import net.minecraft.loot.conditions.EntityHasProperty;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.loot.conditions.LocationCheck;
import net.minecraft.loot.functions.EnchantWithLevels;
import net.minecraft.loot.functions.SetCount;
import net.minecraft.loot.functions.SetDamage;
import net.minecraft.loot.functions.SetNBT;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.world.biome.Biomes;

public class FishingLootTables implements Consumer<BiConsumer<ResourceLocation, LootTable.Builder>> {
   public static final ILootCondition.IBuilder IN_JUNGLE = LocationCheck.builder(LocationPredicate.Builder.func_226870_a_().func_242666_a(Biomes.JUNGLE));
   public static final ILootCondition.IBuilder IN_JUNGLE_HILLS = LocationCheck.builder(LocationPredicate.Builder.func_226870_a_().func_242666_a(Biomes.JUNGLE_HILLS));
   public static final ILootCondition.IBuilder IN_JUNGLE_EDGE = LocationCheck.builder(LocationPredicate.Builder.func_226870_a_().func_242666_a(Biomes.JUNGLE_EDGE));
   public static final ILootCondition.IBuilder IN_BAMBOO_JUNGLE = LocationCheck.builder(LocationPredicate.Builder.func_226870_a_().func_242666_a(Biomes.BAMBOO_JUNGLE));
   public static final ILootCondition.IBuilder IN_MODIFIED_JUNGLE = LocationCheck.builder(LocationPredicate.Builder.func_226870_a_().func_242666_a(Biomes.MODIFIED_JUNGLE));
   public static final ILootCondition.IBuilder IN_MODIFIED_JUNGLE_EDGE = LocationCheck.builder(LocationPredicate.Builder.func_226870_a_().func_242666_a(Biomes.MODIFIED_JUNGLE_EDGE));
   public static final ILootCondition.IBuilder IN_BAMBOO_JUNGLE_HILLS = LocationCheck.builder(LocationPredicate.Builder.func_226870_a_().func_242666_a(Biomes.BAMBOO_JUNGLE_HILLS));

   public void accept(BiConsumer<ResourceLocation, LootTable.Builder> p_accept_1_) {
      p_accept_1_.accept(LootTables.GAMEPLAY_FISHING, LootTable.builder().addLootPool(LootPool.builder().rolls(ConstantRange.of(1)).addEntry(TableLootEntry.builder(LootTables.GAMEPLAY_FISHING_JUNK).weight(10).quality(-2)).addEntry(TableLootEntry.builder(LootTables.GAMEPLAY_FISHING_TREASURE).weight(5).quality(2).acceptCondition(EntityHasProperty.builder(LootContext.EntityTarget.THIS, EntityPredicate.Builder.create().func_234580_a_(FishingPredicate.func_234640_a_(true))))).addEntry(TableLootEntry.builder(LootTables.GAMEPLAY_FISHING_FISH).weight(85).quality(-1))));
      p_accept_1_.accept(LootTables.GAMEPLAY_FISHING_FISH, LootTable.builder().addLootPool(LootPool.builder().addEntry(ItemLootEntry.builder(Items.COD).weight(60)).addEntry(ItemLootEntry.builder(Items.SALMON).weight(25)).addEntry(ItemLootEntry.builder(Items.TROPICAL_FISH).weight(2)).addEntry(ItemLootEntry.builder(Items.PUFFERFISH).weight(13))));
      p_accept_1_.accept(LootTables.GAMEPLAY_FISHING_JUNK, LootTable.builder().addLootPool(LootPool.builder().addEntry(ItemLootEntry.builder(Blocks.LILY_PAD).weight(17)).addEntry(ItemLootEntry.builder(Items.LEATHER_BOOTS).weight(10).acceptFunction(SetDamage.func_215931_a(RandomValueRange.of(0.0F, 0.9F)))).addEntry(ItemLootEntry.builder(Items.LEATHER).weight(10)).addEntry(ItemLootEntry.builder(Items.BONE).weight(10)).addEntry(ItemLootEntry.builder(Items.POTION).weight(10).acceptFunction(SetNBT.builder(Util.make(new CompoundNBT(), (p_218588_0_) -> {
         p_218588_0_.putString("Potion", "minecraft:water");
      })))).addEntry(ItemLootEntry.builder(Items.STRING).weight(5)).addEntry(ItemLootEntry.builder(Items.FISHING_ROD).weight(2).acceptFunction(SetDamage.func_215931_a(RandomValueRange.of(0.0F, 0.9F)))).addEntry(ItemLootEntry.builder(Items.BOWL).weight(10)).addEntry(ItemLootEntry.builder(Items.STICK).weight(5)).addEntry(ItemLootEntry.builder(Items.INK_SAC).weight(1).acceptFunction(SetCount.builder(ConstantRange.of(10)))).addEntry(ItemLootEntry.builder(Blocks.TRIPWIRE_HOOK).weight(10)).addEntry(ItemLootEntry.builder(Items.ROTTEN_FLESH).weight(10)).addEntry(ItemLootEntry.builder(Blocks.BAMBOO).acceptCondition(IN_JUNGLE.alternative(IN_JUNGLE_HILLS).alternative(IN_JUNGLE_EDGE).alternative(IN_BAMBOO_JUNGLE).alternative(IN_MODIFIED_JUNGLE).alternative(IN_MODIFIED_JUNGLE_EDGE).alternative(IN_BAMBOO_JUNGLE_HILLS)).weight(10))));
      p_accept_1_.accept(LootTables.GAMEPLAY_FISHING_TREASURE, LootTable.builder().addLootPool(LootPool.builder().addEntry(ItemLootEntry.builder(Items.NAME_TAG)).addEntry(ItemLootEntry.builder(Items.SADDLE)).addEntry(ItemLootEntry.builder(Items.BOW).acceptFunction(SetDamage.func_215931_a(RandomValueRange.of(0.0F, 0.25F))).acceptFunction(EnchantWithLevels.func_215895_a(ConstantRange.of(30)).func_216059_e())).addEntry(ItemLootEntry.builder(Items.FISHING_ROD).acceptFunction(SetDamage.func_215931_a(RandomValueRange.of(0.0F, 0.25F))).acceptFunction(EnchantWithLevels.func_215895_a(ConstantRange.of(30)).func_216059_e())).addEntry(ItemLootEntry.builder(Items.BOOK).acceptFunction(EnchantWithLevels.func_215895_a(ConstantRange.of(30)).func_216059_e())).addEntry(ItemLootEntry.builder(Items.NAUTILUS_SHELL))));
   }
}