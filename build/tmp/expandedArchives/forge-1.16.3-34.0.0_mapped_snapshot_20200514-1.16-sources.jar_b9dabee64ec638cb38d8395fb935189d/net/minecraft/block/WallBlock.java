package net.minecraft.block;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;
import java.util.Map;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.Property;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;

public class WallBlock extends Block implements IWaterLoggable {
   public static final BooleanProperty UP = BlockStateProperties.UP;
   public static final EnumProperty<WallHeight> field_235612_b_ = BlockStateProperties.field_235908_S_;
   public static final EnumProperty<WallHeight> field_235613_c_ = BlockStateProperties.field_235909_T_;
   public static final EnumProperty<WallHeight> field_235614_d_ = BlockStateProperties.field_235910_U_;
   public static final EnumProperty<WallHeight> field_235615_e_ = BlockStateProperties.field_235911_V_;
   public static final BooleanProperty field_235616_f_ = BlockStateProperties.WATERLOGGED;
   private final Map<BlockState, VoxelShape> field_235617_g_;
   private final Map<BlockState, VoxelShape> field_235618_h_;
   private static final VoxelShape field_235619_i_ = Block.makeCuboidShape(7.0D, 0.0D, 7.0D, 9.0D, 16.0D, 9.0D);
   private static final VoxelShape field_235620_j_ = Block.makeCuboidShape(7.0D, 0.0D, 0.0D, 9.0D, 16.0D, 9.0D);
   private static final VoxelShape field_235621_k_ = Block.makeCuboidShape(7.0D, 0.0D, 7.0D, 9.0D, 16.0D, 16.0D);
   private static final VoxelShape field_235622_o_ = Block.makeCuboidShape(0.0D, 0.0D, 7.0D, 9.0D, 16.0D, 9.0D);
   private static final VoxelShape field_235623_p_ = Block.makeCuboidShape(7.0D, 0.0D, 7.0D, 16.0D, 16.0D, 9.0D);

   public WallBlock(AbstractBlock.Properties properties) {
      super(properties);
      this.setDefaultState(this.stateContainer.getBaseState().with(UP, Boolean.valueOf(true)).with(field_235613_c_, WallHeight.NONE).with(field_235612_b_, WallHeight.NONE).with(field_235614_d_, WallHeight.NONE).with(field_235615_e_, WallHeight.NONE).with(field_235616_f_, Boolean.valueOf(false)));
      this.field_235617_g_ = this.func_235624_a_(4.0F, 3.0F, 16.0F, 0.0F, 14.0F, 16.0F);
      this.field_235618_h_ = this.func_235624_a_(4.0F, 3.0F, 24.0F, 0.0F, 24.0F, 24.0F);
   }

   private static VoxelShape func_235631_a_(VoxelShape p_235631_0_, WallHeight p_235631_1_, VoxelShape p_235631_2_, VoxelShape p_235631_3_) {
      if (p_235631_1_ == WallHeight.TALL) {
         return VoxelShapes.or(p_235631_0_, p_235631_3_);
      } else {
         return p_235631_1_ == WallHeight.LOW ? VoxelShapes.or(p_235631_0_, p_235631_2_) : p_235631_0_;
      }
   }

