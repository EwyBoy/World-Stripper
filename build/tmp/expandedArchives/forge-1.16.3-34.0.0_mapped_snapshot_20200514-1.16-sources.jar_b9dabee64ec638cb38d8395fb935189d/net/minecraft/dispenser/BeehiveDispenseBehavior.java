package net.minecraft.dispenser;

import net.minecraft.block.BeehiveBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.DispenserBlock;
import net.minecraft.entity.IShearable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.BlockTags;
import net.minecraft.tileentity.BeehiveTileEntity;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class BeehiveDispenseBehavior extends OptionalDispenseBehavior {
   /**
    * Dispense the specified stack, play the dispense sound and spawn particles.
    */
   protected ItemStack dispenseStack(IBlockSource source, ItemStack stack) {
      World world = source.getWorld();
      if (!world.isRemote()) {
         BlockPos blockpos = source.getBlockPos().offset(source.getBlockState().get(DispenserBlock.FACING));
         this.func_239796_a_(func_239797_a_((ServerWorld)world, blockpos) || func_239798_b_((ServerWorld)world, blockpos));
         if (this.func_239795_a_() && stack.attemptDamageItem(1, world.getRandom(), (ServerPlayerEntity)null)) {
            stack.setCount(0);
         }
      }

      return stack;
   }

   private static boolean func_239797_a_(ServerWorld p_239797_0_, BlockPos p_239797_1_) {
      BlockState blockstate = p_239797_0_.getBlockState(p_239797_1_);
      if (blockstate.func_235714_a_(BlockTags.BEEHIVES)) {
         int i = blockstate.get(BeehiveBlock.HONEY_LEVEL);
         if (i >= 5) {
            p_239797_0_.playSound((PlayerEntity)null, p_239797_1_, SoundEvents.BLOCK_BEEHIVE_SHEAR, SoundCategory.BLOCKS, 1.0F, 1.0F);
            BeehiveBlock.dropHoneyComb(p_239797_0_, p_239797_1_);
            ((BeehiveBlock)blockstate.getBlock()).takeHoney(p_239797_0_, blockstate, p_239797_1_, (PlayerEntity)null, BeehiveTileEntity.State.BEE_RELEASED);
            return true;
         }
      }

      return false;
   }

   private static boolean func_239798_b_(ServerWorld p_239798_0_, BlockPos p_239798_1_) {
      for(LivingEntity livingentity : p_239798_0_.getEntitiesWithinAABB(LivingEntity.class, new AxisAlignedBB(p_239798_1_), EntityPredicates.NOT_SPECTATING)) {
         if (livingentity instanceof IShearable) {
            IShearable ishearable = (IShearable)livingentity;
            if (ishearable.func_230262_K__()) {
               ishearable.func_230263_a_(SoundCategory.BLOCKS);
               return true;
            }
         }
      }

      return false;
   }
}