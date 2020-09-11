package net.minecraft.world.gen.blockplacer;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;

public class SimpleBlockPlacer extends BlockPlacer {
   public static final Codec<SimpleBlockPlacer> field_236446_b_;
   public static final SimpleBlockPlacer field_236447_c_ = new SimpleBlockPlacer();

   protected BlockPlacerType<?> func_230368_a_() {
      return BlockPlacerType.SIMPLE_BLOCK;
   }

   public void func_225567_a_(IWorld p_225567_1_, BlockPos p_225567_2_, BlockState p_225567_3_, Random p_225567_4_) {
      p_225567_1_.setBlockState(p_225567_2_, p_225567_3_, 2);
   }

   static {
      field_236446_b_ = Codec.unit(() -> {
         return field_236447_c_;
      });
   }
}