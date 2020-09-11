package net.minecraft.block;

import javax.annotation.Nullable;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.JigsawTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.jigsaw.JigsawOrientation;
import net.minecraft.world.gen.feature.template.Template;

public class JigsawBlock extends Block implements ITileEntityProvider {
   public static final EnumProperty<JigsawOrientation> field_235506_a_ = BlockStateProperties.field_235907_P_;

   public JigsawBlock(AbstractBlock.Properties properties) {
      super(properties);
      this.setDefaultState(this.stateContainer.getBaseState().with(field_235506_a_, JigsawOrientation.NORTH_UP));
   }

   protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
      builder.add(field_235506_a_);
   }

   /**
    * Returns the blockstate with the given rotation from the passed blockstate. If inapplicable, returns the passed
    * blockstate.
    * @deprecated call via {@link IBlockState#withRotation(Rotation)} whenever possible. Implementing/overriding is
    * fine.
    */
   public BlockState rotate(BlockState state, Rotation rot) {
      return state.with(field_235506_a_, rot.func_235574_a_().func_235531_a_(state.get(field_235506_a_)));
   }

   /**
    * Returns the blockstate with the given mirror of the passed blockstate. If inapplicable, returns the passed
    * blockstate.
    * @deprecated call via {@link IBlockState#withMirror(Mirror)} whenever possible. Implementing/overriding is fine.
    */
   public BlockState mirror(BlockState state, Mirror mirrorIn) {
      return state.with(field_235506_a_, mirrorIn.func_235512_a_().func_235531_a_(state.get(field_235506_a_)));
   }

   public BlockState getStateForPlacement(BlockItemUseContext context) {
      Direction direction = context.getFace();
      Direction direction1;
      if (direction.getAxis() == Direction.Axis.Y) {
         direction1 = context.getPlacementHorizontalFacing().getOpposite();
      } else {
         direction1 = Direction.UP;
      }

      return this.getDefaultState().with(field_235506_a_, JigsawOrientation.func_239641_a_(direction, direction1));
   }

   @Nullable
   public TileEntity createNewTileEntity(IBlockReader worldIn) {
      return new JigsawTileEntity();
   }

   public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
      TileEntity tileentity = worldIn.getTileEntity(pos);
      if (tileentity instanceof JigsawTileEntity && player.canUseCommandBlock()) {
         player.openJigsaw((JigsawTileEntity)tileentity);
         return ActionResultType.func_233537_a_(worldIn.isRemote);
      } else {
         return ActionResultType.PASS;
      }
   }

   public static boolean func_220171_a(Template.BlockInfo p_220171_0_, Template.BlockInfo p_220171_1_) {
      Direction direction = func_235508_h_(p_220171_0_.state);
      Direction direction1 = func_235508_h_(p_220171_1_.state);
      Direction direction2 = func_235509_l_(p_220171_0_.state);
      Direction direction3 = func_235509_l_(p_220171_1_.state);
      JigsawTileEntity.OrientationType jigsawtileentity$orientationtype = JigsawTileEntity.OrientationType.func_235673_a_(p_220171_0_.nbt.getString("joint")).orElseGet(() -> {
         return direction.getAxis().isHorizontal() ? JigsawTileEntity.OrientationType.ALIGNED : JigsawTileEntity.OrientationType.ROLLABLE;
      });
      boolean flag = jigsawtileentity$orientationtype == JigsawTileEntity.OrientationType.ROLLABLE;
      return direction == direction1.getOpposite() && (flag || direction2 == direction3) && p_220171_0_.nbt.getString("target").equals(p_220171_1_.nbt.getString("name"));
   }

   public static Direction func_235508_h_(BlockState p_235508_0_) {
      return p_235508_0_.get(field_235506_a_).func_239642_b_();
   }

   public static Direction func_235509_l_(BlockState p_235509_0_) {
      return p_235509_0_.get(field_235506_a_).func_239644_c_();
   }
}