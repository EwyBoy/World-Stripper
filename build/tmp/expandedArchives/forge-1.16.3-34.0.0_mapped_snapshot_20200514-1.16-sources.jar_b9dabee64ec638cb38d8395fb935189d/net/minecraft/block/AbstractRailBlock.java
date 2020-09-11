package net.minecraft.block;

import net.minecraft.block.material.PushReaction;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.Property;
import net.minecraft.state.properties.RailShape;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

public abstract class AbstractRailBlock extends Block implements net.minecraftforge.common.extensions.IAbstractRailBlock {
   protected static final VoxelShape FLAT_AABB = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 2.0D, 16.0D);
   protected static final VoxelShape ASCENDING_AABB = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D);
   private final boolean disableCorners;

   public static boolean isRail(World worldIn, BlockPos pos) {
      return isRail(worldIn.getBlockState(pos));
   }

   public static boolean isRail(BlockState state) {
      return state.func_235714_a_(BlockTags.RAILS) && state.getBlock() instanceof AbstractRailBlock;
   }

   protected AbstractRailBlock(boolean p_i48444_1_, AbstractBlock.Properties p_i48444_2_) {
      super(p_i48444_2_);
      this.disableCorners = p_i48444_1_;
   }

   public boolean areCornersDisabled() {
      return this.disableCorners;
   }

   public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
      RailShape railshape = state.isIn(this) ? state.get(this.getShapeProperty()) : null;
      RailShape railShape2 = state.isIn(this) ? getRailDirection(state, worldIn, pos, null) : null;
      return railshape != null && railshape.isAscending() ? ASCENDING_AABB : FLAT_AABB;
   }

   public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
      return hasSolidSideOnTop(worldIn, pos.down());
   }

   public void onBlockAdded(BlockState state, World worldIn, BlockPos pos, BlockState oldState, boolean isMoving) {
      if (!oldState.isIn(state.getBlock())) {
         this.func_235327_a_(state, worldIn, pos, isMoving);
      }
   }

   protected BlockState func_235327_a_(BlockState p_235327_1_, World p_235327_2_, BlockPos p_235327_3_, boolean p_235327_4_) {
      p_235327_1_ = this.getUpdatedState(p_235327_2_, p_235327_3_, p_235327_1_, true);
      if (this.disableCorners) {
         p_235327_1_.neighborChanged(p_235327_2_, p_235327_3_, this, p_235327_3_, p_235327_4_);
      }

      return p_235327_1_;
   }

   public void neighborChanged(BlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
      if (!worldIn.isRemote && worldIn.getBlockState(pos).isIn(this)) {
         RailShape railshape = getRailDirection(state, worldIn, pos, null);
         if (func_235328_a_(pos, worldIn, railshape)) {
            spawnDrops(state, worldIn, pos);
            worldIn.removeBlock(pos, isMoving);
         } else {
            this.updateState(state, worldIn, pos, blockIn);
         }

      }
   }

   private static boolean func_235328_a_(BlockPos p_235328_0_, World p_235328_1_, RailShape p_235328_2_) {
      if (!hasSolidSideOnTop(p_235328_1_, p_235328_0_.down())) {
         return true;
      } else {
         switch(p_235328_2_) {
         case ASCENDING_EAST:
            return !hasSolidSideOnTop(p_235328_1_, p_235328_0_.east());
         case ASCENDING_WEST:
            return !hasSolidSideOnTop(p_235328_1_, p_235328_0_.west());
         case ASCENDING_NORTH:
            return !hasSolidSideOnTop(p_235328_1_, p_235328_0_.north());
         case ASCENDING_SOUTH:
            return !hasSolidSideOnTop(p_235328_1_, p_235328_0_.south());
         default:
            return false;
         }
      }
   }

   protected void updateState(BlockState state, World worldIn, BlockPos pos, Block blockIn) {
   }

   protected BlockState getUpdatedState(World worldIn, BlockPos pos, BlockState state, boolean placing) {
      if (worldIn.isRemote) {
         return state;
      } else {
         RailShape railshape = state.get(this.getShapeProperty());
         return (new RailState(worldIn, pos, state)).func_226941_a_(worldIn.isBlockPowered(pos), placing, railshape).getNewState();
      }
   }

   /**
    * @deprecated call via {@link IBlockState#getMobilityFlag()} whenever possible. Implementing/overriding is fine.
    */
   public PushReaction getPushReaction(BlockState state) {
      return PushReaction.NORMAL;
   }

   public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
      if (!isMoving) {
         super.onReplaced(state, worldIn, pos, newState, isMoving);
         if (getRailDirection(state, worldIn, pos, null).isAscending()) {
            worldIn.notifyNeighborsOfStateChange(pos.up(), this);
         }

         if (this.disableCorners) {
            worldIn.notifyNeighborsOfStateChange(pos, this);
            worldIn.notifyNeighborsOfStateChange(pos.down(), this);
         }

      }
   }

   public BlockState getStateForPlacement(BlockItemUseContext context) {
      BlockState blockstate = super.getDefaultState();
      Direction direction = context.getPlacementHorizontalFacing();
      boolean flag = direction == Direction.EAST || direction == Direction.WEST;
      return blockstate.with(this.getShapeProperty(), flag ? RailShape.EAST_WEST : RailShape.NORTH_SOUTH);
   }

   @Deprecated //Forge: Use getRailDirection(IBlockAccess, BlockPos, IBlockState, EntityMinecart) for enhanced ability
   public abstract Property<RailShape> getShapeProperty();

   /* ======================================== FORGE START =====================================*/

   @Override
   public boolean isFlexibleRail(BlockState state, IBlockReader world, BlockPos pos)
   {
      return  !this.disableCorners;
   }

   @Override
   public RailShape getRailDirection(BlockState state, IBlockReader world, BlockPos pos, @javax.annotation.Nullable net.minecraft.entity.item.minecart.AbstractMinecartEntity cart) {
      return state.get(getShapeProperty());
   }
   /* ========================================= FORGE END ======================================*/
}