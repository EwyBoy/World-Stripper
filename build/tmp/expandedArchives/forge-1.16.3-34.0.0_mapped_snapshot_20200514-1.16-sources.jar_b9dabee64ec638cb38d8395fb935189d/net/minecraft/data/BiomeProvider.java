package net.minecraft.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.function.Supplier;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.biome.Biome;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BiomeProvider implements IDataProvider {
   private static final Logger field_244195_b = LogManager.getLogger();
   private static final Gson field_244196_c = (new GsonBuilder()).setPrettyPrinting().create();
   private final DataGenerator field_244197_d;

   public BiomeProvider(DataGenerator p_i242077_1_) {
      this.field_244197_d = p_i242077_1_;
   }

   /**
    * Performs this provider's action.
    */
   public void act(DirectoryCache cache) {
      Path path = this.field_244197_d.getOutputFolder();

      for(Entry<RegistryKey<Biome>, Biome> entry : WorldGenRegistries.field_243657_i.func_239659_c_()) {
         Path path1 = func_244199_a(path, entry.getKey().func_240901_a_());
         Biome biome = entry.getValue();
         Function<Supplier<Biome>, DataResult<JsonElement>> function = JsonOps.INSTANCE.withEncoder(Biome.field_235051_b_);

         try {
            Optional<JsonElement> optional = function.apply(() -> {
               return biome;
            }).result();
            if (optional.isPresent()) {
               IDataProvider.save(field_244196_c, cache, optional.get(), path1);
            } else {
               field_244195_b.error("Couldn't serialize biome {}", (Object)path1);
            }
         } catch (IOException ioexception) {
            field_244195_b.error("Couldn't save biome {}", path1, ioexception);
         }
      }

   }

   private static Path func_244199_a(Path p_244199_0_, ResourceLocation p_244199_1_) {
      return p_244199_0_.resolve("reports/biomes/" + p_244199_1_.getPath() + ".json");
   }

   /**
    * Gets a name for this provider, to use in logging.
    */
   public String getName() {
      return "Biomes";
   }
}