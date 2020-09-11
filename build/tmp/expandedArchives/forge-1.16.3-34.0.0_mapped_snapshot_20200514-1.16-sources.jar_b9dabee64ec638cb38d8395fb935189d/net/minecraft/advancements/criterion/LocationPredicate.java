package net.minecraft.advancements.criterion;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.mojang.serialization.JsonOps;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.block.CampfireBlock;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.server.ServerWorld;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LocationPredicate {
   private static final Logger field_235305_b_ = LogManager.getLogger();
   public static final LocationPredicate ANY = new LocationPredicate(MinMaxBounds.FloatBound.UNBOUNDED, MinMaxBounds.FloatBound.UNBOUNDED, MinMaxBounds.FloatBound.UNBOUNDED, (RegistryKey<Biome>)null, (Structure<?>)null, (RegistryKey<World>)null, (Boolean)null, LightPredicate.field_226854_a_, BlockPredicate.field_226231_a_, FluidPredicate.field_226643_a_);
   private final MinMaxBounds.FloatBound x;
   private final MinMaxBounds.FloatBound y;
   private final MinMaxBounds.FloatBound z;
   @Nullable
   private final RegistryKey<Biome> biome;
   @Nullable
   private final Structure<?> feature;
   @Nullable
   private final RegistryKey<World> dimension;
   @Nullable
   private final Boolean field_235306_i_;
   private final LightPredicate field_226864_h_;
   private final BlockPredicate field_226865_i_;
   private final FluidPredicate field_226866_j_;

   public LocationPredicate(MinMaxBounds.FloatBound p_i241961_1_, MinMaxBounds.FloatBound p_i241961_2_, MinMaxBounds.FloatBound p_i241961_3_, @Nullable RegistryKey<Biome> p_i241961_4_, @Nullable Structure<?> p_i241961_5_, @Nullable RegistryKey<World> p_i241961_6_, @Nullable Boolean p_i241961_7_, LightPredicate p_i241961_8_, BlockPredicate p_i241961_9_, FluidPredicate p_i241961_10_) {
      this.x = p_i241961_1_;
      this.y = p_i241961_2_;
      this.z = p_i241961_3_;
      this.biome = p_i241961_4_;
      this.feature = p_i241961_5_;
      this.dimension = p_i241961_6_;
      this.field_235306_i_ = p_i241961_7_;
      this.field_226864_h_ = p_i241961_8_;
      this.field_226865_i_ = p_i241961_9_;
      this.field_226866_j_ = p_i241961_10_;
   }

   public static LocationPredicate func_242665_a(RegistryKey<Biome> p_242665_0_) {
      return new LocationPredicate(MinMaxBounds.FloatBound.UNBOUNDED, MinMaxBounds.FloatBound.UNBOUNDED, MinMaxBounds.FloatBound.UNBOUNDED, p_242665_0_, (Structure<?>)null, (RegistryKey<World>)null, (Boolean)null, LightPredicate.field_226854_a_, BlockPredicate.field_226231_a_, FluidPredicate.field_226643_a_);
   }

   public static LocationPredicate func_235308_a_(RegistryKey<World> p_235308_0_) {
      return new LocationPredicate(MinMaxBounds.FloatBound.UNBOUNDED, MinMaxBounds.FloatBound.UNBOUNDED, MinMaxBounds.FloatBound.UNBOUNDED, (RegistryKey<Biome>)null, (Structure<?>)null, p_235308_0_, (Boolean)null, LightPredicate.field_226854_a_, BlockPredicate.field_226231_a_, FluidPredicate.field_226643_a_);
   }

   public static LocationPredicate forFeature(Structure<?> p_218020_0_) {
      return new LocationPredicate(MinMaxBounds.FloatBound.UNBOUNDED, MinMaxBounds.FloatBound.UNBOUNDED, MinMaxBounds.FloatBound.UNBOUNDED, (RegistryKey<Biome>)null, p_218020_0_, (RegistryKey<World>)null, (Boolean)null, LightPredicate.field_226854_a_, BlockPredicate.field_226231_a_, FluidPredicate.field_226643_a_);
   }

   public boolean test(ServerWorld world, double x, double y, double z) {
      return this.test(world, (float)x, (float)y, (float)z);
   }

   public boolean test(ServerWorld world, float x, float y, float z) {
      if (!this.x.test(x)) {
         return false;
      } else if (!this.y.test(y)) {
         return false;
      } else if (!this.z.test(z)) {
         return false;
      } else if (this.dimension != null && this.dimension != world.func_234923_W_()) {
         return false;
      } else {
         BlockPos blockpos = new BlockPos((double)x, (double)y, (double)z);
         boolean flag = world.isBlockPresent(blockpos);
         Optional<RegistryKey<Biome>> optional = world.func_241828_r().func_243612_b(Registry.field_239720_u_).func_230519_c_(world.getBiome(blockpos));
         if (!optional.isPresent()) {
            return false;
         } else if (this.biome == null || flag && this.biome == optional.get()) {
            if (this.feature == null || flag && world.func_241112_a_().func_235010_a_(blockpos, true, this.feature).isValid()) {
               if (this.field_235306_i_ == null || flag && this.field_235306_i_ == CampfireBlock.func_235474_a_(world, blockpos)) {
                  if (!this.field_226864_h_.func_226858_a_(world, blockpos)) {
                     return false;
                  } else if (!this.field_226865_i_.func_226238_a_(world, blockpos)) {
                     return false;
                  } else {
                     return this.field_226866_j_.func_226649_a_(world, blockpos);
                  }
               } else {
                  return false;
               }
            } else {
               return false;
            }
         } else {
            return false;
         }
      }
   }

   public JsonElement serialize() {
      if (this == ANY) {
         return JsonNull.INSTANCE;
      } else {
         JsonObject jsonobject = new JsonObject();
         if (!this.x.isUnbounded() || !this.y.isUnbounded() || !this.z.isUnbounded()) {
            JsonObject jsonobject1 = new JsonObject();
            jsonobject1.add("x", this.x.serialize());
            jsonobject1.add("y", this.y.serialize());
            jsonobject1.add("z", this.z.serialize());
            jsonobject.add("position", jsonobject1);
         }

         if (this.dimension != null) {
            World.field_234917_f_.encodeStart(JsonOps.INSTANCE, this.dimension).resultOrPartial(field_235305_b_::error).ifPresent((p_235307_1_) -> {
               jsonobject.add("dimension", p_235307_1_);
            });
         }

         if (this.feature != null) {
            jsonobject.addProperty("feature", this.feature.getStructureName());
         }

         if (this.biome != null) {
            jsonobject.addProperty("biome", this.biome.func_240901_a_().toString());
         }

         if (this.field_235306_i_ != null) {
            jsonobject.addProperty("smokey", this.field_235306_i_);
         }

         jsonobject.add("light", this.field_226864_h_.func_226856_a_());
         jsonobject.add("block", this.field_226865_i_.func_226236_a_());
         jsonobject.add("fluid", this.field_226866_j_.func_226647_a_());
         return jsonobject;
      }
   }

   public static LocationPredicate deserialize(@Nullable JsonElement element) {
      if (element != null && !element.isJsonNull()) {
         JsonObject jsonobject = JSONUtils.getJsonObject(element, "location");
         JsonObject jsonobject1 = JSONUtils.getJsonObject(jsonobject, "position", new JsonObject());
         MinMaxBounds.FloatBound minmaxbounds$floatbound = MinMaxBounds.FloatBound.fromJson(jsonobject1.get("x"));
         MinMaxBounds.FloatBound minmaxbounds$floatbound1 = MinMaxBounds.FloatBound.fromJson(jsonobject1.get("y"));
         MinMaxBounds.FloatBound minmaxbounds$floatbound2 = MinMaxBounds.FloatBound.fromJson(jsonobject1.get("z"));
         RegistryKey<World> registrykey = jsonobject.has("dimension") ? ResourceLocation.field_240908_a_.parse(JsonOps.INSTANCE, jsonobject.get("dimension")).resultOrPartial(field_235305_b_::error).map((p_235310_0_) -> {
            return RegistryKey.func_240903_a_(Registry.field_239699_ae_, p_235310_0_);
         }).orElse((RegistryKey<World>)null) : null;
         Structure<?> structure = jsonobject.has("feature") ? Structure.field_236365_a_.get(JSONUtils.getString(jsonobject, "feature")) : null;
         RegistryKey<Biome> registrykey1 = null;
         if (jsonobject.has("biome")) {
            ResourceLocation resourcelocation = new ResourceLocation(JSONUtils.getString(jsonobject, "biome"));
            registrykey1 = RegistryKey.func_240903_a_(Registry.field_239720_u_, resourcelocation);
         }

         Boolean obool = jsonobject.has("smokey") ? jsonobject.get("smokey").getAsBoolean() : null;
         LightPredicate lightpredicate = LightPredicate.func_226857_a_(jsonobject.get("light"));
         BlockPredicate blockpredicate = BlockPredicate.func_226237_a_(jsonobject.get("block"));
         FluidPredicate fluidpredicate = FluidPredicate.func_226648_a_(jsonobject.get("fluid"));
         return new LocationPredicate(minmaxbounds$floatbound, minmaxbounds$floatbound1, minmaxbounds$floatbound2, registrykey1, structure, registrykey, obool, lightpredicate, blockpredicate, fluidpredicate);
      } else {
         return ANY;
      }
   }

   public static class Builder {
      private MinMaxBounds.FloatBound x = MinMaxBounds.FloatBound.UNBOUNDED;
      private MinMaxBounds.FloatBound y = MinMaxBounds.FloatBound.UNBOUNDED;
      private MinMaxBounds.FloatBound z = MinMaxBounds.FloatBound.UNBOUNDED;
      @Nullable
      private RegistryKey<Biome> biome;
      @Nullable
      private Structure<?> feature;
      @Nullable
      private RegistryKey<World> dimension;
      @Nullable
      private Boolean field_235311_g_;
      private LightPredicate field_226867_g_ = LightPredicate.field_226854_a_;
      private BlockPredicate field_226868_h_ = BlockPredicate.field_226231_a_;
      private FluidPredicate field_226869_i_ = FluidPredicate.field_226643_a_;

      public static LocationPredicate.Builder func_226870_a_() {
         return new LocationPredicate.Builder();
      }

      public LocationPredicate.Builder func_242666_a(@Nullable RegistryKey<Biome> p_242666_1_) {
         this.biome = p_242666_1_;
         return this;
      }

      public LocationPredicate.Builder func_235312_a_(BlockPredicate p_235312_1_) {
         this.field_226868_h_ = p_235312_1_;
         return this;
      }

      public LocationPredicate.Builder func_235313_a_(Boolean p_235313_1_) {
         this.field_235311_g_ = p_235313_1_;
         return this;
      }

      public LocationPredicate build() {
         return new LocationPredicate(this.x, this.y, this.z, this.biome, this.feature, this.dimension, this.field_235311_g_, this.field_226867_g_, this.field_226868_h_, this.field_226869_i_);
      }
   }
}