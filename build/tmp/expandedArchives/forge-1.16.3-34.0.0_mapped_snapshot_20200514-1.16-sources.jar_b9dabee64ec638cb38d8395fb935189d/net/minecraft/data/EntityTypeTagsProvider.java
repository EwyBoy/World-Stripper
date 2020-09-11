package net.minecraft.data;

import java.nio.file.Path;
import net.minecraft.entity.EntityType;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

public class EntityTypeTagsProvider extends TagsProvider<EntityType<?>> {
   @Deprecated
   public EntityTypeTagsProvider(DataGenerator p_i50784_1_) {
      super(p_i50784_1_, Registry.ENTITY_TYPE);
   }
   public EntityTypeTagsProvider(DataGenerator p_i50784_1_, String modId, @javax.annotation.Nullable net.minecraftforge.common.data.ExistingFileHelper existingFileHelper) {
      super(p_i50784_1_, Registry.ENTITY_TYPE, modId, existingFileHelper);
   }

   protected void registerTags() {
      this.func_240522_a_(EntityTypeTags.SKELETONS).func_240534_a_(EntityType.SKELETON, EntityType.STRAY, EntityType.WITHER_SKELETON);
      this.func_240522_a_(EntityTypeTags.RAIDERS).func_240534_a_(EntityType.EVOKER, EntityType.PILLAGER, EntityType.RAVAGER, EntityType.VINDICATOR, EntityType.ILLUSIONER, EntityType.WITCH);
      this.func_240522_a_(EntityTypeTags.BEEHIVE_INHABITORS).func_240532_a_(EntityType.BEE);
      this.func_240522_a_(EntityTypeTags.ARROWS).func_240534_a_(EntityType.ARROW, EntityType.SPECTRAL_ARROW);
      this.func_240522_a_(EntityTypeTags.field_232893_e_).func_240531_a_(EntityTypeTags.ARROWS).func_240534_a_(EntityType.SNOWBALL, EntityType.FIREBALL, EntityType.SMALL_FIREBALL, EntityType.EGG, EntityType.TRIDENT, EntityType.DRAGON_FIREBALL, EntityType.WITHER_SKULL);
   }

   /**
    * Resolves a Path for the location to save the given tag.
    */
   protected Path makePath(ResourceLocation id) {
      return this.generator.getOutputFolder().resolve("data/" + id.getNamespace() + "/tags/entity_types/" + id.getPath() + ".json");
   }

   /**
    * Gets a name for this provider, to use in logging.
    */
   public String getName() {
      return "Entity Type Tags";
   }
}