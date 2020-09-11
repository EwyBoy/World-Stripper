package net.minecraft.loot;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

public class LootEntryManager {
   public static final LootPoolEntryType field_237410_a_ = func_237419_a_("empty", new EmptyLootEntry.Serializer());
   public static final LootPoolEntryType field_237411_b_ = func_237419_a_("item", new ItemLootEntry.Serializer());
   public static final LootPoolEntryType field_237412_c_ = func_237419_a_("loot_table", new TableLootEntry.Serializer());
   public static final LootPoolEntryType field_237413_d_ = func_237419_a_("dynamic", new DynamicLootEntry.Serializer());
   public static final LootPoolEntryType field_237414_e_ = func_237419_a_("tag", new TagLootEntry.Serializer());
   public static final LootPoolEntryType field_237415_f_ = func_237419_a_("alternatives", ParentedLootEntry.func_237409_a_(AlternativesLootEntry::new));
   public static final LootPoolEntryType field_237416_g_ = func_237419_a_("sequence", ParentedLootEntry.func_237409_a_(SequenceLootEntry::new));
   public static final LootPoolEntryType field_237417_h_ = func_237419_a_("group", ParentedLootEntry.func_237409_a_(GroupLootEntry::new));

   private static LootPoolEntryType func_237419_a_(String p_237419_0_, ILootSerializer<? extends LootEntry> p_237419_1_) {
      return Registry.register(Registry.field_239693_aY_, new ResourceLocation(p_237419_0_), new LootPoolEntryType(p_237419_1_));
   }

   public static Object func_237418_a_() {
      return LootTypesManager.func_237389_a_(Registry.field_239693_aY_, "entry", "type", LootEntry::func_230420_a_).func_237395_a_();
   }
}