   private Map<BlockState, VoxelShape> func_235624_a_(float p_235624_1_, float p_235624_2_, float p_235624_3_, float p_235624_4_, float p_235624_5_, float p_235624_6_) {
      float f = 8.0F - p_235624_1_;
      float f1 = 8.0F + p_235624_1_;
      float f2 = 8.0F - p_235624_2_;
      float f3 = 8.0F + p_235624_2_;
      VoxelShape voxelshape = Block.makeCuboidShape((double)f, 0.0D, (double)f, (double)f1, (double)p_235624_3_, (double)f1);
      VoxelShape voxelshape1 = Block.makeCuboidShape((double)f2, (double)p_235624_4_, 0.0D, (double)f3, (double)p_235624_5_, (double)f3);
      VoxelShape voxelshape2 = Block.makeCuboidShape((double)f2, (double)p_235624_4_, (double)f2, (double)f3, (double)p_235624_5_, 16.0D);
      VoxelShape voxelshape3 = Block.makeCuboidShape(0.0D, (double)p_235624_4_, (double)f2, (double)f3, (double)p_235624_5_, (double)f3);
      VoxelShape voxelshape4 = Block.makeCuboidShape((double)f2, (double)p_235624_4_, (double)f2, 16.0D, (double)p_235624_5_, (double)f3);
      VoxelShape voxelshape5 = Block.makeCuboidShape((double)f2, (double)p_235624_4_, 0.0D, (double)f3, (double)p_235624_6_, (double)f3);
      VoxelShape voxelshape6 = Block.makeCuboidShape((double)f2, (double)p_235624_4_, (double)f2, (double)f3, (double)p_235624_6_, 16.0D);
      VoxelShape voxelshape7 = Block.makeCuboidShape(0.0D, (double)p_235624_4_, (double)f2, (double)f3, (double)p_235624_6_, (double)f3);
      VoxelShape voxelshape8 = Block.makeCuboidShape((double)f2, (double)p_235624_4_, (double)f2, 16.0D, (double)p_235624_6_, (double)f3);
      Builder<BlockState, VoxelShape> builder = ImmutableMap.builder();

      for(Boolean obool : UP.getAllowedValues()) {
         for(WallHeight wallheight : field_235612_b_.getAllowedValues()) {
            for(WallHeight wallheight1 : field_235613_c_.getAllowedValues()) {
               for(WallHeight wallheight2 : field_235615_e_.getAllowedValues()) {
                  for(WallHeight wallheight3 : field_235614_d_.getAllowedValues()) {
                     VoxelShape voxelshape9 = VoxelShapes.empty();
                     voxelshape9 = func_235631_a_(voxelshape9, wallheight, voxelshape4, voxelshape8);
                     voxelshape9 = func_235631_a_(voxelshape9, wallheight2, voxelshape3, voxelshape7);
                     voxelshape9 = func_235631_a_(voxelshape9, wallheight1, voxelshape1, voxelshape5);
                     voxelshape9 = func_235631_a_(voxelshape9, wallheight3, voxelshape2, voxelshape6);
                     if (obool) {
                        voxelshape9 = VoxelShapes.or(voxelshape9, voxelshape);
                     }

                     BlockState blockstate = this.getDefaultState().with(UP, obool).with(field_235612_b_, wallheight).with(field_235615_e_, wallheight2).with(field_235613_c_, wallheight1).with(field_235614_d_, wallheight3);
                     builder.put(blockstate.with(field_235616_f_, Boolean.valueOf(false)), voxelshape9);
                     builder.put(blockstate.with(field_235616_f_, Boolean.valueOf(true)), voxelshape9);
                  }
               }
            }
         }
      }

      return builder.build();
   }

