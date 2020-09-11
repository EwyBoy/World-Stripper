package net.minecraft.loot;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.function.Consumer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.loot.functions.ILootFunction;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

public class ItemLootEntry extends StandaloneLootEntry {
   private final Item item;

   private ItemLootEntry(Item itemIn, int weightIn, int qualityIn, ILootCondition[] conditionsIn, ILootFunction[] functionsIn) {
      super(weightIn, qualityIn, conditionsIn, functionsIn);
      this.item = itemIn;
   }

   public LootPoolEntryType func_230420_a_() {
      return LootEntryManager.field_237411_b_;
   }

   public void func_216154_a(Consumer<ItemStack> p_216154_1_, LootContext p_216154_2_) {
      p_216154_1_.accept(new ItemStack(this.item));
   }

   public static StandaloneLootEntry.Builder<?> builder(IItemProvider itemIn) {
      return builder((p_216169_1_, p_216169_2_, p_216169_3_, p_216169_4_) -> {
         return new ItemLootEntry(itemIn.asItem(), p_216169_1_, p_216169_2_, p_216169_3_, p_216169_4_);
      });
   }

   public static class Serializer extends StandaloneLootEntry.Serializer<ItemLootEntry> {
      public void func_230422_a_(JsonObject p_230422_1_, ItemLootEntry p_230422_2_, JsonSerializationContext p_230422_3_) {
         super.func_230422_a_(p_230422_1_, p_230422_2_, p_230422_3_);
         ResourceLocation resourcelocation = Registry.ITEM.getKey(p_230422_2_.item);
         if (resourcelocation == null) {
            throw new IllegalArgumentException("Can't serialize unknown item " + p_230422_2_.item);
         } else {
            p_230422_1_.addProperty("name", resourcelocation.toString());
         }
      }

      protected ItemLootEntry func_212829_b_(JsonObject p_212829_1_, JsonDeserializationContext p_212829_2_, int p_212829_3_, int p_212829_4_, ILootCondition[] p_212829_5_, ILootFunction[] p_212829_6_) {
         Item item = JSONUtils.getItem(p_212829_1_, "name");
         return new ItemLootEntry(item, p_212829_3_, p_212829_4_, p_212829_5_, p_212829_6_);
      }
   }
}