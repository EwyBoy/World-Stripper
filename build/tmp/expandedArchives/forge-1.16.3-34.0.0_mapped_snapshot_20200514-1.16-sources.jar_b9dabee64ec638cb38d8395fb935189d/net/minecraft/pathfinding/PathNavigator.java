package net.minecraft.pathfinding;

import com.google.common.collect.ImmutableSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.network.DebugPacketSender;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.world.Region;
import net.minecraft.world.World;

public abstract class PathNavigator {
   protected final MobEntity entity;
   protected final World world;
   @Nullable
   protected Path currentPath;
   protected double speed;
   protected int totalTicks;
   protected int ticksAtLastPos;
   protected Vector3d lastPosCheck = Vector3d.ZERO;
   protected Vector3i timeoutCachedNode = Vector3i.NULL_VECTOR;
   protected long timeoutTimer;
   protected long lastTimeoutCheck;
   protected double timeoutLimit;
   protected float maxDistanceToWaypoint = 0.5F;
   protected boolean tryUpdatePath;
   protected long lastTimeUpdated;
   protected NodeProcessor nodeProcessor;
   private BlockPos targetPos;
   private int field_225468_r;
   private float field_226334_s_ = 1.0F;
   private final PathFinder pathFinder;
   private boolean field_244426_t;

   public PathNavigator(MobEntity entityIn, World worldIn) {
      this.entity = entityIn;
      this.world = worldIn;
      int i = MathHelper.floor(entityIn.func_233637_b_(Attributes.field_233819_b_) * 16.0D);
      this.pathFinder = this.getPathFinder(i);
   }

   public void resetRangeMultiplier() {
      this.field_226334_s_ = 1.0F;
   }

   public void setRangeMultiplier(float p_226335_1_) {
      this.field_226334_s_ = p_226335_1_;
   }

   public BlockPos getTargetPos() {
      return this.targetPos;
   }

   protected abstract PathFinder getPathFinder(int p_179679_1_);

   /**
    * Sets the speed
    */
   public void setSpeed(double speedIn) {
      this.speed = speedIn;
   }

   /**
    * Returns true if path can be changed by {@link net.minecraft.pathfinding.PathNavigate#onUpdateNavigation()
    * onUpdateNavigation()}
    */
   public boolean canUpdatePathOnTimeout() {
      return this.tryUpdatePath;
   }

   public void updatePath() {
      if (this.world.getGameTime() - this.lastTimeUpdated > 20L) {
         if (this.targetPos != null) {
            this.currentPath = null;
            this.currentPath = this.getPathToPos(this.targetPos, this.field_225468_r);
            this.lastTimeUpdated = this.world.getGameTime();
            this.tryUpdatePath = false;
         }
      } else {
         this.tryUpdatePath = true;
      }

   }

   @Nullable
   public final Path getPathToPos(double p_225466_1_, double p_225466_3_, double p_225466_5_, int p_225466_7_) {
      return this.getPathToPos(new BlockPos(p_225466_1_, p_225466_3_, p_225466_5_), p_225466_7_);
   }

   @Nullable
   public Path func_225463_a(Stream<BlockPos> p_225463_1_, int p_225463_2_) {
      return this.func_225464_a(p_225463_1_.collect(Collectors.toSet()), 8, false, p_225463_2_);
   }

   @Nullable
   public Path func_241390_a_(Set<BlockPos> p_241390_1_, int p_241390_2_) {
      return this.func_225464_a(p_241390_1_, 8, false, p_241390_2_);
   }

   /**
    * Returns path to given BlockPos
    */
   @Nullable
   public Path getPathToPos(BlockPos pos, int p_179680_2_) {
      return this.func_225464_a(ImmutableSet.of(pos), 8, false, p_179680_2_);
   }

   /**
    * Returns the path to the given EntityLiving. Args : entity
    */
   @Nullable
   public Path getPathToEntity(Entity entityIn, int p_75494_2_) {
      return this.func_225464_a(ImmutableSet.of(entityIn.func_233580_cy_()), 16, true, p_75494_2_);
   }

   @Nullable
   protected Path func_225464_a(Set<BlockPos> p_225464_1_, int p_225464_2_, boolean p_225464_3_, int p_225464_4_) {
      if (p_225464_1_.isEmpty()) {
         return null;
      } else if (this.entity.getPosY() < 0.0D) {
         return null;
      } else if (!this.canNavigate()) {
         return null;
      } else if (this.currentPath != null && !this.currentPath.isFinished() && p_225464_1_.contains(this.targetPos)) {
         return this.currentPath;
      } else {
         this.world.getProfiler().startSection("pathfind");
         float f = (float)this.entity.func_233637_b_(Attributes.field_233819_b_);
         BlockPos blockpos = p_225464_3_ ? this.entity.func_233580_cy_().up() : this.entity.func_233580_cy_();
         int i = (int)(f + (float)p_225464_2_);
         Region region = new Region(this.world, blockpos.add(-i, -i, -i), blockpos.add(i, i, i));
         Path path = this.pathFinder.func_227478_a_(region, this.entity, p_225464_1_, f, p_225464_4_, this.field_226334_s_);
         this.world.getProfiler().endSection();
         if (path != null && path.getTarget() != null) {
            this.targetPos = path.getTarget();
            this.field_225468_r = p_225464_4_;
            this.func_234113_e_();
         }

         return path;
      }
   }

