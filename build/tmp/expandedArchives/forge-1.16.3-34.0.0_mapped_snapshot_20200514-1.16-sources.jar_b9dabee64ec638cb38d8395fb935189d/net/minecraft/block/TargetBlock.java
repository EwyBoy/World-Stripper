package net.minecraft.block;

import java.util.Random;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.stats.Stats;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class TargetBlock extends Block {
   private static final IntegerProperty field_235603_a_ = BlockStateProperties.POWER_0_15;

   public TargetBlock(AbstractBlock.Properties p_i241188_1_) {
      super(p_i241188_1_);
      this.setDefaultState(this.stateContainer.getBaseState().with(field_235603_a_, Integer.valueOf(0)));
   }

   public void onProjectileCollision(World worldIn, BlockState state, BlockRayTraceResult hit, ProjectileEntity projectile) {
      int i = func_235605_a_(worldIn, state, hit, projectile);
      Entity entity = projectile.func_234616_v_();
      if (entity instanceof ServerPlayerEntity) {
         ServerPlayerEntity serverplayerentity = (ServerPlayerEntity)entity;
         serverplayerentity.addStat(Stats.field_232863_aD_);
         CriteriaTriggers.field_232606_L_.func_236350_a_(serverplayerentity, projectile, hit.getHitVec(), i);
      }

   }

   private static int func_235605_a_(IWorld p_235605_0_, BlockState p_235605_1_, BlockRayTraceResult p_235605_2_, Entity p_235605_3_) {
      int i = func_235606_a_(p_235605_2_, p_235605_2_.getHitVec());
      int j = p_235605_3_ instanceof AbstractArrowEntity ? 20 : 8;
      if (!p_235605_0_.getPendingBlockTicks().isTickScheduled(p_235605_2_.getPos(), p_235605_1_.getBlock())) {
         func_235604_a_(p_235605_0_, p_235605_1_, i, p_235605_2_.getPos(), j);
      }

      return i;
   }

   private static int func_235606_a_(BlockRayTraceResult p_235606_0_, Vector3d p_235606_1_) {
      Direction direction = p_235606_0_.getFace();
      double d0 = Math.abs(MathHelper.frac(p_235606_1_.x) - 0.5D);
      double d1 = Math.abs(MathHelper.frac(p_235606_1_.y) - 0.5D);
      double d2 = Math.abs(MathHelper.frac(p_235606_1_.z) - 0.5D);
      Direction.Axis direction$axis = direction.getAxis();
      double d3;
      if (direction$axis == Direction.Axis.Y) {
         d3 = Math.max(d0, d2);
      } else if (direction$axis == Direction.Axis.Z) {
         d3 = Math.max(d0, d1);
      } else {
         d3 = Math.max(d1, d2);
      }

      return Math.max(1, MathHelper.ceil(15.0D * MathHelper.clamp((0.5D - d3) / 0.5D, 0.0D, 1.0D)));
   }

   private static void func_235604_a_(IWorld p_235604_0_, BlockState p_235604_1_, int p_235604_2_, BlockPos p_235604_3_, int p_235604_4_) {
      p_235604_0_.setBlockState(p_235604_3_, p_235604_1_.with(field_235603_a_, Integer.valueOf(p_235604_2_)), 3);
      p_235604_0_.getPendingBlockTicks().scheduleTick(p_235604_3_, p_235604_1_.getBlock(), p_235604_4_);
   }

   public void tick(BlockState state, ServerWorld worldIn, BlockPos pos, Random rand) {
      if (state.get(field_235603_a_) != 0) {
         worldIn.setBlockState(pos, state.with(field_235603_a_, Integer.valueOf(0)), 3);
      }

   }

   /**
    * @deprecated call via {@link IBlockState#getWeakPower(IBlockAccess,BlockPos,EnumFacing)} whenever possible.
    * Implementing/overriding is fine.
    */
   public int getWeakPower(BlockState blockState, IBlockReader blockAccess, BlockPos pos, Direction side) {
      return blockState.get(field_235603_a_);
   }

   /**
    * Can this block provide power. Only wire currently seems to have this change based on its state.
    * @deprecated call via {@link IBlockState#canProvidePower()} whenever possible. Implementing/overriding is fine.
    */
   public boolean canProvidePower(BlockState state) {
      return true;
   }

   protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
      builder.add(field_235603_a_);
   }

   public void onBlockAdded(BlockState state, World worldIn, BlockPos pos, BlockState oldState, boolean isMoving) {
      if (!worldIn.isRemote() && !state.isIn(oldState.getBlock())) {
         if (state.get(field_235603_a_) > 0 && !worldIn.getPendingBlockTicks().isTickScheduled(pos, this)) {
            worldIn.setBlockState(pos, state.with(field_235603_a_, Integer.valueOf(0)), 18);
         }

      }
   }
}