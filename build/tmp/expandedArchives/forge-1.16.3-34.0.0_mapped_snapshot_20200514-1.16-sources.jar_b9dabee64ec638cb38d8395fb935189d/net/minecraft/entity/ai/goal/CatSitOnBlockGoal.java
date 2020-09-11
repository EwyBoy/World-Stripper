package net.minecraft.entity.ai.goal;

import net.minecraft.block.BedBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FurnaceBlock;
import net.minecraft.entity.passive.CatEntity;
import net.minecraft.state.properties.BedPart;
import net.minecraft.tags.BlockTags;
import net.minecraft.tileentity.ChestTileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;

public class CatSitOnBlockGoal extends MoveToBlockGoal {
   private final CatEntity field_220728_g;

   public CatSitOnBlockGoal(CatEntity p_i50330_1_, double p_i50330_2_) {
      super(p_i50330_1_, p_i50330_2_, 8);
      this.field_220728_g = p_i50330_1_;
   }

   /**
    * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
    * method as well.
    */
   public boolean shouldExecute() {
      return this.field_220728_g.isTamed() && !this.field_220728_g.func_233685_eM_() && super.shouldExecute();
   }

   /**
    * Execute a one shot task or start executing a continuous task
    */
   public void startExecuting() {
      super.startExecuting();
      this.field_220728_g.func_233686_v_(false);
   }

   /**
    * Reset the task's internal state. Called when this task is interrupted by another one
    */
   public void resetTask() {
      super.resetTask();
      this.field_220728_g.func_233686_v_(false);
   }

   /**
    * Keep ticking a continuous task that has already been started
    */
   public void tick() {
      super.tick();
      this.field_220728_g.func_233686_v_(this.getIsAboveDestination());
   }

   /**
    * Return true to set given position as destination
    */
   protected boolean shouldMoveTo(IWorldReader worldIn, BlockPos pos) {
      if (!worldIn.isAirBlock(pos.up())) {
         return false;
      } else {
         BlockState blockstate = worldIn.getBlockState(pos);
         if (blockstate.isIn(Blocks.CHEST)) {
            return ChestTileEntity.getPlayersUsing(worldIn, pos) < 1;
         } else {
            return blockstate.isIn(Blocks.FURNACE) && blockstate.get(FurnaceBlock.LIT) ? true : blockstate.func_235715_a_(BlockTags.BEDS, (p_234025_0_) -> {
               return p_234025_0_.<BedPart>func_235903_d_(BedBlock.PART).map((p_234026_0_) -> {
                  return p_234026_0_ != BedPart.HEAD;
               }).orElse(true);
            });
         }
      }
   }
}