package net.minecraft.tags;

import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.ImmutableSet.Builder;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.annotation.Nullable;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.DefaultedRegistry;
import net.minecraft.util.registry.Registry;

public interface ITagCollection<T> {
   Map<ResourceLocation, ITag<T>> func_241833_a();

   @Nullable
   default ITag<T> get(ResourceLocation resourceLocationIn) {
      return this.func_241833_a().get(resourceLocationIn);
   }

   ITag<T> func_241834_b(ResourceLocation p_241834_1_);

   @Nullable
   ResourceLocation func_232973_a_(ITag<T> p_232973_1_);

   default ResourceLocation func_232975_b_(ITag<T> p_232975_1_) {
      ResourceLocation resourcelocation = this.func_232973_a_(p_232975_1_);
      if (resourcelocation == null) {
         throw new IllegalStateException("Unrecognized tag");
      } else {
         return resourcelocation;
      }
   }

   default Collection<ResourceLocation> getRegisteredTags() {
      return this.func_241833_a().keySet();
   }

   default Collection<ResourceLocation> getOwningTags(T itemIn) {
      List<ResourceLocation> list = Lists.newArrayList();

      for(Entry<ResourceLocation, ITag<T>> entry : this.func_241833_a().entrySet()) {
         if (entry.getValue().func_230235_a_(itemIn)) {
            list.add(entry.getKey());
         }
      }

      return list;
   }

   default void func_242203_a(PacketBuffer p_242203_1_, DefaultedRegistry<T> p_242203_2_) {
      Map<ResourceLocation, ITag<T>> map = this.func_241833_a();
      p_242203_1_.writeVarInt(map.size());

      for(Entry<ResourceLocation, ITag<T>> entry : map.entrySet()) {
         p_242203_1_.writeResourceLocation(entry.getKey());
         p_242203_1_.writeVarInt(entry.getValue().func_230236_b_().size());

         for(T t : entry.getValue().func_230236_b_()) {
            p_242203_1_.writeVarInt(p_242203_2_.getId(t));
         }
      }

   }

   static <T> ITagCollection<T> func_242204_a(PacketBuffer p_242204_0_, Registry<T> p_242204_1_) {
      Map<ResourceLocation, ITag<T>> map = Maps.newHashMap();
      int i = p_242204_0_.readVarInt();

      for(int j = 0; j < i; ++j) {
         ResourceLocation resourcelocation = p_242204_0_.readResourceLocation();
         int k = p_242204_0_.readVarInt();
         Builder<T> builder = ImmutableSet.builder();

         for(int l = 0; l < k; ++l) {
            builder.add(p_242204_1_.getByValue(p_242204_0_.readVarInt()));
         }

         map.put(resourcelocation, ITag.func_232946_a_(builder.build()));
      }

      return func_242202_a(map);
   }

   static <T> ITagCollection<T> func_242205_c() {
      return func_242202_a(ImmutableBiMap.of());
   }

   static <T> ITagCollection<T> func_242202_a(Map<ResourceLocation, ITag<T>> p_242202_0_) {
      final BiMap<ResourceLocation, ITag<T>> bimap = ImmutableBiMap.copyOf(p_242202_0_);
      return new ITagCollection<T>() {
         private final ITag<T> field_242207_b = Tag.func_241284_a_();

         public ITag<T> func_241834_b(ResourceLocation p_241834_1_) {
            return bimap.getOrDefault(p_241834_1_, this.field_242207_b);
         }

         @Nullable
         public ResourceLocation func_232973_a_(ITag<T> p_232973_1_) {
            return p_232973_1_ instanceof ITag.INamedTag ? ((ITag.INamedTag)p_232973_1_).func_230234_a_() : bimap.inverse().get(p_232973_1_);
         }

         public Map<ResourceLocation, ITag<T>> func_241833_a() {
            return bimap;
         }
      };
   }
}