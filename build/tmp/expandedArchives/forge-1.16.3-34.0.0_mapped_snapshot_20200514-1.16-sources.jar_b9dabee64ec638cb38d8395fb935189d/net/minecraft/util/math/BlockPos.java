package net.minecraft.util.math;

import com.google.common.collect.AbstractIterator;
import com.mojang.serialization.Codec;
import java.util.Optional;
import java.util.Random;
import java.util.function.Predicate;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import javax.annotation.concurrent.Immutable;
import net.minecraft.dispenser.IPosition;
import net.minecraft.util.AxisRotation;
import net.minecraft.util.Direction;
import net.minecraft.util.Rotation;
import net.minecraft.util.Util;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3i;
import org.apache.commons.lang3.Validate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Immutable
public class BlockPos extends Vector3i {
   public static final Codec<BlockPos> field_239578_a_ = Codec.INT_STREAM.comapFlatMap((p_239586_0_) -> {
      return Util.func_240987_a_(p_239586_0_, 3).map((p_239587_0_) -> {
         return new BlockPos(p_239587_0_[0], p_239587_0_[1], p_239587_0_[2]);
      });
   }, (p_239582_0_) -> {
      return IntStream.of(p_239582_0_.getX(), p_239582_0_.getY(), p_239582_0_.getZ());
   }).stable();
   private static final Logger LOGGER = LogManager.getLogger();
   /** An immutable block pos with zero as all coordinates. */
   public static final BlockPos ZERO = new BlockPos(0, 0, 0);
   private static final int NUM_X_BITS = 1 + MathHelper.log2(MathHelper.smallestEncompassingPowerOfTwo(30000000));
   private static final int NUM_Z_BITS = NUM_X_BITS;
   private static final int NUM_Y_BITS = 64 - NUM_X_BITS - NUM_Z_BITS;
   private static final long X_MASK = (1L << NUM_X_BITS) - 1L;
   private static final long Y_MASK = (1L << NUM_Y_BITS) - 1L;
   private static final long Z_MASK = (1L << NUM_Z_BITS) - 1L;
   private static final int field_218292_j = NUM_Y_BITS;
   private static final int field_218293_k = NUM_Y_BITS + NUM_Z_BITS;

   public BlockPos(int x, int y, int z) {
      super(x, y, z);
   }

   public BlockPos(double x, double y, double z) {
      super(x, y, z);
   }

   public BlockPos(Vector3d vec) {
      this(vec.x, vec.y, vec.z);
   }

   public BlockPos(IPosition p_i50799_1_) {
      this(p_i50799_1_.getX(), p_i50799_1_.getY(), p_i50799_1_.getZ());
   }

   public BlockPos(Vector3i source) {
      this(source.getX(), source.getY(), source.getZ());
   }

   public static long offset(long pos, Direction p_218289_2_) {
      return offset(pos, p_218289_2_.getXOffset(), p_218289_2_.getYOffset(), p_218289_2_.getZOffset());
   }

   public static long offset(long pos, int dx, int dy, int dz) {
      return pack(unpackX(pos) + dx, unpackY(pos) + dy, unpackZ(pos) + dz);
   }

   public static int unpackX(long p_218290_0_) {
      return (int)(p_218290_0_ << 64 - field_218293_k - NUM_X_BITS >> 64 - NUM_X_BITS);
   }

   public static int unpackY(long p_218274_0_) {
      return (int)(p_218274_0_ << 64 - NUM_Y_BITS >> 64 - NUM_Y_BITS);
   }

   public static int unpackZ(long p_218282_0_) {
      return (int)(p_218282_0_ << 64 - field_218292_j - NUM_Z_BITS >> 64 - NUM_Z_BITS);
   }

   public static BlockPos fromLong(long p_218283_0_) {
      return new BlockPos(unpackX(p_218283_0_), unpackY(p_218283_0_), unpackZ(p_218283_0_));
   }

   public long toLong() {
      return pack(this.getX(), this.getY(), this.getZ());
   }

