package net.minecraft.world.storage;

import net.minecraft.util.math.BlockPos;

public interface ISpawnWorldInfo extends IWorldInfo {
   /**
    * Set the x spawn position to the passed in value
    */
   void setSpawnX(int x);

   /**
    * Sets the y spawn position
    */
   void setSpawnY(int y);

   /**
    * Set the z spawn position to the passed in value
    */
   void setSpawnZ(int z);

   void func_241859_a(float p_241859_1_);

   default void setSpawn(BlockPos spawnPoint, float p_176143_2_) {
      this.setSpawnX(spawnPoint.getX());
      this.setSpawnY(spawnPoint.getY());
      this.setSpawnZ(spawnPoint.getZ());
      this.func_241859_a(p_176143_2_);
   }
}