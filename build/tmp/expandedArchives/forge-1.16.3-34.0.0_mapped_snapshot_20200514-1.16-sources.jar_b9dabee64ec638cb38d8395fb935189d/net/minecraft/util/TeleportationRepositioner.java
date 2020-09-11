package net.minecraft.util;

import com.google.common.annotations.VisibleForTesting;
import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntStack;
import java.util.function.Predicate;
import net.minecraft.util.math.BlockPos;

public class TeleportationRepositioner {
   public static TeleportationRepositioner.Result func_243676_a(BlockPos p_243676_0_, Direction.Axis p_243676_1_, int p_243676_2_, Direction.Axis p_243676_3_, int p_243676_4_, Predicate<BlockPos> p_243676_5_) {
      BlockPos.Mutable blockpos$mutable = p_243676_0_.func_239590_i_();
      Direction direction = Direction.getFacingFromAxis(Direction.AxisDirection.NEGATIVE, p_243676_1_);
      Direction direction1 = direction.getOpposite();
      Direction direction2 = Direction.getFacingFromAxis(Direction.AxisDirection.NEGATIVE, p_243676_3_);
      Direction direction3 = direction2.getOpposite();
      int i = func_243677_a(p_243676_5_, blockpos$mutable.setPos(p_243676_0_), direction, p_243676_2_);
      int j = func_243677_a(p_243676_5_, blockpos$mutable.setPos(p_243676_0_), direction1, p_243676_2_);
      int k = i;
      TeleportationRepositioner.IntBounds[] ateleportationrepositioner$intbounds = new TeleportationRepositioner.IntBounds[i + 1 + j];
      ateleportationrepositioner$intbounds[i] = new TeleportationRepositioner.IntBounds(func_243677_a(p_243676_5_, blockpos$mutable.setPos(p_243676_0_), direction2, p_243676_4_), func_243677_a(p_243676_5_, blockpos$mutable.setPos(p_243676_0_), direction3, p_243676_4_));
      int l = ateleportationrepositioner$intbounds[i].field_243682_a;

      for(int i1 = 1; i1 <= i; ++i1) {
         TeleportationRepositioner.IntBounds teleportationrepositioner$intbounds = ateleportationrepositioner$intbounds[k - (i1 - 1)];
         ateleportationrepositioner$intbounds[k - i1] = new TeleportationRepositioner.IntBounds(func_243677_a(p_243676_5_, blockpos$mutable.setPos(p_243676_0_).move(direction, i1), direction2, teleportationrepositioner$intbounds.field_243682_a), func_243677_a(p_243676_5_, blockpos$mutable.setPos(p_243676_0_).move(direction, i1), direction3, teleportationrepositioner$intbounds.field_243683_b));
      }

      for(int l2 = 1; l2 <= j; ++l2) {
         TeleportationRepositioner.IntBounds teleportationrepositioner$intbounds2 = ateleportationrepositioner$intbounds[k + l2 - 1];
         ateleportationrepositioner$intbounds[k + l2] = new TeleportationRepositioner.IntBounds(func_243677_a(p_243676_5_, blockpos$mutable.setPos(p_243676_0_).move(direction1, l2), direction2, teleportationrepositioner$intbounds2.field_243682_a), func_243677_a(p_243676_5_, blockpos$mutable.setPos(p_243676_0_).move(direction1, l2), direction3, teleportationrepositioner$intbounds2.field_243683_b));
      }

      int i3 = 0;
      int j3 = 0;
      int j1 = 0;
      int k1 = 0;
      int[] aint = new int[ateleportationrepositioner$intbounds.length];

      for(int l1 = l; l1 >= 0; --l1) {
         for(int i2 = 0; i2 < ateleportationrepositioner$intbounds.length; ++i2) {
            TeleportationRepositioner.IntBounds teleportationrepositioner$intbounds1 = ateleportationrepositioner$intbounds[i2];
            int j2 = l - teleportationrepositioner$intbounds1.field_243682_a;
            int k2 = l + teleportationrepositioner$intbounds1.field_243683_b;
            aint[i2] = l1 >= j2 && l1 <= k2 ? k2 + 1 - l1 : 0;
         }

         Pair<TeleportationRepositioner.IntBounds, Integer> pair = func_243678_a(aint);
         TeleportationRepositioner.IntBounds teleportationrepositioner$intbounds3 = pair.getFirst();
         int k3 = 1 + teleportationrepositioner$intbounds3.field_243683_b - teleportationrepositioner$intbounds3.field_243682_a;
         int l3 = pair.getSecond();
         if (k3 * l3 > j1 * k1) {
            i3 = teleportationrepositioner$intbounds3.field_243682_a;
            j3 = l1;
            j1 = k3;
            k1 = l3;
         }
      }

      return new TeleportationRepositioner.Result(p_243676_0_.func_241872_a(p_243676_1_, i3 - k).func_241872_a(p_243676_3_, j3 - l), j1, k1);
   }

   private static int func_243677_a(Predicate<BlockPos> p_243677_0_, BlockPos.Mutable p_243677_1_, Direction p_243677_2_, int p_243677_3_) {
      int i;
      for(i = 0; i < p_243677_3_ && p_243677_0_.test(p_243677_1_.move(p_243677_2_)); ++i) {
      }

      return i;
   }

   @VisibleForTesting
   static Pair<TeleportationRepositioner.IntBounds, Integer> func_243678_a(int[] p_243678_0_) {
      int i = 0;
      int j = 0;
      int k = 0;
      IntStack intstack = new IntArrayList();
      intstack.push(0);

      for(int l = 1; l <= p_243678_0_.length; ++l) {
         int i1 = l == p_243678_0_.length ? 0 : p_243678_0_[l];

         while(!intstack.isEmpty()) {
            int j1 = p_243678_0_[intstack.topInt()];
            if (i1 >= j1) {
               intstack.push(l);
               break;
            }

            intstack.popInt();
            int k1 = intstack.isEmpty() ? 0 : intstack.topInt() + 1;
            if (j1 * (l - k1) > k * (j - i)) {
               j = l;
               i = k1;
               k = j1;
            }
         }

         if (intstack.isEmpty()) {
            intstack.push(l);
         }
      }

      return new Pair<>(new TeleportationRepositioner.IntBounds(i, j - 1), k);
   }

   public static class IntBounds {
      public final int field_243682_a;
      public final int field_243683_b;

      public IntBounds(int p_i242076_1_, int p_i242076_2_) {
         this.field_243682_a = p_i242076_1_;
         this.field_243683_b = p_i242076_2_;
      }

      public String toString() {
         return "IntBounds{min=" + this.field_243682_a + ", max=" + this.field_243683_b + '}';
      }
   }

   public static class Result {
      public final BlockPos field_243679_a;
      public final int field_243680_b;
      public final int field_243681_c;

      public Result(BlockPos p_i242075_1_, int p_i242075_2_, int p_i242075_3_) {
         this.field_243679_a = p_i242075_1_;
         this.field_243680_b = p_i242075_2_;
         this.field_243681_c = p_i242075_3_;
      }
   }
}