   public static long pack(int p_218276_0_, int p_218276_1_, int p_218276_2_) {
      long i = 0L;
      i = i | ((long)p_218276_0_ & X_MASK) << field_218293_k;
      i = i | ((long)p_218276_1_ & Y_MASK) << 0;
      return i | ((long)p_218276_2_ & Z_MASK) << field_218292_j;
   }

   public static long func_218288_f(long p_218288_0_) {
      return p_218288_0_ & -16L;
   }

   /**
    * Add the given coordinates to the coordinates of this BlockPos
    */
   public BlockPos add(double x, double y, double z) {
      return x == 0.0D && y == 0.0D && z == 0.0D ? this : new BlockPos((double)this.getX() + x, (double)this.getY() + y, (double)this.getZ() + z);
   }

   /**
    * Add the given coordinates to the coordinates of this BlockPos
    */
   public BlockPos add(int x, int y, int z) {
      return x == 0 && y == 0 && z == 0 ? this : new BlockPos(this.getX() + x, this.getY() + y, this.getZ() + z);
   }

   /**
    * Add the given Vector to this BlockPos
    */
   public BlockPos add(Vector3i vec) {
      return this.add(vec.getX(), vec.getY(), vec.getZ());
   }

   /**
    * Subtract the given Vector from this BlockPos
    */
   public BlockPos subtract(Vector3i vec) {
      return this.add(-vec.getX(), -vec.getY(), -vec.getZ());
   }

   /**
    * Offset this BlockPos 1 block up
    */
   public BlockPos up() {
      return this.offset(Direction.UP);
   }

   /**
    * Offset this BlockPos n blocks up
    */
   public BlockPos up(int n) {
      return this.offset(Direction.UP, n);
   }

   /**
    * Offset this BlockPos 1 block down
    */
   public BlockPos down() {
      return this.offset(Direction.DOWN);
   }

   /**
    * Offset this BlockPos n blocks down
    */
   public BlockPos down(int n) {
      return this.offset(Direction.DOWN, n);
   }

   /**
    * Offset this BlockPos 1 block in northern direction
    */
   public BlockPos north() {
      return this.offset(Direction.NORTH);
   }

   /**
    * Offset this BlockPos n blocks in northern direction
    */
   public BlockPos north(int n) {
      return this.offset(Direction.NORTH, n);
   }

   /**
    * Offset this BlockPos 1 block in southern direction
    */
   public BlockPos south() {
      return this.offset(Direction.SOUTH);
   }

   /**
    * Offset this BlockPos n blocks in southern direction
    */
   public BlockPos south(int n) {
      return this.offset(Direction.SOUTH, n);
   }

   /**
    * Offset this BlockPos 1 block in western direction
    */
   public BlockPos west() {
      return this.offset(Direction.WEST);
   }

   /**
    * Offset this BlockPos n blocks in western direction
    */
   public BlockPos west(int n) {
      return this.offset(Direction.WEST, n);
   }

   /**
    * Offset this BlockPos 1 block in eastern direction
    */
   public BlockPos east() {
      return this.offset(Direction.EAST);
   }

   /**
    * Offset this BlockPos n blocks in eastern direction
    */
   public BlockPos east(int n) {
      return this.offset(Direction.EAST, n);
   }

   /**
    * Offset this BlockPos 1 block in the given direction
    */
   public BlockPos offset(Direction facing) {
      return new BlockPos(this.getX() + facing.getXOffset(), this.getY() + facing.getYOffset(), this.getZ() + facing.getZOffset());
   }

   /**
    * Offsets this BlockPos n blocks in the given direction
    */
   public BlockPos offset(Direction facing, int n) {
      return n == 0 ? this : new BlockPos(this.getX() + facing.getXOffset() * n, this.getY() + facing.getYOffset() * n, this.getZ() + facing.getZOffset() * n);
   }

   public BlockPos func_241872_a(Direction.Axis p_241872_1_, int p_241872_2_) {
      if (p_241872_2_ == 0) {
         return this;
      } else {
         int i = p_241872_1_ == Direction.Axis.X ? p_241872_2_ : 0;
         int j = p_241872_1_ == Direction.Axis.Y ? p_241872_2_ : 0;
         int k = p_241872_1_ == Direction.Axis.Z ? p_241872_2_ : 0;
         return new BlockPos(this.getX() + i, this.getY() + j, this.getZ() + k);
      }
   }

