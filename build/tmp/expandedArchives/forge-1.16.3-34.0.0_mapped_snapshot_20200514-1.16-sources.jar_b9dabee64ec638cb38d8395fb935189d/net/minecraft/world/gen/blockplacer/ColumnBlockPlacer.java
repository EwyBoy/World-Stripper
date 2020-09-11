package net.minecraft.world.gen.blockplacer;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Random;
import net.minecraft.block.BlockState;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;

public class ColumnBlockPlacer extends BlockPlacer {
   public static final Codec<ColumnBlockPlacer> field_236439_b_ = RecordCodecBuilder.create((p_236441_0_) -> {
      return p_236441_0_.group(Codec.INT.fieldOf("min_size").forGetter((p_236442_0_) -> {
         return p_236442_0_.field_227265_b_;
      }), Codec.INT.fieldOf("extra_size").forGetter((p_236440_0_) -> {
         return p_236440_0_.field_227266_c_;
      })).apply(p_236441_0_, ColumnBlockPlacer::new);
   });
   private final int field_227265_b_;
   private final int field_227266_c_;

   public ColumnBlockPlacer(int p_i225826_1_, int p_i225826_2_) {
      this.field_227265_b_ = p_i225826_1_;
      this.field_227266_c_ = p_i225826_2_;
   }

   protected BlockPlacerType<?> func_230368_a_() {
      return BlockPlacerType.COLUMN;
   }

   public void func_225567_a_(IWorld p_225567_1_, BlockPos p_225567_2_, BlockState p_225567_3_, Random p_225567_4_) {
      BlockPos.Mutable blockpos$mutable = p_225567_2_.func_239590_i_();
      int i = this.field_227265_b_ + p_225567_4_.nextInt(p_225567_4_.nextInt(this.field_227266_c_ + 1) + 1);

      for(int j = 0; j < i; ++j) {
         p_225567_1_.setBlockState(blockpos$mutable, p_225567_3_, 2);
         blockpos$mutable.move(Direction.UP);
      }

   }
}