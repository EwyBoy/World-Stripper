package net.minecraft.data;

import java.nio.file.Path;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

public class FluidTagsProvider extends TagsProvider<Fluid> {
   @Deprecated
   public FluidTagsProvider(DataGenerator generatorIn) {
      super(generatorIn, Registry.FLUID);
   }
   public FluidTagsProvider(DataGenerator generatorIn, String modId, @javax.annotation.Nullable net.minecraftforge.common.data.ExistingFileHelper existingFileHelper) {
      super(generatorIn, Registry.FLUID, modId, existingFileHelper);
   }

   protected void registerTags() {
      this.func_240522_a_(FluidTags.WATER).func_240534_a_(Fluids.WATER, Fluids.FLOWING_WATER);
      this.func_240522_a_(FluidTags.LAVA).func_240534_a_(Fluids.LAVA, Fluids.FLOWING_LAVA);
   }

   /**
    * Resolves a Path for the location to save the given tag.
    */
   protected Path makePath(ResourceLocation id) {
      return this.generator.getOutputFolder().resolve("data/" + id.getNamespace() + "/tags/fluids/" + id.getPath() + ".json");
   }

   /**
    * Gets a name for this provider, to use in logging.
    */
   public String getName() {
      return "Fluid Tags";
   }
}