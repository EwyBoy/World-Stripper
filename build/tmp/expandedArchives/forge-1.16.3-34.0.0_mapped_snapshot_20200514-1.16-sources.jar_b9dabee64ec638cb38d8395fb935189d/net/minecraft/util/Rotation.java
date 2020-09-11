package net.minecraft.util;

import com.google.common.collect.Lists;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import net.minecraft.util.math.vector.Orientation;

public enum Rotation {
   NONE(Orientation.IDENTITY),
   CLOCKWISE_90(Orientation.ROT_90_Y_NEG),
   CLOCKWISE_180(Orientation.ROT_180_FACE_XZ),
   COUNTERCLOCKWISE_90(Orientation.ROT_90_Y_POS);

   private final Orientation field_235573_e_;

   private Rotation(Orientation p_i231796_3_) {
      this.field_235573_e_ = p_i231796_3_;
   }

   public Rotation add(Rotation rotation) {
      switch(rotation) {
      case CLOCKWISE_180:
         switch(this) {
         case NONE:
            return CLOCKWISE_180;
         case CLOCKWISE_90:
            return COUNTERCLOCKWISE_90;
         case CLOCKWISE_180:
            return NONE;
         case COUNTERCLOCKWISE_90:
            return CLOCKWISE_90;
         }
      case COUNTERCLOCKWISE_90:
         switch(this) {
         case NONE:
            return COUNTERCLOCKWISE_90;
         case CLOCKWISE_90:
            return NONE;
         case CLOCKWISE_180:
            return CLOCKWISE_90;
         case COUNTERCLOCKWISE_90:
            return CLOCKWISE_180;
         }
      case CLOCKWISE_90:
         switch(this) {
         case NONE:
            return CLOCKWISE_90;
         case CLOCKWISE_90:
            return CLOCKWISE_180;
         case CLOCKWISE_180:
            return COUNTERCLOCKWISE_90;
         case COUNTERCLOCKWISE_90:
            return NONE;
         }
      default:
         return this;
      }
   }

   public Orientation func_235574_a_() {
      return this.field_235573_e_;
   }

   public Direction rotate(Direction facing) {
      if (facing.getAxis() == Direction.Axis.Y) {
         return facing;
      } else {
         switch(this) {
         case CLOCKWISE_90:
            return facing.rotateY();
         case CLOCKWISE_180:
            return facing.getOpposite();
         case COUNTERCLOCKWISE_90:
            return facing.rotateYCCW();
         default:
            return facing;
         }
      }
   }

   public int rotate(int p_185833_1_, int p_185833_2_) {
      switch(this) {
      case CLOCKWISE_90:
         return (p_185833_1_ + p_185833_2_ / 4) % p_185833_2_;
      case CLOCKWISE_180:
         return (p_185833_1_ + p_185833_2_ / 2) % p_185833_2_;
      case COUNTERCLOCKWISE_90:
         return (p_185833_1_ + p_185833_2_ * 3 / 4) % p_185833_2_;
      default:
         return p_185833_1_;
      }
   }

   /**
    * Chooses a random rotation from {@link Rotation}.
    */
   public static Rotation randomRotation(Random rand) {
      return Util.func_240989_a_(values(), rand);
   }

   /**
    * Generates a shuffled list with all rotations.
    */
   public static List<Rotation> shuffledRotations(Random rand) {
      List<Rotation> list = Lists.newArrayList(values());
      Collections.shuffle(list, rand);
      return list;
   }
}