   public BlockPos rotate(Rotation rotationIn) {
      switch(rotationIn) {
      case NONE:
      default:
         return this;
      case CLOCKWISE_90:
         return new BlockPos(-this.getZ(), this.getY(), this.getX());
      case CLOCKWISE_180:
         return new BlockPos(-this.getX(), this.getY(), -this.getZ());
      case COUNTERCLOCKWISE_90:
         return new BlockPos(this.getZ(), this.getY(), -this.getX());
      }
   }

   /**
    * Calculate the cross product of this and the given Vector
    */
   public BlockPos crossProduct(Vector3i vec) {
      return new BlockPos(this.getY() * vec.getZ() - this.getZ() * vec.getY(), this.getZ() * vec.getX() - this.getX() * vec.getZ(), this.getX() * vec.getY() - this.getY() * vec.getX());
   }

   /**
    * Returns a version of this BlockPos that is guaranteed to be immutable.
    *  
    * <p>When storing a BlockPos given to you for an extended period of time, make sure you
    * use this in case the value is changed internally.</p>
    */
   public BlockPos toImmutable() {
      return this;
   }

   public BlockPos.Mutable func_239590_i_() {
      return new BlockPos.Mutable(this.getX(), this.getY(), this.getZ());
   }

   public static Iterable<BlockPos> func_239585_a_(Random p_239585_0_, int p_239585_1_, int p_239585_2_, int p_239585_3_, int p_239585_4_, int p_239585_5_, int p_239585_6_, int p_239585_7_) {
      int i = p_239585_5_ - p_239585_2_ + 1;
      int j = p_239585_6_ - p_239585_3_ + 1;
      int k = p_239585_7_ - p_239585_4_ + 1;
      return () -> {
         return new AbstractIterator<BlockPos>() {
            final BlockPos.Mutable pos = new BlockPos.Mutable();
            int field_239591_b_ = p_239585_1_;

            protected BlockPos computeNext() {
               if (this.field_239591_b_ <= 0) {
                  return this.endOfData();
               } else {
                  BlockPos blockpos = this.pos.setPos(p_239585_2_ + p_239585_0_.nextInt(i), p_239585_3_ + p_239585_0_.nextInt(j), p_239585_4_ + p_239585_0_.nextInt(k));
                  --this.field_239591_b_;
                  return blockpos;
               }
            }
         };
      };
   }

   public static Iterable<BlockPos> func_239583_a_(BlockPos p_239583_0_, int p_239583_1_, int p_239583_2_, int p_239583_3_) {
      int i = p_239583_1_ + p_239583_2_ + p_239583_3_;
      int j = p_239583_0_.getX();
      int k = p_239583_0_.getY();
      int l = p_239583_0_.getZ();
      return () -> {
         return new AbstractIterator<BlockPos>() {
            private final BlockPos.Mutable field_218298_a = new BlockPos.Mutable();
            private int field_239604_i_;
            private int field_239605_j_;
            private int field_239606_k_;
            private int field_239607_l_;
            private int field_239608_m_;
            private boolean field_239609_n_;

            protected BlockPos computeNext() {
               if (this.field_239609_n_) {
                  this.field_239609_n_ = false;
                  this.field_218298_a.setZ(l - (this.field_218298_a.getZ() - l));
                  return this.field_218298_a;
               } else {
                  BlockPos blockpos;
                  for(blockpos = null; blockpos == null; ++this.field_239608_m_) {
                     if (this.field_239608_m_ > this.field_239606_k_) {
                        ++this.field_239607_l_;
                        if (this.field_239607_l_ > this.field_239605_j_) {
                           ++this.field_239604_i_;
                           if (this.field_239604_i_ > i) {
                              return this.endOfData();
                           }

                           this.field_239605_j_ = Math.min(p_239583_1_, this.field_239604_i_);
                           this.field_239607_l_ = -this.field_239605_j_;
                        }

                        this.field_239606_k_ = Math.min(p_239583_2_, this.field_239604_i_ - Math.abs(this.field_239607_l_));
                        this.field_239608_m_ = -this.field_239606_k_;
                     }

                     int i1 = this.field_239607_l_;
                     int j1 = this.field_239608_m_;
                     int k1 = this.field_239604_i_ - Math.abs(i1) - Math.abs(j1);
                     if (k1 <= p_239583_3_) {
                        this.field_239609_n_ = k1 != 0;
                        blockpos = this.field_218298_a.setPos(j + i1, k + j1, l + k1);
                     }
                  }

                  return blockpos;
               }
            }
         };
      };
   }

