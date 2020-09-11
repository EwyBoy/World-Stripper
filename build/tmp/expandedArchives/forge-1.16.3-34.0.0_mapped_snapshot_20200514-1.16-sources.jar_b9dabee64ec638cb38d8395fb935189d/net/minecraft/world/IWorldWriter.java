package net.minecraft.world;

import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;

public interface IWorldWriter {
   boolean func_241211_a_(BlockPos p_241211_1_, BlockState p_241211_2_, int p_241211_3_, int p_241211_4_);

   /**
    * Sets a block state into this world.Flags are as follows:
    * 1 will cause a block update.
    * 2 will send the change to clients.
    * 4 will prevent the block from being re-rendered.
    * 8 will force any re-renders to run on the main thread instead
    * 16 will prevent neighbor reactions (e.g. fences connecting, observers pulsing).
    * 32 will prevent neighbor reactions from spawning drops.
    * 64 will signify the block is being moved.
    * Flags can be OR-ed
    */
   default boolean setBlockState(BlockPos pos, BlockState newState, int flags) {
      return this.func_241211_a_(pos, newState, flags, 512);
   }

   boolean removeBlock(BlockPos pos, boolean isMoving);

   /**
    * Sets a block to air, but also plays the sound and particles and can spawn drops
    */
   default boolean destroyBlock(BlockPos pos, boolean dropBlock) {
      return this.destroyBlock(pos, dropBlock, (Entity)null);
   }

   default boolean destroyBlock(BlockPos p_225521_1_, boolean p_225521_2_, @Nullable Entity p_225521_3_) {
      return this.func_241212_a_(p_225521_1_, p_225521_2_, p_225521_3_, 512);
   }

   boolean func_241212_a_(BlockPos p_241212_1_, boolean p_241212_2_, @Nullable Entity p_241212_3_, int p_241212_4_);

   default boolean addEntity(Entity entityIn) {
      return false;
   }
}