   /**
    * Try to find and set a path to XYZ. Returns true if successful. Args : x, y, z, speed
    */
   public boolean tryMoveToXYZ(double x, double y, double z, double speedIn) {
      return this.setPath(this.getPathToPos(x, y, z, 1), speedIn);
   }

   /**
    * Try to find and set a path to EntityLiving. Returns true if successful. Args : entity, speed
    */
   public boolean tryMoveToEntityLiving(Entity entityIn, double speedIn) {
      Path path = this.getPathToEntity(entityIn, 1);
      return path != null && this.setPath(path, speedIn);
   }

   /**
    * Sets a new path. If it's diferent from the old path. Checks to adjust path for sun avoiding, and stores start
    * coords. Args : path, speed
    */
   public boolean setPath(@Nullable Path pathentityIn, double speedIn) {
      if (pathentityIn == null) {
         this.currentPath = null;
         return false;
      } else {
         if (!pathentityIn.isSamePath(this.currentPath)) {
            this.currentPath = pathentityIn;
         }

         if (this.noPath()) {
            return false;
         } else {
            this.trimPath();
            if (this.currentPath.getCurrentPathLength() <= 0) {
               return false;
            } else {
               this.speed = speedIn;
               Vector3d vector3d = this.getEntityPosition();
               this.ticksAtLastPos = this.totalTicks;
               this.lastPosCheck = vector3d;
               return true;
            }
         }
      }
   }

   /**
    * gets the actively used PathEntity
    */
   @Nullable
   public Path getPath() {
      return this.currentPath;
   }

   public void tick() {
      ++this.totalTicks;
      if (this.tryUpdatePath) {
         this.updatePath();
      }

      if (!this.noPath()) {
         if (this.canNavigate()) {
            this.pathFollow();
         } else if (this.currentPath != null && !this.currentPath.isFinished()) {
            Vector3d vector3d = this.getEntityPosition();
            Vector3d vector3d1 = this.currentPath.getPosition(this.entity);
            if (vector3d.y > vector3d1.y && !this.entity.func_233570_aj_() && MathHelper.floor(vector3d.x) == MathHelper.floor(vector3d1.x) && MathHelper.floor(vector3d.z) == MathHelper.floor(vector3d1.z)) {
               this.currentPath.incrementPathIndex();
            }
         }

         DebugPacketSender.sendPath(this.world, this.entity, this.currentPath, this.maxDistanceToWaypoint);
         if (!this.noPath()) {
            Vector3d vector3d2 = this.currentPath.getPosition(this.entity);
            BlockPos blockpos = new BlockPos(vector3d2);
            this.entity.getMoveHelper().setMoveTo(vector3d2.x, this.world.getBlockState(blockpos.down()).isAir() ? vector3d2.y : WalkNodeProcessor.getGroundY(this.world, blockpos), vector3d2.z, this.speed);
         }
      }
   }

   protected void pathFollow() {
      Vector3d vector3d = this.getEntityPosition();
      this.maxDistanceToWaypoint = this.entity.getWidth() > 0.75F ? this.entity.getWidth() / 2.0F : 0.75F - this.entity.getWidth() / 2.0F;
      Vector3i vector3i = this.currentPath.func_242948_g();
      double d0 = Math.abs(this.entity.getPosX() - ((double)vector3i.getX() + 0.5D));
      double d1 = Math.abs(this.entity.getPosY() - (double)vector3i.getY());
      double d2 = Math.abs(this.entity.getPosZ() - ((double)vector3i.getZ() + 0.5D));
      boolean flag = d0 < (double)this.maxDistanceToWaypoint && d2 < (double)this.maxDistanceToWaypoint && d1 < 1.0D;
      if (flag || this.entity.func_233660_b_(this.currentPath.func_237225_h_().nodeType) && this.func_234112_b_(vector3d)) {
         this.currentPath.incrementPathIndex();
      }

      this.checkForStuck(vector3d);
   }

   private boolean func_234112_b_(Vector3d p_234112_1_) {
      if (this.currentPath.getCurrentPathIndex() + 1 >= this.currentPath.getCurrentPathLength()) {
         return false;
      } else {
         Vector3d vector3d = Vector3d.func_237492_c_(this.currentPath.func_242948_g());
         if (!p_234112_1_.func_237488_a_(vector3d, 2.0D)) {
            return false;
         } else {
            Vector3d vector3d1 = Vector3d.func_237492_c_(this.currentPath.func_242947_d(this.currentPath.getCurrentPathIndex() + 1));
            Vector3d vector3d2 = vector3d1.subtract(vector3d);
            Vector3d vector3d3 = p_234112_1_.subtract(vector3d);
            return vector3d2.dotProduct(vector3d3) > 0.0D;
         }
      }
   }

