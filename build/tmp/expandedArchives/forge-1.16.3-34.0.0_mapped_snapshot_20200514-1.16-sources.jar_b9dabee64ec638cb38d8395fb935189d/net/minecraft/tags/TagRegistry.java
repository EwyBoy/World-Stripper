package net.minecraft.tags;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class TagRegistry<T> {
   private ITagCollection<T> field_232930_b_ = ITagCollection.func_242205_c();
   private final List<TagRegistry.NamedTag<T>> field_232931_c_ = Lists.newArrayList();
   private final Function<ITagCollectionSupplier, ITagCollection<T>> field_242184_c;
   private static java.util.Map<ResourceLocation, List<TagRegistry.NamedTag<?>>> toAdd = com.google.common.collect.Maps.newHashMap();

   public TagRegistry(Function<ITagCollectionSupplier, ITagCollection<T>> p_i241894_1_) {
      this.field_242184_c = p_i241894_1_;
   }

   public ITag.INamedTag<T> func_232937_a_(String p_232937_1_) {
       return add(new TagRegistry.NamedTag<>(new ResourceLocation(p_232937_1_)));
   }

   public net.minecraftforge.common.Tags.IOptionalNamedTag<T> createOptional(ResourceLocation key, @Nullable java.util.function.Supplier<Set<T>> defaults) {
       return add(new TagRegistry.OptionalNamedTag<>(key, defaults));
   }

   /** Call via ForgeTagHandler#makeWrapperTag to avoid any exceptions due to calling this after it is safe to call {@link #func_232937_a_(String)} */
   public static <T> ITag.INamedTag<T> createDelayedTag(ResourceLocation tagRegistry, ResourceLocation name) {
      return delayedAdd(tagRegistry, new TagRegistry.NamedTag<>(name));
   }

   /** Call via ForgeTagHandler#createOptionalTag to avoid any exceptions due to calling this after it is safe to call {@link #createOptional(ResourceLocation, java.util.function.Supplier)} */
   public static <T> net.minecraftforge.common.Tags.IOptionalNamedTag<T> createDelayedOptional(ResourceLocation tagRegistry, ResourceLocation key, @Nullable java.util.function.Supplier<Set<T>> defaults) {
      return delayedAdd(tagRegistry, new TagRegistry.OptionalNamedTag<>(key, defaults));
   }

   private static synchronized <T, R extends TagRegistry.NamedTag<T>> R delayedAdd(ResourceLocation tagRegistry, R tag) {
      if (toAdd == null) throw new RuntimeException("Creating delayed tags or optional tags, is only supported before custom tag types have been added.");
      toAdd.computeIfAbsent(tagRegistry, registry -> Lists.newArrayList()).add(tag);
      return tag;
   }

   public static void performDelayedAdd() {
      if (toAdd != null) {
         for (java.util.Map.Entry<ResourceLocation, List<TagRegistry.NamedTag<?>>> entry : toAdd.entrySet()) {
            TagRegistry<?> tagRegistry = TagRegistryManager.get(entry.getKey());
            if (tagRegistry == null) throw new RuntimeException("A mod attempted to add a delayed tag for a registry that doesn't have custom tag support.");
            for (TagRegistry.NamedTag<?> tag : entry.getValue()) {
               tagRegistry.add((TagRegistry.NamedTag) tag);
            }
         }
         toAdd = null;
      }
   }

   private <R extends TagRegistry.NamedTag<T>> R add(R namedtag) {
      namedtag.func_232943_a_(field_232930_b_::get);
      this.field_232931_c_.add(namedtag);
      return namedtag;
   }

   @OnlyIn(Dist.CLIENT)
   public void func_232932_a_() {
      this.field_232930_b_ = ITagCollection.func_242205_c();
      ITag<T> itag = Tag.func_241284_a_();
      this.field_232931_c_.forEach((p_232933_1_) -> {
         p_232933_1_.func_232943_a_((p_232934_1_) -> {
            return itag;
         });
      });
   }

   public void func_242188_a(ITagCollectionSupplier p_242188_1_) {
      ITagCollection<T> itagcollection = this.field_242184_c.apply(p_242188_1_);
      this.field_232930_b_ = itagcollection;
      this.field_232931_c_.forEach((p_232936_1_) -> {
         p_232936_1_.func_232943_a_(itagcollection::get);
      });
   }

   public ITagCollection<T> func_232939_b_() {
      return this.field_232930_b_;
   }

   public List<? extends ITag.INamedTag<T>> func_241288_c_() {
      return this.field_232931_c_;
   }

   public Set<ResourceLocation> func_242189_b(ITagCollectionSupplier p_242189_1_) {
      ITagCollection<T> itagcollection = this.field_242184_c.apply(p_242189_1_);
      Set<ResourceLocation> set = this.field_232931_c_.stream().filter(e -> !(e instanceof OptionalNamedTag)).map(TagRegistry.NamedTag::func_230234_a_).collect(Collectors.toSet());
      ImmutableSet<ResourceLocation> immutableset = ImmutableSet.copyOf(itagcollection.getRegisteredTags());
      return Sets.difference(set, immutableset);
   }

   static class NamedTag<T> implements ITag.INamedTag<T> {
      @Nullable
      protected ITag<T> field_232942_b_;
      protected final ResourceLocation field_232941_a_;

      private NamedTag(ResourceLocation p_i231430_1_) {
         this.field_232941_a_ = p_i231430_1_;
      }

      public ResourceLocation func_230234_a_() {
         return this.field_232941_a_;
      }

      private ITag<T> func_232944_c_() {
         if (this.field_232942_b_ == null) {
            throw new IllegalStateException("Tag " + this.field_232941_a_ + " used before it was bound");
         } else {
            return this.field_232942_b_;
         }
      }

      void func_232943_a_(Function<ResourceLocation, ITag<T>> p_232943_1_) {
         this.field_232942_b_ = p_232943_1_.apply(this.field_232941_a_);
      }

      public boolean func_230235_a_(T p_230235_1_) {
         return this.func_232944_c_().func_230235_a_(p_230235_1_);
      }

      public List<T> func_230236_b_() {
         return this.func_232944_c_().func_230236_b_();
      }

      @Override
      public String toString() {
          return "NamedTag[" + func_230234_a_().toString() + ']';
      }
      @Override public boolean equals(Object o) { return (o == this) || (o instanceof ITag.INamedTag && java.util.Objects.equals(this.func_230234_a_(), ((ITag.INamedTag<T>)o).func_230234_a_())); }
      @Override public int hashCode() { return func_230234_a_().hashCode(); }
   }

   private static class OptionalNamedTag<T> extends NamedTag<T> implements net.minecraftforge.common.Tags.IOptionalNamedTag<T> {
      @Nullable
      private final java.util.function.Supplier<Set<T>> defaults;
      private boolean defaulted = true;

      private OptionalNamedTag(ResourceLocation name, @Nullable java.util.function.Supplier<Set<T>> defaults) {
         super(name);
         this.defaults = defaults;
      }

      @Override
      public boolean isDefaulted() {
         return defaulted;
      }

      @Override
      void func_232943_a_(Function<ResourceLocation, ITag<T>> p_232943_1_) {
         super.func_232943_a_(p_232943_1_);
         if (this.field_232942_b_ == null) {
            this.defaulted = true;
            Set<T> defs = defaults == null ? null : defaults.get();
            this.field_232942_b_ = defs == null ? Tag.func_241284_a_() : Tag.func_241286_a_(defs);
         } else {
            this.defaulted = false;
         }
      }

      @Override
      public String toString() {
          return "OptionalNamedTag[" + func_230234_a_().toString() + ']';
      }
   }
}