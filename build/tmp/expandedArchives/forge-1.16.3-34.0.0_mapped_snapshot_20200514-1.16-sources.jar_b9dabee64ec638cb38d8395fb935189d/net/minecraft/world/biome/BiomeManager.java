package net.minecraft.world.biome;

import com.google.common.hash.Hashing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class BiomeManager {
   private final BiomeManager.IBiomeReader reader;
   private final long seed;
   private final IBiomeMagnifier magnifier;

   public BiomeManager(BiomeManager.IBiomeReader readerIn, long seedIn, IBiomeMagnifier magnifierIn) {
      this.reader = readerIn;
      this.seed = seedIn;
      this.magnifier = magnifierIn;
   }

   public static long func_235200_a_(long p_235200_0_) {
      return Hashing.sha256().hashLong(p_235200_0_).asLong();
   }

   public BiomeManager copyWithProvider(BiomeProvider newProvider) {
      return new BiomeManager(newProvider, this.seed, this.magnifier);
   }

   public Biome getBiome(BlockPos posIn) {
      return this.magnifier.getBiome(this.seed, posIn.getX(), posIn.getY(), posIn.getZ(), this.reader);
   }

   @OnlyIn(Dist.CLIENT)
   public Biome func_235198_a_(double p_235198_1_, double p_235198_3_, double p_235198_5_) {
      int i = MathHelper.floor(p_235198_1_) >> 2;
      int j = MathHelper.floor(p_235198_3_) >> 2;
      int k = MathHelper.floor(p_235198_5_) >> 2;
      return this.func_235199_a_(i, j, k);
   }

   @OnlyIn(Dist.CLIENT)
   public Biome func_235201_b_(BlockPos p_235201_1_) {
      int i = p_235201_1_.getX() >> 2;
      int j = p_235201_1_.getY() >> 2;
      int k = p_235201_1_.getZ() >> 2;
      return this.func_235199_a_(i, j, k);
   }

   @OnlyIn(Dist.CLIENT)
   public Biome func_235199_a_(int p_235199_1_, int p_235199_2_, int p_235199_3_) {
      return this.reader.getNoiseBiome(p_235199_1_, p_235199_2_, p_235199_3_);
   }

   public interface IBiomeReader {
      Biome getNoiseBiome(int x, int y, int z);
   }
}