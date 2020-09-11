package net.minecraft.tags;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;

public interface ITag<T> {
   static <T> Codec<ITag<T>> func_232947_a_(Supplier<ITagCollection<T>> p_232947_0_) {
      return ResourceLocation.field_240908_a_.flatXmap((p_232949_1_) -> {
         return Optional.ofNullable(p_232947_0_.get().get(p_232949_1_)).map(DataResult::success).orElseGet(() -> {
            return DataResult.error("Unknown tag: " + p_232949_1_);
         });
      }, (p_232948_1_) -> {
         return Optional.ofNullable(p_232947_0_.get().func_232973_a_(p_232948_1_)).map(DataResult::success).orElseGet(() -> {
            return DataResult.error("Unknown tag: " + p_232948_1_);
         });
      });
   }

   boolean func_230235_a_(T p_230235_1_);

   List<T> func_230236_b_();

   default T getRandomElement(Random random) {
      List<T> list = this.func_230236_b_();
      return list.get(random.nextInt(list.size()));
   }

   static <T> ITag<T> func_232946_a_(Set<T> p_232946_0_) {
      return Tag.func_241286_a_(p_232946_0_);
   }

   public static class Builder {
      private final List<ITag.Proxy> field_232953_a_ = Lists.newArrayList();
      private boolean replace = false;

      public static ITag.Builder create() {
         return new ITag.Builder();
      }

      public ITag.Builder func_232954_a_(ITag.Proxy p_232954_1_) {
         this.field_232953_a_.add(p_232954_1_);
         return this;
      }

      public ITag.Builder func_232955_a_(ITag.ITagEntry p_232955_1_, String p_232955_2_) {
         return this.func_232954_a_(new ITag.Proxy(p_232955_1_, p_232955_2_));
      }

      public ITag.Builder func_232961_a_(ResourceLocation p_232961_1_, String p_232961_2_) {
         return this.func_232955_a_(new ITag.ItemEntry(p_232961_1_), p_232961_2_);
      }

      public ITag.Builder func_232964_b_(ResourceLocation p_232964_1_, String p_232964_2_) {
         return this.func_232955_a_(new ITag.TagEntry(p_232964_1_), p_232964_2_);
      }

      public ITag.Builder replace(boolean value) {
         this.replace = value;
         return this;
      }

      public ITag.Builder replace() {
         return replace(true);
      }

      public <T> Optional<ITag<T>> func_232959_a_(Function<ResourceLocation, ITag<T>> p_232959_1_, Function<ResourceLocation, T> p_232959_2_) {
         ImmutableSet.Builder<T> builder = ImmutableSet.builder();

         for(ITag.Proxy itag$proxy : this.field_232953_a_) {
            if (!itag$proxy.func_232968_a_().func_230238_a_(p_232959_1_, p_232959_2_, builder::add)) {
               return Optional.empty();
            }
         }

         return Optional.of(ITag.func_232946_a_(builder.build()));
      }

      public Stream<ITag.Proxy> func_232962_b_() {
         return this.field_232953_a_.stream();
      }

      public <T> Stream<ITag.Proxy> func_232963_b_(Function<ResourceLocation, ITag<T>> p_232963_1_, Function<ResourceLocation, T> p_232963_2_) {
         return this.func_232962_b_().filter((p_232960_2_) -> {
            return !p_232960_2_.func_232968_a_().func_230238_a_(p_232963_1_, p_232963_2_, (p_232957_0_) -> {
            });
         });
      }

      public ITag.Builder func_232956_a_(JsonObject p_232956_1_, String p_232956_2_) {
         JsonArray jsonarray = JSONUtils.getJsonArray(p_232956_1_, "values");
         List<ITag.ITagEntry> list = Lists.newArrayList();

         for(JsonElement jsonelement : jsonarray) {
            list.add(func_242199_a(jsonelement));
         }

         if (JSONUtils.getBoolean(p_232956_1_, "replace", false)) {
            this.field_232953_a_.clear();
         }

         net.minecraftforge.common.ForgeHooks.deserializeTagAdditions(list, p_232956_1_, field_232953_a_);
         list.forEach((p_232958_2_) -> {
            this.field_232953_a_.add(new ITag.Proxy(p_232958_2_, p_232956_2_));
         });
         return this;
      }

      private static ITag.ITagEntry func_242199_a(JsonElement p_242199_0_) {
         String s;
         boolean flag;
         if (p_242199_0_.isJsonObject()) {
            JsonObject jsonobject = p_242199_0_.getAsJsonObject();
            s = JSONUtils.getString(jsonobject, "id");
            flag = JSONUtils.getBoolean(jsonobject, "required", true);
         } else {
            s = JSONUtils.getString(p_242199_0_, "id");
            flag = true;
         }

         if (s.startsWith("#")) {
            ResourceLocation resourcelocation1 = new ResourceLocation(s.substring(1));
            return (ITag.ITagEntry)(flag ? new ITag.TagEntry(resourcelocation1) : new ITag.OptionalTagEntry(resourcelocation1));
         } else {
            ResourceLocation resourcelocation = new ResourceLocation(s);
            return (ITag.ITagEntry)(flag ? new ITag.ItemEntry(resourcelocation) : new ITag.OptionalItemEntry(resourcelocation));
         }
      }

