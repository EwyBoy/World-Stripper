package net.minecraft.world.biome.provider;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Sets;
import com.mojang.serialization.Codec;
import java.util.Random;
import java.util.Set;
import java.util.function.Predicate;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class SingleBiomeProvider extends BiomeProvider {
   public static final Codec<SingleBiomeProvider> field_235260_e_ = Biome.field_235051_b_.fieldOf("biome").xmap(SingleBiomeProvider::new, (p_235261_0_) -> {
      return p_235261_0_.biome;
   }).stable().codec();
   private final Supplier<Biome> biome;

   public SingleBiomeProvider(Biome p_i46709_1_) {
      this(() -> {
         return p_i46709_1_;
      });
   }

   public SingleBiomeProvider(Supplier<Biome> p_i241945_1_) {
      super(ImmutableList.of(p_i241945_1_.get()));
      this.biome = p_i241945_1_;
   }

   protected Codec<? extends BiomeProvider> func_230319_a_() {
      return field_235260_e_;
   }

   @OnlyIn(Dist.CLIENT)
   public BiomeProvider func_230320_a_(long p_230320_1_) {
      return this;
   }

   public Biome getNoiseBiome(int x, int y, int z) {
      return this.biome.get();
   }

   @Nullable
   public BlockPos func_230321_a_(int p_230321_1_, int p_230321_2_, int p_230321_3_, int p_230321_4_, int p_230321_5_, Predicate<Biome> p_230321_6_, Random p_230321_7_, boolean p_230321_8_) {
      if (p_230321_6_.test(this.biome.get())) {
         return p_230321_8_ ? new BlockPos(p_230321_1_, p_230321_2_, p_230321_3_) : new BlockPos(p_230321_1_ - p_230321_4_ + p_230321_7_.nextInt(p_230321_4_ * 2 + 1), p_230321_2_, p_230321_3_ - p_230321_4_ + p_230321_7_.nextInt(p_230321_4_ * 2 + 1));
      } else {
         return null;
      }
   }

   /**
    * Returns the set of biomes contained in cube of side length 2 * radius + 1 centered at (xIn, yIn, zIn)
    */
   public Set<Biome> getBiomes(int xIn, int yIn, int zIn, int radius) {
      return Sets.newHashSet(this.biome.get());
   }
}