   public static Optional<BlockPos> func_239584_a_(BlockPos p_239584_0_, int p_239584_1_, int p_239584_2_, Predicate<BlockPos> p_239584_3_) {
      return func_239588_b_(p_239584_0_, p_239584_1_, p_239584_2_, p_239584_1_).filter(p_239584_3_).findFirst();
   }

   public static Stream<BlockPos> func_239588_b_(BlockPos p_239588_0_, int p_239588_1_, int p_239588_2_, int p_239588_3_) {
      return StreamSupport.stream(func_239583_a_(p_239588_0_, p_239588_1_, p_239588_2_, p_239588_3_).spliterator(), false);
   }

   public static Iterable<BlockPos> getAllInBoxMutable(BlockPos firstPos, BlockPos secondPos) {
      return getAllInBoxMutable(Math.min(firstPos.getX(), secondPos.getX()), Math.min(firstPos.getY(), secondPos.getY()), Math.min(firstPos.getZ(), secondPos.getZ()), Math.max(firstPos.getX(), secondPos.getX()), Math.max(firstPos.getY(), secondPos.getY()), Math.max(firstPos.getZ(), secondPos.getZ()));
   }

   public static Stream<BlockPos> getAllInBox(BlockPos firstPos, BlockPos secondPos) {
      return StreamSupport.stream(getAllInBoxMutable(firstPos, secondPos).spliterator(), false);
   }

   public static Stream<BlockPos> getAllInBox(MutableBoundingBox p_229383_0_) {
      return getAllInBox(Math.min(p_229383_0_.minX, p_229383_0_.maxX), Math.min(p_229383_0_.minY, p_229383_0_.maxY), Math.min(p_229383_0_.minZ, p_229383_0_.maxZ), Math.max(p_229383_0_.minX, p_229383_0_.maxX), Math.max(p_229383_0_.minY, p_229383_0_.maxY), Math.max(p_229383_0_.minZ, p_229383_0_.maxZ));
   }

   public static Stream<BlockPos> func_239581_a_(AxisAlignedBB p_239581_0_) {
      return getAllInBox(MathHelper.floor(p_239581_0_.minX), MathHelper.floor(p_239581_0_.minY), MathHelper.floor(p_239581_0_.minZ), MathHelper.floor(p_239581_0_.maxX), MathHelper.floor(p_239581_0_.maxY), MathHelper.floor(p_239581_0_.maxZ));
   }

   public static Stream<BlockPos> getAllInBox(int p_218287_0_, int p_218287_1_, int p_218287_2_, int p_218287_3_, int p_218287_4_, int p_218287_5_) {
      return StreamSupport.stream(getAllInBoxMutable(p_218287_0_, p_218287_1_, p_218287_2_, p_218287_3_, p_218287_4_, p_218287_5_).spliterator(), false);
   }

