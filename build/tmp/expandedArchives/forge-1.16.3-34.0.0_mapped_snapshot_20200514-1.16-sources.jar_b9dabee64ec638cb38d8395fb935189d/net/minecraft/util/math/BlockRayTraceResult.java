package net.minecraft.util.math;

import net.minecraft.util.Direction;
import net.minecraft.util.math.vector.Vector3d;

public class BlockRayTraceResult extends RayTraceResult {
   private final Direction face;
   private final BlockPos pos;
   private final boolean isMiss;
   private final boolean inside;

   /**
    * Creates a new BlockRayTraceResult marked as a miss.
    */
   public static BlockRayTraceResult createMiss(Vector3d hitVec, Direction faceIn, BlockPos posIn) {
      return new BlockRayTraceResult(true, hitVec, faceIn, posIn, false);
   }

   public BlockRayTraceResult(Vector3d hitVec, Direction faceIn, BlockPos posIn, boolean isInside) {
      this(false, hitVec, faceIn, posIn, isInside);
   }

   private BlockRayTraceResult(boolean isMissIn, Vector3d hitVec, Direction faceIn, BlockPos posIn, boolean isInside) {
      super(hitVec);
      this.isMiss = isMissIn;
      this.face = faceIn;
      this.pos = posIn;
      this.inside = isInside;
   }

   /**
    * Creates a new BlockRayTraceResult, with the clicked face replaced with the given one
    */
   public BlockRayTraceResult withFace(Direction newFace) {
      return new BlockRayTraceResult(this.isMiss, this.hitResult, newFace, this.pos, this.inside);
   }

   public BlockRayTraceResult func_237485_a_(BlockPos p_237485_1_) {
      return new BlockRayTraceResult(this.isMiss, this.hitResult, this.face, p_237485_1_, this.inside);
   }

   public BlockPos getPos() {
      return this.pos;
   }

   /**
    * Gets the face of the block that was clicked
    */
   public Direction getFace() {
      return this.face;
   }

   public RayTraceResult.Type getType() {
      return this.isMiss ? RayTraceResult.Type.MISS : RayTraceResult.Type.BLOCK;
   }

   /**
    * True if the player's head is inside of a block (used by scaffolding)
    */
   public boolean isInside() {
      return this.inside;
   }
}