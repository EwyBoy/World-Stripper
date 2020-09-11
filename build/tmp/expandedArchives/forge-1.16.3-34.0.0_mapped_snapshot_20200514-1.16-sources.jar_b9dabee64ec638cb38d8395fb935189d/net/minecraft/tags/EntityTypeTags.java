package net.minecraft.tags;

import java.util.List;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;

public final class EntityTypeTags {
   protected static final TagRegistry<EntityType<?>> tagCollection = TagRegistryManager.func_242196_a(new ResourceLocation("entity_type"), ITagCollectionSupplier::func_241838_d);
   public static final ITag.INamedTag<EntityType<?>> SKELETONS = func_232896_a_("skeletons");
   public static final ITag.INamedTag<EntityType<?>> RAIDERS = func_232896_a_("raiders");
   public static final ITag.INamedTag<EntityType<?>> BEEHIVE_INHABITORS = func_232896_a_("beehive_inhabitors");
   public static final ITag.INamedTag<EntityType<?>> ARROWS = func_232896_a_("arrows");
   public static final ITag.INamedTag<EntityType<?>> field_232893_e_ = func_232896_a_("impact_projectiles");

   public static ITag.INamedTag<EntityType<?>> func_232896_a_(String p_232896_0_) {
      return tagCollection.func_232937_a_(p_232896_0_);
   }

   public static net.minecraftforge.common.Tags.IOptionalNamedTag<EntityType<?>> createOptional(ResourceLocation name) {
       return tagCollection.createOptional(name, () -> null);
   }

   public static ITagCollection<EntityType<?>> getCollection() {
      return tagCollection.func_232939_b_();
   }

   public static List<? extends ITag.INamedTag<EntityType<?>>> func_242175_b() {
      return tagCollection.func_241288_c_();
   }
}