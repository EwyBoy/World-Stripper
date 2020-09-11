package net.minecraft.block;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
import java.util.Optional;
import java.util.Random;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.TransportationHelper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.world.Explosion;
import net.minecraft.world.ExplosionContext;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.ICollisionReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class RespawnAnchorBlock extends Block {
   public static final IntegerProperty field_235559_a_ = BlockStateProperties.field_235912_aC_;
   private static final ImmutableList<Vector3i> field_242676_b = ImmutableList.of(new Vector3i(0, 0, -1), new Vector3i(-1, 0, 0), new Vector3i(0, 0, 1), new Vector3i(1, 0, 0), new Vector3i(-1, 0, -1), new Vector3i(1, 0, -1), new Vector3i(-1, 0, 1), new Vector3i(1, 0, 1));
   private static final ImmutableList<Vector3i> field_242677_c = (new Builder<Vector3i>()).addAll(field_242676_b).addAll(field_242676_b.stream().map(Vector3i::down).iterator()).addAll(field_242676_b.stream().map(Vector3i::up).iterator()).add(new Vector3i(0, 1, 0)).build();

   public RespawnAnchorBlock(AbstractBlock.Properties p_i241185_1_) {
      super(p_i241185_1_);
      this.setDefaultState(this.stateContainer.getBaseState().with(field_235559_a_, Integer.valueOf(0)));
   }

   public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
      ItemStack itemstack = player.getHeldItem(handIn);
      if (handIn == Hand.MAIN_HAND && !func_235561_a_(itemstack) && func_235561_a_(player.getHeldItem(Hand.OFF_HAND))) {
         return ActionResultType.PASS;
      } else if (func_235561_a_(itemstack) && func_235568_h_(state)) {
         func_235564_a_(worldIn, pos, state);
         if (!player.abilities.isCreativeMode) {
            itemstack.shrink(1);
         }

         return ActionResultType.func_233537_a_(worldIn.isRemote);
      } else if (state.get(field_235559_a_) == 0) {
         return ActionResultType.PASS;
      } else if (!func_235562_a_(worldIn)) {
         if (!worldIn.isRemote) {
            this.func_235567_d_(state, worldIn, pos);
         }

         return ActionResultType.func_233537_a_(worldIn.isRemote);
      } else {
         if (!worldIn.isRemote) {
            ServerPlayerEntity serverplayerentity = (ServerPlayerEntity)player;
            if (serverplayerentity.func_241141_L_() != worldIn.func_234923_W_() || !serverplayerentity.func_241140_K_().equals(pos)) {
               serverplayerentity.func_242111_a(worldIn.func_234923_W_(), pos, 0.0F, false, true);
               worldIn.playSound((PlayerEntity)null, (double)pos.getX() + 0.5D, (double)pos.getY() + 0.5D, (double)pos.getZ() + 0.5D, SoundEvents.field_232819_mt_, SoundCategory.BLOCKS, 1.0F, 1.0F);
               return ActionResultType.SUCCESS;
            }
         }

         return ActionResultType.CONSUME;
      }
   }

   private static boolean func_235561_a_(ItemStack p_235561_0_) {
      return p_235561_0_.getItem() == Items.GLOWSTONE;
   }

   private static boolean func_235568_h_(BlockState p_235568_0_) {
      return p_235568_0_.get(field_235559_a_) < 4;
   }

   private static boolean func_235566_a_(BlockPos p_235566_0_, World p_235566_1_) {
      FluidState fluidstate = p_235566_1_.getFluidState(p_235566_0_);
      if (!fluidstate.isTagged(FluidTags.WATER)) {
         return false;
      } else if (fluidstate.isSource()) {
         return true;
      } else {
         float f = (float)fluidstate.getLevel();
         if (f < 2.0F) {
            return false;
         } else {
            FluidState fluidstate1 = p_235566_1_.getFluidState(p_235566_0_.down());
            return !fluidstate1.isTagged(FluidTags.WATER);
         }
      }
   }

   private void func_235567_d_(BlockState p_235567_1_, World p_235567_2_, final BlockPos p_235567_3_) {
      p_235567_2_.removeBlock(p_235567_3_, false);
      boolean flag = Direction.Plane.HORIZONTAL.func_239636_a_().map(p_235567_3_::offset).anyMatch((p_235563_1_) -> {
         return func_235566_a_(p_235563_1_, p_235567_2_);
      });
      final boolean flag1 = flag || p_235567_2_.getFluidState(p_235567_3_.up()).isTagged(FluidTags.WATER);
      ExplosionContext explosioncontext = new ExplosionContext() {
         public Optional<Float> func_230312_a_(Explosion p_230312_1_, IBlockReader p_230312_2_, BlockPos p_230312_3_, BlockState p_230312_4_, FluidState p_230312_5_) {
            return p_230312_3_.equals(p_235567_3_) && flag1 ? Optional.of(Blocks.WATER.getExplosionResistance()) : super.func_230312_a_(p_230312_1_, p_230312_2_, p_230312_3_, p_230312_4_, p_230312_5_);
         }
      };
      p_235567_2_.func_230546_a_((Entity)null, DamageSource.func_233546_a_(), explosioncontext, (double)p_235567_3_.getX() + 0.5D, (double)p_235567_3_.getY() + 0.5D, (double)p_235567_3_.getZ() + 0.5D, 5.0F, true, Explosion.Mode.DESTROY);
   }

   public static boolean func_235562_a_(World p_235562_0_) {
      return p_235562_0_.func_230315_m_().func_241511_k_();
   }

   public static void func_235564_a_(World p_235564_0_, BlockPos p_235564_1_, BlockState p_235564_2_) {
      p_235564_0_.setBlockState(p_235564_1_, p_235564_2_.with(field_235559_a_, Integer.valueOf(p_235564_2_.get(field_235559_a_) + 1)), 3);
      p_235564_0_.playSound((PlayerEntity)null, (double)p_235564_1_.getX() + 0.5D, (double)p_235564_1_.getY() + 0.5D, (double)p_235564_1_.getZ() + 0.5D, SoundEvents.field_232817_mr_, SoundCategory.BLOCKS, 1.0F, 1.0F);
   }

   /**
    * Called periodically clientside on blocks near the player to show effects (like furnace fire particles). Note that
    * this method is unrelated to {@link randomTick} and {@link #needsRandomTick}, and will always be called regardless
    * of whether the block can receive random update ticks
    */
   @OnlyIn(Dist.CLIENT)
   public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) {
      if (stateIn.get(field_235559_a_) != 0) {
         if (rand.nextInt(100) == 0) {
            worldIn.playSound((PlayerEntity)null, (double)pos.getX() + 0.5D, (double)pos.getY() + 0.5D, (double)pos.getZ() + 0.5D, SoundEvents.field_232816_mq_, SoundCategory.BLOCKS, 1.0F, 1.0F);
         }

         double d0 = (double)pos.getX() + 0.5D + (0.5D - rand.nextDouble());
         double d1 = (double)pos.getY() + 1.0D;
         double d2 = (double)pos.getZ() + 0.5D + (0.5D - rand.nextDouble());
         double d3 = (double)rand.nextFloat() * 0.04D;
         worldIn.addParticle(ParticleTypes.field_239819_as_, d0, d1, d2, 0.0D, d3, 0.0D);
      }
   }

   protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
      builder.add(field_235559_a_);
   }

   /**
    * @deprecated call via {@link IBlockState#hasComparatorInputOverride()} whenever possible. Implementing/overriding
    * is fine.
    */
   public boolean hasComparatorInputOverride(BlockState state) {
      return true;
   }

   public static int func_235565_a_(BlockState p_235565_0_, int p_235565_1_) {
      return MathHelper.floor((float)(p_235565_0_.get(field_235559_a_) - 0) / 4.0F * (float)p_235565_1_);
   }

   /**
    * @deprecated call via {@link IBlockState#getComparatorInputOverride(World,BlockPos)} whenever possible.
    * Implementing/overriding is fine.
    */
   public int getComparatorInputOverride(BlockState blockState, World worldIn, BlockPos pos) {
      return func_235565_a_(blockState, 15);
   }

   public static Optional<Vector3d> func_235560_a_(EntityType<?> p_235560_0_, ICollisionReader p_235560_1_, BlockPos p_235560_2_) {
      Optional<Vector3d> optional = func_242678_a(p_235560_0_, p_235560_1_, p_235560_2_, true);
      return optional.isPresent() ? optional : func_242678_a(p_235560_0_, p_235560_1_, p_235560_2_, false);
   }

   private static Optional<Vector3d> func_242678_a(EntityType<?> p_242678_0_, ICollisionReader p_242678_1_, BlockPos p_242678_2_, boolean p_242678_3_) {
      BlockPos.Mutable blockpos$mutable = new BlockPos.Mutable();

      for(Vector3i vector3i : field_242677_c) {
         blockpos$mutable.setPos(p_242678_2_).func_243531_h(vector3i);
         Vector3d vector3d = TransportationHelper.func_242379_a(p_242678_0_, p_242678_1_, blockpos$mutable, p_242678_3_);
         if (vector3d != null) {
            return Optional.of(vector3d);
         }
      }

      return Optional.empty();
   }

   public boolean allowsMovement(BlockState state, IBlockReader worldIn, BlockPos pos, PathType type) {
      return false;
   }
}