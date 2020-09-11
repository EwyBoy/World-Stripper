package net.minecraft.world.gen.blockplacer;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.block.BlockState;
import net.minecraft.block.DoublePlantBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;

public class DoublePlantBlockPlacer extends BlockPlacer {
   public static final Codec<DoublePlantBlockPlacer> field_236443_b_;
   public static final DoublePlantBlockPlacer field_236444_c_ = new DoublePlantBlockPlacer();

   protected BlockPlacerType<?> func_230368_a_() {
      return BlockPlacerType.DOUBLE_PLANT;
   }

   public void func_225567_a_(IWorld p_225567_1_, BlockPos p_225567_2_, BlockState p_225567_3_, Random p_225567_4_) {
      ((DoublePlantBlock)p_225567_3_.getBlock()).placeAt(p_225567_1_, p_225567_2_, 2);
   }

   static {
      field_236443_b_ = Codec.unit(() -> {
         return field_236444_c_;
      });
   }
}