      public JsonObject func_232965_c_() {
         JsonObject jsonobject = new JsonObject();
         JsonArray jsonarray = new JsonArray();

         for(ITag.Proxy itag$proxy : this.field_232953_a_) {
            itag$proxy.func_232968_a_().func_230237_a_(jsonarray);
         }

         jsonobject.addProperty("replace", replace);
         jsonobject.add("values", jsonarray);
         return jsonobject;
      }
   }

   public interface INamedTag<T> extends ITag<T> {
      ResourceLocation func_230234_a_();
   }

   public interface ITagEntry {
      <T> boolean func_230238_a_(Function<ResourceLocation, ITag<T>> p_230238_1_, Function<ResourceLocation, T> p_230238_2_, Consumer<T> p_230238_3_);

      void func_230237_a_(JsonArray p_230237_1_);
   }

   public static class ItemEntry implements ITag.ITagEntry {
      private final ResourceLocation field_232969_a_;

      public ItemEntry(ResourceLocation p_i231435_1_) {
         this.field_232969_a_ = p_i231435_1_;
      }

      public <T> boolean func_230238_a_(Function<ResourceLocation, ITag<T>> p_230238_1_, Function<ResourceLocation, T> p_230238_2_, Consumer<T> p_230238_3_) {
         T t = p_230238_2_.apply(this.field_232969_a_);
         if (t == null) {
            return false;
         } else {
            p_230238_3_.accept(t);
            return true;
         }
      }

      public void func_230237_a_(JsonArray p_230237_1_) {
         p_230237_1_.add(this.field_232969_a_.toString());
      }

      public String toString() {
         return this.field_232969_a_.toString();
      }
      @Override public boolean equals(Object o) { return o == this || (o instanceof ITag.ItemEntry && java.util.Objects.equals(this.field_232969_a_, ((ITag.ItemEntry) o).field_232969_a_)); }
   }

   public static class OptionalItemEntry implements ITag.ITagEntry {
      private final ResourceLocation field_242200_a;

      public OptionalItemEntry(ResourceLocation p_i241895_1_) {
         this.field_242200_a = p_i241895_1_;
      }

      public <T> boolean func_230238_a_(Function<ResourceLocation, ITag<T>> p_230238_1_, Function<ResourceLocation, T> p_230238_2_, Consumer<T> p_230238_3_) {
         T t = p_230238_2_.apply(this.field_242200_a);
         if (t != null) {
            p_230238_3_.accept(t);
         }

         return true;
      }

      public void func_230237_a_(JsonArray p_230237_1_) {
         JsonObject jsonobject = new JsonObject();
         jsonobject.addProperty("id", this.field_242200_a.toString());
         jsonobject.addProperty("required", false);
         p_230237_1_.add(jsonobject);
      }

      public String toString() {
         return this.field_242200_a.toString() + "?";
      }
   }

   public static class OptionalTagEntry implements ITag.ITagEntry {
      private final ResourceLocation field_242201_a;

      public OptionalTagEntry(ResourceLocation p_i241896_1_) {
         this.field_242201_a = p_i241896_1_;
      }

      public <T> boolean func_230238_a_(Function<ResourceLocation, ITag<T>> p_230238_1_, Function<ResourceLocation, T> p_230238_2_, Consumer<T> p_230238_3_) {
         ITag<T> itag = p_230238_1_.apply(this.field_242201_a);
         if (itag != null) {
            itag.func_230236_b_().forEach(p_230238_3_);
         }

         return true;
      }

      public void func_230237_a_(JsonArray p_230237_1_) {
         JsonObject jsonobject = new JsonObject();
         jsonobject.addProperty("id", "#" + this.field_242201_a);
         jsonobject.addProperty("required", false);
         p_230237_1_.add(jsonobject);
      }

      public String toString() {
         return "#" + this.field_242201_a + "?";
      }
   }

   public static class Proxy {
      private final ITag.ITagEntry field_232966_a_;
      private final String field_232967_b_;

      private Proxy(ITag.ITagEntry p_i231433_1_, String p_i231433_2_) {
         this.field_232966_a_ = p_i231433_1_;
         this.field_232967_b_ = p_i231433_2_;
      }

      public ITag.ITagEntry func_232968_a_() {
         return this.field_232966_a_;
      }

      public String toString() {
         return this.field_232966_a_.toString() + " (from " + this.field_232967_b_ + ")";
      }
   }

   public static class TagEntry implements ITag.ITagEntry {
      private final ResourceLocation id;

      public TagEntry(ResourceLocation resourceLocationIn) {
         this.id = resourceLocationIn;
      }

      public <T> boolean func_230238_a_(Function<ResourceLocation, ITag<T>> p_230238_1_, Function<ResourceLocation, T> p_230238_2_, Consumer<T> p_230238_3_) {
         ITag<T> itag = p_230238_1_.apply(this.id);
         if (itag == null) {
            return false;
         } else {
            itag.func_230236_b_().forEach(p_230238_3_);
            return true;
         }
      }

      public void func_230237_a_(JsonArray p_230237_1_) {
         p_230237_1_.add("#" + this.id);
      }

      public String toString() {
         return "#" + this.id;
      }
      @Override public boolean equals(Object o) { return o == this || (o instanceof ITag.TagEntry && java.util.Objects.equals(this.id, ((ITag.TagEntry) o).id)); }
      public ResourceLocation getId() { return id; }
   }
}