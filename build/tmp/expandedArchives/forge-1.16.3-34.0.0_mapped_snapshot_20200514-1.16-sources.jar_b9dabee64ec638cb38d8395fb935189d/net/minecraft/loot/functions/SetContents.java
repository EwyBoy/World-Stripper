package net.minecraft.loot.functions;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.Arrays;
import java.util.List;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootEntry;
import net.minecraft.loot.LootFunction;
import net.minecraft.loot.LootFunctionType;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.ValidationTracker;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.NonNullList;

public class SetContents extends LootFunction {
   private final List<LootEntry> field_215924_a;

   private SetContents(ILootCondition[] p_i51226_1_, List<LootEntry> p_i51226_2_) {
      super(p_i51226_1_);
      this.field_215924_a = ImmutableList.copyOf(p_i51226_2_);
   }

   public LootFunctionType func_230425_b_() {
      return LootFunctionManager.field_237441_n_;
   }

   public ItemStack doApply(ItemStack stack, LootContext context) {
      if (stack.isEmpty()) {
         return stack;
      } else {
         NonNullList<ItemStack> nonnulllist = NonNullList.create();
         this.field_215924_a.forEach((p_215921_2_) -> {
            p_215921_2_.expand(context, (p_215922_2_) -> {
               p_215922_2_.func_216188_a(LootTable.capStackSizes(nonnulllist::add), context);
            });
         });
         CompoundNBT compoundnbt = new CompoundNBT();
         ItemStackHelper.saveAllItems(compoundnbt, nonnulllist);
         CompoundNBT compoundnbt1 = stack.getOrCreateTag();
         compoundnbt1.put("BlockEntityTag", compoundnbt.merge(compoundnbt1.getCompound("BlockEntityTag")));
         return stack;
      }
   }

   public void func_225580_a_(ValidationTracker p_225580_1_) {
      super.func_225580_a_(p_225580_1_);

      for(int i = 0; i < this.field_215924_a.size(); ++i) {
         this.field_215924_a.get(i).func_225579_a_(p_225580_1_.func_227534_b_(".entry[" + i + "]"));
      }

   }

   public static SetContents.Builder func_215920_b() {
      return new SetContents.Builder();
   }

   public static class Builder extends LootFunction.Builder<SetContents.Builder> {
      private final List<LootEntry> field_216076_a = Lists.newArrayList();

      protected SetContents.Builder doCast() {
         return this;
      }

      public SetContents.Builder func_216075_a(LootEntry.Builder<?> p_216075_1_) {
         this.field_216076_a.add(p_216075_1_.build());
         return this;
      }

      public ILootFunction build() {
         return new SetContents(this.getConditions(), this.field_216076_a);
      }
   }

   public static class Serializer extends LootFunction.Serializer<SetContents> {
      public void func_230424_a_(JsonObject p_230424_1_, SetContents p_230424_2_, JsonSerializationContext p_230424_3_) {
         super.func_230424_a_(p_230424_1_, p_230424_2_, p_230424_3_);
         p_230424_1_.add("entries", p_230424_3_.serialize(p_230424_2_.field_215924_a));
      }

      public SetContents deserialize(JsonObject object, JsonDeserializationContext deserializationContext, ILootCondition[] conditionsIn) {
         LootEntry[] alootentry = JSONUtils.deserializeClass(object, "entries", deserializationContext, LootEntry[].class);
         return new SetContents(conditionsIn, Arrays.asList(alootentry));
      }
   }
}