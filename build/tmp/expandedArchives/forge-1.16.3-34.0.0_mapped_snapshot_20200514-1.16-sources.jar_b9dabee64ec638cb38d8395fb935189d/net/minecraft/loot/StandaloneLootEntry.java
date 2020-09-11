package net.minecraft.loot;

import com.google.common.collect.Lists;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.loot.functions.ILootFunction;
import net.minecraft.loot.functions.LootFunctionManager;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.math.MathHelper;
import org.apache.commons.lang3.ArrayUtils;

public abstract class StandaloneLootEntry extends LootEntry {
   /** The weight of the entry. */
   protected final int weight;
   /** The quality of the entry. */
   protected final int quality;
   /** Functions that are ran on the entry. */
   protected final ILootFunction[] functions;
   private final BiFunction<ItemStack, LootContext, ItemStack> field_216157_c;
   private final ILootGenerator field_216161_h = new StandaloneLootEntry.Generator() {
      public void func_216188_a(Consumer<ItemStack> p_216188_1_, LootContext p_216188_2_) {
         StandaloneLootEntry.this.func_216154_a(ILootFunction.func_215858_a(StandaloneLootEntry.this.field_216157_c, p_216188_1_, p_216188_2_), p_216188_2_);
      }
   };

   protected StandaloneLootEntry(int weightIn, int qualityIn, ILootCondition[] conditionsIn, ILootFunction[] functionsIn) {
      super(conditionsIn);
      this.weight = weightIn;
      this.quality = qualityIn;
      this.functions = functionsIn;
      this.field_216157_c = LootFunctionManager.combine(functionsIn);
   }

   public void func_225579_a_(ValidationTracker p_225579_1_) {
      super.func_225579_a_(p_225579_1_);

      for(int i = 0; i < this.functions.length; ++i) {
         this.functions[i].func_225580_a_(p_225579_1_.func_227534_b_(".functions[" + i + "]"));
      }

   }

   protected abstract void func_216154_a(Consumer<ItemStack> p_216154_1_, LootContext p_216154_2_);

   public boolean expand(LootContext p_expand_1_, Consumer<ILootGenerator> p_expand_2_) {
      if (this.test(p_expand_1_)) {
         p_expand_2_.accept(this.field_216161_h);
         return true;
      } else {
         return false;
      }
   }

   public static StandaloneLootEntry.Builder<?> builder(StandaloneLootEntry.ILootEntryBuilder entryBuilderIn) {
      return new StandaloneLootEntry.BuilderImpl(entryBuilderIn);
   }

   public abstract static class Builder<T extends StandaloneLootEntry.Builder<T>> extends LootEntry.Builder<T> implements ILootFunctionConsumer<T> {
      protected int weight = 1;
      protected int quality = 0;
      private final List<ILootFunction> functions = Lists.newArrayList();

      public T acceptFunction(ILootFunction.IBuilder functionBuilder) {
         this.functions.add(functionBuilder.build());
         return this.func_212845_d_();
      }

      /**
       * Creates an array from the functions list
       */
      protected ILootFunction[] getFunctions() {
         return this.functions.toArray(new ILootFunction[0]);
      }

      public T weight(int weightIn) {
         this.weight = weightIn;
         return this.func_212845_d_();
      }

      public T quality(int qualityIn) {
         this.quality = qualityIn;
         return this.func_212845_d_();
      }
   }

   static class BuilderImpl extends StandaloneLootEntry.Builder<StandaloneLootEntry.BuilderImpl> {
      private final StandaloneLootEntry.ILootEntryBuilder field_216090_c;

      public BuilderImpl(StandaloneLootEntry.ILootEntryBuilder p_i50485_1_) {
         this.field_216090_c = p_i50485_1_;
      }

      protected StandaloneLootEntry.BuilderImpl func_212845_d_() {
         return this;
      }

      public LootEntry build() {
         return this.field_216090_c.build(this.weight, this.quality, this.func_216079_f(), this.getFunctions());
      }
   }

   public abstract class Generator implements ILootGenerator {
      protected Generator() {
      }

      /**
       * Gets the effective weight based on the loot entry's weight and quality multiplied by looter's luck.
       */
      public int getEffectiveWeight(float luck) {
         return Math.max(MathHelper.floor((float)StandaloneLootEntry.this.weight + (float)StandaloneLootEntry.this.quality * luck), 0);
      }
   }

   @FunctionalInterface
   public interface ILootEntryBuilder {
      StandaloneLootEntry build(int p_build_1_, int p_build_2_, ILootCondition[] p_build_3_, ILootFunction[] p_build_4_);
   }

   public abstract static class Serializer<T extends StandaloneLootEntry> extends LootEntry.Serializer<T> {
      public void func_230422_a_(JsonObject p_230422_1_, T p_230422_2_, JsonSerializationContext p_230422_3_) {
         if (p_230422_2_.weight != 1) {
            p_230422_1_.addProperty("weight", p_230422_2_.weight);
         }

         if (p_230422_2_.quality != 0) {
            p_230422_1_.addProperty("quality", p_230422_2_.quality);
         }

         if (!ArrayUtils.isEmpty((Object[])p_230422_2_.functions)) {
            p_230422_1_.add("functions", p_230422_3_.serialize(p_230422_2_.functions));
         }

      }

      public final T func_230421_b_(JsonObject p_230421_1_, JsonDeserializationContext p_230421_2_, ILootCondition[] p_230421_3_) {
         int i = JSONUtils.getInt(p_230421_1_, "weight", 1);
         int j = JSONUtils.getInt(p_230421_1_, "quality", 0);
         ILootFunction[] ailootfunction = JSONUtils.deserializeClass(p_230421_1_, "functions", new ILootFunction[0], p_230421_2_, ILootFunction[].class);
         return this.func_212829_b_(p_230421_1_, p_230421_2_, i, j, p_230421_3_, ailootfunction);
      }

      protected abstract T func_212829_b_(JsonObject p_212829_1_, JsonDeserializationContext p_212829_2_, int p_212829_3_, int p_212829_4_, ILootCondition[] p_212829_5_, ILootFunction[] p_212829_6_);
   }
}