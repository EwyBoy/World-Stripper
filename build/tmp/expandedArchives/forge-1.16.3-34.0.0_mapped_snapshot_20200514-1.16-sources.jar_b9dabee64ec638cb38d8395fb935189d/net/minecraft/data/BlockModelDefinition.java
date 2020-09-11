package net.minecraft.data;

import com.google.common.collect.Maps;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class BlockModelDefinition implements Supplier<JsonElement> {
   private final Map<BlockModeInfo<?>, BlockModeInfo<?>.Field> field_240193_a_ = Maps.newLinkedHashMap();

   public <T> BlockModelDefinition func_240198_a_(BlockModeInfo<T> p_240198_1_, T p_240198_2_) {
      BlockModeInfo<?>.Field blockmodeinfo = this.field_240193_a_.put(p_240198_1_, p_240198_1_.func_240213_a_(p_240198_2_));
      if (blockmodeinfo != null) {
         throw new IllegalStateException("Replacing value of " + blockmodeinfo + " with " + p_240198_2_);
      } else {
         return this;
      }
   }

   public static BlockModelDefinition func_240194_a_() {
      return new BlockModelDefinition();
   }

   public static BlockModelDefinition func_240197_a_(BlockModelDefinition p_240197_0_, BlockModelDefinition p_240197_1_) {
      BlockModelDefinition blockmodeldefinition = new BlockModelDefinition();
      blockmodeldefinition.field_240193_a_.putAll(p_240197_0_.field_240193_a_);
      blockmodeldefinition.field_240193_a_.putAll(p_240197_1_.field_240193_a_);
      return blockmodeldefinition;
   }

   public JsonElement get() {
      JsonObject jsonobject = new JsonObject();
      this.field_240193_a_.values().forEach((p_240196_1_) -> {
         p_240196_1_.func_240217_a_(jsonobject);
      });
      return jsonobject;
   }

   public static JsonElement func_240199_a_(List<BlockModelDefinition> p_240199_0_) {
      if (p_240199_0_.size() == 1) {
         return p_240199_0_.get(0).get();
      } else {
         JsonArray jsonarray = new JsonArray();
         p_240199_0_.forEach((p_240195_1_) -> {
            jsonarray.add(p_240195_1_.get());
         });
         return jsonarray;
      }
   }
}