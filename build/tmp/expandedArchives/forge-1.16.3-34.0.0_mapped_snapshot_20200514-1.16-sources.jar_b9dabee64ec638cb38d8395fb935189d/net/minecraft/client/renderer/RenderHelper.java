package net.minecraft.client.renderer;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.util.Util;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RenderHelper {
   private static final Vector3f field_237529_a_ = Util.make(new Vector3f(0.2F, 1.0F, -0.7F), Vector3f::normalize);
   private static final Vector3f field_237530_b_ = Util.make(new Vector3f(-0.2F, 1.0F, 0.7F), Vector3f::normalize);
   private static final Vector3f field_237531_c_ = Util.make(new Vector3f(0.2F, 1.0F, -0.7F), Vector3f::normalize);
   private static final Vector3f field_237532_d_ = Util.make(new Vector3f(-0.2F, -1.0F, 0.7F), Vector3f::normalize);

   public static void enableStandardItemLighting() {
      RenderSystem.enableLighting();
      RenderSystem.enableColorMaterial();
      RenderSystem.colorMaterial(1032, 5634);
   }

   /**
    * Disables the OpenGL lighting properties enabled by enableStandardItemLighting
    */
   public static void disableStandardItemLighting() {
      RenderSystem.disableLighting();
      RenderSystem.disableColorMaterial();
   }

   public static void func_237533_a_(Matrix4f p_237533_0_) {
      RenderSystem.setupLevelDiffuseLighting(field_237531_c_, field_237532_d_, p_237533_0_);
   }

   public static void setupLevelDiffuseLighting(Matrix4f matrixIn) {
      RenderSystem.setupLevelDiffuseLighting(field_237529_a_, field_237530_b_, matrixIn);
   }

   public static void setupGuiFlatDiffuseLighting() {
      RenderSystem.setupGuiFlatDiffuseLighting(field_237529_a_, field_237530_b_);
   }

   public static void setupGui3DDiffuseLighting() {
      RenderSystem.setupGui3DDiffuseLighting(field_237529_a_, field_237530_b_);
   }
}