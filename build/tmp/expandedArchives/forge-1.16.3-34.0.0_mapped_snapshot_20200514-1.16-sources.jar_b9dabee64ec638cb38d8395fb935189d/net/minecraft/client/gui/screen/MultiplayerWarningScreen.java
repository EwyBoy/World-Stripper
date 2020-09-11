package net.minecraft.client.gui.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.gui.DialogTexts;
import net.minecraft.client.gui.IBidiRenderer;
import net.minecraft.client.gui.chat.NarratorChatListener;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.gui.widget.button.CheckboxButton;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MultiplayerWarningScreen extends Screen {
   private final Screen field_230156_a_;
   private static final ITextComponent field_230157_b_ = (new TranslationTextComponent("multiplayerWarning.header")).func_240699_a_(TextFormatting.BOLD);
   private static final ITextComponent field_230158_c_ = new TranslationTextComponent("multiplayerWarning.message");
   private static final ITextComponent field_230159_d_ = new TranslationTextComponent("multiplayerWarning.check");
   private static final ITextComponent field_238858_q_ = field_230157_b_.func_230532_e_().func_240702_b_("\n").func_230529_a_(field_230158_c_);
   private CheckboxButton field_230162_g_;
   private IBidiRenderer field_243364_s = IBidiRenderer.field_243257_a;

   public MultiplayerWarningScreen(Screen p_i230052_1_) {
      super(NarratorChatListener.EMPTY);
      this.field_230156_a_ = p_i230052_1_;
   }

   protected void func_231160_c_() {
      super.func_231160_c_();
      this.field_243364_s = IBidiRenderer.func_243258_a(this.field_230712_o_, field_230158_c_, this.field_230708_k_ - 50);
      int i = (this.field_243364_s.func_241862_a() + 1) * 9;
      this.func_230480_a_(new Button(this.field_230708_k_ / 2 - 155, 100 + i, 150, 20, DialogTexts.field_240636_g_, (p_230165_1_) -> {
         if (this.field_230162_g_.isChecked()) {
            this.field_230706_i_.gameSettings.field_230152_Z_ = true;
            this.field_230706_i_.gameSettings.saveOptions();
         }

         this.field_230706_i_.displayGuiScreen(new MultiplayerScreen(this.field_230156_a_));
      }));
      this.func_230480_a_(new Button(this.field_230708_k_ / 2 - 155 + 160, 100 + i, 150, 20, DialogTexts.field_240637_h_, (p_230164_1_) -> {
         this.field_230706_i_.displayGuiScreen(this.field_230156_a_);
      }));
      this.field_230162_g_ = new CheckboxButton(this.field_230708_k_ / 2 - 155 + 80, 76 + i, 150, 20, field_230159_d_, false);
      this.func_230480_a_(this.field_230162_g_);
   }

   public String func_231167_h_() {
      return field_238858_q_.getString();
   }

   public void func_230430_a_(MatrixStack p_230430_1_, int p_230430_2_, int p_230430_3_, float p_230430_4_) {
      this.func_231165_f_(0);
      func_238472_a_(p_230430_1_, this.field_230712_o_, field_230157_b_, this.field_230708_k_ / 2, 30, 16777215);
      this.field_243364_s.func_241863_a(p_230430_1_, this.field_230708_k_ / 2, 70);
      super.func_230430_a_(p_230430_1_, p_230430_2_, p_230430_3_, p_230430_4_);
   }
}