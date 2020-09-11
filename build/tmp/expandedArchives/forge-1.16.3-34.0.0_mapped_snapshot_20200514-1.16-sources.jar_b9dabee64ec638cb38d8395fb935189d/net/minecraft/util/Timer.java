package net.minecraft.util;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class Timer {
   public float renderPartialTicks;
   public float elapsedPartialTicks;
   private long lastSyncSysClock;
   private final float tickLength;

   public Timer(float p_i49528_1_, long p_i49528_2_) {
      this.tickLength = 1000.0F / p_i49528_1_;
      this.lastSyncSysClock = p_i49528_2_;
   }

   public int func_238400_a_(long p_238400_1_) {
      this.elapsedPartialTicks = (float)(p_238400_1_ - this.lastSyncSysClock) / this.tickLength;
      this.lastSyncSysClock = p_238400_1_;
      this.renderPartialTicks += this.elapsedPartialTicks;
      int i = (int)this.renderPartialTicks;
      this.renderPartialTicks -= (float)i;
      return i;
   }
}