package net.minecraft.world.gen.blockstateprovider;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import java.util.Random;
import net.minecraft.block.BlockState;
import net.minecraft.util.WeightedList;
import net.minecraft.util.math.BlockPos;

public class WeightedBlockStateProvider extends BlockStateProvider {
   public static final Codec<WeightedBlockStateProvider> field_236811_b_ = WeightedList.func_234002_a_(BlockState.field_235877_b_).comapFlatMap(WeightedBlockStateProvider::func_236812_a_, (p_236813_0_) -> {
      return p_236813_0_.weightedStates;
   }).fieldOf("entries").codec();
   private final WeightedList<BlockState> weightedStates;

   private static DataResult<WeightedBlockStateProvider> func_236812_a_(WeightedList<BlockState> p_236812_0_) {
      return p_236812_0_.func_234005_b_() ? DataResult.error("WeightedStateProvider with no states") : DataResult.success(new WeightedBlockStateProvider(p_236812_0_));
   }

   private WeightedBlockStateProvider(WeightedList<BlockState> p_i225862_1_) {
      this.weightedStates = p_i225862_1_;
   }

   protected BlockStateProviderType<?> func_230377_a_() {
      return BlockStateProviderType.WEIGHTED_STATE_PROVIDER;
   }

   public WeightedBlockStateProvider() {
      this(new WeightedList<>());
   }

   /**
    * Adds the blockstate with the specified weight to the weighted states of the provider.
    */
   public WeightedBlockStateProvider addWeightedBlockstate(BlockState blockStateIn, int weightIn) {
      this.weightedStates.func_226313_a_(blockStateIn, weightIn);
      return this;
   }

   public BlockState getBlockState(Random randomIn, BlockPos blockPosIn) {
      return this.weightedStates.func_226318_b_(randomIn);
   }
}