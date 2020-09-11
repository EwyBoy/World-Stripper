package net.minecraft.world.biome;

import javax.annotation.Nullable;
import net.minecraft.util.IObjectIntIterable;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BiomeContainer implements BiomeManager.IBiomeReader {
   private static final Logger LOGGER = LogManager.getLogger();
   private static final int WIDTH_BITS = (int)Math.round(Math.log(16.0D) / Math.log(2.0D)) - 2;
   private static final int HEIGHT_BITS = (int)Math.round(Math.log(256.0D) / Math.log(2.0D)) - 2;
   public static final int BIOMES_SIZE = 1 << WIDTH_BITS + WIDTH_BITS + HEIGHT_BITS;
   public static final int HORIZONTAL_MASK = (1 << WIDTH_BITS) - 1;
   public static final int VERTICAL_MASK = (1 << HEIGHT_BITS) - 1;
   private final IObjectIntIterable<Biome> field_242704_g;
   private final Biome[] biomes;

   public BiomeContainer(IObjectIntIterable<Biome> p_i241971_1_, Biome[] p_i241971_2_) {
      this.field_242704_g = p_i241971_1_;
      this.biomes = p_i241971_2_;
   }

   private BiomeContainer(IObjectIntIterable<Biome> p_i241967_1_) {
      this(p_i241967_1_, new Biome[BIOMES_SIZE]);
   }

   @OnlyIn(Dist.CLIENT)
   public BiomeContainer(IObjectIntIterable<Biome> p_i241970_1_, int[] p_i241970_2_) {
      this(p_i241970_1_);

      for(int i = 0; i < this.biomes.length; ++i) {
         int j = p_i241970_2_[i];
         Biome biome = p_i241970_1_.getByValue(j);
         if (biome == null) {
            LOGGER.warn("Received invalid biome id: " + j);
            this.biomes[i] = p_i241970_1_.getByValue(0);
         } else {
            this.biomes[i] = biome;
         }
      }

   }

   public BiomeContainer(IObjectIntIterable<Biome> p_i241968_1_, ChunkPos p_i241968_2_, BiomeProvider p_i241968_3_) {
      this(p_i241968_1_);
      int i = p_i241968_2_.getXStart() >> 2;
      int j = p_i241968_2_.getZStart() >> 2;

      for(int k = 0; k < this.biomes.length; ++k) {
         int l = k & HORIZONTAL_MASK;
         int i1 = k >> WIDTH_BITS + WIDTH_BITS & VERTICAL_MASK;
         int j1 = k >> WIDTH_BITS & HORIZONTAL_MASK;
         this.biomes[k] = p_i241968_3_.getNoiseBiome(i + l, i1, j + j1);
      }

   }

   public BiomeContainer(IObjectIntIterable<Biome> p_i241969_1_, ChunkPos p_i241969_2_, BiomeProvider p_i241969_3_, @Nullable int[] p_i241969_4_) {
      this(p_i241969_1_);
      int i = p_i241969_2_.getXStart() >> 2;
      int j = p_i241969_2_.getZStart() >> 2;
      if (p_i241969_4_ != null) {
         for(int k = 0; k < p_i241969_4_.length; ++k) {
            this.biomes[k] = p_i241969_1_.getByValue(p_i241969_4_[k]);
            if (this.biomes[k] == null) {
               int l = k & HORIZONTAL_MASK;
               int i1 = k >> WIDTH_BITS + WIDTH_BITS & VERTICAL_MASK;
               int j1 = k >> WIDTH_BITS & HORIZONTAL_MASK;
               this.biomes[k] = p_i241969_3_.getNoiseBiome(i + l, i1, j + j1);
            }
         }
      } else {
         for(int k1 = 0; k1 < this.biomes.length; ++k1) {
            int l1 = k1 & HORIZONTAL_MASK;
            int i2 = k1 >> WIDTH_BITS + WIDTH_BITS & VERTICAL_MASK;
            int j2 = k1 >> WIDTH_BITS & HORIZONTAL_MASK;
            this.biomes[k1] = p_i241969_3_.getNoiseBiome(i + l1, i2, j + j2);
         }
      }

   }

   public int[] getBiomeIds() {
      int[] aint = new int[this.biomes.length];

      for(int i = 0; i < this.biomes.length; ++i) {
         aint[i] = this.field_242704_g.getId(this.biomes[i]);
      }

      return aint;
   }

   public Biome getNoiseBiome(int x, int y, int z) {
      int i = x & HORIZONTAL_MASK;
      int j = MathHelper.clamp(y, 0, VERTICAL_MASK);
      int k = z & HORIZONTAL_MASK;
      return this.biomes[j << WIDTH_BITS + WIDTH_BITS | k << WIDTH_BITS | i];
   }
}