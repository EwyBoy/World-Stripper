package net.minecraft.loot.functions;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.IRandomRange;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootFunction;
import net.minecraft.loot.LootFunctionType;
import net.minecraft.loot.RandomRanges;
import net.minecraft.loot.conditions.ILootCondition;

public class SetCount extends LootFunction {
   private final IRandomRange countRange;

   private SetCount(ILootCondition[] p_i51222_1_, IRandomRange p_i51222_2_) {
      super(p_i51222_1_);
      this.countRange = p_i51222_2_;
   }

   public LootFunctionType func_230425_b_() {
      return LootFunctionManager.field_237429_b_;
   }

   public ItemStack doApply(ItemStack stack, LootContext context) {
      stack.setCount(this.countRange.generateInt(context.getRandom()));
      return stack;
   }

   public static LootFunction.Builder<?> builder(IRandomRange p_215932_0_) {
      return builder((p_215934_1_) -> {
         return new SetCount(p_215934_1_, p_215932_0_);
      });
   }

   public static class Serializer extends LootFunction.Serializer<SetCount> {
      public void func_230424_a_(JsonObject p_230424_1_, SetCount p_230424_2_, JsonSerializationContext p_230424_3_) {
         super.func_230424_a_(p_230424_1_, p_230424_2_, p_230424_3_);
         p_230424_1_.add("count", RandomRanges.serialize(p_230424_2_.countRange, p_230424_3_));
      }

      public SetCount deserialize(JsonObject object, JsonDeserializationContext deserializationContext, ILootCondition[] conditionsIn) {
         IRandomRange irandomrange = RandomRanges.deserialize(object.get("count"), deserializationContext);
         return new SetCount(conditionsIn, irandomrange);
      }
   }
}