   /**
    * Checks if entity haven't been moved when last checked and if so, clears current {@link
    * net.minecraft.pathfinding.PathEntity}
    */
   protected void checkForStuck(Vector3d positionVec3) {
      if (this.totalTicks - this.ticksAtLastPos > 100) {
         if (positionVec3.squareDistanceTo(this.lastPosCheck) < 2.25D) {
            this.field_244426_t = true;
            this.clearPath();
         } else {
            this.field_244426_t = false;
         }

         this.ticksAtLastPos = this.totalTicks;
         this.lastPosCheck = positionVec3;
      }

      if (this.currentPath != null && !this.currentPath.isFinished()) {
         Vector3i vector3i = this.currentPath.func_242948_g();
         if (vector3i.equals(this.timeoutCachedNode)) {
            this.timeoutTimer += Util.milliTime() - this.lastTimeoutCheck;
         } else {
            this.timeoutCachedNode = vector3i;
            double d0 = positionVec3.distanceTo(Vector3d.func_237492_c_(this.timeoutCachedNode));
            this.timeoutLimit = this.entity.getAIMoveSpeed() > 0.0F ? d0 / (double)this.entity.getAIMoveSpeed() * 1000.0D : 0.0D;
         }

         if (this.timeoutLimit > 0.0D && (double)this.timeoutTimer > this.timeoutLimit * 3.0D) {
            this.func_244427_e();
         }

         this.lastTimeoutCheck = Util.milliTime();
      }

   }

   private void func_244427_e() {
      this.func_234113_e_();
      this.clearPath();
   }

   private void func_234113_e_() {
      this.timeoutCachedNode = Vector3i.NULL_VECTOR;
      this.timeoutTimer = 0L;
      this.timeoutLimit = 0.0D;
      this.field_244426_t = false;
   }

   /**
    * If null path or reached the end
    */
   public boolean noPath() {
      return this.currentPath == null || this.currentPath.isFinished();
   }

   public boolean func_226337_n_() {
      return !this.noPath();
   }

   /**
    * sets active PathEntity to null
    */
   public void clearPath() {
      this.currentPath = null;
   }

   protected abstract Vector3d getEntityPosition();

   /**
    * If on ground or swimming and can swim
    */
   protected abstract boolean canNavigate();

   /**
    * Returns true if the entity is in water or lava, false otherwise
    */
   protected boolean isInLiquid() {
      return this.entity.isInWaterOrBubbleColumn() || this.entity.isInLava();
   }

   /**
    * Trims path data from the end to the first sun covered block
    */
   protected void trimPath() {
      if (this.currentPath != null) {
         for(int i = 0; i < this.currentPath.getCurrentPathLength(); ++i) {
            PathPoint pathpoint = this.currentPath.getPathPointFromIndex(i);
            PathPoint pathpoint1 = i + 1 < this.currentPath.getCurrentPathLength() ? this.currentPath.getPathPointFromIndex(i + 1) : null;
            BlockState blockstate = this.world.getBlockState(new BlockPos(pathpoint.x, pathpoint.y, pathpoint.z));
            if (blockstate.isIn(Blocks.CAULDRON)) {
               this.currentPath.setPoint(i, pathpoint.cloneMove(pathpoint.x, pathpoint.y + 1, pathpoint.z));
               if (pathpoint1 != null && pathpoint.y >= pathpoint1.y) {
                  this.currentPath.setPoint(i + 1, pathpoint.cloneMove(pathpoint1.x, pathpoint.y + 1, pathpoint1.z));
               }
            }
         }

      }
   }

   /**
    * Checks if the specified entity can safely walk to the specified location.
    */
   protected abstract boolean isDirectPathBetweenPoints(Vector3d posVec31, Vector3d posVec32, int sizeX, int sizeY, int sizeZ);

   public boolean canEntityStandOnPos(BlockPos pos) {
      BlockPos blockpos = pos.down();
      return this.world.getBlockState(blockpos).isOpaqueCube(this.world, blockpos);
   }

   public NodeProcessor getNodeProcessor() {
      return this.nodeProcessor;
   }

   public void setCanSwim(boolean canSwim) {
      this.nodeProcessor.setCanSwim(canSwim);
   }

   public boolean getCanSwim() {
      return this.nodeProcessor.getCanSwim();
   }

   public void func_220970_c(BlockPos p_220970_1_) {
      if (this.currentPath != null && !this.currentPath.isFinished() && this.currentPath.getCurrentPathLength() != 0) {
         PathPoint pathpoint = this.currentPath.getFinalPathPoint();
         Vector3d vector3d = new Vector3d(((double)pathpoint.x + this.entity.getPosX()) / 2.0D, ((double)pathpoint.y + this.entity.getPosY()) / 2.0D, ((double)pathpoint.z + this.entity.getPosZ()) / 2.0D);
         if (p_220970_1_.withinDistance(vector3d, (double)(this.currentPath.getCurrentPathLength() - this.currentPath.getCurrentPathIndex()))) {
            this.updatePath();
         }

      }
   }

   public boolean func_244428_t() {
      return this.field_244426_t;
   }
}