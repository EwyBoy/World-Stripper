package net.minecraft.world.gen.blockstateprovider;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;

public class AxisRotatingBlockStateProvider extends BlockStateProvider {
   public static final Codec<AxisRotatingBlockStateProvider> field_236807_b_ = BlockState.field_235877_b_.fieldOf("state").xmap(AbstractBlock.AbstractBlockState::getBlock, Block::getDefaultState).xmap(AxisRotatingBlockStateProvider::new, (p_236808_0_) -> {
      return p_236808_0_.field_227404_b_;
   }).codec();
   private final Block field_227404_b_;

   public AxisRotatingBlockStateProvider(Block p_i225858_1_) {
      this.field_227404_b_ = p_i225858_1_;
   }

   protected BlockStateProviderType<?> func_230377_a_() {
      return BlockStateProviderType.field_236797_e_;
   }

   public BlockState getBlockState(Random randomIn, BlockPos blockPosIn) {
      Direction.Axis direction$axis = Direction.Axis.func_239634_a_(randomIn);
      return this.field_227404_b_.getDefaultState().with(RotatedPillarBlock.AXIS, direction$axis);
   }
}