   /**
    * Creates an Iterable that returns all positions in the box specified by the given corners. <strong>Coordinates must
    * be in order</strong>; e.g. x1 <= x2.
    *  
    * This method uses {@link BlockPos.MutableBlockPos MutableBlockPos} instead of regular BlockPos, which grants better
    * performance. However, the resulting BlockPos instances can only be used inside the iteration loop (as otherwise
    * the value will change), unless {@link #toImmutable()} is called. This method is ideal for searching large areas
    * and only storing a few locations.
    *  
    * @see #getAllInBox(BlockPos, BlockPos)
    * @see #getAllInBox(int, int, int, int, int, int)
    * @see #getAllInBoxMutable(BlockPos, BlockPos)
    */
   public static Iterable<BlockPos> getAllInBoxMutable(int x1, int y1, int z1, int x2, int y2, int z2) {
      int i = x2 - x1 + 1;
      int j = y2 - y1 + 1;
      int k = z2 - z1 + 1;
      int l = i * j * k;
      return () -> {
         return new AbstractIterator<BlockPos>() {
            private final BlockPos.Mutable field_239616_g_ = new BlockPos.Mutable();
            private int field_239617_h_;

            protected BlockPos computeNext() {
               if (this.field_239617_h_ == l) {
                  return this.endOfData();
               } else {
                  int i1 = this.field_239617_h_ % i;
                  int j1 = this.field_239617_h_ / i;
                  int k1 = j1 % j;
                  int l1 = j1 / j;
                  ++this.field_239617_h_;
                  return this.field_239616_g_.setPos(x1 + i1, y1 + k1, z1 + l1);
               }
            }
         };
      };
   }

   public static Iterable<BlockPos.Mutable> func_243514_a(BlockPos p_243514_0_, int p_243514_1_, Direction p_243514_2_, Direction p_243514_3_) {
      Validate.validState(p_243514_2_.getAxis() != p_243514_3_.getAxis(), "The two directions cannot be on the same axis");
      return () -> {
         return new AbstractIterator<BlockPos.Mutable>() {
            private final Direction[] field_243520_e = new Direction[]{p_243514_2_, p_243514_3_, p_243514_2_.getOpposite(), p_243514_3_.getOpposite()};
            private final BlockPos.Mutable field_243521_f = p_243514_0_.func_239590_i_().move(p_243514_3_);
            private final int field_243522_g = 4 * p_243514_1_;
            private int field_243523_h = -1;
            private int field_243524_i;
            private int field_243525_j;
            private int field_243526_k = this.field_243521_f.getX();
            private int field_243527_l = this.field_243521_f.getY();
            private int field_243528_m = this.field_243521_f.getZ();

            protected BlockPos.Mutable computeNext() {
               this.field_243521_f.setPos(this.field_243526_k, this.field_243527_l, this.field_243528_m).move(this.field_243520_e[(this.field_243523_h + 4) % 4]);
               this.field_243526_k = this.field_243521_f.getX();
               this.field_243527_l = this.field_243521_f.getY();
               this.field_243528_m = this.field_243521_f.getZ();
               if (this.field_243525_j >= this.field_243524_i) {
                  if (this.field_243523_h >= this.field_243522_g) {
                     return this.endOfData();
                  }

                  ++this.field_243523_h;
                  this.field_243525_j = 0;
                  this.field_243524_i = this.field_243523_h / 2 + 1;
               }

               ++this.field_243525_j;
               return this.field_243521_f;
            }
         };
      };
   }

   public static class Mutable extends BlockPos {
      public Mutable() {
         this(0, 0, 0);
      }

      public Mutable(int x_, int y_, int z_) {
         super(x_, y_, z_);
      }

      public Mutable(double p_i50824_1_, double p_i50824_3_, double p_i50824_5_) {
         this(MathHelper.floor(p_i50824_1_), MathHelper.floor(p_i50824_3_), MathHelper.floor(p_i50824_5_));
      }

      /**
       * Add the given coordinates to the coordinates of this BlockPos
       */
      public BlockPos add(double x, double y, double z) {
         return super.add(x, y, z).toImmutable();
      }

      /**
       * Add the given coordinates to the coordinates of this BlockPos
       */
      public BlockPos add(int x, int y, int z) {
         return super.add(x, y, z).toImmutable();
      }

      /**
       * Offsets this BlockPos n blocks in the given direction
       */
      public BlockPos offset(Direction facing, int n) {
         return super.offset(facing, n).toImmutable();
      }

      public BlockPos func_241872_a(Direction.Axis p_241872_1_, int p_241872_2_) {
         return super.func_241872_a(p_241872_1_, p_241872_2_).toImmutable();
      }

      public BlockPos rotate(Rotation rotationIn) {
         return super.rotate(rotationIn).toImmutable();
      }

