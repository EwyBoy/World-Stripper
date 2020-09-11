package net.minecraft.client.gui.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.gui.chat.NarratorChatListener;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class DownloadTerrainScreen extends Screen {
   private static final ITextComponent field_243307_a = new TranslationTextComponent("multiplayer.downloadingTerrain");

   public DownloadTerrainScreen() {
      super(NarratorChatListener.EMPTY);
   }

   public boolean func_231178_ax__() {
      return false;
   }

   public void func_230430_a_(MatrixStack p_230430_1_, int p_230430_2_, int p_230430_3_, float p_230430_4_) {
      this.func_231165_f_(0);
      func_238472_a_(p_230430_1_, this.field_230712_o_, field_243307_a, this.field_230708_k_ / 2, this.field_230709_l_ / 2 - 50, 16777215);
      super.func_230430_a_(p_230430_1_, p_230430_2_, p_230430_3_, p_230430_4_);
   }

   public boolean func_231177_au__() {
      return false;
   }
}