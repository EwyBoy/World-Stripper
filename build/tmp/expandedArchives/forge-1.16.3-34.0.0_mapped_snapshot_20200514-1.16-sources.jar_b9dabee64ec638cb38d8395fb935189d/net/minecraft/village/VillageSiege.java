package net.minecraft.village;

import javax.annotation.Nullable;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.spawner.ISpecialSpawner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class VillageSiege implements ISpecialSpawner {
   private static final Logger field_242320_a = LogManager.getLogger();
   private boolean hasSetupSiege;
   private VillageSiege.State siegeState = VillageSiege.State.SIEGE_DONE;
   private int siegeCount;
   private int nextSpawnTime;
   private int spawnX;
   private int spawnY;
   private int spawnZ;

   public int func_230253_a_(ServerWorld p_230253_1_, boolean p_230253_2_, boolean p_230253_3_) {
      if (!p_230253_1_.isDaytime() && p_230253_2_) {
         float f = p_230253_1_.func_242415_f(0.0F);
         if ((double)f == 0.5D) {
            this.siegeState = p_230253_1_.rand.nextInt(10) == 0 ? VillageSiege.State.SIEGE_TONIGHT : VillageSiege.State.SIEGE_DONE;
         }

         if (this.siegeState == VillageSiege.State.SIEGE_DONE) {
            return 0;
         } else {
            if (!this.hasSetupSiege) {
               if (!this.trySetupSiege(p_230253_1_)) {
                  return 0;
               }

               this.hasSetupSiege = true;
            }

            if (this.nextSpawnTime > 0) {
               --this.nextSpawnTime;
               return 0;
            } else {
               this.nextSpawnTime = 2;
               if (this.siegeCount > 0) {
                  this.spawnZombie(p_230253_1_);
                  --this.siegeCount;
               } else {
                  this.siegeState = VillageSiege.State.SIEGE_DONE;
               }

               return 1;
            }
         }
      } else {
         this.siegeState = VillageSiege.State.SIEGE_DONE;
         this.hasSetupSiege = false;
         return 0;
      }
   }

   private boolean trySetupSiege(ServerWorld p_75529_1_) {
      for(PlayerEntity playerentity : p_75529_1_.getPlayers()) {
         if (!playerentity.isSpectator()) {
            BlockPos blockpos = playerentity.func_233580_cy_();
            if (p_75529_1_.isVillage(blockpos) && p_75529_1_.getBiome(blockpos).getCategory() != Biome.Category.MUSHROOM) {
               for(int i = 0; i < 10; ++i) {
                  float f = p_75529_1_.rand.nextFloat() * ((float)Math.PI * 2F);
                  this.spawnX = blockpos.getX() + MathHelper.floor(MathHelper.cos(f) * 32.0F);
                  this.spawnY = blockpos.getY();
                  this.spawnZ = blockpos.getZ() + MathHelper.floor(MathHelper.sin(f) * 32.0F);
                  Vector3d siegeLocation = this.findRandomSpawnPos(p_75529_1_, new BlockPos(this.spawnX, this.spawnY, this.spawnZ));
                  if (siegeLocation != null) {
                     if (net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.event.village.VillageSiegeEvent(this, p_75529_1_, playerentity, siegeLocation))) return false;
                     this.nextSpawnTime = 0;
                     this.siegeCount = 20;
                     break;
                  }
               }

               return true;
            }
         }
      }

      return false;
   }

   private void spawnZombie(ServerWorld p_75530_1_) {
      Vector3d vector3d = this.findRandomSpawnPos(p_75530_1_, new BlockPos(this.spawnX, this.spawnY, this.spawnZ));
      if (vector3d != null) {
         ZombieEntity zombieentity;
         try {
            zombieentity = EntityType.ZOMBIE.create(p_75530_1_); //Forge: Direct Initialization is deprecated, use EntityType.
            zombieentity.onInitialSpawn(p_75530_1_, p_75530_1_.getDifficultyForLocation(zombieentity.func_233580_cy_()), SpawnReason.EVENT, (ILivingEntityData)null, (CompoundNBT)null);
         } catch (Exception exception) {
            field_242320_a.warn("Failed to create zombie for village siege at {}", vector3d, exception);
            return;
         }

         zombieentity.setLocationAndAngles(vector3d.x, vector3d.y, vector3d.z, p_75530_1_.rand.nextFloat() * 360.0F, 0.0F);
         p_75530_1_.func_242417_l(zombieentity);
      }
   }

   @Nullable
   private Vector3d findRandomSpawnPos(ServerWorld p_225476_1_, BlockPos p_225476_2_) {
      for(int i = 0; i < 10; ++i) {
         int j = p_225476_2_.getX() + p_225476_1_.rand.nextInt(16) - 8;
         int k = p_225476_2_.getZ() + p_225476_1_.rand.nextInt(16) - 8;
         int l = p_225476_1_.getHeight(Heightmap.Type.WORLD_SURFACE, j, k);
         BlockPos blockpos = new BlockPos(j, l, k);
         if (p_225476_1_.isVillage(blockpos) && MonsterEntity.canMonsterSpawnInLight(EntityType.ZOMBIE, p_225476_1_, SpawnReason.EVENT, blockpos, p_225476_1_.rand)) {
            return Vector3d.func_237492_c_(blockpos);
         }
      }

      return null;
   }

   static enum State {
      SIEGE_CAN_ACTIVATE,
      SIEGE_TONIGHT,
      SIEGE_DONE;
   }
}