package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.block.Blocks;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.gen.ChunkGenerator;

public class BasaltPillarFeature extends Feature<NoFeatureConfig> {
   public BasaltPillarFeature(Codec<NoFeatureConfig> p_i231926_1_) {
      super(p_i231926_1_);
   }

   public boolean func_241855_a(ISeedReader p_241855_1_, ChunkGenerator p_241855_2_, Random p_241855_3_, BlockPos p_241855_4_, NoFeatureConfig p_241855_5_) {
      if (p_241855_1_.isAirBlock(p_241855_4_) && !p_241855_1_.isAirBlock(p_241855_4_.up())) {
         BlockPos.Mutable blockpos$mutable = p_241855_4_.func_239590_i_();
         BlockPos.Mutable blockpos$mutable1 = p_241855_4_.func_239590_i_();
         boolean flag = true;
         boolean flag1 = true;
         boolean flag2 = true;
         boolean flag3 = true;

         while(p_241855_1_.isAirBlock(blockpos$mutable)) {
            if (World.isOutsideBuildHeight(blockpos$mutable)) {
               return true;
            }

            p_241855_1_.setBlockState(blockpos$mutable, Blocks.field_235337_cO_.getDefaultState(), 2);
            flag = flag && this.func_236253_b_(p_241855_1_, p_241855_3_, blockpos$mutable1.func_239622_a_(blockpos$mutable, Direction.NORTH));
            flag1 = flag1 && this.func_236253_b_(p_241855_1_, p_241855_3_, blockpos$mutable1.func_239622_a_(blockpos$mutable, Direction.SOUTH));
            flag2 = flag2 && this.func_236253_b_(p_241855_1_, p_241855_3_, blockpos$mutable1.func_239622_a_(blockpos$mutable, Direction.WEST));
            flag3 = flag3 && this.func_236253_b_(p_241855_1_, p_241855_3_, blockpos$mutable1.func_239622_a_(blockpos$mutable, Direction.EAST));
            blockpos$mutable.move(Direction.DOWN);
         }

         blockpos$mutable.move(Direction.UP);
         this.func_236252_a_(p_241855_1_, p_241855_3_, blockpos$mutable1.func_239622_a_(blockpos$mutable, Direction.NORTH));
         this.func_236252_a_(p_241855_1_, p_241855_3_, blockpos$mutable1.func_239622_a_(blockpos$mutable, Direction.SOUTH));
         this.func_236252_a_(p_241855_1_, p_241855_3_, blockpos$mutable1.func_239622_a_(blockpos$mutable, Direction.WEST));
         this.func_236252_a_(p_241855_1_, p_241855_3_, blockpos$mutable1.func_239622_a_(blockpos$mutable, Direction.EAST));
         blockpos$mutable.move(Direction.DOWN);
         BlockPos.Mutable blockpos$mutable2 = new BlockPos.Mutable();

         for(int i = -3; i < 4; ++i) {
            for(int j = -3; j < 4; ++j) {
               int k = MathHelper.abs(i) * MathHelper.abs(j);
               if (p_241855_3_.nextInt(10) < 10 - k) {
                  blockpos$mutable2.setPos(blockpos$mutable.add(i, 0, j));
                  int l = 3;

                  while(p_241855_1_.isAirBlock(blockpos$mutable1.func_239622_a_(blockpos$mutable2, Direction.DOWN))) {
                     blockpos$mutable2.move(Direction.DOWN);
                     --l;
                     if (l <= 0) {
                        break;
                     }
                  }

                  if (!p_241855_1_.isAirBlock(blockpos$mutable1.func_239622_a_(blockpos$mutable2, Direction.DOWN))) {
                     p_241855_1_.setBlockState(blockpos$mutable2, Blocks.field_235337_cO_.getDefaultState(), 2);
                  }
               }
            }
         }

         return true;
      } else {
         return false;
      }
   }

   private void func_236252_a_(IWorld p_236252_1_, Random p_236252_2_, BlockPos p_236252_3_) {
      if (p_236252_2_.nextBoolean()) {
         p_236252_1_.setBlockState(p_236252_3_, Blocks.field_235337_cO_.getDefaultState(), 2);
      }

   }

   private boolean func_236253_b_(IWorld p_236253_1_, Random p_236253_2_, BlockPos p_236253_3_) {
      if (p_236253_2_.nextInt(10) != 0) {
         p_236253_1_.setBlockState(p_236253_3_, Blocks.field_235337_cO_.getDefaultState(), 2);
         return true;
      } else {
         return false;
      }
   }
}