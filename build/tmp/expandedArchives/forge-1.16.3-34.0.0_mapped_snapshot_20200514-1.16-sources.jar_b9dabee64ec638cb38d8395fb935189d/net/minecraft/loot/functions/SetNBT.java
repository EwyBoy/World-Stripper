package net.minecraft.loot.functions;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSyntaxException;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootFunction;
import net.minecraft.loot.LootFunctionType;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.util.JSONUtils;

public class SetNBT extends LootFunction {
   private final CompoundNBT tag;

   private SetNBT(ILootCondition[] conditionsIn, CompoundNBT tagIn) {
      super(conditionsIn);
      this.tag = tagIn;
   }

   public LootFunctionType func_230425_b_() {
      return LootFunctionManager.field_237432_e_;
   }

   public ItemStack doApply(ItemStack stack, LootContext context) {
      stack.getOrCreateTag().merge(this.tag);
      return stack;
   }

   public static LootFunction.Builder<?> builder(CompoundNBT p_215952_0_) {
      return builder((p_215951_1_) -> {
         return new SetNBT(p_215951_1_, p_215952_0_);
      });
   }

   public static class Serializer extends LootFunction.Serializer<SetNBT> {
      public void func_230424_a_(JsonObject p_230424_1_, SetNBT p_230424_2_, JsonSerializationContext p_230424_3_) {
         super.func_230424_a_(p_230424_1_, p_230424_2_, p_230424_3_);
         p_230424_1_.addProperty("tag", p_230424_2_.tag.toString());
      }

      public SetNBT deserialize(JsonObject object, JsonDeserializationContext deserializationContext, ILootCondition[] conditionsIn) {
         try {
            CompoundNBT compoundnbt = JsonToNBT.getTagFromJson(JSONUtils.getString(object, "tag"));
            return new SetNBT(conditionsIn, compoundnbt);
         } catch (CommandSyntaxException commandsyntaxexception) {
            throw new JsonSyntaxException(commandsyntaxexception.getMessage());
         }
      }
   }
}