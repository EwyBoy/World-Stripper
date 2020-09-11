package net.minecraft.item;

import com.google.common.collect.Sets;
import com.google.common.collect.ImmutableMap.Builder;
import java.util.Map;
import java.util.Set;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class AxeItem extends ToolItem {
   private static final Set<Material> field_234662_c_ = Sets.newHashSet(Material.WOOD, Material.field_237214_y_, Material.PLANTS, Material.TALL_PLANTS, Material.BAMBOO, Material.GOURD);
   private static final Set<Block> field_150917_d_ = Sets.newHashSet(Blocks.LADDER, Blocks.SCAFFOLDING, Blocks.OAK_BUTTON, Blocks.SPRUCE_BUTTON, Blocks.BIRCH_BUTTON, Blocks.JUNGLE_BUTTON, Blocks.DARK_OAK_BUTTON, Blocks.ACACIA_BUTTON, Blocks.field_235358_mQ_, Blocks.field_235359_mR_);
   protected static final Map<Block, Block> BLOCK_STRIPPING_MAP = (new Builder<Block, Block>()).put(Blocks.OAK_WOOD, Blocks.STRIPPED_OAK_WOOD).put(Blocks.OAK_LOG, Blocks.STRIPPED_OAK_LOG).put(Blocks.DARK_OAK_WOOD, Blocks.STRIPPED_DARK_OAK_WOOD).put(Blocks.DARK_OAK_LOG, Blocks.STRIPPED_DARK_OAK_LOG).put(Blocks.ACACIA_WOOD, Blocks.STRIPPED_ACACIA_WOOD).put(Blocks.ACACIA_LOG, Blocks.STRIPPED_ACACIA_LOG).put(Blocks.BIRCH_WOOD, Blocks.STRIPPED_BIRCH_WOOD).put(Blocks.BIRCH_LOG, Blocks.STRIPPED_BIRCH_LOG).put(Blocks.JUNGLE_WOOD, Blocks.STRIPPED_JUNGLE_WOOD).put(Blocks.JUNGLE_LOG, Blocks.STRIPPED_JUNGLE_LOG).put(Blocks.SPRUCE_WOOD, Blocks.STRIPPED_SPRUCE_WOOD).put(Blocks.SPRUCE_LOG, Blocks.STRIPPED_SPRUCE_LOG).put(Blocks.field_235368_mh_, Blocks.field_235369_mi_).put(Blocks.field_235370_mj_, Blocks.field_235371_mk_).put(Blocks.field_235377_mq_, Blocks.field_235378_mr_).put(Blocks.field_235379_ms_, Blocks.field_235380_mt_).build();

   public AxeItem(IItemTier tier, float attackDamageIn, float attackSpeedIn, Item.Properties builder) {
      super(attackDamageIn, attackSpeedIn, tier, field_150917_d_, builder.addToolType(net.minecraftforge.common.ToolType.AXE, tier.getHarvestLevel()));
   }

   public float getDestroySpeed(ItemStack stack, BlockState state) {
      Material material = state.getMaterial();
      return field_234662_c_.contains(material) ? this.efficiency : super.getDestroySpeed(stack, state);
   }

   /**
    * Called when this item is used when targetting a Block
    */
   public ActionResultType onItemUse(ItemUseContext context) {
      World world = context.getWorld();
      BlockPos blockpos = context.getPos();
      BlockState blockstate = world.getBlockState(blockpos);
      BlockState block = blockstate.getToolModifiedState(world, blockpos, context.getPlayer(), context.getItem(), net.minecraftforge.common.ToolType.AXE);
      if (block != null) {
         PlayerEntity playerentity = context.getPlayer();
         world.playSound(playerentity, blockpos, SoundEvents.ITEM_AXE_STRIP, SoundCategory.BLOCKS, 1.0F, 1.0F);
         if (!world.isRemote) {
            world.setBlockState(blockpos, block, 11);
            if (playerentity != null) {
               context.getItem().damageItem(1, playerentity, (p_220040_1_) -> {
                  p_220040_1_.sendBreakAnimation(context.getHand());
               });
            }
         }

         return ActionResultType.func_233537_a_(world.isRemote);
      } else {
         return ActionResultType.PASS;
      }
   }

   @javax.annotation.Nullable
   public static BlockState getAxeStrippingState(BlockState originalState) {
      Block block = BLOCK_STRIPPING_MAP.get(originalState.getBlock());
      return block != null ? block.getDefaultState().with(RotatedPillarBlock.AXIS, originalState.get(RotatedPillarBlock.AXIS)) : null;
   }
}