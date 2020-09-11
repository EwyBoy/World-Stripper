package net.minecraft.world.gen.blockstateprovider;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.biome.Biome;

public class ForestFlowerBlockStateProvider extends BlockStateProvider {
   public static final Codec<ForestFlowerBlockStateProvider> field_236801_b_;
   private static final BlockState[] field_227401_b_ = new BlockState[]{Blocks.DANDELION.getDefaultState(), Blocks.POPPY.getDefaultState(), Blocks.ALLIUM.getDefaultState(), Blocks.AZURE_BLUET.getDefaultState(), Blocks.RED_TULIP.getDefaultState(), Blocks.ORANGE_TULIP.getDefaultState(), Blocks.WHITE_TULIP.getDefaultState(), Blocks.PINK_TULIP.getDefaultState(), Blocks.OXEYE_DAISY.getDefaultState(), Blocks.CORNFLOWER.getDefaultState(), Blocks.LILY_OF_THE_VALLEY.getDefaultState()};
   public static final ForestFlowerBlockStateProvider field_236802_c_ = new ForestFlowerBlockStateProvider();

   protected BlockStateProviderType<?> func_230377_a_() {
      return BlockStateProviderType.FOREST_FLOWER_PROVIDER;
   }

   public BlockState getBlockState(Random randomIn, BlockPos blockPosIn) {
      double d0 = MathHelper.clamp((1.0D + Biome.INFO_NOISE.noiseAt((double)blockPosIn.getX() / 48.0D, (double)blockPosIn.getZ() / 48.0D, false)) / 2.0D, 0.0D, 0.9999D);
      return field_227401_b_[(int)(d0 * (double)field_227401_b_.length)];
   }

   static {
      field_236801_b_ = Codec.unit(() -> {
         return field_236802_c_;
      });
   }
}