      /**
       * Sets position
       */
      public BlockPos.Mutable setPos(int xIn, int yIn, int zIn) {
         this.setX(xIn);
         this.setY(yIn);
         this.setZ(zIn);
         return this;
      }

      public BlockPos.Mutable setPos(double xIn, double yIn, double zIn) {
         return this.setPos(MathHelper.floor(xIn), MathHelper.floor(yIn), MathHelper.floor(zIn));
      }

      public BlockPos.Mutable setPos(Vector3i vec) {
         return this.setPos(vec.getX(), vec.getY(), vec.getZ());
      }

      public BlockPos.Mutable setPos(long p_218294_1_) {
         return this.setPos(unpackX(p_218294_1_), unpackY(p_218294_1_), unpackZ(p_218294_1_));
      }

      public BlockPos.Mutable func_218295_a(AxisRotation p_218295_1_, int p_218295_2_, int p_218295_3_, int p_218295_4_) {
         return this.setPos(p_218295_1_.getCoordinate(p_218295_2_, p_218295_3_, p_218295_4_, Direction.Axis.X), p_218295_1_.getCoordinate(p_218295_2_, p_218295_3_, p_218295_4_, Direction.Axis.Y), p_218295_1_.getCoordinate(p_218295_2_, p_218295_3_, p_218295_4_, Direction.Axis.Z));
      }

      public BlockPos.Mutable func_239622_a_(Vector3i p_239622_1_, Direction p_239622_2_) {
         return this.setPos(p_239622_1_.getX() + p_239622_2_.getXOffset(), p_239622_1_.getY() + p_239622_2_.getYOffset(), p_239622_1_.getZ() + p_239622_2_.getZOffset());
      }

      public BlockPos.Mutable func_239621_a_(Vector3i p_239621_1_, int p_239621_2_, int p_239621_3_, int p_239621_4_) {
         return this.setPos(p_239621_1_.getX() + p_239621_2_, p_239621_1_.getY() + p_239621_3_, p_239621_1_.getZ() + p_239621_4_);
      }

      public BlockPos.Mutable move(Direction facing) {
         return this.move(facing, 1);
      }

      public BlockPos.Mutable move(Direction facing, int n) {
         return this.setPos(this.getX() + facing.getXOffset() * n, this.getY() + facing.getYOffset() * n, this.getZ() + facing.getZOffset() * n);
      }

      public BlockPos.Mutable move(int xIn, int yIn, int zIn) {
         return this.setPos(this.getX() + xIn, this.getY() + yIn, this.getZ() + zIn);
      }

      public BlockPos.Mutable func_243531_h(Vector3i p_243531_1_) {
         return this.setPos(this.getX() + p_243531_1_.getX(), this.getY() + p_243531_1_.getY(), this.getZ() + p_243531_1_.getZ());
      }

      public BlockPos.Mutable func_239620_a_(Direction.Axis p_239620_1_, int p_239620_2_, int p_239620_3_) {
         switch(p_239620_1_) {
         case X:
            return this.setPos(MathHelper.clamp(this.getX(), p_239620_2_, p_239620_3_), this.getY(), this.getZ());
         case Y:
            return this.setPos(this.getX(), MathHelper.clamp(this.getY(), p_239620_2_, p_239620_3_), this.getZ());
         case Z:
            return this.setPos(this.getX(), this.getY(), MathHelper.clamp(this.getZ(), p_239620_2_, p_239620_3_));
         default:
            throw new IllegalStateException("Unable to clamp axis " + p_239620_1_);
         }
      }

      /**
       * Sets the X coordinate.
       */
      public void setX(int xIn) {
         super.setX(xIn);
      }

      public void setY(int yIn) {
         super.setY(yIn);
      }

      /**
       * Sets the Z coordinate.
       */
      public void setZ(int zIn) {
         super.setZ(zIn);
      }

      /**
       * Returns a version of this BlockPos that is guaranteed to be immutable.
       *  
       * <p>When storing a BlockPos given to you for an extended period of time, make sure you
       * use this in case the value is changed internally.</p>
       */
      public BlockPos toImmutable() {
         return new BlockPos(this);
      }
   }
}