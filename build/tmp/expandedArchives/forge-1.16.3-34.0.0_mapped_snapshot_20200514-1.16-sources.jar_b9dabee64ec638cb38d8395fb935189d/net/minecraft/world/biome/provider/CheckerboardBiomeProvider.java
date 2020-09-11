package net.minecraft.world.biome.provider;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import java.util.function.Supplier;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class CheckerboardBiomeProvider extends BiomeProvider {
   public static final Codec<CheckerboardBiomeProvider> field_235255_e_ = RecordCodecBuilder.create((p_235258_0_) -> {
      return p_235258_0_.group(Biome.field_242420_e.fieldOf("biomes").forGetter((p_235259_0_) -> {
         return p_235259_0_.field_205320_b;
      }), Codec.intRange(0, 62).fieldOf("scale").orElse(2).forGetter((p_235257_0_) -> {
         return p_235257_0_.field_235256_h_;
      })).apply(p_235258_0_, CheckerboardBiomeProvider::new);
   });
   private final List<Supplier<Biome>> field_205320_b;
   private final int field_205321_c;
   private final int field_235256_h_;

   public CheckerboardBiomeProvider(List<Supplier<Biome>> p_i231637_1_, int p_i231637_2_) {
      super(p_i231637_1_.stream());
      this.field_205320_b = p_i231637_1_;
      this.field_205321_c = p_i231637_2_ + 2;
      this.field_235256_h_ = p_i231637_2_;
   }

   protected Codec<? extends BiomeProvider> func_230319_a_() {
      return field_235255_e_;
   }

   @OnlyIn(Dist.CLIENT)
   public BiomeProvider func_230320_a_(long p_230320_1_) {
      return this;
   }

   public Biome getNoiseBiome(int x, int y, int z) {
      return this.field_205320_b.get(Math.floorMod((x >> this.field_205321_c) + (z >> this.field_205321_c), this.field_205320_b.size())).get();
   }
}