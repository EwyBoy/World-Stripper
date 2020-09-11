package net.minecraft.item;

import net.minecraft.block.AbstractFireBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.CampfireBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class FireChargeItem extends Item {
   public FireChargeItem(Item.Properties builder) {
      super(builder);
   }

   /**
    * Called when this item is used when targetting a Block
    */
   public ActionResultType onItemUse(ItemUseContext context) {
      World world = context.getWorld();
      BlockPos blockpos = context.getPos();
      BlockState blockstate = world.getBlockState(blockpos);
      boolean flag = false;
      if (CampfireBlock.func_241470_h_(blockstate)) {
         this.playUseSound(world, blockpos);
         world.setBlockState(blockpos, blockstate.with(CampfireBlock.LIT, Boolean.valueOf(true)));
         flag = true;
      } else {
         blockpos = blockpos.offset(context.getFace());
         if (AbstractFireBlock.func_241465_a_(world, blockpos, context.getPlacementHorizontalFacing())) {
            this.playUseSound(world, blockpos);
            world.setBlockState(blockpos, AbstractFireBlock.func_235326_a_(world, blockpos));
            flag = true;
         }
      }

      if (flag) {
         context.getItem().shrink(1);
         return ActionResultType.func_233537_a_(world.isRemote);
      } else {
         return ActionResultType.FAIL;
      }
   }

   private void playUseSound(World worldIn, BlockPos pos) {
      worldIn.playSound((PlayerEntity)null, pos, SoundEvents.ITEM_FIRECHARGE_USE, SoundCategory.BLOCKS, 1.0F, (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F);
   }
}