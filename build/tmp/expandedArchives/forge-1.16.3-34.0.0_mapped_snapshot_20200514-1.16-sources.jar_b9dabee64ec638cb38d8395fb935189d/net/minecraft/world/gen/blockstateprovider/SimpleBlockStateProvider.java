package net.minecraft.world.gen.blockstateprovider;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;

public class SimpleBlockStateProvider extends BlockStateProvider {
   public static final Codec<SimpleBlockStateProvider> field_236809_b_ = BlockState.field_235877_b_.fieldOf("state").xmap(SimpleBlockStateProvider::new, (p_236810_0_) -> {
      return p_236810_0_.state;
   }).codec();
   private final BlockState state;

   public SimpleBlockStateProvider(BlockState p_i225860_1_) {
      this.state = p_i225860_1_;
   }

   protected BlockStateProviderType<?> func_230377_a_() {
      return BlockStateProviderType.SIMPLE_STATE_PROVIDER;
   }

   public BlockState getBlockState(Random randomIn, BlockPos blockPosIn) {
      return this.state;
   }
}