package net.minecraft.world.gen.carver;

import com.mojang.serialization.Codec;
import java.util.BitSet;
import java.util.List;
import java.util.Random;
import java.util.function.Function;
import java.util.function.Supplier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKeyCodec;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunk;

public class ConfiguredCarver<WC extends ICarverConfig> {
   public static final Codec<ConfiguredCarver<?>> field_236235_a_ = Registry.CARVER.dispatch((p_236236_0_) -> {
      return p_236236_0_.carver;
   }, WorldCarver::func_236244_c_);
   public static final Codec<Supplier<ConfiguredCarver<?>>> field_244390_b_ = RegistryKeyCodec.func_241794_a_(Registry.field_243551_at, field_236235_a_);
   public static final Codec<List<Supplier<ConfiguredCarver<?>>>> field_242759_c = RegistryKeyCodec.func_244328_b(Registry.field_243551_at, field_236235_a_);
   private final WorldCarver<WC> carver;
   private final WC config;

   public ConfiguredCarver(WorldCarver<WC> p_i49928_1_, WC p_i49928_2_) {
      this.carver = p_i49928_1_;
      this.config = p_i49928_2_;
   }

   public WC func_242760_a() {
      return this.config;
   }

   public boolean shouldCarve(Random p_222730_1_, int p_222730_2_, int p_222730_3_) {
      return this.carver.shouldCarve(p_222730_1_, p_222730_2_, p_222730_3_, this.config);
   }

   public boolean func_227207_a_(IChunk p_227207_1_, Function<BlockPos, Biome> p_227207_2_, Random p_227207_3_, int p_227207_4_, int p_227207_5_, int p_227207_6_, int p_227207_7_, int p_227207_8_, BitSet p_227207_9_) {
      return this.carver.func_225555_a_(p_227207_1_, p_227207_2_, p_227207_3_, p_227207_4_, p_227207_5_, p_227207_6_, p_227207_7_, p_227207_8_, p_227207_9_, this.config);
   }
}