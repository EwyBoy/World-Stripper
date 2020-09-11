package net.minecraft.block;

import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class RedstoneLampBlock extends Block {
   public static final BooleanProperty LIT = RedstoneTorchBlock.LIT;

   public RedstoneLampBlock(AbstractBlock.Properties properties) {
      super(properties);
      this.setDefaultState(this.getDefaultState().with(LIT, Boolean.valueOf(false)));
   }

   @Nullable
   public BlockState getStateForPlacement(BlockItemUseContext context) {
      return this.getDefaultState().with(LIT, Boolean.valueOf(context.getWorld().isBlockPowered(context.getPos())));
   }

   public void neighborChanged(BlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
      if (!worldIn.isRemote) {
         boolean flag = state.get(LIT);
         if (flag != worldIn.isBlockPowered(pos)) {
            if (flag) {
               worldIn.getPendingBlockTicks().scheduleTick(pos, this, 4);
            } else {
               worldIn.setBlockState(pos, state.func_235896_a_(LIT), 2);
            }
         }

      }
   }

   public void tick(BlockState state, ServerWorld worldIn, BlockPos pos, Random rand) {
      if (state.get(LIT) && !worldIn.isBlockPowered(pos)) {
         worldIn.setBlockState(pos, state.func_235896_a_(LIT), 2);
      }

   }

   protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
      builder.add(LIT);
   }
}