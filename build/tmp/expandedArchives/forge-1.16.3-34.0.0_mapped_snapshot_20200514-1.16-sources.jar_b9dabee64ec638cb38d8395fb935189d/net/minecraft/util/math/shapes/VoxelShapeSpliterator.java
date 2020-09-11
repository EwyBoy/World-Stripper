package net.minecraft.util.math.shapes;

import java.util.Objects;
import java.util.Spliterators.AbstractSpliterator;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.CubeCoordinateIterator;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.ICollisionReader;
import net.minecraft.world.border.WorldBorder;

public class VoxelShapeSpliterator extends AbstractSpliterator<VoxelShape> {
   @Nullable
   private final Entity field_234868_a_;
   private final AxisAlignedBB field_234869_b_;
   private final ISelectionContext field_234870_c_;
   private final CubeCoordinateIterator field_234871_d_;
   private final BlockPos.Mutable field_234872_e_;
   private final VoxelShape field_234873_f_;
   private final ICollisionReader field_234874_g_;
   private boolean field_234875_h_;
   private final BiPredicate<BlockState, BlockPos> field_241458_i_;

   public VoxelShapeSpliterator(ICollisionReader p_i231606_1_, @Nullable Entity p_i231606_2_, AxisAlignedBB p_i231606_3_) {
      this(p_i231606_1_, p_i231606_2_, p_i231606_3_, (p_241459_0_, p_241459_1_) -> {
         return true;
      });
   }

   public VoxelShapeSpliterator(ICollisionReader p_i241238_1_, @Nullable Entity p_i241238_2_, AxisAlignedBB p_i241238_3_, BiPredicate<BlockState, BlockPos> p_i241238_4_) {
      super(Long.MAX_VALUE, 1280);
      this.field_234870_c_ = p_i241238_2_ == null ? ISelectionContext.dummy() : ISelectionContext.forEntity(p_i241238_2_);
      this.field_234872_e_ = new BlockPos.Mutable();
      this.field_234873_f_ = VoxelShapes.create(p_i241238_3_);
      this.field_234874_g_ = p_i241238_1_;
      this.field_234875_h_ = p_i241238_2_ != null;
      this.field_234868_a_ = p_i241238_2_;
      this.field_234869_b_ = p_i241238_3_;
      this.field_241458_i_ = p_i241238_4_;
      int i = MathHelper.floor(p_i241238_3_.minX - 1.0E-7D) - 1;
      int j = MathHelper.floor(p_i241238_3_.maxX + 1.0E-7D) + 1;
      int k = MathHelper.floor(p_i241238_3_.minY - 1.0E-7D) - 1;
      int l = MathHelper.floor(p_i241238_3_.maxY + 1.0E-7D) + 1;
      int i1 = MathHelper.floor(p_i241238_3_.minZ - 1.0E-7D) - 1;
      int j1 = MathHelper.floor(p_i241238_3_.maxZ + 1.0E-7D) + 1;
      this.field_234871_d_ = new CubeCoordinateIterator(i, k, i1, j, l, j1);
   }

   public boolean tryAdvance(Consumer<? super VoxelShape> p_tryAdvance_1_) {
      return this.field_234875_h_ && this.func_234879_b_(p_tryAdvance_1_) || this.func_234878_a_(p_tryAdvance_1_);
   }

   boolean func_234878_a_(Consumer<? super VoxelShape> p_234878_1_) {
      while(true) {
         if (this.field_234871_d_.hasNext()) {
            int i = this.field_234871_d_.getX();
            int j = this.field_234871_d_.getY();
            int k = this.field_234871_d_.getZ();
            int l = this.field_234871_d_.numBoundariesTouched();
            if (l == 3) {
               continue;
            }

            IBlockReader iblockreader = this.func_234876_a_(i, k);
            if (iblockreader == null) {
               continue;
            }

            this.field_234872_e_.setPos(i, j, k);
            BlockState blockstate = iblockreader.getBlockState(this.field_234872_e_);
            if (!this.field_241458_i_.test(blockstate, this.field_234872_e_) || l == 1 && !blockstate.isCollisionShapeLargerThanFullBlock() || l == 2 && !blockstate.isIn(Blocks.MOVING_PISTON)) {
               continue;
            }

            VoxelShape voxelshape = blockstate.getCollisionShape(this.field_234874_g_, this.field_234872_e_, this.field_234870_c_);
            if (voxelshape == VoxelShapes.fullCube()) {
               if (!this.field_234869_b_.intersects((double)i, (double)j, (double)k, (double)i + 1.0D, (double)j + 1.0D, (double)k + 1.0D)) {
                  continue;
               }

               p_234878_1_.accept(voxelshape.withOffset((double)i, (double)j, (double)k));
               return true;
            }

            VoxelShape voxelshape1 = voxelshape.withOffset((double)i, (double)j, (double)k);
            if (!VoxelShapes.compare(voxelshape1, this.field_234873_f_, IBooleanFunction.AND)) {
               continue;
            }

            p_234878_1_.accept(voxelshape1);
            return true;
         }

         return false;
      }
   }

   @Nullable
   private IBlockReader func_234876_a_(int p_234876_1_, int p_234876_2_) {
      int i = p_234876_1_ >> 4;
      int j = p_234876_2_ >> 4;
      return this.field_234874_g_.getBlockReader(i, j);
   }

   boolean func_234879_b_(Consumer<? super VoxelShape> p_234879_1_) {
      Objects.requireNonNull(this.field_234868_a_);
      this.field_234875_h_ = false;
      WorldBorder worldborder = this.field_234874_g_.getWorldBorder();
      AxisAlignedBB axisalignedbb = this.field_234868_a_.getBoundingBox();
      if (!func_234877_a_(worldborder, axisalignedbb)) {
         VoxelShape voxelshape = worldborder.getShape();
         if (!func_241461_b_(voxelshape, axisalignedbb) && func_241460_a_(voxelshape, axisalignedbb)) {
            p_234879_1_.accept(voxelshape);
            return true;
         }
      }

      return false;
   }

   private static boolean func_241460_a_(VoxelShape p_241460_0_, AxisAlignedBB p_241460_1_) {
      return VoxelShapes.compare(p_241460_0_, VoxelShapes.create(p_241460_1_.grow(1.0E-7D)), IBooleanFunction.AND);
   }

   private static boolean func_241461_b_(VoxelShape p_241461_0_, AxisAlignedBB p_241461_1_) {
      return VoxelShapes.compare(p_241461_0_, VoxelShapes.create(p_241461_1_.shrink(1.0E-7D)), IBooleanFunction.AND);
   }

   public static boolean func_234877_a_(WorldBorder p_234877_0_, AxisAlignedBB p_234877_1_) {
      double d0 = (double)MathHelper.floor(p_234877_0_.minX());
      double d1 = (double)MathHelper.floor(p_234877_0_.minZ());
      double d2 = (double)MathHelper.ceil(p_234877_0_.maxX());
      double d3 = (double)MathHelper.ceil(p_234877_0_.maxZ());
      return p_234877_1_.minX > d0 && p_234877_1_.minX < d2 && p_234877_1_.minZ > d1 && p_234877_1_.minZ < d3 && p_234877_1_.maxX > d0 && p_234877_1_.maxX < d2 && p_234877_1_.maxZ > d1 && p_234877_1_.maxZ < d3;
   }
}