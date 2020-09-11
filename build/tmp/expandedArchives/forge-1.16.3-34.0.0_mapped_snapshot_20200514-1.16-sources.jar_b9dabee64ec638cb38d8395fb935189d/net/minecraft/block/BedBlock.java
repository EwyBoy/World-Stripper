package net.minecraft.block;

import java.util.List;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.block.material.PushReaction;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.DyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BedPart;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.BedTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityMerger;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.TransportationHelper;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.Explosion;
import net.minecraft.world.ExplosionContext;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.ICollisionReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.commons.lang3.ArrayUtils;

public class BedBlock extends HorizontalBlock implements ITileEntityProvider {
   public static final EnumProperty<BedPart> PART = BlockStateProperties.BED_PART;
   public static final BooleanProperty OCCUPIED = BlockStateProperties.OCCUPIED;
   protected static final VoxelShape field_220176_c = Block.makeCuboidShape(0.0D, 3.0D, 0.0D, 16.0D, 9.0D, 16.0D);
   protected static final VoxelShape field_220177_d = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 3.0D, 3.0D, 3.0D);
   protected static final VoxelShape field_220178_e = Block.makeCuboidShape(0.0D, 0.0D, 13.0D, 3.0D, 3.0D, 16.0D);
   protected static final VoxelShape field_220179_f = Block.makeCuboidShape(13.0D, 0.0D, 0.0D, 16.0D, 3.0D, 3.0D);
   protected static final VoxelShape field_220180_g = Block.makeCuboidShape(13.0D, 0.0D, 13.0D, 16.0D, 3.0D, 16.0D);
   protected static final VoxelShape field_220181_h = VoxelShapes.or(field_220176_c, field_220177_d, field_220179_f);
   protected static final VoxelShape field_220182_i = VoxelShapes.or(field_220176_c, field_220178_e, field_220180_g);
   protected static final VoxelShape field_220183_j = VoxelShapes.or(field_220176_c, field_220177_d, field_220178_e);
   protected static final VoxelShape field_220184_k = VoxelShapes.or(field_220176_c, field_220179_f, field_220180_g);
   private final DyeColor color;

   public BedBlock(DyeColor colorIn, AbstractBlock.Properties properties) {
      super(properties);
      this.color = colorIn;
      this.setDefaultState(this.stateContainer.getBaseState().with(PART, BedPart.FOOT).with(OCCUPIED, Boolean.valueOf(false)));
   }

   @Nullable
   @OnlyIn(Dist.CLIENT)
   public static Direction func_220174_a(IBlockReader p_220174_0_, BlockPos p_220174_1_) {
      BlockState blockstate = p_220174_0_.getBlockState(p_220174_1_);
      return blockstate.getBlock() instanceof BedBlock ? blockstate.get(HORIZONTAL_FACING) : null;
   }

   public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
      if (worldIn.isRemote) {
         return ActionResultType.CONSUME;
      } else {
         if (state.get(PART) != BedPart.HEAD) {
            pos = pos.offset(state.get(HORIZONTAL_FACING));
            state = worldIn.getBlockState(pos);
            if (!state.isIn(this)) {
               return ActionResultType.CONSUME;
            }
         }

         if (!func_235330_a_(worldIn)) {
            worldIn.removeBlock(pos, false);
            BlockPos blockpos = pos.offset(state.get(HORIZONTAL_FACING).getOpposite());
            if (worldIn.getBlockState(blockpos).isIn(this)) {
               worldIn.removeBlock(blockpos, false);
            }

            worldIn.func_230546_a_((Entity)null, DamageSource.func_233546_a_(), (ExplosionContext)null, (double)pos.getX() + 0.5D, (double)pos.getY() + 0.5D, (double)pos.getZ() + 0.5D, 5.0F, true, Explosion.Mode.DESTROY);
            return ActionResultType.SUCCESS;
         } else if (state.get(OCCUPIED)) {
            if (!this.func_226861_a_(worldIn, pos)) {
               player.sendStatusMessage(new TranslationTextComponent("block.minecraft.bed.occupied"), true);
            }

            return ActionResultType.SUCCESS;
         } else {
            player.trySleep(pos).ifLeft((p_220173_1_) -> {
               if (p_220173_1_ != null) {
                  player.sendStatusMessage(p_220173_1_.getMessage(), true);
               }

            });
            return ActionResultType.SUCCESS;
         }
      }
   }

   public static boolean func_235330_a_(World p_235330_0_) {
      return p_235330_0_.func_230315_m_().func_241510_j_();
   }

   private boolean func_226861_a_(World p_226861_1_, BlockPos p_226861_2_) {
      List<VillagerEntity> list = p_226861_1_.getEntitiesWithinAABB(VillagerEntity.class, new AxisAlignedBB(p_226861_2_), LivingEntity::isSleeping);
      if (list.isEmpty()) {
         return false;
      } else {
         list.get(0).wakeUp();
         return true;
      }
   }

   /**
    * Block's chance to react to a living entity falling on it.
    */
   public void onFallenUpon(World worldIn, BlockPos pos, Entity entityIn, float fallDistance) {
      super.onFallenUpon(worldIn, pos, entityIn, fallDistance * 0.5F);
   }

   /**
    * Called when an Entity lands on this Block. This method *must* update motionY because the entity will not do that
    * on its own
    */
   public void onLanded(IBlockReader worldIn, Entity entityIn) {
      if (entityIn.isSuppressingBounce()) {
         super.onLanded(worldIn, entityIn);
      } else {
         this.func_226860_a_(entityIn);
      }

   }

   private void func_226860_a_(Entity p_226860_1_) {
      Vector3d vector3d = p_226860_1_.getMotion();
      if (vector3d.y < 0.0D) {
         double d0 = p_226860_1_ instanceof LivingEntity ? 1.0D : 0.8D;
         p_226860_1_.setMotion(vector3d.x, -vector3d.y * (double)0.66F * d0, vector3d.z);
      }

   }

   /**
    * Update the provided state given the provided neighbor facing and neighbor state, returning a new state.
    * For example, fences make their connections to the passed in state if possible, and wet concrete powder immediately
    * returns its solidified counterpart.
    * Note that this method should ideally consider only the specific face passed in.
    */
   public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
      if (facing == getDirectionToOther(stateIn.get(PART), stateIn.get(HORIZONTAL_FACING))) {
         return facingState.isIn(this) && facingState.get(PART) != stateIn.get(PART) ? stateIn.with(OCCUPIED, facingState.get(OCCUPIED)) : Blocks.AIR.getDefaultState();
      } else {
         return super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
      }
   }

   /**
    * Given a bed part and the direction it's facing, find the direction to move to get the other bed part
    */
   private static Direction getDirectionToOther(BedPart p_208070_0_, Direction p_208070_1_) {
      return p_208070_0_ == BedPart.FOOT ? p_208070_1_ : p_208070_1_.getOpposite();
   }

   /**
    * Called before the Block is set to air in the world. Called regardless of if the player's tool can actually collect
    * this block
    */
   public void onBlockHarvested(World worldIn, BlockPos pos, BlockState state, PlayerEntity player) {
      if (!worldIn.isRemote && player.isCreative()) {
         BedPart bedpart = state.get(PART);
         if (bedpart == BedPart.FOOT) {
            BlockPos blockpos = pos.offset(getDirectionToOther(bedpart, state.get(HORIZONTAL_FACING)));
            BlockState blockstate = worldIn.getBlockState(blockpos);
            if (blockstate.getBlock() == this && blockstate.get(PART) == BedPart.HEAD) {
               worldIn.setBlockState(blockpos, Blocks.AIR.getDefaultState(), 35);
               worldIn.playEvent(player, 2001, blockpos, Block.getStateId(blockstate));
            }
         }
      }

      super.onBlockHarvested(worldIn, pos, state, player);
   }

   @Nullable
   public BlockState getStateForPlacement(BlockItemUseContext context) {
      Direction direction = context.getPlacementHorizontalFacing();
      BlockPos blockpos = context.getPos();
      BlockPos blockpos1 = blockpos.offset(direction);
      return context.getWorld().getBlockState(blockpos1).isReplaceable(context) ? this.getDefaultState().with(HORIZONTAL_FACING, direction) : null;
   }

   public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
      Direction direction = func_226862_h_(state).getOpposite();
      switch(direction) {
      case NORTH:
         return field_220181_h;
      case SOUTH:
         return field_220182_i;
      case WEST:
         return field_220183_j;
      default:
         return field_220184_k;
      }
   }

   public static Direction func_226862_h_(BlockState p_226862_0_) {
      Direction direction = p_226862_0_.get(HORIZONTAL_FACING);
      return p_226862_0_.get(PART) == BedPart.HEAD ? direction.getOpposite() : direction;
   }

   @OnlyIn(Dist.CLIENT)
   public static TileEntityMerger.Type func_226863_i_(BlockState p_226863_0_) {
      BedPart bedpart = p_226863_0_.get(PART);
      return bedpart == BedPart.HEAD ? TileEntityMerger.Type.FIRST : TileEntityMerger.Type.SECOND;
   }

   private static boolean func_242657_b(IBlockReader p_242657_0_, BlockPos p_242657_1_) {
      return p_242657_0_.getBlockState(p_242657_1_.down()).getBlock() instanceof BedBlock;
   }

   public static Optional<Vector3d> func_242652_a(EntityType<?> p_242652_0_, ICollisionReader p_242652_1_, BlockPos p_242652_2_, float p_242652_3_) {
      Direction direction = p_242652_1_.getBlockState(p_242652_2_).get(HORIZONTAL_FACING);
      Direction direction1 = direction.rotateY();
      Direction direction2 = direction1.func_243532_a(p_242652_3_) ? direction1.getOpposite() : direction1;
      if (func_242657_b(p_242652_1_, p_242652_2_)) {
         return func_242653_a(p_242652_0_, p_242652_1_, p_242652_2_, direction, direction2);
      } else {
         int[][] aint = func_242656_a(direction, direction2);
         Optional<Vector3d> optional = func_242654_a(p_242652_0_, p_242652_1_, p_242652_2_, aint, true);
         return optional.isPresent() ? optional : func_242654_a(p_242652_0_, p_242652_1_, p_242652_2_, aint, false);
      }
   }

   private static Optional<Vector3d> func_242653_a(EntityType<?> p_242653_0_, ICollisionReader p_242653_1_, BlockPos p_242653_2_, Direction p_242653_3_, Direction p_242653_4_) {
      int[][] aint = func_242658_b(p_242653_3_, p_242653_4_);
      Optional<Vector3d> optional = func_242654_a(p_242653_0_, p_242653_1_, p_242653_2_, aint, true);
      if (optional.isPresent()) {
         return optional;
      } else {
         BlockPos blockpos = p_242653_2_.down();
         Optional<Vector3d> optional1 = func_242654_a(p_242653_0_, p_242653_1_, blockpos, aint, true);
         if (optional1.isPresent()) {
            return optional1;
         } else {
            int[][] aint1 = func_242655_a(p_242653_3_);
            Optional<Vector3d> optional2 = func_242654_a(p_242653_0_, p_242653_1_, p_242653_2_, aint1, true);
            if (optional2.isPresent()) {
               return optional2;
            } else {
               Optional<Vector3d> optional3 = func_242654_a(p_242653_0_, p_242653_1_, p_242653_2_, aint, false);
               if (optional3.isPresent()) {
                  return optional3;
               } else {
                  Optional<Vector3d> optional4 = func_242654_a(p_242653_0_, p_242653_1_, blockpos, aint, false);
                  return optional4.isPresent() ? optional4 : func_242654_a(p_242653_0_, p_242653_1_, p_242653_2_, aint1, false);
               }
            }
         }
      }
   }

   private static Optional<Vector3d> func_242654_a(EntityType<?> p_242654_0_, ICollisionReader p_242654_1_, BlockPos p_242654_2_, int[][] p_242654_3_, boolean p_242654_4_) {
      BlockPos.Mutable blockpos$mutable = new BlockPos.Mutable();

      for(int[] aint : p_242654_3_) {
         blockpos$mutable.setPos(p_242654_2_.getX() + aint[0], p_242654_2_.getY(), p_242654_2_.getZ() + aint[1]);
         Vector3d vector3d = TransportationHelper.func_242379_a(p_242654_0_, p_242654_1_, blockpos$mutable, p_242654_4_);
         if (vector3d != null) {
            return Optional.of(vector3d);
         }
      }

      return Optional.empty();
   }

   /**
    * @deprecated call via {@link IBlockState#getMobilityFlag()} whenever possible. Implementing/overriding is fine.
    */
   public PushReaction getPushReaction(BlockState state) {
      return PushReaction.DESTROY;
   }

   /**
    * The type of render function called. MODEL for mixed tesr and static model, MODELBLOCK_ANIMATED for TESR-only,
    * LIQUID for vanilla liquids, INVISIBLE to skip all rendering
    * @deprecated call via {@link IBlockState#getRenderType()} whenever possible. Implementing/overriding is fine.
    */
   public BlockRenderType getRenderType(BlockState state) {
      return BlockRenderType.ENTITYBLOCK_ANIMATED;
   }

   protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
      builder.add(HORIZONTAL_FACING, PART, OCCUPIED);
   }

   public TileEntity createNewTileEntity(IBlockReader worldIn) {
      return new BedTileEntity(this.color);
   }

   /**
    * Called by ItemBlocks after a block is set in the world, to allow post-place logic
    */
   public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
      super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
      if (!worldIn.isRemote) {
         BlockPos blockpos = pos.offset(state.get(HORIZONTAL_FACING));
         worldIn.setBlockState(blockpos, state.with(PART, BedPart.HEAD), 3);
         worldIn.func_230547_a_(pos, Blocks.AIR);
         state.func_235734_a_(worldIn, pos, 3);
      }

   }

   @OnlyIn(Dist.CLIENT)
   public DyeColor getColor() {
      return this.color;
   }

   /**
    * Return a random long to be passed to {@link IBakedModel#getQuads}, used for random model rotations
    */
   @OnlyIn(Dist.CLIENT)
   public long getPositionRandom(BlockState state, BlockPos pos) {
      BlockPos blockpos = pos.offset(state.get(HORIZONTAL_FACING), state.get(PART) == BedPart.HEAD ? 0 : 1);
      return MathHelper.getCoordinateRandom(blockpos.getX(), pos.getY(), blockpos.getZ());
   }

   public boolean allowsMovement(BlockState state, IBlockReader worldIn, BlockPos pos, PathType type) {
      return false;
   }

   private static int[][] func_242656_a(Direction p_242656_0_, Direction p_242656_1_) {
      return ArrayUtils.addAll((int[][])func_242658_b(p_242656_0_, p_242656_1_), (int[][])func_242655_a(p_242656_0_));
   }

   private static int[][] func_242658_b(Direction p_242658_0_, Direction p_242658_1_) {
      return new int[][]{{p_242658_1_.getXOffset(), p_242658_1_.getZOffset()}, {p_242658_1_.getXOffset() - p_242658_0_.getXOffset(), p_242658_1_.getZOffset() - p_242658_0_.getZOffset()}, {p_242658_1_.getXOffset() - p_242658_0_.getXOffset() * 2, p_242658_1_.getZOffset() - p_242658_0_.getZOffset() * 2}, {-p_242658_0_.getXOffset() * 2, -p_242658_0_.getZOffset() * 2}, {-p_242658_1_.getXOffset() - p_242658_0_.getXOffset() * 2, -p_242658_1_.getZOffset() - p_242658_0_.getZOffset() * 2}, {-p_242658_1_.getXOffset() - p_242658_0_.getXOffset(), -p_242658_1_.getZOffset() - p_242658_0_.getZOffset()}, {-p_242658_1_.getXOffset(), -p_242658_1_.getZOffset()}, {-p_242658_1_.getXOffset() + p_242658_0_.getXOffset(), -p_242658_1_.getZOffset() + p_242658_0_.getZOffset()}, {p_242658_0_.getXOffset(), p_242658_0_.getZOffset()}, {p_242658_1_.getXOffset() + p_242658_0_.getXOffset(), p_242658_1_.getZOffset() + p_242658_0_.getZOffset()}};
   }

   private static int[][] func_242655_a(Direction p_242655_0_) {
      return new int[][]{{0, 0}, {-p_242655_0_.getXOffset(), -p_242655_0_.getZOffset()}};
   }
}