package net.minecraft.advancements;

import com.google.gson.JsonObject;
import net.minecraft.loot.ConditionArraySerializer;
import net.minecraft.util.ResourceLocation;

public interface ICriterionInstance {
   ResourceLocation getId();

   JsonObject func_230240_a_(ConditionArraySerializer p_230240_1_);
}