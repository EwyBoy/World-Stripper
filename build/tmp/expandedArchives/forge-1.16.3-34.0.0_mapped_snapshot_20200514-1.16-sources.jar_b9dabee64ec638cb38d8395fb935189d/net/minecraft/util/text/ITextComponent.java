package net.minecraft.util.text;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.stream.JsonReader;
import com.mojang.brigadier.Message;
import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;
import java.util.Map.Entry;
import javax.annotation.Nullable;
import net.minecraft.util.EnumTypeAdapterFactory;
import net.minecraft.util.IReorderingProcessor;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public interface ITextComponent extends Message, ITextProperties {
   /**
    * Gets the style of this component. Returns a direct reference; changes to this style will modify the style of this
    * component (IE, there is no need to call {@link #setStyle(Style)} again after modifying it).
    *  
    * If this component's style is currently <code>null</code>, it will be initialized to the default style, and the
    * parent style of all sibling components will be set to that style. (IE, changes to this style will also be
    * reflected in sibling components.)
    *  
    * This method never returns <code>null</code>.
    */
   Style getStyle();

   /**
    * Gets the raw content of this component (but not its sibling components), without any formatting codes. For
    * example, this is the raw text in a {@link TextComponentString}, but it's the translated text for a {@link
    * TextComponentTranslation} and it's the score value for a {@link TextComponentScore}.
    */
   String getUnformattedComponentText();

   default String getString() {
      return ITextProperties.super.getString();
   }

   default String getStringTruncated(int maxLen) {
      StringBuilder stringbuilder = new StringBuilder();
      this.func_230438_a_((p_240639_2_) -> {
         int i = maxLen - stringbuilder.length();
         if (i <= 0) {
            return field_240650_b_;
         } else {
            stringbuilder.append(p_240639_2_.length() <= i ? p_240639_2_ : p_240639_2_.substring(0, i));
            return Optional.empty();
         }
      });
      return stringbuilder.toString();
   }

   /**
    * Gets the sibling components of this one.
    */
   List<ITextComponent> getSiblings();

   IFormattableTextComponent func_230531_f_();

   IFormattableTextComponent func_230532_e_();

   @OnlyIn(Dist.CLIENT)
   IReorderingProcessor func_241878_f();

   @OnlyIn(Dist.CLIENT)
   default <T> Optional<T> func_230439_a_(ITextProperties.IStyledTextAcceptor<T> p_230439_1_, Style p_230439_2_) {
      Style style = this.getStyle().func_240717_a_(p_230439_2_);
      Optional<T> optional = this.func_230534_b_(p_230439_1_, style);
      if (optional.isPresent()) {
         return optional;
      } else {
         for(ITextComponent itextcomponent : this.getSiblings()) {
            Optional<T> optional1 = itextcomponent.func_230439_a_(p_230439_1_, style);
            if (optional1.isPresent()) {
               return optional1;
            }
         }

         return Optional.empty();
      }
   }

   default <T> Optional<T> func_230438_a_(ITextProperties.ITextAcceptor<T> p_230438_1_) {
      Optional<T> optional = this.func_230533_b_(p_230438_1_);
      if (optional.isPresent()) {
         return optional;
      } else {
         for(ITextComponent itextcomponent : this.getSiblings()) {
            Optional<T> optional1 = itextcomponent.func_230438_a_(p_230438_1_);
            if (optional1.isPresent()) {
               return optional1;
            }
         }

         return Optional.empty();
      }
   }

   @OnlyIn(Dist.CLIENT)
   default <T> Optional<T> func_230534_b_(ITextProperties.IStyledTextAcceptor<T> p_230534_1_, Style p_230534_2_) {
      return p_230534_1_.accept(p_230534_2_, this.getUnformattedComponentText());
   }

   default <T> Optional<T> func_230533_b_(ITextProperties.ITextAcceptor<T> p_230533_1_) {
      return p_230533_1_.accept(this.getUnformattedComponentText());
   }

   @OnlyIn(Dist.CLIENT)
   static ITextComponent func_244388_a(@Nullable String p_244388_0_) {
      return (ITextComponent)(p_244388_0_ != null ? new StringTextComponent(p_244388_0_) : StringTextComponent.field_240750_d_);
   }

   public static class Serializer implements JsonDeserializer<IFormattableTextComponent>, JsonSerializer<ITextComponent> {
      private static final Gson GSON = Util.make(() -> {
         GsonBuilder gsonbuilder = new GsonBuilder();
         gsonbuilder.disableHtmlEscaping();
         gsonbuilder.registerTypeHierarchyAdapter(ITextComponent.class, new ITextComponent.Serializer());
         gsonbuilder.registerTypeHierarchyAdapter(Style.class, new Style.Serializer());
         gsonbuilder.registerTypeAdapterFactory(new EnumTypeAdapterFactory());
         return gsonbuilder.create();
      });
      private static final Field JSON_READER_POS_FIELD = Util.make(() -> {
         try {
            new JsonReader(new StringReader(""));
            Field field = JsonReader.class.getDeclaredField("pos");
            field.setAccessible(true);
            return field;
         } catch (NoSuchFieldException nosuchfieldexception) {
            throw new IllegalStateException("Couldn't get field 'pos' for JsonReader", nosuchfieldexception);
         }
      });
      private static final Field JSON_READER_LINESTART_FIELD = Util.make(() -> {
         try {
            new JsonReader(new StringReader(""));
            Field field = JsonReader.class.getDeclaredField("lineStart");
            field.setAccessible(true);
            return field;
         } catch (NoSuchFieldException nosuchfieldexception) {
            throw new IllegalStateException("Couldn't get field 'lineStart' for JsonReader", nosuchfieldexception);
         }
      });

      public IFormattableTextComponent deserialize(JsonElement p_deserialize_1_, Type p_deserialize_2_, JsonDeserializationContext p_deserialize_3_) throws JsonParseException {
         if (p_deserialize_1_.isJsonPrimitive()) {
            return new StringTextComponent(p_deserialize_1_.getAsString());
         } else if (!p_deserialize_1_.isJsonObject()) {
            if (p_deserialize_1_.isJsonArray()) {
               JsonArray jsonarray1 = p_deserialize_1_.getAsJsonArray();
               IFormattableTextComponent iformattabletextcomponent1 = null;

               for(JsonElement jsonelement : jsonarray1) {
                  IFormattableTextComponent iformattabletextcomponent2 = this.deserialize(jsonelement, jsonelement.getClass(), p_deserialize_3_);
                  if (iformattabletextcomponent1 == null) {
                     iformattabletextcomponent1 = iformattabletextcomponent2;
                  } else {
                     iformattabletextcomponent1.func_230529_a_(iformattabletextcomponent2);
                  }
               }

               return iformattabletextcomponent1;
            } else {
               throw new JsonParseException("Don't know how to turn " + p_deserialize_1_ + " into a Component");
            }
         } else {
            JsonObject jsonobject = p_deserialize_1_.getAsJsonObject();
            IFormattableTextComponent iformattabletextcomponent;
            if (jsonobject.has("text")) {
               iformattabletextcomponent = new StringTextComponent(JSONUtils.getString(jsonobject, "text"));
            } else if (jsonobject.has("translate")) {
               String s = JSONUtils.getString(jsonobject, "translate");
               if (jsonobject.has("with")) {
                  JsonArray jsonarray = JSONUtils.getJsonArray(jsonobject, "with");
                  Object[] aobject = new Object[jsonarray.size()];

                  for(int i = 0; i < aobject.length; ++i) {
                     aobject[i] = this.deserialize(jsonarray.get(i), p_deserialize_2_, p_deserialize_3_);
                     if (aobject[i] instanceof StringTextComponent) {
                        StringTextComponent stringtextcomponent = (StringTextComponent)aobject[i];
                        if (stringtextcomponent.getStyle().isEmpty() && stringtextcomponent.getSiblings().isEmpty()) {
                           aobject[i] = stringtextcomponent.getText();
                        }
                     }
                  }

                  iformattabletextcomponent = new TranslationTextComponent(s, aobject);
               } else {
                  iformattabletextcomponent = new TranslationTextComponent(s);
               }
            } else if (jsonobject.has("score")) {
               JsonObject jsonobject1 = JSONUtils.getJsonObject(jsonobject, "score");
               if (!jsonobject1.has("name") || !jsonobject1.has("objective")) {
                  throw new JsonParseException("A score component needs a least a name and an objective");
               }

               iformattabletextcomponent = new ScoreTextComponent(JSONUtils.getString(jsonobject1, "name"), JSONUtils.getString(jsonobject1, "objective"));
            } else if (jsonobject.has("selector")) {
               iformattabletextcomponent = new SelectorTextComponent(JSONUtils.getString(jsonobject, "selector"));
            } else if (jsonobject.has("keybind")) {
               iformattabletextcomponent = new KeybindTextComponent(JSONUtils.getString(jsonobject, "keybind"));
            } else {
               if (!jsonobject.has("nbt")) {
                  throw new JsonParseException("Don't know how to turn " + p_deserialize_1_ + " into a Component");
               }

               String s1 = JSONUtils.getString(jsonobject, "nbt");
               boolean flag = JSONUtils.getBoolean(jsonobject, "interpret", false);
               if (jsonobject.has("block")) {
                  iformattabletextcomponent = new NBTTextComponent.Block(s1, flag, JSONUtils.getString(jsonobject, "block"));
               } else if (jsonobject.has("entity")) {
                  iformattabletextcomponent = new NBTTextComponent.Entity(s1, flag, JSONUtils.getString(jsonobject, "entity"));
               } else {
                  if (!jsonobject.has("storage")) {
                     throw new JsonParseException("Don't know how to turn " + p_deserialize_1_ + " into a Component");
                  }

                  iformattabletextcomponent = new NBTTextComponent.Storage(s1, flag, new ResourceLocation(JSONUtils.getString(jsonobject, "storage")));
               }
            }

            if (jsonobject.has("extra")) {
               JsonArray jsonarray2 = JSONUtils.getJsonArray(jsonobject, "extra");
               if (jsonarray2.size() <= 0) {
                  throw new JsonParseException("Unexpected empty array of components");
               }

               for(int j = 0; j < jsonarray2.size(); ++j) {
                  iformattabletextcomponent.func_230529_a_(this.deserialize(jsonarray2.get(j), p_deserialize_2_, p_deserialize_3_));
               }
            }

            iformattabletextcomponent.func_230530_a_(p_deserialize_3_.deserialize(p_deserialize_1_, Style.class));
            return iformattabletextcomponent;
         }
      }

      private void serializeChatStyle(Style style, JsonObject object, JsonSerializationContext ctx) {
         JsonElement jsonelement = ctx.serialize(style);
         if (jsonelement.isJsonObject()) {
            JsonObject jsonobject = (JsonObject)jsonelement;

            for(Entry<String, JsonElement> entry : jsonobject.entrySet()) {
               object.add(entry.getKey(), entry.getValue());
            }
         }

      }

      public JsonElement serialize(ITextComponent p_serialize_1_, Type p_serialize_2_, JsonSerializationContext p_serialize_3_) {
         JsonObject jsonobject = new JsonObject();
         if (!p_serialize_1_.getStyle().isEmpty()) {
            this.serializeChatStyle(p_serialize_1_.getStyle(), jsonobject, p_serialize_3_);
         }

         if (!p_serialize_1_.getSiblings().isEmpty()) {
            JsonArray jsonarray = new JsonArray();

            for(ITextComponent itextcomponent : p_serialize_1_.getSiblings()) {
               jsonarray.add(this.serialize(itextcomponent, itextcomponent.getClass(), p_serialize_3_));
            }

            jsonobject.add("extra", jsonarray);
         }

         if (p_serialize_1_ instanceof StringTextComponent) {
            jsonobject.addProperty("text", ((StringTextComponent)p_serialize_1_).getText());
         } else if (p_serialize_1_ instanceof TranslationTextComponent) {
            TranslationTextComponent translationtextcomponent = (TranslationTextComponent)p_serialize_1_;
            jsonobject.addProperty("translate", translationtextcomponent.getKey());
            if (translationtextcomponent.getFormatArgs() != null && translationtextcomponent.getFormatArgs().length > 0) {
               JsonArray jsonarray1 = new JsonArray();

               for(Object object : translationtextcomponent.getFormatArgs()) {
                  if (object instanceof ITextComponent) {
                     jsonarray1.add(this.serialize((ITextComponent)object, object.getClass(), p_serialize_3_));
                  } else {
                     jsonarray1.add(new JsonPrimitive(String.valueOf(object)));
                  }
               }

               jsonobject.add("with", jsonarray1);
            }
         } else if (p_serialize_1_ instanceof ScoreTextComponent) {
            ScoreTextComponent scoretextcomponent = (ScoreTextComponent)p_serialize_1_;
            JsonObject jsonobject1 = new JsonObject();
            jsonobject1.addProperty("name", scoretextcomponent.getName());
            jsonobject1.addProperty("objective", scoretextcomponent.getObjective());
            jsonobject.add("score", jsonobject1);
         } else if (p_serialize_1_ instanceof SelectorTextComponent) {
            SelectorTextComponent selectortextcomponent = (SelectorTextComponent)p_serialize_1_;
            jsonobject.addProperty("selector", selectortextcomponent.getSelector());
         } else if (p_serialize_1_ instanceof KeybindTextComponent) {
            KeybindTextComponent keybindtextcomponent = (KeybindTextComponent)p_serialize_1_;
            jsonobject.addProperty("keybind", keybindtextcomponent.getKeybind());
         } else {
            if (!(p_serialize_1_ instanceof NBTTextComponent)) {
               throw new IllegalArgumentException("Don't know how to serialize " + p_serialize_1_ + " as a Component");
            }

            NBTTextComponent nbttextcomponent = (NBTTextComponent)p_serialize_1_;
            jsonobject.addProperty("nbt", nbttextcomponent.func_218676_i());
            jsonobject.addProperty("interpret", nbttextcomponent.func_218677_j());
            if (p_serialize_1_ instanceof NBTTextComponent.Block) {
               NBTTextComponent.Block nbttextcomponent$block = (NBTTextComponent.Block)p_serialize_1_;
               jsonobject.addProperty("block", nbttextcomponent$block.func_218683_k());
            } else if (p_serialize_1_ instanceof NBTTextComponent.Entity) {
               NBTTextComponent.Entity nbttextcomponent$entity = (NBTTextComponent.Entity)p_serialize_1_;
               jsonobject.addProperty("entity", nbttextcomponent$entity.func_218687_k());
            } else {
               if (!(p_serialize_1_ instanceof NBTTextComponent.Storage)) {
                  throw new IllegalArgumentException("Don't know how to serialize " + p_serialize_1_ + " as a Component");
               }

               NBTTextComponent.Storage nbttextcomponent$storage = (NBTTextComponent.Storage)p_serialize_1_;
               jsonobject.addProperty("storage", nbttextcomponent$storage.func_229726_k_().toString());
            }
         }

         return jsonobject;
      }

      /**
       * Serializes a component into JSON.
       */
      public static String toJson(ITextComponent component) {
         return GSON.toJson(component);
      }

      public static JsonElement toJsonTree(ITextComponent p_200528_0_) {
         return GSON.toJsonTree(p_200528_0_);
      }

      @Nullable
      public static IFormattableTextComponent func_240643_a_(String p_240643_0_) {
         return JSONUtils.fromJson(GSON, p_240643_0_, IFormattableTextComponent.class, false);
      }

      @Nullable
      public static IFormattableTextComponent func_240641_a_(JsonElement p_240641_0_) {
         return GSON.fromJson(p_240641_0_, IFormattableTextComponent.class);
      }

      @Nullable
      public static IFormattableTextComponent func_240644_b_(String p_240644_0_) {
         return JSONUtils.fromJson(GSON, p_240644_0_, IFormattableTextComponent.class, true);
      }

      public static IFormattableTextComponent func_240642_a_(com.mojang.brigadier.StringReader p_240642_0_) {
         try {
            JsonReader jsonreader = new JsonReader(new StringReader(p_240642_0_.getRemaining()));
            jsonreader.setLenient(false);
            IFormattableTextComponent iformattabletextcomponent = GSON.getAdapter(IFormattableTextComponent.class).read(jsonreader);
            p_240642_0_.setCursor(p_240642_0_.getCursor() + getPos(jsonreader));
            return iformattabletextcomponent;
         } catch (StackOverflowError | IOException ioexception) {
            throw new JsonParseException(ioexception);
         }
      }

      private static int getPos(JsonReader p_197673_0_) {
         try {
            return JSON_READER_POS_FIELD.getInt(p_197673_0_) - JSON_READER_LINESTART_FIELD.getInt(p_197673_0_) + 1;
         } catch (IllegalAccessException illegalaccessexception) {
            throw new IllegalStateException("Couldn't read position of JsonReader", illegalaccessexception);
         }
      }
   }
}