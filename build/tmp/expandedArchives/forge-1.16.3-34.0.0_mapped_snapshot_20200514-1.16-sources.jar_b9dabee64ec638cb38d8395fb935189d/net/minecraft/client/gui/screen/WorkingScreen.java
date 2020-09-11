package net.minecraft.client.gui.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import javax.annotation.Nullable;
import net.minecraft.client.gui.chat.NarratorChatListener;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class WorkingScreen extends Screen implements IProgressUpdate {
   @Nullable
   private ITextComponent field_238648_a_;
   @Nullable
   private ITextComponent stage;
   private int progress;
   private boolean doneWorking;

   public WorkingScreen() {
      super(NarratorChatListener.EMPTY);
   }

   public boolean func_231178_ax__() {
      return false;
   }

   public void displaySavingString(ITextComponent component) {
      this.resetProgressAndMessage(component);
   }

   public void resetProgressAndMessage(ITextComponent component) {
      this.field_238648_a_ = component;
      this.displayLoadingString(new TranslationTextComponent("progress.working"));
   }

   public void displayLoadingString(ITextComponent component) {
      this.stage = component;
      this.setLoadingProgress(0);
   }

   /**
    * Updates the progress bar on the loading screen to the specified amount.
    */
   public void setLoadingProgress(int progress) {
      this.progress = progress;
   }

   public void setDoneWorking() {
      this.doneWorking = true;
   }

   public void func_230430_a_(MatrixStack p_230430_1_, int p_230430_2_, int p_230430_3_, float p_230430_4_) {
      if (this.doneWorking) {
         if (!this.field_230706_i_.isConnectedToRealms()) {
            this.field_230706_i_.displayGuiScreen((Screen)null);
         }

      } else {
         this.func_230446_a_(p_230430_1_);
         if (this.field_238648_a_ != null) {
            func_238472_a_(p_230430_1_, this.field_230712_o_, this.field_238648_a_, this.field_230708_k_ / 2, 70, 16777215);
         }

         if (this.stage != null && this.progress != 0) {
            func_238472_a_(p_230430_1_, this.field_230712_o_, (new StringTextComponent("")).func_230529_a_(this.stage).func_240702_b_(" " + this.progress + "%"), this.field_230708_k_ / 2, 90, 16777215);
         }

         super.func_230430_a_(p_230430_1_, p_230430_2_, p_230430_3_, p_230430_4_);
      }
   }
}