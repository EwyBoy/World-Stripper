package net.minecraft.tags;

import java.util.List;
import net.minecraft.fluid.Fluid;
import net.minecraft.util.ResourceLocation;

public final class FluidTags {
   protected static final TagRegistry<Fluid> collection = TagRegistryManager.func_242196_a(new ResourceLocation("fluid"), ITagCollectionSupplier::func_241837_c);
   public static final ITag.INamedTag<Fluid> WATER = makeWrapperTag("water");
   public static final ITag.INamedTag<Fluid> LAVA = makeWrapperTag("lava");

   public static ITag.INamedTag<Fluid> makeWrapperTag(String p_206956_0_) {
      return collection.func_232937_a_(p_206956_0_);
   }

   public static net.minecraftforge.common.Tags.IOptionalNamedTag<Fluid> createOptional(ResourceLocation name) {
       return collection.createOptional(name, () -> null);
   }

   public static List<? extends ITag.INamedTag<Fluid>> func_241280_c_() {
      return collection.func_241288_c_();
   }

   //Forge: Readd this stripped getter like the other tag classes
   public static ITagCollection<Fluid> getCollection() {
       return collection.func_232939_b_();
   }
}