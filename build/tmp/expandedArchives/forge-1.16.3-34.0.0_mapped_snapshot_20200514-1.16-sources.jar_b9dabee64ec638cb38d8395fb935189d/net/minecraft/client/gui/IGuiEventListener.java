package net.minecraft.client.gui;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public interface IGuiEventListener {
   default void mouseMoved(double xPos, double mouseY) {
   }

   default boolean func_231044_a_(double p_231044_1_, double p_231044_3_, int p_231044_5_) {
      return false;
   }

   default boolean func_231048_c_(double p_231048_1_, double p_231048_3_, int p_231048_5_) {
      return false;
   }

   default boolean func_231045_a_(double p_231045_1_, double p_231045_3_, int p_231045_5_, double p_231045_6_, double p_231045_8_) {
      return false;
   }

   default boolean func_231043_a_(double p_231043_1_, double p_231043_3_, double p_231043_5_) {
      return false;
   }

   default boolean func_231046_a_(int p_231046_1_, int p_231046_2_, int p_231046_3_) {
      return false;
   }

   default boolean keyReleased(int keyCode, int scanCode, int modifiers) {
      return false;
   }

   default boolean func_231042_a_(char p_231042_1_, int p_231042_2_) {
      return false;
   }

   default boolean func_231049_c__(boolean p_231049_1_) {
      return false;
   }

   default boolean func_231047_b_(double p_231047_1_, double p_231047_3_) {
      return false;
   }
}