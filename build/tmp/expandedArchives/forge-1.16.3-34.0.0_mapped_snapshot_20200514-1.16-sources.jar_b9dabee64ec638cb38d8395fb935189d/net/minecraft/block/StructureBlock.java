package net.minecraft.block;

import javax.annotation.Nullable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.StructureMode;
import net.minecraft.tileentity.StructureBlockTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class StructureBlock extends ContainerBlock {
   public static final EnumProperty<StructureMode> MODE = BlockStateProperties.STRUCTURE_BLOCK_MODE;

   public StructureBlock(AbstractBlock.Properties properties) {
      super(properties);
   }

   public TileEntity createNewTileEntity(IBlockReader worldIn) {
      return new StructureBlockTileEntity();
   }

   public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
      TileEntity tileentity = worldIn.getTileEntity(pos);
      if (tileentity instanceof StructureBlockTileEntity) {
         return ((StructureBlockTileEntity)tileentity).usedBy(player) ? ActionResultType.func_233537_a_(worldIn.isRemote) : ActionResultType.PASS;
      } else {
         return ActionResultType.PASS;
      }
   }

   /**
    * Called by ItemBlocks after a block is set in the world, to allow post-place logic
    */
   public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
      if (!worldIn.isRemote) {
         if (placer != null) {
            TileEntity tileentity = worldIn.getTileEntity(pos);
            if (tileentity instanceof StructureBlockTileEntity) {
               ((StructureBlockTileEntity)tileentity).createdBy(placer);
            }
         }

      }
   }

   /**
    * The type of render function called. MODEL for mixed tesr and static model, MODELBLOCK_ANIMATED for TESR-only,
    * LIQUID for vanilla liquids, INVISIBLE to skip all rendering
    * @deprecated call via {@link IBlockState#getRenderType()} whenever possible. Implementing/overriding is fine.
    */
   public BlockRenderType getRenderType(BlockState state) {
      return BlockRenderType.MODEL;
   }

   public BlockState getStateForPlacement(BlockItemUseContext context) {
      return this.getDefaultState().with(MODE, StructureMode.DATA);
   }

   protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
      builder.add(MODE);
   }

   public void neighborChanged(BlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
      if (worldIn instanceof ServerWorld) {
         TileEntity tileentity = worldIn.getTileEntity(pos);
         if (tileentity instanceof StructureBlockTileEntity) {
            StructureBlockTileEntity structureblocktileentity = (StructureBlockTileEntity)tileentity;
            boolean flag = worldIn.isBlockPowered(pos);
            boolean flag1 = structureblocktileentity.isPowered();
            if (flag && !flag1) {
               structureblocktileentity.setPowered(true);
               this.func_242679_a((ServerWorld)worldIn, structureblocktileentity);
            } else if (!flag && flag1) {
               structureblocktileentity.setPowered(false);
            }

         }
      }
   }

   private void func_242679_a(ServerWorld p_242679_1_, StructureBlockTileEntity p_242679_2_) {
      switch(p_242679_2_.getMode()) {
      case SAVE:
         p_242679_2_.save(false);
         break;
      case LOAD:
         p_242679_2_.func_242688_a(p_242679_1_, false);
         break;
      case CORNER:
         p_242679_2_.unloadStructure();
      case DATA:
      }

   }
}