package net.minecraft.client.audio;

import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.lwjgl.openal.AL10;

@OnlyIn(Dist.CLIENT)
public class Listener {
   private float gain = 1.0F;
   private Vector3d field_237503_b_ = Vector3d.ZERO;

   public void setPosition(Vector3d p_216465_1_) {
      this.field_237503_b_ = p_216465_1_;
      AL10.alListener3f(4100, (float)p_216465_1_.x, (float)p_216465_1_.y, (float)p_216465_1_.z);
   }

   public Vector3d func_237504_a_() {
      return this.field_237503_b_;
   }

   public void func_227580_a_(Vector3f p_227580_1_, Vector3f p_227580_2_) {
      AL10.alListenerfv(4111, new float[]{p_227580_1_.getX(), p_227580_1_.getY(), p_227580_1_.getZ(), p_227580_2_.getX(), p_227580_2_.getY(), p_227580_2_.getZ()});
   }

   public void setGain(float gainIn) {
      AL10.alListenerf(4106, gainIn);
      this.gain = gainIn;
   }

   public float getGain() {
      return this.gain;
   }

   public void init() {
      this.setPosition(Vector3d.ZERO);
      this.func_227580_a_(Vector3f.ZN, Vector3f.YP);
   }
}