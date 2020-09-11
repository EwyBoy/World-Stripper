package net.minecraft.block;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.inventory.container.SmithingTableContainer;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

public class SmithingTableBlock extends CraftingTableBlock {
   private static final ITextComponent field_235575_a_ = new TranslationTextComponent("container.upgrade");

   public SmithingTableBlock(AbstractBlock.Properties p_i49974_1_) {
      super(p_i49974_1_);
   }

   public INamedContainerProvider getContainer(BlockState state, World worldIn, BlockPos pos) {
      return new SimpleNamedContainerProvider((p_235576_2_, p_235576_3_, p_235576_4_) -> {
         return new SmithingTableContainer(p_235576_2_, p_235576_3_, IWorldPosCallable.of(worldIn, pos));
      }, field_235575_a_);
   }

   public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
      if (worldIn.isRemote) {
         return ActionResultType.SUCCESS;
      } else {
         player.openContainer(state.getContainer(worldIn, pos));
         player.addStat(Stats.field_232864_aE_);
         return ActionResultType.CONSUME;
      }
   }
}