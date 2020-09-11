package com.mojang.realmsclient.util;

import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class UploadTokenCache {
   private static final Long2ObjectMap<String> field_225236_a = new Long2ObjectOpenHashMap<>();

   public static String func_225235_a(long p_225235_0_) {
      return field_225236_a.get(p_225235_0_);
   }

   public static void func_225233_b(long p_225233_0_) {
      field_225236_a.remove(p_225233_0_);
   }

   public static void func_225234_a(long p_225234_0_, String p_225234_2_) {
      field_225236_a.put(p_225234_0_, p_225234_2_);
   }
}