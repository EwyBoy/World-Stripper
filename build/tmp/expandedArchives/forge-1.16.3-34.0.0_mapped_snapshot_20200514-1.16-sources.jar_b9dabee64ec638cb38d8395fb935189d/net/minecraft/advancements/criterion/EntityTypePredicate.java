package net.minecraft.advancements.criterion;

import com.google.common.base.Joiner;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSyntaxException;
import javax.annotation.Nullable;
import net.minecraft.entity.EntityType;
import net.minecraft.tags.ITag;
import net.minecraft.tags.TagCollectionManager;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

public abstract class EntityTypePredicate {
   public static final EntityTypePredicate ANY = new EntityTypePredicate() {
      public boolean test(EntityType<?> p_209368_1_) {
         return true;
      }

      public JsonElement serialize() {
         return JsonNull.INSTANCE;
      }
   };
   private static final Joiner field_209372_b = Joiner.on(", ");

   public abstract boolean test(EntityType<?> p_209368_1_);

   public abstract JsonElement serialize();

   public static EntityTypePredicate deserialize(@Nullable JsonElement p_209370_0_) {
      if (p_209370_0_ != null && !p_209370_0_.isJsonNull()) {
         String s = JSONUtils.getString(p_209370_0_, "type");
         if (s.startsWith("#")) {
            ResourceLocation resourcelocation1 = new ResourceLocation(s.substring(1));
            return new EntityTypePredicate.TagPredicate(TagCollectionManager.func_242178_a().func_241838_d().func_241834_b(resourcelocation1));
         } else {
            ResourceLocation resourcelocation = new ResourceLocation(s);
            EntityType<?> entitytype = Registry.ENTITY_TYPE.func_241873_b(resourcelocation).orElseThrow(() -> {
               return new JsonSyntaxException("Unknown entity type '" + resourcelocation + "', valid types are: " + field_209372_b.join(Registry.ENTITY_TYPE.keySet()));
            });
            return new EntityTypePredicate.TypePredicate(entitytype);
         }
      } else {
         return ANY;
      }
   }

   public static EntityTypePredicate func_217999_b(EntityType<?> p_217999_0_) {
      return new EntityTypePredicate.TypePredicate(p_217999_0_);
   }

   public static EntityTypePredicate func_217998_a(ITag<EntityType<?>> p_217998_0_) {
      return new EntityTypePredicate.TagPredicate(p_217998_0_);
   }

   static class TagPredicate extends EntityTypePredicate {
      private final ITag<EntityType<?>> field_218001_b;

      public TagPredicate(ITag<EntityType<?>> p_i50558_1_) {
         this.field_218001_b = p_i50558_1_;
      }

      public boolean test(EntityType<?> p_209368_1_) {
         return this.field_218001_b.func_230235_a_(p_209368_1_);
      }

      public JsonElement serialize() {
         return new JsonPrimitive("#" + TagCollectionManager.func_242178_a().func_241838_d().func_232975_b_(this.field_218001_b));
      }
   }

   static class TypePredicate extends EntityTypePredicate {
      private final EntityType<?> field_218000_b;

      public TypePredicate(EntityType<?> p_i50556_1_) {
         this.field_218000_b = p_i50556_1_;
      }

      public boolean test(EntityType<?> p_209368_1_) {
         return this.field_218000_b == p_209368_1_;
      }

      public JsonElement serialize() {
         return new JsonPrimitive(Registry.ENTITY_TYPE.getKey(this.field_218000_b).toString());
      }
   }
}