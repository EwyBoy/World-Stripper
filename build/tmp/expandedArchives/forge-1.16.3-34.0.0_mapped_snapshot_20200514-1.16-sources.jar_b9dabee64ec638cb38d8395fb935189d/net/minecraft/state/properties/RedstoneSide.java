package net.minecraft.state.properties;

import net.minecraft.util.IStringSerializable;

public enum RedstoneSide implements IStringSerializable {
   UP("up"),
   SIDE("side"),
   NONE("none");

   private final String name;

   private RedstoneSide(String name) {
      this.name = name;
   }

   public String toString() {
      return this.func_176610_l();
   }

   public String func_176610_l() {
      return this.name;
   }

   public boolean func_235921_b_() {
      return this != NONE;
   }
}