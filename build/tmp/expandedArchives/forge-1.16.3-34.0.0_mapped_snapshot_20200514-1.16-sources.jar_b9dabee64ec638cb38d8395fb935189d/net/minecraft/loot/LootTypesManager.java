package net.minecraft.loot;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonSyntaxException;
import com.mojang.datafixers.util.Pair;
import java.lang.reflect.Type;
import java.util.function.Function;
import javax.annotation.Nullable;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

public class LootTypesManager {
   public static <E, T extends LootType<E>> LootTypesManager.LootTypeRegistryWrapper<E, T> func_237389_a_(Registry<T> p_237389_0_, String p_237389_1_, String p_237389_2_, Function<E, T> p_237389_3_) {
      return new LootTypesManager.LootTypeRegistryWrapper<>(p_237389_0_, p_237389_1_, p_237389_2_, p_237389_3_);
   }

   public interface ISerializer<T> {
      JsonElement func_237397_a_(T p_237397_1_, JsonSerializationContext p_237397_2_);

      T func_237396_a_(JsonElement p_237396_1_, JsonDeserializationContext p_237396_2_);
   }

   public static class LootTypeRegistryWrapper<E, T extends LootType<E>> {
      private final Registry<T> field_237390_a_;
      private final String field_237391_b_;
      private final String field_237392_c_;
      private final Function<E, T> field_237393_d_;
      @Nullable
      private Pair<T, LootTypesManager.ISerializer<? extends E>> field_237394_e_;

      private LootTypeRegistryWrapper(Registry<T> p_i232160_1_, String p_i232160_2_, String p_i232160_3_, Function<E, T> p_i232160_4_) {
         this.field_237390_a_ = p_i232160_1_;
         this.field_237391_b_ = p_i232160_2_;
         this.field_237392_c_ = p_i232160_3_;
         this.field_237393_d_ = p_i232160_4_;
      }

      public Object func_237395_a_() {
         return new LootTypesManager.Serializer(this.field_237390_a_, this.field_237391_b_, this.field_237392_c_, this.field_237393_d_, this.field_237394_e_);
      }
   }

   static class Serializer<E, T extends LootType<E>> implements JsonDeserializer<E>, JsonSerializer<E> {
      private final Registry<T> field_237398_a_;
      private final String field_237399_b_;
      private final String field_237400_c_;
      private final Function<E, T> field_237401_d_;
      @Nullable
      private final Pair<T, LootTypesManager.ISerializer<? extends E>> field_237402_e_;

      private Serializer(Registry<T> p_i232162_1_, String p_i232162_2_, String p_i232162_3_, Function<E, T> p_i232162_4_, @Nullable Pair<T, LootTypesManager.ISerializer<? extends E>> p_i232162_5_) {
         this.field_237398_a_ = p_i232162_1_;
         this.field_237399_b_ = p_i232162_2_;
         this.field_237400_c_ = p_i232162_3_;
         this.field_237401_d_ = p_i232162_4_;
         this.field_237402_e_ = p_i232162_5_;
      }

      public E deserialize(JsonElement p_deserialize_1_, Type p_deserialize_2_, JsonDeserializationContext p_deserialize_3_) throws JsonParseException {
         if (p_deserialize_1_.isJsonObject()) {
            JsonObject jsonobject = JSONUtils.getJsonObject(p_deserialize_1_, this.field_237399_b_);
            ResourceLocation resourcelocation = new ResourceLocation(JSONUtils.getString(jsonobject, this.field_237400_c_));
            T t = this.field_237398_a_.getOrDefault(resourcelocation);
            if (t == null) {
               throw new JsonSyntaxException("Unknown type '" + resourcelocation + "'");
            } else {
               return t.func_237408_a_().func_230423_a_(jsonobject, p_deserialize_3_);
            }
         } else if (this.field_237402_e_ == null) {
            throw new UnsupportedOperationException("Object " + p_deserialize_1_ + " can't be deserialized");
         } else {
            return this.field_237402_e_.getSecond().func_237396_a_(p_deserialize_1_, p_deserialize_3_);
         }
      }

      public JsonElement serialize(E p_serialize_1_, Type p_serialize_2_, JsonSerializationContext p_serialize_3_) {
         T t = this.field_237401_d_.apply(p_serialize_1_);
         if (this.field_237402_e_ != null && this.field_237402_e_.getFirst() == t) {
            return ((ISerializer<E>)this.field_237402_e_.getSecond()).func_237397_a_(p_serialize_1_, p_serialize_3_);
         } else if (t == null) {
            throw new JsonSyntaxException("Unknown type: " + p_serialize_1_);
         } else {
            JsonObject jsonobject = new JsonObject();
            jsonobject.addProperty(this.field_237400_c_, this.field_237398_a_.getKey(t).toString());
            ((LootType)t).func_237408_a_().func_230424_a_(jsonobject, p_serialize_1_, p_serialize_3_);
            return jsonobject;
         }
      }
   }
}