package net.minecraft.block;

import net.minecraft.util.math.vector.Vector3d;

public class PortalInfo {
   public final Vector3d pos;
   public final Vector3d motion;
   public final float field_242960_c;
   public final float field_242961_d;

   public PortalInfo(Vector3d p_i242042_1_, Vector3d p_i242042_2_, float p_i242042_3_, float p_i242042_4_) {
      this.pos = p_i242042_1_;
      this.motion = p_i242042_2_;
      this.field_242960_c = p_i242042_3_;
      this.field_242961_d = p_i242042_4_;
   }
}