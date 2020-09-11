package net.minecraft.world.biome.provider;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mojang.serialization.Codec;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeManager;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public abstract class BiomeProvider implements BiomeManager.IBiomeReader {
   public static final Codec<BiomeProvider> field_235202_a_ = Registry.field_239689_aA_.dispatchStable(BiomeProvider::func_230319_a_, Function.identity());
   protected final Map<Structure<?>, Boolean> hasStructureCache = Maps.newHashMap();
   protected final Set<BlockState> topBlocksCache = Sets.newHashSet();
   protected final List<Biome> biomes;

   protected BiomeProvider(Stream<Supplier<Biome>> p_i241937_1_) {
      this(p_i241937_1_.map(Supplier::get).collect(ImmutableList.toImmutableList()));
   }

   protected BiomeProvider(List<Biome> p_i231634_1_) {
      this.biomes = p_i231634_1_;
   }

   protected abstract Codec<? extends BiomeProvider> func_230319_a_();

   @OnlyIn(Dist.CLIENT)
   public abstract BiomeProvider func_230320_a_(long p_230320_1_);

   public List<Biome> func_235203_c_() {
      return this.biomes;
   }

   /**
    * Returns the set of biomes contained in cube of side length 2 * radius + 1 centered at (xIn, yIn, zIn)
    */
   public Set<Biome> getBiomes(int xIn, int yIn, int zIn, int radius) {
      int i = xIn - radius >> 2;
      int j = yIn - radius >> 2;
      int k = zIn - radius >> 2;
      int l = xIn + radius >> 2;
      int i1 = yIn + radius >> 2;
      int j1 = zIn + radius >> 2;
      int k1 = l - i + 1;
      int l1 = i1 - j + 1;
      int i2 = j1 - k + 1;
      Set<Biome> set = Sets.newHashSet();

      for(int j2 = 0; j2 < i2; ++j2) {
         for(int k2 = 0; k2 < k1; ++k2) {
            for(int l2 = 0; l2 < l1; ++l2) {
               int i3 = i + k2;
               int j3 = j + l2;
               int k3 = k + j2;
               set.add(this.getNoiseBiome(i3, j3, k3));
            }
         }
      }

      return set;
   }

   @Nullable
   public BlockPos func_225531_a_(int xIn, int yIn, int zIn, int radiusIn, Predicate<Biome> biomesIn, Random randIn) {
      return this.func_230321_a_(xIn, yIn, zIn, radiusIn, 1, biomesIn, randIn, false);
   }

   @Nullable
   public BlockPos func_230321_a_(int p_230321_1_, int p_230321_2_, int p_230321_3_, int p_230321_4_, int p_230321_5_, Predicate<Biome> p_230321_6_, Random p_230321_7_, boolean p_230321_8_) {
      int i = p_230321_1_ >> 2;
      int j = p_230321_3_ >> 2;
      int k = p_230321_4_ >> 2;
      int l = p_230321_2_ >> 2;
      BlockPos blockpos = null;
      int i1 = 0;
      int j1 = p_230321_8_ ? 0 : k;

      for(int k1 = j1; k1 <= k; k1 += p_230321_5_) {
         for(int l1 = -k1; l1 <= k1; l1 += p_230321_5_) {
            boolean flag = Math.abs(l1) == k1;

            for(int i2 = -k1; i2 <= k1; i2 += p_230321_5_) {
               if (p_230321_8_) {
                  boolean flag1 = Math.abs(i2) == k1;
                  if (!flag1 && !flag) {
                     continue;
                  }
               }

               int k2 = i + i2;
               int j2 = j + l1;
               if (p_230321_6_.test(this.getNoiseBiome(k2, l, j2))) {
                  if (blockpos == null || p_230321_7_.nextInt(i1 + 1) == 0) {
                     blockpos = new BlockPos(k2 << 2, p_230321_2_, j2 << 2);
                     if (p_230321_8_) {
                        return blockpos;
                     }
                  }

                  ++i1;
               }
            }
         }
      }

      return blockpos;
   }

   public boolean hasStructure(Structure<?> structureIn) {
      return this.hasStructureCache.computeIfAbsent(structureIn, (p_226839_1_) -> {
         return this.biomes.stream().anyMatch((p_226838_1_) -> {
            return p_226838_1_.func_242440_e().func_242493_a(p_226839_1_);
         });
      });
   }

   public Set<BlockState> getSurfaceBlocks() {
      if (this.topBlocksCache.isEmpty()) {
         for(Biome biome : this.biomes) {
            this.topBlocksCache.add(biome.func_242440_e().func_242502_e().getTop());
         }
      }

      return this.topBlocksCache;
   }

   static {
      Registry.register(Registry.field_239689_aA_, "fixed", SingleBiomeProvider.field_235260_e_);
      Registry.register(Registry.field_239689_aA_, "multi_noise", NetherBiomeProvider.field_235263_f_);
      Registry.register(Registry.field_239689_aA_, "checkerboard", CheckerboardBiomeProvider.field_235255_e_);
      Registry.register(Registry.field_239689_aA_, "vanilla_layered", OverworldBiomeProvider.field_235297_e_);
      Registry.register(Registry.field_239689_aA_, "the_end", EndBiomeProvider.field_235314_e_);
   }
}