   public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
      return this.field_235617_g_.get(state);
   }

   public VoxelShape getCollisionShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
      return this.field_235618_h_.get(state);
   }

   public boolean allowsMovement(BlockState state, IBlockReader worldIn, BlockPos pos, PathType type) {
      return false;
   }

   private boolean func_220113_a(BlockState p_220113_1_, boolean p_220113_2_, Direction p_220113_3_) {
      Block block = p_220113_1_.getBlock();
      boolean flag = block instanceof FenceGateBlock && FenceGateBlock.isParallel(p_220113_1_, p_220113_3_);
      return p_220113_1_.func_235714_a_(BlockTags.WALLS) || !cannotAttach(block) && p_220113_2_ || block instanceof PaneBlock || flag;
   }

   public BlockState getStateForPlacement(BlockItemUseContext context) {
      IWorldReader iworldreader = context.getWorld();
      BlockPos blockpos = context.getPos();
      FluidState fluidstate = context.getWorld().getFluidState(context.getPos());
      BlockPos blockpos1 = blockpos.north();
      BlockPos blockpos2 = blockpos.east();
      BlockPos blockpos3 = blockpos.south();
      BlockPos blockpos4 = blockpos.west();
      BlockPos blockpos5 = blockpos.up();
      BlockState blockstate = iworldreader.getBlockState(blockpos1);
      BlockState blockstate1 = iworldreader.getBlockState(blockpos2);
      BlockState blockstate2 = iworldreader.getBlockState(blockpos3);
      BlockState blockstate3 = iworldreader.getBlockState(blockpos4);
      BlockState blockstate4 = iworldreader.getBlockState(blockpos5);
      boolean flag = this.func_220113_a(blockstate, blockstate.isSolidSide(iworldreader, blockpos1, Direction.SOUTH), Direction.SOUTH);
      boolean flag1 = this.func_220113_a(blockstate1, blockstate1.isSolidSide(iworldreader, blockpos2, Direction.WEST), Direction.WEST);
      boolean flag2 = this.func_220113_a(blockstate2, blockstate2.isSolidSide(iworldreader, blockpos3, Direction.NORTH), Direction.NORTH);
      boolean flag3 = this.func_220113_a(blockstate3, blockstate3.isSolidSide(iworldreader, blockpos4, Direction.EAST), Direction.EAST);
      BlockState blockstate5 = this.getDefaultState().with(field_235616_f_, Boolean.valueOf(fluidstate.getFluid() == Fluids.WATER));
      return this.func_235626_a_(iworldreader, blockstate5, blockpos5, blockstate4, flag, flag1, flag2, flag3);
   }

   /**
    * Update the provided state given the provided neighbor facing and neighbor state, returning a new state.
    * For example, fences make their connections to the passed in state if possible, and wet concrete powder immediately
    * returns its solidified counterpart.
    * Note that this method should ideally consider only the specific face passed in.
    */
   public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
      if (stateIn.get(field_235616_f_)) {
         worldIn.getPendingFluidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickRate(worldIn));
      }

      if (facing == Direction.DOWN) {
         return super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
      } else {
         return facing == Direction.UP ? this.func_235625_a_(worldIn, stateIn, facingPos, facingState) : this.func_235627_a_(worldIn, currentPos, stateIn, facingPos, facingState, facing);
      }
   }

   private static boolean func_235629_a_(BlockState p_235629_0_, Property<WallHeight> p_235629_1_) {
      return p_235629_0_.get(p_235629_1_) != WallHeight.NONE;
   }

   private static boolean func_235632_a_(VoxelShape p_235632_0_, VoxelShape p_235632_1_) {
      return !VoxelShapes.compare(p_235632_1_, p_235632_0_, IBooleanFunction.ONLY_FIRST);
   }

   private BlockState func_235625_a_(IWorldReader p_235625_1_, BlockState p_235625_2_, BlockPos p_235625_3_, BlockState p_235625_4_) {
      boolean flag = func_235629_a_(p_235625_2_, field_235613_c_);
      boolean flag1 = func_235629_a_(p_235625_2_, field_235612_b_);
      boolean flag2 = func_235629_a_(p_235625_2_, field_235614_d_);
      boolean flag3 = func_235629_a_(p_235625_2_, field_235615_e_);
      return this.func_235626_a_(p_235625_1_, p_235625_2_, p_235625_3_, p_235625_4_, flag, flag1, flag2, flag3);
   }

   private BlockState func_235627_a_(IWorldReader p_235627_1_, BlockPos p_235627_2_, BlockState p_235627_3_, BlockPos p_235627_4_, BlockState p_235627_5_, Direction p_235627_6_) {
      Direction direction = p_235627_6_.getOpposite();
      boolean flag = p_235627_6_ == Direction.NORTH ? this.func_220113_a(p_235627_5_, p_235627_5_.isSolidSide(p_235627_1_, p_235627_4_, direction), direction) : func_235629_a_(p_235627_3_, field_235613_c_);
      boolean flag1 = p_235627_6_ == Direction.EAST ? this.func_220113_a(p_235627_5_, p_235627_5_.isSolidSide(p_235627_1_, p_235627_4_, direction), direction) : func_235629_a_(p_235627_3_, field_235612_b_);
      boolean flag2 = p_235627_6_ == Direction.SOUTH ? this.func_220113_a(p_235627_5_, p_235627_5_.isSolidSide(p_235627_1_, p_235627_4_, direction), direction) : func_235629_a_(p_235627_3_, field_235614_d_);
      boolean flag3 = p_235627_6_ == Direction.WEST ? this.func_220113_a(p_235627_5_, p_235627_5_.isSolidSide(p_235627_1_, p_235627_4_, direction), direction) : func_235629_a_(p_235627_3_, field_235615_e_);
      BlockPos blockpos = p_235627_2_.up();
      BlockState blockstate = p_235627_1_.getBlockState(blockpos);
      return this.func_235626_a_(p_235627_1_, p_235627_3_, blockpos, blockstate, flag, flag1, flag2, flag3);
   }

   private BlockState func_235626_a_(IWorldReader p_235626_1_, BlockState p_235626_2_, BlockPos p_235626_3_, BlockState p_235626_4_, boolean p_235626_5_, boolean p_235626_6_, boolean p_235626_7_, boolean p_235626_8_) {
      VoxelShape voxelshape = p_235626_4_.getCollisionShape(p_235626_1_, p_235626_3_).project(Direction.DOWN);
      BlockState blockstate = this.func_235630_a_(p_235626_2_, p_235626_5_, p_235626_6_, p_235626_7_, p_235626_8_, voxelshape);
      return blockstate.with(UP, Boolean.valueOf(this.func_235628_a_(blockstate, p_235626_4_, voxelshape)));
   }

   private boolean func_235628_a_(BlockState p_235628_1_, BlockState p_235628_2_, VoxelShape p_235628_3_) {
      boolean flag = p_235628_2_.getBlock() instanceof WallBlock && p_235628_2_.get(UP);
      if (flag) {
         return true;
      } else {
         WallHeight wallheight = p_235628_1_.get(field_235613_c_);
         WallHeight wallheight1 = p_235628_1_.get(field_235614_d_);
         WallHeight wallheight2 = p_235628_1_.get(field_235612_b_);
         WallHeight wallheight3 = p_235628_1_.get(field_235615_e_);
         boolean flag1 = wallheight1 == WallHeight.NONE;
         boolean flag2 = wallheight3 == WallHeight.NONE;
         boolean flag3 = wallheight2 == WallHeight.NONE;
         boolean flag4 = wallheight == WallHeight.NONE;
         boolean flag5 = flag4 && flag1 && flag2 && flag3 || flag4 != flag1 || flag2 != flag3;
         if (flag5) {
            return true;
         } else {
            boolean flag6 = wallheight == WallHeight.TALL && wallheight1 == WallHeight.TALL || wallheight2 == WallHeight.TALL && wallheight3 == WallHeight.TALL;
            if (flag6) {
               return false;
            } else {
               return p_235628_2_.getBlock().isIn(BlockTags.field_232877_ar_) || func_235632_a_(p_235628_3_, field_235619_i_);
            }
         }
      }
   }

   private BlockState func_235630_a_(BlockState p_235630_1_, boolean p_235630_2_, boolean p_235630_3_, boolean p_235630_4_, boolean p_235630_5_, VoxelShape p_235630_6_) {
      return p_235630_1_.with(field_235613_c_, this.func_235633_a_(p_235630_2_, p_235630_6_, field_235620_j_)).with(field_235612_b_, this.func_235633_a_(p_235630_3_, p_235630_6_, field_235623_p_)).with(field_235614_d_, this.func_235633_a_(p_235630_4_, p_235630_6_, field_235621_k_)).with(field_235615_e_, this.func_235633_a_(p_235630_5_, p_235630_6_, field_235622_o_));
   }

   private WallHeight func_235633_a_(boolean p_235633_1_, VoxelShape p_235633_2_, VoxelShape p_235633_3_) {
      if (p_235633_1_) {
         return func_235632_a_(p_235633_2_, p_235633_3_) ? WallHeight.TALL : WallHeight.LOW;
      } else {
         return WallHeight.NONE;
      }
   }

   public FluidState getFluidState(BlockState state) {
      return state.get(field_235616_f_) ? Fluids.WATER.getStillFluidState(false) : super.getFluidState(state);
   }

   public boolean propagatesSkylightDown(BlockState state, IBlockReader reader, BlockPos pos) {
      return !state.get(field_235616_f_);
   }

   protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
      builder.add(UP, field_235613_c_, field_235612_b_, field_235615_e_, field_235614_d_, field_235616_f_);
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
         return state.with(field_235613_c_, state.get(field_235614_d_)).with(field_235612_b_, state.get(field_235615_e_)).with(field_235614_d_, state.get(field_235613_c_)).with(field_235615_e_, state.get(field_235612_b_));
      case COUNTERCLOCKWISE_90:
         return state.with(field_235613_c_, state.get(field_235612_b_)).with(field_235612_b_, state.get(field_235614_d_)).with(field_235614_d_, state.get(field_235615_e_)).with(field_235615_e_, state.get(field_235613_c_));
      case CLOCKWISE_90:
         return state.with(field_235613_c_, state.get(field_235615_e_)).with(field_235612_b_, state.get(field_235613_c_)).with(field_235614_d_, state.get(field_235612_b_)).with(field_235615_e_, state.get(field_235614_d_));
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
         return state.with(field_235613_c_, state.get(field_235614_d_)).with(field_235614_d_, state.get(field_235613_c_));
      case FRONT_BACK:
         return state.with(field_235612_b_, state.get(field_235615_e_)).with(field_235615_e_, state.get(field_235612_b_));
      default:
         return super.mirror(state, mirrorIn);
      }
   }
}