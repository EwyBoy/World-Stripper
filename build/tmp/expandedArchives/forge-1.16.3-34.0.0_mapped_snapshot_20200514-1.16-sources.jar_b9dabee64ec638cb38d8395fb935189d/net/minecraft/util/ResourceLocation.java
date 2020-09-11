package net.minecraft.util;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import java.lang.reflect.Type;
import javax.annotation.Nullable;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ResourceLocation implements Comparable<ResourceLocation> {
   public static final Codec<ResourceLocation> field_240908_a_ = Codec.STRING.comapFlatMap(ResourceLocation::func_240911_c_, ResourceLocation::toString).stable();
   private static final SimpleCommandExceptionType INVALID_EXCEPTION = new SimpleCommandExceptionType(new TranslationTextComponent("argument.id.invalid"));
   protected final String namespace;
   protected final String path;

   protected ResourceLocation(String[] resourceParts) {
      this.namespace = org.apache.commons.lang3.StringUtils.isEmpty(resourceParts[0]) ? "minecraft" : resourceParts[0];
      this.path = resourceParts[1];
      if (!isValidNamespace(this.namespace)) {
         throw new ResourceLocationException("Non [a-z0-9_.-] character in namespace of location: " + this.namespace + ':' + this.path);
      } else if (!isPathValid(this.path)) {
         throw new ResourceLocationException("Non [a-z0-9/._-] character in path of location: " + this.namespace + ':' + this.path);
      }
   }

   public ResourceLocation(String resourceName) {
      this(decompose(resourceName, ':'));
   }

   public ResourceLocation(String namespaceIn, String pathIn) {
      this(new String[]{namespaceIn, pathIn});
   }

   /**
    * Constructs a ResourceLocation by splitting a String representation of a valid location on a specified character.
    */
   public static ResourceLocation create(String resourceName, char splitOn) {
      return new ResourceLocation(decompose(resourceName, splitOn));
   }

   @Nullable
   public static ResourceLocation tryCreate(String string) {
      try {
         return new ResourceLocation(string);
      } catch (ResourceLocationException resourcelocationexception) {
         return null;
      }
   }

   protected static String[] decompose(String resourceName, char splitOn) {
      String[] astring = new String[]{"minecraft", resourceName};
      int i = resourceName.indexOf(splitOn);
      if (i >= 0) {
         astring[1] = resourceName.substring(i + 1, resourceName.length());
         if (i >= 1) {
            astring[0] = resourceName.substring(0, i);
         }
      }

      return astring;
   }

   private static DataResult<ResourceLocation> func_240911_c_(String p_240911_0_) {
      try {
         return DataResult.success(new ResourceLocation(p_240911_0_));
      } catch (ResourceLocationException resourcelocationexception) {
         return DataResult.error("Not a valid resource location: " + p_240911_0_ + " " + resourcelocationexception.getMessage());
      }
   }

   public String getPath() {
      return this.path;
   }

   public String getNamespace() {
      return this.namespace;
   }

   public String toString() {
      return this.namespace + ':' + this.path;
   }

   public boolean equals(Object p_equals_1_) {
      if (this == p_equals_1_) {
         return true;
      } else if (!(p_equals_1_ instanceof ResourceLocation)) {
         return false;
      } else {
         ResourceLocation resourcelocation = (ResourceLocation)p_equals_1_;
         return this.namespace.equals(resourcelocation.namespace) && this.path.equals(resourcelocation.path);
      }
   }

   public int hashCode() {
      return 31 * this.namespace.hashCode() + this.path.hashCode();
   }

   public int compareTo(ResourceLocation p_compareTo_1_) {
      int i = this.path.compareTo(p_compareTo_1_.path);
      if (i == 0) {
         i = this.namespace.compareTo(p_compareTo_1_.namespace);
      }

      return i;
   }

   public static ResourceLocation read(StringReader reader) throws CommandSyntaxException {
      int i = reader.getCursor();

      while(reader.canRead() && isValidPathCharacter(reader.peek())) {
         reader.skip();
      }

      String s = reader.getString().substring(i, reader.getCursor());

      try {
         return new ResourceLocation(s);
      } catch (ResourceLocationException resourcelocationexception) {
         reader.setCursor(i);
         throw INVALID_EXCEPTION.createWithContext(reader);
      }
   }

   public static boolean isValidPathCharacter(char charIn) {
      return charIn >= '0' && charIn <= '9' || charIn >= 'a' && charIn <= 'z' || charIn == '_' || charIn == ':' || charIn == '/' || charIn == '.' || charIn == '-';
   }

   /**
    * Checks if the path contains invalid characters.
    */
   private static boolean isPathValid(String pathIn) {
      for(int i = 0; i < pathIn.length(); ++i) {
         if (!func_240909_b_(pathIn.charAt(i))) {
            return false;
         }
      }

      return true;
   }

   /**
    * Returns true if given namespace only consists of allowed characters.
    */
   private static boolean isValidNamespace(String namespaceIn) {
      for(int i = 0; i < namespaceIn.length(); ++i) {
         if (!func_240910_c_(namespaceIn.charAt(i))) {
            return false;
         }
      }

      return true;
   }

   public static boolean func_240909_b_(char p_240909_0_) {
      return p_240909_0_ == '_' || p_240909_0_ == '-' || p_240909_0_ >= 'a' && p_240909_0_ <= 'z' || p_240909_0_ >= '0' && p_240909_0_ <= '9' || p_240909_0_ == '/' || p_240909_0_ == '.';
   }

   private static boolean func_240910_c_(char p_240910_0_) {
      return p_240910_0_ == '_' || p_240910_0_ == '-' || p_240910_0_ >= 'a' && p_240910_0_ <= 'z' || p_240910_0_ >= '0' && p_240910_0_ <= '9' || p_240910_0_ == '.';
   }

   /**
    * Checks if the specified resource name (namespace and path) contains invalid characters.
    */
   @OnlyIn(Dist.CLIENT)
   public static boolean isResouceNameValid(String resourceName) {
      String[] astring = decompose(resourceName, ':');
      return isValidNamespace(org.apache.commons.lang3.StringUtils.isEmpty(astring[0]) ? "minecraft" : astring[0]) && isPathValid(astring[1]);
   }

   public static class Serializer implements JsonDeserializer<ResourceLocation>, JsonSerializer<ResourceLocation> {
      public ResourceLocation deserialize(JsonElement p_deserialize_1_, Type p_deserialize_2_, JsonDeserializationContext p_deserialize_3_) throws JsonParseException {
         return new ResourceLocation(JSONUtils.getString(p_deserialize_1_, "location"));
      }

      public JsonElement serialize(ResourceLocation p_serialize_1_, Type p_serialize_2_, JsonSerializationContext p_serialize_3_) {
         return new JsonPrimitive(p_serialize_1_.toString());
      }
   }
}