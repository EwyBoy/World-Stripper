package net.minecraft.entity.ai.brain.schedule;

import net.minecraft.util.registry.Registry;

public class Activity extends net.minecraftforge.registries.ForgeRegistryEntry<Activity> {
   public static final Activity CORE = register("core");
   public static final Activity IDLE = register("idle");
   public static final Activity WORK = register("work");
   public static final Activity PLAY = register("play");
   public static final Activity REST = register("rest");
   public static final Activity MEET = register("meet");
   public static final Activity PANIC = register("panic");
   public static final Activity RAID = register("raid");
   public static final Activity PRE_RAID = register("pre_raid");
   public static final Activity HIDE = register("hide");
   public static final Activity field_234621_k_ = register("fight");
   public static final Activity field_234622_l_ = register("celebrate");
   public static final Activity field_234623_m_ = register("admire_item");
   public static final Activity field_234624_n_ = register("avoid");
   public static final Activity field_234625_o_ = register("ride");
   private final String id;
   private final int field_234626_q_;

   public Activity(String key) {
      this.id = key;
      this.field_234626_q_ = key.hashCode();
   }

   public String getKey() {
      return this.id;
   }

   private static Activity register(String key) {
      return Registry.register(Registry.ACTIVITY, key, new Activity(key));
   }

   public boolean equals(Object p_equals_1_) {
      if (this == p_equals_1_) {
         return true;
      } else if (p_equals_1_ != null && this.getClass() == p_equals_1_.getClass()) {
         Activity activity = (Activity)p_equals_1_;
         return this.id.equals(activity.id);
      } else {
         return false;
      }
   }

   public int hashCode() {
      return this.field_234626_q_;
   }

   public String toString() {
      return this.getKey();
   }
}