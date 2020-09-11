package net.minecraft.entity.ai.brain.task;

import com.google.common.collect.ImmutableMap;
import java.util.Optional;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CropsBlock;
import net.minecraft.entity.ai.brain.memory.MemoryModuleStatus;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.memory.WalkTarget;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.BoneMealItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPosWrapper;
import net.minecraft.world.server.ServerWorld;

public class BoneMealCropsTask extends Task<VillagerEntity> {
   private long field_233990_b_;
   private long field_233991_c_;
   private int field_233992_d_;
   private Optional<BlockPos> field_233993_e_ = Optional.empty();

   public BoneMealCropsTask() {
      super(ImmutableMap.of(MemoryModuleType.LOOK_TARGET, MemoryModuleStatus.VALUE_ABSENT, MemoryModuleType.WALK_TARGET, MemoryModuleStatus.VALUE_ABSENT));
   }

   protected boolean shouldExecute(ServerWorld worldIn, VillagerEntity owner) {
      if (owner.ticksExisted % 10 == 0 && (this.field_233991_c_ == 0L || this.field_233991_c_ + 160L <= (long)owner.ticksExisted)) {
         if (owner.getVillagerInventory().count(Items.BONE_MEAL) <= 0) {
            return false;
         } else {
            this.field_233993_e_ = this.func_233997_b_(worldIn, owner);
            return this.field_233993_e_.isPresent();
         }
      } else {
         return false;
      }
   }

   protected boolean shouldContinueExecuting(ServerWorld worldIn, VillagerEntity entityIn, long gameTimeIn) {
      return this.field_233992_d_ < 80 && this.field_233993_e_.isPresent();
   }

   private Optional<BlockPos> func_233997_b_(ServerWorld p_233997_1_, VillagerEntity p_233997_2_) {
      BlockPos.Mutable blockpos$mutable = new BlockPos.Mutable();
      Optional<BlockPos> optional = Optional.empty();
      int i = 0;

      for(int j = -1; j <= 1; ++j) {
         for(int k = -1; k <= 1; ++k) {
            for(int l = -1; l <= 1; ++l) {
               blockpos$mutable.func_239621_a_(p_233997_2_.func_233580_cy_(), j, k, l);
               if (this.func_233996_a_(blockpos$mutable, p_233997_1_)) {
                  ++i;
                  if (p_233997_1_.rand.nextInt(i) == 0) {
                     optional = Optional.of(blockpos$mutable.toImmutable());
                  }
               }
            }
         }
      }

      return optional;
   }

   private boolean func_233996_a_(BlockPos p_233996_1_, ServerWorld p_233996_2_) {
      BlockState blockstate = p_233996_2_.getBlockState(p_233996_1_);
      Block block = blockstate.getBlock();
      return block instanceof CropsBlock && !((CropsBlock)block).isMaxAge(blockstate);
   }

   protected void startExecuting(ServerWorld worldIn, VillagerEntity entityIn, long gameTimeIn) {
      this.func_233994_a_(entityIn);
      entityIn.setItemStackToSlot(EquipmentSlotType.MAINHAND, new ItemStack(Items.BONE_MEAL));
      this.field_233990_b_ = gameTimeIn;
      this.field_233992_d_ = 0;
   }

   private void func_233994_a_(VillagerEntity p_233994_1_) {
      this.field_233993_e_.ifPresent((p_233995_1_) -> {
         BlockPosWrapper blockposwrapper = new BlockPosWrapper(p_233995_1_);
         p_233994_1_.getBrain().setMemory(MemoryModuleType.LOOK_TARGET, blockposwrapper);
         p_233994_1_.getBrain().setMemory(MemoryModuleType.WALK_TARGET, new WalkTarget(blockposwrapper, 0.5F, 1));
      });
   }

   protected void resetTask(ServerWorld worldIn, VillagerEntity entityIn, long gameTimeIn) {
      entityIn.setItemStackToSlot(EquipmentSlotType.MAINHAND, ItemStack.EMPTY);
      this.field_233991_c_ = (long)entityIn.ticksExisted;
   }

   protected void updateTask(ServerWorld worldIn, VillagerEntity owner, long gameTime) {
      BlockPos blockpos = this.field_233993_e_.get();
      if (gameTime >= this.field_233990_b_ && blockpos.withinDistance(owner.getPositionVec(), 1.0D)) {
         ItemStack itemstack = ItemStack.EMPTY;
         Inventory inventory = owner.getVillagerInventory();
         int i = inventory.getSizeInventory();

         for(int j = 0; j < i; ++j) {
            ItemStack itemstack1 = inventory.getStackInSlot(j);
            if (itemstack1.getItem() == Items.BONE_MEAL) {
               itemstack = itemstack1;
               break;
            }
         }

         if (!itemstack.isEmpty() && BoneMealItem.applyBonemeal(itemstack, worldIn, blockpos)) {
            worldIn.playEvent(2005, blockpos, 0);
            this.field_233993_e_ = this.func_233997_b_(worldIn, owner);
            this.func_233994_a_(owner);
            this.field_233990_b_ = gameTime + 40L;
         }

         ++this.field_233992_d_;
      }
   }
}