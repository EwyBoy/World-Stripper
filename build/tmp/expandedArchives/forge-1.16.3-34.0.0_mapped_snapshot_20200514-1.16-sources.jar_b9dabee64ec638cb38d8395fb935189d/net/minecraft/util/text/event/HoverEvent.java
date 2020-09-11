package net.minecraft.util.text.event;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class HoverEvent {
   private static final Logger field_240660_a_ = LogManager.getLogger();
   private final HoverEvent.Action<?> action;
   private final Object value;

   public <T> HoverEvent(HoverEvent.Action<T> p_i232564_1_, T p_i232564_2_) {
      this.action = p_i232564_1_;
      this.value = p_i232564_2_;
   }

   /**
    * Gets the action to perform when this event is raised.
    */
   public HoverEvent.Action<?> getAction() {
      return this.action;
   }

   @Nullable
   public <T> T func_240662_a_(HoverEvent.Action<T> p_240662_1_) {
      return (T)(this.action == p_240662_1_ ? p_240662_1_.func_240674_b_(this.value) : null);
   }

   public boolean equals(Object p_equals_1_) {
      if (this == p_equals_1_) {
         return true;
      } else if (p_equals_1_ != null && this.getClass() == p_equals_1_.getClass()) {
         HoverEvent hoverevent = (HoverEvent)p_equals_1_;
         return this.action == hoverevent.action && Objects.equals(this.value, hoverevent.value);
      } else {
         return false;
      }
   }

   public String toString() {
      return "HoverEvent{action=" + this.action + ", value='" + this.value + '\'' + '}';
   }

   public int hashCode() {
      int i = this.action.hashCode();
      return 31 * i + (this.value != null ? this.value.hashCode() : 0);
   }

   @Nullable
   public static HoverEvent func_240661_a_(JsonObject p_240661_0_) {
      String s = JSONUtils.getString(p_240661_0_, "action", (String)null);
      if (s == null) {
         return null;
      } else {
         HoverEvent.Action<?> action = HoverEvent.Action.getValueByCanonicalName(s);
         if (action == null) {
            return null;
         } else {
            JsonElement jsonelement = p_240661_0_.get("contents");
            if (jsonelement != null) {
               return action.func_240668_a_(jsonelement);
            } else {
               ITextComponent itextcomponent = ITextComponent.Serializer.func_240641_a_(p_240661_0_.get("value"));
               return itextcomponent != null ? action.func_240670_a_(itextcomponent) : null;
            }
         }
      }
   }

   public JsonObject func_240663_b_() {
      JsonObject jsonobject = new JsonObject();
      jsonobject.addProperty("action", this.action.getCanonicalName());
      jsonobject.add("contents", this.action.func_240669_a_(this.value));
      return jsonobject;
   }

   public static class Action<T> {
      public static final HoverEvent.Action<ITextComponent> field_230550_a_ = new HoverEvent.Action<>("show_text", true, ITextComponent.Serializer::func_240641_a_, ITextComponent.Serializer::toJsonTree, Function.identity());
      public static final HoverEvent.Action<HoverEvent.ItemHover> field_230551_b_ = new HoverEvent.Action<>("show_item", true, (p_240673_0_) -> {
         return HoverEvent.ItemHover.func_240694_b_(p_240673_0_);
      }, (p_240676_0_) -> {
         return p_240676_0_.func_240693_b_();
      }, (p_240675_0_) -> {
         return HoverEvent.ItemHover.func_240695_b_(p_240675_0_);
      });
      public static final HoverEvent.Action<HoverEvent.EntityHover> field_230552_c_ = new HoverEvent.Action<>("show_entity", true, HoverEvent.EntityHover::func_240682_a_, HoverEvent.EntityHover::func_240681_a_, HoverEvent.EntityHover::func_240683_a_);
      private static final Map<String, HoverEvent.Action> NAME_MAPPING = Stream.of(field_230550_a_, field_230551_b_, field_230552_c_).collect(ImmutableMap.toImmutableMap(HoverEvent.Action::getCanonicalName, (p_240671_0_) -> {
         return p_240671_0_;
      }));
      private final String canonicalName;
      private final boolean allowedInChat;
      private final Function<JsonElement, T> field_240665_g_;
      private final Function<T, JsonElement> field_240666_h_;
      private final Function<ITextComponent, T> field_240667_i_;

      public Action(String p_i232565_1_, boolean p_i232565_2_, Function<JsonElement, T> p_i232565_3_, Function<T, JsonElement> p_i232565_4_, Function<ITextComponent, T> p_i232565_5_) {
         this.canonicalName = p_i232565_1_;
         this.allowedInChat = p_i232565_2_;
         this.field_240665_g_ = p_i232565_3_;
         this.field_240666_h_ = p_i232565_4_;
         this.field_240667_i_ = p_i232565_5_;
      }

      /**
       * Indicates whether this event can be run from chat text.
       */
      public boolean shouldAllowInChat() {
         return this.allowedInChat;
      }

      /**
       * Gets the canonical name for this action (e.g., "show_achievement")
       */
      public String getCanonicalName() {
         return this.canonicalName;
      }

      /**
       * Gets a value by its canonical name.
       */
      @Nullable
      public static HoverEvent.Action getValueByCanonicalName(String canonicalNameIn) {
         return NAME_MAPPING.get(canonicalNameIn);
      }

      private T func_240674_b_(Object p_240674_1_) {
         return (T)p_240674_1_;
      }

      @Nullable
      public HoverEvent func_240668_a_(JsonElement p_240668_1_) {
         T t = this.field_240665_g_.apply(p_240668_1_);
         return t == null ? null : new HoverEvent(this, t);
      }

      @Nullable
      public HoverEvent func_240670_a_(ITextComponent p_240670_1_) {
         T t = this.field_240667_i_.apply(p_240670_1_);
         return t == null ? null : new HoverEvent(this, t);
      }

      public JsonElement func_240669_a_(Object p_240669_1_) {
         return this.field_240666_h_.apply(this.func_240674_b_(p_240669_1_));
      }

      public String toString() {
         return "<action " + this.canonicalName + ">";
      }
   }

   public static class EntityHover {
      public final EntityType<?> field_240677_a_;
      public final UUID field_240678_b_;
      @Nullable
      public final ITextComponent field_240679_c_;
      @Nullable
      @OnlyIn(Dist.CLIENT)
      private List<ITextComponent> field_240680_d_;

      public EntityHover(EntityType<?> p_i232566_1_, UUID p_i232566_2_, @Nullable ITextComponent p_i232566_3_) {
         this.field_240677_a_ = p_i232566_1_;
         this.field_240678_b_ = p_i232566_2_;
         this.field_240679_c_ = p_i232566_3_;
      }

      @Nullable
      public static HoverEvent.EntityHover func_240682_a_(JsonElement p_240682_0_) {
         if (!p_240682_0_.isJsonObject()) {
            return null;
         } else {
            JsonObject jsonobject = p_240682_0_.getAsJsonObject();
            EntityType<?> entitytype = Registry.ENTITY_TYPE.getOrDefault(new ResourceLocation(JSONUtils.getString(jsonobject, "type")));
            UUID uuid = UUID.fromString(JSONUtils.getString(jsonobject, "id"));
            ITextComponent itextcomponent = ITextComponent.Serializer.func_240641_a_(jsonobject.get("name"));
            return new HoverEvent.EntityHover(entitytype, uuid, itextcomponent);
         }
      }

      @Nullable
      public static HoverEvent.EntityHover func_240683_a_(ITextComponent p_240683_0_) {
         try {
            CompoundNBT compoundnbt = JsonToNBT.getTagFromJson(p_240683_0_.getString());
            ITextComponent itextcomponent = ITextComponent.Serializer.func_240643_a_(compoundnbt.getString("name"));
            EntityType<?> entitytype = Registry.ENTITY_TYPE.getOrDefault(new ResourceLocation(compoundnbt.getString("type")));
            UUID uuid = UUID.fromString(compoundnbt.getString("id"));
            return new HoverEvent.EntityHover(entitytype, uuid, itextcomponent);
         } catch (CommandSyntaxException | JsonSyntaxException jsonsyntaxexception) {
            return null;
         }
      }

      public JsonElement func_240681_a_() {
         JsonObject jsonobject = new JsonObject();
         jsonobject.addProperty("type", Registry.ENTITY_TYPE.getKey(this.field_240677_a_).toString());
         jsonobject.addProperty("id", this.field_240678_b_.toString());
         if (this.field_240679_c_ != null) {
            jsonobject.add("name", ITextComponent.Serializer.toJsonTree(this.field_240679_c_));
         }

         return jsonobject;
      }

      @OnlyIn(Dist.CLIENT)
      public List<ITextComponent> func_240684_b_() {
         if (this.field_240680_d_ == null) {
            this.field_240680_d_ = Lists.newArrayList();
            if (this.field_240679_c_ != null) {
               this.field_240680_d_.add(this.field_240679_c_);
            }

            this.field_240680_d_.add(new TranslationTextComponent("gui.entity_tooltip.type", this.field_240677_a_.getName()));
            this.field_240680_d_.add(new StringTextComponent(this.field_240678_b_.toString()));
         }

         return this.field_240680_d_;
      }

      public boolean equals(Object p_equals_1_) {
         if (this == p_equals_1_) {
            return true;
         } else if (p_equals_1_ != null && this.getClass() == p_equals_1_.getClass()) {
            HoverEvent.EntityHover hoverevent$entityhover = (HoverEvent.EntityHover)p_equals_1_;
            return this.field_240677_a_.equals(hoverevent$entityhover.field_240677_a_) && this.field_240678_b_.equals(hoverevent$entityhover.field_240678_b_) && Objects.equals(this.field_240679_c_, hoverevent$entityhover.field_240679_c_);
         } else {
            return false;
         }
      }

      public int hashCode() {
         int i = this.field_240677_a_.hashCode();
         i = 31 * i + this.field_240678_b_.hashCode();
         return 31 * i + (this.field_240679_c_ != null ? this.field_240679_c_.hashCode() : 0);
      }
   }

   public static class ItemHover {
      private final Item field_240685_a_;
      private final int field_240686_b_;
      @Nullable
      private final CompoundNBT field_240687_c_;
      @Nullable
      @OnlyIn(Dist.CLIENT)
      private ItemStack field_240688_d_;

      ItemHover(Item p_i232567_1_, int p_i232567_2_, @Nullable CompoundNBT p_i232567_3_) {
         this.field_240685_a_ = p_i232567_1_;
         this.field_240686_b_ = p_i232567_2_;
         this.field_240687_c_ = p_i232567_3_;
      }

      public ItemHover(ItemStack p_i232568_1_) {
         this(p_i232568_1_.getItem(), p_i232568_1_.getCount(), p_i232568_1_.getTag() != null ? p_i232568_1_.getTag().copy() : null);
      }

      public boolean equals(Object p_equals_1_) {
         if (this == p_equals_1_) {
            return true;
         } else if (p_equals_1_ != null && this.getClass() == p_equals_1_.getClass()) {
            HoverEvent.ItemHover hoverevent$itemhover = (HoverEvent.ItemHover)p_equals_1_;
            return this.field_240686_b_ == hoverevent$itemhover.field_240686_b_ && this.field_240685_a_.equals(hoverevent$itemhover.field_240685_a_) && Objects.equals(this.field_240687_c_, hoverevent$itemhover.field_240687_c_);
         } else {
            return false;
         }
      }

      public int hashCode() {
         int i = this.field_240685_a_.hashCode();
         i = 31 * i + this.field_240686_b_;
         return 31 * i + (this.field_240687_c_ != null ? this.field_240687_c_.hashCode() : 0);
      }

      @OnlyIn(Dist.CLIENT)
      public ItemStack func_240689_a_() {
         if (this.field_240688_d_ == null) {
            this.field_240688_d_ = new ItemStack(this.field_240685_a_, this.field_240686_b_);
            if (this.field_240687_c_ != null) {
               this.field_240688_d_.setTag(this.field_240687_c_);
            }
         }

         return this.field_240688_d_;
      }

      private static HoverEvent.ItemHover func_240694_b_(JsonElement p_240694_0_) {
         if (p_240694_0_.isJsonPrimitive()) {
            return new HoverEvent.ItemHover(Registry.ITEM.getOrDefault(new ResourceLocation(p_240694_0_.getAsString())), 1, (CompoundNBT)null);
         } else {
            JsonObject jsonobject = JSONUtils.getJsonObject(p_240694_0_, "item");
            Item item = Registry.ITEM.getOrDefault(new ResourceLocation(JSONUtils.getString(jsonobject, "id")));
            int i = JSONUtils.getInt(jsonobject, "count", 1);
            if (jsonobject.has("tag")) {
               String s = JSONUtils.getString(jsonobject, "tag");

               try {
                  CompoundNBT compoundnbt = JsonToNBT.getTagFromJson(s);
                  return new HoverEvent.ItemHover(item, i, compoundnbt);
               } catch (CommandSyntaxException commandsyntaxexception) {
                  HoverEvent.field_240660_a_.warn("Failed to parse tag: {}", s, commandsyntaxexception);
               }
            }

            return new HoverEvent.ItemHover(item, i, (CompoundNBT)null);
         }
      }

      @Nullable
      private static HoverEvent.ItemHover func_240695_b_(ITextComponent p_240695_0_) {
         try {
            CompoundNBT compoundnbt = JsonToNBT.getTagFromJson(p_240695_0_.getString());
            return new HoverEvent.ItemHover(ItemStack.read(compoundnbt));
         } catch (CommandSyntaxException commandsyntaxexception) {
            HoverEvent.field_240660_a_.warn("Failed to parse item tag: {}", p_240695_0_, commandsyntaxexception);
            return null;
         }
      }

      private JsonElement func_240693_b_() {
         JsonObject jsonobject = new JsonObject();
         jsonobject.addProperty("id", Registry.ITEM.getKey(this.field_240685_a_).toString());
         if (this.field_240686_b_ != 1) {
            jsonobject.addProperty("count", this.field_240686_b_);
         }

         if (this.field_240687_c_ != null) {
            jsonobject.addProperty("tag", this.field_240687_c_.toString());
         }

         return jsonobject;
      }
   }
}