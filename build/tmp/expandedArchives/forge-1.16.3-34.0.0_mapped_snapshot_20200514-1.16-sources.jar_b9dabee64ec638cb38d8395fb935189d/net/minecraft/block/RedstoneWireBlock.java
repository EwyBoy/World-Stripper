package net.minecraft.block;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.particles.RedstoneParticleData;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.RedstoneSide;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class RedstoneWireBlock extends Block {
   public static final EnumProperty<RedstoneSide> NORTH = BlockStateProperties.REDSTONE_NORTH;
   public static final EnumProperty<RedstoneSide> EAST = BlockStateProperties.REDSTONE_EAST;
   public static final EnumProperty<RedstoneSide> SOUTH = BlockStateProperties.REDSTONE_SOUTH;
   public static final EnumProperty<RedstoneSide> WEST = BlockStateProperties.REDSTONE_WEST;
   public static final IntegerProperty POWER = BlockStateProperties.POWER_0_15;
   public static final Map<Direction, EnumProperty<RedstoneSide>> FACING_PROPERTY_MAP = Maps.newEnumMap(ImmutableMap.of(Direction.NORTH, NORTH, Direction.EAST, EAST, Direction.SOUTH, SOUTH, Direction.WEST, WEST));
   private static final VoxelShape field_235538_g_ = Block.makeCuboidShape(3.0D, 0.0D, 3.0D, 13.0D, 1.0D, 13.0D);
   private static final Map<Direction, VoxelShape> field_235539_h_ = Maps.newEnumMap(ImmutableMap.of(Direction.NORTH, Block.makeCuboidShape(3.0D, 0.0D, 0.0D, 13.0D, 1.0D, 13.0D), Direction.SOUTH, Block.makeCuboidShape(3.0D, 0.0D, 3.0D, 13.0D, 1.0D, 16.0D), Direction.EAST, Block.makeCuboidShape(3.0D, 0.0D, 3.0D, 16.0D, 1.0D, 13.0D), Direction.WEST, Block.makeCuboidShape(0.0D, 0.0D, 3.0D, 13.0D, 1.0D, 13.0D)));
   private static final Map<Direction, VoxelShape> field_235540_i_ = Maps.newEnumMap(ImmutableMap.of(Direction.NORTH, VoxelShapes.or(field_235539_h_.get(Direction.NORTH), Block.makeCuboidShape(3.0D, 0.0D, 0.0D, 13.0D, 16.0D, 1.0D)), Direction.SOUTH, VoxelShapes.or(field_235539_h_.get(Direction.SOUTH), Block.makeCuboidShape(3.0D, 0.0D, 15.0D, 13.0D, 16.0D, 16.0D)), Direction.EAST, VoxelShapes.or(field_235539_h_.get(Direction.EAST), Block.makeCuboidShape(15.0D, 0.0D, 3.0D, 16.0D, 16.0D, 13.0D)), Direction.WEST, VoxelShapes.or(field_235539_h_.get(Direction.WEST), Block.makeCuboidShape(0.0D, 0.0D, 3.0D, 1.0D, 16.0D, 13.0D))));
   private final Map<BlockState, VoxelShape> field_235541_j_ = Maps.newHashMap();
   private static final Vector3f[] field_235542_k_ = new Vector3f[16];
   private final BlockState field_235543_o_;
   private boolean canProvidePower = true;

   public RedstoneWireBlock(AbstractBlock.Properties properties) {
      super(properties);
      this.setDefaultState(this.stateContainer.getBaseState().with(NORTH, RedstoneSide.NONE).with(EAST, RedstoneSide.NONE).with(SOUTH, RedstoneSide.NONE).with(WEST, RedstoneSide.NONE).with(POWER, Integer.valueOf(0)));
      this.field_235543_o_ = this.getDefaultState().with(NORTH, RedstoneSide.SIDE).with(EAST, RedstoneSide.SIDE).with(SOUTH, RedstoneSide.SIDE).with(WEST, RedstoneSide.SIDE);

      for(BlockState blockstate : this.getStateContainer().getValidStates()) {
         if (blockstate.get(POWER) == 0) {
            this.field_235541_j_.put(blockstate, this.func_235554_l_(blockstate));
         }
      }

   }

   private VoxelShape func_235554_l_(BlockState p_235554_1_) {
      VoxelShape voxelshape = field_235538_g_;

      for(Direction direction : Direction.Plane.HORIZONTAL) {
         RedstoneSide redstoneside = p_235554_1_.get(FACING_PROPERTY_MAP.get(direction));
         if (redstoneside == RedstoneSide.SIDE) {
            voxelshape = VoxelShapes.or(voxelshape, field_235539_h_.get(direction));
         } else if (redstoneside == RedstoneSide.UP) {
            voxelshape = VoxelShapes.or(voxelshape, field_235540_i_.get(direction));
         }
      }

      return voxelshape;
   }

   public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
      return this.field_235541_j_.get(state.with(POWER, Integer.valueOf(0)));
   }

   public BlockState getStateForPlacement(BlockItemUseContext context) {
      return this.func_235544_a_(context.getWorld(), this.field_235543_o_, context.getPos());
   }

   private BlockState func_235544_a_(IBlockReader p_235544_1_, BlockState p_235544_2_, BlockPos p_235544_3_) {
      boolean flag = func_235556_n_(p_235544_2_);
      p_235544_2_ = this.func_235551_b_(p_235544_1_, this.getDefaultState().with(POWER, p_235544_2_.get(POWER)), p_235544_3_);
      if (flag && func_235556_n_(p_235544_2_)) {
         return p_235544_2_;
      } else {
         boolean flag1 = p_235544_2_.get(NORTH).func_235921_b_();
         boolean flag2 = p_235544_2_.get(SOUTH).func_235921_b_();
         boolean flag3 = p_235544_2_.get(EAST).func_235921_b_();
         boolean flag4 = p_235544_2_.get(WEST).func_235921_b_();
         boolean flag5 = !flag1 && !flag2;
         boolean flag6 = !flag3 && !flag4;
         if (!flag4 && flag5) {
            p_235544_2_ = p_235544_2_.with(WEST, RedstoneSide.SIDE);
         }

         if (!flag3 && flag5) {
            p_235544_2_ = p_235544_2_.with(EAST, RedstoneSide.SIDE);
         }

         if (!flag1 && flag6) {
            p_235544_2_ = p_235544_2_.with(NORTH, RedstoneSide.SIDE);
         }

         if (!flag2 && flag6) {
            p_235544_2_ = p_235544_2_.with(SOUTH, RedstoneSide.SIDE);
         }

         return p_235544_2_;
      }
   }

   private BlockState func_235551_b_(IBlockReader p_235551_1_, BlockState p_235551_2_, BlockPos p_235551_3_) {
      boolean flag = !p_235551_1_.getBlockState(p_235551_3_.up()).isNormalCube(p_235551_1_, p_235551_3_);

      for(Direction direction : Direction.Plane.HORIZONTAL) {
         if (!p_235551_2_.get(FACING_PROPERTY_MAP.get(direction)).func_235921_b_()) {
            RedstoneSide redstoneside = this.func_235545_a_(p_235551_1_, p_235551_3_, direction, flag);
            p_235551_2_ = p_235551_2_.with(FACING_PROPERTY_MAP.get(direction), redstoneside);
         }
      }

      return p_235551_2_;
   }

   /**
    * Update the provided state given the provided neighbor facing and neighbor state, returning a new state.
    * For example, fences make their connections to the passed in state if possible, and wet concrete powder immediately
    * returns its solidified counterpart.
    * Note that this method should ideally consider only the specific face passed in.
    */
   public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
      if (facing == Direction.DOWN) {
         return stateIn;
      } else if (facing == Direction.UP) {
         return this.func_235544_a_(worldIn, stateIn, currentPos);
      } else {
         RedstoneSide redstoneside = this.getSide(worldIn, currentPos, facing);
         return redstoneside.func_235921_b_() == stateIn.get(FACING_PROPERTY_MAP.get(facing)).func_235921_b_() && !func_235555_m_(stateIn) ? stateIn.with(FACING_PROPERTY_MAP.get(facing), redstoneside) : this.func_235544_a_(worldIn, this.field_235543_o_.with(POWER, stateIn.get(POWER)).with(FACING_PROPERTY_MAP.get(facing), redstoneside), currentPos);
      }
   }

   private static boolean func_235555_m_(BlockState p_235555_0_) {
      return p_235555_0_.get(NORTH).func_235921_b_() && p_235555_0_.get(SOUTH).func_235921_b_() && p_235555_0_.get(EAST).func_235921_b_() && p_235555_0_.get(WEST).func_235921_b_();
   }

   private static boolean func_235556_n_(BlockState p_235556_0_) {
      return !p_235556_0_.get(NORTH).func_235921_b_() && !p_235556_0_.get(SOUTH).func_235921_b_() && !p_235556_0_.get(EAST).func_235921_b_() && !p_235556_0_.get(WEST).func_235921_b_();
   }

   /**
    * performs updates on diagonal neighbors of the target position and passes in the flags. The flags can be referenced
    * from the docs for {@link IWorldWriter#setBlockState(IBlockState, BlockPos, int)}.
    */
   public void updateDiagonalNeighbors(BlockState state, IWorld worldIn, BlockPos pos, int flags, int p_196248_5_) {
      BlockPos.Mutable blockpos$mutable = new BlockPos.Mutable();

      for(Direction direction : Direction.Plane.HORIZONTAL) {
         RedstoneSide redstoneside = state.get(FACING_PROPERTY_MAP.get(direction));
         if (redstoneside != RedstoneSide.NONE && !worldIn.getBlockState(blockpos$mutable.func_239622_a_(pos, direction)).isIn(this)) {
            blockpos$mutable.move(Direction.DOWN);
            BlockState blockstate = worldIn.getBlockState(blockpos$mutable);
            if (!blockstate.isIn(Blocks.OBSERVER)) {
               BlockPos blockpos = blockpos$mutable.offset(direction.getOpposite());
               BlockState blockstate1 = blockstate.updatePostPlacement(direction.getOpposite(), worldIn.getBlockState(blockpos), worldIn, blockpos$mutable, blockpos);
               func_241468_a_(blockstate, blockstate1, worldIn, blockpos$mutable, flags, p_196248_5_);
            }

            blockpos$mutable.func_239622_a_(pos, direction).move(Direction.UP);
            BlockState blockstate3 = worldIn.getBlockState(blockpos$mutable);
            if (!blockstate3.isIn(Blocks.OBSERVER)) {
               BlockPos blockpos1 = blockpos$mutable.offset(direction.getOpposite());
               BlockState blockstate2 = blockstate3.updatePostPlacement(direction.getOpposite(), worldIn.getBlockState(blockpos1), worldIn, blockpos$mutable, blockpos1);
               func_241468_a_(blockstate3, blockstate2, worldIn, blockpos$mutable, flags, p_196248_5_);
            }
         }
      }

   }

   private RedstoneSide getSide(IBlockReader worldIn, BlockPos pos, Direction face) {
      return this.func_235545_a_(worldIn, pos, face, !worldIn.getBlockState(pos.up()).isNormalCube(worldIn, pos));
   }

   private RedstoneSide func_235545_a_(IBlockReader p_235545_1_, BlockPos p_235545_2_, Direction p_235545_3_, boolean p_235545_4_) {
      BlockPos blockpos = p_235545_2_.offset(p_235545_3_);
      BlockState blockstate = p_235545_1_.getBlockState(blockpos);
      if (p_235545_4_) {
         boolean flag = this.func_235552_b_(p_235545_1_, blockpos, blockstate);
         if (flag && canConnectTo(p_235545_1_.getBlockState(blockpos.up()), p_235545_1_, blockpos.up(), null) ) {
            if (blockstate.isSolidSide(p_235545_1_, blockpos, p_235545_3_.getOpposite())) {
               return RedstoneSide.UP;
            }

            return RedstoneSide.SIDE;
         }
      }

      return !canConnectTo(blockstate, p_235545_1_, blockpos, p_235545_3_) && (blockstate.isNormalCube(p_235545_1_, blockpos) || !canConnectTo(p_235545_1_.getBlockState(blockpos.down()), p_235545_1_, blockpos.down(), null)) ? RedstoneSide.NONE : RedstoneSide.SIDE;
   }

   public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
      BlockPos blockpos = pos.down();
      BlockState blockstate = worldIn.getBlockState(blockpos);
      return this.func_235552_b_(worldIn, blockpos, blockstate);
   }

   private boolean func_235552_b_(IBlockReader p_235552_1_, BlockPos p_235552_2_, BlockState p_235552_3_) {
      return p_235552_3_.isSolidSide(p_235552_1_, p_235552_2_, Direction.UP) || p_235552_3_.isIn(Blocks.HOPPER);
   }

   private void func_235547_a_(World p_235547_1_, BlockPos p_235547_2_, BlockState p_235547_3_) {
      int i = this.func_235546_a_(p_235547_1_, p_235547_2_);
      if (p_235547_3_.get(POWER) != i) {
         if (p_235547_1_.getBlockState(p_235547_2_) == p_235547_3_) {
            p_235547_1_.setBlockState(p_235547_2_, p_235547_3_.with(POWER, Integer.valueOf(i)), 2);
         }

         Set<BlockPos> set = Sets.newHashSet();
         set.add(p_235547_2_);

         for(Direction direction : Direction.values()) {
            set.add(p_235547_2_.offset(direction));
         }

         for(BlockPos blockpos : set) {
            p_235547_1_.notifyNeighborsOfStateChange(blockpos, this);
         }
      }

   }

   private int func_235546_a_(World p_235546_1_, BlockPos p_235546_2_) {
      this.canProvidePower = false;
      int i = p_235546_1_.getRedstonePowerFromNeighbors(p_235546_2_);
      this.canProvidePower = true;
      int j = 0;
      if (i < 15) {
         for(Direction direction : Direction.Plane.HORIZONTAL) {
            BlockPos blockpos = p_235546_2_.offset(direction);
            BlockState blockstate = p_235546_1_.getBlockState(blockpos);
            j = Math.max(j, this.func_235557_o_(blockstate));
            BlockPos blockpos1 = p_235546_2_.up();
            if (blockstate.isNormalCube(p_235546_1_, blockpos) && !p_235546_1_.getBlockState(blockpos1).isNormalCube(p_235546_1_, blockpos1)) {
               j = Math.max(j, this.func_235557_o_(p_235546_1_.getBlockState(blockpos.up())));
            } else if (!blockstate.isNormalCube(p_235546_1_, blockpos)) {
               j = Math.max(j, this.func_235557_o_(p_235546_1_.getBlockState(blockpos.down())));
            }
         }
      }

      return Math.max(i, j - 1);
   }

   private int func_235557_o_(BlockState p_235557_1_) {
      return p_235557_1_.isIn(this) ? p_235557_1_.get(POWER) : 0;
   }

   /**
    * Calls World.notifyNeighborsOfStateChange() for all neighboring blocks, but only if the given block is a redstone
    * wire.
    */
   private void notifyWireNeighborsOfStateChange(World worldIn, BlockPos pos) {
      if (worldIn.getBlockState(pos).isIn(this)) {
         worldIn.notifyNeighborsOfStateChange(pos, this);

         for(Direction direction : Direction.values()) {
            worldIn.notifyNeighborsOfStateChange(pos.offset(direction), this);
         }

      }
   }

   public void onBlockAdded(BlockState state, World worldIn, BlockPos pos, BlockState oldState, boolean isMoving) {
      if (!oldState.isIn(state.getBlock()) && !worldIn.isRemote) {
         this.func_235547_a_(worldIn, pos, state);

         for(Direction direction : Direction.Plane.VERTICAL) {
            worldIn.notifyNeighborsOfStateChange(pos.offset(direction), this);
         }

         this.func_235553_d_(worldIn, pos);
      }
   }

   public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
      if (!isMoving && !state.isIn(newState.getBlock())) {
         super.onReplaced(state, worldIn, pos, newState, isMoving);
         if (!worldIn.isRemote) {
            for(Direction direction : Direction.values()) {
               worldIn.notifyNeighborsOfStateChange(pos.offset(direction), this);
            }

            this.func_235547_a_(worldIn, pos, state);
            this.func_235553_d_(worldIn, pos);
         }
      }
   }

   private void func_235553_d_(World p_235553_1_, BlockPos p_235553_2_) {
      for(Direction direction : Direction.Plane.HORIZONTAL) {
         this.notifyWireNeighborsOfStateChange(p_235553_1_, p_235553_2_.offset(direction));
      }

      for(Direction direction1 : Direction.Plane.HORIZONTAL) {
         BlockPos blockpos = p_235553_2_.offset(direction1);
         if (p_235553_1_.getBlockState(blockpos).isNormalCube(p_235553_1_, blockpos)) {
            this.notifyWireNeighborsOfStateChange(p_235553_1_, blockpos.up());
         } else {
            this.notifyWireNeighborsOfStateChange(p_235553_1_, blockpos.down());
         }
      }

   }

   public void neighborChanged(BlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
      if (!worldIn.isRemote) {
         if (state.isValidPosition(worldIn, pos)) {
            this.func_235547_a_(worldIn, pos, state);
         } else {
            spawnDrops(state, worldIn, pos);
            worldIn.removeBlock(pos, false);
         }

      }
   }

   /**
    * @deprecated call via {@link IBlockState#getStrongPower(IBlockAccess,BlockPos,EnumFacing)} whenever possible.
    * Implementing/overriding is fine.
    */
   public int getStrongPower(BlockState blockState, IBlockReader blockAccess, BlockPos pos, Direction side) {
      return !this.canProvidePower ? 0 : blockState.getWeakPower(blockAccess, pos, side);
   }

   /**
    * @deprecated call via {@link IBlockState#getWeakPower(IBlockAccess,BlockPos,EnumFacing)} whenever possible.
    * Implementing/overriding is fine.
    */
   public int getWeakPower(BlockState blockState, IBlockReader blockAccess, BlockPos pos, Direction side) {
      if (this.canProvidePower && side != Direction.DOWN) {
         int i = blockState.get(POWER);
         if (i == 0) {
            return 0;
         } else {
            return side != Direction.UP && !this.func_235544_a_(blockAccess, blockState, pos).get(FACING_PROPERTY_MAP.get(side.getOpposite())).func_235921_b_() ? 0 : i;
         }
      } else {
         return 0;
      }
   }

   protected static boolean canConnectTo(BlockState blockState, IBlockReader world, BlockPos pos, @Nullable Direction side) {
      if (blockState.isIn(Blocks.REDSTONE_WIRE)) {
         return true;
      } else if (blockState.isIn(Blocks.REPEATER)) {
         Direction direction = blockState.get(RepeaterBlock.HORIZONTAL_FACING);
         return direction == side || direction.getOpposite() == side;
      } else if (blockState.isIn(Blocks.OBSERVER)) {
         return side == blockState.get(ObserverBlock.FACING);
      } else {
         return blockState.canConnectRedstone(world, pos, side) && side != null;
      }
   }

   /**
    * Can this block provide power. Only wire currently seems to have this change based on its state.
    * @deprecated call via {@link IBlockState#canProvidePower()} whenever possible. Implementing/overriding is fine.
    */
   public boolean canProvidePower(BlockState state) {
      return this.canProvidePower;
   }

   @OnlyIn(Dist.CLIENT)
   public static int func_235550_b_(int p_235550_0_) {
      Vector3f vector3f = field_235542_k_[p_235550_0_];
      return MathHelper.rgb(vector3f.getX(), vector3f.getY(), vector3f.getZ());
   }

   @OnlyIn(Dist.CLIENT)
   private void func_235549_a_(World p_235549_1_, Random p_235549_2_, BlockPos p_235549_3_, Vector3f p_235549_4_, Direction p_235549_5_, Direction p_235549_6_, float p_235549_7_, float p_235549_8_) {
      float f = p_235549_8_ - p_235549_7_;
      if (!(p_235549_2_.nextFloat() >= 0.2F * f)) {
         float f1 = 0.4375F;
         float f2 = p_235549_7_ + f * p_235549_2_.nextFloat();
         double d0 = 0.5D + (double)(0.4375F * (float)p_235549_5_.getXOffset()) + (double)(f2 * (float)p_235549_6_.getXOffset());
         double d1 = 0.5D + (double)(0.4375F * (float)p_235549_5_.getYOffset()) + (double)(f2 * (float)p_235549_6_.getYOffset());
         double d2 = 0.5D + (double)(0.4375F * (float)p_235549_5_.getZOffset()) + (double)(f2 * (float)p_235549_6_.getZOffset());
         p_235549_1_.addParticle(new RedstoneParticleData(p_235549_4_.getX(), p_235549_4_.getY(), p_235549_4_.getZ(), 1.0F), (double)p_235549_3_.getX() + d0, (double)p_235549_3_.getY() + d1, (double)p_235549_3_.getZ() + d2, 0.0D, 0.0D, 0.0D);
      }
   }

   /**
    * Called periodically clientside on blocks near the player to show effects (like furnace fire particles). Note that
    * this method is unrelated to {@link randomTick} and {@link #needsRandomTick}, and will always be called regardless
    * of whether the block can receive random update ticks
    */
   @OnlyIn(Dist.CLIENT)
   public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) {
      int i = stateIn.get(POWER);
      if (i != 0) {
         for(Direction direction : Direction.Plane.HORIZONTAL) {
            RedstoneSide redstoneside = stateIn.get(FACING_PROPERTY_MAP.get(direction));
            switch(redstoneside) {
            case UP:
               this.func_235549_a_(worldIn, rand, pos, field_235542_k_[i], direction, Direction.UP, -0.5F, 0.5F);
            case SIDE:
               this.func_235549_a_(worldIn, rand, pos, field_235542_k_[i], Direction.DOWN, direction, 0.0F, 0.5F);
               break;
            case NONE:
            default:
               this.func_235549_a_(worldIn, rand, pos, field_235542_k_[i], Direction.DOWN, direction, 0.0F, 0.3F);
            }
         }

      }
   }

   /**
    * Returns the blockstate with the given rotation from the passed blockstate. If inapplicable, returns the passed
    * blockstate.
    * @deprecated call via {@link IBlockState#withRotation(Rotation)} whenever possible. Implementing/overriding is
    * fine.
    */
   public BlockState rotate(BlockState state, Rotation rot) {
      switch(rot) {
      case CLOCKWISE_180:
         return state.with(NORTH, state.get(SOUTH)).with(EAST, state.get(WEST)).with(SOUTH, state.get(NORTH)).with(WEST, state.get(EAST));
      case COUNTERCLOCKWISE_90:
         return state.with(NORTH, state.get(EAST)).with(EAST, state.get(SOUTH)).with(SOUTH, state.get(WEST)).with(WEST, state.get(NORTH));
      case CLOCKWISE_90:
         return state.with(NORTH, state.get(WEST)).with(EAST, state.get(NORTH)).with(SOUTH, state.get(EAST)).with(WEST, state.get(SOUTH));
      default:
         return state;
      }
   }

   /**
    * Returns the blockstate with the given mirror of the passed blockstate. If inapplicable, returns the passed
    * blockstate.
    * @deprecated call via {@link IBlockState#withMirror(Mirror)} whenever possible. Implementing/overriding is fine.
    */
   public BlockState mirror(BlockState state, Mirror mirrorIn) {
      switch(mirrorIn) {
      case LEFT_RIGHT:
         return state.with(NORTH, state.get(SOUTH)).with(SOUTH, state.get(NORTH));
      case FRONT_BACK:
         return state.with(EAST, state.get(WEST)).with(WEST, state.get(EAST));
      default:
         return super.mirror(state, mirrorIn);
      }
   }

   protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
      builder.add(NORTH, EAST, SOUTH, WEST, POWER);
   }

   public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
      if (!player.abilities.allowEdit) {
         return ActionResultType.PASS;
      } else {
         if (func_235555_m_(state) || func_235556_n_(state)) {
            BlockState blockstate = func_235555_m_(state) ? this.getDefaultState() : this.field_235543_o_;
            blockstate = blockstate.with(POWER, state.get(POWER));
            blockstate = this.func_235544_a_(worldIn, blockstate, pos);
            if (blockstate != state) {
               worldIn.setBlockState(pos, blockstate, 3);
               this.func_235548_a_(worldIn, pos, state, blockstate);
               return ActionResultType.SUCCESS;
            }
         }

         return ActionResultType.PASS;
      }
   }

   private void func_235548_a_(World p_235548_1_, BlockPos p_235548_2_, BlockState p_235548_3_, BlockState p_235548_4_) {
      for(Direction direction : Direction.Plane.HORIZONTAL) {
         BlockPos blockpos = p_235548_2_.offset(direction);
         if (p_235548_3_.get(FACING_PROPERTY_MAP.get(direction)).func_235921_b_() != p_235548_4_.get(FACING_PROPERTY_MAP.get(direction)).func_235921_b_() && p_235548_1_.getBlockState(blockpos).isNormalCube(p_235548_1_, blockpos)) {
            p_235548_1_.notifyNeighborsOfStateExcept(blockpos, p_235548_4_.getBlock(), direction.getOpposite());
         }
      }

   }

   static {
      for(int i = 0; i <= 15; ++i) {
         float f = (float)i / 15.0F;
         float f1 = f * 0.6F + (f > 0.0F ? 0.4F : 0.3F);
         float f2 = MathHelper.clamp(f * f * 0.7F - 0.5F, 0.0F, 1.0F);
         float f3 = MathHelper.clamp(f * f * 0.6F - 0.7F, 0.0F, 1.0F);
         field_235542_k_[i] = new Vector3f(f1, f2, f3);
      }

   }
}