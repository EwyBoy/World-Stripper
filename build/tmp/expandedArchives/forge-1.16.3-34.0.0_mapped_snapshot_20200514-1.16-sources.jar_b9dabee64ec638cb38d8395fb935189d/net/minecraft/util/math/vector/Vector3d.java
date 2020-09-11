package net.minecraft.util.math.vector;

import java.util.EnumSet;
import net.minecraft.dispenser.IPosition;
import net.minecraft.util.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class Vector3d implements IPosition {
   public static final Vector3d ZERO = new Vector3d(0.0D, 0.0D, 0.0D);
   public final double x;
   public final double y;
   public final double z;

   @OnlyIn(Dist.CLIENT)
   public static Vector3d func_237487_a_(int p_237487_0_) {
      double d0 = (double)(p_237487_0_ >> 16 & 255) / 255.0D;
      double d1 = (double)(p_237487_0_ >> 8 & 255) / 255.0D;
      double d2 = (double)(p_237487_0_ & 255) / 255.0D;
      return new Vector3d(d0, d1, d2);
   }

   public static Vector3d func_237489_a_(Vector3i p_237489_0_) {
      return new Vector3d((double)p_237489_0_.getX() + 0.5D, (double)p_237489_0_.getY() + 0.5D, (double)p_237489_0_.getZ() + 0.5D);
   }

   public static Vector3d func_237491_b_(Vector3i p_237491_0_) {
      return new Vector3d((double)p_237491_0_.getX(), (double)p_237491_0_.getY(), (double)p_237491_0_.getZ());
   }

   public static Vector3d func_237492_c_(Vector3i p_237492_0_) {
      return new Vector3d((double)p_237492_0_.getX() + 0.5D, (double)p_237492_0_.getY(), (double)p_237492_0_.getZ() + 0.5D);
   }

   public static Vector3d func_237490_a_(Vector3i p_237490_0_, double p_237490_1_) {
      return new Vector3d((double)p_237490_0_.getX() + 0.5D, (double)p_237490_0_.getY() + p_237490_1_, (double)p_237490_0_.getZ() + 0.5D);
   }

   public Vector3d(double xIn, double yIn, double zIn) {
      this.x = xIn;
      this.y = yIn;
      this.z = zIn;
   }

   public Vector3d(Vector3f p_i225900_1_) {
      this((double)p_i225900_1_.getX(), (double)p_i225900_1_.getY(), (double)p_i225900_1_.getZ());
   }

   /**
    * Returns a new vector with the result of the specified vector minus this.
    */
   public Vector3d subtractReverse(Vector3d vec) {
      return new Vector3d(vec.x - this.x, vec.y - this.y, vec.z - this.z);
   }

   /**
    * Normalizes the vector to a length of 1 (except if it is the zero vector)
    */
   public Vector3d normalize() {
      double d0 = (double)MathHelper.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
      return d0 < 1.0E-4D ? ZERO : new Vector3d(this.x / d0, this.y / d0, this.z / d0);
   }

   public double dotProduct(Vector3d vec) {
      return this.x * vec.x + this.y * vec.y + this.z * vec.z;
   }

   /**
    * Returns a new vector with the result of this vector x the specified vector.
    */
   public Vector3d crossProduct(Vector3d vec) {
      return new Vector3d(this.y * vec.z - this.z * vec.y, this.z * vec.x - this.x * vec.z, this.x * vec.y - this.y * vec.x);
   }

   public Vector3d subtract(Vector3d vec) {
      return this.subtract(vec.x, vec.y, vec.z);
   }

   public Vector3d subtract(double x, double y, double z) {
      return this.add(-x, -y, -z);
   }

   public Vector3d add(Vector3d vec) {
      return this.add(vec.x, vec.y, vec.z);
   }

   /**
    * Adds the specified x,y,z vector components to this vector and returns the resulting vector. Does not change this
    * vector.
    */
   public Vector3d add(double x, double y, double z) {
      return new Vector3d(this.x + x, this.y + y, this.z + z);
   }

   public boolean func_237488_a_(IPosition p_237488_1_, double p_237488_2_) {
      return this.squareDistanceTo(p_237488_1_.getX(), p_237488_1_.getY(), p_237488_1_.getZ()) < p_237488_2_ * p_237488_2_;
   }

   /**
    * Euclidean distance between this and the specified vector, returned as double.
    */
   public double distanceTo(Vector3d vec) {
      double d0 = vec.x - this.x;
      double d1 = vec.y - this.y;
      double d2 = vec.z - this.z;
      return (double)MathHelper.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
   }

   /**
    * The square of the Euclidean distance between this and the specified vector.
    */
   public double squareDistanceTo(Vector3d vec) {
      double d0 = vec.x - this.x;
      double d1 = vec.y - this.y;
      double d2 = vec.z - this.z;
      return d0 * d0 + d1 * d1 + d2 * d2;
   }

   public double squareDistanceTo(double xIn, double yIn, double zIn) {
      double d0 = xIn - this.x;
      double d1 = yIn - this.y;
      double d2 = zIn - this.z;
      return d0 * d0 + d1 * d1 + d2 * d2;
   }

   public Vector3d scale(double factor) {
      return this.mul(factor, factor, factor);
   }

   public Vector3d inverse() {
      return this.scale(-1.0D);
   }

   public Vector3d mul(Vector3d p_216369_1_) {
      return this.mul(p_216369_1_.x, p_216369_1_.y, p_216369_1_.z);
   }

   public Vector3d mul(double factorX, double factorY, double factorZ) {
      return new Vector3d(this.x * factorX, this.y * factorY, this.z * factorZ);
   }

   /**
    * Returns the length of the vector.
    */
   public double length() {
      return (double)MathHelper.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
   }

   public double lengthSquared() {
      return this.x * this.x + this.y * this.y + this.z * this.z;
   }

   public boolean equals(Object p_equals_1_) {
      if (this == p_equals_1_) {
         return true;
      } else if (!(p_equals_1_ instanceof Vector3d)) {
         return false;
      } else {
         Vector3d vector3d = (Vector3d)p_equals_1_;
         if (Double.compare(vector3d.x, this.x) != 0) {
            return false;
         } else if (Double.compare(vector3d.y, this.y) != 0) {
            return false;
         } else {
            return Double.compare(vector3d.z, this.z) == 0;
         }
      }
   }

   public int hashCode() {
      long j = Double.doubleToLongBits(this.x);
      int i = (int)(j ^ j >>> 32);
      j = Double.doubleToLongBits(this.y);
      i = 31 * i + (int)(j ^ j >>> 32);
      j = Double.doubleToLongBits(this.z);
      return 31 * i + (int)(j ^ j >>> 32);
   }

   public String toString() {
      return "(" + this.x + ", " + this.y + ", " + this.z + ")";
   }

   public Vector3d rotatePitch(float pitch) {
      float f = MathHelper.cos(pitch);
      float f1 = MathHelper.sin(pitch);
      double d0 = this.x;
      double d1 = this.y * (double)f + this.z * (double)f1;
      double d2 = this.z * (double)f - this.y * (double)f1;
      return new Vector3d(d0, d1, d2);
   }

   public Vector3d rotateYaw(float yaw) {
      float f = MathHelper.cos(yaw);
      float f1 = MathHelper.sin(yaw);
      double d0 = this.x * (double)f + this.z * (double)f1;
      double d1 = this.y;
      double d2 = this.z * (double)f - this.x * (double)f1;
      return new Vector3d(d0, d1, d2);
   }

   @OnlyIn(Dist.CLIENT)
   public Vector3d func_242988_c(float p_242988_1_) {
      float f = MathHelper.cos(p_242988_1_);
      float f1 = MathHelper.sin(p_242988_1_);
      double d0 = this.x * (double)f + this.y * (double)f1;
      double d1 = this.y * (double)f - this.x * (double)f1;
      double d2 = this.z;
      return new Vector3d(d0, d1, d2);
   }

   /**
    * returns a Vec3d from given pitch and yaw degrees as Vec2f
    */
   public static Vector3d fromPitchYaw(Vector2f p_189984_0_) {
      return fromPitchYaw(p_189984_0_.x, p_189984_0_.y);
   }

   /**
    * returns a Vec3d from given pitch and yaw degrees
    */
   public static Vector3d fromPitchYaw(float pitch, float yaw) {
      float f = MathHelper.cos(-yaw * ((float)Math.PI / 180F) - (float)Math.PI);
      float f1 = MathHelper.sin(-yaw * ((float)Math.PI / 180F) - (float)Math.PI);
      float f2 = -MathHelper.cos(-pitch * ((float)Math.PI / 180F));
      float f3 = MathHelper.sin(-pitch * ((float)Math.PI / 180F));
      return new Vector3d((double)(f1 * f2), (double)f3, (double)(f * f2));
   }

   public Vector3d align(EnumSet<Direction.Axis> axes) {
      double d0 = axes.contains(Direction.Axis.X) ? (double)MathHelper.floor(this.x) : this.x;
      double d1 = axes.contains(Direction.Axis.Y) ? (double)MathHelper.floor(this.y) : this.y;
      double d2 = axes.contains(Direction.Axis.Z) ? (double)MathHelper.floor(this.z) : this.z;
      return new Vector3d(d0, d1, d2);
   }

   public double getCoordinate(Direction.Axis axis) {
      return axis.getCoordinate(this.x, this.y, this.z);
   }

   public final double getX() {
      return this.x;
   }

   public final double getY() {
      return this.y;
   }

   public final double getZ() {
      return this.z;
   }
}