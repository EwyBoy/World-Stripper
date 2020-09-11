package net.minecraft.util.text;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonSyntaxException;
import java.lang.reflect.Type;
import java.util.Objects;
import javax.annotation.Nullable;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.ResourceLocationException;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.util.text.event.HoverEvent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class Style {
   public static final Style field_240709_b_ = new Style((Color)null, (Boolean)null, (Boolean)null, (Boolean)null, (Boolean)null, (Boolean)null, (ClickEvent)null, (HoverEvent)null, (String)null, (ResourceLocation)null);
   public static final ResourceLocation field_240708_a_ = new ResourceLocation("minecraft", "default");
   @Nullable
   private final Color color;
   @Nullable
   private final Boolean bold;
   @Nullable
   private final Boolean italic;
   @Nullable
   private final Boolean underlined;
   @Nullable
   private final Boolean strikethrough;
   @Nullable
   private final Boolean obfuscated;
   @Nullable
   private final ClickEvent clickEvent;
   @Nullable
   private final HoverEvent hoverEvent;
   @Nullable
   private final String insertion;
   @Nullable
   private final ResourceLocation field_240710_l_;

   private Style(@Nullable Color p_i232570_1_, @Nullable Boolean p_i232570_2_, @Nullable Boolean p_i232570_3_, @Nullable Boolean p_i232570_4_, @Nullable Boolean p_i232570_5_, @Nullable Boolean p_i232570_6_, @Nullable ClickEvent p_i232570_7_, @Nullable HoverEvent p_i232570_8_, @Nullable String p_i232570_9_, @Nullable ResourceLocation p_i232570_10_) {
      this.color = p_i232570_1_;
      this.bold = p_i232570_2_;
      this.italic = p_i232570_3_;
      this.underlined = p_i232570_4_;
      this.strikethrough = p_i232570_5_;
      this.obfuscated = p_i232570_6_;
      this.clickEvent = p_i232570_7_;
      this.hoverEvent = p_i232570_8_;
      this.insertion = p_i232570_9_;
      this.field_240710_l_ = p_i232570_10_;
   }

   @Nullable
   public Color func_240711_a_() {
      return this.color;
   }

   /**
    * Whether or not text of this ChatStyle should be in bold.
    */
   public boolean getBold() {
      return this.bold == Boolean.TRUE;
   }

   /**
    * Whether or not text of this ChatStyle should be italicized.
    */
   public boolean getItalic() {
      return this.italic == Boolean.TRUE;
   }

   /**
    * Whether or not to format text of this ChatStyle using strikethrough.
    */
   public boolean getStrikethrough() {
      return this.strikethrough == Boolean.TRUE;
   }

   /**
    * Whether or not text of this ChatStyle should be underlined.
    */
   public boolean getUnderlined() {
      return this.underlined == Boolean.TRUE;
   }

   /**
    * Whether or not text of this ChatStyle should be obfuscated.
    */
   public boolean getObfuscated() {
      return this.obfuscated == Boolean.TRUE;
   }

   /**
    * Whether or not this style is empty (inherits everything from the parent).
    */
   public boolean isEmpty() {
      return this == field_240709_b_;
   }

   /**
    * The effective chat click event.
    */
   @Nullable
   public ClickEvent getClickEvent() {
      return this.clickEvent;
   }

   /**
    * The effective chat hover event.
    */
   @Nullable
   public HoverEvent getHoverEvent() {
      return this.hoverEvent;
   }

   /**
    * Get the text to be inserted into Chat when the component is shift-clicked
    */
   @Nullable
   public String getInsertion() {
      return this.insertion;
   }

   public ResourceLocation func_240729_k_() {
      return this.field_240710_l_ != null ? this.field_240710_l_ : field_240708_a_;
   }

   public Style func_240718_a_(@Nullable Color p_240718_1_) {
      return new Style(p_240718_1_, this.bold, this.italic, this.underlined, this.strikethrough, this.obfuscated, this.clickEvent, this.hoverEvent, this.insertion, this.field_240710_l_);
   }

   public Style func_240712_a_(@Nullable TextFormatting p_240712_1_) {
      return this.func_240718_a_(p_240712_1_ != null ? Color.func_240744_a_(p_240712_1_) : null);
   }

   public Style func_240713_a_(@Nullable Boolean p_240713_1_) {
      return new Style(this.color, p_240713_1_, this.italic, this.underlined, this.strikethrough, this.obfuscated, this.clickEvent, this.hoverEvent, this.insertion, this.field_240710_l_);
   }

   public Style func_240722_b_(@Nullable Boolean p_240722_1_) {
      return new Style(this.color, this.bold, p_240722_1_, this.underlined, this.strikethrough, this.obfuscated, this.clickEvent, this.hoverEvent, this.insertion, this.field_240710_l_);
   }

   @OnlyIn(Dist.CLIENT)
   public Style func_244282_c(@Nullable Boolean p_244282_1_) {
      return new Style(this.color, this.bold, this.italic, p_244282_1_, this.strikethrough, this.obfuscated, this.clickEvent, this.hoverEvent, this.insertion, this.field_240710_l_);
   }

   public Style setUnderlined(@Nullable Boolean underlined) {
      return new Style(this.color, this.bold, this.italic, underlined, this.strikethrough, this.obfuscated, this.clickEvent, this.hoverEvent, this.insertion, this.field_240710_l_);
   }

   public Style setStrikethrough(@Nullable Boolean strikethrough) {
      return new Style(this.color, this.bold, this.italic, this.underlined, strikethrough, this.obfuscated, this.clickEvent, this.hoverEvent, this.insertion, this.field_240710_l_);
   }

   public Style setObfuscated(@Nullable Boolean obfuscated) {
      return new Style(this.color, this.bold, this.italic, this.underlined, this.strikethrough, obfuscated, this.clickEvent, this.hoverEvent, this.insertion, this.field_240710_l_);
   }

   public Style func_240715_a_(@Nullable ClickEvent p_240715_1_) {
      return new Style(this.color, this.bold, this.italic, this.underlined, this.strikethrough, this.obfuscated, p_240715_1_, this.hoverEvent, this.insertion, this.field_240710_l_);
   }

   public Style func_240716_a_(@Nullable HoverEvent p_240716_1_) {
      return new Style(this.color, this.bold, this.italic, this.underlined, this.strikethrough, this.obfuscated, this.clickEvent, p_240716_1_, this.insertion, this.field_240710_l_);
   }

   public Style func_240714_a_(@Nullable String p_240714_1_) {
      return new Style(this.color, this.bold, this.italic, this.underlined, this.strikethrough, this.obfuscated, this.clickEvent, this.hoverEvent, p_240714_1_, this.field_240710_l_);
   }

   public Style func_240719_a_(@Nullable ResourceLocation p_240719_1_) {
      return new Style(this.color, this.bold, this.italic, this.underlined, this.strikethrough, this.obfuscated, this.clickEvent, this.hoverEvent, this.insertion, p_240719_1_);
   }

   public Style func_240721_b_(TextFormatting p_240721_1_) {
      Color color = this.color;
      Boolean obool = this.bold;
      Boolean obool1 = this.italic;
      Boolean obool2 = this.strikethrough;
      Boolean obool3 = this.underlined;
      Boolean obool4 = this.obfuscated;
      switch(p_240721_1_) {
      case OBFUSCATED:
         obool4 = true;
         break;
      case BOLD:
         obool = true;
         break;
      case STRIKETHROUGH:
         obool2 = true;
         break;
      case UNDERLINE:
         obool3 = true;
         break;
      case ITALIC:
         obool1 = true;
         break;
      case RESET:
         return field_240709_b_;
      default:
         color = Color.func_240744_a_(p_240721_1_);
      }

      return new Style(color, obool, obool1, obool3, obool2, obool4, this.clickEvent, this.hoverEvent, this.insertion, this.field_240710_l_);
   }

   public Style func_240723_c_(TextFormatting p_240723_1_) {
      Color color = this.color;
      Boolean obool = this.bold;
      Boolean obool1 = this.italic;
      Boolean obool2 = this.strikethrough;
      Boolean obool3 = this.underlined;
      Boolean obool4 = this.obfuscated;
      switch(p_240723_1_) {
      case OBFUSCATED:
         obool4 = true;
         break;
      case BOLD:
         obool = true;
         break;
      case STRIKETHROUGH:
         obool2 = true;
         break;
      case UNDERLINE:
         obool3 = true;
         break;
      case ITALIC:
         obool1 = true;
         break;
      case RESET:
         return field_240709_b_;
      default:
         obool4 = false;
         obool = false;
         obool2 = false;
         obool3 = false;
         obool1 = false;
         color = Color.func_240744_a_(p_240723_1_);
      }

      return new Style(color, obool, obool1, obool3, obool2, obool4, this.clickEvent, this.hoverEvent, this.insertion, this.field_240710_l_);
   }

   public Style func_240720_a_(TextFormatting... p_240720_1_) {
      Color color = this.color;
      Boolean obool = this.bold;
      Boolean obool1 = this.italic;
      Boolean obool2 = this.strikethrough;
      Boolean obool3 = this.underlined;
      Boolean obool4 = this.obfuscated;

      for(TextFormatting textformatting : p_240720_1_) {
         switch(textformatting) {
         case OBFUSCATED:
            obool4 = true;
            break;
         case BOLD:
            obool = true;
            break;
         case STRIKETHROUGH:
            obool2 = true;
            break;
         case UNDERLINE:
            obool3 = true;
            break;
         case ITALIC:
            obool1 = true;
            break;
         case RESET:
            return field_240709_b_;
         default:
            color = Color.func_240744_a_(textformatting);
         }
      }

      return new Style(color, obool, obool1, obool3, obool2, obool4, this.clickEvent, this.hoverEvent, this.insertion, this.field_240710_l_);
   }

   public Style func_240717_a_(Style p_240717_1_) {
      if (this == field_240709_b_) {
         return p_240717_1_;
      } else {
         return p_240717_1_ == field_240709_b_ ? this : new Style(this.color != null ? this.color : p_240717_1_.color, this.bold != null ? this.bold : p_240717_1_.bold, this.italic != null ? this.italic : p_240717_1_.italic, this.underlined != null ? this.underlined : p_240717_1_.underlined, this.strikethrough != null ? this.strikethrough : p_240717_1_.strikethrough, this.obfuscated != null ? this.obfuscated : p_240717_1_.obfuscated, this.clickEvent != null ? this.clickEvent : p_240717_1_.clickEvent, this.hoverEvent != null ? this.hoverEvent : p_240717_1_.hoverEvent, this.insertion != null ? this.insertion : p_240717_1_.insertion, this.field_240710_l_ != null ? this.field_240710_l_ : p_240717_1_.field_240710_l_);
      }
   }

   public String toString() {
      return "Style{ color=" + this.color + ", bold=" + this.bold + ", italic=" + this.italic + ", underlined=" + this.underlined + ", strikethrough=" + this.strikethrough + ", obfuscated=" + this.obfuscated + ", clickEvent=" + this.getClickEvent() + ", hoverEvent=" + this.getHoverEvent() + ", insertion=" + this.getInsertion() + ", font=" + this.func_240729_k_() + '}';
   }

   public boolean equals(Object p_equals_1_) {
      if (this == p_equals_1_) {
         return true;
      } else if (!(p_equals_1_ instanceof Style)) {
         return false;
      } else {
         Style style = (Style)p_equals_1_;
         return this.getBold() == style.getBold() && Objects.equals(this.func_240711_a_(), style.func_240711_a_()) && this.getItalic() == style.getItalic() && this.getObfuscated() == style.getObfuscated() && this.getStrikethrough() == style.getStrikethrough() && this.getUnderlined() == style.getUnderlined() && Objects.equals(this.getClickEvent(), style.getClickEvent()) && Objects.equals(this.getHoverEvent(), style.getHoverEvent()) && Objects.equals(this.getInsertion(), style.getInsertion()) && Objects.equals(this.func_240729_k_(), style.func_240729_k_());
      }
   }

   public int hashCode() {
      return Objects.hash(this.color, this.bold, this.italic, this.underlined, this.strikethrough, this.obfuscated, this.clickEvent, this.hoverEvent, this.insertion);
   }

   public static class Serializer implements JsonDeserializer<Style>, JsonSerializer<Style> {
      @Nullable
      public Style deserialize(JsonElement p_deserialize_1_, Type p_deserialize_2_, JsonDeserializationContext p_deserialize_3_) throws JsonParseException {
         if (p_deserialize_1_.isJsonObject()) {
            JsonObject jsonobject = p_deserialize_1_.getAsJsonObject();
            if (jsonobject == null) {
               return null;
            } else {
               Boolean obool = func_240733_a_(jsonobject, "bold");
               Boolean obool1 = func_240733_a_(jsonobject, "italic");
               Boolean obool2 = func_240733_a_(jsonobject, "underlined");
               Boolean obool3 = func_240733_a_(jsonobject, "strikethrough");
               Boolean obool4 = func_240733_a_(jsonobject, "obfuscated");
               Color color = func_240737_e_(jsonobject);
               String s = func_240736_d_(jsonobject);
               ClickEvent clickevent = func_240735_c_(jsonobject);
               HoverEvent hoverevent = func_240734_b_(jsonobject);
               ResourceLocation resourcelocation = func_240732_a_(jsonobject);
               return new Style(color, obool, obool1, obool2, obool3, obool4, clickevent, hoverevent, s, resourcelocation);
            }
         } else {
            return null;
         }
      }

      @Nullable
      private static ResourceLocation func_240732_a_(JsonObject p_240732_0_) {
         if (p_240732_0_.has("font")) {
            String s = JSONUtils.getString(p_240732_0_, "font");

            try {
               return new ResourceLocation(s);
            } catch (ResourceLocationException resourcelocationexception) {
               throw new JsonSyntaxException("Invalid font name: " + s);
            }
         } else {
            return null;
         }
      }

      @Nullable
      private static HoverEvent func_240734_b_(JsonObject p_240734_0_) {
         if (p_240734_0_.has("hoverEvent")) {
            JsonObject jsonobject = JSONUtils.getJsonObject(p_240734_0_, "hoverEvent");
            HoverEvent hoverevent = HoverEvent.func_240661_a_(jsonobject);
            if (hoverevent != null && hoverevent.getAction().shouldAllowInChat()) {
               return hoverevent;
            }
         }

         return null;
      }

      @Nullable
      private static ClickEvent func_240735_c_(JsonObject p_240735_0_) {
         if (p_240735_0_.has("clickEvent")) {
            JsonObject jsonobject = JSONUtils.getJsonObject(p_240735_0_, "clickEvent");
            String s = JSONUtils.getString(jsonobject, "action", (String)null);
            ClickEvent.Action clickevent$action = s == null ? null : ClickEvent.Action.getValueByCanonicalName(s);
            String s1 = JSONUtils.getString(jsonobject, "value", (String)null);
            if (clickevent$action != null && s1 != null && clickevent$action.shouldAllowInChat()) {
               return new ClickEvent(clickevent$action, s1);
            }
         }

         return null;
      }

      @Nullable
      private static String func_240736_d_(JsonObject p_240736_0_) {
         return JSONUtils.getString(p_240736_0_, "insertion", (String)null);
      }

      @Nullable
      private static Color func_240737_e_(JsonObject p_240737_0_) {
         if (p_240737_0_.has("color")) {
            String s = JSONUtils.getString(p_240737_0_, "color");
            return Color.func_240745_a_(s);
         } else {
            return null;
         }
      }

      @Nullable
      private static Boolean func_240733_a_(JsonObject p_240733_0_, String p_240733_1_) {
         return p_240733_0_.has(p_240733_1_) ? p_240733_0_.get(p_240733_1_).getAsBoolean() : null;
      }

      @Nullable
      public JsonElement serialize(Style p_serialize_1_, Type p_serialize_2_, JsonSerializationContext p_serialize_3_) {
         if (p_serialize_1_.isEmpty()) {
            return null;
         } else {
            JsonObject jsonobject = new JsonObject();
            if (p_serialize_1_.bold != null) {
               jsonobject.addProperty("bold", p_serialize_1_.bold);
            }

            if (p_serialize_1_.italic != null) {
               jsonobject.addProperty("italic", p_serialize_1_.italic);
            }

            if (p_serialize_1_.underlined != null) {
               jsonobject.addProperty("underlined", p_serialize_1_.underlined);
            }

            if (p_serialize_1_.strikethrough != null) {
               jsonobject.addProperty("strikethrough", p_serialize_1_.strikethrough);
            }

            if (p_serialize_1_.obfuscated != null) {
               jsonobject.addProperty("obfuscated", p_serialize_1_.obfuscated);
            }

            if (p_serialize_1_.color != null) {
               jsonobject.addProperty("color", p_serialize_1_.color.func_240747_b_());
            }

            if (p_serialize_1_.insertion != null) {
               jsonobject.add("insertion", p_serialize_3_.serialize(p_serialize_1_.insertion));
            }

            if (p_serialize_1_.clickEvent != null) {
               JsonObject jsonobject1 = new JsonObject();
               jsonobject1.addProperty("action", p_serialize_1_.clickEvent.getAction().getCanonicalName());
               jsonobject1.addProperty("value", p_serialize_1_.clickEvent.getValue());
               jsonobject.add("clickEvent", jsonobject1);
            }

            if (p_serialize_1_.hoverEvent != null) {
               jsonobject.add("hoverEvent", p_serialize_1_.hoverEvent.func_240663_b_());
            }

            if (p_serialize_1_.field_240710_l_ != null) {
               jsonobject.addProperty("font", p_serialize_1_.field_240710_l_.toString());
            }

            return jsonobject;
         }
      }
   }
}