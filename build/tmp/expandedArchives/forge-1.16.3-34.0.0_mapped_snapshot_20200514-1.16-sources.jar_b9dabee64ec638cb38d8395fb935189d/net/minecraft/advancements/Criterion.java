package net.minecraft.advancements;

import com.google.common.collect.Maps;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import java.util.Map;
import java.util.Map.Entry;
import javax.annotation.Nullable;
import net.minecraft.loot.ConditionArrayParser;
import net.minecraft.loot.ConditionArraySerializer;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;

public class Criterion {
   private final ICriterionInstance criterionInstance;

   public Criterion(ICriterionInstance p_i47470_1_) {
      this.criterionInstance = p_i47470_1_;
   }

   public Criterion() {
      this.criterionInstance = null;
   }

   public void serializeToNetwork(PacketBuffer p_192140_1_) {
   }

   public static Criterion func_232633_a_(JsonObject p_232633_0_, ConditionArrayParser p_232633_1_) {
      ResourceLocation resourcelocation = new ResourceLocation(JSONUtils.getString(p_232633_0_, "trigger"));
      ICriterionTrigger<?> icriteriontrigger = CriteriaTriggers.get(resourcelocation);
      if (icriteriontrigger == null) {
         throw new JsonSyntaxException("Invalid criterion trigger: " + resourcelocation);
      } else {
         ICriterionInstance icriterioninstance = icriteriontrigger.func_230307_a_(JSONUtils.getJsonObject(p_232633_0_, "conditions", new JsonObject()), p_232633_1_);
         return new Criterion(icriterioninstance);
      }
   }

   public static Criterion criterionFromNetwork(PacketBuffer p_192146_0_) {
      return new Criterion();
   }

   public static Map<String, Criterion> func_232634_b_(JsonObject p_232634_0_, ConditionArrayParser p_232634_1_) {
      Map<String, Criterion> map = Maps.newHashMap();

      for(Entry<String, JsonElement> entry : p_232634_0_.entrySet()) {
         map.put(entry.getKey(), func_232633_a_(JSONUtils.getJsonObject(entry.getValue(), "criterion"), p_232634_1_));
      }

      return map;
   }

   /**
    * Read criteria from {@code buf}.
    * 
    * @return the read criteria. Each key-value pair consists of a {@code Criterion} and its name.
    * @see #serializeToNetwork(Map, PacketBuffer)
    */
   public static Map<String, Criterion> criteriaFromNetwork(PacketBuffer bus) {
      Map<String, Criterion> map = Maps.newHashMap();
      int i = bus.readVarInt();

      for(int j = 0; j < i; ++j) {
         map.put(bus.readString(32767), criterionFromNetwork(bus));
      }

      return map;
   }

   /**
    * Write {@code criteria} to {@code buf}.
    * 
    * @see #criteriaFromNetwork(PacketBuffer)
    */
   public static void serializeToNetwork(Map<String, Criterion> criteria, PacketBuffer buf) {
      buf.writeVarInt(criteria.size());

      for(Entry<String, Criterion> entry : criteria.entrySet()) {
         buf.writeString(entry.getKey());
         entry.getValue().serializeToNetwork(buf);
      }

   }

   @Nullable
   public ICriterionInstance getCriterionInstance() {
      return this.criterionInstance;
   }

   public JsonElement serialize() {
      JsonObject jsonobject = new JsonObject();
      jsonobject.addProperty("trigger", this.criterionInstance.getId().toString());
      JsonObject jsonobject1 = this.criterionInstance.func_230240_a_(ConditionArraySerializer.field_235679_a_);
      if (jsonobject1.size() != 0) {
         jsonobject.add("conditions", jsonobject1);
      }

      return jsonobject;
   }
}