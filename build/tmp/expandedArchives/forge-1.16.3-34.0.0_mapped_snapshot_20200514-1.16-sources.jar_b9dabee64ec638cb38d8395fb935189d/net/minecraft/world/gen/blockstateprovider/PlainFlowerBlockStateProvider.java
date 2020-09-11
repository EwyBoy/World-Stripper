package net.minecraft.world.gen.blockstateprovider;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;

public class PlainFlowerBlockStateProvider extends BlockStateProvider {
   public static final Codec<PlainFlowerBlockStateProvider> field_236804_b_;
   public static final PlainFlowerBlockStateProvider field_236805_c_ = new PlainFlowerBlockStateProvider();
   private static final BlockState[] field_227402_b_ = new BlockState[]{Blocks.ORANGE_TULIP.getDefaultState(), Blocks.RED_TULIP.getDefaultState(), Blocks.PINK_TULIP.getDefaultState(), Blocks.WHITE_TULIP.getDefaultState()};
   private static final BlockState[] field_227403_c_ = new BlockState[]{Blocks.POPPY.getDefaultState(), Blocks.AZURE_BLUET.getDefaultState(), Blocks.OXEYE_DAISY.getDefaultState(), Blocks.CORNFLOWER.getDefaultState()};

   protected BlockStateProviderType<?> func_230377_a_() {
      return BlockStateProviderType.PLAIN_FLOWER_PROVIDER;
   }

   public BlockState getBlockState(Random randomIn, BlockPos blockPosIn) {
      double d0 = Biome.INFO_NOISE.noiseAt((double)blockPosIn.getX() / 200.0D, (double)blockPosIn.getZ() / 200.0D, false);
      if (d0 < -0.8D) {
         return Util.func_240989_a_(field_227402_b_, randomIn);
      } else {
         return randomIn.nextInt(3) > 0 ? Util.func_240989_a_(field_227403_c_, randomIn) : Blocks.DANDELION.getDefaultState();
      }
   }

   static {
      field_236804_b_ = Codec.unit(() -> {
         return field_236805_c_;
      });
   }
}