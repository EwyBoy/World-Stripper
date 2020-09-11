package net.minecraft.item;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import java.util.Map;
import java.util.Set;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class HoeItem extends ToolItem {
   private static final Set<Block> field_234683_c_ = ImmutableSet.of(Blocks.NETHER_WART_BLOCK, Blocks.field_235374_mn_, Blocks.HAY_BLOCK, Blocks.DRIED_KELP_BLOCK, Blocks.field_235396_nb_, Blocks.field_235383_mw_, Blocks.SPONGE, Blocks.WET_SPONGE, Blocks.JUNGLE_LEAVES, Blocks.OAK_LEAVES, Blocks.SPRUCE_LEAVES, Blocks.DARK_OAK_LEAVES, Blocks.ACACIA_LEAVES, Blocks.BIRCH_LEAVES);
   protected static final Map<Block, BlockState> HOE_LOOKUP = Maps.newHashMap(ImmutableMap.of(Blocks.GRASS_BLOCK, Blocks.FARMLAND.getDefaultState(), Blocks.GRASS_PATH, Blocks.FARMLAND.getDefaultState(), Blocks.DIRT, Blocks.FARMLAND.getDefaultState(), Blocks.COARSE_DIRT, Blocks.DIRT.getDefaultState()));

   public HoeItem(IItemTier p_i231595_1_, int p_i231595_2_, float p_i231595_3_, Item.Properties p_i231595_4_) {
      super((float)p_i231595_2_, p_i231595_3_, p_i231595_1_, field_234683_c_, p_i231595_4_.addToolType(net.minecraftforge.common.ToolType.HOE, p_i231595_1_.getHarvestLevel()));
   }

   /**
    * Called when this item is used when targetting a Block
    */
   public ActionResultType onItemUse(ItemUseContext context) {
      World world = context.getWorld();
      BlockPos blockpos = context.getPos();
      int hook = net.minecraftforge.event.ForgeEventFactory.onHoeUse(context);
      if (hook != 0) return hook > 0 ? ActionResultType.SUCCESS : ActionResultType.FAIL;
      if (context.getFace() != Direction.DOWN && world.isAirBlock(blockpos.up())) {
         BlockState blockstate = world.getBlockState(blockpos).getToolModifiedState(world, blockpos, context.getPlayer(), context.getItem(), net.minecraftforge.common.ToolType.HOE);
         if (blockstate != null) {
            PlayerEntity playerentity = context.getPlayer();
            world.playSound(playerentity, blockpos, SoundEvents.ITEM_HOE_TILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
            if (!world.isRemote) {
               world.setBlockState(blockpos, blockstate, 11);
               if (playerentity != null) {
                  context.getItem().damageItem(1, playerentity, (p_220043_1_) -> {
                     p_220043_1_.sendBreakAnimation(context.getHand());
                  });
               }
            }

            return ActionResultType.func_233537_a_(world.isRemote);
         }
      }

      return ActionResultType.PASS;
   }

   @javax.annotation.Nullable
   public static BlockState getHoeTillingState(BlockState originalState) {
      return HOE_LOOKUP.get(originalState.getBlock());
   }
}