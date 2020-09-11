package net.minecraft.util;

import net.minecraft.entity.MobEntity;
import net.minecraft.pathfinding.GroundPathNavigator;

public class GroundPathHelper {
   public static boolean func_242319_a(MobEntity p_242319_0_) {
      return p_242319_0_.getNavigator() instanceof GroundPathNavigator;
   }
}