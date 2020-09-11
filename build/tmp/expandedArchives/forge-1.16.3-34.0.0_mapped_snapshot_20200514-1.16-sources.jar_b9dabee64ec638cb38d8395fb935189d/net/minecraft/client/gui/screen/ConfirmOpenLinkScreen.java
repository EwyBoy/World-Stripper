package net.minecraft.client.gui.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import it.unimi.dsi.fastutil.booleans.BooleanConsumer;
import net.minecraft.client.gui.DialogTexts;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ConfirmOpenLinkScreen extends ConfirmScreen {
   /** Text to warn players from opening unsafe links. */
   private final ITextComponent openLinkWarning;
   /** Label for the Copy to Clipboard button. */
   private final ITextComponent copyLinkButtonText;
   private final String linkText;
   private final boolean showSecurityWarning;

   public ConfirmOpenLinkScreen(BooleanConsumer p_i51121_1_, String p_i51121_2_, boolean p_i51121_3_) {
      super(p_i51121_1_, new TranslationTextComponent(p_i51121_3_ ? "chat.link.confirmTrusted" : "chat.link.confirm"), new StringTextComponent(p_i51121_2_));
      this.confirmButtonText = (ITextComponent)(p_i51121_3_ ? new TranslationTextComponent("chat.link.open") : DialogTexts.field_240634_e_);
      this.cancelButtonText = p_i51121_3_ ? DialogTexts.field_240633_d_ : DialogTexts.field_240635_f_;
      this.copyLinkButtonText = new TranslationTextComponent("chat.copy");
      this.openLinkWarning = new TranslationTextComponent("chat.link.warning");
      this.showSecurityWarning = !p_i51121_3_;
      this.linkText = p_i51121_2_;
   }

   protected void func_231160_c_() {
      super.func_231160_c_();
      this.field_230710_m_.clear();
      this.field_230705_e_.clear();
      this.func_230480_a_(new Button(this.field_230708_k_ / 2 - 50 - 105, this.field_230709_l_ / 6 + 96, 100, 20, this.confirmButtonText, (p_213006_1_) -> {
         this.callbackFunction.accept(true);
      }));
      this.func_230480_a_(new Button(this.field_230708_k_ / 2 - 50, this.field_230709_l_ / 6 + 96, 100, 20, this.copyLinkButtonText, (p_213005_1_) -> {
         this.copyLinkToClipboard();
         this.callbackFunction.accept(false);
      }));
      this.func_230480_a_(new Button(this.field_230708_k_ / 2 - 50 + 105, this.field_230709_l_ / 6 + 96, 100, 20, this.cancelButtonText, (p_213004_1_) -> {
         this.callbackFunction.accept(false);
      }));
   }

   /**
    * Copies the link to the system clipboard.
    */
   public void copyLinkToClipboard() {
      this.field_230706_i_.keyboardListener.setClipboardString(this.linkText);
   }

   public void func_230430_a_(MatrixStack p_230430_1_, int p_230430_2_, int p_230430_3_, float p_230430_4_) {
      super.func_230430_a_(p_230430_1_, p_230430_2_, p_230430_3_, p_230430_4_);
      if (this.showSecurityWarning) {
         func_238472_a_(p_230430_1_, this.field_230712_o_, this.openLinkWarning, this.field_230708_k_ / 2, 110, 16764108);
      }

   }
}